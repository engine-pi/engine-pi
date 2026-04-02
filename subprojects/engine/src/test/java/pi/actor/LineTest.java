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
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pi.Controller;
import pi.graphics.geom.Vector;

/**
 * @author Josef Friedrich
 *
 * @since 0.45.0
 */
public class LineTest
{
    Line line;

    @BeforeEach
    public void setUp()
    {
        Controller.instantMode(false);
        line = new Line(1, 2, 3, 4);
    }

    @Test
    void end1Getter()
    {
        Vector end1 = line.end1();
        assertEquals(1, end1.x(), 0.001);
        assertEquals(2, end1.y(), 0.001);
    }

    @Test
    void end1Setter()
    {
        line.end1(new Vector(7, 8));
        Vector end1 = line.end1();
        assertEquals(7, end1.x(), 0.001);
        assertEquals(8, end1.y(), 0.001);
    }

    @Test
    void end2Getter()
    {
        Vector end2 = line.end2();
        assertEquals(3, end2.x(), 0.001);
        assertEquals(4, end2.y(), 0.001);
    }

    @Test
    void end2Setter()
    {

        line.end2(new Vector(7, 8));
        Vector end2 = line.end2();
        assertEquals(7, end2.x(), 0.001);
        assertEquals(8, end2.y(), 0.001);
    }

    @Test
    void strokeWidth()
    {
        line.strokeWidth(1);
        assertEquals(1, line.strokeWidth(), 0.001);
    }

    @Test
    void offset()
    {
        line.offset(0.5);
        assertEquals(0.5, line.offset(), 0.001);
        assertEquals(0.5, line.end1.offset(), 0.001);
        assertEquals(0.5, line.end2.offset(), 0.001);

        line.end1.offset(1);
        line.end2.offset(2);

        assertThrows(RuntimeException.class, () -> line.offset());
    }
}
