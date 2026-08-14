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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import pi.debug.ToStringFormatter;

class BoxTest
{
    private static class TestBox extends Box
    {
        private int drawCalls = 0;

        TestBox()
        {
            supportsDefinedDimension = true;
        }

        void addChildBox(Box child)
        {
            childs.add(child);
            child.parent = this;
        }

        @Override
        public int numberOfChilds()
        {
            return childs.size();
        }

        @Override
        protected void calculateDimension()
        {
            width = definedWidth;
            height = definedHeight;
        }

        @Override
        protected void calculateAnchors()
        {
            // No-op in this unit test.
        }

        @Override
        void draw(Graphics2D g)
        {
            drawCalls++;
        }
    }

    @Nested
    class WidthTest
    {
        @Test
        void getterAndSetter()
        {
            TestBox box = new TestBox();

            assertSame(box, box.width(120));
            box.measure();
            assertEquals(120, box.width());
            assertEquals(120.0, box.widthMeter(), 0.0);
        }

        @Test
        void widthMeterSetter()
        {
            TestBox box = new TestBox();

            box.widthMeter(5.0);
            box.measure();
            assertEquals(5, box.width());
        }

        @Test
        void unsupportedDimensionThrows()
        {
            Box box = new TestBox()
            {
                {
                    supportsDefinedDimension = false;
                }
            };

            assertThrows(IllegalArgumentException.class, () -> box.width(100));
        }
    }

    @Nested
    class HeightTest
    {
        @Test
        void getterAndSetter()
        {
            TestBox box = new TestBox();

            assertSame(box, box.height(80));
            box.measure();
            assertEquals(80, box.height());
            assertEquals(80.0, box.heightMeter(), 0.0);
        }

        @Test
        void heightMeterSetter()
        {
            TestBox box = new TestBox();

            box.heightMeter(7.0);
            box.measure();
            assertEquals(7, box.height());
        }

        @Test
        void unsupportedDimensionThrows()
        {
            Box box = new TestBox()
            {
                {
                    supportsDefinedDimension = false;
                }
            };

            assertThrows(IllegalArgumentException.class, () -> box.height(80));
        }
    }

    @Nested
    class DimensionStateTest
    {
        @Test
        void noDefinedDimensions()
        {
            TestBox box = new TestBox();

            assertFalse(box.hasDefiniedDimension());
            assertFalse(box.hasOnlyDefiniedWidth());
            assertFalse(box.hasOnlyDefiniedHeight());
        }

        @Test
        void widthOnly()
        {
            TestBox box = new TestBox();
            box.width(120);

            assertTrue(box.hasDefiniedDimension());
            assertTrue(box.hasOnlyDefiniedWidth());
            assertFalse(box.hasOnlyDefiniedHeight());
        }

        @Test
        void heightOnly()
        {
            TestBox box = new TestBox();
            box.height(80);

            assertTrue(box.hasDefiniedDimension());
            assertFalse(box.hasOnlyDefiniedWidth());
            assertTrue(box.hasOnlyDefiniedHeight());
        }

        @Test
        void bothDimensions()
        {
            TestBox box = new TestBox();
            box.width(120).height(80);

            assertTrue(box.hasDefiniedDimension());
            assertFalse(box.hasOnlyDefiniedWidth());
            assertFalse(box.hasOnlyDefiniedHeight());
        }
    }

    @Nested
    class PositionTest
    {
        @Test
        void xAndYSetters()
        {
            TestBox box = new TestBox();

            assertSame(box, box.x(12).y(34));
            assertEquals(12, box.x());
            assertEquals(34, box.y());
            assertEquals(12.0, box.xMeter(), 0.0);
            assertEquals(34.0, box.yMeter(), 0.0);
        }

        @Test
        void meterSetters()
        {
            TestBox box = new TestBox();

            box.xMeter(2.5).yMeter(3.5);
            assertEquals(2.5, box.xMeter(), 0.0);
            assertEquals(3.5, box.yMeter(), 0.0);
        }

        @Test
        void anchorMethods()
        {
            TestBox box = new TestBox();

            assertSame(box, box.anchor(10, 20));
            assertEquals(10, box.x());
            assertEquals(20, box.y());

            assertSame(box, box.anchorMeter(1.5, 2.5));
            assertEquals(1.5, box.xMeter(), 0.0);
            assertEquals(2.5, box.yMeter(), 0.0);
        }
    }

    @Nested
    class PixelPerMeterTest
    {
        @Test
        void updateValueScalesFromCurrentFactor()
        {
            TestBox box = new TestBox();
            box.pixelPerMeter(2.0);

            assertEquals(12.5, box.updateValue(5.0, 5.0), 0.0);
            assertEquals(-7.5, box.updateValue(-3.0, 5.0), 0.0);
        }

        @Test
        void scalesCoordinatesAndSize()
        {
            TestBox box = new TestBox();
            box.x(10).y(7).width(30).height(20);
            box.measure();

            assertEquals(10, box.x());
            assertEquals(7, box.y());
            assertEquals(-13, box.yTop());
            assertEquals(30, box.width());
            assertEquals(20, box.height());

            assertSame(box, box.pixelPerMeter(2.0));

            assertEquals(2.0, box.pixelPerMeter(), 0.0);
            assertEquals(20, box.x());
            assertEquals(14, box.y());
            assertEquals(-26, box.yTop());
            assertEquals(60, box.width());
            assertEquals(40, box.height());
        }

        @Test
        void rejectsNonPositiveValues()
        {
            TestBox box = new TestBox();

            assertThrows(IllegalArgumentException.class,
                () -> box.pixelPerMeter(0));
            assertThrows(IllegalArgumentException.class,
                () -> box.pixelPerMeter(-1));
        }
    }

    @Nested
    class StateToggleTest
    {
        @Test
        void enableDisableToggle()
        {
            TestBox box = new TestBox();

            assertSame(box, box.disable());
            assertTrue(box.disabled);
            assertSame(box, box.toggle());
            assertFalse(box.disabled);
            assertSame(box, box.enable());
            assertFalse(box.disabled);
            assertSame(box, box.disabled(false));
            assertFalse(box.disabled);
            assertSame(box, box.toggle());
            assertTrue(box.disabled);
        }
    }

    @Nested
    class CollectionAndRenderTest
    {
        @Test
        void iteratorReturnsDirectChildren()
        {
            TestBox parent = new TestBox();
            TestBox child1 = new TestBox();
            TestBox child2 = new TestBox();
            parent.addChildBox(child1);
            parent.addChildBox(child2);

            List<Box> actual = new ArrayList<>();
            parent.iterator().forEachRemaining(actual::add);

            assertEquals(2, actual.size());
            assertSame(child1, actual.get(0));
            assertSame(child2, actual.get(1));
        }

        @Test
        void renderAndRemeasure()
        {
            TestBox box = new TestBox();
            box.width(50);
            box.render(null);

            assertEquals(1, box.drawCalls);

            box.remeasure();
            box.render(null);
            assertEquals(2, box.drawCalls);

            box.render(null, 2.0);
            assertEquals(3, box.drawCalls);
        }

        @Test
        void renderHonorsDisabledState()
        {
            TestBox box = new TestBox();

            box.render(null);
            assertEquals(1, box.drawCalls);

            box.disable();
            box.render(null);
            assertEquals(1, box.drawCalls);

            box.enable();
            box.render(null);
            assertEquals(2, box.drawCalls);
        }
    }

    @Nested
    class FormattingTest
    {
        @Test
        void toStringCleanAndOriginal()
        {
            TestBox box = new TestBox();
            box.width(10).height(20);

            String original = box.toString(false);
            String cleaned = box.toString(true);

            assertNotNull(original);
            assertEquals(ToStringFormatter.clean(original), cleaned);
        }
    }
}
