package de.pirckheimer_gymnasium.engine_pi.actor;

import de.pirckheimer_gymnasium.engine_pi.util.FileUtil;

import java.awt.image.BufferedImage;
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

    public ImageFontGlyph(Path path, BufferedImage image)
    {
        this.filename = FileUtil.getFileName(path);
        this.image = image;
        if (filename.length() == 1)
        {
            glyph = filename.charAt(0);
        }
    }

    public char getGlyph()
    {
        return glyph;
    }

    public BufferedImage getImage()
    {
        return image;
    }

    public String getFilename()
    {
        return filename;
    }
}
