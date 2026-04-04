/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/internal/io/ResourceLoader.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2017 Michael Andonie and contributors.
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
package pi.resources;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import pi.annotations.Getter;
import pi.util.FileUtil;

/**
 * Lädt Dateien aus der JAR oder dem aktuellen Arbeitsverzeichnis.
 *
 * @author Niklas Keller
 */
final public class ResourceLoader
{

    private ResourceLoader()
    {
        // keine Objekte erlaubt!
    }

    public static byte[] load(String filename) throws IOException
    {
        String normalizedFilename = FileUtil.normalizePath(filename);
        Path path = Paths.get(normalizedFilename);
        URL url = ResourceLoader.class.getResource("/" + normalizedFilename);
        if (url != null)
        {
            try
            {
                path = Paths.get(url.toURI());
            }
            catch (URISyntaxException e)
            {
                throw new ResourceLoadException("Could not convert URL to URI",
                        e);
            }
        }
        return Files.readAllBytes(path);
    }

    public static InputStream loadAsStream(String filename) throws IOException
    {
        String normalizedFilename = FileUtil.normalizePath(filename);
        if (ResourceLoader.class.getResource("/" + normalizedFilename) != null)
        {
            return ResourceLoader.class
                .getResourceAsStream("/" + normalizedFilename);
        }
        return new FileInputStream(FileUtil.normalizePath(normalizedFilename));
    }

    public static File loadAsFile(String filename)
    {
        String normalizedFilename = FileUtil.normalizePath(filename);
        URL url = ResourceLoader.class.getResource("/" + normalizedFilename);
        if (url != null)
        {
            try
            {
                return new File(url.toURI());
            }
            catch (URISyntaxException e)
            {
                throw new ResourceLoadException(filename, e);
            }
        }
        return new File(FileUtil.normalizePath(normalizedFilename));
    }

    /**
     * Gets the specified file as InputStream from either a resource folder or
     * the file system.
     *
     * @author Steffen Wilke
     * @author Matthias Wilke
     *
     * @param file The path to the file.
     *
     * @return The contents of the specified file as {@code InputStream}.
     *
     * @see Resources
     */
    public static InputStream get(String file)
    {
        return get(location(file));
    }

    /**
     * Gets the specified file as InputStream from either a resource folder or
     * the file system.
     *
     * @author Steffen Wilke
     * @author Matthias Wilke
     *
     * @param file The path to the file.
     *
     * @return The contents of the specified file as {@code InputStream}.
     *
     * @see Resources
     */
    public static InputStream get(URL file)
    {
        InputStream stream = resource(file);
        return stream.markSupported() ? stream
                : new BufferedInputStream(stream);
    }

    /**
     * Reads the specified file as String from either a resource folder or the
     * file system.<br>
     * Since no {@code Charset} is specified with this overload, the
     * implementation uses UTF-8 by default.
     *
     * @author Steffen Wilke
     * @author Matthias Wilke
     *
     * @param file The path to the file.
     *
     * @return The contents of the specified file as {@code String}
     */
    public static String read(String file)
    {
        return read(file, StandardCharsets.UTF_8);
    }

    /**
     * Reads the specified file as String from either a resource folder or the
     * file system.<br>
     *
     * @author Steffen Wilke
     * @author Matthias Wilke
     *
     * @param file The path to the file.
     * @param charset The charset that is used to read the String from the file.
     *
     * @return The contents of the specified file as {@code String}
     */
    public static String read(String file, Charset charset)
    {
        return read(location(file), charset);
    }

    /**
     * Reads the specified file as String from either a resource folder or the
     * file system.<br>
     * Since no {@code Charset} is specified with this overload, the
     * implementation uses UTF-8 by default.
     *
     * @author Steffen Wilke
     * @author Matthias Wilke
     *
     * @param file The path to the file.
     *
     * @return The contents of the specified file as {@code String}
     */
    public static String read(URL file)
    {
        return read(file, StandardCharsets.UTF_8);
    }

    /**
     * Reads the specified file as String from either a resource folder or the
     * file system.<br>
     *
     * @author Steffen Wilke
     * @author Matthias Wilke
     *
     * @param file The path to the file.
     * @param charset The charset that is used to read the String from the file.
     *
     * @return The contents of the specified file as {@code String}
     */
    public static String read(URL file, Charset charset)
    {
        try (Scanner scanner = new Scanner(file.openStream(),
                charset.toString()))
        {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : null;
        }
        catch (IOException e)
        {
            throw new ResourceLoadException(String.valueOf(file), e);
        }
    }

    /**
     * Ermittelt die {@link URL} zu einer angegebenen Ressource oder Datei.
     *
     * <p>
     * Zuerst wird versucht, die Ressource über den System-Klassenlader zu
     * finden. Falls dies nicht gelingt, wird der übergebene Name als URL
     * interpretiert. Ist auch das nicht möglich, wird der Name als Dateipfad
     * behandelt und in eine URL umgewandelt.
     *
     * @param name Name der Ressource, URL oder Dateipfad
     *
     * @return die gefundene oder erzeugte URL
     *
     * @throws ResourceLoadException wenn der angegebene Wert weder als
     *     Ressource, noch als URL oder gültiger Dateipfad verarbeitet werden
     *     kann
     *
     * @author Steffen Wilke
     * @author Matthias Wilke
     */
    @Getter
    public static URL location(String name)
    {
        URL fromClass = ClassLoader.getSystemResource(name);
        if (fromClass != null)
        {
            return fromClass;
        }
        try
        {
            return new URL(name);
        }
        catch (MalformedURLException e)
        {
            try
            {
                return (new File(name)).toURI().toURL();
            }
            catch (MalformedURLException e1)
            {
                throw new ResourceLoadException(name, e1);
            }
        }
    }

    /**
     * Öffnet einen Input-Stream für die angegebene Ressourcen-URL.
     *
     * @param file die URL der zu ladenden Ressource
     *
     * @return ein InputStream für die Ressource
     *
     * @throws ResourceLoadException wenn die Ressource nicht geöffnet werden
     *     kann
     *
     * @author Steffen Wilke
     * @author Matthias Wilke
     */
    @Getter
    private static InputStream resource(final URL file)
    {
        try
        {
            return file.openStream();
        }
        catch (IOException e)
        {
            throw new ResourceLoadException(String.valueOf(file), e);
        }
    }
}
