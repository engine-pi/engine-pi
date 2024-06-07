package rocks.friedrich.engine_omega.actor;

import rocks.friedrich.engine_omega.Vector;

public class RegularPolygon extends Polygon
{
    public RegularPolygon(int numSides, double radius)
    {
        super(RegularPolygon.getVectors(numSides, radius));
    }

    public RegularPolygon(int numSides)
    {
        this(numSides, 1);
    }

    public static Vector[] getVectors(int numSides, double radius)
    {
        Vector[] vectors = new Vector[numSides];
        double angleStep = 2 * Math.PI / numSides;
        for (int i = 0; i < numSides; ++i)
        {
            double angle = i * angleStep;
            vectors[i] = new Vector(radius * Math.cos(angle),
                    radius * Math.sin(angle));
        }
        return vectors;
    }
}
