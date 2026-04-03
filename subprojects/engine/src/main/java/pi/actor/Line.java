/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025, 2026 Josef Friedrich and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package pi.actor;

import static pi.Controller.colorScheme;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;

import pi.Controller;
import pi.annotations.API;
import pi.annotations.ChainableMethod;
import pi.annotations.Getter;
import pi.annotations.Internal;
import pi.annotations.Setter;
import pi.graphics.geom.DirectedLineSegment;
import pi.graphics.geom.Vector;
import pi.physics.FixtureBuilder;
import pi.util.Graphics2DUtil;

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/line/LinePhysicsDemo.java
// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/LineRandomDemo.java
// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/graphics/geom/DirectedLineSegmentDemo.java

/**
 * Eine <b>Linie</b> zwischen zwei Endpunkten.
 *
 * @author Josef Friedrich
 *
 * @since 0.36.0
 */
public class Line extends Actor
{
    /**
     * Erstellt eine neue Linie mit den angegebenen <b>Koordinaten</b> der zwei
     * Endpunkte.
     *
     * @param x1 Die <b>x</b>-Koordinate des <b>ersten</b> Endpunkts.
     * @param y1 Die <b>y</b>-Koordinate des <b>ersten</b> Endpunkts.
     * @param x2 Die <b>x</b>-Koordinate des <b>zweiten</b> Endpunkts.
     * @param y2 Die <b>y</b>-Koordinate des <b>zweiten</b> Endpunkts.
     */
    public Line(double x1, double y1, double x2, double y2)
    {
        this(new Vector(x1, y1), new Vector(x2, y2));
    }

    /**
     * Erstellt eine neue Linie zwischen zwei Endpunkten als <b>Vektoren</b>.
     *
     * @param end1 Der <b>erste Endpunkt</b> der Linie als Vektor.
     * @param end2 Der <b>zweite Endpunkt</b> der Linie als Vektor.
     */
    public Line(Vector end1, Vector end2)
    {
        super(null);
        this.end1 = new LineEnd(end1, end2);
        this.end2 = new LineEnd(end2, end1);
        color(colorScheme.get().orange());
        update();
    }

    /* end1 */

    /**
     * Das Linenende bei Endpunkt 1.
     */
    public final LineEnd end1;

    /**
     * Gibt <b>ersten Endpunkt</b> der Linie zurück.
     *
     * @return Der <b>ersten Endpunkt</b> der Linie.
     */
    @API
    @Getter
    public Vector end1()
    {
        return this.end1.end();
    }

    /**
     * Setzt den <b>ersten Endpunkt</b> der Linie.
     *
     * @param end1 Der neue <b>erste Endpunkt</b> als {@link Vector}.
     *
     * @return diese {@link Line} Instanz für Method Chaining
     */
    @API
    @Setter
    @ChainableMethod
    public Line end1(Vector end1)
    {
        this.end1.end(end1);
        this.end2.opposite(end1);
        update();
        return this;
    }

    /* end2 */

    /**
     * Das Linienende bei Endpunkt 2.
     */
    public final LineEnd end2;

    /**
     * Gibt <b>zweiten Endpunkt</b> der Linie zurück.
     *
     * @return Der <b>zweiten Endpunkt</b> der Linie.
     */
    @API
    @Getter
    public Vector end2()
    {
        return this.end2.end();
    }

    /**
     * Setzt den <b>zweiten Endpunkt</b> der Linie.
     *
     * @param end2 Der neue <b>zweite Endpunkt</b> als {@link Vector}.
     *
     * @return diese {@link Line} Instanz für Method Chaining
     */
    @API
    @Setter
    @ChainableMethod
    public Line end2(Vector end2)
    {
        this.end2.end(end2);
        this.end1.opposite(end2);
        update();
        return this;
    }

    /* strokeWidth */

    /**
     * Die <b>Dicke</b> der <b>Linie</b> in Meter.
     */
    private double strokeWidth = 0.125;

    /**
     * Gibt die <b>Dicke</b> der <b>Linie</b> in Meter.
     *
     * <p>
     * Der voreingestellt Standardwert beträgt {@code 0.125} Meter.
     * </p>
     */
    @API
    @Getter
    public double strokeWidth()
    {
        return strokeWidth;
    }

    /**
     * Setzt die <b>Dicke</b> der <b>Linie</b> in Meter.
     *
     * <p>
     * Der voreingestellt Standardwert beträgt {@code 0.125} Meter.
     * </p>
     *
     * @param strokeWidth Die <b>Dicke</b> der <b>Linie</b> in Meter.
     */
    @API
    @Setter
    @ChainableMethod
    public Line strokeWidth(double strokeWidth)
    {
        this.strokeWidth = strokeWidth;
        update();
        return this;
    }

    /* rounded */

    private boolean rounded = false;

    @API
    @Getter
    public boolean rounded()
    {
        return rounded;
    }

    @API
    @Setter
    @ChainableMethod
    public Line rounded(boolean rounded)
    {
        this.rounded = rounded;
        return this;
    }

    private Cap cap = null;

    /* cap */

    // Go to
    // file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/line/LineCapPatternDemo.java

    @API
    @Getter
    public Cap cap()
    {
        if (cap != null)
        {
            return cap;
        }

        if (rounded())
        {
            return Cap.ROUND;
        }
        return Cap.BUTT;
    }

    @API
    @Setter
    public Line cap(Cap cap)
    {
        this.cap = cap;
        if (cap != Cap.ROUND)
        {
            rounded = false;
        }
        return this;
    }

    /* dashed */

    /**
     * Gibt an, ob eine gestrichelte Linie gezeichnet werden soll.
     */
    private boolean dashed = false;

    /**
     * Gibt an, ob die Linie <b>gestrichelt</b> oder durchgezogen gezeichnet
     * wird.
     *
     * @return Ob die Linie <b>gestrichelt</b> oder durchgezogen gezeichnet
     *     wird.
     */
    @API
    @Getter
    public boolean dashed()
    {
        return dashed;
    }

    /**
     * Setzt, ob eine gestrichelte Linie gezeichnet werden soll oder nicht.
     *
     * @param dashed Die <b>Dicke</b> der <b>Linie</b> in Meter.
     */
    @API
    @Setter
    @ChainableMethod
    public Line dashed(boolean dashed)
    {
        this.dashed = dashed;
        return this;
    }

    /* dashPattern */

    // Go to
    // file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/line/LineDashPatternDemo.java

    private double[] dashPattern = new double[] {};

    public double[] dashPattern()
    {
        return dashPattern;
    }

    /**
     * Legt das Strichmuster für die Darstellung dieser Linie fest.
     *
     * <p>
     * Die übergebenen Werte definieren abwechselnd sichtbare und ausgelassene
     * Abschnittslängen (z. B. {@code 5, 3} für 5 Einheiten Strich, 3 Einheiten
     * Lücke).
     * </p>
     *
     * @param dashPattern Ein Strichmuster als Folge von Abschnittslängen.
     *
     * @return diese {@code Line}-Instanz zur Verkettung weiterer Aufrufe
     *     (Fluent API)
     *
     * @since 0.45.0
     */
    @API
    @Setter
    @ChainableMethod
    public Line dashPattern(double... dashPattern)
    {
        if (dashPattern.length < 1)
        {
            throw new IllegalArgumentException(
                    "Ein Strichmuster muss mindestens aus einem Abschnitt bestehen.");
        }
        this.dashPattern = dashPattern;
        dashed = true;
        return this;
    }

    /* offset */

    /**
     * Gibt den <b>Versatz</b> beider Linienenden in Richtung des
     * gegenüberliegenden Punkts in Meter zurück.
     *
     * <b>Dieser Versatz ist beispielsweise nützlich, um Kanten zwischen Knoten
     * einzuzeichnen. Werden die Knoten als Kreise dargestellt, kann der Radius
     * der Knoten als Versatz verwendet werden.</b>
     *
     * @return Der <b>Versatz</b> beider Linienende in Richtung des
     *     gegenüberliegenden Punkts in Meter.
     *
     * @throws RuntimeException Falls die beiden Linienenden einen
     *     unterschiedlichen Versatz haben.
     */
    @API
    @Setter
    @ChainableMethod
    public double offset()
    {
        if (end1.offset() != end2.offset())
        {
            throw new RuntimeException(
                    "Die beiden Endpunkt der Linien haben einen unterschiedlichen Versatz.");
        }
        return end1.offset();
    }

    /**
     * Setzt den <b>Versatz</b> beider Linienenden in Richtung des
     * gegenüberliegenden Punkts in Meter.
     *
     * <b>Dieser Versatz ist beispielsweise nützlich, um Kanten zwischen Knoten
     * einzuzeichnen. Werden die Knoten als Kreise dargestellt, kann der Radius
     * der Knoten als Versatz verwendet werden.</b>
     *
     * @param offset Der <b>Versatz</b> beider Linienenden in Richtung des
     *     gegenüberliegenden Punkts in Meter.
     */
    @API
    @Setter
    @ChainableMethod
    public Line offset(double offset)
    {
        end1.offset(offset);
        end2.offset(offset);
        return this;
    }

    @Override
    public void update()
    {
        DirectedLineSegment segment = end1.lineSegment();

        // Wir machen die Halterung etwas größer, dass die Linie etwas mehr
        // Gewicht in der Physik-Simulation bekommt.
        double halfStrokeWidth = (strokeWidth / 2) * 1.5;
        fixture(() -> FixtureBuilder.polygon(
            segment.relativeVerticalPointFixedOffset(0, halfStrokeWidth),
            segment.relativeVerticalPointFixedOffset(0, -halfStrokeWidth),
            segment.relativeVerticalPointFixedOffset(1, halfStrokeWidth),
            segment.relativeVerticalPointFixedOffset(1, -halfStrokeWidth)));
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
            if (this.dashPattern.length > 1)
            {
                dash = new float[this.dashPattern.length];
                for (int i = 0; i < this.dashPattern.length; i++)
                {
                    dash[i] = (float) (this.dashPattern[i] * pixelPerMeter);
                }

            }
            else
            {
                dash = new float[] { scaledStrokeWidth * 6,
                        scaledStrokeWidth * 3 };
            }
        }

        g.setStroke(new BasicStroke(scaledStrokeWidth, cap().cap(),
                BasicStroke.JOIN_MITER, 10.0f, dash, 1f));
        g.setColor(color());

        Graphics2DUtil.drawLine(g, end1(), end2(), pixelPerMeter);

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
         * @param end Der Punkt am <b>Linienende</b>.
         * @param opposite Der diesem Linienende <b>gegenüberliegende</b> Punkt.
         */
        private LineEnd(Vector end, Vector opposite)
        {
            this.end = end;
            this.opposite = opposite;
            update();
        }

        /* end */

        /**
         * Der Punkt am <b>Linienende</b> ohne Versatz.
         */
        private Vector end;

        /**
         * Der Punkt am <b>Linienende</b> mit Versatz.
         */
        private Vector endWithOffset;

        /**
         * Gibt den Punkt am <b>Linienende</b> mit Verzug zurück.
         *
         * @return Der Punkt am <b>Linienende</b>
         */
        @Internal
        Vector end()
        {
            return endWithOffset;
        }

        /**
         * Setzt den Punkt an <b>Linienende</b>.
         *
         * @param end Der Punkt am <b>Linienende</b>.
         */
        @Internal
        LineEnd end(Vector end)
        {
            this.end = end;
            update();
            return this;
        }

        /* opposite */

        /**
         * Der diesem Linienende <b>gegenüberliegende</b> Punkt ohne Versatz.
         */
        Vector opposite;

        /**
         * Setzt den diesem Linienende <b>gegenüberliegende</b> Punkt.
         *
         * @param opposite Der diesem Linienende <b>gegenüberliegende</b> Punkt.
         */
        @Internal
        LineEnd opposite(Vector opposite)
        {
            this.opposite = opposite;
            update();
            return this;
        }

        /* offset */

        /**
         * Ein <b>Versatz</b> des Linienendes in Richtung des gegenüberliegenden
         * Punkts in Meter.
         */
        private double offset;

        /**
         * Gibt den <b>Versatz</b> des Linienendes in Richtung des
         * gegenüberliegenden Punkts in Meter zurück.
         *
         * <b>Dieser Versatz ist beispielsweise nützlich, um Kanten zwischen
         * Knoten einzuzeichnen. Werden die Knoten als Kreise dargestellt, kann
         * der Radius der Knoten als Versatz verwendet werden.</b>
         *
         * @return Der <b>Versatz</b> des Linienendes in Richtung des
         *     gegenüberliegenden Punkts in Meter.
         */
        @API
        @Getter
        public double offset()
        {
            return offset;
        }

        /**
         * Setzt den <b>Versatz</b> des Linienendes in Richtung des
         * gegenüberliegenden Punkts in Meter.
         *
         * <b>Dieser Versatz ist beispielsweise nützlich, um Kanten zwischen
         * Knoten einzuzeichnen. Werden die Knoten als Kreise dargestellt, kann
         * der Radius der Knoten als Versatz verwendet werden.</b>
         *
         * @param offset Der <b>Versatz</b> des Linienendes in Richtung des
         *     gegenüberliegenden Punkts in Meter.
         */
        @API
        @Setter
        @ChainableMethod
        public LineEnd offset(double offset)
        {
            this.offset = offset;
            update();
            return this;
        }

        /* arrow */

        /**
         * Die <b>Art der Pfeilspitze</b> am Linienende.
         */
        private ArrowType arrow = ArrowType.NONE;

        /**
         * Gibt die Art der <b>Pfeilspitze</b> zurück.
         *
         * @return Die Art der <b>Pfeilspitze</b>.
         *
         * @since 0.45.0
         */
        @API
        @Getter
        public ArrowType arrow()
        {
            return arrow;
        }

        /**
         * Setzt die Art der <b>Pfeilspitze</b>.
         *
         * @param arrow Die Art der <b>Pfeilspitze</b>.
         *
         * @since 0.42.0
         */
        @API
        @Setter
        @ChainableMethod
        public LineEnd arrow(ArrowType arrow)
        {
            this.arrow = arrow;
            return this;
        }

        /**
         * Setzt, ob eine <b>Pfeilspitze</b> (als Winkel) gezeichnet werden soll
         * oder nicht.
         *
         * @param arrow Ob eine <b>Pfeilspitze</b> (als Winkel) gezeichnet
         *     werden soll oder nicht.
         *
         * @since 0.42.0
         */
        @API
        @Setter
        @ChainableMethod
        public LineEnd arrow(boolean arrow)
        {
            if (arrow)
            {
                this.arrow = ArrowType.CHEVRON;
            }
            else
            {
                this.arrow = ArrowType.NONE;
            }
            return this;
        }

        /* arrowAngle */

        /**
         * Der <b>Winkel</b> der Pfeilspitze in Grad.
         */
        private double arrowAngle = 45;

        /**
         * Gibt den <b>Winkel</b> der Pfeilspitze in Grad zurück.
         *
         * <p>
         * Es handelt sich um den Winkel, der an der Spitze eines
         * gleichschenkligen Dreiecks liegt. In der Mathematik wird dieser
         * Winkel auch γ (gamma) genannt. Der Standardwert für diesen Winkel
         * beträgt {@code 45} Grad.
         * </p>
         *
         * @return Der <b>Winkel</b> der Pfeilspitze in Grad.
         */
        @API
        @Getter
        public double arrowAngle()
        {
            return arrowAngle;
        }

        /**
         * Setzt den <b>Winkel</b> der Pfeilspitze in Grad.
         *
         * <p>
         * Es handelt sich um den Winkel, der an der Spitze eines
         * gleichschenkligen Dreiecks liegt. In der Mathematik wird dieser
         * Winkel auch γ (gamma) genannt. Der Standardwert für diesen Winkel
         * beträgt {@code 45} Grad.
         * </p>
         *
         * @param arrowAngle Der <b>Winkel</b> der Pfeilspitze in Grad.
         */
        @API
        @Setter
        @ChainableMethod
        public LineEnd arrowAngle(double arrowAngle)
        {
            this.arrowAngle = arrowAngle;
            return this;
        }

        /* arrowSideLength */

        /**
         * Die <b>Seitenlänge der Pfeilspitze</b> in Meter.
         */
        private double arrowSideLength = 0.5;

        /**
         * Gibt die <b>Seitenlänge der Pfeilspitze</b> in Meter zurück.
         *
         * <p>
         * Die Pfeilspitze wird als gleichschenkliges Dreieck eingezeichnet. Die
         * Seitenlänge bezieht sich auf die Länge der Schenkel des
         * gleichseitigen Dreiecks. Standardmäßig ist die Seitenlänge der
         * Pfeilspitze {@code 0.5} Meter lang.
         * </p>
         *
         * @return Die <b>Seitenlänge der Pfeilspitze</b> in Meter.
         */
        @API
        @Getter
        public double arrowSideLength()
        {
            return arrowSideLength;
        }

        /**
         * Setzt die <b>Seitenlänge der Pfeilspitze</b> in Meter.
         *
         * <p>
         * Die Pfeilspitze wird als gleichschenkliges Dreieck eingezeichnet. Die
         * Seitenlänge bezieht sich auf die Länge der Schenkel des
         * gleichseitigen Dreiecks. Standardmäßig ist die Seitenlänge der
         * Pfeilspitze {@code 0.5} Meter lang.
         * </p>
         *
         * @param arrowSideLength Die <b>Seitenlänge der Pfeilspitze</b> in
         *     Meter.
         */
        @API
        @Setter
        @ChainableMethod
        public LineEnd arrowSideLength(double arrowSideLength)
        {
            this.arrowSideLength = arrowSideLength;
            return this;
        }

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

        private DirectedLineSegment lineSegment()
        {
            return lineSegment;
        }

        /**
         * Kann nicht überschrieben werden, da die Klasse nicht vom Actor erbt.
         */
        private void update()
        {
            lineSegment = new DirectedLineSegment(end, opposite);
            if (offset != 0)
            {
                endWithOffset = lineSegment.fixedPoint(offset);
                lineSegment = new DirectedLineSegment(endWithOffset, opposite);
            }
            else
            {
                endWithOffset = end;
            }
        }

        /**
         * Zeichnet eine Pfeilspitze ein.
         *
         * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
         */
        void render(Graphics2D g, double pixelPerMeter)
        {
            int sideLength = (int) Math
                .round(this.arrowSideLength * pixelPerMeter);

            if (arrow == ArrowType.CHEVRON)
            {
                Graphics2DUtil.drawArrow(g,
                    opposite.multiply(pixelPerMeter),
                    end().multiply(pixelPerMeter),
                    sideLength,
                    arrowAngle,
                    false);
            }
            else if (arrow == ArrowType.TRIANGLE)
            {
                // Bei dicken Linien verdeckt das Dreieck das Linienende nicht
                // ganz. Wir schieben die Pfeilspitze etwas nach vorne.
                // Die doppelte Liniedicke liefert ein gutes Ergebnis.
                Vector shiftedEnd = lineSegment.fixedPoint(-2 * strokeWidth);
                Graphics2DUtil.drawArrow(g,
                    opposite.multiply(pixelPerMeter),
                    shiftedEnd.multiply(pixelPerMeter),
                    sideLength,
                    arrowAngle,
                    true);
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
        // GraphViz array types: BOX
        // CROW
        // CURVE
        // ICURVE
        // DIAMOND
        // DOT
        // INV
        // NONE
        // NORMAL
        // TEE
        // VEE
        // https://www.graphviz.org/doc/info/arrows.html
        // https://google.github.io/guice/api-docs/4.2.2/javadoc/index.html?com/google/inject/grapher/graphviz/ArrowType.html

        // yFiles array types:
        // CHEVRON
        // CROSS
        // DELTOID
        // DIAMOND
        // ELLIPSE
        // KITE
        // NONE
        // OPEN
        // STEALTH
        // TRIANGLE
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
        CHEVRON,

        /**
         * Eine Pfeilspitze in Form eines <b>Dreiecks</b>.
         */
        TRIANGLE
    }

    /**
     * Definiert die Form der Endkappen einer Linie.
     *
     * <p>
     * Die Endkappe bestimmt, wie der Anfangs- und Endpunkt einer gezeichneten
     * Linie visuell abgeschlossen wird:
     * </p>
     *
     * <ul>
     * <li>{@link #BUTT}: Gerade abgeschnitten, exakt am Endpunkt.</li>
     * <li>{@link #ROUND}: Abgerundet mit halbrunder Kappe am Endpunkt.</li>
     * <li>{@link #SQUARE}: Rechteckig verlängert über den Endpunkt hinaus.</li>
     * </ul>
     *
     * @since 0.45.0
     */
    public enum Cap
    {
        /**
         * Gerade abgeschnitten, exakt am Endpunkt.
         */
        BUTT(0),

        /**
         * Abgerundet mit halbrunder Kappe am Endpunkt.
         */
        ROUND(1),

        /**
         * Rechteckig verlängert über den Endpunkt hinaus.
         */
        SQUARE(2);

        private final int cap;

        Cap(int cap)
        {
            this.cap = cap;
        }

        public int cap()
        {
            return cap;
        }
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
