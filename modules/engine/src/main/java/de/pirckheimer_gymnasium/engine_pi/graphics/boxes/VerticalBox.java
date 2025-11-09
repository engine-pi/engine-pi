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
 * Ordnet die enthaltenen Kindboxen <b>vertikal</b> von oben nach unten an.
 *
 * @author Josef Friedrich
 *
 * @since 0.38.0
 */
public class VerticalBox extends MultipleChildBoxContainer
{
    /**
     *
     * @param childs
     *
     * @since 0.38.0
     */
    public VerticalBox(Box... childs)
    {
        super(childs);
    }

    @Override
    int width()
    {
        int maxWidth = 0;
        for (Box box : childs)
        {
            if (box.width() > maxWidth)
            {
                maxWidth = box.width();
            }
        }
        return maxWidth;
    }

    @Override
    int height()
    {
        int height = 0;
        for (Box box : childs)
        {
            height += box.height();
        }
        return height;
    }

    @Override
    void calculateAnchors()
    {
        int yCursor = y;
        for (Box child : childs)
        {
            child.x = x;
            child.y = yCursor;
            yCursor += child.height();
            child.calculateAnchors();
        }
    }

}
