package demos.graphics2d;

import java.awt.Color;
import java.awt.Graphics2D;

import pi.Vector;
import pi.graphics.geom.DirectedLineSegment;

public class DirectedLineSegmentDemo extends Graphics2DComponent
{

    @Override
    public void render(Graphics2D g)
    {
        Vector from = new Vector(100, 100);
        Vector to = new Vector(300, 300);
        DirectedLineSegment line = new DirectedLineSegment(from, to);

        g.drawLine(from.x(1), from.y(1), to.x(1), to.y(1));

        Vector half = line.proportionalPoint(0.5);
        g.fillOval(half.x(1), half.y(1), 3, 3);

        g.setColor(Color.RED);

        Vector distance = line.distancePoint(100);
        g.fillOval(distance.x(1), distance.y(1), 3, 3);
    }

    public static void main(String[] args)
    {
        new DirectedLineSegmentDemo().show();
    }
}
