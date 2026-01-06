package pi.actor;

import static pi.Resources.colors;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;

import pi.Game;
import pi.Vector;
import pi.annotations.Internal;
import pi.annotations.Setter;
import pi.graphics.DirectedLineSegment;
import pi.physics.FixtureBuilder;
import pi.util.Graphics2DUtil;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/LineRandomDemo.java

/**
 * Eine <b>Linie</b> zwischen zwei Punkten.
 *
 * @author Josef Friedrich
 *
 * @since 0.36.0
 */
public class Line extends Actor
{
    /**
     * Der <b>Punkt 1</b>.
     */
    private Vector point1;

    /**
     * Der <b>Punkt 2</b>.
     */
    private Vector point2;

    /**
     * Das Linenende bei Punkt 1.
     */
    private LineEnd end1;

    /**
     * Das Linienende bei Punkt 2.
     */
    private LineEnd end2;

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
        this.end1 = new LineEnd(point1, point2);
        this.end2 = new LineEnd(point2, point1);
        color(colors.getSafe("orange"));
    }

    @Setter
    public Line point1(Vector point1)
    {
        this.point1 = point1;
        return this;
    }

    @Setter
    public Line point2(Vector point2)
    {
        this.point2 = point2;
        return this;
    }

    /**
     * Setzt die Art der <b>Pfeilspitze</b> des Punkt <b>1</b>.
     *
     * @param arrow1 Die Art der <b>Pfeilspitze</b> des Punkt <b>1</b>.
     *
     * @since 0.42.0
     */
    @Setter
    public Line arrow1(ArrowType arrow1)
    {
        this.end1.arrow = arrow1;
        return this;
    }

    /**
     * Setzt die Art der <b>Pfeilspitze</b> des Punkt <b>2</b>.
     *
     * @param arrow2 Die Art der <b>Pfeilspitze</b> des Punkt <b>2</b>.
     *
     * @since 0.42.0
     */
    @Setter
    public Line arrow2(ArrowType arrow2)
    {
        this.end2.arrow = arrow2;
        return this;
    }

    /**
     * Setzt die <b>Dicke</b> der <b>Linie</b> in Meter.
     *
     * @param strokeWidth Die <b>Dicke</b> der <b>Linie</b> in Meter.
     */
    @Setter
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
        AffineTransform oldTransform = g.getTransform();
        g.scale(1, -1);
        Stroke oldStroke = g.getStroke();
        Color oldColor = g.getColor();
        Stroke stroke = new BasicStroke((float) (strokeWidth * pixelPerMeter),
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        g.setStroke(stroke);
        g.setColor(color());

        Graphics2DUtil.drawLine(g, point1, point2, pixelPerMeter);

        end1.render(g, pixelPerMeter);
        end2.render(g, pixelPerMeter);

        g.setTransform(oldTransform);
        g.setStroke(oldStroke);
        g.setColor(oldColor);
    }

    class LineEnd
    {
        /**
         * Der Punkt am Linienende.
         */
        Vector end;

        /**
         * Der gegenüberliegende Punkt.
         */
        Vector opposite;

        /**
         * Die Art der Pfeilspitze am Linienende.
         */
        ArrowType arrow;

        LineEnd(Vector end, Vector opposite)
        {
            this.end = end;
            this.opposite = opposite;
            arrow = ArrowType.NONE;
        }

        /**
         * Zeichnet eine Pfeilspitze ein.
         *
         * @param arrowType Die Art der Pfeilspitze.
         * @param arrowTarget Der Punkt an dem die Pfeilspitze eingezeichnet
         *     werden soll.
         * @param from Der Punkt vom dem aus die Pfeilspitze wegzeigen soll.
         * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
         */
        void render(Graphics2D g, double pixelPerMeter)
        {
            if (arrow == ArrowType.CHEVERON)
            {
                Graphics2DUtil.drawArrow(g, opposite.multiply(pixelPerMeter),
                        end.multiply(pixelPerMeter), 50, 45, false);
            }
            else if (arrow == ArrowType.TRIANGLE)
            {
                // Bei dicken Linien verdeckt das Dreieck das Linienende nicht
                // ganz.

                DirectedLineSegment segment = new DirectedLineSegment(end,
                        opposite);
                // Wir schieben die Pfeilspitze etwas nach vorne.

                Vector shiftedEnd = segment.distancePoint(-strokeWidth);

                Graphics2DUtil.drawArrow(g, opposite.multiply(pixelPerMeter),
                        end.multiply(pixelPerMeter), 50, 45, true);
            }
        }
    }

    /**
     * Die verschiedenen Arten einer <b>Pfeilspitze</b>.
     *
     * @author Josef Friedrich
     *
     * @since 0.42.0
     */
    public enum ArrowType
    {
        // https://google.github.io/guice/api-docs/4.2.2/javadoc/index.html?com/google/inject/grapher/graphviz/ArrowType.html

        // https://docs.yworks.com/yfiles-html/api/ArrowType.html

        /**
         * <b>Keine</b> Pfeilspitze
         */
        NONE,

        /**
         * Eine Pfeilspitze in Form eines <b>Winkels</b> (nach dem französischen
         * Wort Chevron (französisch chevron = „Winkel“, „Sparren“,
         * „Zickzackleiste“) )
         */
        CHEVERON,

        /**
         * Eine Pfeilspitze in Form eines <b>Dreiecks</b>.
         */
        TRIANGLE
    }

    /**
     * @hidden
     */
    public static void main(String[] args)
    {
        Game.instantMode(false);
        Game.start((scene) -> {
            Line line = new Line(1, 1, 4, 5);
            line.color("grün");
            scene.add(line);
        });
    }
}
