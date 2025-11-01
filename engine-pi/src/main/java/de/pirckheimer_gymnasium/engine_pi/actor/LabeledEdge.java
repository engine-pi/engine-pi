package de.pirckheimer_gymnasium.engine_pi.actor;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;
import de.pirckheimer_gymnasium.engine_pi.physics.FixtureBuilder;

/**
 * Eine beschriftete Kante
 *
 * @author Josef Friedrich
 *
 * @since 0.36.0
 */
public class LabeledEdge extends Actor
{
    private Vector point1;

    private Vector point2;

    private String label;

    public LabeledEdge(double x1, double y1, double x2, double y2, String label)
    {
        this(new Vector(x1, y1), new Vector(x2, y2), label);
    }

    public LabeledEdge(double x1, double y1, double x2, double y2)
    {
        this(x1, y1, x2, y2, null);
    }

    public LabeledEdge(Vector point1, Vector point2)
    {
        this(point1, point1, null);
    }

    public LabeledEdge(Vector point1, Vector point2, String label)
    {
        super(() -> FixtureBuilder.line(point1, point2));
        this.point1 = point1;
        this.point2 = point2;
        this.label = label;
        setColor("gray");
    }

    /**
     * @hidden
     */
    @Internal
    @Override
    public void render(Graphics2D g, double pixelPerMeter)
    {
        // AffineTransform at = g.getTransform();
        // g.scale(1, -1);
        Stroke oldStroke = g.getStroke();
        Stroke stroke = new BasicStroke(2, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_MITER);
        g.setStroke(stroke);
        g.setColor(getColor());

        if (label != null)
        {
            Font font = new Font(null, Font.PLAIN, 48);
            AffineTransform affineTransform = new AffineTransform();

            affineTransform.rotate(point1.getRadians(point2), 0, 0);
            Font rotatedFont = font.deriveFont(affineTransform);
            g.setFont(rotatedFont);
            g.drawString(label, 0, 0);
        }
        g.drawLine(point1.getX(pixelPerMeter), point1.getY(pixelPerMeter),
                point2.getX(pixelPerMeter), point2.getY(pixelPerMeter));
        // g.setTransform(at);
        g.setStroke(oldStroke);
    }

    /**
     * @hidden
     */
    public static void main(String[] args)
    {
        Game.start((scene) -> {
            LabeledEdge edge = new LabeledEdge(1, 1, 4, 5, "label");
            scene.add(edge);
        });
    }
}
