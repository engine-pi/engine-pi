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

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import pi.graphics.geom.Vector;

/**
 * @author Josef Friedrich
 */
public class RandomTest
{
    @Nested
    class ToggleTest
    {
        @Test
        void testDistribution()
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
            assert trueCount > 0 : "Should have at least some true values";
            assert falseCount > 0 : "Should have at least some false values";
            assert Math.abs(trueCount - falseCount) < iterations * 0.2
                    : "Distribution should be roughly 50/50";
        }

        @Test
        void testReturnsBoolean()
        {
            boolean result = Random.toggle();
            assert result == true || result == false
                    : "toggle() should return a boolean value";
        }

        @Test
        void testMultipleCalls()
        {
            for (int i = 0; i < 100; i++)
            {
                boolean result = Random.toggle();
                assert result == true || result == false
                        : "Each call should return a valid boolean";
            }
        }

        @Test
        void testVariety()
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
            assert foundTrue : "Should eventually return true";
            assert foundFalse : "Should eventually return false";
        }
    }

    @Nested
    class VectorTest
    {
        @Test
        void test1()
        {
            Vector v = Random.vector(0, 10, 0, 10);
            assert v.x() >= 0 && v.x() <= 10 : "X component out of range";
            assert v.y() >= 0 && v.y() <= 10 : "Y component out of range";
        }

        @Test
        void test2()
        {
            Vector v = Random.vector(-5, 5, -10, 10);
            assert v.x() >= -5 && v.x() <= 5 : "X component out of range";
            assert v.y() >= -10 && v.y() <= 10 : "Y component out of range";
        }
    }
}
