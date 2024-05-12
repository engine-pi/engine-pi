/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/util/Imaging.java
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
package rocks.friedrich.engine_omega.util;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Statische Klasse, die Hilfsmethoden zur Farbberechnung und -manipulation
 * bereitstellt.
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
     * Encodes the specified color to a hexadecimal string representation. The
     * output format is:
     *
     * <ul>
     * <li>#RRGGBB - For colors without alpha
     * <li>#RRGGBBAA - For colors with alpha
     * </ul>
     * <p>
     * Examples: <br>
     * {@code Color.RED} = "#ff0000"<br>
     * {@code new Color(255, 0, 0, 200)} = "#ff0000c8"
     *
     * @param color The color that is encoded.
     * @return An hexadecimal string representation of the specified color.
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
     * Instanz der Klasse {@link Color}. The accepted format is:
     * <p>
     * <i>Note: This returns null if the format of the provided color string is
     * invalid.</i>
     * </p>
     *
     * <ul>
     * <li>#RRGGBB - For colors without alpha
     * <li>#RRGGBBAA - For colors with alpha
     * </ul>
     * <p>
     * Examples: <br>
     * "#ff0000" = {@code Color.RED}<br>
     * "#ff0000c8" = {@code new Color(255, 0, 0, 200)}
     *
     * @param hex The hexadecimal encodes color string representation.
     * @return The decoded color.
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
                    "Could not parse color string \"{0}\". Invalid string length \"{1}\"!\nAccepted lengths:\n\t{2} for Colors without Alpha (#ff0000)\n\t{3} for Colors with Alpha (#c8ff0000)",
                    new Object[]
                    { hex, hex.length(), HEX_STRING_LENGTH,
                            HEX_STRING_LENGTH_ALPHA });
            return null;
        }
    }

    /**
     * Ensures that the specified value lies within the accepted range for Color
     * values (0-255). Smaller values will be forced to be 0 and larger values
     * will result in 255.
     *
     * @param value The value to check for.
     * @return An integer value that fits the color value restrictions.
     */
    public static int ensureColorValueRange(float value)
    {
        return ensureColorValueRange(Math.round(value));
    }

    /**
     * Ensures that the specified value lies within the accepted range for Color
     * values (0-255). Smaller values will be forced to be 0 and larger values
     * will result in 255.
     *
     * @param value The value to check for.
     * @return An integer value that fits the color value restrictions.
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

    public static Color interpolate(Color color1, Color color2, double factor)
    {
        factor = MathUtil.clamp(factor, 0, 1);
        int r = (int) (color1.getRed() * (1 - factor)
                + color2.getRed() * factor);
        int g = (int) (color1.getGreen() * (1 - factor)
                + color2.getGreen() * factor);
        int b = (int) (color1.getBlue() * (1 - factor)
                + color2.getBlue() * factor);
        int a = (int) (color1.getAlpha() * (1 - factor)
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
}
