package de.pirckheimer_gymnasium.engine_pi.actor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.physics.FixtureBuilder;

/**
 * https://stackoverflow.com/questions/4055430/java-code-for-wrapping-text-lines-to-a-max-line-width
 */
public class PixelText extends Actor
{
    /**
     * Pfad zu einem Ordner, in dem die einzelnen Bilder liegen.
     */
    private String basePath;

    private BufferedImage renderedTextImage;

    public PixelText(String basePath, String content)
    {
        super(() -> FixtureBuilder.rectangle(10, 10));
        this.basePath = basePath;
        renderedTextImage = mergeImages();
    }

    private BufferedImage mergeImages()
    {
        BufferedImage c = new BufferedImage(100, 100,
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = c.getGraphics();
        for (int i = 0; i < 3; i++)
        {
            BufferedImage glyph = loadBufferedImage(
                    String.valueOf(i).charAt(0));
            g.drawImage(glyph, i * 8, i * 8, null);
        }
        return c;
    }

    private String convertGlyphToImageName(char glyph)
    {
        switch (glyph)
        {
        case '.':
            return "dot";

        case ',':
            return "comma";

        case '"':
            return "quotes";

        case '©':
            return "copyright";

        default:
            return String.valueOf(glyph);
        }
    }

    private String getImagePath(char glyph)
    {
        return basePath + "/" + convertGlyphToImageName(glyph) + ".png";
    }

    private BufferedImage loadBufferedImage(char glyph)
    {
        BufferedImage image = Resources.IMAGES.get(getImagePath(glyph));
        if (image != null)
        {
            image = convertColorspace(image, BufferedImage.TYPE_INT_ARGB);
            // bufferedImage = ImageUtil.scale(
            // ImageUtil.replaceColor(bufferedImage, Color.BLACK, color),
            // Tetris.SCALE);
            // image = new Image(bufferedImage, Tetris.SCALE *
            // Tetris.BLOCK_SIZE);
            // image.setPosition(x, y);
            // scene.add(image);
        }
        return image;
    }

    final private static BufferedImage convertColorspace(BufferedImage image,
            int newType)
    {
        BufferedImage raw = image;
        image = new BufferedImage(raw.getWidth(), raw.getHeight(), newType);
        ColorConvertOp op = new ColorConvertOp(null);
        op.filter(raw, image);
        return image;
    }

    /**
     * Zeichnet die Figur an der Position {@code (0|0)} mit der Rotation
     * {@code 0}.
     *
     * @param g             Das {@link Graphics2D}-Objekt, in das gezeichnet
     *                      werden soll.
     * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
     */
    @Override
    public void render(Graphics2D g, double pixelPerMeter)
    {
        AffineTransform pre = g.getTransform();
        int imageH = renderedTextImage.getHeight();
        int imageW = renderedTextImage.getWidth();
        // g.scale(width * pixelPerMeter / imageW,
        // height * pixelPerMeter / imageH);
        g.drawImage(renderedTextImage, 0, 0, imageW, imageH, null);
        g.setTransform(pre);
    }
}
