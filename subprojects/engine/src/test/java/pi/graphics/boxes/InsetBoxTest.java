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
package pi.graphics.boxes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public class InsetBoxTest
{
    InsetBox inset;

    @BeforeEach
    void beforeEach()
    {
        inset = new InsetBox(new DimensionBox(42, 23)).top(1)
            .right(2)
            .bottom(3)
            .left(4);
    }

    @Test
    void top()
    {
        InsetBox result = inset.top(1);
        assertEquals(1, inset.top());
        assertEquals(result, inset);
    }

    @Test
    void right()
    {
        InsetBox result = inset.right(2);
        assertEquals(2, inset.right());
        assertEquals(result, inset);
    }

    @Test
    void bottom()
    {
        InsetBox result = inset.bottom(3);
        assertEquals(3, inset.bottom());
        assertEquals(result, inset);
    }

    @Test
    void left()
    {
        InsetBox result = inset.left(4);
        assertEquals(4, inset.left());
        assertEquals(result, inset);
    }

    @Test
    void allSides()
    {
        InsetBox result = inset.allSides(10);
        assertEquals(10, inset.top());
        assertEquals(10, inset.right());
        assertEquals(10, inset.bottom());
        assertEquals(10, inset.left());
        assertEquals(result, inset);
    }

    @Test
    void width()
    {
        assertEquals(0, inset.width());
        inset.measure();
        assertEquals(48, inset.width());
    }

    @Test
    void height()
    {
        assertEquals(0, inset.height());
        inset.measure();
        assertEquals(27, inset.height());
    }

    @Test
    void testToStringFormatter()
    {
        assertEquals("InsetBox [top=1, right=2, bottom=3, left=4]",
            inset.toString(true));

        inset.measure();
        assertEquals(
            "InsetBox [top=1, right=2, bottom=3, left=4, width=48, height=27]",
            inset.toString(true));
    }
}
