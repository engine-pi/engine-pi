package rocks.friedrich.engine_omega.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.awt.image.WritableRaster;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.logging.Level;

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
                        int newColor = to[i].getRGB();
                        recoloredImage.setRGB(x, y, newColor);
                    }
                }
            }
        }
        return recoloredImage;
    }

    /**
     * Ersetzt Farben in einem Bild entsprechend einer Map, die Quell- und
     * Zielfarben enthält, und gibt dann das Ergebnis zurück.
     *
     * @see https://github.com/gurkenlabs/litiengine/blob/e9fda2a5bbd3c294538245bfc013e8b17c27797b/litiengine/src/main/java/de/gurkenlabs/litiengine/util/Imaging.java#L490-L513
     * @see https://codereview.stackexchange.com/a/146611
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
