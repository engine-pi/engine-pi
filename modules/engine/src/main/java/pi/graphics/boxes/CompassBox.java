package pi.graphics.boxes;

import java.awt.Graphics2D;

import pi.Vector;
import pi.annotations.Setter;

/**
 * Eine quadratische Box mit einem Kompasspfeil, der in der Mitte des Quadrats
 * angebracht ist.
 *
 * @since 0.42.0
 *
 * @author Josef Friedrich
 */
public class CompassBox extends LeafBox
{

    double direction;

    public CompassBox()
    {
        super();
        supportsDefinedDimension = true;
    }

    @Setter
    public CompassBox direction(double direction)
    {
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

    private Vector center()
    {
        return new Vector(x + centerDistanz(), y + centerDistanz());
    }

    @Override
    protected void calculateDimension()
    {
        width = definedWidth;
        height = definedHeight;
    }

    @Override
    void draw(Graphics2D g)
    {
        Vector center = center();
        double distance = centerDistanz();
        Vector from = center.rotate(-direction).multiply(distance);
        Vector to = center.rotate(direction).multiply(distance);
        g.drawLine(from.getX(1), from.getY(1), to.getX(1), to.getY(1));
    }
}
