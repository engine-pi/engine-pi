package pi.graphics.boxes_ng;

import static pi.util.MathUtil.round;

import java.awt.Graphics2D;

import pi.annotations.ChainableMethod;
import pi.annotations.Getter;
import pi.annotations.Setter;
import pi.graphics.geom.Vector;
import pi.util.Graphics2DUtil;

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/graphics/boxes_ng/CompassBoxDemo.java

/**
 * Ein <b>Kompasspfeil</b>, der in der Mitte eines Quadrats angebracht ist.
 *
 * @since 0.42.0
 *
 * @author Josef Friedrich
 */
public class CompassBox extends LeafBox
{
    /**
     * Die <b>Höhe</b> des gleichschenkligen Dreiecks, das die
     * <b>Pfeilspitze</b> bildet, im Verhältnis zu {@link #size}.
     */
    static final double ARROW_HEIGHT = 0.3;

    /**
     * Die <b>Breite</b> der Basis des gleichschenkligen Dreiecks, das die
     * <b>Pfeilspitze</b> bildet, im Verhältnis zu {@link #size}.
     */
    static final double ARROW_WIDTH = 0.3;

    /**
     * Erstellt einen neuen Kompasspfeil mit der angegebenen Seitenlänge.
     *
     * @param size Die <b>Seitenlänge</b> des Quadrats in Pixel, in das der
     *     Kompasspfeil eingepasst ist.
     */
    public CompassBox(double size)
    {
        super();
        this.size = size;
        definedHeight = size;
        definedWidth = size;
        supportsDefinedDimension = true;
    }

    /* size */

    /**
     * Die <b>Seitenlänge</b> des Quadrats in Pixel, in das der Kompasspfeil
     * eingepasst ist, bzw. der Durchmesser des Kreises, auf dem die beiden
     * Endpunkte des Pfeils liegen.
     */
    double size;

    /**
     * Gibt die <b>Seitenlänge</b> des Quadrats in Pixel zurück.
     *
     * @return Die <b>Seitenlänge</b> des Quadrats in Pixel.
     *
     * @since 0.53.0
     */
    @Getter
    public int size()
    {
        return round(size);
    }

    /**
     * Setzt die <b>Seitenlänge</b> des Quadrats in Pixel.
     *
     * @param size Die neue <b>Seitenlänge</b> des Quadrats in Pixel.
     *
     * @return Eine Referenz auf die eigene Instanz des Kompasses, damit nach
     *     dem Erbauer/Builder-Entwurfsmuster die Eigenschaften des Kompasses
     *     durch aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code compass.size(..).showOuterCircle(..)}.
     *
     * @since 0.53.0
     */
    @Setter
    @ChainableMethod
    public CompassBox size(double size)
    {
        this.size = size;
        return this;
    }

    /* direction */

    /**
     * Die Richtung in Grad, in der die Kompassnadel zeigt.
     * <ul>
     * <li>{@code 0} = nach rechts</li>
     * <li>{@code 90} = nach oben</li>
     * <li>{@code 180} = nach links</li>
     * <li>{@code 270} = nach unten</li>
     * </ul>
     */
    double direction;

    /**
     * Setzt die <b>Richtung</b> der Kompassnadel in Grad.
     *
     * @param direction Die <b>Richtung</b> in Grad, in der die Kompassnadel
     *     zeigt.
     *     <ul>
     *     <li>{@code 0} = nach rechts</li>
     *     <li>{@code 90} = nach oben</li>
     *     <li>{@code 180} = nach links</li>
     *     <li>{@code 270} = nach unten</li>
     *     </ul>
     *
     * @return Eine Referenz auf die eigene Instanz des Kompasses, damit nach
     *     dem Erbauer/Builder-Entwurfsmuster die Eigenschaften des Kompasses
     *     durch aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code compass.size(..).showOuterCircle(..)}.
     */
    @Setter
    @ChainableMethod
    public CompassBox direction(double direction)
    {
        this.direction = direction;
        return this;
    }

    /* showCenter */

    /**
     * Bestimmt, ob der Mittelpunkt des Kompasspfeils angezeigt wird.
     */
    boolean showCenter = true;

    /**
     * Legt fest, ob der Mittelpunkt des Kompasspfeils sichtbar ist.
     *
     * @param showCenter {@code true}, wenn der Mittelpunkt angezeigt werden
     *     soll, sonst {@code false}.
     *
     * @return Eine Referenz auf die eigene Instanz des Kompasses, damit nach
     *     dem Erbauer/Builder-Entwurfsmuster die Eigenschaften des Kompasses
     *     durch aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code compass.size(..).showOuterCircle(..)}.
     */
    @Setter
    @ChainableMethod
    public CompassBox showCenter(boolean showCenter)
    {
        this.showCenter = showCenter;
        return this;
    }

    /* showOuterCircle */

    /**
     * Bestimmt, ob der äußere Kreis um den Kompasspfeil angezeigt wird.
     */
    boolean showOuterCircle = false;

    /**
     * Legt fest, ob der äußere Kreis um den Kompasspfeil angezeigt wird.
     *
     * @param showOuterCircle {@code true}, wenn der äußere Kreis angezeigt
     *     werden soll, sonst {@code false}.
     *
     * @return Eine Referenz auf die eigene Instanz des Kompasses, damit nach
     *     dem Erbauer/Builder-Entwurfsmuster die Eigenschaften des Kompasses
     *     durch aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code compass.size(..).showOuterCircle(..)}.
     */
    @Setter
    @ChainableMethod
    public CompassBox showOuterCircle(boolean showOuterCircle)
    {
        this.showOuterCircle = showOuterCircle;
        return this;
    }

    /**
     * Zeigt den äußeren Kreis an.
     *
     * @return Eine Referenz auf die eigene Instanz des Kompasses, damit nach
     *     dem Erbauer/Builder-Entwurfsmuster die Eigenschaften des Kompasses
     *     durch aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code compass.size(..).showOuterCircle(..)}.
     */
    @Setter
    @ChainableMethod
    public CompassBox showOuterCircle()
    {
        return showOuterCircle(true);
    }

    /**
     * Gibt den <b>Radius</b> des äußeren Kreises, also die Entfernung zur Mitte
     * des Quadrats, also die Hälfte der Seitenlänge des Quadrats.
     *
     * @return Der <b>Radius</b> des äußeren Kreises, also die Entfernung zur
     *     Mitte des Quadrats, also die Hälfte der Seitenlänge des Quadrats.
     */
    @Getter
    public double radius()
    {
        return size / 2.0;
    }

    /**
     * Gibt den <b>Mittelpunkt</b> des Rahmenquadrats.
     *
     * @return Der <b>Mittelpunkt</b> des Rahmenquadrats.
     */
    @Getter
    public Vector center()
    {
        return new Vector(x() + radius(), y() - radius());
    }

    /**
     * @hidden
     */
    @Override
    protected void calculateDimension()
    {
        width = size;
        height = size;
    }

    /**
     * @hidden
     */
    @Override
    void draw(Graphics2D g)
    {
        Vector center = center();
        double radius = radius();

        // Graphics2D hat einen andere Drehrichtung als die Engine Pi.
        double normalizedDirection = -direction;

        if (showOuterCircle)
        {
            // Der äußere Kreis.
            g.drawOval(x(), yTop(), size(), size());
        }
        if (showCenter)
        {
            // Kleiner Kreis als Mittelpunkt.
            g.drawOval(center.x(1) - 1, center.y(1) - 1, 2, 2);
        }
        // Ursprung des Pfeils.
        Vector from = center
            .add(Vector.ofAngle(normalizedDirection - 180).multiply(radius));

        // Punkt, der in eine bestimmte Richtung auf dem Einheitskreis zeigt.
        Vector toUnionCircle = Vector.ofAngle(normalizedDirection);

        // Endpunkt des Pfeils, wo die Pfeilspitze sitzt.
        Vector to = center.add(toUnionCircle.multiply(radius));

        // Die Spitze des Kompasspfeils als Winkel gezeichnet.
        Graphics2DUtil.drawArrow(g, from, to, 8, 45, false);

        // Die Line des Kompasspfeils.
        g.drawLine(from.x(1), from.y(1), to.x(1), to.y(1));
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        var formatter = toStringFormatter();

        if (direction != 0)
        {
            formatter.prepend("direction", direction);
        }
        if (size > 0)
        {
            formatter.prepend("size", size);
        }
        return formatter.format();
    }
}
