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

import static pi.Controller.images;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import pi.Scene;
import pi.annotations.API;
import pi.annotations.ChainableMethod;
import pi.annotations.Getter;
import pi.annotations.Setter;
import pi.debug.ToStringFormatter;
import pi.graphics.boxes.HAlign;
import pi.resources.ResourceLoader;
import pi.util.FileUtil;
import pi.util.ImageUtil;
import pi.util.TextUtil;

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/main-classes/actor/image-text.md

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/image_text/AlignmentDemo.java
// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/image_text/ColorDemo.java
// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/image_text/ColorDemo.java
// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/image_text/ColorDemo.java
// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/image_text/ColorDemo.java
// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/image_text/MultilineDemo.java

/**
 * Zur Darstellung von <b>Texten</b> durch eine <b>Bilderschriftart</b>.
 *
 * @author Josef Friedrich
 *
 * @since 0.23.0
 *
 * @see Font
 */
public class ImageText extends Image
{
    /**
     * Erzeugt einen neuen <b>Text</b>, der durch eine <b>Bilderschriftart</b>
     * dargestellt wird.
     *
     * @param font Die Bilderschriftart.
     */
    public ImageText(Font font)
    {
        // Wir verwenden ein Platzhalter-Bild. Das eigentliche Bild wird in der
        // update()-Methode gesetzt.
        super();
        this.font = font;
        update();
    }

    /* font */

    /**
     * Die <b>Bilderschriftart</b>.
     */
    private @NonNull Font font;

    /**
     * Gibt die <b>Bilderschriftart</b> zurück.
     *
     * @return Die <b>Bilderschriftart</b>
     *
     * @since 0.46.0
     */
    @API
    @Getter
    public @NonNull Font font()
    {
        return font;
    }

    /**
     * Setzt die <b>Bilderschriftart</b>.
     *
     * @param font Die <b>Bilderschriftart</b>
     *
     * @return Eine Referenz auf die eigene Instanz des Bildertextes, damit nach
     *     dem Erbauer/Builder-Entwurfsmuster die Eigenschaften des Bildertextes
     *     durch aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code text.content(..).lineWidth(..)}.
     *
     * @since 0.46.0
     */
    @API
    @Setter
    @ChainableMethod
    public ImageText font(Font font)
    {
        this.font = font;
        update();
        return this;
    }

    /* content */

    /**
     * Der <b>Textinhalt</b>, der in das Bild geschrieben werden soll.
     */
    private @NonNull String content = " ";

    /**
     * Setzt den <b>Textinhalt</b> neu.
     *
     * @param content Der <b>Textinhalt</b>, der in das Bild geschrieben werden
     *     soll.
     *
     * @return Eine Referenz auf die eigene Instanz des Bildertextes, damit nach
     *     dem Erbauer/Builder-Entwurfsmuster die Eigenschaften des Bildertextes
     *     durch aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code text.content(..).lineWidth(..)}.
     */
    @API
    @Setter
    @ChainableMethod
    public ImageText content(Object... content)
    {
        this.content = TextUtil.wrap(TextUtil.convertToMultilineString(content),
            lineWidth,
            hAlign);

        if (this.content.isEmpty())
        {
            // Da wir die Attribute durch verkettete Setter setzen, kann es
            // sein, dass es noch keinen Inhalt gibt. Wir brauchen jedoch
            // einen Inhalt, damit das Bild generiert werden kann.
            this.content = " ";
        }
        update();
        return this;
    }

    /**
     * Gibt den Textinhalt, der in das Bild geschrieben werden soll, zurück.
     *
     * @return Der Textinhalt, der in das Bild geschrieben werden soll.
     *
     * @since 0.25.0
     */
    @API
    @Getter
    public @NonNull String content()
    {
        return content;
    }

    /* lineWidth */

    /**
     * Die <b>Zeilenbreite</b>, also die maximale Anzahl an Zeichen, die eine
     * Zeile aufnehmen kann.
     */
    private int lineWidth = 0;

    /**
     * Gibt die <b>Zeilenbreite</b> zurück, also die maximale Anzahl an Zeichen,
     * die eine Zeile aufnehmen kann.
     *
     * @return Die <b>Zeilenbreite</b>, also die maximale Anzahl an Zeichen, die
     *     eine Zeile aufnehmen kann.
     *
     * @since 0.46.0
     */
    @API
    @Getter
    public int lineWidth()
    {
        if (lineWidth == 0)
        {
            return TextUtil.getLineWidth(content);
        }
        return lineWidth;
    }

    /**
     * Setzt die <b>Zeilenbreite</b>, also die maximale Anzahl an Zeichen, die
     * eine Zeile aufnehmen kann.
     *
     * <p>
     * Wird die Zeilenbreite auf 0 gesetzt, so erhält man einen einzeiligen
     * Text.
     * </p>
     *
     * @param lineWidth Die <b>Zeilenbreite</b>, also die maximale Anzahl an
     *     Zeichen, die eine Zeile aufnehmen kann.
     *
     * @return Eine Referenz auf die eigene Instanz des Bildertextes, damit nach
     *     dem Erbauer/Builder-Entwurfsmuster die Eigenschaften des Bildertextes
     *     durch aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code text.content(..).lineWidth(..)}.
     *
     * @since 0.46.0
     */
    @API
    @Setter
    @ChainableMethod
    public ImageText lineWidth(int lineWidth)
    {
        if (lineWidth < 0)
        {
            throw new IllegalArgumentException(
                    "Die Zeilenbreite eines Bildertextes muss größer gleich 0 sein, nicht: "
                            + lineWidth);
        }
        this.lineWidth = lineWidth;
        content(content);
        update();
        return this;
    }

    /* alignment */

    /**
     * Die <b>Textausrichtung</b>.
     */
    private @NonNull HAlign hAlign = HAlign.LEFT;

    /**
     * Gibt die <b>Textausrichtung</b> zurück.
     *
     * @return Die <b>Textausrichtung</b>.
     *
     * @since 0.46.0
     */
    @API
    @Getter
    public @NonNull HAlign hAlign()
    {
        return hAlign;
    }

    /**
     * Setzt die <b>Textausrichtung</b>.
     *
     * @param hAlign Die <b>Textausrichtung</b>
     *
     * @return Eine Referenz auf die eigene Instanz des Bildertextes, damit nach
     *     dem Erbauer/Builder-Entwurfsmuster die Eigenschaften des Bildertextes
     *     durch aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code text.content(..).lineWidth(..)}.
     *
     * @since 0.46.0
     */
    @API
    @Setter
    @ChainableMethod
    public ImageText hAlign(HAlign hAlign)
    {
        this.hAlign = hAlign;
        content(content);
        update();
        return this;
    }

    /* color */

    /**
     * Setzt die <b>Farbe</b>, in der die schwarze Farbe der Ausgangsbilder
     * umgefärbt werden soll.
     *
     * @param color Die <b>Farbe</b>, in der die schwarze Farbe der
     *     Ausgangsbilder umgefärbt werden soll.
     *
     * @return Eine Referenz auf die eigene Instanz des Bildertextes, damit nach
     *     dem Erbauer/Builder-Entwurfsmuster die Eigenschaften des Bildertextes
     *     durch aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code text.content(..).lineWidth(..)}.
     *
     * @since 0.46.0
     */
    @API
    @Setter
    @ChainableMethod
    @Override
    public ImageText color(Color color)
    {
        // Wir müssen den Setter überladen, damit wir einen update()-Aufruf
        // einbauen können.
        super.color(color);
        update();
        return this;
    }

    /**
     * Setzt die <b>Farbe</b>, in der die schwarze Farbe der Ausgangsbilder
     * umgefärbt werden soll.
     *
     * @param color Die <b>Farbe</b>, in der die schwarze Farbe der
     *     Ausgangsbilder umgefärbt werden soll.
     *
     * @return Eine Referenz auf die eigene Instanz des Bildertextes, damit nach
     *     dem Erbauer/Builder-Entwurfsmuster die Eigenschaften des Bildertextes
     *     durch aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code text.content(..).lineWidth(..)}.
     *
     * @since 0.46.0
     */
    @API
    @Setter
    @ChainableMethod
    @Override
    public ImageText color(String color)
    {
        // Wir müssen den Setter überschreiben, damit wir einen update()-Aufruf
        // einbauen können.
        super.color(color);
        update();
        return this;
    }

    /* pixelMultiplication */

    /**
     * Wie oft ein <b>Pixel vervielfältigt</b> werden soll.
     */
    private int pixelMultiplication = 1;

    /**
     * Gibt zurück, wie oft ein <b>Pixel vervielfältigt</b> werden soll.
     *
     * <p>
     * Beispielsweise verwandelt die Zahl {@code 3} ein Pixel in {@code 9} Pixel
     * der Abmessung {@code 3x3}.
     * </p>
     *
     * @return Wie oft ein Pixel vervielfältigt werden soll.
     *
     * @since 0.25.0
     */
    @API
    @Getter
    public int pixelMultiplication()
    {
        return pixelMultiplication;
    }

    /**
     * Setzt, wie oft ein <b>Pixel vervielfältigt</b> werden soll.
     *
     * <p>
     * Beispielsweise verwandelt die Zahl {@code 3} ein Pixel in {@code 9} Pixel
     * der Abmessung {@code 3x3}.
     * </p>
     *
     * @param pixelMultiplication Wie oft ein Pixel vervielfältigt werden soll.
     *
     * @return Eine Referenz auf die eigene Instanz des Bildertextes, damit nach
     *     dem Erbauer/Builder-Entwurfsmuster die Eigenschaften des Bildertextes
     *     durch aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code text.content(..).lineWidth(..)}.
     *
     * @since 0.25.0
     */
    @API
    @Setter
    @ChainableMethod
    public ImageText pixelMultiplication(int pixelMultiplication)
    {
        this.pixelMultiplication = pixelMultiplication;
        update();
        return this;
    }

    /**
     * @hidden
     */
    @Override
    public void update()
    {
        // Wir verwenden nicht den Setter image() der Actor-Oberklasse Image,
        // sonst kommt es zu rekursiven Aufrufen ohne Abbruchbedingung.
        image = font.createImage(content,
            lineWidth(),
            hAlign(),
            color(),
            pixelMultiplication());
        super.update();
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        ToStringFormatter formatter = toStringFormatter().className(this);
        formatter.prepend("font", font);

        if (pixelMultiplication > 1)
        {
            formatter.append("pixelMultiplication", pixelMultiplication);
        }
        if (color != null)
        {
            formatter.append("color", color);
        }
        if (lineWidth > 0)
        {
            formatter.append("lineWidth", lineWidth);
        }
        formatter.append("alignment", hAlign);
        return formatter.format();
    }

    static class ImageTextFontException extends RuntimeException
    {
        ImageTextFontException(Throwable throwable)
        {
            super(throwable);
        }

        ImageTextFontException(String message)
        {
            super(message);
        }
    }

    /**
     * Eine <b>Schriftart</b>, bei der die einzelnen <b>Buchstaben</b> durch ein
     * <b>Bild</b> repräsentiert sind.
     *
     * <p>
     * Jedes Bild entspricht einem Buchstaben oder Zeichen. Die Bilder müssen
     * alle die gleiche Abmessung aufweisen.
     * </p>
     * <p>
     * Eine Alternative wäre die <a href=
     * "https://javadoc.io/doc/com.badlogicgames.gdx/gdx/1.4.0/com/badlogic/gdx/graphics/g2d/BitmapFont.html">BitmapFont-Klasse</a>
     * der Game-Engine libgdx.
     * <a href="https://libgdx.com/wiki/graphics/2d/fonts/bitmap-fonts">...</a>
     *
     * @author Josef Friedrich
     *
     * @see ImageText
     *
     * @since 0.23.0
     */
    public static class Font
    {
        /**
         * Erzeugt eine neue Bilderschriftart.
         *
         * @param basePath Der Pfad zu einem Ordner, in dem die Bilder der
         *     einzelnen Buchstaben liegen.
         * @param extension Die Dateierweiterung der Buchstabenbilder.
         */
        public Font(@NonNull String basePath, @NonNull String extension)
        {
            this.basePath = basePath;
            this.extension = extension;

            glyphWidth = 0;
            glyphHeight = 0;
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths
                .get(Objects.requireNonNull(ResourceLoader.location(basePath))
                    .toURI())))
            {
                for (Path path : stream)
                {
                    if (!Files.isDirectory(path) && path.toString()
                        .toLowerCase()
                        .endsWith("." + this.extension.toLowerCase()))
                    {
                        Glyph glyph = new Glyph(path);
                        if ((glyphWidth > 0 && glyphWidth != glyph.width())
                                || (glyphHeight > 0
                                        && glyphHeight != glyph.height()))
                        {
                            throw new ImageTextFontException(
                                    "Alle Bilder einer Bilderschriftart müssen die gleichen Abmessungen haben!");
                        }
                        glyphWidth = glyph.width();
                        glyphHeight = glyph.height();
                        glyphsByFilename.put(glyph.filename(), glyph);
                        if (glyph.character() != 0)
                        {
                            glyphs.put(glyph.character(), glyph);
                        }
                    }
                }
            }
            catch (IOException | URISyntaxException e)
            {
                throw new ImageTextFontException(e);
            }
            addDefaultMapping();
        }

        /**
         * Erzeugt eine neue Bilderschriftart. Die einzelnen Glyphen müssen als
         * Dateierweiterung {@code png} haben. Der Text wird linksbündig
         * ausgerichtet.
         *
         * @param basePath Der <b>Pfad</b> zu einem Ordner, in dem die Bilder
         *     der einzelnen Buchstaben liegen.
         */
        public Font(@NonNull String basePath)
        {
            this(basePath, "png");
        }

        /* basePath */

        /**
         * Der <b>Pfad</b> zu einem Ordner, in dem die Bilder der einzelnen
         * Buchstaben liegen.
         */
        private @NonNull String basePath;

        /**
         * Setzt den <b>Pfad</b> zu einem Ordner, in dem die Bilder der
         * einzelnen Buchstaben liegen.
         *
         * @param basePath Der <b>Pfad</b> zu einem Ordner, in dem die Bilder
         *     der einzelnen Buchstaben liegen.
         *
         * @return Eine Instanz dieser Klasse, damit mehrere Setter mit der
         *     Punktschreibweise verkettet werden können.
         */
        @API
        @Setter
        @ChainableMethod
        public Font basePath(@NonNull String basePath)
        {
            this.basePath = basePath;
            return this;
        }

        /* glyphWidth */

        /**
         * Die <b>Breite</b> der Buchstabenbilder in Pixel.
         */
        private int glyphWidth;

        /**
         * Gibt die <b>Breite</b> der Buchstabenbilder in Pixel zurück.
         *
         * @return Die <b>Breite</b> der Buchstabenbilder in Pixel.
         */
        @API
        @Getter
        public int glyphWidth()
        {
            return glyphWidth;
        }

        /**
         * Die <b>Höhe</b> der Buchstabenbilder in Pixel.
         */
        private int glyphHeight;

        /**
         * Gibt die <b>Höhe</b> der Buchstabenbilder in Pixel zurück.
         *
         * @return Die <b>Höhe</b> der Buchstabenbilder in Pixel.
         *
         * @since 0.46.0
         */
        @API
        @Getter
        public int glyphHeight()
        {
            return glyphHeight;
        }

        /* extension */

        /**
         * Die <b>Dateierweiterung</b> der Buchstabenbilder.
         */
        private @NonNull String extension;

        /**
         * Setzt die <b>Dateierweiterung</b> der Buchstabenbilder.
         *
         * @param extension Die <b>Dateierweiterung</b> der Buchstabenbilder.
         *
         * @return Eine Instanz dieser Klasse, damit mehrere Setter mit der
         *     Punktschreibweise verkettet werden können.
         */
        @API
        @Setter
        @ChainableMethod
        public Font extension(@NonNull String extension)
        {
            this.extension = extension;
            return this;
        }

        /* supportsCase */

        /**
         * Die <b>Handhabung der Groß- und Kleinschreibung</b>.
         */
        private @Nullable CaseSensitivity supportsCase = null;

        /**
         * Gibt die <b>Handhabung der Groß- und Kleinschreibung</b> zurück.
         *
         * <p>
         * Diese Methode liefert zurück, ob die Bilderschriftart nur
         * Großschreibung oder nur Kleinschreibung unterstützt. Ist das Attribut
         * auf {@code null} gesetzt, so unterstützt die Schriftart sowohl Klein-
         * als auch Großschreibung.
         * </p>
         *
         * @return Die <b>Handhabung der Groß- und Kleinschreibung</b>.
         *
         * @since 0.46.0
         */
        @API
        @Getter
        public @Nullable CaseSensitivity supportsCase()
        {
            return supportsCase;
        }

        /**
         * Setzt die <b>Handhabung der Groß- und Kleinschreibung</b>.
         *
         * <p>
         * Diese Methode legt fest, ob die Bilderschriftart nur Großschreibung
         * oder nur Kleinschreibung unterstützt. Ist das Attribut auf
         * {@code null} gesetzt, so unterstützt die Schriftart sowohl Klein- als
         * auch Großschreibung.
         * </p>
         *
         * @param supportsCase Die <b>Handhabung der Groß- und
         *     Kleinschreibung</b>.
         *
         * @since 0.46.0
         */
        @API
        @Setter
        @ChainableMethod
        public Font supportsCase(@Nullable CaseSensitivity supportsCase)
        {
            this.supportsCase = supportsCase;
            return this;
        }

        /* throwException */

        /**
         * Ob bei einem nicht vorhandenen Zeichen eine <b>Fehlermeldung
         * geworfen</b> werden soll oder nicht.
         */
        private boolean throwException = true;

        /**
         * Setzt, ob bei einem nicht vorhandenen Zeichen eine <b>Fehlermeldung
         * geworfen</b> werden soll oder nicht.
         *
         * @param throwException Ob bei einem nicht vorhandenen Zeichen eine
         *     <b>Fehlermeldung geworfen</b> werden soll oder nicht.
         *
         * @return Eine Instanz dieser Klasse, damit mehrere Setter mit der
         *     Punktschreibweise verkettet werden können.
         */
        @API
        @Setter
        @ChainableMethod
        public Font throwException(boolean throwException)
        {
            this.throwException = throwException;
            return this;
        }

        /**
         * Fügt standardmäßig einige Zuordnungen hinzu. Diese können
         * überschrieben werden.
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

        private final Map<Character, Glyph> glyphs = new LinkedHashMap<>();

        private final Map<String, Glyph> glyphsByFilename = new LinkedHashMap<>();

        /**
         * Gibt die <b>Zeichen</b> der Bilderschriftart als Feld/Array zurück.
         *
         * @return Die <b>Zeichen</b> der Bilderschriftart als Feld/Array.
         */
        @API
        @Getter
        public Glyph[] glyphs()
        {
            return glyphs.values().toArray(new Glyph[0]);
        }

        /**
         * Ordnet einem <b>Zeichen</b> einem <b>Bilder-Dateinamen</b> zu.
         *
         * <p>
         * Nicht alle Zeichen wie zum Beispiel der Schrägstrich oder der
         * Doppelpunkt können als Dateinamen verwendet werden.
         * </p>
         *
         * @param glyph Das <b>Zeichen</b>, das durch ein Bild dargestellt
         *     werden soll.
         * @param filename Der <b>Dateiname</b> des Bilds ohne Dateierweiterung,
         *     das ein Zeichen darstellt, relativ zu {@link #basePath}.
         *
         * @return Eine Instanz dieser Klasse, damit mehrere Setter mit der
         *     Punktschreibweise verkettet werden können.
         */
        @API
        @ChainableMethod
        public Font addMapping(char glyph, String filename)
        {
            Glyph imageGlyph = glyphsByFilename.get(filename);
            if (imageGlyph != null)
            {
                imageGlyph.character(glyph);
                glyphs.put(glyph, imageGlyph);
            }
            return this;
        }

        /**
         * Lädt ein Bild, das ein Zeichen darstellt.
         *
         * @param glyph Das Zeichen, das durch ein Bild dargestellt werden soll.
         * @param content Der Textinhalt, der in das Bild geschrieben werden
         *     soll. Dieser Parameter wird für die Fehlermeldung benötigt.
         *
         * @return Ein Bild, das ein Zeichen darstellt.
         *
         * @throws RuntimeException Falls das Zeichen kein entsprechendes Bild
         *     hat.
         */
        @Getter
        private Glyph glyph(char glyph, String content)
        {
            if (glyph == ' ')
            {
                return null;
            }
            Glyph image = glyphs.get(glyph);
            if (image == null && throwException)
            {
                throw new ImageTextGlpyhException("Unbekannter Buchstabe „"
                        + glyph + "“ im Text „" + content + "“.");
            }
            return image;
        }

        /**
         * Setzt den gegebenen Textinhalt in ein {@link BufferedImage Bild}.
         *
         * @param content Der Textinhalt, der in das Bild geschrieben werden
         *     soll.
         * @param lineWidth Die maximale Anzahl an Zeichen, die eine Zeile
         *     aufnehmen kann.
         * @param alignment Die Textausrichtung.
         * @param color Die Farbe, in der die schwarze Farbe der Ausgangsbilder
         *     umgefärbt werden soll.
         * @param pixelMultiplication Wie oft ein Pixel vervielfältigt werden
         *     soll. Beispielsweise verwandelt die Zahl {@code 3} ein Pixel in
         *     {@code 9 Pixel} der Abmessung {@code 3x3}.
         *
         * @return Ein Bild, in dem alle Zeichen-Einzelbilder zusammengefügt
         *     wurden.
         *
         * @hidden
         */
        BufferedImage createImage(String content, int lineWidth,
                HAlign alignment, Color color, int pixelMultiplication)
        {
            if (supportsCase == CaseSensitivity.UPPER)
            {
                content = content.toUpperCase();
            }
            else if (supportsCase == CaseSensitivity.LOWER)
            {
                content = content.toLowerCase();
            }
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
                    Glyph glyph = glyph(line.charAt(j), content);
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
         * @hidden
         */
        @Override
        public String toString()
        {
            ToStringFormatter formatter = new ToStringFormatter(this);
            formatter.append("basePath", basePath);

            if (!glyphs.isEmpty())
            {
                formatter.append("glyphs", glyphs);
            }

            if (glyphWidth != 8 || glyphHeight != 8)
            {
                formatter.append("glyphDimension",
                    String.format("%sx%s", glyphWidth, glyphHeight));
            }

            if (!extension.equals("png"))
            {
                formatter.append("extension", extension);
            }

            if (supportsCase != null)
            {
                formatter.append("supportsCase", supportsCase);
            }

            return formatter.format();
        }
    }

    /**
     * Beschreibt, wie mit der <b>Groß- und Kleinschreibung</b> umgegangen
     * werden soll.
     *
     * @author Josef Friedrich
     *
     * @since 0.23.0
     */
    public enum CaseSensitivity
    {
        /**
         * Alle Klein- werden zu <b>Großbuchstaben</b> konvertiert.
         */
        UPPER,

        /**
         * Alle Groß- werden zu <b>Kleinbuchstaben</b> konvertiert.
         */
        LOWER
    }

    static class ImageTextGlpyhException extends RuntimeException
    {
        ImageTextGlpyhException(Throwable throwable)
        {
            super(throwable);
        }

        ImageTextGlpyhException(String message)
        {
            super(message);
        }
    }

    /**
     * Stellt ein <b>Zeichen</b> dar, das durch ein <b>Bild</b> repräsentiert
     * ist.
     *
     * @author Josef Friedrich
     *
     * @since 0.27.0
     *
     * @see Font
     */
    public static class Glyph
    {
        /**
         * Das in den Speicher geladene <b>Bild</b>, das ein Zeichen darstellt.
         */
        BufferedImage image;

        /**
         * Der <b>Dateiname</b> des Bilds ohne Dateierweiterung.
         */
        String filename;

        public Glyph(Path path)
        {
            filename = FileUtil.getFileName(path);
            try
            {
                image = images.get(path.toUri().toURL());
                image = ImageUtil.addAlphaChannel(image);
            }
            catch (MalformedURLException e)
            {
                throw new ImageTextGlpyhException(e);
            }
            if (filename.length() == 1)
            {
                character = filename.charAt(0);
            }
        }

        /**
         * Das <b>Zeichen</b>, das durch ein Bild dargestellt werden soll.
         */
        char character;

        /**
         * Gibt das <b>Zeichen</b>, das durch ein Bild dargestellt werden soll,
         * zurück.
         *
         * @return Das Zeichen, das durch ein Bild dargestellt werden soll.
         */
        @Getter
        public char character()
        {
            return character;
        }

        /**
         * Gibt das <b>Zeichen</b>, das durch ein Bild dargestellt werden soll,
         * als Zeichenkette zurück.
         *
         * @return Das <b>Zeichen</b>, das durch ein Bild dargestellt werden
         *     soll, als Zeichenkette.
         */
        @API
        @Getter
        public String content()
        {
            return String.valueOf(character);
        }

        /**
         * Setzt das <b>Zeichen</b>, das durch ein Bild dargestellt werden soll.
         *
         * @param character Das <b>Zeichen</b>, das durch ein Bild dargestellt
         *     werden soll.
         */
        @API
        @Setter
        public void character(char character)
        {
            this.character = character;
        }

        /**
         * Gibt das in den Speicher geladene <b>Bild</b>, das ein Zeichen
         * darstellt, zurück.
         *
         * @return Das in den Speicher geladene <b>Bild</b>, das ein Zeichen
         *     darstellt.
         */
        @API
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
        @API
        @Getter
        public int width()
        {
            return image.getWidth();
        }

        /**
         * Gibt die <b>Höhe</b> des Bilds in Pixel zurück.
         *
         * @return Die <b>Höhe</b> des Bilds in Pixel.
         */
        @API
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
        @API
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
        @API
        @Getter
        public String unicodeName()
        {
            return filename.substring(5).replace("-", " ").toUpperCase();
        }

        /**
         * Gibt die <b>vierstellige, hexadezimale Unicode-Nummer</b> des
         * Zeichens (beispielsweise {@code 0041}) zurück.
         *
         * @return Die vierstellige, hexadezimale Unicode-Nummer.
         */
        @API
        @Getter
        public String hexNumber()
        {
            return filename.substring(0, 4).toUpperCase();
        }

        @Override
        public String toString()
        {
            ToStringFormatter formatter = new ToStringFormatter(this);
            formatter.append("character", character);
            formatter.append("filename", filename);
            return formatter.format();
        }
    }

    /**
     * Zeichnet in eine Szene ein <b>Schriftmuster</b> ein.
     *
     * <h2>tetris</h2>
     *
     * <p>
     * <img alt="SpecimenTetris" src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/docs/main-classes/actor/image-text/SpecimenTetris.png">
     * </p>
     *
     * <h2>pacman</h2>
     *
     * <p>
     * <img alt="SpecimenPacman" src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/docs/main-classes/actor/image-text/SpecimenPacman.png">
     * </p>
     *
     * <h2>space-invaders</h2>
     *
     * <p>
     * <img alt="SpecimenSpaceInvaders" src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/docs/main-classes/actor/image-text/SpecimenSpaceInvaders.png">
     * </p>
     *
     * @author Josef Friedrich
     *
     * @since 0.27.0
     */
    public static class Specimen
    {
        /**
         * @param scene Die Szene, in der das Schriftmuster eingezeichnet werden
         *     soll.
         * @param font Die Bilderschrift, von der alle Zeichen dargestellt
         *     werden sollen.
         * @param glyphsPerRow Wie viele Zeichen in einer Zeile dargestellt
         *     werden sollen.
         * @param x Die x-Koordinate des linken, oberen Zeichens.
         * @param y Die y-Koordinate des linken, oberen Zeichens.
         */
        public Specimen(Scene scene, Font font, int glyphsPerRow, double x,
                double y)
        {
            double startX = x;
            int i = 0;
            for (Glyph glyph : font.glyphs())
            {
                ImageText text = new ImageText(font).content(glyph.character());
                text.anchor(x, y);
                scene.add(text);
                scene.add(
                    new Text(glyph.content()).anchor(x + 2, y).color("gray"));
                scene.add(new Text(glyph.unicodeName()).font("Monospaced")
                    .height(0.3)
                    .anchor(x, y - 0.4)
                    .color("gray"));
                scene.add(new Text(glyph.hexNumber()).font("Monospaced")
                    .height(0.3)
                    .anchor(x, y - 0.8)
                    .color("gray"));
                x += 4;
                i++;
                if (i % glyphsPerRow == 0)
                {
                    x = startX;
                    y -= 2;
                }
            }
        }

        /**
         * @param scene Die Szene, in der das Schriftmuster eingezeichnet werden
         *     soll.
         * @param font Die Bilderschrift, von der alle Zeichen dargestellt
         *     werden sollen.
         */
        public Specimen(Scene scene, Font font)
        {
            this(scene, font, 5, -10, 8);
        }
    }
}
