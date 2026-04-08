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
package pi.graphics.geom;

import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pi.CustomAssertions.assertToStringClassName;
import static pi.CustomAssertions.assertToStringFieldOrder;
import static pi.CustomAssertions.assertToStringFieldValue;
import static pi.graphics.geom.Vector.v;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import pi.EnLocale;

class VectorTest
{
    @Test
    void vectorOfVectors()
    {
        Vector p1 = v(10, 10);
        Vector p2 = v(30, 20);
        Vector vector = new Vector(p1, p2);
        assertEquals(vector.x(), 20, 0.0001);
        assertEquals(vector.y(), 10, 0.0001);
    }

    @Nested
    class OfAngleTest
    {
        private void assertOfAngle(double angle, double x, double y)
        {
            Vector a = Vector.ofAngle(angle);
            assertEquals(a.x(), x, 0.0001);
            assertEquals(a.y(), y, 0.0001);
            assertEquals(a.length(), 1, 0.0001);
        }

        @Test
        void test0()
        {
            assertOfAngle(0, 1.0, 0.0);
        }

        @Test
        void test90()
        {
            assertOfAngle(90, 0.0, 1.0);
        }

        @Test
        void test180()
        {
            assertOfAngle(180, -1.0, 0.0);
            assertOfAngle(-180, -1.0, 0.0);
        }

        @Test
        void test270()
        {
            assertOfAngle(270, 0.0, -1.0);
            assertOfAngle(-90, 0.0, -1.0);
        }

        @Test
        void test360()
        {
            assertOfAngle(360, 1.0, 0.0);
        }
    }

    @Test
    void x()
    {
        double x = .013;
        assertEquals(v(x, 0).x(), x, 0.00001);
    }

    @Test
    void y()
    {
        double y = .013;
        assertEquals(v(0, y).y(), y, 0.00001);
    }

    @Test
    void scalarProduct()
    {
        Vector v1 = v(1, 0);
        Vector v2 = v(0, 1);
        Vector v3 = v(2, 1);
        assertEquals(v1.scalarProduct(v2), 0, 0);
        assertNotEquals(v1.scalarProduct(v3), 0, 0);
        assertNotEquals(v2.scalarProduct(v3), 0, 0);
    }

    @Test
    void equals()
    {
        Vector v1 = v(1, 1);
        Vector p1 = v(1, 1);
        assertEquals(p1, v1);
    }

    @Test
    void notEquals()
    {
        Vector vector = v(1, 1);
        assertNotEquals(v(1, 0), vector);
        assertNotEquals(v(0, 1), vector);
        assertNotEquals(v(0, 0), vector);
        assertNotEquals(vector, new Object());
    }

    @Test
    void subtract()
    {
        Vector v1 = v(3, 3);
        Vector v2 = v(2, 2);
        assertEquals(v1.subtract(v2), v(1, 1));
    }

    @Test
    void multiply()
    {
        Vector vector = v(1, 2);
        assertEquals(vector.multiply(2), v(2, 4));
    }

    @Test
    void normalize()
    {
        Vector vector = v(10, 100);
        assertEquals(vector.normalize().length(), 1, 0);
    }

    @Test
    void divideThrowsException()
    {
        assertThrows(ArithmeticException.class, () -> v(0, 0).divide(0));
    }

    @Test
    void length()
    {
        assertEquals(v(1, 1).length(), Math.sqrt(2), 0.00001);
    }

    @Nested
    class GetLengthOtherTest
    {
        @Test
        void withOther()
        {
            // distance between (1,2) and (4,6) is 5
            assertEquals(5.0, v(1, 2).length(v(4, 6)), 1e-9);
        }

        @Test
        void withOtherSameVector()
        {
            Vector a = v(2.5, -1.5);
            assertEquals(0.0, a.length(a), 1e-9);
        }

        @Test
        void withOtherNullThrows()
        {
            assertThrows(NullPointerException.class,
                () -> v(0, 0).length(null));
        }
    }

    @Test
    void negate()
    {
        assertEquals(v(1, 1).negate(), v(-1, -1));
    }

    @Test
    void add()
    {
        assertEquals(v(1, 1).add(v(1, 1)), v(2, 2));
    }

    @Test
    void isNull()
    {
        assertFalse(v(1, 1).isNull());
        assertFalse(v(1, 0).isNull());
        assertFalse(v(0, 1).isNull());
        assertTrue(v(0, 0).isNull());
    }

    @Test
    void isIntegral()
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
        void sameVectorReturnsZero()
        {
            Vector v = v(1, 2);
            assertEquals(360, v.angle(v), 1e-5);
        }

        @Test
        void orthogonalIsNinety()
        {
            Vector a = v(1, 0);
            Vector b = v(0, 1);
            assertEquals(90.0, a.angle(b), 1e-9);
            assertEquals(270.0, b.angle(a), 1e-9);
        }

        @Test
        void oppositeIsOneEighty()
        {
            Vector a = v(1, 0);
            Vector b = v(-1, 0);
            assertEquals(180.0, a.angle(b), 1e-9);
        }

        @Test
        void arbitraryVectors()
        {
            Vector a = v(1.0, 1.0);
            Vector b = v(2.0, 3.0);
            double expected = Math.toDegrees(
                Math.acos(a.scalarProduct(b) / (a.length() * b.length())));
            assertEquals(expected, a.angle(b), 1e-9);
        }

        @Test
        void nullThrowsException()
        {
            assertThrows(NullPointerException.class, () -> v(0, 0).angle(null));
        }
    }

    @ExtendWith(EnLocale.class)
    @Test
    void toStringFormatter()
    {
        Vector vector = new Vector(1, 1);
        assertToStringClassName(vector);
        assertToStringFieldOrder(new String[] { "x", "y" }, vector);
        assertToStringFieldValue("x", 1.0, vector);
        assertToStringFieldValue("y", 1.0, vector);
    }

    @Nested
    class GetAngleOwnTest
    {
        double delta = 1e-9;

        private void assertAngle(double expected, double x, double y)
        {
            assertEquals(expected, v(x, y).angle(), delta);
        }

        @Test
        void zero()
        {
            assertAngle(0.0, 0, 0);
        }

        @Test
        void east()
        {
            assertAngle(0.0, 1, 0);
        }

        @Test
        void eastNorth()
        {
            assertAngle(45, 1, 1);
        }

        @Test
        void north()
        {
            assertAngle(90, 0, 1);
        }

        @Test
        void northWest()
        {
            assertAngle(135, -1, 1);
        }

        @Test
        void west()
        {
            assertAngle(180, -1, 0);
        }

        @Test
        void westSouth()
        {
            assertAngle(-135, -1, -1);
        }

        @Test
        void south()
        {
            assertAngle(-90, 0, -1);
        }

        @Test
        void southEast()
        {
            assertAngle(-45, 1, -1);
        }

        @Test
        void arbitraryVector()
        {
            Vector a = v(2.0, -3.0);
            double expected = Math.toDegrees(Math.atan2(a.y(), a.x()));
            assertEquals(expected, a.angle(), 1e-9);
        }
    }

    @Nested
    class GetRadiansOwnTest
    {
        double delta = 1e-9;

        private void assertRadians(double expected, double x, double y)
        {
            assertEquals(expected, v(x, y).radians(), delta);
        }

        @Test
        void zero()
        {
            assertRadians(0.0, 0, 0);
        }

        @Test
        void aast()
        {
            assertRadians(0.0, 1, 0);
        }

        @Test
        void eastNorth()
        {
            assertRadians(PI / 4, 1, 1);
        }

        @Test
        void north()
        {
            assertRadians(PI / 2, 0, 1);
        }

        @Test
        void northWest()
        {
            assertRadians(PI / 2 + PI / 4, -1, 1);
        }

        @Test
        void west()
        {
            assertRadians(PI, -1, 0);
        }

        @Test
        void westSouth()
        {
            assertRadians(-PI / 2 - PI / 4, -1, -1);
        }

        @Test
        void south()
        {
            assertRadians(-PI / 2, 0, -1);
        }

        @Test
        void southEast()
        {
            assertRadians(-PI / 4, 1, -1);
        }

        @Test
        void arbitraryVector()
        {
            Vector a = v(2.0, -3.0);
            double expected = Math.atan2(a.y(), a.x());
            assertEquals(expected, a.radians(), 1e-9);
        }
    }
}
