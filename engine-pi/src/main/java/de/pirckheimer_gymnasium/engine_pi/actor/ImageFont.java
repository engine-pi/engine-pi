package de.pirckheimer_gymnasium.engine_pi.actor;

import static de.pirckheimer_gymnasium.engine_pi.actor.ImageFontCaseSensitivity.TO_LOWER;
import static de.pirckheimer_gymnasium.engine_pi.actor.ImageFontCaseSensitivity.TO_UPPER;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.debug.ToStringFormatter;
import de.pirckheimer_gymnasium.engine_pi.resources.NamedColor;
import de.pirckheimer_gymnasium.engine_pi.resources.ResourceLoader;
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
 * <p>
 * Eine Alternative wäre die <a href=
 * "https://javadoc.io/doc/com.badlogicgames.gdx/gdx/1.4.0/com/badlogic/gdx/graphics/g2d/BitmapFont.html">BitmapFont-Klasse</a>
 * der Game-Engine libgdx.
 * <a href="https://libgdx.com/wiki/graphics/2d/fonts/bitmap-fonts">...</a>
 *
 * @author Josef Friedrich
 *
 * @since 0.23.0
 *
 * @see ImageFontText
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
     * die Zahl {@code 3} ein Pixel in {@code 9 Pixel} der Abmessung
     * {@code 3x3}.
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

    private final Map<Character, ImageFontGlyph> glyphs = new HashMap<>();

    private final Map<String, ImageFontGlyph> glyphsByFilename = new HashMap<>();

    /**
     * Ob bei einem nicht vorhandenen Zeichen eine Fehlermeldung geworfen werden
     * soll oder nicht.
     */
    private boolean throwException = true;

    /**
     * Erzeugt eine neue Bilderschriftart.
     *
     * @param basePath        Der Pfad zu einem Ordner, in dem die Bilder der
     *                        einzelnen Buchstaben liegen.
     * @param extension       Die Dateierweiterung der Buchstabenbilder.
     * @param caseSensitivity Die Handhabung der Groß- und Kleinschreibung.
     * @param alignment       Die Textausrichtung.
     */
    public ImageFont(String basePath, String extension,
            ImageFontCaseSensitivity caseSensitivity, TextAlignment alignment)
    {
        this.basePath = basePath;
        this.extension = extension;
        this.caseSensitivity = caseSensitivity;
        this.alignment = alignment;
        glyphWidth = 0;
        glyphHeight = 0;
        addDefaultMapping();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(
                Objects.requireNonNull(ResourceLoader.getLocation(basePath))
                        .toURI())))
        {
            for (Path path : stream)
            {
                if (!Files.isDirectory(path) && path.toString().toLowerCase()
                        .endsWith("." + this.extension.toLowerCase()))
                {
                    BufferedImage glyph = Resources.IMAGES
                            .get(path.toUri().toURL());
                    if (glyph != null)
                    {
                        if ((glyphWidth > 0 && glyphWidth != glyph.getWidth())
                                || (glyphHeight > 0
                                        && glyphHeight != glyph.getHeight()))
                        {
                            throw new Exception(
                                    "Alle Bilder einer Bilderschriftart müssen die gleichen Abmessungen haben!");
                        }
                        glyphWidth = glyph.getWidth();
                        glyphHeight = glyph.getHeight();


                        ImageFontGlyph imageFontGlyph = new ImageFontGlyph(path, glyph);
                        glyphsByFilename.put(path.getFileName().toString(),imageFontGlyph
                                );

                        if (imageFontGlyph.getGlyph() != 0) {
                            glyphs.put(imageFontGlyph.getGlyph(), imageFontGlyph);
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }


    }

    /**
     * Erzeugt eine neue Bilderschriftart. Die einzelnen Glyphen müssen als
     * Dateierweiterung {@code png} haben. Der Text wird linksbündig
     * ausgerichtet.
     *
     * @param basePath        Der Pfad zu einem Ordner, in dem die Bilder der
     *                        einzelnen Buchstaben liegen.
     * @param caseSensitivity Die Handhabung der Groß- und Kleinschreibung.
     */
    public ImageFont(String basePath, ImageFontCaseSensitivity caseSensitivity)
    {
        this(basePath, "png", caseSensitivity, TextAlignment.LEFT);
    }

    /**
     * Erzeugt eine neue Bilderschriftart. Die einzelnen Glyphen müssen als
     * Dateierweiterung {@code png} haben. Der Text wird linksbündig
     * ausgerichtet.
     *
     * @param basePath Der Pfad zu einem Ordner, in dem die Bilder der einzelnen
     *                 Buchstaben liegen.
     */
    public ImageFont(String basePath)
    {
        this(basePath, null);
    }

    /**
     * Setzt den Pfad zu einem Ordner, in dem die Bilder der einzelnen
     * Buchstaben liegen.
     *
     * @param basePath Der Pfad zu einem Ordner, in dem die Bilder der einzelnen
     *                 Buchstaben liegen.
     * @return Eine Instanz dieser Klasse, damit mehrere Setter mit der
     *         Punktschreibweise verkettet werden können.
     */
    public ImageFont setBasePath(String basePath)
    {
        this.basePath = basePath;
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
     *                            Beispielsweise verwandelt die Zahl {@code 3}
     *                            ein Pixel in {@code 9 Pixel} der Abmessung
     *                            {@code 3x3}.
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
     *         verwandelt die Zahl {@code 3} ein Pixel in {@code 9 Pixel} der
     *         Abmessung {@code 3x3}.
     *
     * @see Game#getPixelMultiplication
     */
    public int getPixelMultiplication()
    {
        return pixelMultiplication;
    }

    /**
     * Setzt die Handhabung der Groß- und Kleinschreibung.
     *
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
     * Setzt, ob bei einem nicht vorhandenen Zeichen eine Fehlermeldung geworfen
     * werden soll oder nicht.
     *
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

    /**
     * Wandelt ein Zeichen in einen Bilder-Dateinamen um.
     *
     * @param glyph Das Zeichen, das in einen Bilder-Dateinamen umgewandelt
     *              werden soll.
     *
     * @return Der Bilderdateiname.
     */
    private String convertGlyphToImageName(char glyph)
    {
        String filename = map.get(glyph);
        if (filename != null)
        {
            return filename;
        }
        return String.valueOf(glyph);
    }

    /**
     * Fügt standardmäßig einige Zuordnungen hinzu. Diese können überschrieben
     * werden.
     */
    private void addDefaultMapping()
    {
        // Namen nach https://en.wikipedia.org/wiki/ASCII
        addMapping('-', "dash").addMapping(',', "comma").addMapping(';',
                "semicolon");
        addMapping(':', "colon");
        addMapping('!', "exclamation"); // mark
        addMapping('?', "question"); // mark
        addMapping('.', "dot");
        addMapping('’', "apostrophe");
        addMapping('"', "quotes");
        addMapping('(', "bracket-round-left");
        addMapping(')', "bracket-round-right");
        addMapping('[', "bracket-square-left");
        addMapping(']', "bracket-square-right");
        addMapping('{', "bracket-curly-left");
        addMapping('}', "bracket-curly-right");
        addMapping('*', "asterisk");
        addMapping('/', "slash");
        addMapping('\\', "backslash");
        addMapping('&', "ampersand");
        addMapping('#', "hash");
        addMapping('%', "percent"); // sign
        addMapping('©', "copyright");
        addMapping('$', "dollar"); // sign
    }

    /**
     * Ordnet einem <b>Zeichen</b> einem <b>Bilder-Dateinamen</b> zu. Nicht alle
     * Zeichen wie zum Beispiel der Schrägstrich oder der Doppelpunkt können als
     * Dateinamen verwendet werden.
     *
     * @param glyph    Das Zeichen, das durch ein Bild dargestellt werden soll.
     * @param filename Der Dateiname des Bilds ohne Dateierweiterung, das ein
     *                 Zeichen darstellt, relativ zu {@link #basePath}.
     *
     * @return Eine Instanz dieser Klasse, damit mehrere Setter mit der
     *         Punktschreibweise verkettet werden können.
     */
    public ImageFont addMapping(char glyph, String filename)
    {
        map.put(glyph, filename);
        return this;
    }

    /**
     * Der Dateipfad zu einer Bilddatei, das ein Zeichen darstellt.
     *
     * @param glyph Das Zeichen, das durch ein Bild dargestellt werden soll.
     *
     * @return Der Dateipfad zu einer Bilddatei, das ein Zeichen darstellt.
     */
    private String getImagePath(char glyph)
    {
        return basePath + "/" + convertGlyphToImageName(glyph) + "."
                + extension;
    }

    /**
     * Lädt ein Bild, das ein Zeichen darstellt.
     *
     * @param glyph   Das Zeichen, das durch ein Bild dargestellt werden soll.
     * @param content Der Textinhalt, der in das Bild geschrieben werden soll.
     *                Dieser Parameter wird für die Fehlermeldung benötigt.
     *
     * @return Ein Bild, das ein Zeichen darstellt.
     *
     * @throws RuntimeException Falls das Zeichen kein entsprechendes Bild hat.
     */
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

    /**
     * Verarbeitet die Zeichenkette, die gesetzt werden soll.
     *
     *
     * @param content   Der Textinhalt, der in das Bild geschrieben werden soll.
     * @param lineWidth Die maximale Anzahl an Zeichen, die eine Zeile aufnehmen
     *                  kann.
     * @param alignment Die Textausrichtung.
     *
     * @return Der ausgerichtete Text, in dem neue Zeilenumbrüche eingefügt
     *         wurden.
     */
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
     *                            Beispielsweise verwandelt die Zahl {@code 3}
     *                            ein Pixel in {@code 9 Pixel} der Abmessung
     *                            {@code 3x3}.
     *
     * @return Ein Bild, in dem alle Zeichen-Einzelbilder zusammengefügt wurden.
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
            image = ImageUtil.multiplyPixel(image, pixelMultiplication);
        }
        return image;
    }

    /**
     * Setzt den gegebenen Textinhalt in ein Bild.
     *
     * @param content Der Textinhalt, der in das Bild geschrieben werden soll.
     *
     * @return Ein Bild, in dem alle Zeichen-Einzelbilder zusammengefügt wurden.
     */
    public BufferedImage render(String content)
    {
        return render(content, getLineWidth(content), alignment, color,
                pixelMultiplication);
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        ToStringFormatter formatter = new ToStringFormatter("ImageFont");
        formatter.add("basePath", basePath);
        if (glyphWidth != 8 || glyphHeight != 8)
        {
            formatter.add("glyphDimension",
                    String.format("%sx%s", glyphWidth, glyphHeight));
        }
        if (!extension.equals("png"))
        {
            formatter.add("extension", extension);
        }
        if (pixelMultiplication > 1)
        {
            formatter.add("pixelMultiplication", pixelMultiplication);
        }
        if (color != null)
        {
            formatter.add("color", color);
        }
        if (caseSensitivity != null)
        {
            formatter.add("caseSensitivity", caseSensitivity);
        }
        if (lineWidth > 0)
        {
            formatter.add("lineWidth", lineWidth);
        }
        formatter.add("alignment", alignment);
        return formatter.format();
    }
}
