/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/resources/Resources.java
 *
 * MIT License
 *
 * Copyright (c) 2016 - 2024 Gurkenlabs
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
package rocks.friedrich.engine_omega.resources;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import rocks.friedrich.engine_omega.sound.Sound;

/**
 * This class is the engines entry point for accessing any kind of resources. A
 * resource is any non-executable data that is deployed with your game. The
 * {@code Resources} class provides access to types of
 * {@code ResourcesContainers} and is used by different (loading) mechanisms to
 * make resources available during runtime.
 * <p>
 * The LITIENGINE supports a variety of different resource types, including:
 * </p>
 *
 * <ul>
 * <li>images</li>
 * <li>sounds</li>
 * </ul>
 *
 * @see ResourcesContainer
 */
public final class AllResourcesContainer
{
    private static final Logger log = Logger
            .getLogger(AllResourcesContainer.class.getName());

    private static ImagesContainer images = new ImagesContainer();

    private static SoundsContainer sounds = new SoundsContainer();

    private AllResourcesContainer()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Stellt den Zugriff auf den {@link ImagesContainer Zwischenspeicher für
     * Bild-Resourcen} vom Datentyp {@link java.awt.image.BufferedImage} bereit.
     *
     * @author Josef Friedrich
     *
     * @return Ein Zwischenspeicher für Bild-Resourcen vom Datentyp
     *         {@link java.awt.image.BufferedImage}.
     */
    public static ImagesContainer getImages()
    {
        return images;
    }

    /**
     * Stellt den Zugriff auf den {@link SoundsContainer Zwischenspeicher für
     * Audio-Resourcen} vom Datentyp
     * {@link rocks.friedrich.engine_omega.sound.Sound Sound} bereit.
     *
     * @author Josef Friedrich
     *
     * @return Ein {@link SoundsContainer Zwischenspeicher für Audio-Resourcen}
     *         vom Datentyp {@link rocks.friedrich.engine_omega.sound.Sound
     *         Sound}.
     */
    public static SoundsContainer getSounds()
    {
        return sounds;
    }

    /**
     * Gets the specified file as InputStream from either a resource folder or
     * the file system.
     *
     * @param file The path to the file.
     * @return The contents of the specified file as {@code InputStream}.
     * @see AllResourcesContainer
     */
    public static InputStream get(String file)
    {
        return get(getLocation(file));
    }

    /**
     * Gets the specified file as InputStream from either a resource folder or
     * the file system.
     *
     * @param file The path to the file.
     * @return The contents of the specified file as {@code InputStream}.
     * @see AllResourcesContainer
     */
    public static InputStream get(URL file)
    {
        InputStream stream = getResource(file);
        if (stream == null)
        {
            return null;
        }
        return stream.markSupported() ? stream
                : new BufferedInputStream(stream);
    }

    /**
     * Reads the specified file as String from either a resource folder or the
     * file system.<br>
     * Since no {@code Charset} is specified with this overload, the
     * implementation uses UTF-8 by default.
     *
     * @param file The path to the file.
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
     * @param file    The path to the file.
     * @param charset The charset that is used to read the String from the file.
     * @return The contents of the specified file as {@code String}
     */
    public static String read(String file, Charset charset)
    {
        final URL location = getLocation(file);
        if (location == null)
        {
            return null;
        }
        return read(location, charset);
    }

    /**
     * Reads the specified file as String from either a resource folder or the
     * file system.<br>
     * Since no {@code Charset} is specified with this overload, the
     * implementation uses UTF-8 by default.
     *
     * @param file The path to the file.
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
     * @param file    The path to the file.
     * @param charset The charset that is used to read the String from the file.
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
            log.log(Level.SEVERE, e.getMessage());
            return null;
        }
    }

    /**
     * Clears the all resource containers by removing previously loaded
     * resources.
     */
    public static void clearAll()
    {
        getSounds().clear();
    }

    public static URL getLocation(String name)
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
                return null;
            }
        }
    }

    private static InputStream getResource(final URL file)
    {
        try
        {
            return file.openStream();
        }
        catch (IOException e)
        {
            return null;
        }
    }
}
