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
 *
 * @author Josef Friedrich
 *
 * @since 0.20.0
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
            throw new RuntimeException(e);
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

    /**
     * Lädt Git-Eigenschaften aus der Datei "git.properties".
     *
     * <p>
     * Diese Methode lädt die Git-Eigenschaften aus einer Ressourcendatei namens
     * "git.properties" und gibt sie als {@link Properties}-Objekt zurück.
     * </p>
     *
     * <p>
     * Das <a href=
     * "https://github.com/git-commit-id/git-commit-id-maven-plugin">maven git
     * commit id plugin</a> ist folgendermaßen konfiguriert.
     * </p>
     *
     * <pre>
     * {@code
     * <plugin>
     *     <groupId>io.github.git-commit-id</groupId>
     *     <artifactId>git-commit-id-maven-plugin</artifactId>
     *     <version>9.0.2</version>
     *     <executions>
     *         <execution>
     *             <id>get-the-git-infos</id>
     *             <goals>
     *                 <goal>revision</goal>
     *             </goals>
     *             <phase>validate</phase>
     *         </execution>
     *     </executions>
     *     <configuration>
     *         <generateGitPropertiesFile>true</generateGitPropertiesFile>
     *         <generateGitPropertiesFilename>${project.build.outputDirectory}/git.properties</generateGitPropertiesFilename>
     *         <dotGitDirectory>${project.basedir}/.git</dotGitDirectory>
     *     </configuration>
     * </plugin>
     * }
     * </pre>
     *
     *
     * <pre>
     * {@code
     * #Generated by Git-Commit-Id-Plugin
     * git.branch=main
     * git.build.host=zotac
     * git.build.time=2025-12-31T10\:47\:45+01\:00
     * git.build.user.email=josef@friedrich.rocks
     * git.build.user.name=Josef Friedrich
     * git.build.version=0.41.0
     * git.closest.tag.commit.count=106
     * git.closest.tag.name=v0.41.0
     * git.commit.author.time=2025-12-31T10\:06\:22+01\:00
     * git.commit.committer.time=2025-12-31T10\:06\:22+01\:00
     * git.commit.id=892f7dffe86c70889928fc8d7a02987b8dab2219
     * git.commit.id.abbrev=892f7df
     * git.commit.id.describe=v0.41.0-106-g892f7df-dirty
     * git.commit.id.describe-short=v0.41.0-106-dirty
     * git.commit.message.full=Refactor the photographer
     * git.commit.message.short=Refactor the photographer
     * git.commit.time=2025-12-31T10\:06\:22+01\:00
     * git.commit.user.email=josef@friedrich.rocks
     * git.commit.user.name=Josef Friedrich
     * git.dirty=true
     * git.local.branch.ahead=0
     * git.local.branch.behind=0
     * git.remote.origin.url=git@github.com\:engine-pi/engine-pi.git
     * git.tag=
     * git.tags=
     * git.total.commit.count=1630
     * }
     * </pre>
     *
     *
     * @return Das {@link Properties}-Objekt mit den Git-Eigenschaften aus der
     *     {@code git.properties}-Datei
     *
     * @throws RuntimeException wenn eine {@link IOException} beim Laden der
     *     Eigenschaften auftritt.
     */
    private static Properties getGitProperties()
    {
        final Properties properties = new Properties();
        try
        {
            properties.load(ResourceLoader.loadAsStream("git.properties"));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        return properties;
    }

    static
    {
        GIT_PROPERTIES = getGitProperties();
    }

    /**
     * Ruft die <b>vollständige Git-Commit-ID</b> des aktuellen Builds ab (z.B.
     * {@code "892f7dffe86c70889928fc8d7a02987b8dab2219"}).
     *
     * @return die Git-Commit-ID als String, oder {@code null} wenn nicht
     *     verfügbar
     *
     * @since 0.42.0
     */
    public static String getGitCommitId()
    {
        return GIT_PROPERTIES.getProperty("git.commit.id");
    }

    /**
     * Gibt die <b>abgekürzte Git-Commit-ID</b> zurück (z.B. {@code "892f7df"}).
     *
     * @return die abgekürzte Git-Commit-ID als Zeichenkette, oder {@code null}
     *     falls nicht verfügbar
     */
    public static String getGitCommitIdAbbrev()
    {
        return GIT_PROPERTIES.getProperty("git.commit.id.abbrev");
    }

    /**
     * Gibt die <b>Git-Commit-ID-Beschreibung</b> zurück (z.B.
     * {@code "v0.41.0-106-g892f7df-dirty"}).
     *
     * @return die Git-Commit-ID-Beschreibung als String, oder {@code null}
     *     falls die Eigenschaft nicht verfügbar ist
     *
     * @since 0.42.0
     */
    public static String getGitCommitIdDescribe()
    {
        return GIT_PROPERTIES.getProperty("git.commit.id.describe");
    }

    /**
     * Gibt die <b>abgekürzte Git-Commit-ID-Beschreibung</b> zurück (z.B.
     * {@code "v0.41.0-106-dirty"}).
     *
     * @return die abgekürzte Git-Commit-ID als String, oder {@code null} falls
     *     nicht verfügbar
     *
     * @since 0.42.0
     */
    public static String getGitCommitIdDescribeShort()
    {
        return GIT_PROPERTIES.getProperty("git.commit.id.describe-short");
    }

    /**
     * Gibt die <b>URL des Repositories</b> zurück (z.B.
     * {@code "git@github.com\:engine-pi/engine-pi.git"}).
     *
     * @return Die URL des Repositories als Zeichenkette, oder {@code null}
     *     falls nicht verfügbar.
     *
     * @since 0.42.0
     */
    public static String getGitRemoteOriginUrl()
    {
        return GIT_PROPERTIES.getProperty("git.remote.origin.url");
    }
}
