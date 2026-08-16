/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/test/java/de/gurkenlabs/litiengine/util/ArrayUtilitiesTests.java
 *
 * MIT License
 *
 * Copyright (c) 2016 - 2025 Gurkenlabs
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package pi.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.logging.Logger;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ArrayUtilTests
{

    @BeforeEach
    void setUp()
    {
        Logger.getLogger(ArrayUtil.class.getName()).setUseParentHandlers(false);
    }

    @Test
    void append()
    {
        Integer[] test = new Integer[] { 1, 2, 3, 4, 5 };
        Integer[] result = ArrayUtil.append(test, 6);

        assertArrayEquals(new Integer[] { 1, 2, 3, 4, 5, 6 }, result);
    }

    @ParameterizedTest
    @MethodSource("getContains")
    void contains(Object[] array, Object value, Boolean expected)
    {
        assertEquals(expected, ArrayUtil.contains(array, value));
    }

    private static Stream<Arguments> getContains()
    {
        return Stream
            .of(Arguments.of(new Object[]
            { 1, 2, 3, 4, 5, null }, 2, true),
                Arguments.of(new Object[]
                { 1, 2, 3, 4, 5, null }, null, true),
                Arguments.of(new Object[]
                {}, "", false),
                Arguments.of(new Object[]
                { null }, null, true),
                Arguments.of(new Object[]
                { 4 }, 4, true));
    }

    @ParameterizedTest
    @MethodSource("getContainsString")
    void containsString(String[] string, String argument, Boolean ignoreCase,
            Boolean expected)
    {
        assertEquals(expected,
            ArrayUtil.contains(string, argument, ignoreCase));
    }

    private static Stream<Arguments> getContainsString()
    {
        return Stream
            .of(Arguments.of(new String[]
            { "test", "test123" }, "Test", true, true),
                Arguments.of(new String[]
                { "test", "test123" }, "Test", false, false),
                Arguments.of(new String[]
                { "test", "test123", null, "" }, "Test", false, false),
                Arguments.of(new String[]
                { "test", "test123", null, "" }, null, false, false),
                Arguments.of(new String[]
                {}, "", true, false),
                Arguments.of(new String[]
                {}, "", true, false),
                Arguments.of(new String[]
                { null }, null, false, false),
                Arguments.of(new String[]
                { null }, null, true, false),
                Arguments.of(new String[]
                { "test" }, "Test", true, true),
                Arguments.of(new String[]
                { "test" }, "test", false, true),
                Arguments.of(null, null, false, false));
    }

    @Test
    void remove()
    {
        Integer[] test = new Integer[] { 1, 2, 3, 4, 5 };
        Integer[] result = ArrayUtil.remove(test, 6);

        assertArrayEquals(new Integer[] { 1, 2, 3, 4, 5 }, result);
    }
}
