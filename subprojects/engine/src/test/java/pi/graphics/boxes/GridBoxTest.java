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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * @author Josef Friedrich
 */
public class GridBoxTest
{
    GridBox<DimensionBox> gridBox = new GridBox<>(DimensionBox.create(5));

    @Test
    void columns()
    {
        assertEquals(2, gridBox.columnCount());
    }

    @Test
    void rows()
    {
        assertEquals(3, gridBox.rowCount());
    }

    @Test
    void gridArray()
    {
        assertEquals(3, gridBox.grid.size());
        assertEquals(2, gridBox.grid.get(0).size());
    }

    @Test
    void getRow()
    {
        assertEquals(2, gridBox.getRow(0).size());
        assertEquals(2, gridBox.getRow(1).size());
        assertEquals(2, gridBox.getRow(2).size());
    }

    @Test
    void getColumn()
    {
        assertEquals(3, gridBox.getColumn(0).size());
        assertEquals(3, gridBox.getColumn(1).size());

        GridBox<Box> g2 = new GridBox<>(DimensionBox.create(27)).columns(4);
        assertEquals(7, g2.getColumn(0).size());
        assertEquals(7, g2.getColumn(1).size());
        assertEquals(7, g2.getColumn(2).size());
        assertEquals(7, g2.getColumn(3).size());
    }
}
