package de.pirckheimer_gymnasium.engine_pi_demos.graphics2d;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import de.pirckheimer_gymnasium.engine_pi.util.FontUtil;

public class FontStringBounds extends Component
{
    public void render(Graphics2D g)
    {

        Font font = new Font(null, Font.PLAIN, 48);
        g.setFont(font);

        Rectangle2D bounds = FontUtil.getStringBounds("A String", font);

        int x = 100;
        int y = 100;
        int width = (int) Math.round(bounds.getWidth());
        int height = (int) Math.round(bounds.getHeight());
        int baseline = (int) Math.round(bounds.getY());

        // The left and right edges of the rectangle are at x and x + width. The
        // top and bottom edges are at y and y + height.
        g.drawRect(x, y + baseline, width, height);

        // The baseline of the first character is at position (x, y)
        g.drawString("A String", x, y);
        g.dispose();
    }

    public static void main(String[] args)
    {
        new FontStringBounds().show();
    }
}
