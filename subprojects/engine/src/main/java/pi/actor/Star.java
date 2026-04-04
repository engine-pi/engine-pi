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

import pi.annotations.API;
import pi.annotations.ChainableMethod;
import pi.annotations.Getter;
import pi.annotations.Internal;
import pi.annotations.Setter;
import pi.debug.ToStringFormatter;
import pi.graphics.geom.Vector;

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/main-classes/actor/star.md

/**
 * Ein regelmäßiger <b>Stern</b>.
 *
 * Ein Stern wird durch die {@link Star#numPoints() Anzahl der Zacken}, einen
 * {@link Star#radius() äußeren Radius} und einen {@link Star#innerRadius()
 * inneren Radius} definiert.
 *
 * <h2>Standard-Werte</h2>
 * <ul>
 * <li>Anzahl der Zacken: 7</li>
 * <li>Äußerer Radius: 2 Meter</li>
 * <li>Innerer Radius: 1 Meter</li>
 * </ul>
 *
 * <h2>Verwendungsbeispiel</h2>
 *
 * <pre>{@code
 * Star star = new Star();
 * star.numPoints(5).radius(3).innerRadius(1.5);
 * }</pre>
 *
 * @author Josef Friedrich
 */
public class Star extends Polygon
{
    private static final int DEFAULT_NUM_POINTS = 7;

    private static final double DEFAULT_RADIUS = 2;

    private static final double DEFAULT_INNER_RADIUS = 1;

    /**
     * Erstellt einen Stern mit einem <b>äußeren Radius vom 2 Meter</b>, einem
     * <b>innere Radius von 1 Meter</b> und <b>7 Zacken</b>.
     */
    public Star()
    {
        this(DEFAULT_NUM_POINTS, DEFAULT_RADIUS, DEFAULT_INNER_RADIUS);
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
        update();
    }

    /* numPoints */

    /**
     * Die <b>Anzahl der Zacken</b> des Sterns.
     */
    private int numPoints = DEFAULT_NUM_POINTS;

    /**
     * Gibt die <b>Anzahl der Zacken</b> des Sterns zurück.
     *
     * @return Die <b>Anzahl der Zacken</b>.
     *
     * @since 0.45.0
     */
    @API
    @Getter
    public int numPoints()
    {
        return numPoints;
    }

    /**
     * Setzt die <b>Anzahl der Zacken</b> des Sterns.
     *
     * @param numPoints Die neue <b>Anzahl der Zacken</b>.
     *
     * @since 0.45.0
     */
    @API
    @Setter
    @ChainableMethod
    public Star numPoints(int numPoints)
    {
        this.numPoints = numPoints;
        update();
        return this;
    }

    /* radius */

    /**
     * Der <b>äußere Radius</b> des Sterns.
     */
    private double radius = DEFAULT_RADIUS;

    /**
     * Gibt den <b>äußeren Radius</b> des Sterns zurück.
     *
     * @return Der äußere Radius in Metern.
     *
     * @since 0.45.0
     */

    @API
    @Getter
    public double radius()
    {
        return radius;
    }

    /**
     * Setzt den <b>äußeren Radius</b> des Sterns.
     *
     * @param radius Der neue äußere Radius in Metern.
     *
     * @since 0.45.0
     */
    @API
    @Setter
    @ChainableMethod
    public Star radius(double radius)
    {
        this.radius = radius;
        update();
        return this;
    }

    /* innerRadius */

    /**
     * Der <b>innere Radius</b> des Sterns.
     */
    private double innerRadius = DEFAULT_INNER_RADIUS;

    /**
     * Gibt den <b>inneren Radius</b> des Sterns zurück.
     *
     * @return Der innere Radius in Metern.
     *
     * @since 0.45.0
     */
    @API
    @Getter
    public double innerRadius()
    {
        return innerRadius;
    }

    /**
     * Setzt den <b>inneren Radius</b> des Sterns.
     *
     * @param innerRadius Der neue innere Radius in Metern.
     *
     * @since 0.45.0
     */
    @API
    @Setter
    @ChainableMethod
    public Star innerRadius(double innerRadius)
    {
        this.innerRadius = innerRadius;
        update();
        return this;
    }

    /**
     * @hidden
     */
    @Internal
    public void update()
    {
        points(Star.calculateVectors(numPoints, radius, innerRadius));
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
        ToStringFormatter formatter = new ToStringFormatter(this);
        formatter.append("numPoints", numPoints);
        formatter.append("radius", radius, "m");
        formatter.append("innerRadius", innerRadius, "m");
        return formatter.format();
    }
}
