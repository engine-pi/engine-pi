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
package de.pirckheimer_gymnasium.engine_pi.util;

import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
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
    private static GraphicsConfiguration graphicsConfig;

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
     * Ersetzt eine Farbe in einem Bild.
     *
     * @param bufferedImage Das Originalbild.
     * @param from          Die Quellfarbe. Eine Farbe kodiert als Zeichenkette
     *                      in hexadezimaler Notation.
     * @param to            Eine Zielfarbe. Eine Farbe kodiert als Zeichenkette
     *                      in hexadezimaler Notation.
     *
     * @return Eine neue Version des Originalbildes, bei der eine Quellfarben
     *         durch eine Zielfarbe ersetzt wurde.
     */
    public static BufferedImage replaceColor(final BufferedImage bufferedImage,
            String from, String to)
    {
        return replaceColors(bufferedImage, new String[] { from },
                new String[]
                { to });
    }

    /**
     * Ersetzt eine Farbe in einem Bild.
     *
     * @param bufferedImage Das Originalbild.
     * @param from          Die Quellfarbe.
     * @param to            Eine Zielfarbe.
     *
     * @return Eine neue Version des Originalbildes, bei der eine Quellfarben
     *         durch eine Zielfarbe ersetzt wurde.
     */
    public static BufferedImage replaceColor(final BufferedImage bufferedImage,
            Color from, Color to)
    {
        return replaceColors(bufferedImage, new Color[] { from },
                new Color[]
                { to });
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

    /**
     * Speichert ein Bild in das Dateisystem ab.
     *
     * @author Michael Andonie
     * @author Niklas Keller
     *
     * @param image    Das Bild, das gespeichert werden soll.
     * @param filename Der Dateiname, unter dem das Bild gespeichert werden
     *                 soll.
     */
    public static void write(BufferedImage image, String filename)
    {
        filename = filename.toLowerCase();
        String formatname = null;
        if (filename.endsWith(".png"))
        {
            formatname = "png";
        }
        else if (filename.endsWith(".gif"))
        {
            formatname = "gif";
        }
        else if (filename.endsWith(".jpg"))
        {
            formatname = "jpg";
        }
        else
        {
            Logger.error("IO",
                    "Nicht unterstütztes Format. Nur png, jpg, gif ist unterstützt");
            return;
        }
        try
        {
            ImageIO.write(image, formatname,
                    new File(FileUtil.normalizePath(filename)));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Fehler beim Schreiben des Bildes");
        }
    }

    /**
     * Gets an empty {@link BufferedImage} with the given size.
     *
     * @param width  the width
     * @param height the height
     * @return an empty {@link BufferedImage} with the given size
     */
    public static BufferedImage getCompatibleImage(final int width,
            final int height)
    {
        if (width == 0 || height == 0)
        {
            return null;
        }
        if (graphicsConfig == null)
        {
            final GraphicsEnvironment env = GraphicsEnvironment
                    .getLocalGraphicsEnvironment();
            final GraphicsDevice device = env.getDefaultScreenDevice();
            graphicsConfig = device.getDefaultConfiguration();
        }
        return graphicsConfig.createCompatibleImage(width, height,
                Transparency.TRANSLUCENT);
    }

    /**
     * Optimiert ein Bild für das Rendering, abhängig vom Bildschirm des
     * Anwenders.
     *
     * @param image Das Bild, das optimiert werden soll.
     *
     * @return Das optimierte Bild.
     *
     */
    public static BufferedImage toCompatibleImage(final BufferedImage image)
    {
        if (image == null || image.getWidth() == 0 || image.getHeight() == 0)
        {
            return image;
        }
        final BufferedImage compatibleImg = getCompatibleImage(image.getWidth(),
                image.getHeight());
        if (compatibleImg == null)
        {
            return null;
        }
        compatibleImg.createGraphics().drawImage(image, 0, 0, null);
        return compatibleImg;
    }
}
