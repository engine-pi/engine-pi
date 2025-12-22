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

/**
 * Eine leere Abschlussbox, damit keine Null-Checks erforderlich sind.
 *
 * @since 0.40.0
 */
public class EmptyBox extends LeafBox
{

    public static EmptyBox[] create(int number)
    {
        EmptyBox[] boxes = new EmptyBox[number];
        for (int i = 0; i < number; i++)
        {
            boxes[i] = new EmptyBox();
        }
        return boxes;
    }

    @Override
    protected void calculateDimension()
    {
    }

    @Override
    void draw(Graphics2D g)
    {
    }

    @Override
    public String toString()
    {
        return getToStringFormatter().format();
    }

}
