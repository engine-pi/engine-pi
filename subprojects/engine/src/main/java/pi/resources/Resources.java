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
package pi.resources;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import pi.resources.color.ColorContainer;
import pi.resources.font.FontContainer;
import pi.resources.sound.SoundContainer;

/**
 * Zur <b>Aufbewahrung</b> und <b>Verwaltung</b> verschiedener
 * <b>Ressourcen</b>.
 *
 * <p>
 * Diese Klasse ist der Einstiegspunkt für den Zugriff auf alle Arten von
 * {@link Resource}n. Eine Ressource ist jede nicht-ausführbare Datei, die mit
 * dem Spiel bereitgestellt wird. Die {@link Resources} Klasse bietet Zugriff
 * auf verschiedene Spezialisierungen von {@link ResourcesContainer} und wird
 * von verschiedenen (Lade-)Mechanismen verwendet, um Ressourcen während der
 * Laufzeit verfügbar zu machen.
 * </p>
 *
 * @see ResourcesContainer
 */
public final class Resources
{
    private static final Logger log = Logger
            .getLogger(Resources.class.getName());

    /**
     * Ein <b>Speicher</b> für <b>Farben</b> des Datentyps {@link java.awt.Color
     * Color}.
     */
    public static final ColorContainer colors = new ColorContainer();

    /**
     * Ein Speicher für <b>Schriftarten</b> des Datentyps {@link java.awt.Font
     * Font}.
     */
    public static final FontContainer fonts = new FontContainer();

    /**
     * Ein Speicher für <b>Bilder</b> des Datentyps
     * {@link java.awt.image.BufferedImage BufferedImage}.
     */
    public static final ImageContainer images = new ImageContainer();

    /**
     * Ein Speicher für <b>Klänge</b> des Datentyps
     * {@link pi.resources.sound.Sound Sound}.
     */
    public static final SoundContainer sounds = new SoundContainer();


    /**
     * Der private Konstruktor verhindert, dass Instanzen von dieser Klasse
     * gemacht werden. Die Klassen, hat ausschließlich statischen Attributen und
     * Methoden.
     *
     * @throws UnsupportedOperationException
     */
    private Resources()
    {
        throw new UnsupportedOperationException();
    }


    /**
     * Stellt den Zugriff auf den {@link ImageContainer Zwischenspeicher für
     * Bild-Resourcen} vom Datentyp {@link java.awt.image.BufferedImage} bereit.
     *
     * @author Josef Friedrich
     *
     * @return Ein Zwischenspeicher für Bild-Ressourcen vom Datentyp
     *     {@link java.awt.image.BufferedImage}.
     */
    public static ImageContainer getImages()
    {
        return images;
    }

    /**
     * Stellt den Zugriff auf den {@link SoundContainer Zwischenspeicher für
     * Audio-Resourcen} vom Datentyp {@link pi.resources.sound.Sound Sound}
     * bereit.
     *
     * @author Josef Friedrich
     *
     * @return Ein {@link SoundContainer Zwischenspeicher für Audio-Ressourcen}
     *     vom Datentyp {@link pi.resources.sound.Sound Sound}.
     */
    public static SoundContainer getSounds()
    {
        return sounds;
    }

    /**
     * Clears the all resource containers by removing previously loaded
     * resources.
     */
    public static void clearAll()
    {
        colors.clear();
        fonts.clear();
        images.clear();
        sounds.clear();
    }

    /**
     * Gets the specified file as InputStream from either a resource folder or
     * the file system.
     *
     * @param file The path to the file.
     *
     * @return The contents of the specified file as {@code InputStream}.
     *
     * @see Resources
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
     *
     * @return The contents of the specified file as {@code InputStream}.
     *
     * @see Resources
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
     * @param file The path to the file.
     * @param charset The charset that is used to read the String from the file.
     *
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
     * @param file The path to the file.
     * @param charset The charset that is used to read the String from the file.
     *
     * @return The contents of the specified file as {@code String}
     */
    public static String read(URL file, Charset charset)
    {
        try (Scanner scanner = new Scanner(file.openStream(), charset))
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
     * Gets the location of the specified resource. This method attempts to find
     * the resource using the system class loader first. If the resource is not
     * found, it tries to locate it as a file in the file system.
     *
     * @param name The name of the resource.
     *
     * @return The URL of the resource, or null if the resource could not be
     *     found.
     */
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
                return (Path.of(name).toUri().toURL());
            }
            catch (MalformedURLException e1)
            {
                return null;
            }
        }
    }

    /**
     * Retrieves an InputStream for the specified URL. This method attempts to
     * open a stream to the resource located at the given URL.
     *
     * @param file The URL of the resource to be accessed.
     *
     * @return An InputStream to the resource, or null if an I/O error occurs.
     */
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
