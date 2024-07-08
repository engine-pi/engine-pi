/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/util/ColorHelper.java
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
package de.pirckheimer_gymnasium.engine_pi.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

class HexColorString
{
    private static final String HEX_WEBCOLOR_PATTERN = "^#[a-fA-F0-9]+$";

    private static final Pattern pattern = Pattern
            .compile(HEX_WEBCOLOR_PATTERN);

    /**
     * Überprüft, ob die gegebene Zeichenketten eine Farbe in hexadezimaler
     * Notation (z. B. {@code #ff0000}) codiert.
     *
     * @param color Eine Zeichenkette, die überprüft werden soll.
     *
     * @return Wahr, falls die Zeichenketten eine Farbe in hexadezimaler
     *         Notation (z. B. {@code #ff0000}) korrekt codiert.
     */
    public static boolean isValid(final String colorCode)
    {
        if (colorCode.length() == 7 || colorCode.length() == 9)
        {
            return pattern.matcher(colorCode).matches();
        }
        return false;
    }
}

/**
 * Statische Klasse, die Hilfsmethoden zur <b>Farb</b>berechnung und
 * -manipulation bereitstellt.
 */
public final class ColorUtil
{
    private static final Logger log = Logger
            .getLogger(ColorUtil.class.getName());

    private static final int HEX_STRING_LENGTH = 7;

    private static final int HEX_STRING_LENGTH_ALPHA = 9;

    private static final int MAX_RGB_VALUE = 255;

    private ColorUtil()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Kodiert die angegebene Farbe in eine hexadezimale Repräsenation. Das
     * Ausgabeformat ist wie folgt:
     *
     * <ul>
     * <li>{@code #RRGGBB} - Für Farben ohne Alpha-Kanal bzw. ohne zusätzliche
     * Transparenzinformationen.
     *
     * <li>{@code #RRGGBBAA} - Für Farben mit Alpha-Kanal bzw. mit zusätzlichen
     * Transparenzinformationen.
     * </ul>
     *
     * <p>
     * Beispiele: <br>
     * {@code Color.RED} = "#ff0000"<br>
     * {@code new Color(255, 0, 0, 200)} = "#ff0000c8"
     *
     * @param color Die Farbe, die kodiert werden soll.
     *
     * @return Eine hexadezimale Repräsenation als Zeichenkette.
     *
     * @see ColorUtil#decode(String)
     * @see Color
     * @see Color#getRGB()
     * @see Integer#toHexString(int)
     */
    public static String encode(Color color)
    {
        if (color == null)
        {
            return null;
        }
        String colorString = "#" + String.format("%02x%02x%02x", color.getRed(),
                color.getGreen(), color.getBlue());
        if (color.getAlpha() < MAX_RGB_VALUE)
        {
            colorString += String.format("%02x", color.getAlpha());
        }
        return colorString;
    }

    /**
     * Konvertiert eine Farbe in hexadezimaler Notation in die entsprechende
     * Instanz der Klasse {@link Color}.
     * <p>
     * <i>Hinweis: Die Methode gibt null zurück, falls die Eingabe nicht korrekt
     * ist.</i>
     * </p>
     *
     * Mögliche Eingabeformate sind:
     *
     * <ul>
     * <li>{@code #RRGGBB} - Für Farben ohne Alpha-Kanal bzw. ohne zusätzliche
     * Transparenzinformationen.
     *
     * <li>{@code #RRGGBBAA} - Für Farben mit Alpha-Kanal bzw. mit zusätzlichen
     * Transparenzinformationen.
     * </ul>
     *
     * <p>
     * Beispiele: <br>
     * "#ff0000" = {@code Color.RED}<br>
     * "#ff0000c8" = {@code new Color(255, 0, 0, 200)}
     *
     * @param hex Die Farbe in einer hexadezimalen Repräsentation.
     *
     * @return Die dekodierte Farbe.
     *
     * @see ColorUtil#encode(Color)
     * @see Color
     * @see Color#decode(String)
     * @see Integer#decode(String)
     */
    public static Color decode(String hex)
    {
        return decode(hex, false);
    }

    /**
     * Konvertiert eine Farbe in hexadezimaler Notation in die entsprechende
     * Instanz der Klasse {@link Color}.
     *
     * @param hex   Ein Farbe in hexadezimaler Notation.
     * @param solid Bedeutet, dass der Alphakanal grundsätzlich ein dunklere
     *              Version der Grundfarbe erzeugt.
     *
     * @return Die Farbe als Instanz der Klasse {@link Color}.
     */
    public static Color decode(String hex, boolean solid)
    {
        if (hex == null || hex.isEmpty())
        {
            return null;
        }
        if (!hex.startsWith("#"))
        {
            if (hex.length() == HEX_STRING_LENGTH - 1
                    || hex.length() == HEX_STRING_LENGTH_ALPHA - 1)
            {
                hex = "#" + hex;
            }
            else
            {
                log.log(Level.SEVERE,
                        "Could not parse color string \"{0}\". A color string needs to start with a \"#\" character.",
                        hex);
                return null;
            }
        }
        switch (hex.length())
        {
        case HEX_STRING_LENGTH:
            return decodeWellformedHexString(hex);

        case HEX_STRING_LENGTH_ALPHA:
            return decodeHexStringWithAlpha(hex, solid);

        default:
            log.log(Level.SEVERE,
                    "Could not parse color string \"{0}\". Invalid string length \"{1}\"!\nAccepted lengths:\n\t{2} for Colors without Alpha (#ff0000)\n\t{3} for Colors with Alpha (#ff0000c8)",
                    new Object[]
                    { hex, hex.length(), HEX_STRING_LENGTH,
                            HEX_STRING_LENGTH_ALPHA });
            return null;
        }
    }

    /**
     * Dekodiert eine Feld / Array bestehend aus hexadezimalen Repräsentationen
     * von Farben in Zeichenkettenform in ein Feld aus Objekten der Klasse
     * {@link Color}.
     *
     * @param hex Eine Feld / Array bestehend aus hexadezimalen Repräsentationen
     *            von Farben in Zeichenkettenform.
     *
     * @return Ein Feld bestehend aus Objekten der Klasse {@link Color}.
     */
    public static Color[] decode(String[] hex)
    {
        return Arrays.stream(hex).map(ColorUtil::decode).toArray(Color[]::new);
    }

    /**
     * Überprüft, ob die gegebene Zeichenketten eine Farbe in hexadezimaler
     * Notation (z. B. {@code #ff0000}) codiert.
     *
     * @param color Eine Zeichenkette, die überprüft werden soll.
     *
     * @return Wahr, falls die Zeichenketten eine Farbe in hexadezimaler
     *         Notation (z. B. {@code #ff0000}) korrekt codiert.
     */
    public static boolean isHexColorString(String color)
    {
        return HexColorString.isValid(color);
    }

    /**
     * Stellt sicher, dass der angegebene Wert innerhalb des akzeptierten
     * Bereichs für Farbwerte (0-255) liegt. Kleinere Werte werden zwangsweise
     * auf 0 gesetzt und größere Werte ergeben 255.
     *
     * @param value Die Zahl, die überprüft werden soll.
     *
     * @return Ein ganzzahliger Wert, der den Farbwertbeschränkungen entspricht.
     */
    public static int ensureColorValueRange(float value)
    {
        return ensureColorValueRange(Math.round(value));
    }

    /**
     * Stellt sicher, dass der angegebene Wert innerhalb des akzeptierten
     * Bereichs für Farbwerte (0-255) liegt. Kleinere Werte werden zwangsweise
     * auf 0 gesetzt und größere Werte ergeben 255.
     *
     * @param value Die Zahl, die überprüft werden soll.
     *
     * @return Ein ganzzahliger Wert, der den Farbwertbeschränkungen entspricht.
     */
    public static int ensureColorValueRange(int value)
    {
        return MathUtil.clamp(value, 0, MAX_RGB_VALUE);
    }

    /**
     * Premultiplies the alpha on the given color.
     *
     * @param color The color to premultiply
     * @return The color given, with alpha replaced with a black background.
     */
    public static Color premultiply(Color color)
    {
        if (color.getAlpha() == 255)
        {
            return color;
        }
        return new Color(premultiply(color.getRed(), color.getAlpha()),
                premultiply(color.getGreen(), color.getAlpha()),
                premultiply(color.getBlue(), color.getAlpha()));
    }

    /**
     * Mischt eine neue Farbe aus zwei gegebenen Farben, wobei das
     * Mischverhältnis angegeben werden kann.
     *
     * @param color1 Die erste Farbe, mit der gemischt werden soll.
     * @param color2 Die zweite Farbe, mit der gemischt werden soll.
     * @param factor Das Mischverhältnis. Ein Wert zwischen 0 und 1. Ist dieser
     *               Wert 0, so wird {@code color1} zurückgeben, ist er 1 dann
     *               {@code color2}.
     *
     * @return Die neue, aus zwei Farben gemischte Farbe.
     */
    public static Color interpolate(Color color1, Color color2, double factor)
    {
        factor = MathUtil.clamp(factor, 0, 1);
        double reverse = (1 - factor);
        int r = (int) (color1.getRed() * reverse + color2.getRed() * factor);
        int g = (int) (color1.getGreen() * reverse
                + color2.getGreen() * factor);
        int b = (int) (color1.getBlue() * reverse + color2.getBlue() * factor);
        int a = (int) (color1.getAlpha() * reverse
                + color2.getAlpha() * factor);
        return new Color(r, g, b, a);
    }

    public static Color getTransparentVariant(Color color, int newAlpha)
    {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(),
                ensureColorValueRange(newAlpha));
    }

    private static Color decodeWellformedHexString(String hex)
    {
        try
        {
            return Color.decode(hex);
        }
        catch (NumberFormatException e)
        {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }

    private static int premultiply(int value, int alpha)
    {
        // account for gamma
        return (int) Math.round(value * Math.pow(alpha / 255.0, 1 / 2.2));
    }

    /**
     * @param hex   Ein Farbe in hexadezimaler Notation.
     * @param solid bedeutet, dass der Alphakanal grundsätzlich ein dunklere
     *              Version der Grundfarbe erzeugt.
     *
     * @return Die Farbe als Instanz der Klasse {@link Color}.
     */
    private static Color decodeHexStringWithAlpha(String hex, boolean solid)
    {
        String alpha = hex.substring(7, 9);
        int alphaValue;
        try
        {
            alphaValue = ensureColorValueRange(Integer.parseInt(alpha, 16));
        }
        catch (NumberFormatException e)
        {
            log.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
        StringBuilder sb = new StringBuilder(hex);
        sb.replace(7, 9, "");
        String baseColorString = sb.toString();
        Color baseColor = decodeWellformedHexString(baseColorString);
        if (baseColor == null)
        {
            return null;
        }
        baseColor = new Color(baseColor.getRGB() & 0xffffff | alphaValue << 24,
                true);
        if (solid)
        {
            return premultiply(baseColor);
        }
        else
        {
            return baseColor;
        }
    }

    /**
     * Gibt eine Farbe mit geändertem <b>Alphakanal</b> zurück.
     *
     * @param color Die Farbe, dess Alphakanal geändert werden soll.
     * @param alpha Der Alphakanal als Ganzzahl von 0 - 255.
     *
     * @return Die Farbe mit geändertem <b>Alphakanal</b>.
     */
    public static Color changeAlpha(Color color, int alpha)
    {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(),
                alpha);
    }

    /**
     * Berechnet die Komplementärfarbe.
     *
     * <p>
     * Der Alphakanal der Ausgangsfarbe wird ignoriert.
     * </p>
     *
     * @param color Die Ausgangsfarbe, von der die Komplementärfarbe berechnet
     *              werden soll.
     *
     * @return Die Komplementärfarbe.
     */
    public static Color getComplementary(Color color)
    {
        return new Color(255 - color.getRed(), 255 - color.getGreen(),
                255 - color.getBlue());
    }

    /**
     * Berechnet die Durchschnittsfarbe eines Bildes.
     *
     * https://stackoverflow.com/questions/28162488/get-average-color-on-bufferedimage-and-bufferedimage-portion-as-fast-as-possible
     *
     * @param image Das Bild von dem die Durchschnittfarbe berechnet werden
     *              soll.
     *
     * @return Die Durchschnittsfarbe.
     */
    public static Color calculateAverage(BufferedImage image)
    {
        int STEP = 2;
        int sampled = 0;
        long sumR = 0, sumG = 0, sumB = 0;
        for (int x = 0; x < image.getWidth(); x++)
        {
            for (int y = 0; y < image.getHeight(); y++)
            {
                if (x % STEP == 0 && y % STEP == 0)
                {
                    Color pixel = new Color(image.getRGB(x, y));
                    sumR += pixel.getRed();
                    sumG += pixel.getGreen();
                    sumB += pixel.getBlue();
                    sampled++;
                }
            }
        }
        return new Color(Math.round(sumR / sampled), Math.round(sumG / sampled),
                Math.round(sumB / sampled));
    }
}
