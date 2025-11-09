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

/**
 * Eine <b>horizontale</b> Box, die die enthaltenen Kinder-Boxen horizontal von
 * links nach rechts anordnet.
 *
 * @author Josef Friedrich
 *
 * @since 0.39.0
 */
public class HorizontalBox extends MultipleChildBoxContainer
{
    /**
     * Erzeugt eine neue <b>horizontale</b> Box.
     *
     * @param childs Die Kinder-Boxen, die <b>horizontal</b> von links nach
     *     rechts angeordnet werden sollen.
     *
     * @see Box#horizontal(Box...)
     *
     * @since 0.39.0
     */
    public HorizontalBox(Box... childs)
    {
        super(childs);
    }

    @Override
    int width()
    {
        int width = 0;
        for (Box child : childs)
        {
            width += child.width();
        }
        return width;
    }

    @Override
    int height()
    {
        int maxHeight = 0;
        for (Box child : childs)
        {
            if (child.height() > maxHeight)
            {
                maxHeight = child.height();
            }
        }
        return maxHeight;
    }

    @Override
    void calculateAnchors()
    {
        int xCursor = x();
        for (Box child : childs)
        {
            child.x = xCursor;
            child.y = y();
            xCursor += child.width();
            child.calculateAnchors();
        }
    }
}
