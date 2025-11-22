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
package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/graphics/boxes/VerticalBoxDemo.java

/**
 * Eine <b>vertikale</b> Box, die die enthaltenen Kinder-Boxen vertikal von oben
 * nach unten anordnet.
 *
 * @author Josef Friedrich
 *
 * @since 0.38.0
 */
public class VerticalBox extends PaddingBox
{
    /**
     * Erzeugt eine neue <b>vertikale</b> Box.
     *
     * @param childs Die Kinder-Boxen, die <b>vertikal</b> von oben nach unten
     *     angeordnet werden sollen.
     *
     * @since 0.38.0
     *
     * @see Boxes#vertical(Box...)
     */
    public VerticalBox(Box... childs)
    {
        super(childs);
    }

    @Override
    protected void calculateDimension()
    {
        int maxWidth = 0;
        for (Box child : childs)
        {
            child.calculateDimension();
            if (child.width > maxWidth)
            {
                maxWidth = child.width;
            }
            height += child.height;
        }
        width = maxWidth;
    }

    @Override
    protected void calculateAnchors()
    {
        int yCursor = y;
        for (Box child : childs)
        {
            child.x = x;
            child.y = yCursor;
            yCursor += child.height;
            child.calculateAnchors();
        }
    }
}
