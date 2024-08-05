/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/actor/Circle.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2018 Michael Andonie and contributors.
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
package de.pirckheimer_gymnasium.engine_pi.actor;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.jbox2d.collision.shapes.CircleShape;
import de.pirckheimer_gymnasium.jbox2d.collision.shapes.Shape;

import de.pirckheimer_gymnasium.engine_pi.annotations.API;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;
import de.pirckheimer_gymnasium.engine_pi.physics.FixtureData;

/**
 * Beschreibt einen <b>Kreis</b>.
 *
 * @author Michael Andonie
 * @author Niklas Keller
 *
 * @see Rectangle
 * @see Triangle
 */
public class Circle extends Geometry
{
    private double diameter;

    /**
     * Erzeugt einen <b>Kreis</b> durch Angabe des <b>Durchmessers</b>.
     *
     * @param diameter Der Durchmesser des Kreises.
     */
    public Circle(double diameter)
    {
        super(() -> new FixtureData(createCircleShape(diameter)));
        this.diameter = diameter;
        // https://www.byk-instruments.com/de/color-determines-shape
        // Die Farbe Blau dagegen wirkt für Itten rund, erweckt ein Gefühl der
        // Entspanntheit und Bewegung und steht für den "in sich bewegten
        // Geist", wie er sich ausdrückt. Der Kreis entspricht der Farbe Blau,
        // da er ein Symbol der "stetigen Bewegung" darstelle.
        setColor("blue");
    }

    /**
     * Erzeugt einen <b>Kreis</b> mit <b>einem Meter Durchmesser</b>.
     *
     * @author Josef Friedrich
     */
    public Circle()
    {
        this(1);
    }

    /**
     * Gibt den <b>Durchmesser</b> des Kreises aus.
     *
     * @return Der Durchmesser des Kreises.
     */
    @API
    public double getDiameter()
    {
        return diameter;
    }

    /**
     * Gibt den <b>Radius</b> des Kreises aus.
     *
     * @return Der Radius des Kreises.
     */
    @API
    public double getRadius()
    {
        return diameter / 2;
    }

    /**
     * Zeichnet die Figur an der Position {@code (0|0)} mit der Rotation
     * {@code 0}.
     *
     * @param g Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
     */
    @Override
    public void render(Graphics2D g, double pixelPerMeter)
    {
        g.setColor(getColor());
        g.fillOval(0, -(int) (diameter * pixelPerMeter),
                (int) (diameter * pixelPerMeter),
                (int) (diameter * pixelPerMeter));
    }

    /**
     * Setzt den Radius des Kreises neu. Ändert damit die physikalischen
     * Eigenschaften des Objekts.
     *
     * @param radius Der neue Radius des Kreises.
     */
    @API
    public void resetRadius(double radius)
    {
        this.diameter = 2 * radius;
        FixtureData[] fixtureData = this.getPhysicsHandler().getPhysicsData()
                .generateFixtureData();
        FixtureData thatoneCircle = fixtureData[0];
        thatoneCircle.setShape(createCircleShape(this.diameter));
        this.setFixture(() -> thatoneCircle);
    }

    @Internal
    private static Shape createCircleShape(double diameter)
    {
        CircleShape shape = new CircleShape();
        shape.radius = (float) diameter / 2;
        shape.p.set(shape.radius, shape.radius);
        return shape;
    }
}
