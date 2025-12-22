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
     * Die <b>Seitenlänge</b> des Quadrats in den der Kompasspfeil eingepasst
     * ist bzw. der Durchmesser des Kreises auf dem die beiden Endpunkte des
     * Pfeils liegen.
     */
    int size;

    /**
     * Die Richtung, in der die Kompassnadel zeigt. 0 = nach rechts, 90 = nach
     * oben, 180 = nach links, 270 = nach unten.
     */
    double direction;

    /**
     * Die Höhe des gleichschenkligen Dreiecks, das die Pfeilspitze bildet.
     */
    int arrowHeight = 15;

    /**
     * Die Breite der Basis des gleichschenkligen Dreiecks, das die Pfeilspitze
     * bildet.
     */
    int arrowWidth = 10;

    public CompassBox(int size)
    {
        super();
        this.size = size;
        this.definedHeight = size;
        this.definedWidth = size;
        supportsDefinedDimension = true;
    }

    @Setter
    public CompassBox direction(double direction)
    {
        //
        this.direction = direction;
        return this;
    }

    /**
     * Die Entfernung zur Mitte des Quadrats also die Hälfte der Seitenlänge des
     * Quadrats.
     */
    private double centerDistanz()
    {
        return (double) definedWidth / 2;

    }

    /**
     * Gibt den <b>Mittelpunkt</b> des Rahmenquadrats.
     *
     * @return Der <b>Mittelpunkt</b> des Rahmenquadrats.
     */
    private Vector center()
    {
        return new Vector(x + centerDistanz(), y + centerDistanz());
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
        double distance = centerDistanz();

        // Graphics2D hat einen andere Drehrichtung als die Engine Pi
        double normalizeDirection = -direction;

        // Der äußere Kreis
        g.drawOval(x, y, size, size);
        // Kleiner Kreis als Mittelpunkt
        g.drawOval(center.getX(1) - 1, center.getY(1) - 1, 2, 2);

        // Ursprung des Pfeils
        Vector from = center.add(
                Vector.ofAngle(normalizeDirection - 180).multiply(distance));

        // Punkt der in bestimmte Richtung auf dem Einheitskreis zeigt.
        Vector toUnionCircle = Vector.ofAngle(normalizeDirection);

        // Endpunkt des Pfeils, wo die Pfeilspitze sitzt.
        Vector to = center.add(toUnionCircle.multiply(distance));

        // Die Mitte der Basis des Pfeildreiecks
        Vector arrowBase = center
                .add(toUnionCircle.multiply(distance - arrowHeight));

        Vector arrowLeft = arrowBase.add(Vector.ofAngle(normalizeDirection - 90)
                .multiply((double) arrowWidth / 2));
        Vector arrowRight = arrowBase
                .add(Vector.ofAngle(normalizeDirection + 90)
                        .multiply((double) arrowWidth / 2));

        // Die Spitze des Kompasspfeils als Dreieck gezeichnet.
        g.fillPolygon(
                new int[]
                { arrowLeft.getX(1), to.getX(1), arrowRight.getX(1) },
                new int[]
                { arrowLeft.getY(1), to.getY(1), arrowRight.getY(1) }, 3);

        // Die Line des Compasspfeils
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
