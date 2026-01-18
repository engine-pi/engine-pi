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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pi.debug.ToStringFormatter;

/**
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public class EllipseBoxTest
{
    EllipseBox ellipse;

    @BeforeEach
    void setup()
    {
        ellipse = new EllipseBox(100, 50);
    }

    @Test
    void testCalculateDimension()
    {
        assertEquals(0, ellipse.width());
        assertEquals(0, ellipse.height());
        ellipse.calculateDimension();
        assertEquals(100, ellipse.width());
        assertEquals(50, ellipse.height());
    }

    @Test
    void testColor()
    {
        EllipseBox result = ellipse.color("red");
        assertSame(ellipse, result);
    }

    @Test
    void testColor2()
    {
        Color testColor = Color.BLUE;
        EllipseBox result = ellipse.color(testColor);
        assertSame(ellipse, result);
    }

    // @Test
    // void testDraw()
    // {
    // EllipseBox ellipse = new EllipseBox(100, 50);
    // Graphics2D g = mock(Graphics2D.class);
    // ellipse.draw(g);
    // verify(g).drawOval(0, 0, 100, 50);
    // }

    @Test
    void testWidth()
    {
        assertSame(ellipse, ellipse.width(150));
        ellipse.calculateDimension();
        assertEquals(150, ellipse.width());
    }

    @Test
    void testHeight()
    {
        assertSame(ellipse, ellipse.height(75));
        ellipse.calculateDimension();
        assertEquals(75, ellipse.height());
    }

    @Test
    void testToString()
    {
        String result = ellipse.toString();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("EllipseBox [dWidth=100, dHeight=50]",
            ToStringFormatter.clean(result));
    }
}
