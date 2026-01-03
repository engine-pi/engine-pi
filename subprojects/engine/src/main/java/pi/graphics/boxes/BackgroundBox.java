/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
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

import static pi.Resources.colors;

import java.awt.Color;
import java.awt.Graphics2D;

import pi.annotations.Setter;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/graphics/boxes/BackgroundBoxDemo.java

/**
 * Unterlegt eine Kind-Box mit einer <b>Hintergrundfarbe</b>.
 *
 * @since 0.40.0
 */
public class BackgroundBox extends ChildBox
{
    /**
     * Die <b>Hintergrundfarbe</b>.
     *
     * @since 0.38.0
     */
    Color color = null;

    public BackgroundBox()
    {
    }

    public BackgroundBox(Box child)
    {
        super(child);
    }

    /* Setter */

    /**
     * Setzt die <b>Hintergrundfarbe</b>.
     *
     * @param color Die <b>Hintergrundfarbe</b>.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.40.0
     */
    public BackgroundBox color(Color color)
    {
        this.color = color;
        return this;
    }

    /**
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     */
    @Setter
    public BackgroundBox color(String color)
    {
        this.color = colors.get(color);
        return this;
    }
    /* Getter */

    @Override
    protected void calculateDimension()
    {
        width = child.width;
        height = child.height;
    }

    @Override
    protected void calculateAnchors()
    {
        child.x = x;
        child.y = y;
    }

    @Override
    void draw(Graphics2D g)
    {
        if (color != null)
        {
            Color oldColor = g.getColor();
            g.setColor(color);
            g.fillRect(x, y, width, height);
            g.setColor(oldColor);
        }
    }

    @Override
    public String toString()
    {
        var formatter = toStringFormatter();
        if (color != null)
        {
            formatter.prepend("color", color);
        }
        return formatter.format();
    }
}
