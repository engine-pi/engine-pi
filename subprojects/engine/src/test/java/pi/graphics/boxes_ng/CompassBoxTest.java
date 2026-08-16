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
package pi.graphics.boxes_ng;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import pi.graphics.geom.Vector;

/**
 * @since 0.53.0
 */
class CompassBoxTest
{
    @Test
    void initializesSizeAndDimension()
    {
        CompassBox compass = new CompassBox(42);
        compass.measure();

        assertEquals(42, compass.size());
        assertEquals(42, compass.width());
        assertEquals(42, compass.height());
    }

    @Test
    void sizeSetterAndGetterWorkWithChaining()
    {
        CompassBox compass = new CompassBox(10);

        CompassBox returned = compass.size(25);
        compass.measure();

        assertSame(compass, returned);
        assertEquals(25, compass.size());
        assertEquals(25, compass.width());
        assertEquals(25, compass.height());
    }

    @Test
    void directionAndDisplayFlagsCanBeConfigured()
    {
        CompassBox compass = new CompassBox(30);

        assertSame(compass, compass.direction(90));
        assertEquals(90, compass.direction);

        assertSame(compass, compass.showCenter(false));
        assertEquals(false, compass.showCenter);

        assertSame(compass, compass.showOuterCircle());
        assertEquals(true, compass.showOuterCircle);
    }

    @Test
    void radiusAndCenterAreCalculatedFromAnchorAndSize()
    {
        CompassBox compass = new CompassBox(20);
        compass.x(10).y(30);

        assertEquals(10.0, compass.radius());

        Vector center = compass.center();

        // 10 + 10 = 20
        assertEquals(20.0, center.x(), 0.0001);

        // 30 - 10 = 20
        assertEquals(20.0, center.y(), 0.0001);
    }
}
