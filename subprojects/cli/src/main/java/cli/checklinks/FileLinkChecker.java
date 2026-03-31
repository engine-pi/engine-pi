/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2026 Josef Friedrich and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
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

/**
 * Ein Kommando-Zeilenwerkzeug zur Überprüfung von {@code file://}-Links in
 * Java- und Markdown-Dateien.
 *
 * <p>
 * Diese Klasse implementiert einen Link-Checker, der rekursiv durch Dateien und
 * Verzeichnisse geht und alle {@code file://}-URIs validiert. Sie überprüft, ob
 * die Zieldateien existieren und gibt detaillierte Berichte über fehlerhafte
 * oder fehlende Links aus.
 * </p>
 *
 * <h2>Funktionsweise</h2>
 *
 * <ul>
 * <li>Sammelt alle unterstützten Quelldateien (Java und Markdown) aus den
 * angegebenen Verzeichnissen</li>
 * <li>Durchsucht jede Datei nach {@code file://}-Links mittels regulärer
 * Ausdrücke</li>
 * <li>Für Java-Dateien: Extrahiert Links nur aus Kommentaren (Zeilen- und
 * Blockkommentare)</li>
 * <li>Für Markdown-Dateien: Scannt den gesamten Inhalt</li>
 * <li>Validiert jeden gefundenen Link und meldet fehlerhafte oder fehlende
 * Ziele</li>
 * </ul>
 *
 * <h2>Verwendung</h2>
 *
 * <pre>
 * java cli.checklinks.FileLinkChecker [OPTIONS] [ROOT...]
 * </pre>
 *
 * <h2>Optionen</h2>
 * <ul>
 * <li>{@code -v, --verbose}: Gibt auch gültige Links aus</li>
 * <li>{@code -h, --help}: Zeigt die Hilfemeldung an</li>
 * </ul>
 *
 * <h2>Parameter</h2>
 * <ul>
 * <li>{@code ROOT}: Optionale Liste von Dateien oder Verzeichnissen zum
 * Scannen. Standardmäßig wird das aktuelle Verzeichnis verwendet</li>
 * </ul>
 *
 * <h2>Rückgabeverte</h2>
 * <ul>
 * <li>0: Alle Links sind gültig</li>
 * <li>1: Ein oder mehrere fehlerhafte oder fehlende Links wurden gefunden</li>
 * </ul>
 *
 * @author Josef Friedrich
 *
 * @since 0.45.0
 */
@Command(name = "checklinks", mixinStandardHelpOptions = true, description = "Checks file:// links in Java and Markdown files")
public class FileLinkChecker implements Callable<Integer>
{
    private static final CommandLine.Help.Ansi ANSI = CommandLine.Help.Ansi.AUTO;

    private static final String BASE_PATH_STRING = "/data/school/repos/inf/java/engine-pi/";

    private static final Pattern FILE_URI_PATTERN = Pattern.compile(
        "file://" + BASE_PATH_STRING + "[^\\s\"'<>`]+",
        Pattern.CASE_INSENSITIVE);

    private static final Set<String> SUPPORTED_EXTENSIONS = Set.of(".java",
        ".md");

    @Parameters(arity = "0..*", paramLabel = "ROOT", description = "Files or directories to scan. Defaults to current directory.")
    private List<Path> roots = new ArrayList<>();

    @Option(names = { "-v",
            "--verbose" }, description = "Print valid links too")
    private boolean verbose;

    /**
     * Führt die Überprüfung von Dateiverknüpfungen durch.
     *
     * <p>
     * Diese Methode scannt alle Quelldateien in den angegebenen
     * Wurzelverzeichnissen auf {@code file://} URIs und überprüft, ob die
     * referenzierten Ziele existieren.
     * </p>
     *
     * <p>
     * Der Prozess umfasst folgende Schritte:
     * </p>
     *
     * <ul>
     * <li>Sammlung aller Quelldateien aus den Wurzelverzeichnissen</li>
     * <li>Zeilenweise Verarbeitung jeder Datei mit UTF-8 Codierung</li>
     * <li>Filterung von Kommentarbereichen (Block-Kommentare werden
     * ignoriert)</li>
     * <li>Extraktion und Validierung von {@code file://} URI-Mustern</li>
     * <li>Bereinigung von nachfolgenden Satzzeichen</li>
     * <li>Auflösung und Überprüfung der Verknüpfungsziele</li>
     * </ul>
     *
     * <p>
     * Für jede überprüfte Verknüpfung wird überprüft:
     * </p>
     *
     * <ul>
     * <li>Ob das Ziel existiert (broken flag)</li>
     * <li>Im ausführlichen Modus werden erfolgreiche Links grün angezeigt</li>
     * <li>Fehlerhafte Verknüpfungen werden gesammelt und am Ende angezeigt</li>
     * </ul>
     *
     * <p>
     * Fehler beim Dateizugriff werden ebenfalls erfasst und in den Bericht
     * aufgenommen.
     * </p>
     *
     * @return 0, wenn keine fehlerhaften Verknüpfungen gefunden wurden; 1 sonst
     */
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

    /**
     * Formatiert eine Meldung für einen fehlerhaften Link.
     *
     * @param sourceFile Der Pfad der Quelldatei, die den fehlerhaften Link
     *     enthält.
     * @param lineNumber Die Zeilennummer, in der der fehlerhafte Link gefunden
     *     wurde.-
     * @param rawLink Der Link mit {@code file://}-Präfix.
     * @param result das Ergebnis der Link-Überprüfung, das Fehlerinformationen
     *     enthält
     *
     * @return eine formatierte Zeichenkette mit farblicher Hervorhebung, die
     *     den fehlerhaften Link und den Grund des Fehlers (Fehler oder fehlende
     *     Datei) anzeigt. Das Format ist:
     *     {@code BROKEN: <sourceFile> :<lineNumber> ->
     *     <rawLink> (<error oder MISSING>)"}
     *
     * @since 0.45.0
     */
    private static String formatBroken(Path sourceFile, int lineNumber,
            String rawLink, LinkCheckResult result)
    {
        if (result.error() != null)
        {
            return String.format("%s: file://%s :%d -> %s (%s)",
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

    /**
     * Löst einen Link zu einer Datei auf und überprüft, ob die Zieldatei
     * existiert.
     *
     * @param rawLink Der zu verarbeitende Link als String (kann ein URI mit
     *     Fragment sein).
     * @param sourceFile der Pfad der Quellendatei, relativ zu der der Link
     *     aufgelöst wird
     *
     * @return ein {@link LinkCheckResult} Objekt, das die aufgelöste Zielpfad,
     *     einen Fehler-Flag (true falls Datei nicht existiert) und eine
     *     optionale Fehlermeldung enthält. Gibt {@code null} zurück, wenn der
     *     Link kein "file"-URI ist oder kein Schema hat
     *
     * @throws keine - Exceptions werden abgefangen und in LinkCheckResult
     *     gekapselt
     */
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

    /**
     * Sammelt alle unterstützten Quelldateien aus den angegebenen
     * Verzeichnissen.
     *
     * <p>
     * Diese Methode durchsucht die gegebenen Pfade und sammelt alle regulären
     * Dateien, die vom System unterstützt werden. Für jede Eingabe wird
     * überprüft, ob es sich um eine existierende Datei oder um ein Verzeichnis
     * handelt:
     * </p>
     *
     * <ul>
     * <li>Existiert der Pfad nicht, wird er übersprungen</li>
     * <li>Ist es eine Datei und wird unterstützt, wird sie hinzugefügt</li>
     * <li>Ist es ein Verzeichnis, werden alle unterstützten Dateien rekursiv
     * gesammelt</li>
     * </ul>
     *
     * <p>
     * Alle gesammelten Pfade werden normalisiert und in absolute Pfade
     * konvertiert. Bei Scan-Fehlern werden Warnmeldungen ausgegeben, aber die
     * Verarbeitung wird fortgesetzt.
     * </p>
     *
     * @param roots Liste der zu durchsuchenden Wurzelpfade (Dateien oder
     *     Verzeichnisse)
     *
     * @return ein Set aller gefundenen unterstützten Quelldateien als
     *     normalisierte absolute Pfade, oder ein leeres Set, wenn keine Dateien
     *     gefunden werden
     *
     * @since 0.45.0
     */
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
