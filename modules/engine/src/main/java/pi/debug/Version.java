/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2024 Josef Friedrich and contributors.
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
package pi.debug;

import java.io.IOException;
import java.net.JarURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import pi.annotations.API;
import pi.resources.ResourceLoader;

/**
 * Gibt die <b>Versionsnummer</b> sowie weitere <b>Build-Informationen</b>
 * zurück.
 */
public final class Version
{
    /**
     * Gibt an, ob das Programm gerade aus einer Jar heraus gestartet wurde.
     *
     * @return <code>true</code>, falls ja, sonst <code>false</code>.
     */
    @API
    public static boolean isJar()
    {
        String className = MainAnimation.class.getName().replace('.', '/');
        String classJar = MainAnimation.class
                .getResource("/" + className + ".class").toString();
        return classJar.startsWith("jar:");
    }

    /**
     * Gibt den Namen der Jar-Datei zurück, die gerade ausgeführt wird.
     *
     * @return Dateiname der Jar-Datei oder <code>null</code>, falls das
     *     Programm nicht über eine Jar-Datei ausgeführt wird.
     */
    @API
    public static String getJarName()
    {
        String className = MainAnimation.class.getName().replace('.', '/');
        String classJar = MainAnimation.class
                .getResource("/" + className + ".class").toString();
        if (classJar.startsWith("jar:"))
        {
            String[] values = classJar.split("/");
            for (String value : values)
            {
                if (value.contains("!"))
                {
                    try
                    {
                        return java.net.URLDecoder.decode(
                                value.substring(0, value.length() - 1),
                                StandardCharsets.UTF_8);
                    }
                    catch (Exception e)
                    {
                        return null;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Gibt an, wann die Jar-Datei erzeugt wurde.
     *
     * @return Erzeugungsdatum der Jar-Datei in Sekunden seit dem 01.01.1970
     *     (Unix Timestamp) oder den aktuellen Timestamp, falls nicht von einer
     *     Jar-Datei ausgeführt.
     */
    @API
    public static long getBuildTime()
    {
        try
        {
            String uri = MainAnimation.class.getName().replace('.', '/')
                    + ".class";
            JarURLConnection j = (JarURLConnection) ClassLoader
                    .getSystemResource(uri).openConnection();
            long time = j.getJarFile().getEntry("META-INF/MANIFEST.MF")
                    .getTime();
            return time > 0 ? time : System.currentTimeMillis();
        }
        catch (Exception e)
        {
            return System.currentTimeMillis();
        }
    }

    private static final Properties PROJECT_PROPERTIES;

    private static Properties getProjectProperties()
    {
        // https://stackoverflow.com/questions/26551439/getting-maven-project-version-and-artifact-id-from-pom-while-running-in-eclipse/26573884#26573884
        final Properties properties = new Properties();
        try
        {
            properties.load(ResourceLoader.loadAsStream("project.properties"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return properties;
    }

    static
    {
        PROJECT_PROPERTIES = getProjectProperties();
    }

    public static String getVersion()
    {
        return PROJECT_PROPERTIES.getProperty("version");
    }

    private static final Properties GIT_PROPERTIES;

    private static Properties getGitProperties()
    {
        final Properties properties = new Properties();
        try
        {
            properties.load(ResourceLoader.loadAsStream("git.properties"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return properties;
    }

    static
    {
        GIT_PROPERTIES = getGitProperties();
    }

    public static String getGitCommitIdAbbrev()
    {
        return GIT_PROPERTIES.getProperty("git.commit.id.abbrev");
    }
}
