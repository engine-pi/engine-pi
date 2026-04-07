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
import static pi.util.TextAlignment.CENTER;
import static pi.util.TextAlignment.LEFT;
import static pi.util.TextAlignment.RIGHT;
import static pi.util.TextUtil.align;
import static pi.util.TextUtil.convertToMultilineString;
import static pi.util.TextUtil.convertToString;
import static pi.util.TextUtil.getLineCount;
import static pi.util.TextUtil.getLineWidth;
import static pi.util.TextUtil.wrap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @Nested
    class ConvertToStringTest
    {
        @Test
        void nullValue()
        {
            assertEquals(convertToString(null), "");
        }

        @Test
        void simpleString()
        {
            assertEquals(convertToString("hello"), "hello");
        }

        @Test
        void integer()
        {
            assertEquals(convertToString(42), "42");
        }

        @Test
        void doubleValue()
        {
            assertEquals(convertToString(3.14), "3.14");
        }

        @Test
        void emptyList()
        {
            assertEquals(convertToString(List.of()), "[]");
        }

        @Test
        void listWithStrings()
        {
            assertEquals(convertToString(List.of("a", "b", "c")), "[a, b, c]");
        }

        @Test
        void listWithIntegers()
        {
            assertEquals(convertToString(List.of(1, 2, 3)), "[1, 2, 3]");
        }

        @Test
        void listWithMixedTypes()
        {
            assertEquals(convertToString(List.of("a", 1, "b", 2)),
                "[a, 1, b, 2]");
        }

        @Test
        void listWithNullElement()
        {
            List<Object> list = new ArrayList<>();
            list.add("a");
            list.add(null);
            list.add("b");
            assertEquals(convertToString(list), "[a, , b]");
        }

        @Test
        void emptySet()
        {
            assertEquals(convertToString(Set.of()), "[]");
        }

        @Test
        void setWithElements()
        {
            String result = convertToString(Set.of("x", "y", "z"));
            assertEquals(result.startsWith("["), true);
            assertEquals(result.endsWith("]"), true);
            assertEquals(result.contains("x"), true);
            assertEquals(result.contains("y"), true);
            assertEquals(result.contains("z"), true);
        }

        @Test
        void emptyMap()
        {
            assertEquals(convertToString(Map.of()), "{}");
        }

        @Test
        void mapWithOneEntry()
        {
            assertEquals(convertToString(Map.of("key", "value")),
                "{key=value}");
        }

        @Test
        void mapWithMultipleEntries()
        {
            Map<String, String> map = new HashMap<>();
            map.put("a", "1");
            map.put("b", "2");
            String result = convertToString(map);
            assertEquals(result.startsWith("{"), true);
            assertEquals(result.endsWith("}"), true);
            assertEquals(result.contains("a=1"), true);
            assertEquals(result.contains("b=2"), true);
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
            assertEquals(convertToString(array), "[a, b, c]");
        }

        @Test
        void objectArrayWithIntegers()
        {
            int[] array = { 1, 2, 3 };
            assertEquals(convertToString(array), "[1, 2, 3]");
        }

        @Test
        void objectArrayEmpty()
        {
            double[] array = {};
            assertEquals(convertToString(array), "[]");
        }

        @Test
        void nestedList()
        {
            List<String> inner = List.of("x", "y");
            List<Object> outer = new ArrayList<>();
            outer.add("a");
            outer.add(inner);
            outer.add("b");
            assertEquals(convertToString(outer), "[a, [x, y], b]");
        }

        @Test
        void nestedMap()
        {
            Map<String, Object> inner = new HashMap<>();
            inner.put("key1", "val1");
            Map<String, Object> outer = new HashMap<>();
            outer.put("nested", inner);
            String result = convertToString(outer);
            assertEquals(result.contains("nested={"), true);
            assertEquals(result.contains("key1=val1"), true);
        }

        @Test
        void listInsideMap()
        {
            Map<String, Object> map = new HashMap<>();
            map.put("items", List.of(1, 2, 3));
            String result = convertToString(map);
            assertEquals(result.contains("items=[1, 2, 3]"), true);
        }

        @Test
        void arrayWithNull()
        {
            Object[] array = { "a", null, "b" };
            assertEquals(convertToString(array), "[a, , b]");
        }
    }

    @Nested
    class ConvertToMultilineStringTest
    {
        @Test
        void emptyVarargs()
        {
            assertEquals(convertToMultilineString(), "");
        }

        @Test
        void nullVarargs()
        {
            assertEquals(convertToMultilineString((Object[]) null), "");
        }

        @Test
        void singleElement()
        {
            assertEquals(convertToMultilineString("hello"), "hello");
        }

        @Test
        void multipleElements()
        {
            assertEquals(convertToMultilineString("a", 1, true), "a\n1\ntrue");
        }

        @Test
        void nullElement()
        {
            assertEquals(convertToMultilineString("a", null, "b"), "a\n\nb");
        }

        @Test
        void collectionTypesUseConvertToString()
        {
            assertEquals(
                convertToMultilineString(List.of(1, 2), Map.of("x", 10)),
                "[1, 2]\n{x=10}");
        }
    }
}
