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
package pi.configuration;

import pi.Game;
import pi.Resources;
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

/**
 * Configuration class that manages multiple configuration groups and handles
 * loading and saving settings.
 *
 * @author Steffen Wilke
 * @author Matthias Wilke
 *
 * @since 0.42.0
 */
public class ConfigurationLoader
{
    private static final Logger log = Logger
            .getLogger(ConfigurationLoader.class.getName());

    private static final String DEFAULT_CONFIGURATION_FILE_NAME = "config.properties";

    private final List<ConfigurationGroup> configurationGroups;

    private final Path path;

    /**
     * Initializes a new instance of the {@code Configuration} class.
     *
     * @param configurationGroups The configuration groups managed by this
     *     instance.
     */
    public ConfigurationLoader(final ConfigurationGroup... configurationGroups)
    {
        this(DEFAULT_CONFIGURATION_FILE_NAME, configurationGroups);
    }

    /**
     * Constructs a new instance of the {@code Configuration} class using the
     * specified file name. This constructor converts the provided file name
     * string into a {@code Path} object and delegates the initialization to
     * another constructor.
     *
     * @param path The path of the file from which to load the settings.
     * @param configurationGroups The configuration groups managed by this
     *     instance.
     */
    public ConfigurationLoader(final String path,
            final ConfigurationGroup... configurationGroups)
    {
        this(Path.of(path), configurationGroups);
    }

    /**
     * Initializes a new instance of the {@code Configuration} class.
     *
     * @param path The path of the file from which to load the settings.
     * @param configurationGroups The configuration groups managed by this
     *     instance.
     */
    public ConfigurationLoader(final Path path,
            final ConfigurationGroup... configurationGroups)
    {
        this.path = path;
        this.configurationGroups = new ArrayList<>();
        if (configurationGroups != null && configurationGroups.length > 0)
        {
            Collections.addAll(this.configurationGroups, configurationGroups);
        }
    }

    /**
     * Gets the strongly typed configuration group if it was previously added to
     * the configuration.
     *
     * @param <T> The type of the config group.
     * @param groupClass The class that provides the generic type for this
     *     method.
     *
     * @return The configuration group of the specified type or null if none can
     *     be found.
     */
    public <T extends ConfigurationGroup> T getConfigurationGroup(
            final Class<T> groupClass)
    {
        for (final ConfigurationGroup group : getConfigurationGroups())
        {
            if (group.getClass().equals(groupClass))
            {
                return groupClass.cast(group);
            }
        }

        return null;
    }

    /**
     * Gets the configuration group with the specified prefix.
     *
     * @param prefix The prefix of the configuration group to retrieve.
     *
     * @return The configuration group with the specified prefix, or null if
     *     none can be found.
     */
    public ConfigurationGroup getConfigurationGroup(final String prefix)
    {
        for (final ConfigurationGroup group : getConfigurationGroups())
        {

            final ConfigurationGroupInfo info = group.getClass()
                    .getAnnotation(ConfigurationGroupInfo.class);
            if (info == null)
            {
                continue;
            }

            if (info.prefix().equals(prefix))
            {
                return group;
            }
        }

        return null;
    }

    /**
     * Gets all {@code ConfigurationGroups} from the configuration.
     *
     * @return All config groups.
     */
    public List<ConfigurationGroup> getConfigurationGroups()
    {
        return this.configurationGroups;
    }

    /**
     * Adds the specified configuration group to the configuration.
     *
     * @param group The group to add.
     */
    public void add(ConfigurationGroup group)
    {
        getConfigurationGroups().add(group);
    }

    /**
     * Gets the path of the file to which this configuration is saved.
     *
     * @return The path of the configuration file.
     *
     * @see #save()
     */
    public Path getPath()
    {
        return this.path;
    }

    /**
     * Versucht, die Konfiguration aus der Datei im Anwendungsordner zu
     * <b>laden</b>.
     *
     * <p>
     * Wenn keine vorhanden ist, versucht es, die Datei aus einem beliebigen
     * Ressourcenordner zu laden. Wenn keine vorhanden ist, erstellt es eine
     * neue Konfigurationsdatei im Anwendungsordner.
     * </p>
     */
    public void load()
    {
        try (InputStream settingsStream = Resources.get(getPath().toString()))
        {
            if (!Files.exists(getPath()) || !Files.isRegularFile(getPath())
                    || settingsStream == null)
            {
                try (OutputStream out = Files.newOutputStream(getPath()))
                {
                    createDefaultSettingsFile(out);
                }
                log.log(Level.INFO, "Konfigurationsdatei „{0}“ erstellt",
                        getPath());
                return;
            }
        }
        catch (IOException e)
        {
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        if (Files.exists(getPath()))
        {
            try (InputStream settingsStream = Files.newInputStream(getPath());
                    BufferedInputStream bufferedStream = new BufferedInputStream(
                            settingsStream))
            {

                Properties properties = new Properties();
                properties.load(bufferedStream);
                initializeSettingsByProperties(properties);
                log.log(Level.INFO, "Konfiguration aus der Datei „{0}“ geladen",
                        getPath());
            }
            catch (IOException e)
            {
                log.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    /**
     * Saves this configuration to a file with the specified name of this
     * instance (config.properties is the engines default config file).
     *
     * @see #getPath()
     * @see ConfigurationLoader#DEFAULT_CONFIGURATION_FILE_NAME
     */
    public void save()
    {
        try
        {
            Files.deleteIfExists(getPath());
        }
        catch (IOException e)
        {
            throw new RuntimeException(
                    "Failed to delete existing configuration file", e);
        }

        try (OutputStream out = Files.newOutputStream(getPath(),
                StandardOpenOption.CREATE_NEW))
        {
            getConfigurationGroups().stream()
                    .filter(group -> Game.isDebug() || !group.isDebug())
                    .forEach(group -> storeConfigurationGroup(out, group));

            log.log(Level.INFO, "Configuration {0} saved", getPath());
        }
        catch (IOException e)
        {
            log.log(Level.SEVERE,
                    "Failed to save configuration: " + e.getMessage(), e);
        }
    }

    private static void storeConfigurationGroup(final OutputStream out,
            final ConfigurationGroup group)
    {
        try
        {
            final Properties groupProperties = new CleanProperties();
            group.storeProperties(groupProperties);
            groupProperties.store(out, group.getPrefix() + "SETTINGS");
            out.flush();
        }
        catch (final IOException e)
        {
            log.log(Level.SEVERE, e.getMessage(), e);
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
     * @see #getConfigurationGroups()
     * @see #storeConfigurationGroup(OutputStream, ConfigurationGroup)
     */
    private void createDefaultSettingsFile(final OutputStream out)
    {
        for (final ConfigurationGroup group : getConfigurationGroups())
        {
            storeConfigurationGroup(out, group);
        }
    }

    private void initializeSettingsByProperties(final Properties properties)
    {
        for (final String key : properties.stringPropertyNames())
        {
            for (final ConfigurationGroup group : getConfigurationGroups())
            {
                if (key.startsWith(group.getPrefix()))
                {
                    group.initializeByProperty(key,
                            properties.getProperty(key));
                }
            }
        }
    }
}
