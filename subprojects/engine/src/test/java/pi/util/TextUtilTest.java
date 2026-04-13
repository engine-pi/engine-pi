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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pi.graphics.boxes.HAlign.CENTER;
import static pi.graphics.boxes.HAlign.LEFT;
import static pi.graphics.boxes.HAlign.RIGHT;
import static pi.util.TextUtil.align;
import static pi.util.TextUtil.convertToMultilineString;
import static pi.util.TextUtil.convertToString;
import static pi.util.TextUtil.getLineCount;
import static pi.util.TextUtil.getLineWidth;
import static pi.util.TextUtil.wrap;
import static pi.util.TextUtil.normalizeLineSeparator;
import static pi.util.TextUtil.splitLines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import pi.EnLocale;

class TextUtilTest
{
    @ExtendWith(EnLocale.class)
    @Nested
    class RoundNumberTest
    {

        @Test
        void withoutDecimalPlaces()
        {
            assertEquals("1.2", TextUtil.roundNumber(1.2345));
        }

        @Test
        void withDecimalPlaces()
        {
            assertEquals("1.234", TextUtil.roundNumber(1.2345, 3));
        }
    }

    @Test
    void getLineWidthMethod()
    {
        assertEquals(9, getLineWidth("Lorem\nipsum\ndolor sit"));
    }

    @Test
    void getLineCountMethod()
    {
        assertEquals(3, getLineCount("Lorem\nipsum\ndolor sit"));
    }

    @Nested
    class NormalizeLineSeparatorTest
    {
        @Test
        void unixNewlineIsUnchanged()
        {
            assertEquals("line1\nline2",
                normalizeLineSeparator("line1\nline2"));
        }

        @Test
        void windowsLineEndingIsReplaced()
        {
            assertEquals("line1\nline2",
                normalizeLineSeparator("line1\r\nline2"));
        }

        @Test
        void classicMacLineEndingIsReplaced()
        {
            assertEquals("line1\nline2",
                normalizeLineSeparator("line1\rline2"));
        }

        @Test
        void mixedLineEndingsAreNormalized()
        {
            assertEquals("a\nb\nc\nd", normalizeLineSeparator("a\r\nb\rc\nd"));
        }

        @Test
        void emptyStringRemainsEmpty()
        {
            assertEquals("", normalizeLineSeparator(""));
        }

        @Test
        void noLineEndingIsUnchanged()
        {
            assertEquals("hello", normalizeLineSeparator("hello"));
        }

        @Test
        void multipleWindowsLineEndings()
        {
            assertEquals("a\nb\nc", normalizeLineSeparator("a\r\nb\r\nc"));
        }
    }

    @Nested
    class AlignTest
    {
        String text = "Lorem ipsum\ndolor sit\namet.";

        String oneLine = "Lorem ipsum";

        @Test
        void oneLine()
        {
            assertEquals("Lorem ipsum", align(oneLine, LEFT));
        }

        @Test
        void left()
        {
            assertEquals("""
                    Lorem ipsum
                    dolor sit
                    amet.""", align(text, LEFT));
        }

        @Test
        void width()
        {
            assertEquals("""
                    Lorem ipsum
                    dolor sit
                    amet.""", align(text, 15, LEFT));
        }

        @Test
        void widthOnOneLine()
        {
            assertEquals("Lorem ipsum", align(oneLine, 15, LEFT));
        }

        @Test
        void right()
        {
            assertEquals("""
                    Lorem ipsum
                      dolor sit
                          amet.""", align(text, RIGHT));
        }

        @Test
        void center()
        {
            assertEquals("""
                    Lorem ipsum
                     dolor sit
                       amet.""", align(text, CENTER));
        }
    }

    @Nested
    class WrapTest
    {
        String text = "Lorem ipsum dolor sit";

        @Test
        void left()
        {
            assertEquals("Lorem\nipsum\ndolor sit", wrap(text, 10, LEFT));
        }

        @Test
        void center()
        {
            assertEquals("""
                      Lorem
                      ipsum
                    dolor sit""", wrap(text, 10, CENTER));
        }

        @Test
        void right()
        {
            assertEquals("""
                         Lorem
                         ipsum
                    \sdolor sit""", wrap(text, 10, RIGHT));
        }

        @Test
        void widthNotToSmall()
        {
            assertEquals("Lorem\nipsum\ndolor sit", wrap(text, 10));
        }

        @Test
        void widthToSmall()
        {
            assertThrows(IllegalArgumentException.class, () -> wrap(text, 4));
        }

        @Test
        void inputWithNewlines()
        {
            assertEquals("Lorem\nipsum\ndolor\nsit",
                wrap("Lorem\nipsum\ndolor\nsit", 10));
        }
    }

    @Nested
    class ConvertToStringTest
    {
        @Test
        void nullValue()
        {
            assertEquals("", convertToString(null));
        }

        @Test
        void simpleString()
        {
            assertEquals("hello", convertToString("hello"));
        }

        @Test
        void integer()
        {
            assertEquals("42", convertToString(42));
        }

        @Test
        void doubleValue()
        {
            assertEquals("3.14", convertToString(3.14));
        }

        @Test
        void emptyList()
        {
            assertEquals("[]", convertToString(List.of()));
        }

        @Test
        void listWithStrings()
        {
            assertEquals("[a, b, c]", convertToString(List.of("a", "b", "c")));
        }

        @Test
        void listWithIntegers()
        {
            assertEquals("[1, 2, 3]", convertToString(List.of(1, 2, 3)));
        }

        @Test
        void listWithMixedTypes()
        {
            assertEquals("[a, 1, b, 2]",
                convertToString(List.of("a", 1, "b", 2)));
        }

        @Test
        void listWithNullElement()
        {
            List<Object> list = new ArrayList<>();
            list.add("a");
            list.add(null);
            list.add("b");
            assertEquals("[a, , b]", convertToString(list));
        }

        @Test
        void emptySet()
        {
            assertEquals("[]", convertToString(Set.of()));
        }

        @Test
        void setWithElements()
        {
            String result = convertToString(Set.of("x", "y", "z"));
            assertEquals(true, result.startsWith("["));
            assertEquals(true, result.endsWith("]"));
            assertEquals(true, result.contains("x"));
            assertEquals(true, result.contains("y"));
            assertEquals(true, result.contains("z"));
        }

        @Test
        void emptyMap()
        {
            assertEquals("{}", convertToString(Map.of()));
        }

        @Test
        void mapWithOneEntry()
        {
            assertEquals("{key=value}",
                convertToString(Map.of("key", "value")));
        }

        @Test
        void mapWithMultipleEntries()
        {
            Map<String, String> map = new HashMap<>();
            map.put("a", "1");
            map.put("b", "2");
            String result = convertToString(map);
            assertEquals(true, result.startsWith("{"));
            assertEquals(true, result.endsWith("}"));
            assertEquals(true, result.contains("a=1"));
            assertEquals(true, result.contains("b=2"));
        }

        @Test
        void mapWithIntegerValues()
        {
            String result = convertToString(Map.of("x", 10, "y", 20));
            assertTrue(
                result.equals("{x=10, y=20}") || result.equals("{y=20, x=10}"));
        }

        @Test
        void objectArrayWithStrings()
        {
            String[] array = { "a", "b", "c" };
            assertEquals("[a, b, c]", convertToString(array));
        }

        @Test
        void objectArrayWithIntegers()
        {
            int[] array = { 1, 2, 3 };
            assertEquals("[1, 2, 3]", convertToString(array));
        }

        @Test
        void objectArrayEmpty()
        {
            double[] array = {};
            assertEquals("[]", convertToString(array));
        }

        @Test
        void nestedList()
        {
            List<String> inner = List.of("x", "y");
            List<Object> outer = new ArrayList<>();
            outer.add("a");
            outer.add(inner);
            outer.add("b");
            assertEquals("[a, [x, y], b]", convertToString(outer));
        }

        @Test
        void nestedMap()
        {
            Map<String, Object> inner = new HashMap<>();
            inner.put("key1", "val1");
            Map<String, Object> outer = new HashMap<>();
            outer.put("nested", inner);
            String result = convertToString(outer);
            assertEquals(true, result.contains("nested={"));
            assertEquals(true, result.contains("key1=val1"));
        }

        @Test
        void listInsideMap()
        {
            Map<String, Object> map = new HashMap<>();
            map.put("items", List.of(1, 2, 3));
            String result = convertToString(map);
            assertEquals(true, result.contains("items=[1, 2, 3]"));
        }

        @Test
        void arrayWithNull()
        {

            assertEquals("[a, , b]",
                convertToString(new Object[]
                { "a", null, "b" }));
        }
    }

    @Nested
    class ConvertToMultilineStringTest
    {
        @Test
        void emptyVarargs()
        {
            assertEquals("", convertToMultilineString());
        }

        @Test
        void nullVarargs()
        {
            assertEquals("", convertToMultilineString((Object[]) null));
        }

        @Test
        void singleElement()
        {
            assertEquals("hello", convertToMultilineString("hello"));
        }

        @Test
        void multipleElements()
        {
            assertEquals("a\n1\ntrue", convertToMultilineString("a", 1, true));
        }

        @Test
        void nullElement()
        {
            assertEquals("a\n\nb", convertToMultilineString("a", null, "b"));
        }

        @Test
        void collectionTypesUseConvertToString()
        {
            assertEquals("[1, 2]\n{x=10}",
                convertToMultilineString(List.of(1, 2), Map.of("x", 10)));
        }
    }

}
