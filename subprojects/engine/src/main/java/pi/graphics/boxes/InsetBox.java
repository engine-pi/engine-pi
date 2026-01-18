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

import pi.annotations.Getter;
import pi.annotations.Setter;
import pi.debug.ToStringFormatter;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/graphics/boxes/InsetBoxDemo.java

/**
 * Ein <b>Außenabstand</b> um die enthaltene Kind-Box.
 *
 * @author Josef Friedrich
 *
 * @since 0.40.0
 *
 * @see java.awt.Insets
 */
public class InsetBox extends ChildBox
{

    public InsetBox()
    {
    }

    /**
     * Erzeugt einen neuen <b>Außenabstand</b> durch die Angabe der enthaltenen
     * Kind-Box. Rahmen
     *
     * @param child Die <b>Kind-Box</b>, die umrahmt werden soll.
     *
     * @since 0.40.0
     */
    public InsetBox(Box child)
    {
        super(child);
    }

    /* In der Reihenfolge der CSS padding oder margin Attribute */

    /* */

    int top = 0;

    /**
     * Gibt den <b>oberen</b> Außenabstand in Pixel zurück.
     *
     * @return Der <b>obere</b> Außenabstand in Pixel.
     *
     * @since 0.42.0
     */
    @Getter
    public int top()
    {
        return top;
    }

    /**
     * Setzt den <b>oberen</b> Außenabstand in Pixel.
     *
     * @param top Der <b>obere</b> Außenabstand in Pixel.
     *
     * @return Eine Referenz auf die eigene Instanz der Außenabstand-Box, damit
     *     nach dem Erbauer/Builder-Entwurfsmuster die Eigenschaften der
     *     Außenabstand-Box durch aneinander gekettete Setter festgelegt werden
     *     können, z.B. {@code inset.top(..).right(..)}.
     */
    @Setter
    public InsetBox top(int top)
    {
        this.top = top;
        return this;
    }

    /* right */

    int right = 0;

    /**
     * Gibt den <b>rechten</b> Außenabstand in Pixel zurück.
     *
     * @return Der <b>rechten</b> Außenabstand in Pixel.
     *
     * @since 0.42.0
     */
    @Getter
    public int right()
    {
        return right;
    }

    /**
     * Setzt den <b>rechten</b> Außenabstand in Pixel.
     *
     * @param right Der <b>rechten</b> Außenabstand in Pixel.
     *
     * @return Eine Referenz auf die eigene Instanz der Außenabstand-Box, damit
     *     nach dem Erbauer/Builder-Entwurfsmuster die Eigenschaften der
     *     Außenabstand-Box durch aneinander gekettete Setter festgelegt werden
     *     können, z.B. {@code inset.top(..).right(..)}.
     */
    @Setter
    public InsetBox right(int right)
    {
        this.right = right;
        return this;
    }

    /* bottom */

    int bottom = 0;

    /**
     * Gibt den <b>unteren</b> Außenabstand in Pixel zurück.
     *
     * @return Der <b>unteren</b> Außenabstand in Pixel.
     *
     * @since 0.42.0
     */
    @Getter
    public int bottom()
    {
        return bottom;
    }

    /**
     * Setzt den <b>unteren</b> Außenabstand in Pixel.
     *
     * @param bottom Der <b>unteren</b> Außenabstand in Pixel.
     *
     * @return Eine Referenz auf die eigene Instanz der Außenabstand-Box, damit
     *     nach dem Erbauer/Builder-Entwurfsmuster die Eigenschaften der
     *     Außenabstand-Box durch aneinander gekettete Setter festgelegt werden
     *     können, z.B. {@code inset.top(..).right(..)}.
     */
    @Setter
    public InsetBox bottom(int bottom)
    {
        this.bottom = bottom;
        return this;
    }

    /* left */

    int left = 0;

    /**
     * Gibt den <b>linken</b> Außenabstand in Pixel zurück.
     *
     * @return Der <b>linken</b> Außenabstand in Pixel.
     *
     * @since 0.42.0
     */
    @Getter
    public int left()
    {
        return left;
    }

    /**
     * Setzt den <b>linken</b> Außenabstand in Pixel.
     *
     * @param left Der <b>linken</b> Außenabstand in Pixel.
     *
     * @return Eine Referenz auf die eigene Instanz der Außenabstand-Box, damit
     *     nach dem Erbauer/Builder-Entwurfsmuster die Eigenschaften der
     *     Außenabstand-Box durch aneinander gekettete Setter festgelegt werden
     *     können, z.B. {@code inset.top(..).right(..)}.
     */
    @Setter
    public InsetBox left(int left)
    {
        this.left = left;
        return this;
    }

    /* allSides */

    /**
     * Setzt den Außenabstand <b>aller Seiten</b> in Pixel.
     *
     * @param margin Der Außenabstand <b>aller Seiten</b> in Pixel.
     *
     * @return Eine Referenz auf die eigene Instanz der Außenabstand-Box, damit
     *     nach dem Erbauer/Builder-Entwurfsmuster die Eigenschaften der
     *     Außenabstand-Box durch aneinander gekettete Setter festgelegt werden
     *     können, z.B. {@code inset.top(..).right(..)}.
     *
     * @since 0.40.0
     */
    @Setter
    public InsetBox allSides(int margin)
    {
        top = margin;
        right = margin;
        bottom = margin;
        left = margin;
        return this;
    }

    /**
     * @hidden
     */
    @Override
    protected void calculateDimension()
    {
        width = left + child.width + right;
        height = top + child.height + bottom;
    }

    /**
     * @hidden
     */
    @Override
    protected void calculateAnchors()
    {
        child.x = x + left;
        child.y = y + top;
    }

    /**
     * @hidden
     */
    @Override
    void draw(Graphics2D g)
    {
        // do nothing
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        ToStringFormatter formatter = toStringFormatter();

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
