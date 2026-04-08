/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2024 Josef Friedrich and contributors.
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pi.CustomAssertions.assertToStringClassName;

import pi.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class StarTest
{
    private Star star;

    @BeforeEach
    void setUp()
    {
        Controller.instantMode(false);
        star = new Star();
    }

    @Nested
    class ConstructorTest
    {
        @Test
        void defaultConstructor()
        {
            assertEquals(7, star.numPoints());
            assertEquals(2.0, star.radius());
            assertEquals(1.0, star.innerRadius());
        }

        @Test
        void constructorWithParameters()
        {
            Star customStar = new Star(5, 3.0, 1.5);
            assertEquals(5, customStar.numPoints());
            assertEquals(3.0, customStar.radius());
            assertEquals(1.5, customStar.innerRadius());
        }
    }

    @Nested
    class NumPointsTest
    {
        @Test
        void getter()
        {
            assertEquals(7, star.numPoints());
        }

        @Test
        void setter()
        {
            Star result = star.numPoints(5);
            assertEquals(5, star.numPoints());
            assertSame(star, result);
        }
    }

    @Nested
    class RadiusTest
    {
        @Test
        void getter()
        {
            assertEquals(2.0, star.radius());
        }

        @Test
        void setter()
        {
            Star result = star.radius(4.0);
            assertEquals(4.0, star.radius());
            assertSame(star, result);
        }
    }

    @Nested
    class InnerRadiusTest
    {
        @Test
        void getter()
        {
            assertEquals(1.0, star.innerRadius());
        }

        @Test
        void setter()
        {
            Star result = star.innerRadius(0.5);
            assertEquals(0.5, star.innerRadius());
            assertSame(star, result);
        }
    }

    @Test
    void chaining()
    {
        Star result = star.numPoints(5).radius(3.0).innerRadius(1.5);
        assertSame(star, result);
        assertEquals(5, star.numPoints());
        assertEquals(3.0, star.radius());
        assertEquals(1.5, star.innerRadius());
    }

    @Nested
    class ToStringTest
    {
        @Test
        void className()
        {
            assertToStringClassName(star);
        }

        @Test
        void toStringMethod()
        {
            String result = star.toString();
            assertNotNull(result);
            assertTrue(result.contains("numPoints"));
            assertTrue(result.contains("radius"));
            assertTrue(result.contains("innerRadius"));
        }
    }
}
