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

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/graphics/boxes/HorizontalBoxDemo.java

/**
 * Eine <b>horizontale</b> Box, die die enthaltenen Kinder-Boxen horizontal von
 * links nach rechts anordnet.
 *
 * @author Josef Friedrich
 *
 * @since 0.39.0
 */
public class HorizontalBox extends PaddingBox
{
    /**
     * Erzeugt eine neue <b>horizontale</b> Box.
     *
     * @param childs Die Kinder-Boxen, die <b>horizontal</b> von links nach
     *     rechts angeordnet werden sollen.
     *
     * @see Boxes#horizontal(Box...)
     *
     * @since 0.39.0
     */
    public HorizontalBox(Box... childs)
    {
        super(childs);
    }

    @Override
    protected void calculateDimension()
    {
        int maxHeight = 0;
        for (Box child : childs)
        {
            width += child.width;
            if (child.height > maxHeight)
            {
                maxHeight = child.height;
            }
        }

        width += (numberOfChilds() + 1) * padding;
        height = maxHeight + 2 * padding;
    }

    @Override
    protected void calculateAnchors()
    {
        int xCursor = x + padding;
        for (Box child : childs)
        {
            child.x = xCursor;
            child.y = y + padding;
            xCursor += child.width + padding;
        }
    }
}
