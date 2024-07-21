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
package de.pirckheimer_gymnasium.engine_pi.actor;

import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.util.FileUtil;
import de.pirckheimer_gymnasium.engine_pi.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.nio.file.Path;

/**
 * Stellt ein Zeichen dar, das durch ein Bild repräsentiert ist.
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
     * Das Zeichen, das durch ein Bild dargestellt werden soll.
     */
    char glyph;

    /**
     * Das in den Speicher geladene Bild.
     */
    BufferedImage image;

    /**
     * Der Dateiname des Bilds ohne Dateierweiterung.
     */
    String filename;

    public ImageFontGlyph(Path path)
    {
        filename = FileUtil.getFileName(path);
        try
        {
            image = Resources.IMAGES.get(path.toUri().toURL());
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
     * Gibt das Zeichen, das durch ein Bild dargestellt werden soll, zurück.
     *
     * @return Das Zeichen, das durch ein Bild dargestellt werden soll.
     */
    public char getGlyph()
    {
        return glyph;
    }

    public void setGlyph(char glyph)
    {
        this.glyph = glyph;
    }

    public BufferedImage getImage()
    {
        return image;
    }

    public int getWidth()
    {
        return image.getWidth();
    }

    public int getHeight()
    {
        return image.getHeight();
    }

    /**
     * Gibt den Dateinamen des Bilds ohne Dateierweiterung zurück.
     *
     * @return Der Dateiname des Bilds ohne Dateierweiterung.
     */
    public String getFilename()
    {
        return filename;
    }
}
