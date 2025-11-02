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
    private Vector from;

    private Vector to;

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
        this.from = point1;
        this.to = point2;
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
        int fromX = from.getX(pixelPerMeter);
        int fromY = from.getY(pixelPerMeter) * -1;
        // In Pixel mit umgedrehter y-Richtung
        Vector fromPx = new Vector(fromX, fromY);

        int toX = to.getX(pixelPerMeter);
        int toY = to.getY(pixelPerMeter) * -1;
        // In Pixel mit umgedrehter y-Richtung
        Vector toPx = new Vector(toX, toY);

        Stroke oldStroke = g.getStroke();
        Stroke stroke = new BasicStroke(2, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_MITER);
        g.setStroke(stroke);
        g.setColor(getColor());

        if (label != null)
        {
            Font font = new Font(null, Font.PLAIN, 24);
            AffineTransform affineTransform = new AffineTransform();

            // Der Differenzvektor
            Vector sub = toPx.subtract(fromPx);

            Vector lineMid = fromPx.add(sub.divide(0.5));
            affineTransform.rotate(sub.getRadians(), 0, 0);
            Font rotatedFont = font.deriveFont(affineTransform);
            g.setFont(rotatedFont);
            g.drawString(label, (int) lineMid.getX(), (int) lineMid.getY());
        }
        g.drawLine(fromX, fromY, toX, toY);
        g.setStroke(oldStroke);
    }

    /**
     * @hidden
     */
    public static void main(String[] args)
    {
        Game.debug();
        Game.start((scene) -> {
            LabeledEdge edge = new LabeledEdge(1, 1, 4, 5, "label");
            scene.add(edge);
        });
    }
}
