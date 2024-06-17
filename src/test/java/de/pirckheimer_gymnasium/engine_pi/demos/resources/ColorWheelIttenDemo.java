package de.pirckheimer_gymnasium.engine_pi.demos.resources;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.actor.Polygon;

public class ColorWheelIttenDemo extends Scene
{
    public ColorWheelIttenDemo()
    {
        int NUMBER_SEGMENTS = 12;
        Vector[] points = new Vector[NUMBER_SEGMENTS];
        double segmentDegree = 360 / NUMBER_SEGMENTS;
        for (int i = 0; i < NUMBER_SEGMENTS; i++)
        {
            double degree = (i * segmentDegree * -1) + 90;
            points[i] = getCirclePointByDegree(5, degree + (segmentDegree / 2));
            Vector textPosition = getCirclePointByDegree(7, degree);
            createText(i + "", 0.5, textPosition.getX(), textPosition.getY())
                    .setColor("weiß");
        }
        Polygon polygon = new Polygon(points);
        add(polygon);
    }

    private Vector getCirclePointByDegree(double radius, double degree)
    {
        return new Vector(radius * Math.cos(Math.PI * 2 * degree / 360),
                radius * Math.sin(Math.PI * 2 * degree / 360));
    }

    public static void main(String[] args)
    {
        Game.start(800, 800, new ColorWheelIttenDemo());
    }
}
