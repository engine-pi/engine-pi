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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Graphics2D;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * @author Josef Friedrich
 *
 * @since 0.46.0
 */
class BoxTest
{
    private static class TestBox extends LeafBox
    {
        TestBox(boolean supportsDefinedDimension)
        {
            this.supportsDefinedDimension = supportsDefinedDimension;
        }

        @Override
        protected void calculateDimension()
        {
            width = definedWidth;
            height = definedHeight;
        }

        @Override
        void draw(Graphics2D g)
        {
            // Nicht zu tun.
        }
    }

    TestBox box;

    @BeforeEach
    void setUp()
    {
        box = new TestBox(true);
    }

    @Nested
    class WidthTest
    {
        @Test
        void returnsSameInstance()
        {
            assertSame(box, box.width(120));
        }

        @Test
        void returnsCalculatedWidth()
        {

            assertEquals(0, box.width());
            box.width(120);
            box.calculateDimension();
            assertEquals(120, box.width());
        }
    }

    @Nested
    class HeightTest
    {
        @Test
        void heightSetterReturnsSameInstance()
        {
            assertSame(box, box.height(80));
        }

        @Test
        void heightGetterReturnsCalculatedHeight()
        {
            assertEquals(0, box.height());
            box.height(80);
            box.calculateDimension();
            assertEquals(80, box.height());
        }
    }

    @Nested
    class HasDefinedTest
    {
        @Test
        void setNone()
        {

            assertFalse(box.hasDefiniedDimension());
            assertFalse(box.hasOnlyDefiniedWidth());
            assertFalse(box.hasOnlyDefiniedHeight());
        }

        @Test
        void setWidth()
        {
            box.width(120);

            assertTrue(box.hasDefiniedDimension());
            assertTrue(box.hasOnlyDefiniedWidth());
            assertFalse(box.hasOnlyDefiniedHeight());
        }

        @Test
        void setHeight()
        {
            box.height(80);

            assertTrue(box.hasDefiniedDimension());
            assertFalse(box.hasOnlyDefiniedWidth());
            assertTrue(box.hasOnlyDefiniedHeight());
        }

        @Test
        void setBoth()
        {
            box.width(120);
            box.height(80);

            assertTrue(box.hasDefiniedDimension());
            assertFalse(box.hasOnlyDefiniedWidth());
            assertFalse(box.hasOnlyDefiniedHeight());
        }
    }

    @Test
    void widthThrowsExceptionIfDefinedDimensionIsNotSupported()
    {
        TestBox box = new TestBox(false);
        assertThrows(IllegalArgumentException.class, () -> box.width(10));
    }

    @Test
    void heightThrowsExceptionIfDefinedDimensionIsNotSupported()
    {
        TestBox box = new TestBox(false);
        assertThrows(IllegalArgumentException.class, () -> box.height(10));
    }
}
