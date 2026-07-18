/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/configuration/Configuration.java
 *
 * MIT License
 *
 * Copyright (c) 2016 - 2025 Gurkenlabs
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package pi.config;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import pi.annotations.Getter;
import pi.resources.ResourceLoader;

/**
 * Eine <b>Konfigurations</b>klasse, die mehrere Konfigurationsgruppen verwaltet
 * und das <b>Laden</b> und <b>Speichern</b> von Einstellungen übernimmt.
 *
 * @author Steffen Wilke
 * @author Matthias Wilke
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public class ConfigLoader
{
    private static final Logger log = Logger
        .getLogger(ConfigLoader.class.getName());

    private final List<ConfigGroup> groups;

    private final Path path;

    /**
     * Erstellt eine neue Instanz der Klasse {@link ConfigLoader} mit dem
     * angegebenen Dateinamen. Dieser Konstruktor wandelt den übergebenen
     * Dateinamen-String in ein {@link Path}-Objekt um und delegiert die
     * Initialisierung an einen anderen Konstruktor.
     *
     * @param path Der Pfad der Datei, aus der die Einstellungen geladen werden.
     * @param configurationGroups Die von dieser Instanz verwalteten
     *     Konfigurationsgruppen.
     */
    public ConfigLoader(final String path,
            final ConfigGroup... configurationGroups)
    {
        this(Path.of(path), configurationGroups);
    }

    /**
     * Erstellt eine neue Instanz der Klasse {@link ConfigLoader}.
     *
     * @param path Der Pfad der Datei, aus der die Einstellungen geladen werden.
     * @param configurationGroups Die von dieser Instanz verwalteten
     *     Konfigurationsgruppen.
     */
    public ConfigLoader(final Path path,
            final ConfigGroup... configurationGroups)
    {
        this.path = path;
        this.groups = new ArrayList<>();
        if (configurationGroups != null && configurationGroups.length > 0)
        {
            Collections.addAll(this.groups, configurationGroups);
        }
    }

    // Go to
    // file:///data/school/repos/inf/java/engine-pi/docs/manual/resources/config.md

    /**
     * Gibt die typisierte Konfigurationsgruppe zurück, sofern sie zuvor zur
     * Konfiguration hinzugefügt wurde.
     *
     * @param <T> Der Typ der Konfigurationsgruppe.
     * @param groupClass Die Klasse, die den generischen Typ für diese Methode
     *     bereitstellt.
     *
     * @throws ConfigException wenn die Konfigurationsgruppe mit dem angegebenen
     *     Präfix gefunden wird.
     */
    public <T extends ConfigGroup> T getGroup(final Class<T> groupClass)
    {
        for (final ConfigGroup group : groups())
        {
            if (group.getClass().equals(groupClass))
            {
                return groupClass.cast(group);
            }
        }

        throw new ConfigException(
                "Die Konfigurationsgruppe " + groupClass.getCanonicalName()
                        + " ist noch nicht registriert worden.");
    }

    // Go to
    // file:///data/school/repos/inf/java/engine-pi/docs/manual/resources/config.md

    /**
     * Ruft eine Konfigurationsgruppe basierend auf ihrem Präfix ab.
     *
     * @param prefix Das Präfix der gesuchten Konfigurationsgruppe
     *
     * @return Die gefundene {@link ConfigGroup}
     *
     * @throws ConfigException wenn keine Konfigurationsgruppe mit dem
     *     angegebenen Präfix gefunden wird.
     */
    public ConfigGroup getGroup(final String prefix)
    {
        for (final ConfigGroup group : groups())
        {

            final ConfigGroupInfo info = group.getClass()
                .getAnnotation(ConfigGroupInfo.class);
            if (info == null)
            {
                continue;
            }

            if (info.prefix().equals(prefix))
            {
                return group;
            }
        }

        throw new ConfigException("Die Konfigurationsgruppe mit dem Präfix "
                + prefix + " konnte nicht gefunden werden.");
    }

    /**
     * Gibt alle {@link ConfigGroup Konfigurationsgruppen} zurück.
     *
     * @return Alle Konfigurationsgruppen.
     */
    @Getter
    public List<ConfigGroup> groups()
    {
        return groups;
    }

    // Go to
    // file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/resources/config/MyConfigGroup.java

    // Go to
    // file:///data/school/repos/inf/java/engine-pi/docs/manual/resources/config.md

    /**
     * <b>Fügt</b> die angegeben <b>Konfigurationsgruppen</b> zu dieser
     * Konfigurationsinstanz <b>hinzu</b>.
     *
     * <p>
     * Eine bereits vorhandene {@code properties}-Datei wird dabei geladen.
     * {@link #save()} muss jedoch manuell ausgeführt werden, falls
     * aktualisierte Werte in die {@code properties}-Datei gespeichert werden
     * sollen.
     * </p>
     *
     * @param groups Die <b>Konfigurationsgruppen</b>, die zu dieser
     *     Konfigurationsinstanz hinzugefügt werden sollen.
     */
    public void addGroup(ConfigGroup... groups)
    {
        Collections.addAll(this.groups, groups);
        // Gruppen, die später hinzugefügt werden, haben in der
        // engine-pi.properties-Datei eventuell schon Werte gespeichert. Diese
        // müssen noch geladen werden.
        load();

        // Speichern sollte manuell ausgeführt werden.
        // Würden wir in dieser Methode save() ausführen, gehen Werte von
        // Gruppen verloren, die erst später hinzugefügt wurden.
        // save();
    }

    /**
     * Ruft den <b>Pfad</b> der Datei ab, in der diese Konfiguration gespeichert
     * ist.
     *
     * @return Der <b>Pfad</b> zur Konfigurationsdatei.
     *
     * @see #save()
     */
    @Getter
    public Path path()
    {
        return path;
    }

    /**
     * <b>Lädt</b> die Konfiguration aus der Datei im Anwendungsordner.
     *
     * <p>
     * Ist keine Konfigurationsdatei vorhanden, wir einen neue Datei erstellt.
     * </p>
     *
     * @throws ConfigException Wenn das Laden der Konfigurationsdatei
     *     fehlgeschlagen ist.
     */
    public void load()
    {
        InputStream settingsStream = null;

        try
        {
            settingsStream = ResourceLoader.get(path().toString());
        }
        catch (Exception e)
        {
            // Ignore
        }

        try
        {
            if (!Files.exists(path()) || !Files.isRegularFile(path())
                    || settingsStream == null)
            {
                try (OutputStream out = Files.newOutputStream(path()))
                {
                    createDefaultSettingsFile(out);
                }
                log.log(Level.CONFIG,
                    "Konfigurationsdatei „{0}“ erstellt",
                    path());
                return;
            }
        }
        catch (IOException e)
        {
            throw new ConfigException(e);
        }

        if (Files.exists(path()))
        {
            try (InputStream settingsStream2 = Files.newInputStream(path());
                    BufferedInputStream bufferedStream = new BufferedInputStream(
                            settingsStream2))
            {

                Properties properties = new Properties();
                properties.load(bufferedStream);
                initializeSettingsByProperties(properties);
                log.log(Level.CONFIG,
                    "Konfiguration aus der Datei „{0}“ geladen",
                    path());
            }
            catch (IOException e)
            {
                throw new ConfigException(
                        "Das Laden der Konfigurationsdatei ist fehlgeschlagen: "
                                + e.getMessage());
            }
        }
    }

    /**
     * Speichert diese Konfiguration in eine Datei mit dem für diese Instanz
     * festgelegten Namen (engine-pi.properties ist die
     * Standard-Konfigurationsdatei der Engine).
     *
     * @throws ConfigException Wenn das Speichern der Konfigurationsdatei
     *     fehlgeschlagen ist.
     */
    public void save()
    {
        deleteConfigFile();

        try (OutputStream out = Files.newOutputStream(path(),
            StandardOpenOption.CREATE_NEW))
        {
            groups().forEach(group -> storeConfigurationGroup(out, group));

            log.log(Level.CONFIG,
                "Die Konfiguration wurde in die Datei {0} gespeichert.",
                path());
        }
        catch (IOException e)
        {
            throw new ConfigException(
                    "Das Speichern der Konfigurationsdatei ist fehlgeschlagen: "
                            + e.getMessage());
        }
    }

    /**
     * <b>Löscht</b> die Konfigurationsdatei, falls sie existiert.
     *
     * <p>
     * Diese Methode versucht, die Konfigurationsdatei unter dem durch
     * {@link #path()} definierten Pfad zu löschen. Falls die Datei nicht
     * existiert, wird keine Aktion durchgeführt.
     * </p>
     *
     * @throws ConfigException falls beim Löschen der Datei ein Fehler auftritt
     */
    public void deleteConfigFile()
    {
        try
        {
            Files.deleteIfExists(path());
        }
        catch (IOException e)
        {

            throw new ConfigException(
                    "Das Löschen der existierenden Konfigurationsdatei ist fehlgeschlagen: "
                            + e.getMessage());
        }
    }

    /**
     * Speichert die übergebene Konfigurationsgruppe im angegebenen
     * Ausgabestrom.
     *
     * <p>
     * Die Eigenschaften der Gruppe werden zuerst in ein
     * {@link CleanProperties}-Objekt geschrieben und anschließend zusammen mit
     * einem Kommentar in den {@link OutputStream} serialisiert.
     * </p>
     *
     * @param out Der Ausgabestrom, in den die Eigenschaften geschrieben werden.
     * @param group Die zu speichernde Konfigurationsgruppe.
     *
     * @throws ConfigException falls die Konfigurationsgruppe nicht gespeichert
     *     werden kann.
     */
    private static void storeConfigurationGroup(final OutputStream out,
            final ConfigGroup group)
    {
        try
        {
            final Properties groupProperties = new CleanProperties();
            group.storeProperties(groupProperties);
            groupProperties.store(out, group.prefix() + "SETTINGS");
            out.flush();
        }
        catch (final IOException e)
        {
            throw new ConfigException("Die Konfigurationsgruppe "
                    + group.getClass().getCanonicalName()
                    + " konnte nicht gespeichert werden: " + e.getMessage());
        }
    }

    /**
     * Erstellt eine Konfigurationsdatei durch Speicherung aller
     * Konfigurationsgruppen.
     *
     * <p>
     * Diese Methode iteriert über alle verfügbaren Konfigurationsgruppen und
     * speichert jede Gruppe in den angegebenen {@link OutputStream}.
     * </p>
     *
     * @param out Der {@link OutputStream}, in den die Konfigurationsgruppen
     *     geschrieben werden sollen.
     *
     * @see #groups()
     * @see #storeConfigurationGroup(OutputStream, ConfigGroup)
     */
    private void createDefaultSettingsFile(final OutputStream out)
    {
        for (final ConfigGroup group : groups())
        {
            storeConfigurationGroup(out, group);
        }
    }

    /**
     * Initialisiert alle registrierten Konfigurationsgruppen anhand der
     * übergebenen Eigenschaften.
     *
     * <p>
     * Für jeden Schlüssel wird geprüft, ob er mit dem Präfix einer
     * Konfigurationsgruppe beginnt. Trifft dies zu, wird die jeweilige Gruppe
     * mit Schlüssel und Wert initialisiert.
     * </p>
     *
     * @param properties Die geladenen Eigenschaften aus der
     *     Konfigurationsdatei.
     */
    private void initializeSettingsByProperties(final Properties properties)
    {
        for (final String key : properties.stringPropertyNames())
        {
            for (final ConfigGroup group : groups())
            {
                if (key.startsWith(group.prefix()))
                {
                    group.initializeByProperty(key,
                        properties.getProperty(key));
                }
            }
        }
    }
}
