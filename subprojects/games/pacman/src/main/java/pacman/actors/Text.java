package pacman.actors;

import pi.Resources;
import pi.actor.ImageFont;
import pi.actor.ImageFontCaseSensitivity;
import pi.actor.ImageFontText;
import pi.util.TextAlignment;

public class Text extends ImageFontText
{
    private static final ImageFont font = new ImageFont("images/image-font",
            ImageFontCaseSensitivity.TO_UPPER);

    public Text(String content, String color)
    {
        super(font, content, 28, TextAlignment.LEFT,
                Resources.colors.get(color), 1, 8);
    }

    public Text(String content)
    {
        this(content, "red");
    }
}
