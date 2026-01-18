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
package pi.graphics.geom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pi.graphics.geom.Vector.v;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class DirectedLineSegmentTest
{
    private DirectedLineSegment line(double aX, double aY, double bX, double bY)
    {
        return new DirectedLineSegment(v(aX, aY), v(bX, bY));
    }

    @Test
    public void difference()
    {
        assertEquals(v(5, 5), line(0, 0, 5, 5).difference());
        assertEquals(v(-5, -5), line(5, 5, 0, 0).difference());
    }

    @Test
    public void proportionalPoint()
    {
        DirectedLineSegment l = line(0, 0, 1, 1);
        assertEquals(v(1, 1), l.relativePoint(1));
        assertEquals(v(0, 0), l.relativePoint(0));
        assertEquals(v(2, 2), l.relativePoint(2));
        assertEquals(v(0.5, 0.5), l.relativePoint(0.5));
    }

    @Test
    public void distancePoint()
    {
        DirectedLineSegment l = line(0, 0, 1, 1);
        assertEquals(v(0, 0), l.fixedPoint(0));
        assertEquals(v(1, 1).normalize(), l.fixedPoint(1));
        assertEquals(v(1, 1).normalize().multiply(2), l.fixedPoint(2));

        DirectedLineSegment l2 = line(0, 0, 3, 4);
        assertEquals(v(0, 0), l2.fixedPoint(0));
        assertEquals(v(0.6, 0.8), l2.fixedPoint(1));
        assertEquals(v(1.2, 1.6), l2.fixedPoint(2));
    }

    @Test
    public void length()
    {
        assertEquals(1.4142135623730951, line(0, 0, 1, 1).length());
        assertEquals(1.4142135623730951, line(1, 1, 0, 0).length());
    }

    @Nested
    class AngleTest
    {
        @Test
        public void cardinal()
        {
            assertEquals(0.0, line(0, 0, 1, 0).angle(), 1e-12);
            assertEquals(90.0, line(0, 0, 0, 1).angle(), 1e-12);
            assertEquals(180.0, line(0, 0, -1, 0).angle(), 1e-12);
            assertEquals(-90.0, line(0, 0, 0, -1).angle(), 1e-12);
        }

        @Test
        public void diagonals()
        {
            assertEquals(45.0, line(0, 0, 1, 1).angle(), 1e-12);
            assertEquals(135.0, line(0, 0, -1, 1).angle(), 1e-12);
            assertEquals(-135.0, line(0, 0, -1, -1).angle(), 1e-12);
            assertEquals(-45.0, line(0, 0, 1, -1).angle(), 1e-12);
        }

        @Test
        public void arbitrary()
        {
            assertEquals(53.13010235415598, line(0, 0, 3, 4).angle(), 1e-12);
            assertEquals(-126.86989764584402,
                line(0, 0, -3, -4).angle(),
                1e-12);
            assertEquals(0.0, line(5, 5, 10, 5).angle(), 1e-12);
            assertEquals(180.0, line(10, 5, 5, 5).angle(), 1e-12);
        }
    }

}
