package de.pirckheimer_gymnasium.engine_pi.actor;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;
import de.pirckheimer_gymnasium.engine_pi.physics.FixtureBuilder;

/**
 * Eine <b>Linie</b> zwischen zwei Punkten.
 *
 * @author Josef Friedrich
 *
 * @since 0.36.0
 */
public class Line extends Actor
{
    private Vector point1;

    private Vector point2;

    public Line(double x1, double y1, double x2, double y2)
    {
        this(new Vector(x1, y1), new Vector(x2, y2));
    }

    public Line(Vector point1, Vector point2)
    {
        super(() -> FixtureBuilder.rectangle(300, 300));
        this.point1 = point1;
        this.point2 = point2;
    }

    @Internal
    @Override
    public void render(Graphics2D g, double pixelPerMeter)
    {
        AffineTransform at = g.getTransform();
        g.scale(1, -1);
        Stroke oldStroke = g.getStroke();
        Stroke stroke = new BasicStroke(10, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_MITER);
        g.setStroke(stroke);
        g.setColor(getColor());
        g.drawLine(point1.getX(pixelPerMeter), point1.getY(pixelPerMeter),
                point2.getX(pixelPerMeter), point2.getY(pixelPerMeter));
        g.setTransform(at);
        g.setStroke(oldStroke);
    }

    /**
     * @hidden
     */
    public static void main(String[] args)
    {
        Game.start((scene) -> {
            Line line = new Line(1, 1, 4, 5);
            line.setColor("grün");
            scene.add(line);
        });
    }
}
