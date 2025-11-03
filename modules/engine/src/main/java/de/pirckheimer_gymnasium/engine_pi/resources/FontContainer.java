/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/resources/Fonts.java
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
package de.pirckheimer_gymnasium.engine_pi.resources;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.GraphicsEnvironment;

import de.pirckheimer_gymnasium.engine_pi.annotations.API;

/**
 * Ein Speicher für <b>Schriftarten</b> des Datentyps {@link Font}.
 *
 * @author Steffen Wilke
 * @author Matthias Wilke
 * @author Josef Friedrich
 */
public final class FontContainer extends ResourcesContainer<Font>
{
    private static final int DEFAULT_SIZE = 12;

    /**
     * Alle möglichen Schriftnamen des Systems, auf dem man sich gerade
     * befindet.<br>
     * Hiernach werden Überprüfungen gemacht, ob die gewünschte Schriftart auf
     * dem System vorhanden ist.
     */
    public static final String[] systemFonts;
    static
    {
        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        systemFonts = ge.getAvailableFontFamilyNames();
    }

    /**
     * Prüft, ob eine Schriftart auf diesem System vorhanden ist.
     *
     * @param fontName Der Name der zu überprüfenden Schriftart.
     *
     * @return <code>true</code>, falls die Schriftart auf dem System existiert,
     *     sonst <code>false</code>.
     */
    @API
    public static boolean isSystemFont(String fontName)
    {
        for (String s : systemFonts)
        {
            if (s.equals(fontName))
            {
                return true;
            }
        }
        return false;
    }

    public static void printSystemFonts()
    {
        for (String s : systemFonts)
        {
            System.out.println(s);
        }
    }

    /**
     * Gibt eine Liste der Namen der Systemschriftarten zurück.
     *
     * @return Liste mit Systemschriftarten.
     */
    @API
    public static String[] getSystemFonts()
    {
        return systemFonts.clone();
    }

    /**
     * Lädt eine Systemschriftart basierend auf dem Namen.
     *
     * @param fontName Name des Fonts.
     *
     * @return Geladener Font.
     */
    @API
    public static Font loadByName(String fontName)
    {
        return new Font(fontName, Font.PLAIN, DEFAULT_SIZE);
    }

    private static final Logger log = Logger
            .getLogger(FontContainer.class.getName());

    public Font get(String name, float size)
    {
        Font font = this.get(name);
        if (font == null)
        {
            return null;
        }
        return font.deriveFont(size);
    }

    public Font get(String name, int style)
    {
        Font font = this.get(name);
        if (font == null)
        {
            return null;
        }
        return font.deriveFont(style);
    }

    public Font get(String name, int style, float size)
    {
        Font font = this.get(name);
        if (font == null)
        {
            return null;
        }
        return font.deriveFont(style, size);
    }

    public Font get(String name)
    {
        if (isSystemFont(name))
        {
            return loadByName(name);
        }
        return super.get(name);
    }

    /**
     * Gibt die mit der Engine Pi mitgelieferte Standardschrift aus.
     *
     * @return
     *
     * @since 0.37.0
     */
    public Font getDefault(FontStyle style)
    {
        String[] fontFiles = { "fonts/Cantarell-Regular.ttf",
                "fonts/Cantarell-Bold.ttf", "fonts/Cantarell-Italic.ttf",
                "fonts/Cantarell-BoldItalic.ttf" };

        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        for (String file : fontFiles)
        {
            try
            {
                Font f = this.get(file);
                if (f != null)
                {
                    ge.registerFont(f);
                }
            }
            catch (Exception e)
            {
                throw new RuntimeException(
                        "failed to register default font " + file);
            }
        }

        Font defaultFont = this.get("Cantarell");
        if (defaultFont == null)
        {
            // fallback to a system sans-serif font
            return new Font(Font.SANS_SERIF, style.getStyle(), DEFAULT_SIZE);
        }

        return defaultFont.deriveFont(style.getStyle(), (float) DEFAULT_SIZE);
    }

    /***
     * Loads a custom font with the specified name from game's resources. As a
     * fallback, when no font could be found by the specified {@code fontName},
     * it tries to get the font from the environment by calling.
     *
     * @param resourceName The name of the font
     *
     * @return The loaded font.
     *
     * @see Font#createFont(int, java.io.File)
     * @see Font#getFont(String)
     */
    @Override
    protected Font load(URL resourceName)
    {
        try (final InputStream fontStream = ResourceLoader.get(resourceName))
        {
            if (fontStream == null)
            {
                log.log(Level.SEVERE, "font {0} could not be loaded",
                        resourceName);
                return null;
            }
            return Font.createFont(Font.TRUETYPE_FONT, fontStream);
        }
        catch (final FontFormatException | IOException e)
        {
            log.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
    }
}
