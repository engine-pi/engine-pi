package rocks.friedrich.engine_omega.actor;

import rocks.friedrich.engine_omega.Vector;

public class Star extends Polygon
{
    public Star()
    {
        super(Star.getVectors(0,0,2,1,7));
    }

    public static Vector[] getVectors(int x, int y, double radius, double innerRadius,
            int numPoints)
    {
        Vector[] vectors = new Vector[numPoints * 2];
        double angleStep = Math.PI / numPoints;
        double startAngle = Math.PI / 2.0;
        for (int i = 0; i < numPoints; i++)
        {
            vectors[i * 2] = new Vector(
                    x + radius * Math.cos(startAngle + 2 * i * angleStep),
                    y - radius * Math.sin(startAngle + 2 * i * angleStep));
            vectors[i * 2 + 1] = new Vector(
                    x + innerRadius
                            * Math.cos(startAngle + (2 * i + 1) * angleStep),
                    y - innerRadius
                            * Math.sin(startAngle + (2 * i + 1) * angleStep));
        }
        return vectors;
    }
}
