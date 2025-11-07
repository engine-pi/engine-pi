package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

import java.awt.Font;

import de.pirckheimer_gymnasium.engine_pi.util.FontUtil;

/**
 * Eine einzeilige Textbox
 */
public class TextBox extends Box
{

    String content;

    Font font;

    int width;

    int height;

    public TextBox(String content, Font font)
    {
        this.content = content;
        this.font = font;

        var bounds = FontUtil.getStringBoundsNg(content, font);

        width = bounds.getWidth();
        height = bounds.getHeight();
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

}
