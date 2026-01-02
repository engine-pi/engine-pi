package pi.graphics.boxes;

import java.awt.Graphics2D;

import pi.Vector;
import pi.annotations.Setter;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/graphics/boxes/CompassBoxDemo.java

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
     * Die <b>Seitenlänge</b> des Quadrats, in das der Kompasspfeil eingepasst
     * ist, bzw. der Durchmesser des Kreises, auf dem die beiden Endpunkte des
     * Pfeils liegen.
     */
    int size;

    /**
     * Die Richtung in Grad, in der die Kompassnadel zeigt. 0 = nach rechts, 90
     * = nach oben, 180 = nach links, 270 = nach unten.
     */
    double direction;

    /**
     * Die <b>Höhe</b> des gleichschenkligen Dreiecks, das die
     * <b>Pfeilspitze</b> bildet, im Verhältnis zu {@link #size}.
     */
    final double arrowHeight = 0.3;

    /**
     * Die <b>Breite</b> der Basis des gleichschenkligen Dreiecks, das die
     * <b>Pfeilspitze</b> bildet, im Verhältnis zu {@link #size}.
     */
    final double arrowWidth = 0.3;

    boolean showCenter = true;

    boolean showOuterCircle = false;

    public CompassBox(int size)
    {
        super();
        this.size = size;
        this.definedHeight = size;
        this.definedWidth = size;
        supportsDefinedDimension = true;
    }

    /**
     * Setzt die <b>Richtung</b> der Kompassnadel in Grad.
     *
     * @param direction Die <b>Richtung</b> in Grad, in der die Kompassnadel
     *     zeigt. 0 = nach rechts, 90 = nach oben, 180 = nach links, 270 = nach
     *     unten.
     */
    @Setter
    public CompassBox direction(double direction)
    {
        //
        this.direction = direction;
        return this;
    }

    @Setter
    public CompassBox showCenter(boolean showCenter)
    {
        this.showCenter = showCenter;
        return this;
    }

    @Setter
    public CompassBox showOuterCircle(boolean showOuterCircle)
    {
        this.showOuterCircle = showOuterCircle;
        return this;
    }

    @Setter
    public CompassBox showOuterCircle()
    {
        return showOuterCircle(true);
    }

    /**
     * Die Entfernung zur Mitte des Quadrats also die Hälfte der Seitenlänge des
     * Quadrats.
     */
    private double radius()
    {
        return (double) size / 2;
    }

    /**
     * Gibt den <b>Mittelpunkt</b> des Rahmenquadrats.
     *
     * @return Der <b>Mittelpunkt</b> des Rahmenquadrats.
     */
    private Vector center()
    {
        return new Vector(x + radius(), y + radius());
    }

    @Override
    protected void calculateDimension()
    {
        width = size;
        height = size;
    }

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
            g.drawOval(x, y, size, size);
        }
        if (showCenter)
        {
            // Kleiner Kreis als Mittelpunkt.
            g.drawOval(center.getX(1) - 1, center.getY(1) - 1, 2, 2);
        }
        // Ursprung des Pfeils.
        Vector from = center.add(
                Vector.ofAngle(normalizedDirection - 180).multiply(radius));

        // Punkt, der in eine bestimmte Richtung auf dem Einheitskreis zeigt.
        Vector toUnionCircle = Vector.ofAngle(normalizedDirection);

        // Endpunkt des Pfeils, wo die Pfeilspitze sitzt.
        Vector to = center.add(toUnionCircle.multiply(radius));

        // Die Mitte der Basis des Pfeildreiecks.
        Vector arrowBase = center
                .add(toUnionCircle.multiply(radius * (1 - arrowHeight * 2)));

        double halfArrowBase = (double) radius * arrowWidth;

        Vector arrowLeft = arrowBase.add(Vector
                .ofAngle(normalizedDirection - 90).multiply(halfArrowBase));
        Vector arrowRight = arrowBase.add(Vector
                .ofAngle(normalizedDirection + 90).multiply(halfArrowBase));

        // Die Spitze des Kompasspfeils als Dreieck gezeichnet.
        g.fillPolygon(
                new int[]
                { arrowLeft.getX(1), to.getX(1), arrowRight.getX(1) },
                new int[]
                { arrowLeft.getY(1), to.getY(1), arrowRight.getY(1) }, 3);

        // Die Line des Kompasspfeils.
        g.drawLine(from.getX(1), from.getY(1), to.getX(1), to.getY(1));
    }

    @Override
    public String toString()
    {
        var formatter = getToStringFormatter();

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
