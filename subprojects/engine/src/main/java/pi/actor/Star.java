/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2024 Josef Friedrich and contributors.
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

import pi.debug.ToStringFormatter;
import pi.graphics.geom.Vector;

/**
 * Ein regelmäßiger <b>Stern</b>.
 *
 * @author Josef Friedrich
 */
public class Star extends Polygon
{

    /**
     * Die <b>Anzahl der Zacken</b> des Sterns.
     */
    private int numPoints;

    /**
     * Der <b>äußere Radius</b> des Sterns.
     */
    private double radius;

    /**
     * Der <b>innere Radius</b> des Sterns.
     */
    private double innerRadius;

    /**
     * Erstellt einen Stern mit einem <b>äußeren Radius vom 2 Meter</b>, einem
     * <b>innere Radius von 1 Meter</b> und <b>7 Zacken</b>.
     */
    public Star()
    {
        this(7, 2, 1);
    }

    /**
     * Erstellt einen Stern durch Angabe des <b>äußeren Radius</b>, des
     * <b>innere Radius</b> und der <b>Anzahl der Zacken</b>.
     *
     * @param numPoints Die <b>Anzahl der Zacken</b> des Sterns.
     * @param radius Der <b>äußere Radius</b> des Sterns.
     * @param innerRadius Der <b>innere Radius</b> des Sterns.
     *
     * @since 0.36.0
     */
    public Star(int numPoints, double radius, double innerRadius)
    {
        super(Star.calculateVectors(numPoints, radius, innerRadius));
        this.numPoints = numPoints;
        this.radius = radius;
        this.innerRadius = innerRadius;

    }

    /**
     * Berechnet die Vektoren, die die Punkte eines Sterns mit einer bestimmten
     * Anzahl von Spitzen, einem äußeren Radius und einem inneren Radius
     * darstellen.
     *
     * @param numPoints Die <b>Anzahl der Zacken</b> des Sterns.
     * @param radius Der <b>äußere Radius</b> des Sterns.
     * @param innerRadius Der <b>innere Radius</b> des Sterns.
     *
     * @return Ein Array von Vektoren, das die Punkte des Sterns repräsentiert.
     */
    private static Vector[] calculateVectors(int numPoints, double radius,
            double innerRadius)
    {
        int x = 0;
        int y = 0;
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

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        ToStringFormatter formatter = new ToStringFormatter("Star");
        formatter.append("numPoints", numPoints);
        formatter.append("radius", radius, "m");
        formatter.append("innerRadius", innerRadius, "m");
        return formatter.format();
    }
}
