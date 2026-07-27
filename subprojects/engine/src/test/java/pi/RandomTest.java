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
package pi;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import pi.graphics.geom.Vector;
import pi.resources.font.FontStyle;

/**
 * @author Josef Friedrich
 */
class RandomTest
{
    @Nested
    class ToggleTest
    {
        @Test
        void distribution()
        {
            int trueCount = 0;
            int iterations = 1000;
            for (int i = 0; i < iterations; i++)
            {
                if (Random.toggle())
                {
                    trueCount++;
                }
            }
            int falseCount = iterations - trueCount;
            assertTrue(trueCount > 0);
            assertTrue(falseCount > 0);
            assertTrue(Math.abs(trueCount - falseCount) < iterations * 0.2);
        }

        @Test
        void returnsBoolean()
        {
            boolean result = Random.toggle();
            assertTrue(result == true || result == false);
        }

        @Test
        void multipleCalls()
        {
            for (int i = 0; i < 100; i++)
            {
                boolean result = Random.toggle();
                assertTrue(result == true || result == false);
            }
        }

        @Test
        void variety()
        {
            boolean foundTrue = false;
            boolean foundFalse = false;
            for (int i = 0; i < 100; i++)
            {
                if (Random.toggle())
                {
                    foundTrue = true;
                }
                else
                {
                    foundFalse = true;
                }
                if (foundTrue && foundFalse)
                {
                    break;
                }
            }
            assertTrue(foundTrue);
        }
    }

    @Nested
    class VectorTest
    {
        @Test
        void test1()
        {
            Vector v = Random.vector(0, 10, 0, 10);
            assertTrue(v.x() >= 0 && v.x() <= 10);
            assertTrue(v.y() >= 0 && v.y() <= 10);
        }

        @Test
        void test2()
        {
            Vector v = Random.vector(-5, 5, -10, 10);
            assertTrue(v.x() >= -5 && v.x() <= 5);
            assertTrue(v.y() >= -10 && v.y() <= 10);
        }
    }

    @Nested
    class FontStyleAsIntTest
    {
        @Test
        void returnsIntInRange()
        {
            for (int i = 0; i < 1000; i++)
            {
                int result = Random.fontStyleAsInt();
                assertTrue(result >= 0 && result < 4);
            }
        }

        @Test
        void distributionAcrossRange()
        {
            boolean[] seen = new boolean[4];
            for (int i = 0; i < 1000; i++)
            {
                int result = Random.fontStyleAsInt();
                seen[result] = true;
            }
            for (int i = 0; i < 4; i++)
            {
                assertTrue(seen[i]);
            }
        }

        @Test
        void multipleCalls()
        {
            for (int i = 0; i < 500; i++)
            {
                int result = Random.fontStyleAsInt();
                assertTrue(result >= 0 && result <= 3);
            }
        }
    }

    @Nested
    class FontStyleAsEnumTest
    {
        @Test
        void returnsNonNull()
        {
            FontStyle style = Random.fontStyleAsEnum();
            assertNotNull(style);
        }

        @Test
        void returnsFontStyleEnum()
        {
            FontStyle style = Random.fontStyleAsEnum();
            assertInstanceOf(FontStyle.class, style);
        }

        @Test
        void multipleCalls()
        {
            for (int i = 0; i < 100; i++)
            {
                FontStyle style = Random.fontStyleAsEnum();
                assertNotNull(style);
            }
        }

        @Test
        void variety()
        {
            java.util.Set<FontStyle> foundStyles = new java.util.HashSet<>();
            for (int i = 0; i < 1000; i++)
            {
                FontStyle style = Random.fontStyleAsEnum();
                foundStyles.add(style);
                if (foundStyles.size() > 1)
                {
                    break;
                }
            }
            assertTrue(foundStyles.size() > 1);
        }
    }
}
