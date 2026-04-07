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
package pi.actor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pi.CustomAssertions.assertToStringClassName;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import pi.Controller;
import pi.graphics.geom.Vector;

/**
 * @author Josef Friedrich
 *
 * @since 0.45.0
 */
public class LineTest
{
    Line line;

    @BeforeEach
    public void setUp()
    {
        Controller.instantMode(false);
        line = new Line(1, 2, 3, 4);
    }

    @Test
    void end1Getter()
    {
        Vector end1 = line.end1();
        assertEquals(1, end1.x(), 0.001);
        assertEquals(2, end1.y(), 0.001);
    }

    @Test
    void end1Setter()
    {
        line.end1(new Vector(7, 8));
        Vector end1 = line.end1();
        assertEquals(7, end1.x(), 0.001);
        assertEquals(8, end1.y(), 0.001);
    }

    @Test
    void end2Getter()
    {
        Vector end2 = line.end2();
        assertEquals(3, end2.x(), 0.001);
        assertEquals(4, end2.y(), 0.001);
    }

    @Test
    void end2Setter()
    {

        line.end2(new Vector(7, 8));
        Vector end2 = line.end2();
        assertEquals(7, end2.x(), 0.001);
        assertEquals(8, end2.y(), 0.001);
    }

    @Test
    void strokeWidth()
    {
        line.strokeWidth(1);
        assertEquals(1, line.strokeWidth(), 0.001);
    }

    @Nested
    class RoundedTest
    {
        @Test
        void defaultGetterReturnsFalse()
        {
            assertFalse(line.rounded());
        }

        @Test
        void setterReturnsSameInstanceAndSetsTrue()
        {
            Line returned = line.rounded(true);

            assertSame(line, returned);
            assertTrue(line.rounded());
            assertEquals(Line.Cap.ROUND, line.cap());
        }

        @Test
        void roundedTrueOverridesPreviouslySetNonRoundCap()
        {
            line.cap(Line.Cap.BUTT);
            assertEquals(Line.Cap.BUTT, line.cap());
            assertFalse(line.rounded());

            line.rounded(true);

            assertTrue(line.rounded());
            assertEquals(Line.Cap.ROUND, line.cap());
        }

        @Test
        void roundedFalseKeepsExplicitNonRoundCap()
        {
            line.cap(Line.Cap.SQUARE);
            assertEquals(Line.Cap.SQUARE, line.cap());

            Line returned = line.rounded(false);

            assertSame(line, returned);
            assertFalse(line.rounded());
            assertEquals(Line.Cap.SQUARE, line.cap());
        }
    }

    @Nested
    class CapRoundedTest
    {
        @Test
        void defaultGetterReturnsButt()
        {
            assertEquals(Line.Cap.BUTT, line.cap());
        }

        @Test
        void getterSetter()
        {
            Line returned = line.cap(Line.Cap.SQUARE);
            assertSame(line, returned);
            assertEquals(Line.Cap.SQUARE, line.cap());
        }

        @Test
        void roundCapSetsRounded()
        {
            assertFalse(line.rounded());

            Line returned = line.cap(Line.Cap.ROUND);

            assertSame(line, returned);
            assertEquals(Line.Cap.ROUND, line.cap());
            assertTrue(line.rounded());
        }

        @Test
        void roundedTrueAlsoMakesCapRound()
        {
            Line returned = line.rounded(true);

            assertSame(line, returned);
            assertTrue(line.rounded());
            assertEquals(Line.Cap.ROUND, line.cap());
        }

        @Test
        void nonRoundCapResetsRounded()
        {
            line.rounded(true);
            assertTrue(line.rounded());
            assertEquals(Line.Cap.ROUND, line.cap());

            Line returned = line.cap(Line.Cap.BUTT);

            assertSame(line, returned);
            assertEquals(Line.Cap.BUTT, line.cap());
            assertFalse(line.rounded());
        }
    }

    @Test
    void offset()
    {
        line.offset(0.5);
        assertEquals(0.5, line.offset(), 0.001);
        assertEquals(0.5, line.end1.offset(), 0.001);
        assertEquals(0.5, line.end2.offset(), 0.001);

        line.end1.offset(1);
        line.end2.offset(2);

        assertThrows(RuntimeException.class, () -> line.offset());
    }

    @Nested
    class LineEndTest
    {
        @Test
        void endGetterSetter()
        {
            Line.End returned = line.end1.end(new Vector(10, 20));
            assertSame(line.end1, returned);

            Vector end = line.end1.end();
            assertEquals(10, end.x(), 0.001);
            assertEquals(20, end.y(), 0.001);

            Vector lineEnd = line.end1();
            assertEquals(10, lineEnd.x(), 0.001);
            assertEquals(20, lineEnd.y(), 0.001);
        }

        @Test
        void offsetGetterSetter()
        {
            assertEquals(0, line.end1.offset(), 0.001);

            Line.End returned = line.end1.offset(0.75);
            assertSame(line.end1, returned);
            assertEquals(0.75, line.end1.offset(), 0.001);

            double expected = 1 + 0.75 / Math.sqrt(2);
            assertEquals(expected, line.end1().x(), 0.001);
            assertEquals(2 + 0.75 / Math.sqrt(2), line.end1().y(), 0.001);
        }

        @Nested
        class ArrowTest
        {
            @Test
            void getterSetterWithEnum()
            {
                line.end1.arrow(Line.ArrowType.TRIANGLE);
                assertEquals(Line.ArrowType.TRIANGLE, line.end1.arrow());
            }

            @Test
            void getterSetterWithBoolean()
            {
                line.end1.arrow(true);
                assertEquals(Line.ArrowType.CHEVRON, line.end1.arrow());

                line.end1.arrow(false);
                assertEquals(Line.ArrowType.NONE, line.end1.arrow());
            }
        }

        @Test
        void arrowAngleGetterSetter()
        {
            assertEquals(45, line.end1.arrowAngle(), 0.001);

            Line.End returned = line.end1.arrowAngle(30);
            assertSame(line.end1, returned);
            assertEquals(30, line.end1.arrowAngle(), 0.001);
        }

        @Test
        void arrowSideLengthSetter()
        {
            Line.End returned = line.end1.arrowSideLength(1.25);
            assertSame(line.end1, returned);
            assertEquals(1.25, line.end1.arrowSideLength(), 0.001);
        }
    }

    @Nested
    class ToStringTest
    {
        @Test
        void className()
        {
            assertToStringClassName(line);
        }
    }
}
