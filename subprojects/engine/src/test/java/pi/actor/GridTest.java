/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2026 Josef Friedrich and contributors.
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
package pi.actor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import pi.Controller;

public class GridTest
{
    private Grid grid;

    @BeforeEach
    public void setUp()
    {
        Controller.instantMode(false);
        grid = new Grid(3, 4);
    }

    @Nested
    class ColsTest
    {

    }

    @Test
    void getterReturnsConstructorValue()
    {
        assertEquals(3, grid.cols());
    }

    @Test
    void setterReturnsSameInstanceAndUpdatesValue()
    {
        Grid returned = grid.cols(7);

        assertSame(grid, returned);
        assertEquals(7, grid.cols());
    }

    @Nested
    class RowsTest
    {
        @Test
        void getterReturnsConstructorValue()
        {
            assertEquals(4, grid.rows());
        }

        @Test
        void setterReturnsSameInstanceAndUpdatesValue()
        {
            Grid returned = grid.rows(8);

            assertSame(grid, returned);
            assertEquals(8, grid.rows());
        }
    }

    @Nested
    class SizeTest
    {
        @Test
        void defaultIsOneMeter()
        {
            assertEquals(1, grid.size(), 0.001);
        }

        @Test
        void constructorValueIsReturnedByGetter()
        {
            Grid custom = new Grid(3, 4, 2.5);
            assertEquals(2.5, custom.size(), 0.001);
        }

        @Test
        void setterReturnsSameInstanceAndUpdatesValue()
        {
            Grid returned = grid.size(3.75);

            assertSame(grid, returned);
            assertEquals(3.75, grid.size(), 0.001);
        }
    }

    @Nested
    class lineThicknessTest
    {
        @Test
        void defaultIsTwoHundredthMeter()
        {
            assertEquals(0.02, grid.lineThickness(), 0.001);
        }

        @Test
        void setterReturnsSameInstanceAndUpdatesValue()
        {
            Grid returned = grid.lineThickness(0.5);

            assertSame(grid, returned);
            assertEquals(0.5, grid.lineThickness(), 0.001);
        }

    }

    @Nested
    class BackgroundTest
    {
        @Test
        void isNullByDefault()
        {
            assertNull(grid.background());
        }

        @Test
        void setterUpdatesValue()
        {
            Color color = Color.BLUE;

            grid.background(color);

            assertSame(color, grid.background());
        }
    }

}
