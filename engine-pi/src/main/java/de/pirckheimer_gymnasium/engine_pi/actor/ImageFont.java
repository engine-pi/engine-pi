package de.pirckheimer_gymnasium.engine_pi.actor;

import static de.pirckheimer_gymnasium.engine_pi.actor.ImageFontCaseSensitivity.TO_LOWER;
import static de.pirckheimer_gymnasium.engine_pi.actor.ImageFontCaseSensitivity.TO_UPPER;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.util.ImageUtil;
import de.pirckheimer_gymnasium.engine_pi.util.TextAlignment;
import de.pirckheimer_gymnasium.engine_pi.util.TextUtil;

/**
 * Eine <b>Schriftart</b>, bei der die einzelnen <b>Buchstaben</b> durch ein
 * <b>Bild</b> repräsentiert sind.
 *
 * <p>
 * Jedes Bild entspricht einem Buchstaben oder Zeichen. Die Bilder müssen alle
 * die gleiche Abmessung aufweisen.
 * </p>
 *
 * Eine Alternative wäre die <a href=
 * "https://javadoc.io/doc/com.badlogicgames.gdx/gdx/1.4.0/com/badlogic/gdx/graphics/g2d/BitmapFont.html">BitmapFont-Klasse</a>
 * der Game-Engine libgdx.
 * <a href="https://libgdx.com/wiki/graphics/2d/fonts/bitmap-fonts">...</a>
 *
 * @author Josef Friedrich
 *
 * @since 0.23.0
 */
public class ImageFont
{
    /**
     * Der Pfad zu einem Ordner, in dem die Bilder der einzelnen Buchstaben
     * liegen.
     */
    private String basePath;

    /**
     * Die Breite der Buchstabenbilder in Pixel.
     */
    private int glyphWidth;

    /**
     * Die Höhe der Buchstabenbilder in Pixel.
     */
    private int glyphHeight;

    /**
     * Die Dateierweiterung der Buchstabenbilder.
     */
    private String extension;

    /**
     * Wie oft ein Pixel vervielfältigt werden soll. Beispielsweise verwandelt
     * die Zahl 3 ein Pixel in 9 Pixel der Abmessung 3x3.
     */
    private int pixelMultiplication = 1;

    /**
     * Die Farbe in der die schwarze Farbe der Ausgangsbilder umgefärbt werden
     * soll.
     */
    private Color color;

    /**
     * Die Handhabung der Groß- und Kleinschreibung.
     */
    private ImageFontCaseSensitivity caseSensitivity;

    /**
     * Die maximale Anzahl an Zeichen, die eine Zeile aufnehmen kann.
     */
    private int lineWidth = -1;

    /**
     * Die Textausrichtung.
     */
    private TextAlignment alignment;

    private final Map<Character, String> map = new HashMap<>();

    /**
     * Ob bei einem nicht vorhandenen Zeichen eine Fehlermeldung geworfen werden
     * soll oder nicht.
     */
    private boolean throwException = true;

    /**
     * @param basePath        Der Pfad zu einem Ordner, in dem die Bilder der
     *                        einzelnen Buchstaben liegen.
     * @param glyphWidth      Die Breite der Buchstabenbilder in Pixel.
     * @param glyphHeight     Die Höhe der Buchstabenbilder in Pixel.
     * @param extension       Die Dateierweiterung der Buchstabenbilder.
     * @param caseSensitivity Die Handhabung der Groß- und Kleinschreibung.
     * @param alignment       Die Textausrichtung.
     */
    public ImageFont(String basePath, int glyphWidth, int glyphHeight,
            String extension, ImageFontCaseSensitivity caseSensitivity,
            TextAlignment alignment)
    {
        this.basePath = basePath;
        this.glyphWidth = glyphWidth;
        this.glyphHeight = glyphHeight;
        this.extension = extension;
        this.caseSensitivity = caseSensitivity;
        this.alignment = alignment;
        addMapping('.', "dot");
        addMapping(',', "comma");
        addMapping('"', "quotes");
        addMapping('©', "copyright");
    }

    /**
     * Erzeugt eine neue Bilderschriftart. Die einzelnen Glyphen müssten 8x8
     * Pixel und als Dateierweiterung pgn haben. Der Text wird linksbündig
     * ausgerichtet.
     *
     * @param basePath        Der Pfad zu einem Ordner, in dem die Bilder der
     *                        einzelnen Buchstaben liegen.
     * @param caseSensitivity Die Handhabung der Groß- und Kleinschreibung.
     */
    public ImageFont(String basePath, ImageFontCaseSensitivity caseSensitivity)
    {
        this(basePath, 8, 8, "png", caseSensitivity, TextAlignment.LEFT);
    }

    /**
     * Setzt den Pfad zu einem Ordner, in dem die Bilder der einzelnen
     * Buchstaben liegen.
     *
     * @param basePath Der Pfad zu einem Ordner, in dem die Bilder der einzelnen
     *                 Buchstaben liegen.
     *
     * @return Eine Instanz dieser Klasse, damit mehrere Setter mit der
     *         Punktschreibweise verkettet werden können.
     */
    public ImageFont setBasePath(String basePath)
    {
        this.basePath = basePath;
        return this;
    }

    /**
     * Setzt die Breite der Buchstabenbilder in Pixel.
     *
     * @param glyphWidth Die Breite der Buchstabenbilder in Pixel.
     *
     * @return Eine Instanz dieser Klasse, damit mehrere Setter mit der
     *         Punktschreibweise verkettet werden können.
     */
    public ImageFont setGlyphWidth(int glyphWidth)
    {
        this.glyphWidth = glyphWidth;
        return this;
    }

    /**
     * Gibt die Breite der Buchstabenbilder in Pixel zurück.
     *
     * @return Die Breite der Buchstabenbilder in Pixel.
     */
    public int getGlyphWidth()
    {
        return glyphWidth;
    }

    /**
     * Setzt die Höhe der Buchstabenbilder in Pixel.
     *
     * @param glyphHeight Die Höhe der Buchstabenbilder in Pixel.
     *
     * @return Eine Instanz dieser Klasse, damit mehrere Setter mit der
     *         Punktschreibweise verkettet werden können.
     */
    public ImageFont setGlyphHeight(int glyphHeight)
    {
        this.glyphHeight = glyphHeight;
        return this;
    }

    /**
     * Setzt die Farbe, in der die schwarze Farbe der Ausgangsbilder umgefärbt
     * werden soll.
     *
     * @param color Die Farbe, in der die schwarze Farbe der Ausgangsbilder
     *              umgefärbt werden soll.
     *
     * @return Eine Instanz dieser Klasse, damit mehrere Setter mit der
     *         Punktschreibweise verkettet werden können.
     */
    public ImageFont setColor(Color color)
    {
        this.color = color;
        return this;
    }

    /**
     * Gibt die Farbe zurück, in der die schwarze Farbe der Ausgangsbilder
     * umgefärbt werden soll.
     *
     * @return Die Farbe, in der die schwarze Farbe der Ausgangsbilder umgefärbt
     *         werden soll.
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * Setzt die Dateierweiterung der Buchstabenbilder.
     *
     * @param extension Die Dateierweiterung der Buchstabenbilder.
     *
     * @return Eine Instanz dieser Klasse, damit mehrere Setter mit der
     *         Punktschreibweise verkettet werden können.
     */
    public ImageFont setExtension(String extension)
    {
        this.extension = extension;
        return this;
    }

    /**
     * Setzt, wie oft ein Pixel vervielfältigt werden soll.
     *
     * @param pixelMultiplication Wie oft ein Pixel vervielfältigt werden soll.
     *                            Beispielsweise verwandelt die Zahl 3 ein Pixel
     *                            in 9 Pixel der Abmessung 3x3.
     *
     * @return Eine Instanz dieser Klasse, damit mehrere Setter mit der
     *         Punktschreibweise verkettet werden können.
     */
    public ImageFont setPixelMultiplication(int pixelMultiplication)
    {
        this.pixelMultiplication = pixelMultiplication;
        return this;
    }

    /**
     * Gibt zurück, wie oft ein Pixel vervielfältigt werden soll.
     *
     * @return Wie oft ein Pixel vervielfältigt werden soll. Beispielsweise
     *         verwandelt die Zahl 3 ein Pixel in 9 Pixel der Abmessung 3x3.
     */
    public int getPixelMultiplication()
    {
        return pixelMultiplication;
    }

    /**
     * @param caseSensitivity Die Handhabung der Groß- und Kleinschreibung.
     *
     * @return Eine Instanz dieser Klasse, damit mehrere Setter mit der
     *         Punktschreibweise verkettet werden können.
     */
    public ImageFont setCaseSensitivity(
            ImageFontCaseSensitivity caseSensitivity)
    {
        this.caseSensitivity = caseSensitivity;
        return this;
    }

    /**
     * Setzt die maximale Anzahl an Zeichen, die eine Zeile aufnehmen kann.
     *
     * @param lineWidth Die maximale Anzahl an Zeichen, die eine Zeile aufnehmen
     *                  kann.
     *
     * @return Eine Instanz dieser Klasse, damit mehrere Setter mit der
     *         Punktschreibweise verkettet werden können.
     */
    public ImageFont setLineWidth(int lineWidth)
    {
        this.lineWidth = lineWidth;
        return this;
    }

    /**
     * Gibt die maximale Anzahl an Zeichen zurück, die eine Zeile aufnehmen
     * kann.
     *
     * @return Die maximale Anzahl an Zeichen, die eine Zeile aufnehmen kann.
     */
    public int getLineWidth()
    {
        return lineWidth;
    }

    /**
     * Gibt die maximale Anzahl an Zeichen zurück, die eine Zeile aufnehmen
     * kann.
     *
     * @return Die maximale Anzahl an Zeichen, die eine Zeile aufnehmen kann.
     */
    public int getLineWidth(String content)
    {
        if (lineWidth == -1)
        {
            return TextUtil.getLineWidth(content) + 1;
        }
        return lineWidth;
    }

    /**
     * Gibt die maximale Anzahl an Zeichen zurück, die eine Zeile aufnehmen
     * kann.
     *
     * @return Die maximale Anzahl an Zeichen, die eine Zeile aufnehmen kann.
     */
    public int getLineWidth(String content, int lineWidth)
    {
        if (lineWidth == -1)
        {
            return TextUtil.getLineWidth(content) + 1;
        }
        return lineWidth;
    }

    /**
     * Setzt die Textausrichtung.
     *
     * @param alignment Die Textausrichtung.
     *
     * @return Eine Instanz dieser Klasse, damit mehrere Setter mit der
     *         Punktschreibweise verkettet werden können.
     */
    public ImageFont setAlignment(TextAlignment alignment)
    {
        this.alignment = alignment;
        return this;
    }

    /**
     * Gibt die Textausrichtung zurück.
     *
     * @return Die Textausrichtung.
     */
    public TextAlignment getAlignment()
    {
        return alignment;
    }

    /**
     * @param throwException Ob bei einem nicht vorhandenen Zeichen eine
     *                       Fehlermeldung geworfen werden soll oder nicht.
     *
     * @return Eine Instanz dieser Klasse, damit mehrere Setter mit der
     *         Punktschreibweise verkettet werden können.
     */
    public ImageFont setThrowException(boolean throwException)
    {
        this.throwException = throwException;
        return this;
    }

    private String convertGlyphToImageName(char glyph)
    {
        String filename = map.get(glyph);
        if (filename != null)
        {
            return filename;
        }
        return String.valueOf(glyph);
    }

    public void addMapping(char letter, String filename)
    {
        map.put(letter, filename);
    }

    private String getImagePath(char glyph)
    {
        return basePath + "/" + convertGlyphToImageName(glyph) + "."
                + extension;
    }

    private BufferedImage loadBufferedImage(char glyph, String content)
    {
        if (glyph == ' ')
        {
            return null;
        }
        try
        {
            BufferedImage image = Resources.IMAGES.get(getImagePath(glyph));
            if (image != null)
            {
                image = ImageUtil.addAlphaChannel(image);
            }
            return image;
        }
        catch (Exception e)
        {
            if (throwException)
            {
                throw new RuntimeException("Unbekannter Buchstabe „" + glyph
                        + "“ im Text „" + content + "“.");
            }
        }
        return null;
    }

    private String processContent(String content, int lineWidth,
            TextAlignment alignment)
    {
        if (caseSensitivity == TO_UPPER)
        {
            content = content.toUpperCase();
        }
        else if (caseSensitivity == TO_LOWER)
        {
            content = content.toLowerCase();
        }
        return TextUtil.wrap(content, lineWidth, alignment);
    }

    /**
     * Setzt den gegebenen Textinhalt in ein Bild.
     *
     * @param content             Der Textinhalt, der in das Bild geschrieben
     *                            werden soll.
     * @param lineWidth           Die maximale Anzahl an Zeichen, die eine Zeile
     *                            aufnehmen kann.
     * @param alignment           Die Textausrichtung.
     * @param color               Die Farbe, in der die schwarze Farbe der
     *                            Ausgangsbilder umgefärbt werden soll.
     * @param pixelMultiplication Wie oft ein Pixel vervielfältigt werden soll.
     *                            Beispielsweise verwandelt die Zahl 3 ein Pixel
     *                            in 9 Pixel der Abmessung 3x3.
     *
     * @return Ein Bild.
     */
    public BufferedImage render(String content, int lineWidth,
            TextAlignment alignment, Color color, int pixelMultiplication)
    {
        lineWidth = getLineWidth(content, lineWidth);
        content = processContent(content, lineWidth, alignment);
        int lineCount = TextUtil.getLineCount(content);
        int imageHeight = glyphHeight * lineCount;
        int imageWidth = glyphWidth * lineWidth;
        BufferedImage image = new BufferedImage(imageWidth, imageHeight,
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        String[] lines = TextUtil.splitLines(content);
        int y;
        int x;
        for (int i = 0; i < lines.length; i++)
        {
            y = i * glyphHeight;
            String line = lines[i];
            for (int j = 0; j < line.length(); j++)
            {
                x = j * glyphWidth;
                BufferedImage glyph = loadBufferedImage(line.charAt(j),
                        content);
                if (glyph != null)
                {
                    g.drawImage(glyph, x, y, null);
                }
            }
        }
        if (color != null)
        {
            image = ImageUtil.replaceColor(image, Color.BLACK, color);
        }
        if (pixelMultiplication > 1)
        {
            image = ImageUtil.scale(image, pixelMultiplication);
        }
        return image;
    }

    /**
     * Setzt den gegebenen Textinhalt in ein Bild.
     *
     * @param content Der Textinhalt, der in das Bild geschrieben werden soll.
     *
     * @return Ein Bild.
     */
    public BufferedImage render(String content)
    {
        return render(content, getLineWidth(content), alignment, color,
                pixelMultiplication);
    }
}
