package rocks.friedrich.engine_omega.util;

import java.awt.Color;
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
 */
public class ImageUtil
{
    /**
     * Creates a new {@code BufferedImage} instance from the specified image.
     *
     * https://github.com/gurkenlabs/litiengine/blob/e9fda2a5bbd3c294538245bfc013e8b17c27797b/litiengine/src/main/java/de/gurkenlabs/litiengine/util/Imaging.java#L390-L401
     *
     * @param image The image to be copied.
     * @return A {@link BufferedImage} that is a copy of the input image.
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
     * Ersetzt Farben in einem Bild entsprechend zweier Felder, die die
     * {@link Color Quell-} und {@link Color Zielfarben} enthälten, und gibt
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

    public static void write(BufferedImage image, String pathname)
            throws IOException
    {
        ImageIO.write(image, "png", new File(pathname));
    }
}
