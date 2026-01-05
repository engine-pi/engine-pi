package pi.util;

import pi.Vector;

/**
 * Eine Strecke
 *
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public class LineSegment
{
    /**
     * Der Punkt A. Der Ursprung A (0)
     */
    Vector a;

    /**
     * Der Punkt B. Das Ende (1)
     */
    Vector b;

    public LineSegment(Vector a, Vector b)
    {
        this.a = a;
        this.b = b;
    }

    /**
     * Der Differenzvektor von a nach b.
     */
    Vector aToB()
    {
        return b.subtract(a);
    }

    /**
     * Der Differenzvektor von b nach a.
     */
    Vector bToA()
    {
        return a.subtract(b);
    }

    /**
     * Ein beliebiger Punkt auf der Linie, die durch die beiden Punkte
     * festgelegt ist.
     *
     * @param factor 0 ist a, 1 ist b
     */
    Vector point(double factor)
    {
        return aToB().multiply(factor);
    }

    /**
     * Die Länge der Strecke.
     */
    double length()
    {
        return aToB().length();
    }
}
