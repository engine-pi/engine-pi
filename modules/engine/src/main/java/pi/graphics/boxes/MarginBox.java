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

import java.awt.Graphics2D;

import pi.debug.ToStringFormatter;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/graphics/boxes/MarginBoxDemo.java

/**
 * Ein <b>Außenabstand</b> um die enthaltene Kind-Box.
 *
 * @author Josef Friedrich
 *
 * @since 0.40.0
 */
public class MarginBox extends ChildBox
{
    int top = 0;

    int right = 0;

    int bottom = 0;

    int left = 0;

    /**
     * Erzeugt einen neuen <b>Außenabstand</b> durch die Angabe der enthaltenen
     * Kind-Box. Rahmen
     *
     * @param child Die <b>Kind-Box</b>, die umrahmt werden soll.
     *
     * @since 0.40.0
     */
    public MarginBox(Box child)
    {
        super(child);
    }

    /* Setter */

    /**
     * Setzt den <b>Außenabstand</b> in Pixel.
     *
     * @param margin Der <b>Außenabstand</b> in Pixel.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.40.0
     */
    public MarginBox allSides(int margin)
    {
        top = margin;
        right = margin;
        bottom = margin;
        left = margin;
        return this;
    }

    public MarginBox top(int top)
    {
        this.top = top;
        return this;
    }

    public MarginBox right(int right)
    {
        this.right = right;
        return this;
    }

    public MarginBox bottom(int bottom)
    {
        this.bottom = bottom;
        return this;
    }

    public MarginBox left(int left)
    {
        this.left = left;
        return this;
    }

    @Override
    protected void calculateDimension()
    {
        width = left + child.width + right;
        height = top + child.height + bottom;
    }

    @Override
    protected void calculateAnchors()
    {
        child.x = x + left;
        child.y = y + top;
    }

    @Override
    void draw(Graphics2D g)
    {
        // do nothing
    }

    @Override
    public String toString()
    {
        ToStringFormatter formatter = getToStringFormatter();

        if (left > 0)
        {
            formatter.prepend("left", left);
        }

        if (bottom > 0)
        {
            formatter.prepend("bottom", bottom);
        }

        if (right > 0)
        {
            formatter.prepend("right", right);
        }

        if (top > 0)
        {
            formatter.prepend("top", top);
        }
        return formatter.format();
    }
}
