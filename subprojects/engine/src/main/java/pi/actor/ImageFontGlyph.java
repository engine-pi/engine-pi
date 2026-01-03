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
package pi.actor;

import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.nio.file.Path;

import pi.Resources;
import pi.annotations.Getter;
import pi.annotations.Setter;
import pi.util.FileUtil;
import pi.util.ImageUtil;

/**
 * Stellt ein <b>Zeichen</b> dar, das durch ein <b>Bild</b> repräsentiert ist.
 *
 * @author Josef Friedrich
 *
 * @since 0.27.0
 *
 * @see ImageFont
 */
public class ImageFontGlyph
{
    /**
     * Das <b>Zeichen</b>, das durch ein Bild dargestellt werden soll.
     */
    char glyph;

    /**
     * Das in den Speicher geladene <b>Bild</b>, das ein Zeichen darstellt.
     */
    BufferedImage image;

    /**
     * Der <b>Dateiname</b> des Bilds ohne Dateierweiterung.
     */
    String filename;

    public ImageFontGlyph(Path path)
    {
        filename = FileUtil.getFileName(path);
        try
        {
            image = Resources.images.get(path.toUri().toURL());
            image = ImageUtil.addAlphaChannel(image);
        }
        catch (MalformedURLException e)
        {
            throw new RuntimeException(e);
        }
        if (filename.length() == 1)
        {
            glyph = filename.charAt(0);
        }
    }

    /**
     * Gibt das <b>Zeichen</b>, das durch ein Bild dargestellt werden soll,
     * zurück.
     *
     * @return Das Zeichen, das durch ein Bild dargestellt werden soll.
     */
    @Getter
    public char glyph()
    {
        return glyph;
    }

    /**
     * Gibt das <b>Zeichen</b>, das durch ein Bild dargestellt werden soll, als
     * Zeichenkette zurück.
     *
     * @return Das <b>Zeichen</b>, das durch ein Bild dargestellt werden soll,
     *     als Zeichenkette.
     */
    @Getter
    public String content()
    {
        return String.valueOf(glyph);
    }

    /**
     * Setzt das <b>Zeichen</b>, das durch ein Bild dargestellt werden soll.
     *
     * @param glyph Das <b>Zeichen</b>, das durch ein Bild dargestellt werden
     *     soll.
     */
    @Setter
    public void glyph(char glyph)
    {
        this.glyph = glyph;
    }

    /**
     * Gibt das in den Speicher geladene <b>Bild</b>, das ein Zeichen darstellt,
     * zurück.
     *
     * @return Das in den Speicher geladene <b>Bild</b>, das ein Zeichen
     *     darstellt.
     */
    @Getter
    public BufferedImage image()
    {
        return image;
    }

    /**
     * Gibt die <b>Breite</b> des Bilds in Pixel zurück.
     *
     * @return Die <b>Breite</b> des Bilds in Pixel.
     */
    public int getWidth()
    {
        return image.getWidth();
    }

    /**
     * Gibt die <b>Höhe</b> des Bilds in Pixel zurück.
     *
     * @return Die <b>Höhe</b> des Bilds in Pixel.
     */
    @Getter
    public int height()
    {
        return image.getHeight();
    }

    /**
     * Gibt den Dateinamen des Bilds ohne Dateierweiterung zurück.
     *
     * @return Der Dateiname des Bilds ohne Dateierweiterung.
     */
    @Getter
    public String filename()
    {
        return filename;
    }

    /**
     * Gibt den <b>Unicode-Namen</b> des Zeichens (beispielsweise
     * {@code LATIN CAPITAL LETTER A}) zurück.
     *
     * @return Den <b>Unicode-Namen</b> des Zeichens.
     */
    @Getter
    public String unicodeName()
    {
        return filename.substring(5).replace("-", " ").toUpperCase();
    }

    /**
     * Gibt die <b>vierstellige, hexadezimale Unicode-Nummer</b> des Zeichens
     * (beispielsweise {@code 0041}) zurück.
     *
     * @return Die vierstellige, hexadezimale Unicode-Nummer.
     */
    @Getter
    public String hexNumber()
    {
        return filename.substring(0, 4).toUpperCase();
    }
}
