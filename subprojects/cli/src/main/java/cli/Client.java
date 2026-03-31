package cli;

import java.io.File;

import cli.checklinks.FileLinkChecker;
import cli.java2umltext.Client.Config;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.PropertiesDefaultProvider;

@Command(name = "cli", mixinStandardHelpOptions = true, description = "Engine Pi command line tools", subcommands = {
        FileLinkChecker.class, Config.class })
public class Client implements Runnable
{
    @Override
    public void run()
    {
        // Print command usage when no subcommand is provided.
        CommandLine.usage(this, System.out);
    }

    public static void main(String[] args)
    {
        CommandLine root = new CommandLine(new Client())
            .setCaseInsensitiveEnumValuesAllowed(true);
        CommandLine java2umltext = root.getSubcommands().get("java2umltext");
        if (java2umltext != null)
        {
            java2umltext.setCaseInsensitiveEnumValuesAllowed(true);
            File propertiesFile = new File(System.getProperty("user.dir"),
                    "java2umltext.properties");
            if (propertiesFile.isFile() && propertiesFile.canRead())
            {
                java2umltext.setDefaultValueProvider(
                    new PropertiesDefaultProvider(propertiesFile));
            }
        }

        int exitCode = root.execute(args);
        System.exit(exitCode);
    }
}
