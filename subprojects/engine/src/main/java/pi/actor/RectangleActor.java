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
package pi.actor;

import java.awt.Graphics2D;
import java.util.function.Supplier;

import pi.Resources;
import pi.annotations.API;
import pi.annotations.Getter;
import pi.annotations.Internal;
import pi.annotations.Setter;
import pi.physics.FixtureBuilder;
import pi.physics.FixtureData;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/docs/development/design.md

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
 * @author Josef Friedrich
 *
 * @see CircleActor
 * @see TriangleActor
 * @see pi.Rectangle
 */
public abstract class RectangleActor extends Geometry
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
     * Erzeugt ein <b>Rechteck</b> durch Angabe der <b>Breite</b> und
     * <b>Höhe</b>.
     *
     * @param width Die <b>Breite</b> des Rechtecks in Meter.
     * @param height Die <b>Höhe</b> des Rechtecks in Meter.
     */
    @API
    public RectangleActor(double width, double height)
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
    public RectangleActor(double width, double height,
            Supplier<FixtureData> shapeSupplier)
    {
        super(shapeSupplier);
        assertPositiveWidthAndHeight(width, height);
        this.width = width;
        this.height = height;
        color(Resources.colors.getSafe("red"));
    }

    /**
     * Gibt die <b>Breite</b> des Rechtecks in Meter zurück.
     *
     * @return Die <b>Breite</b> des Rechtecks in Meter.
     */
    @API
    @Getter
    public double width()
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
    @Setter
    public void width(double width)
    {
        size(width, height());
    }

    /**
     * Gibt die <b>Höhe</b> des Rechtecks in Meter zurück.
     *
     * @return Die <b>Höhe</b> des Rechtecks in Meter.
     */
    @API
    @Getter
    public double height()
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
    @Setter
    public void height(double height)
    {
        size(width(), height);
    }

    /**
     * Setzt die <b>Höhe</b> und <b>Breite</b> des Rechtecks neu. Dadurch ändern
     * sich die physikalischen Eigenschaften (Masse etc.) des Rechtecks.
     *
     * @param width Die neue <b>Breite</b> für das Rechteck in Meter.
     * @param height Die neue <b>Höhe</b> für das Rechteck in Meter.
     */
    @API
    @Setter
    public void size(double width, double height)
    {
        assertPositiveWidthAndHeight(width, height);
        this.width = width;
        this.height = height;
        this.fixture(() -> FixtureBuilder.rectangle(width, height));
    }

    @API
    @Getter
    public double borderRadius()
    {
        return borderRadius;
    }

    @API
    @Setter
    public void borderRadius(double percent)
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
     * @hidden
     */
    @Internal
    @Override
    public void render(Graphics2D g, double pixelPerMeter)
    {
        g.setColor(color());
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
