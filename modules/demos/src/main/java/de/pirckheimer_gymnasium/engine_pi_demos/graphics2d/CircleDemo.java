package de.pirckheimer_gymnasium.engine_pi_demos.graphics2d;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class CircleDemo extends Component
{
    @Override
    public void render(Graphics2D g)
    {
        // Draw filled circle
        g.setColor(Color.RED);
        g.fillOval(50, 50, 100, 100);

        // Draw outlined circle
        g.setColor(Color.BLUE);
        g.setStroke(new BasicStroke(3));
        g.drawOval(200, 50, 100, 100);

        // Draw circle with different size
        g.setColor(Color.GREEN);
        g.fillOval(350, 50, 150, 150);

        // Draw small circle
        g.setColor(Color.ORANGE);
        g.fillOval(100, 250, 50, 50);

        // Draw circle with thick border
        g.setColor(Color.MAGENTA);
        g.setStroke(new BasicStroke(5));
        g.drawOval(200, 250, 80, 80);

        g.setStroke(new BasicStroke(1));
        g.setColor(Color.YELLOW);
        g.fillOval(400, 250, 80, 80);
        g.setColor(Color.BLACK);
        g.drawOval(400, 250, 80, 80);
    }

    public static void main(String[] args)
    {
        new CircleDemo().show();
    }
}
