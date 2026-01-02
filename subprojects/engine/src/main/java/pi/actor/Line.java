package pi.actor;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;

import pi.Game;
import pi.Vector;
import pi.annotations.Internal;
import pi.physics.FixtureBuilder;

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

    /**
     * Die <b>Dicke</b> der <b>Linie</b> in Meter.
     */
    private double strokeWidth = 0.125;

    public Line(double x1, double y1, double x2, double y2)
    {
        this(new Vector(x1, y1), new Vector(x2, y2));
    }

    public Line(Vector point1, Vector point2)
    {
        super(() -> FixtureBuilder.line(point1, point2));
        this.point1 = point1;
        this.point2 = point2;
    }

    public Line point1(Vector point1)
    {
        this.point1 = point1;
        return this;
    }

    public Line point2(Vector point2)
    {
        this.point2 = point2;
        return this;
    }

    /**
     * Setzt die <b>Dicke</b> der <b>Linie</b> in Meter.
     *
     * @param strokeWidth Die <b>Dicke</b> der <b>Linie</b> in Meter.
     */
    public Line strokeWidth(double strokeWidth)
    {
        this.strokeWidth = strokeWidth;
        return this;
    }

    /**
     * @hidden
     */
    @Internal
    @Override
    public void render(Graphics2D g, double pixelPerMeter)
    {
        AffineTransform at = g.getTransform();
        g.scale(1, -1);
        Stroke oldStroke = g.getStroke();
        Stroke stroke = new BasicStroke((float) (strokeWidth * pixelPerMeter),
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
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
        Game.instantMode(false);
        Game.start((scene) -> {
            Line line = new Line(1, 1, 4, 5);
            line.setColor("grün");
            scene.add(line);
        });
    }
}
