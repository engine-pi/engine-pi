package de.pirckheimer_gymnasium.demos.graphics2d;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class FontRotationDemo extends Component
{
    public void render(Graphics2D g)
    {
        Font font = new Font(null, Font.PLAIN, 24);
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(45), 0, 0);
        Font rotatedFont = font.deriveFont(affineTransform);
        g.setFont(rotatedFont);
        g.drawString("A String", 100, 100);
        g.dispose();
    }

    public static void main(String[] args)
    {
        new FontRotationDemo().show();
    }
}
