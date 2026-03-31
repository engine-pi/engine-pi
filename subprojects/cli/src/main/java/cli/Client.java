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
package cli;

import java.io.File;

import cli.checklinks.FileLinkChecker;
import cli.java2umltext.Client.Config;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.PropertiesDefaultProvider;

/**
 * Haupteinstiegspunkt für die Engine Pi Kommandozeilen-Anwendung.
 *
 * <p>
 * Diese Klasse dient als Einstiegspunkt für die CLI und verwaltet die
 * verfügbaren Unterbefehle wie FileLinkChecker und Config. Sie implementiert
 * das Runnable-Interface und nutzt die Picocli-Bibliothek zur Verarbeitung von
 * Kommandozeilenargumenten.
 * </p>
 *
 * <p>
 * Die Klasse ermöglicht es Benutzern, verschiedene Engine Pi Tools über die
 * Kommandozeile aufzurufen. Wenn kein Unterbefehl angegeben wird, wird die
 * Nutzungsmitteilung ausgegeben.
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.45.0
 */
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
