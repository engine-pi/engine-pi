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

import static pi.actor.ImageFontCaseSensitivity.TO_LOWER;
import static pi.actor.ImageFontCaseSensitivity.TO_UPPER;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import pi.annotations.Getter;
import pi.annotations.Setter;
import pi.debug.ToStringFormatter;
import pi.resources.ResourceLoader;
import pi.util.ImageUtil;
import pi.util.TextAlignment;
import pi.util.TextUtil;

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
 * @see ImageFontText
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

    private final Map<Character, ImageFontGlyph> glyphs = new LinkedHashMap<>();

    private final Map<String, ImageFontGlyph> glyphsByFilename = new LinkedHashMap<>();

    /**
     * Ob bei einem nicht vorhandenen Zeichen eine Fehlermeldung geworfen werden
     * soll oder nicht.
     */
    private boolean throwException = true;

    /**
     * Erzeugt eine neue Bilderschriftart.
     *
     * @param basePath Der Pfad zu einem Ordner, in dem die Bilder der einzelnen
     *     Buchstaben liegen.
     * @param extension Die Dateierweiterung der Buchstabenbilder.
     * @param caseSensitivity Die Handhabung der Groß- und Kleinschreibung.
     * @param alignment Die Textausrichtung.
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
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(
                Objects.requireNonNull(ResourceLoader.getLocation(basePath))
                        .toURI())))
        {
            for (Path path : stream)
            {
                if (!Files.isDirectory(path) && path.toString().toLowerCase()
                        .endsWith("." + this.extension.toLowerCase()))
                {
                    ImageFontGlyph glyph = new ImageFontGlyph(path);
                    if ((glyphWidth > 0 && glyphWidth != glyph.getWidth())
                            || (glyphHeight > 0
                                    && glyphHeight != glyph.height()))
                    {
                        throw new Exception(
                                "Alle Bilder einer Bilderschriftart müssen die gleichen Abmessungen haben!");
                    }
                    glyphWidth = glyph.getWidth();
                    glyphHeight = glyph.height();
                    glyphsByFilename.put(glyph.filename(), glyph);
                    if (glyph.glyph() != 0)
                    {
                        glyphs.put(glyph.glyph(), glyph);
                    }
                }
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        addDefaultMapping();
    }

    /**
     * Erzeugt eine neue Bilderschriftart. Die einzelnen Glyphen müssen als
     * Dateierweiterung {@code png} haben. Der Text wird linksbündig
     * ausgerichtet.
     *
     * @param basePath Der Pfad zu einem Ordner, in dem die Bilder der einzelnen
     *     Buchstaben liegen.
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
     *     Buchstaben liegen.
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
     *     Buchstaben liegen.
     *
     * @return Eine Instanz dieser Klasse, damit mehrere Setter mit der
     *     Punktschreibweise verkettet werden können.
     */
    @Setter
    public ImageFont basePath(String basePath)
    {
        this.basePath = basePath;
        return this;
    }

    /**
     * Gibt die Breite der Buchstabenbilder in Pixel zurück.
     *
     * @return Die Breite der Buchstabenbilder in Pixel.
     */
    @Getter
    public int glyphWidth()
    {
        return glyphWidth;
    }

    /**
     * Setzt die Farbe, in der die schwarze Farbe der Ausgangsbilder umgefärbt
     * werden soll.
     *
     * @param color Die Farbe, in der die schwarze Farbe der Ausgangsbilder
     *     umgefärbt werden soll.
     *
     * @return Eine Instanz dieser Klasse, damit mehrere Setter mit der
     *     Punktschreibweise verkettet werden können.
     */
    @Setter
    public ImageFont color(Color color)
    {
        this.color = color;
        return this;
    }

    /**
     * Gibt die Farbe zurück, in der die schwarze Farbe der Ausgangsbilder
     * umgefärbt werden soll.
     *
     * @return Die Farbe, in der die schwarze Farbe der Ausgangsbilder umgefärbt
     *     werden soll.
     */
    @Getter
    public Color color()
    {
        return color;
    }

    /**
     * Setzt die Dateierweiterung der Buchstabenbilder.
     *
     * @param extension Die Dateierweiterung der Buchstabenbilder.
     *
     * @return Eine Instanz dieser Klasse, damit mehrere Setter mit der
     *     Punktschreibweise verkettet werden können.
     */
    @Setter
    public ImageFont extension(String extension)
    {
        this.extension = extension;
        return this;
    }

    /**
     * Setzt, wie oft ein Pixel vervielfältigt werden soll.
     *
     * @param pixelMultiplication Wie oft ein Pixel vervielfältigt werden soll.
     *     Beispielsweise verwandelt die Zahl {@code 3} ein Pixel in
     *     {@code 9 Pixel} der Abmessung {@code 3x3}.
     *
     * @return Eine Instanz dieser Klasse, damit mehrere Setter mit der
     *     Punktschreibweise verkettet werden können.
     */
    @Setter
    public ImageFont pixelMultiplication(int pixelMultiplication)
    {
        this.pixelMultiplication = pixelMultiplication;
        return this;
    }

    /**
     * Gibt zurück, wie oft ein Pixel vervielfältigt werden soll. i
     *
     * @return Wie oft ein Pixel vervielfältigt werden soll. Beispielsweise
     *     verwandelt die Zahl {@code 3} ein Pixel in {@code 9 Pixel} der
     *     Abmessung {@code 3x3}.
     */
    @Getter
    public int pixelMultiplication()
    {
        return pixelMultiplication;
    }

    /**
     * Setzt die Handhabung der Groß- und Kleinschreibung.
     *
     * @param caseSensitivity Die Handhabung der Groß- und Kleinschreibung.
     *
     * @return Eine Instanz dieser Klasse, damit mehrere Setter mit der
     *     Punktschreibweise verkettet werden können.
     */
    @Setter
    public ImageFont caseSensitivity(ImageFontCaseSensitivity caseSensitivity)
    {
        this.caseSensitivity = caseSensitivity;
        return this;
    }

    /**
     * Setzt die maximale Anzahl an Zeichen, die eine Zeile aufnehmen kann.
     *
     * @param lineWidth Die maximale Anzahl an Zeichen, die eine Zeile aufnehmen
     *     kann.
     *
     * @return Eine Instanz dieser Klasse, damit mehrere Setter mit der
     *     Punktschreibweise verkettet werden können.
     */
    @Setter
    public ImageFont lineWidth(int lineWidth)
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
    @Getter
    public int lineWidth()
    {
        return lineWidth;
    }

    /**
     * Gibt die maximale Anzahl an Zeichen zurück, die eine Zeile aufnehmen
     * kann.
     *
     * @return Die maximale Anzahl an Zeichen, die eine Zeile aufnehmen kann.
     */
    @Getter
    public int lineWidth(String content)
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
    @Getter
    public int lineWidth(String content, int lineWidth)
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
     *     Punktschreibweise verkettet werden können.
     */
    @Setter
    public ImageFont alignment(TextAlignment alignment)
    {
        this.alignment = alignment;
        return this;
    }

    /**
     * Gibt die Textausrichtung zurück.
     *
     * @return Die Textausrichtung.
     */
    @Getter
    public TextAlignment alignment()
    {
        return alignment;
    }

    /**
     * Setzt, ob bei einem nicht vorhandenen Zeichen eine Fehlermeldung geworfen
     * werden soll oder nicht.
     *
     * @param throwException Ob bei einem nicht vorhandenen Zeichen eine
     *     Fehlermeldung geworfen werden soll oder nicht.
     *
     * @return Eine Instanz dieser Klasse, damit mehrere Setter mit der
     *     Punktschreibweise verkettet werden können.
     */
    @Setter
    public ImageFont throwException(boolean throwException)
    {
        this.throwException = throwException;
        return this;
    }

    /**
     * Fügt standardmäßig einige Zuordnungen hinzu. Diese können überschrieben
     * werden.
     */
    private void addDefaultMapping()
    {
        // https://de.wikipedia.org/wiki/Unicodeblock_Basis-Lateinisch
        addMapping(' ', "0020_space") //
                .addMapping('!', "0021_exclamation-mark") //
                .addMapping('"', "0022_quotation-mark") //
                .addMapping('#', "0023_number-sign") //
                .addMapping('$', "0024_dollar-sign") //
                .addMapping('%', "0025_percent-sign") //
                .addMapping('&', "0026_ampersand") //
                .addMapping('\'', "0027_apostrophe") //
                .addMapping('(', "0028_left-parenthesis") //
                .addMapping(')', "0029_right-parenthesis") //
                .addMapping('*', "002a_asterisk") //
                .addMapping('+', "002b_plus-sign") //
                .addMapping(',', "002c_comma") //
                .addMapping('-', "002d_hyphen-minus") //
                .addMapping('.', "002e_full-stop") //
                .addMapping('/', "002f_solidus") //
                .addMapping('0', "0030_digit-zero") //
                .addMapping('1', "0031_digit-one") //
                .addMapping('2', "0032_digit-two") //
                .addMapping('3', "0033_digit-three") //
                .addMapping('4', "0034_digit-four") //
                .addMapping('5', "0035_digit-five") //
                .addMapping('6', "0036_digit-six") //
                .addMapping('7', "0037_digit-seven") //
                .addMapping('8', "0038_digit-eight") //
                .addMapping('9', "0039_digit-nine") //
                .addMapping(':', "003a_colon") //
                .addMapping(';', "003b_semicolon") //
                .addMapping('<', "003c_less-than-sign") //
                .addMapping('=', "003d_equals-sign") //
                .addMapping('>', "003e_greater-than-sign") //
                .addMapping('?', "003f_question-mark") //
                .addMapping('@', "0040_commercial-at") //
                .addMapping('A', "0041_latin-capital-letter-a") //
                .addMapping('B', "0042_latin-capital-letter-b") //
                .addMapping('C', "0043_latin-capital-letter-c") //
                .addMapping('D', "0044_latin-capital-letter-d") //
                .addMapping('E', "0045_latin-capital-letter-e") //
                .addMapping('F', "0046_latin-capital-letter-f") //
                .addMapping('G', "0047_latin-capital-letter-g") //
                .addMapping('H', "0048_latin-capital-letter-h") //
                .addMapping('I', "0049_latin-capital-letter-i") //
                .addMapping('J', "004a_latin-capital-letter-j") //
                .addMapping('K', "004b_latin-capital-letter-k") //
                .addMapping('L', "004c_latin-capital-letter-l") //
                .addMapping('M', "004d_latin-capital-letter-m") //
                .addMapping('N', "004e_latin-capital-letter-n") //
                .addMapping('O', "004f_latin-capital-letter-o") //
                .addMapping('P', "0050_latin-capital-letter-p") //
                .addMapping('Q', "0051_latin-capital-letter-q") //
                .addMapping('R', "0052_latin-capital-letter-r") //
                .addMapping('S', "0053_latin-capital-letter-s") //
                .addMapping('T', "0054_latin-capital-letter-t") //
                .addMapping('U', "0055_latin-capital-letter-u") //
                .addMapping('V', "0056_latin-capital-letter-v") //
                .addMapping('W', "0057_latin-capital-letter-w") //
                .addMapping('X', "0058_latin-capital-letter-x") //
                .addMapping('Y', "0059_latin-capital-letter-y") //
                .addMapping('Z', "005a_latin-capital-letter-z") //
                .addMapping('[', "005b_left-square-bracket") //
                .addMapping('\\', "005c_reverse-solidus") //
                .addMapping(']', "005d_right-square-bracket") //
                .addMapping('^', "005e_circumflex-accent") //
                .addMapping('_', "005f_low-line") //
                .addMapping('`', "0060_grave-accent") //
                .addMapping('a', "0061_latin-small-letter-a") //
                .addMapping('b', "0062_latin-small-letter-b") //
                .addMapping('c', "0063_latin-small-letter-c") //
                .addMapping('d', "0064_latin-small-letter-d") //
                .addMapping('e', "0065_latin-small-letter-e") //
                .addMapping('f', "0066_latin-small-letter-f") //
                .addMapping('g', "0067_latin-small-letter-g") //
                .addMapping('h', "0068_latin-small-letter-h") //
                .addMapping('i', "0069_latin-small-letter-i") //
                .addMapping('j', "006a_latin-small-letter-j") //
                .addMapping('k', "006b_latin-small-letter-k") //
                .addMapping('l', "006c_latin-small-letter-l") //
                .addMapping('m', "006d_latin-small-letter-m") //
                .addMapping('n', "006e_latin-small-letter-n") //
                .addMapping('o', "006f_latin-small-letter-o") //
                .addMapping('p', "0070_latin-small-letter-p") //
                .addMapping('q', "0071_latin-small-letter-q") //
                .addMapping('r', "0072_latin-small-letter-r") //
                .addMapping('s', "0073_latin-small-letter-s") //
                .addMapping('t', "0074_latin-small-letter-t") //
                .addMapping('u', "0075_latin-small-letter-u") //
                .addMapping('v', "0076_latin-small-letter-v") //
                .addMapping('w', "0077_latin-small-letter-w") //
                .addMapping('x', "0078_latin-small-letter-x") //
                .addMapping('y', "0079_latin-small-letter-y") //
                .addMapping('z', "007a_latin-small-letter-z") //
                .addMapping('{', "007b_left-curly-bracket") //
                .addMapping('|', "007c_vertical-line") //
                .addMapping('}', "007d_right-curly-bracket") //
                .addMapping('~', "007e_tilde") //
                .addMapping('©', "00a9_copyright-sign") //
                .addMapping('“', "201c_left-double-quotation-mark") //
                .addMapping('”', "201d_right-double-quotation-mark");
    }

    @Getter
    public ImageFontGlyph[] glyphs()
    {
        return glyphs.values().toArray(new ImageFontGlyph[0]);
    }

    /**
     * Ordnet einem <b>Zeichen</b> einem <b>Bilder-Dateinamen</b> zu. Nicht alle
     * Zeichen wie zum Beispiel der Schrägstrich oder der Doppelpunkt können als
     * Dateinamen verwendet werden.
     *
     * @param glyph Das Zeichen, das durch ein Bild dargestellt werden soll.
     * @param filename Der Dateiname des Bilds ohne Dateierweiterung, das ein
     *     Zeichen darstellt, relativ zu {@link #basePath}.
     *
     * @return Eine Instanz dieser Klasse, damit mehrere Setter mit der
     *     Punktschreibweise verkettet werden können.
     */
    public ImageFont addMapping(char glyph, String filename)
    {
        ImageFontGlyph imageGlyph = glyphsByFilename.get(filename);
        if (imageGlyph != null)
        {
            imageGlyph.glyph(glyph);
            glyphs.put(glyph, imageGlyph);
        }
        return this;
    }

    /**
     * Lädt ein Bild, das ein Zeichen darstellt.
     *
     * @param glyph Das Zeichen, das durch ein Bild dargestellt werden soll.
     * @param content Der Textinhalt, der in das Bild geschrieben werden soll.
     *     Dieser Parameter wird für die Fehlermeldung benötigt.
     *
     * @return Ein Bild, das ein Zeichen darstellt.
     *
     * @throws RuntimeException Falls das Zeichen kein entsprechendes Bild hat.
     */
    @Getter
    private ImageFontGlyph glyph(char glyph, String content)
    {
        if (glyph == ' ')
        {
            return null;
        }
        ImageFontGlyph image = glyphs.get(glyph);
        if (image == null && throwException)
        {
            throw new RuntimeException("Unbekannter Buchstabe „" + glyph
                    + "“ im Text „" + content + "“.");
        }
        return image;
    }

    /**
     * Verarbeitet die Zeichenkette, die gesetzt werden soll.
     *
     * @param content Der Textinhalt, der in das Bild geschrieben werden soll.
     * @param lineWidth Die maximale Anzahl an Zeichen, die eine Zeile aufnehmen
     *     kann.
     * @param alignment Die Textausrichtung.
     *
     * @return Der ausgerichtete Text, in dem neue Zeilenumbrüche eingefügt
     *     wurden.
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
     * @param content Der Textinhalt, der in das Bild geschrieben werden soll.
     * @param lineWidth Die maximale Anzahl an Zeichen, die eine Zeile aufnehmen
     *     kann.
     * @param alignment Die Textausrichtung.
     * @param color Die Farbe, in der die schwarze Farbe der Ausgangsbilder
     *     umgefärbt werden soll.
     * @param pixelMultiplication Wie oft ein Pixel vervielfältigt werden soll.
     *     Beispielsweise verwandelt die Zahl {@code 3} ein Pixel in
     *     {@code 9 Pixel} der Abmessung {@code 3x3}.
     *
     * @return Ein Bild, in dem alle Zeichen-Einzelbilder zusammengefügt wurden.
     */
    public BufferedImage render(String content, int lineWidth,
            TextAlignment alignment, Color color, int pixelMultiplication)
    {
        lineWidth = lineWidth(content, lineWidth);
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
                ImageFontGlyph glyph = glyph(line.charAt(j), content);
                if (glyph != null)
                {
                    g.drawImage(glyph.image(), x, y, null);
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
        return render(content, lineWidth(content), alignment, color,
                pixelMultiplication);
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        ToStringFormatter formatter = new ToStringFormatter("ImageFont");
        formatter.append("basePath", basePath);
        if (glyphWidth != 8 || glyphHeight != 8)
        {
            formatter.append("glyphDimension",
                    String.format("%sx%s", glyphWidth, glyphHeight));
        }
        if (!extension.equals("png"))
        {
            formatter.append("extension", extension);
        }
        if (pixelMultiplication > 1)
        {
            formatter.append("pixelMultiplication", pixelMultiplication);
        }
        if (color != null)
        {
            formatter.append("color", color);
        }
        if (caseSensitivity != null)
        {
            formatter.append("caseSensitivity", caseSensitivity);
        }
        if (lineWidth > 0)
        {
            formatter.append("lineWidth", lineWidth);
        }
        formatter.append("alignment", alignment);
        return formatter.format();
    }
}
