/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2026 Josef Friedrich and contributors.
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
package pi.graphics.boxes;

import static pi.Controller.colors;

import java.awt.Color;
import java.awt.Graphics2D;

import pi.annotations.Setter;

/**
 * Eine <b>Ellipse</b>.
 *
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public class EllipseBox extends LeafBox
{
    /**
     * Erzeugt eine <b>Ellipse</b> durch Angabe der <b>Breite</b> und
     * <b>Höhe</b> in Pixel.
     *
     * @param width Die <b>Breite</b> der Ellipse in Pixel.
     * @param height Die <b>Höhe</b> der Ellipse in Pixel.
     *
     * @since 0.42.0
     */
    public EllipseBox(int width, int height)
    {
        super();
        supportsDefinedDimension = true;
        definedWidth = width;
        definedHeight = height;
    }

    /**
     * Setzt die <b>Breite</b> der Ellipse in Pixel.
     *
     * @param width Die <b>Breite</b> der Ellipse in Pixel.
     *
     * @return Eine Referenz auf die eigene Instanz der Ellipse, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Ellipse durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code ellipse.width(..).height(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    public EllipseBox width(int width)
    {
        definedWidth = width;
        return this;
    }

    /**
     * Setzt die <b>Höhe</b> der Ellipse in Pixel.
     *
     * @param height Die <b>Höhe</b> der Ellipse in Pixel.
     *
     * @return Eine Referenz auf die eigene Instanz der Ellipse, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Ellipse durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code ellipse.width(..).height(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    public EllipseBox height(int height)
    {
        definedHeight = height;
        return this;
    }

    /**
     * Die <b>Farbe</b> der Ellipse.
     *
     * @since 0.42.0
     */
    Color color = null;

    /**
     * Setzt die <b>Farbe</b> der Ellipse als Zeichenkette.
     *
     * @param color Ein Farbname ({@link pi.resources.color.ColorContainer siehe
     *     Auflistung}) oder eine Farbe in hexadezimaler Codierung (z.B.
     *     {@code #ff0000}).
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    public EllipseBox color(String color)
    {
        this.color = colors.get(color);
        return this;
    }

    /**
     * Setzt die <b>Farbe</b> der Ellipse als Objekt der Klasse {@link Color}.
     *
     * @param color Die <b>Farbe</b> der Ellipse.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    public EllipseBox color(Color color)
    {
        this.color = color;
        return this;
    }

    /**
     * @hidden
     */
    @Override
    protected void calculateDimension()
    {
        width = definedWidth;
        height = definedHeight;
    }

    /**
     * @hidden
     */
    @Override
    void draw(Graphics2D g)
    {
        g.drawOval(x, y, definedWidth, definedHeight);
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        return toStringFormatter().format();
    }
}
