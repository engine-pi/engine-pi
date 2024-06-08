/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/actor/Actor.java
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
import java.util.function.Supplier;

import de.pirckheimer_gymnasium.engine_pi.annotations.API;
import de.pirckheimer_gymnasium.engine_pi.physics.FixtureBuilder;
import de.pirckheimer_gymnasium.engine_pi.physics.FixtureData;
import de.pirckheimer_gymnasium.engine_pi.resources.Container;

/**
 * Beschreibt ein Rechteck.
 *
 * @author Michael Andonie
 * @author Niklas Keller
 *
 * @see Circle
 * @see Triangle
 */
public class Rectangle extends Geometry
{
    /**
     * Die Breite des Rechtecks in Meter.
     */
    private double width;

    /**
     * Die Höhe des Rechtecks in Meter.
     */
    private double height;

    /**
     * Für abgerundete Ecken, Prozent der Abrundung der kleineren Seite
     */
    private double borderRadius;

    /**
     * Erzeugt ein Rechteck durch Angabe der Breite und der Höhe.
     *
     * @param width  Die Breite des Rechtecks.
     * @param height Die Höhe des Rechtecks.
     *
     * @see ActorCreator#createRectangle(double, double, double, double)
     * @see ActorCreator#createRectangle(double, double)
     */
    public Rectangle(double width, double height)
    {
        this(width, height, () -> FixtureBuilder.rectangle(width, height));
        setColor(Container.colors.getBlue());
    }

    public Rectangle(double width, double height,
            Supplier<FixtureData> shapeSupplier)
    {
        super(shapeSupplier);
        assertPositiveWidthAndHeight(width, height);
        this.width = width;
        this.height = height;
    }

    /**
     * Erzeugt ein Quadrat mit der Seitenlängen von einem Meter.
     */
    public Rectangle()
    {
        this(1, 1);
    }

    /**
     * Gibt die Breite des Rechtecks in Meter zurück.
     *
     * @return Die Breite des Rechtecks in Meter.
     */
    @API
    public double getWidth()
    {
        return width;
    }

    /**
     * Setzt die Breite des Rechtecks neu. Dadurch ändern sich die
     * physikalischen Eigenschaften (Masse etc.) des Rechtecks.
     *
     * @param width Die neue Breite für das Rechteck in Meter.
     */
    @API
    public void setWidth(double width)
    {
        setSize(width, getHeight());
    }

    /**
     * Gibt die Höhe des Rechtecks in Meter zurück.
     *
     * @return Die Höhe des Rechtecks in Meter.
     */
    @API
    public double getHeight()
    {
        return height;
    }

    /**
     * Setzt die Höhe des Rechtecks neu. Dadurch ändern sich die physikalischen
     * Eigenschaften (Masse etc.) des Rechtecks.
     *
     * @param height Die neue Höhe für das Rechteck in Meter.
     */
    @API
    public void setHeight(double height)
    {
        setSize(getWidth(), height);
    }

    /**
     * Setzt die Höhe und Breite des Rechtecks neu. Dadurch ändern sich die
     * physikalischen Eigenschaften (Masse etc.) des Rechtecks.
     *
     * @param width  Die neue Breite für das Rechteck in Meter.
     * @param height Die neue Höhe für das Rechteck in Meter.
     */
    @API
    public void setSize(double width, double height)
    {
        assertPositiveWidthAndHeight(width, height);
        this.width = width;
        this.height = height;
        this.setFixture(() -> FixtureBuilder.rectangle(width, height));
    }

    @API
    public double getBorderRadius()
    {
        return borderRadius;
    }

    @API
    public void setBorderRadius(double percent)
    {
        if (percent < 0 || percent > 1)
        {
            throw new IllegalArgumentException(
                    "Borderradius kann nur zwischen 0 und 1 sein. War "
                            + percent);
        }
        this.borderRadius = percent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(Graphics2D g, double pixelPerMeter)
    {
        g.setColor(getColor());
        if (borderRadius == 0)
        {
            g.fillRect(0, (int) (-height * pixelPerMeter),
                    (int) (width * pixelPerMeter),
                    (int) (height * pixelPerMeter));
        }
        else
        {
            int borderRadius = (int) (Math.min(width, height) * pixelPerMeter
                    * this.borderRadius);
            g.fillRoundRect(0, (int) (-height * pixelPerMeter),
                    (int) (width * pixelPerMeter),
                    (int) (height * pixelPerMeter), borderRadius, borderRadius);
        }
    }
}
