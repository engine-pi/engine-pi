package de.pirckheimer_gymnasium.engine_pi.actor;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.util.ImageUtil;
import de.pirckheimer_gymnasium.engine_pi.util.TextAlignment;
import de.pirckheimer_gymnasium.engine_pi.util.TextUtil;

/**
 * @author Josef Friedrich
 */
public class ImageFontText extends Image
{
    private final ImageFont imageFont;

    public ImageFontText(ImageFont imageFont, String content, int lineWidth,
            TextAlignment alignment)
    {
        super(imageFont.render(content, lineWidth, alignment),
                imageFont.getGlyphWidth());
        this.imageFont = imageFont.setLineWidth(lineWidth)
                .setAlignment(alignment);
    }

    public ImageFontText(ImageFont imageFont, String content)
    {
        this(imageFont, content, 80, TextAlignment.LEFT);
    }

    public void setContent(String content)
    {
        setImage(imageFont.render(content));
    }
}
