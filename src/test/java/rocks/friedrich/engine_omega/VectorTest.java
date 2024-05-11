/*
 * Engine Omega ist eine anfängerorientierte 2D-Gaming Engine.
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
package rocks.friedrich.engine_omega;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class VectorTest
{
    @Test
    public void testVectorOfVectors()
    {
        Vector p1 = new Vector(10f, 10f);
        Vector p2 = new Vector(30f, 20f);
        Vector vector = new Vector(p1, p2);
        assertEquals(vector.getX(), 20f, 0.0001f);
        assertEquals(vector.getY(), 10f, 0.0001f);
    }

    @Test
    public void testGetScalarProduct()
    {
        Vector v1 = new Vector(1, 0);
        Vector v2 = new Vector(0, 1);
        Vector v3 = new Vector(2, 1);
        assertEquals(v1.getScalarProduct(v2), 0, 0);
        assertNotEquals(v1.getScalarProduct(v3), 0, 0);
        assertNotEquals(v2.getScalarProduct(v3), 0, 0);
    }

    @Test
    public void testEquals()
    {
        Vector v1 = new Vector(1, 1);
        Vector p1 = new Vector(1, 1);
        assertEquals(p1, v1);
    }

    @Test
    public void testNotEquals()
    {
        Vector vector = new Vector(1, 1);
        assertNotEquals(new Vector(1, 0), vector);
        assertNotEquals(new Vector(0, 1), vector);
        assertNotEquals(new Vector(0, 0), vector);
        assertNotEquals(vector, new Object());
    }

    @Test
    public void testSubtract()
    {
        Vector v1 = new Vector(3, 3);
        Vector v2 = new Vector(2, 2);
        assertEquals(v1.subtract(v2), new Vector(1, 1));
    }

    @Test
    public void testMultiply()
    {
        Vector vector = new Vector(1, 2);
        assertEquals(vector.multiply(2), new Vector(2, 4));
    }

    @Test
    public void testNormalize()
    {
        Vector vector = new Vector(10, 100);
        assertEquals(vector.normalize().getLength(), 1, 0);
    }

    @Test
    public void testDivideThrowsException()
    {
        assertThrows(ArithmeticException.class,
                () -> new Vector(0, 0).divide(0));
    }

    @Test
    public void testGetLength()
    {
        assertEquals(new Vector(1, 1).getLength(), Math.sqrt(2), 0.00001);
    }

    @Test
    public void testNegate()
    {
        assertEquals(new Vector(1, 1).negate(), new Vector(-1, -1));
    }

    @Test
    public void testAdd()
    {
        assertEquals(new Vector(1, 1).add(new Vector(1, 1)), new Vector(2, 2));
    }

    @Test
    public void testIsNull()
    {
        assertFalse(new Vector(1, 1).isNull());
        assertFalse(new Vector(1, 0).isNull());
        assertFalse(new Vector(0, 1).isNull());
        assertTrue(new Vector(0, 0).isNull());
    }

    @Test
    public void testIsIntegral()
    {
        assertTrue(new Vector(1, 1).isIntegral());
        assertFalse(new Vector(.5f, .5f).isIntegral());
        assertFalse(new Vector(.5f, 1).isIntegral());
        assertFalse(new Vector(1, .5f).isIntegral());
    }

    @Test
    public void testGetX()
    {
        double x = .013;
        assertEquals(new Vector(x, 0).getX(), x, 0.00001);
    }

    @Test
    public void testGetY()
    {
        double y = .013;
        assertEquals(new Vector(0, y).getY(), y, 0.00001);
    }

    @Test
    public void testToString()
    {
        assertEquals("Vector [ x = 1.0; y = 1.0 ]",
                new Vector(1, 1).toString());
    }
}
