package de.pirckheimer_gymnasium.engine_pi.demos.resources;

import java.awt.Color;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.actor.Polygon;

public class ColorWheelIttenDemo extends Scene
{
    private final int NUMBER_SEGMENTS = 12;

    private final double SEGMENT_ANGLE = 360 / NUMBER_SEGMENTS;

    private final double HALF_SEGMENT_ANGLE = SEGMENT_ANGLE / 2.0;

    private final Color[] COLORS = Resources.colorScheme.getWheelColors();

    private final double OUTER_RADIUS = 7.0;

    private final double INNER_RADIUS = 4.0;

    public ColorWheelIttenDemo()
    {
        for (int i = 0; i < NUMBER_SEGMENTS; i++)
        {
            double angle = (i * SEGMENT_ANGLE * -1) + 90;
            Vector textPosition = getCirclePoint(8, angle);
            createText(i + "", 0.5, textPosition.getX(), textPosition.getY())
                    .setColor("weiß");
            createColorSegment(i, angle);
        }
    }

    /**
     * Berechnet einen Punkt auf der Kreislinie.
     *
     * @param radius Der Radius des Kreises.
     * @param angle  Der Winkel, der die Lage des Punktes angibt (0 = rechts, 90
     *               = oben, 180 = links, 270 = unten).
     *
     * @return Ein Punkt, der auf der Kreislinie liegt.
     *
     */
    private Vector getCirclePoint(double radius, double angle)
    {
        return Vector.ofAngle(angle).multiply(radius);
    }

    /**
     * Ein farbiges Segment in der Form eines Trapezes mit einer der zwölft
     * Farben des Farbkreises von Itten. Zwölf Segmente ergeben einen Kreis.
     *
     * @param index Der Farbindex. 0 = gelb
     * @param angle Der Winkel deutet auf die Mitte des Segments.
     */
    private void createColorSegment(int index, double angle)
    {
        // Erster Winkel
        double start = angle - HALF_SEGMENT_ANGLE;
        // Zweiter Winkel
        double end = angle + HALF_SEGMENT_ANGLE;
        Polygon polygon = new Polygon(getCirclePoint(OUTER_RADIUS, start),
                getCirclePoint(INNER_RADIUS, start),
                getCirclePoint(INNER_RADIUS, end),
                getCirclePoint(OUTER_RADIUS, end));
        polygon.setColor(COLORS[index]);
        add(polygon);
    }

    public static void main(String[] args)
    {
        Game.start(800, 800, new ColorWheelIttenDemo());
    }
}
