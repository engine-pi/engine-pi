package rocks.friedrich.engine_omega.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;

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

    public static void write(BufferedImage image, String pathname)
            throws IOException
    {
        ImageIO.write(image, "png", new File(pathname));
    }

    /**
     * Ersetzt Farben in einem Bild entsprechend einer Map, die Quell- und
     * Zielfarben enthält, und gibt dann das Ergebnis zurück.
     *
     * https://github.com/gurkenlabs/litiengine/blob/e9fda2a5bbd3c294538245bfc013e8b17c27797b/litiengine/src/main/java/de/gurkenlabs/litiengine/util/Imaging.java#L490-L513
     * https://codereview.stackexchange.com/a/146611
     *
     * @param bufferedImage Das OriginalBild
     * @param colorMappings eine Map mit Quellfarben als Schlüssel und
     *                      Zielfarben als Werte
     *
     * @return eine neue Version des Originalbildes, bei der die Quellfarben
     *         durch die Zielfarben ersetzt werden
     */
    public static BufferedImage replaceColors(final BufferedImage bufferedImage,
            Map<Color, Color> colorMappings)
    {
        return replaceColors(bufferedImage,
                colorMappings.keySet().toArray(Color[]::new),
                colorMappings.keySet().toArray(Color[]::new));
    }
}
