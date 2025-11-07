package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

import java.awt.Font;
import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.engine_pi.util.FontUtil;

/**
 * Eine einzeilige Textbox.
 */
public class TextBox extends Box
{

    private String content;

    private Font font;

    private int width;

    private int height;

    private int baseline;

    public TextBox(String content, Font font)
    {
        this.content = content;
        this.font = font;
        var bounds = FontUtil.getStringBoundsNg(content, font);
        width = bounds.getWidth();
        height = bounds.getHeight();
        baseline = bounds.getBaseline();
    }

    @Override
    public int width()
    {
        return width;
    }

    @Override
    public int height()
    {
        return height;
    }

    @Override
    public void render(Graphics2D g)
    {
        g.setFont(font);
        g.drawString(content, x(), y() + baseline);
    }
}
