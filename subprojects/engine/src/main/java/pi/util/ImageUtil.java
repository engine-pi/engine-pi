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
package pi.util;

import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import pi.resources.color.ColorUtil;

/**
 * Statische Klasse, die Hilfsmethoden zur Bildmanipulation bereitstellt.
 *
 * <a href=
 * "https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/util/Imaging.java">LITIENGINE</a>
 *
 * @author Steffen Wilke
 * @author Matthias Wilke
 */
public class ImageUtil
{
    private static GraphicsConfiguration graphicsConfig;

    private ImageUtil()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Erzeugt eine neue {@code BufferedImage} Instanz des Übergabeparameters.
     *
     * <a href=
     * "https://github.com/gurkenlabs/litiengine/blob/e9fda2a5bbd3c294538245bfc013e8b17c27797b/litiengine/src/main/java/de/gurkenlabs/litiengine/util/Imaging.java#L390-L401">LITIENGINE</a>
     *
     * @param image Das zu kopierende Bild.
     *
     * @return Ein {@link BufferedImage}, das eine Kopie des Eingabeparameters
     *     ist.
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
     * Ersetzt die Farben in einem Bild entsprechend zweier Felder (Arrays), die
     * die {@link Color Quell-} und {@link Color Zielfarben} enthalten, und gibt
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
     * @param from Ein Feld, das die <b>Quellfarben</b> enthält.
     * @param to Ein Feld, das die <b>Zielfarben</b> enthält.
     *
     * @return Eine neue Version des Originalbildes, bei der die Quellfarben
     *     durch die Zielfarben ersetzt werden.
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
     * Ersetzt die Farben in einem Bild entsprechend zweier Felder (Arrays), die
     * die {@link String Quell-} und {@link String Zielfarben} in hexadezimaler
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
     * @param from Ein Feld, das die <b>Quellfarben</b> enthält.
     * @param to Ein Feld, das die <b>Zielfarben</b> enthält.
     *
     * @return Eine neue Version des Originalbildes, bei der die Quellfarben
     *     durch die Zielfarben ersetzt werden.
     */
    public static BufferedImage replaceColors(final BufferedImage bufferedImage,
            String[] from, String[] to)
    {
        return replaceColors(bufferedImage, ColorUtil.decode(from),
                ColorUtil.decode(to));
    }

    /**
     * Ersetzt die Farben in einem Bild entsprechend einer {@link Map}, die
     * Quell- und Zielfarben enthält, und gibt dann das Ergebnis zurück.
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
     *     Zielfarben als Werte.
     *
     * @return Eine neue Version des Originalbildes, bei der die Quellfarben
     *     durch die Zielfarben ersetzt werden.
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
        }
        return replaceColors(bufferedImage, from, to);
    }

    /**
     * Ersetzt eine Farbe in einem Bild.
     *
     * @param bufferedImage Das Originalbild.
     * @param from Die <b>Quellfarbe</b>. Eine Farbe kodiert als Zeichenkette in
     *     <b>hexadezimaler</b> Notation.
     * @param to Eine <b>Zielfarbe</b>. Eine Farbe kodiert als Zeichenkette in
     *     <b>hexadezimaler</b> Notation.
     *
     * @return Eine neue Version des Originalbildes, bei der eine Quellfarben
     *     durch eine Zielfarbe ersetzt wurde.
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
     * @param from Die <b>Quellfarbe</b>.
     * @param to Eine <b>Zielfarbe</b>.
     *
     * @return Eine neue Version des Originalbildes, bei der eine Quellfarben
     *     durch eine Zielfarbe ersetzt wurde.
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
     * <ul>
     * <li>Entsprechender Code in der <a href=
     * "https://github.com/gurkenlabs/litiengine/blob/e9fda2a5bbd3c294538245bfc013e8b17c27797b/litiengine/src/main/java/de/gurkenlabs/litiengine/util/Imaging.java#L558-L764">LITIengine</a>.
     * </li>
     * <li><a href=
     * "https://stackoverflow.com/a/4216635">stackoverflow.com</a></li>
     * </ul>
     *
     * @param image Das Originalbild.
     * @param factor Der Faktor, um den das Bild vergrößert werden soll. Der
     *     Faktor {@code 2} verwandelt beispielsweise ein Pixel in {@code 4}
     *     Pixel nämlich {@code 2x2}.
     *
     * @return Das vergrößerte Bild.
     */
    public static BufferedImage multiplyPixel(BufferedImage image, int factor)
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
     * <p>
     * Das Dateiformat wird anhand der Dateierweiterung erkannt. Folgende
     * Formate werden unterstützt: png, jpg, gif
     * </p>
     *
     * @param image Das Bild, das gespeichert werden soll.
     * @param filePath der Pfad zur Zieldatei (unterstützte Formate: png, jpg,
     *     gif)
     *
     * @throws RuntimeException wenn das Dateiformat nicht unterstützt wird oder
     *     ein Fehler beim Schreiben auftritt
     */
    public static void write(BufferedImage image, String filePath)
    {
        String extension = FileUtil.getExtension(filePath);
        String formatName;
        switch (extension)
        {
        case "png":
            formatName = "png";
            break;

        case "gif":
            formatName = "gif";
            break;

        case "jpg":
            formatName = "jpg";
            break;

        default:
            throw new RuntimeException(
                    "Nicht unterstütztes Format. Nur png, jpg, gif ist unterstützt");
        }
        try
        {
            ImageIO.write(image, formatName,
                    new File(FileUtil.normalizePath(filePath)));
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
     * @param width the width
     * @param height the height
     *
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

    /**
     * Ändert den Farbraum eines Bildes.
     *
     * @param image Das Bild, dessen Farbraum geändert werden soll.
     * @param newType Der neue Bildtyp, zum Beispiel
     *     {@link BufferedImage#TYPE_INT_ARGB}.
     *
     * @return Ein neues Bild mit geändertem Farbraum.
     */
    public static BufferedImage convertColorspace(BufferedImage image,
            int newType)
    {
        BufferedImage raw = image;
        image = new BufferedImage(raw.getWidth(), raw.getHeight(), newType);
        ColorConvertOp op = new ColorConvertOp(null);
        op.filter(raw, image);
        return image;
    }

    /**
     * Ändert den Farbraum eines Bildes in den <b>RGB-Farbraum mit
     * Alphakanal</b> ({@link BufferedImage#TYPE_INT_ARGB}).
     *
     * @param image Das Bild, dessen Farbraum geändert werden soll.
     *
     * @return Ein neues Bild mit geändertem Farbraum.
     */
    public static BufferedImage addAlphaChannel(BufferedImage image)
    {
        return convertColorspace(image, BufferedImage.TYPE_INT_ARGB);
    }
}
