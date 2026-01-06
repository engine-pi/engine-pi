package pi.graphics;

import pi.Vector;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/graphics2d/DirectedLineSegmentDemo.java

/**
 * Eine <b>gerichtete Strecke</b>.
 *
 * <p>
 * Diese Strecke liegt auf einer Linie, die durch die beiden Punkte festgelegt
 * ist.
 * </p>
 *
 * @param from Der Ursprung (0)
 * @param to Das Ziel (1)
 *
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public record DirectedLineSegment(Vector from, Vector to)
{
    /**
     * Der Differenzvektor von a nach b.
     */
    public Vector difference()
    {
        return to.subtract(from);
    }

    /**
     * Ein beliebiger Punkt auf der Linie, der im Verhältnis zu den beiden
     * Punkten der Strecke festgelegt ist.
     *
     * @param factor 0 ist der Ursprung, 1 ist das Ziel
     */
    public Vector proportionalPoint(double factor)
    {
        return from.add(difference().multiply(factor));
    }

    /**
     * Ein Punkt, der in einer bestimmten Entfernung zum Ursprung liegt.
     *
     * <p>
     * Positive Entfernungen liegen in der Richtung des {@link #to Ziels}.
     * </p>
     *
     * @param distance Der Abstand vom Ursprung.
     */
    public Vector distancePoint(double distance)
    {
        return from.add(difference().normalize().multiply(distance));
    }

    /**
     * Die Länge der Strecke.
     */
    public double length()
    {
        return difference().length();
    }

    /**
     * @return Eine in die andere Richtung verlaufende Strecke.
     */
    public DirectedLineSegment negate()
    {
        return new DirectedLineSegment(to, from);
    }
}
