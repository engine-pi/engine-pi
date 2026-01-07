package pi.actor;

import static pi.Controller.colors;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;

import pi.Controller;
import pi.Vector;
import pi.annotations.Getter;
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
     * Das Linenende bei Punkt 1.
     */
    public final LineEnd end1;

    /**
     * Das Linienende bei Punkt 2.
     */
    public final LineEnd end2;

    /**
     * Die <b>Dicke</b> der <b>Linie</b> in Meter.
     */
    private double strokeWidth = 0.125;

    /**
     * Gibt an, ob eine gestrichelte Linie gezeichnet werden soll.
     */
    private boolean dashed = false;

    public Line(double x1, double y1, double x2, double y2)
    {
        this(new Vector(x1, y1), new Vector(x2, y2));
    }

    public Line(Vector point1, Vector point2)
    {
        super(() -> FixtureBuilder.line(point1, point2));
        this.end1 = new LineEnd(point1, point2);
        this.end2 = new LineEnd(point2, point1);
        color(colors.getSafe("orange"));
    }

    @Getter
    public Vector point1()
    {
        return this.end1.end();
    }

    @Setter
    public Line point1(Vector point1)
    {
        this.end1.end(point1);
        this.end2.opposite(point1);
        return this;
    }

    @Getter
    public Vector point2()
    {
        return this.end2.end();
    }

    @Setter
    public Line point2(Vector point2)
    {
        this.end2.end(point2);
        this.end1.opposite(point2);
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
     * Setzt, ob eine gestrichelte Linie gezeichnet werden soll oder nicht.
     *
     * @param dashed Die <b>Dicke</b> der <b>Linie</b> in Meter.
     */
    @Setter
    public Line dashed(boolean dashed)
    {
        this.dashed = dashed;
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

        float[] dash = (float[]) (null);

        float scaledStrokeWidth = (float) (strokeWidth * pixelPerMeter);

        if (dashed)
        {
            dash = new float[] { scaledStrokeWidth * 6, scaledStrokeWidth * 3 };
        }

        g.setStroke(new BasicStroke(scaledStrokeWidth, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_MITER, 10.0f, dash, 1f));
        g.setColor(color());

        Graphics2DUtil.drawLine(g, point1(), point2(), pixelPerMeter);

        end1.render(g, pixelPerMeter);
        end2.render(g, pixelPerMeter);

        g.setTransform(oldTransform);
        g.setStroke(oldStroke);
        g.setColor(oldColor);
    }

    /**
     * Ein <b>Linienende</b>.
     *
     * @since 0.42.0
     */
    public class LineEnd
    {
        /**
         * Der Punkt am <b>Linienende</b> ohne Versatz.
         */
        private Vector end;

        private Vector endWithOffset;

        /**
         * Der diesem Linienende <b>gegenüberliegende</b> Punkt ohne Versatz.
         */
        private Vector opposite;

        /**
         * Die <b>Art der Pfeilspitze</b> am Linienende.
         */
        private ArrowType arrow = ArrowType.NONE;

        /**
         * Hilfsklasse, um z.B. die Pfeilspitze etwas anderes positionieren zu
         * können oder einen Verzug des Linienendes zu berechnen.
         *
         * <p>
         * Das {@link #end Linienende} ist dabei als
         * {@link DirectedLineSegment#from() Ursprung} definiert.
         * </p>
         */
        private DirectedLineSegment lineSegment;

        /**
         * Ein <b>Versatz</b> des Linienendes in Richtung des gegenüberliegenden
         * Punkts in Meter. Nützlich um Kanten zwischen Knoten einzuzeichnen.
         */
        private double offset;

        /**
         * @param end Der Punkt am <b>Linienende</b>.
         * @param opposite Der diesem Linienende <b>gegenüberliegende</b> Punkt.
         */
        LineEnd(Vector end, Vector opposite)
        {
            this.end = end;
            this.opposite = opposite;
            syncAttributes();
        }

        private void syncAttributes()
        {
            lineSegment = new DirectedLineSegment(end, opposite);
            if (offset != 0)
            {
                endWithOffset = lineSegment.distancePoint(offset);
                lineSegment = new DirectedLineSegment(endWithOffset, opposite);
            }
            else
            {
                endWithOffset = end;
            }
        }

        public LineEnd offset(double offset)
        {
            this.offset = offset;
            syncAttributes();
            return this;
        }

        /**
         * Gibt den Punkt am <b>Linienende</b> mit Verzug zurück.
         *
         * @return Der Punkt am <b>Linienende</b>
         */
        public Vector end()
        {
            return endWithOffset;
        }

        /**
         * Setzt den Punkt an <b>Linienende</b>.
         *
         * @param end Der Punkt am <b>Linienende</b>.
         */
        @Setter
        public LineEnd end(Vector end)
        {
            this.end = end;
            syncAttributes();
            return this;
        }

        /**
         * Setzt den diesem Linienende <b>gegenüberliegende</b> Punkt.
         *
         * @param opposite Der diesem Linienende <b>gegenüberliegende</b> Punkt.
         */
        @Setter
        public LineEnd opposite(Vector opposite)
        {
            this.opposite = opposite;
            syncAttributes();
            return this;
        }

        /**
         * Setzt die Art der <b>Pfeilspitze</b>.
         *
         * @param arrow Die Art der <b>Pfeilspitze</b>.
         *
         * @since 0.42.0
         */
        public LineEnd arrow(ArrowType arrow)
        {
            this.arrow = arrow;
            return this;
        }

        /**
         * Zeichnet eine Pfeilspitze ein.
         *
         * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
         */
        void render(Graphics2D g, double pixelPerMeter)
        {
            if (arrow == ArrowType.CHEVERON)
            {
                Graphics2DUtil.drawArrow(g, opposite.multiply(pixelPerMeter),
                        end().multiply(pixelPerMeter), 50, 45, false);
            }
            else if (arrow == ArrowType.TRIANGLE)
            {
                // Bei dicken Linien verdeckt das Dreieck das Linienende nicht
                // ganz. Wir schieben die Pfeilspitze etwas nach vorne.
                // Die doppelte Liniedicke liefert ein gutes Ergebnis.
                Vector shiftedEnd = lineSegment.distancePoint(-2 * strokeWidth);
                Graphics2DUtil.drawArrow(g, opposite.multiply(pixelPerMeter),
                        shiftedEnd.multiply(pixelPerMeter), 50, 45, true);
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
        Controller.instantMode(false);
        Controller.start((scene) -> {
            Line line = new Line(1, 1, 4, 5);
            line.color("grün");
            scene.add(line);
        });
    }
}
