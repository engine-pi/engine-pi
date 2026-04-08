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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.awt.Color;
import java.awt.Graphics2D;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public class EllipseBoxTest
{
    EllipseBox ellipse;

    @BeforeEach
    void setUp()
    {
        ellipse = new EllipseBox(100, 50).color(Color.RED);
    }

    @Test
    void width()
    {
        assertSame(ellipse, ellipse.width(150));
        ellipse.calculateDimension();
        assertEquals(150, ellipse.width());
    }

    @Test
    void height()
    {
        assertSame(ellipse, ellipse.height(75));
        ellipse.calculateDimension();
        assertEquals(75, ellipse.height());
    }

    @Test
    void color()
    {
        EllipseBox result = ellipse.color(Color.BLUE);
        assertSame(ellipse, result);
        assertEquals(Color.BLUE, ellipse.color());
    }

    @Test
    void draw()
    {
        Graphics2D g = mock(Graphics2D.class);
        ellipse.draw(g);
        verify(g).fillOval(0, 0, 100, 50);
    }

    @Test
    void calculateDimension()
    {
        assertEquals(0, ellipse.width());
        assertEquals(0, ellipse.height());
        ellipse.calculateDimension();
        assertEquals(100, ellipse.width());
        assertEquals(50, ellipse.height());
    }

    @Test
    void toStringFormatter()
    {
        assertEquals("EllipseBox [color=#ff0000, dWidth=100, dHeight=50]",
            ellipse.toString(true));
    }
}
