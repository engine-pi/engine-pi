/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2014 Michael Andonie and contributors.
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
package de.pirckheimer_gymnasium.engine_pi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static de.pirckheimer_gymnasium.engine_pi.Vector.v;
import static java.lang.Math.PI;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class VectorTest
{
    @Test
    public void testVectorOfVectors()
    {
        Vector p1 = v(10, 10);
        Vector p2 = v(30, 20);
        Vector vector = new Vector(p1, p2);
        assertEquals(vector.getX(), 20, 0.0001);
        assertEquals(vector.getY(), 10, 0.0001);
    }

    @Nested
    class OfAngleTest
    {
        private void assertOfAngle(double angle, double x, double y)
        {
            Vector a = Vector.ofAngle(angle);
            assertEquals(a.getX(), x, 0.0001);
            assertEquals(a.getY(), y, 0.0001);
            assertEquals(a.getLength(), 1, 0.0001);
        }

        @Test
        public void test0()
        {
            assertOfAngle(0, 1.0, 0.0);
        }

        @Test
        public void test90()
        {
            assertOfAngle(90, 0.0, 1.0);
        }

        @Test
        public void test180()
        {
            assertOfAngle(180, -1.0, 0.0);
        }

        @Test
        public void test270()
        {
            assertOfAngle(270, 0.0, -1.0);
        }

        @Test
        public void test360()
        {
            assertOfAngle(360, 1.0, 0.0);
        }
    }

    @Test
    public void testGetX()
    {
        double x = .013;
        assertEquals(v(x, 0).getX(), x, 0.00001);
    }

    @Test
    public void testGetY()
    {
        double y = .013;
        assertEquals(v(0, y).getY(), y, 0.00001);
    }

    @Test
    public void testGetScalarProduct()
    {
        Vector v1 = v(1, 0);
        Vector v2 = v(0, 1);
        Vector v3 = v(2, 1);
        assertEquals(v1.getScalarProduct(v2), 0, 0);
        assertNotEquals(v1.getScalarProduct(v3), 0, 0);
        assertNotEquals(v2.getScalarProduct(v3), 0, 0);
    }

    @Test
    public void testEquals()
    {
        Vector v1 = v(1, 1);
        Vector p1 = v(1, 1);
        assertEquals(p1, v1);
    }

    @Test
    public void testNotEquals()
    {
        Vector vector = v(1, 1);
        assertNotEquals(v(1, 0), vector);
        assertNotEquals(v(0, 1), vector);
        assertNotEquals(v(0, 0), vector);
        assertNotEquals(vector, new Object());
    }

    @Test
    public void testSubtract()
    {
        Vector v1 = v(3, 3);
        Vector v2 = v(2, 2);
        assertEquals(v1.subtract(v2), v(1, 1));
    }

    @Test
    public void testMultiply()
    {
        Vector vector = v(1, 2);
        assertEquals(vector.multiply(2), v(2, 4));
    }

    @Test
    public void testNormalize()
    {
        Vector vector = v(10, 100);
        assertEquals(vector.normalize().getLength(), 1, 0);
    }

    @Test
    public void testDivideThrowsException()
    {
        assertThrows(ArithmeticException.class, () -> v(0, 0).divide(0));
    }

    @Test
    public void testGetLength()
    {
        assertEquals(v(1, 1).getLength(), Math.sqrt(2), 0.00001);
    }

    @Nested
    class GetLengthOtherTest
    {
        @Test
        public void testWithOther()
        {
            // distance between (1,2) and (4,6) is 5
            assertEquals(5.0, v(1, 2).getLength(v(4, 6)), 1e-9);
        }

        @Test
        public void testWithOtherSameVector()
        {
            Vector a = v(2.5, -1.5);
            assertEquals(0.0, a.getLength(a), 1e-9);
        }

        @Test
        public void testWithOtherNullThrows()
        {
            assertThrows(NullPointerException.class,
                    () -> v(0, 0).getLength(null));
        }
    }

    @Test
    public void testNegate()
    {
        assertEquals(v(1, 1).negate(), v(-1, -1));
    }

    @Test
    public void testAdd()
    {
        assertEquals(v(1, 1).add(v(1, 1)), v(2, 2));
    }

    @Test
    public void testIsNull()
    {
        assertFalse(v(1, 1).isNull());
        assertFalse(v(1, 0).isNull());
        assertFalse(v(0, 1).isNull());
        assertTrue(v(0, 0).isNull());
    }

    @Test
    public void testIsIntegral()
    {
        assertTrue(v(1, 1).isIntegral());
        assertFalse(v(.5, .5).isIntegral());
        assertFalse(v(.5, 1).isIntegral());
        assertFalse(v(1, .5).isIntegral());
    }

    @Nested
    class GetAngleTest
    {
        @Test
        public void testSameVectorReturnsZero()
        {
            Vector v = v(1, 2);
            assertEquals(360, v.getAngle(v), 1e-5);
        }

        @Test
        public void testOrthogonalIsNinety()
        {
            Vector a = v(1, 0);
            Vector b = v(0, 1);
            assertEquals(90.0, a.getAngle(b), 1e-9);
            assertEquals(270.0, b.getAngle(a), 1e-9);
        }

        @Test
        public void testOppositeIsOneEighty()
        {
            Vector a = v(1, 0);
            Vector b = v(-1, 0);
            assertEquals(180.0, a.getAngle(b), 1e-9);
        }

        @Test
        public void testArbitraryVectors()
        {
            Vector a = v(1.0, 1.0);
            Vector b = v(2.0, 3.0);
            double expected = Math.toDegrees(Math.acos(
                    a.getScalarProduct(b) / (a.getLength() * b.getLength())));
            assertEquals(expected, a.getAngle(b), 1e-9);
        }

        @Test
        public void testNullThrowsException()
        {
            assertThrows(NullPointerException.class,
                    () -> v(0, 0).getAngle(null));
        }
    }

    @Test
    public void testToString()
    {
        assertEquals("Vector [x=1.0, y=1.0]", v(1, 1).toString());
    }

    @Nested
    class GetAngleOwnTest
    {

        double delta = 1e-9;

        private void assertAngle(double expected, double x, double y)
        {
            assertEquals(expected, v(x, y).getAngle(), delta);
        }

        @Test
        public void testZero()
        {
            assertAngle(0.0, 0, 0);
        }

        @Test
        public void testEast()
        {
            assertAngle(0.0, 1, 0);
        }

        @Test
        public void testEastNorth()
        {
            assertAngle(45, 1, 1);
        }

        @Test
        public void testNorth()
        {
            assertAngle(90, 0, 1);
        }

        @Test
        public void testNorthWest()
        {
            assertAngle(135, -1, 1);
        }

        @Test
        public void testWest()
        {
            assertAngle(180, -1, 0);
        }

        @Test
        public void testWestSouth()
        {
            assertAngle(-135, -1, -1);
        }

        @Test
        public void testSouth()
        {
            assertAngle(-90, 0, -1);
        }

        @Test
        public void testSouthEast()
        {
            assertAngle(-45, 1, -1);
        }

        @Test
        public void testArbitraryVector()
        {
            Vector a = v(2.0, -3.0);
            double expected = Math.toDegrees(Math.atan2(a.getY(), a.getX()));
            assertEquals(expected, a.getAngle(), 1e-9);
        }
    }

    @Nested
    class GetRadiansOwnTest
    {

        double delta = 1e-9;

        private void assertRadians(double expected, double x, double y)
        {
            assertEquals(expected, v(x, y).getRadians(), delta);
        }

        @Test
        public void testZero()
        {
            assertRadians(0.0, 0, 0);
        }

        @Test
        public void testEast()
        {
            assertRadians(0.0, 1, 0);
        }

        @Test
        public void testEastNorth()
        {
            assertRadians(PI / 4, 1, 1);
        }

        @Test
        public void testNorth()
        {
            assertRadians(PI / 2, 0, 1);
        }

        @Test
        public void testNorthWest()
        {
            assertRadians(PI / 2 + PI / 4, -1, 1);
        }

        @Test
        public void testWest()
        {
            assertRadians(PI, -1, 0);
        }

        @Test
        public void testWestSouth()
        {
            assertRadians(-PI / 2 - PI / 4, -1, -1);
        }

        @Test
        public void testSouth()
        {
            assertRadians(-PI / 2, 0, -1);
        }

        @Test
        public void testSouthEast()
        {
            assertRadians(-PI / 4, 1, -1);
        }

        @Test
        public void testArbitraryVector()
        {
            Vector a = v(2.0, -3.0);
            double expected = Math.atan2(a.getY(), a.getX());
            assertEquals(expected, a.getRadians(), 1e-9);
        }
    }
}
