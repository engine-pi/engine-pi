package de.pirckheimer_gymnasium.pacman.actors;

import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.actor.ImageFont;
import de.pirckheimer_gymnasium.engine_pi.actor.ImageFontCaseSensitivity;
import de.pirckheimer_gymnasium.engine_pi.actor.ImageFontText;
import de.pirckheimer_gymnasium.engine_pi.util.TextAlignment;

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
