package de.pirckheimer_gymnasium.engine_pi.actor;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;
import de.pirckheimer_gymnasium.engine_pi.physics.FixtureBuilder;
import de.pirckheimer_gymnasium.engine_pi.util.FontStringBounds;
import de.pirckheimer_gymnasium.engine_pi.util.FontUtil;

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

    /**
     * Der Bezeichner der Kante (z.B. das Kantengewicht).
     */
    private String label;

    /**
     * Der Abstand der Kantenbezeichnung zur Kantenlinie in Meter.
     */
    public static double LABEL_LINE_DISTANCE = 0.25;

    /**
     * Die <b>Schriftgröße</b> des Bezeichners in Punkten (z.B. 12pt).
     */
    public static double FONT_SIZE = 12;

    public static Font FONT = Resources.FONTS.get("fonts/Cantarell-Regular.ttf")
            .deriveFont((float) FONT_SIZE);

    public LabeledEdge(double fromX, double fromY, double toX, double toY,
            String label)
    {
        this(new Vector(fromX, fromY), new Vector(toX, toY), label);
    }

    public LabeledEdge(double fromX, double fromY, double toX, double toY)
    {
        this(fromX, fromY, toX, toY, null);
    }

    public LabeledEdge(Vector from, Vector to)
    {
        this(from, to, null);
    }

    public LabeledEdge(Vector from, Vector to, String label)
    {
        super(() -> FixtureBuilder.line(from, to));
        this.from = from;
        this.to = to;
        this.label = label;
        setColor("gray");
    }

    public void setLabel(String label)
    {
        this.label = label;
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
            AffineTransform affineTransform = new AffineTransform();

            FontStringBounds labelBounds = FontUtil.getStringBoundsNg(label,
                    FONT);

            // Der Differenzvektor
            Vector edge = toPx.subtract(fromPx);

            double edgeLength = edge.getLength();
            double labelMargin = (edgeLength - labelBounds.getWidth()) / 2;

            Vector labelLineDistance = Vector.ofAngle(edge.getAngle() - 90)
                    .multiply(LABEL_LINE_DISTANCE * pixelPerMeter);

            Vector labelAnchor = fromPx
                    .add(edge.multiply(labelMargin / edgeLength))
                    .add(labelLineDistance);
            affineTransform.rotate(edge.getRadians(), 0, 0);
            Font rotatedFont = FONT.deriveFont(affineTransform);
            g.setFont(rotatedFont);
            g.drawString(label, (int) labelAnchor.getX(),
                    (int) labelAnchor.getY());
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
            LabeledEdge e1, e2;
            e1 = new LabeledEdge(1, 1, 4, 5, "label");

            e2 = new LabeledEdge(-1, -1, -4, -5,
                    "e2: A very, very, very long label");
            scene.add(e1, e2);
        });
    }
}
