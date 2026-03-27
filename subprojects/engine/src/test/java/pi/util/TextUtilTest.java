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
package pi.util;

import static pi.util.TextAlignment.CENTER;
import static pi.util.TextAlignment.LEFT;
import static pi.util.TextAlignment.RIGHT;
import static pi.util.TextUtil.align;
import static pi.util.TextUtil.getLineCount;
import static pi.util.TextUtil.getLineWidth;
import static pi.util.TextUtil.wrap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import pi.EnLocale;

public class TextUtilTest
{
    @ExtendWith(EnLocale.class)
    @Nested
    class RoundNumberTest
    {

        @Test
        void withoutDecimalPlaces()
        {
            assertEquals(TextUtil.roundNumber(1.2345), "1.2");
        }

        @Test
        void withDecimalPlaces()
        {
            assertEquals(TextUtil.roundNumber(1.2345, 3), "1.234");
        }
    }

    @Test
    void getLineWidthMethod()
    {
        assertEquals(getLineWidth("Lorem\nipsum\ndolor sit"), 9);
    }

    @Test
    void getLineCountMethod()
    {
        assertEquals(getLineCount("Lorem\nipsum\ndolor sit"), 3);
    }

    @Nested
    class AlignTest
    {
        String text = "Lorem ipsum\ndolor sit\namet.";

        String oneLine = "Lorem ipsum";

        @Test
        void oneLine()
        {
            assertEquals(align(oneLine, LEFT), "Lorem ipsum");
        }

        @Test
        void left()
        {
            assertEquals(align(text, LEFT),
                "Lorem ipsum\n" + "dolor sit\n" + "amet.");
        }

        @Test
        void width()
        {
            assertEquals(align(text, 15, LEFT),
                "Lorem ipsum\n" + "dolor sit\n" + "amet.");
        }

        @Test
        void widthOnOneLine()
        {
            assertEquals(align(oneLine, 15, LEFT), "Lorem ipsum");
        }

        @Test
        void right()
        {
            assertEquals(align(text, RIGHT),
                "Lorem ipsum\n" + "  dolor sit\n" + "      amet.");
        }

        @Test
        void center()
        {
            assertEquals(align(text, CENTER),
                "Lorem ipsum\n" + " dolor sit\n" + "   amet.");
        }
    }

    @Nested
    class WrapTest
    {
        String text = "Lorem ipsum dolor sit";

        @Test
        void left()
        {
            assertEquals(wrap(text, 10, LEFT), "Lorem\nipsum\ndolor sit");
        }

        @Test
        void center()
        {
            assertEquals(wrap(text, 10, CENTER),
                "  Lorem\n" + "  ipsum\n" + "dolor sit");
        }

        @Test
        void right()
        {
            assertEquals(wrap(text, 10, RIGHT),
                "     Lorem\n" + "     ipsum\n" + " dolor sit");
        }

        @Test
        void widthNotToSmall()
        {
            assertEquals(wrap(text, 10), "Lorem\nipsum\ndolor sit");
        }

        @Test
        void widthToSmall()
        {
            assertThrows(IllegalArgumentException.class, () -> wrap(text, 4));
        }

        @Test
        void inputWithNewlines()
        {
            assertEquals(wrap("Lorem\nipsum\ndolor\nsit", 10),
                "Lorem\nipsum\ndolor\nsit");
        }
    }
}
