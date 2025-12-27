package demos.graphics2d;

import java.awt.Font;
import java.awt.Graphics2D;

import pi.resources.font.FontUtil;

public class FontStringBounds extends Graphics2DComponent
{
    public void render(Graphics2D g)
    {

        Font font = new Font(null, Font.PLAIN, 48);
        g.setFont(font);

        var bounds = FontUtil.getStringBounds("A String", font);

        int x = 100;
        int y = 100;
        int width = (int) Math.round(bounds.getWidth());
        int height = (int) Math.round(bounds.getHeight());
        int baseline = (int) Math.round(bounds.getBaseline());

        // The left and right edges of the rectangle are at x and x + width. The
        // top and bottom edges are at y and y + height.
        g.drawRect(x, y - baseline, width, height);

        // The baseline of the first character is at position (x, y)
        g.drawString("A String", x, y);
        g.dispose();
    }

    public static void main(String[] args)
    {
        new FontStringBounds().show();
    }
}
