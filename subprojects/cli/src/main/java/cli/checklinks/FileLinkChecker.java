package cli.checklinks;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "checklinks", mixinStandardHelpOptions = true, description = "Checks file:// links in Java and Markdown files")
public class FileLinkChecker implements Callable<Integer>
{
    private static final CommandLine.Help.Ansi ANSI = CommandLine.Help.Ansi.AUTO;

    private static final String BASE_PATH_STRING = "/data/school/repos/inf/java/engine-pi/";

    private static final Pattern FILE_URI_PATTERN = Pattern.compile(
        "file://" + BASE_PATH_STRING + "[^\\s\"'<>`]+",
        Pattern.CASE_INSENSITIVE);

    private static final Set<String> SUPPORTED_EXTENSIONS = Set
        .of(".java", ".md", ".markdown");

    @Parameters(arity = "0..*", paramLabel = "ROOT", description = "Files or directories to scan. Defaults to current directory.")
    private List<Path> roots = new ArrayList<>();

    @Option(names = { "-v",
            "--verbose" }, description = "Print valid links too")
    private boolean verbose;

    @Override
    public Integer call()
    {
        List<Path> effectiveRoots = roots.isEmpty() ? List.of(Path.of("."))
                : roots;
        Set<Path> files = collectSourceFiles(effectiveRoots);

        int linksChecked = 0;
        List<String> brokenReports = new ArrayList<>();

        for (Path sourceFile : files)
        {
            try (Stream<String> stream = Files.lines(sourceFile,
                StandardCharsets.UTF_8))
            {
                boolean[] inBlockComment = new boolean[] { false };
                int lineNumber = 0;
                for (String line : (Iterable<String>) stream::iterator)
                {
                    lineNumber++;
                    String scanText = textToScan(sourceFile,
                        line,
                        inBlockComment);
                    if (scanText == null || scanText.isBlank())
                    {
                        continue;
                    }
                    Matcher matcher = FILE_URI_PATTERN.matcher(scanText);
                    while (matcher.find())
                    {
                        String rawLink = stripTrailingPunctuation(
                            matcher.group());
                        LinkCheckResult result = resolveLink(rawLink,
                            sourceFile);
                        if (result != null)
                        {
                            linksChecked++;
                            if (result.broken())
                            {
                                brokenReports.add(formatBroken(sourceFile,
                                    lineNumber,
                                    rawLink,
                                    result));
                            }
                            else if (verbose)
                            {
                                System.out.printf("%s: %s:%d -> %s%n",
                                    color("@|green,bold OK|@"),
                                    sourceFile.normalize(),
                                    lineNumber,
                                    result.targetPath().normalize());
                            }
                        }
                    }
                }
            }
            catch (IOException e)
            {
                brokenReports.add(String.format("ERROR: Could not read %s (%s)",
                    sourceFile.normalize(),
                    e.getMessage()));
            }
        }

        if (!brokenReports.isEmpty())
        {
            for (String report : brokenReports)
            {
                System.out.println(report);
            }
            System.out.printf(
                "%n%s %d file:// links in %d files, %d issue(s) found.%n",
                color("@|red,bold Checked|@"),
                linksChecked,
                files.size(),
                brokenReports.size());
            return 1;
        }

        System.out.printf("%s %d file:// links in %d files. %s%n",
            color("@|green,bold Checked|@"),
            linksChecked,
            files.size(),
            color("@|green,bold No missing target files found.|@"));
        return 0;
    }

    private static String formatBroken(Path sourceFile, int lineNumber,
            String rawLink, LinkCheckResult result)
    {
        if (result.error() != null)
        {
            return String.format("%s: %s:%d -> %s (%s)",
                color("@|red,bold BROKEN|@"),
                sourceFile.normalize(),
                lineNumber,
                rawLink,
                result.error());
        }
        return String.format("%s: file://%s :%d -> %s (%s)",
            color("@|red,bold BROKEN|@"),
            sourceFile.normalize(),
            lineNumber,
            rawLink,
            color("@|red MISSING|@"));
    }

    private static LinkCheckResult resolveLink(String rawLink, Path sourceFile)
    {
        try
        {
            URI uriWithFragment = URI.create(rawLink);
            if (uriWithFragment.getScheme() == null
                    || !"file".equalsIgnoreCase(uriWithFragment.getScheme()))
            {
                return null;
            }
            URI uri = new URI(uriWithFragment.getScheme(),
                    uriWithFragment.getAuthority(), uriWithFragment.getPath(),
                    uriWithFragment.getQuery(), null);
            Path target = Path.of(uri);
            if (!target.isAbsolute())
            {
                target = sourceFile.getParent().resolve(target).normalize();
            }
            boolean exists = Files.exists(target);
            return new LinkCheckResult(target, !exists, null);
        }
        catch (IllegalArgumentException | URISyntaxException e)
        {
            return new LinkCheckResult(null, true, e.getMessage());
        }
    }

    private static Set<Path> collectSourceFiles(List<Path> roots)
    {
        Set<Path> result = new LinkedHashSet<>();
        for (Path root : roots)
        {
            if (!Files.exists(root))
            {
                continue;
            }
            if (Files.isRegularFile(root))
            {
                if (isSupportedFile(root))
                {
                    result.add(root.toAbsolutePath().normalize());
                }
                continue;
            }
            try (Stream<Path> stream = Files.walk(root))
            {
                stream.filter(Files::isRegularFile)
                    .filter(FileLinkChecker::isSupportedFile)
                    .map(path -> path.toAbsolutePath().normalize())
                    .forEach(result::add);
            }
            catch (IOException e)
            {
                System.out.printf("%s: Could not scan %s (%s)%n",
                    color("@|yellow,bold WARN|@"),
                    root.toAbsolutePath().normalize(),
                    e.getMessage());
            }
        }
        return result;
    }

    private static boolean isSupportedFile(Path path)
    {
        String lower = path.getFileName().toString().toLowerCase();
        return SUPPORTED_EXTENSIONS.stream().anyMatch(lower::endsWith);
    }

    private static String textToScan(Path sourceFile, String line,
            boolean[] inBlockComment)
    {
        String lower = sourceFile.getFileName().toString().toLowerCase();
        if (lower.endsWith(".md") || lower.endsWith(".markdown"))
        {
            return line;
        }

        // For Java files only comments are relevant for file:// links.
        if (!lower.endsWith(".java"))
        {
            return null;
        }

        if (inBlockComment[0])
        {
            int blockEnd = line.indexOf("*/");
            if (blockEnd >= 0)
            {
                inBlockComment[0] = false;
                return line.substring(0, blockEnd + 2);
            }
            return line;
        }

        int lineComment = line.indexOf("//");
        int blockStart = line.indexOf("/*");

        if (lineComment < 0 && blockStart < 0)
        {
            return null;
        }

        if (lineComment >= 0 && (blockStart < 0 || lineComment < blockStart))
        {
            return line.substring(lineComment);
        }

        if (blockStart >= 0)
        {
            int blockEnd = line.indexOf("*/", blockStart + 2);
            if (blockEnd < 0)
            {
                inBlockComment[0] = true;
                return line.substring(blockStart);
            }
            return line.substring(blockStart, blockEnd + 2);
        }

        return null;
    }

    private static String stripTrailingPunctuation(String value)
    {
        int end = value.length();
        while (end > 0)
        {
            char ch = value.charAt(end - 1);
            if (ch == ')' || ch == ']' || ch == '}' || ch == '.' || ch == ','
                    || ch == ';' || ch == ':' || ch == '!' || ch == '?')
            {
                end--;
            }
            else
            {
                break;
            }
        }
        return value.substring(0, end);
    }

    private static String color(String markup)
    {
        return ANSI.string(markup);
    }

    private record LinkCheckResult(Path targetPath, boolean broken,
            String error)
    {
    }

    public static void main(String[] args)
    {
        System.exit(new CommandLine(new FileLinkChecker()).execute(args));
    }
}
