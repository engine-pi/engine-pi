package pi.graphics;

import pi.Vector;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/graphics2d/DirectedLineSegmentDemo.java

/**
 * Eine <b>gerichtete Strecke</b>.
 *
 * <p>
 * Diese Strecke ist definiert durch zwei Punkte nämlich dem {@link #from
 * Ursprung} und dem {@link #to Ziel}. Die Strecke liegt auf einer Linie, die
 * durch die beiden Punkte festgelegt ist.
 * </p>
 *
 * @param from Der Ursprung (steht auch für die Zahl 0)
 * @param to Das Ziel (steht auch für die Zahl 1)
 *
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public record DirectedLineSegment(Vector from, Vector to)
{
    /**
     * Der Differenzvektor von Urspung zum Ziel.
     */
    public Vector difference()
    {
        return to.subtract(from);
    }

    /**
     * Gibt einen Punkt auf der Linie zurück, dessen Lage im <b>Verhältnis</b>
     * zu den beiden Punkten der Strecke festgelegt ist.
     *
     * @param factor Bei dem Faktor {@code 0} liegt der berechnete Punkt auf dem
     *     Ursprung, bei {@code 1} auf dem Ziel.
     */
    public Vector proportionalPoint(double factor)
    {
        return from.add(difference().multiply(factor));
    }

    /**
     * Ein Punkt auf der Linie, der sich in einer bestimmten Entfernung vom
     * Ursprung befindet.
     *
     * <p>
     * Positive Entfernungen liegen in der Richtung des {@link #to Ziels}.
     * </p>
     *
     * @param distance Der <b>Abstand</b> vom Ursprung.
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
