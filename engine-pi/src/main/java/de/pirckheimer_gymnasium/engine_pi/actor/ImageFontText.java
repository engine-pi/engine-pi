package de.pirckheimer_gymnasium.engine_pi.actor;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.util.ImageUtil;
import de.pirckheimer_gymnasium.engine_pi.util.TextAlignment;
import de.pirckheimer_gymnasium.engine_pi.util.TextUtil;

/**
 *  @author Josef Friedrich
 */
public class ImageFontText extends Image
{
    /**
     *
     * @param basePath  Pfad zu einem Ordner, in dem die einzelnen Bilder
     *                  liegen.
     * @param content
     * @param lineWidth
     * @param alignment
     */
    public ImageFontText(String basePath, String content, int lineWidth,
            TextAlignment alignment, int pixelFontWidth, int pixelFontHeight)
    {
        super(getRenderedTextImage(basePath,
                processContent(content, lineWidth, alignment), pixelFontWidth,
                pixelFontHeight), 8);
    }

    public ImageFontText(String basePath, String content)
    {
        this(basePath, content, 80, TextAlignment.LEFT, 8, 8);
    }

    private static String processContent(String content, int lineWidth,
            TextAlignment alignment)
    {
        return TextUtil.wrap(content.toUpperCase(), lineWidth, alignment);
    }

    private static String convertGlyphToImageName(char glyph)
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

    private static String getImagePath(String basePath, char glyph)
    {
        return basePath + "/" + convertGlyphToImageName(glyph) + ".png";
    }

    private static BufferedImage loadBufferedImage(String basePath, char glyph)
    {
        if (glyph == ' ')
        {
            return null;
        }
        BufferedImage image = Resources.IMAGES
                .get(getImagePath(basePath, glyph));
        if (image != null)
        {
            image = ImageUtil.addAlphaChannel(image);
        }
        return image;
    }

    private static BufferedImage getRenderedTextImage(String basePath,
            String content, int pixelFontWidth, int pixelFontHeight)
    {
        int lineWidth = TextUtil.getLineWidth(content);
        int lineCount = TextUtil.getLineCount(content);
        int imageHeight = pixelFontHeight * lineCount;
        int imageWidth = pixelFontWidth * lineWidth;
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
                BufferedImage glyph = loadBufferedImage(basePath,
                        line.charAt(j));
                if (glyph != null)
                {
                    g.drawImage(glyph, x, y, null);
                }
            }
        }
        return image;
    }
}
