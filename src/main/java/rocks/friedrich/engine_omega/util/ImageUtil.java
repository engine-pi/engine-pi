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
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

/**
 * Statische Klasse, die Hilfsmethoden zur Bildmanipulation bereitstellt.
 *
 * https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/util/Imaging.java
 */
public class ImageUtil
{
    /**
     * Erzeugt eine neue {@code BufferedImage} Instanze des Übergabeparameters.
     *
     * https://github.com/gurkenlabs/litiengine/blob/e9fda2a5bbd3c294538245bfc013e8b17c27797b/litiengine/src/main/java/de/gurkenlabs/litiengine/util/Imaging.java#L390-L401
     *
     * @param image Das zu kopierende Bild.
     *
     * @return Ein {@link BufferedImage}, das eine Kopie des Eingabeparameters
     *         ist.
     */
    public static BufferedImage copy(BufferedImage image)
    {
        ColorModel cm = image.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = image
                .copyData(image.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    /**
     * Ersetzt Farben in einem Bild entsprechend zweier Felder (Arrays), die die
     * {@link Color Quell-} und {@link Color Zielfarben} enthalten, und gibt
     * dann das Ergebnis zurück.
     *
     * <ul>
     *
     * <li>Quelle: <a href=
     * "https://github.com/gurkenlabs/litiengine/blob/e9fda2a5bbd3c294538245bfc013e8b17c27797b/litiengine/src/main/java/de/gurkenlabs/litiengine/util/Imaging.java#L490-L513">LITIengine:
     * src/main/java/de/gurkenlabs/litiengine/util/Imaging.java Zeile
     * 490-513</a></li>
     *
     * <li>Quelle: <a href=
     * "https://codereview.stackexchange.com/a/146611">codereview.stackexchange.com</a></li>
     *
     * </ul>
     *
     * @param bufferedImage Das Originalbild.
     * @param to            Ein Feld, das die Quellfarben enthält.
     * @param from          Ein Feld, das die Zeilfarben enthält.
     *
     * @return Eine neue Version des Originalbildes, bei der die Quellfarben
     *         durch die Zielfarben ersetzt werden.
     */
    public static BufferedImage replaceColors(final BufferedImage bufferedImage,
            Color[] from, Color[] to)
    {
        BufferedImage recoloredImage = copy(bufferedImage);
        if (from.length != to.length)
        {
            throw new RuntimeException(
                    "Die beiden Felder aus Farben müssen die gleiche Länge haben");
        }
        for (int i = 0; i < from.length; i++)
        {
            for (int y = 0; y < recoloredImage.getHeight(); y++)
            {
                for (int x = 0; x < recoloredImage.getWidth(); x++)
                {
                    if (recoloredImage.getRGB(x, y) == from[i].getRGB())
                    {
                        recoloredImage.setRGB(x, y, to[i].getRGB());
                    }
                }
            }
        }
        return recoloredImage;
    }

    /**
     * Ersetzt Farben in einem Bild entsprechend zweier Felder (Arrays), die die
     * {@link String Quell-} und {@link String Zielfarben} in hexadezimaler
     * Notation enthalten, und gibt dann das Ergebnis zurück.
     *
     * <ul>
     *
     * <li>Quelle: <a href=
     * "https://github.com/gurkenlabs/litiengine/blob/e9fda2a5bbd3c294538245bfc013e8b17c27797b/litiengine/src/main/java/de/gurkenlabs/litiengine/util/Imaging.java#L490-L513">LITIengine:
     * src/main/java/de/gurkenlabs/litiengine/util/Imaging.java Zeile
     * 490-513</a></li>
     *
     * <li>Quelle: <a href=
     * "https://codereview.stackexchange.com/a/146611">codereview.stackexchange.com</a></li>
     *
     * </ul>
     *
     * @param bufferedImage Das Originalbild.
     * @param to            Ein Feld, das die Quellfarben enthält.
     * @param from          Ein Feld, das die Zeilfarben enthält.
     *
     * @return Eine neue Version des Originalbildes, bei der die Quellfarben
     *         durch die Zielfarben ersetzt werden.
     */
    public static BufferedImage replaceColors(final BufferedImage bufferedImage,
            String[] from, String[] to)
    {
        return replaceColors(bufferedImage, ColorUtil.decode(from),
                ColorUtil.decode(to));
    }

    /**
     * Ersetzt Farben in einem Bild entsprechend einer {@link Map}, die Quell-
     * und Zielfarben enthält, und gibt dann das Ergebnis zurück.
     *
     * <ul>
     *
     * <li>Quelle: <a href=
     * "https://github.com/gurkenlabs/litiengine/blob/e9fda2a5bbd3c294538245bfc013e8b17c27797b/litiengine/src/main/java/de/gurkenlabs/litiengine/util/Imaging.java#L490-L513">LITIengine:
     * src/main/java/de/gurkenlabs/litiengine/util/Imaging.java Zeile
     * 490-513</a></li>
     *
     * <li>Quelle: <a href=
     * "https://codereview.stackexchange.com/a/146611">codereview.stackexchange.com</a></li>
     *
     * </ul>
     *
     * @param bufferedImage Das Originalbild.
     * @param colorMappings Eine {@link Map} mit Quellfarben als Schlüssel und
     *                      Zielfarben als Werte.
     *
     * @return Eine neue Version des Originalbildes, bei der die Quellfarben
     *         durch die Zielfarben ersetzt werden.
     */
    public static BufferedImage replaceColors(final BufferedImage bufferedImage,
            Map<Color, Color> colorMappings)
    {
        int colors = colorMappings.size();
        Color[] from = new Color[colors];
        Color[] to = new Color[colors];
        int index = 0;
        for (Entry<Color, Color> c : colorMappings.entrySet())
        {
            from[index] = c.getKey();
            to[index] = c.getValue();
            index++;
        } ;
        return replaceColors(bufferedImage, from, to);
    }

    /**
     * Vergrößert ein Bild, indem die Pixel vervielfacht werden. Es wird dabei
     * die Interpolationsmethode {@link AffineTransformOp#TYPE_NEAREST_NEIGHBOR}
     * angewendet.
     *
     * Entsprechender Code in der <a href=
     * "https://github.com/gurkenlabs/litiengine/blob/e9fda2a5bbd3c294538245bfc013e8b17c27797b/litiengine/src/main/java/de/gurkenlabs/litiengine/util/Imaging.java#L558-L764">LITIengine</a>.
     *
     * <a href="https://stackoverflow.com/a/4216635">stackoverflow.com</a>
     *
     * @param image  Das Originalbild.
     * @param factor Der Faktor, um den das Bild vergrößert werden soll. Der
     *               Faktor 2 verwandelt beispielsweise ein Pixel in vier Pixel
     *               nämlich {@code 2x2}.
     *
     * @return Das vergrößerte Bild.
     */
    public static BufferedImage scale(BufferedImage image, int factor)
    {
        BufferedImage after = new BufferedImage(image.getWidth() * factor,
                image.getHeight() * factor, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(factor, factor);
        AffineTransformOp scaleOp = new AffineTransformOp(at,
                AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return scaleOp.filter(image, after);
    }

    public static void write(BufferedImage image, String pathname)
            throws IOException
    {
        ImageIO.write(image, "png", new File(pathname));
    }
}
