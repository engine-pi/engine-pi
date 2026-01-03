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

import pi.annotations.Setter;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/graphics/boxes/VerticalBoxDemo.java

/**
 * Eine <b>vertikale</b> Box, die die enthaltenen Kinder-Boxen vertikal von oben
 * nach unten anordnet.
 *
 * @author Josef Friedrich
 *
 * @since 0.38.0
 */
public class VerticalBox<T extends Box> extends PaddingBox<T>
{
    /**
     * Erzeugt eine neue <b>vertikale</b> Box.
     *
     * @param childs Die Kinder-Boxen, die <b>vertikal</b> von oben nach unten
     *     angeordnet werden sollen.
     *
     * @since 0.38.0
     */
    public VerticalBox(Box... childs)
    {
        super(childs);
    }

    @Override
    protected void calculateDimension()
    {
        // Die Methode wird mehrmals ausgeführt, falls calculateDimensionTwice
        // gesetzt ist.
        width = 0;
        height = 0;
        int maxWidth = 0;
        for (Box child : childs)
        {
            if (child.width > maxWidth)
            {
                maxWidth = child.width;
            }
            height += child.height;
        }

        for (Box child : childs)
        {
            child.width(maxWidth);
        }

        width = maxWidth + 2 * padding;
        height += (numberOfChilds() + 1) * padding;
    }

    @Override
    protected void calculateAnchors()
    {
        int yCursor = y + padding;
        for (Box child : childs)
        {
            child.x = x + padding;
            child.y = yCursor;
            yCursor += child.height + padding;
        }
    }

    @Setter
    public VerticalBox<T> hAlign(HAlign hAlgin)
    {
        forEachCell(cell -> cell.cell.hAlign(hAlgin));
        return this;
    }

    @Override
    public String toString()
    {
        return toStringFormatter().format();
    }
}
