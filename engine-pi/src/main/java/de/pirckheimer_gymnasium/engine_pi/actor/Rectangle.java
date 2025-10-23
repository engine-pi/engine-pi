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

import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.annotations.API;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;
import de.pirckheimer_gymnasium.engine_pi.physics.FixtureBuilder;
import de.pirckheimer_gymnasium.engine_pi.physics.FixtureData;

/**
 * Beschreibt ein <b>Rechteck</b>.
 *
 * <p>
 * Das Rechteck ist standardmäßig <b>rot</b> gefärbt. Die Farbe Rot stellt für
 * <a href="https://www.byk-instruments.com/de/color-determines-shape">Itten</a>
 * die körperhafte Materie dar. Sie wirkt statisch und schwer. Er ordnet deshalb
 * der Farbe die statische Form des Quadrates zu.
 * </p>
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
     * Die <b>Breite</b> des Rechtecks in Meter.
     */
    private double width;

    /**
     * Die <b>Höhe</b> des Rechtecks in Meter.
     */
    private double height;

    /**
     * Für abgerundete Ecken, Prozent der Abrundung der kleineren Seite
     */
    private double borderRadius;

    /**
     * Erzeugt ein <b>Quadrat</b> mit der Seitenlängen von <b>einem Meter</b>.
     *
     * @see de.pirckheimer_gymnasium.engine_pi.instant.Rectangle#Rectangle()
     * @see ActorAdder#addRectangle()
     */
    @API
    public Rectangle()
    {
        this(1, 1);
    }

    /**
     * Erzeugt ein <b>Quadrat</b> unter Angabe der <b>Seitenlänge</b>.
     *
     * @param sideLength Die <b>Seitenlänge</b> des Quadrats in Meter.
     *
     * @see de.pirckheimer_gymnasium.engine_pi.instant.Rectangle#Rectangle(double)
     * @see ActorAdder#addRectangle(double)
     *
     * @since 0.34.0
     */
    @API
    public Rectangle(double sideLength)
    {
        this(sideLength, sideLength);
    }

    /**
     * Erzeugt ein <b>Rechteck</b> durch Angabe der <b>Breite</b> und
     * <b>Höhe</b>.
     *
     * @param width Die <b>Breite</b> des Rechtecks in Meter.
     * @param height Die <b>Höhe</b> des Rechtecks in Meter.
     *
     * @see de.pirckheimer_gymnasium.engine_pi.instant.Rectangle#Rectangle(double,
     *     double)
     * @see ActorAdder#addRectangle(double, double)
     */
    @API
    public Rectangle(double width, double height)
    {
        this(width, height, () -> FixtureBuilder.rectangle(width, height));
    }

    /**
     * Erzeugt ein <b>Rechteck</b> durch Angabe der <b>Breite</b> und
     * <b>Höhe</b>.
     *
     * @param width Die <b>Breite</b> des Rechtecks in Meter.
     * @param height Die <b>Höhe</b> des Rechtecks in Meter.
     * @param shapeSupplier Eine Lamda-Funktion, die den <b>Umriss</b> liefert.
     */
    public Rectangle(double width, double height,
            Supplier<FixtureData> shapeSupplier)
    {
        super(shapeSupplier);
        assertPositiveWidthAndHeight(width, height);
        this.width = width;
        this.height = height;
        setColor(Resources.COLORS.getSafe("red"));
    }

    /**
     * Gibt die <b>Breite</b> des Rechtecks in Meter zurück.
     *
     * @return Die <b>Breite</b> des Rechtecks in Meter.
     */
    @API
    public double getWidth()
    {
        return width;
    }

    /**
     * Setzt die <b>Breite</b> des Rechtecks neu. Dadurch ändern sich die
     * physikalischen Eigenschaften (Masse etc.) des Rechtecks.
     *
     * @param width Die neue <b>Breite</b> für das Rechteck in Meter.
     */
    @API
    public void setWidth(double width)
    {
        setSize(width, getHeight());
    }

    /**
     * Gibt die <b>Höhe</b> des Rechtecks in Meter zurück.
     *
     * @return Die <b>Höhe</b> des Rechtecks in Meter.
     */
    @API
    public double getHeight()
    {
        return height;
    }

    /**
     * Setzt die <b>Höhe</b> des Rechtecks neu. Dadurch ändern sich die
     * physikalischen Eigenschaften (Masse etc.) des Rechtecks.
     *
     * @param height Die neue <b>Höhe</b> für das Rechteck in Meter.
     */
    @API
    public void setHeight(double height)
    {
        setSize(getWidth(), height);
    }

    /**
     * Setzt die <b>Höhe</b> und <b>Breite</b> des Rechtecks neu. Dadurch ändern
     * sich die physikalischen Eigenschaften (Masse etc.) des Rechtecks.
     *
     * @param width Die neue <b>Breite</b> für das Rechteck in Meter.
     * @param height Die neue <b>Höhe</b> für das Rechteck in Meter.
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

    @Internal
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
