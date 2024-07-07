package de.pirckheimer_gymnasium.engine_pi.actor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.physics.FixtureBuilder;
import de.pirckheimer_gymnasium.engine_pi.util.ImageUtil;
import de.pirckheimer_gymnasium.engine_pi.util.TextAlignment;
import de.pirckheimer_gymnasium.engine_pi.util.TextUtil;

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

    private int lineWidth = 80;

    private TextAlignment alignment = TextAlignment.LEFT;

    private String content;

    private int pixelFontWidth = 8;

    private int pixelFontHeight = 8;

    public PixelText(String basePath, String content)
    {
        super(() -> FixtureBuilder.rectangle(10, 10));
        this.basePath = basePath;
        setContent(content);
        setRenderedTextImage();
    }

    public PixelText setContent(String content)
    {
        content = content.toUpperCase();
        this.content = TextUtil.wrap(content, lineWidth, alignment);
        return this;
    }

    private PixelText setRenderedTextImage()
    {
        int imageHeight = pixelFontHeight * getLineCount();
        int imageWidth = pixelFontWidth * getLineWidth();
        BufferedImage image = new BufferedImage(imageWidth, imageHeight,
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        String[] lines = TextUtil.splitLines(content);
        int y = 0;
        int x = 0;
        for (int i = 0; i < lines.length; i++)
        {
            y = i * pixelFontHeight;
            String line = lines[i];
            for (int j = 0; j < line.length(); j++)
            {
                x = j * pixelFontWidth;
                BufferedImage glyph = loadBufferedImage(line.charAt(j));
                if (glyph != null)
                {
                    g.drawImage(glyph, x, y, null);
                }
            }
        }
        renderedTextImage = image;
        return this;
    }

    public int getLineWidth()
    {
        return TextUtil.getLineWidth(content);
    }

    public int getLineCount()
    {
        return TextUtil.getLineCount(content);
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
        if (glyph == ' ')
        {
            return null;
        }
        BufferedImage image = Resources.IMAGES.get(getImagePath(glyph));
        if (image != null)
        {
            image = ImageUtil.addAlphaChannel(image);
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
        g.drawImage(renderedTextImage, 0, 0, imageW, imageH, null);
        g.setTransform(pre);
    }
}
