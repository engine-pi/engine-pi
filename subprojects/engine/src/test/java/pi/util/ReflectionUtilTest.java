/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/test/java/de/gurkenlabs/litiengine/util/ReflectionUtilitiesTests.java
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

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class ReflectionUtilTests
{
    @Test
    void testGetField()
    {
        assertNotNull(ReflectionUtil.getField(TestImpl.class, "integerField"));
        assertNotNull(ReflectionUtil.getField(ChildImpl.class, "integerField"));
        assertNull(ReflectionUtil.getField(TestImpl.class, "nananananan"));
    }

    @Test
    void testSetValue()
    {
        var test = new TestImpl();
        assertDoesNotThrow(() -> ReflectionUtil.setValue(TestImpl.class, test,
                "integerField", 12));
        assertEquals(12, test.integerField);
    }

    @ParameterizedTest
    @MethodSource("getWrapperParameters")
    void testIsWrapperTypeTrue(Class<?> primitive, Class<?> wrapper)
    {
        // act
        boolean isWrapper = ReflectionUtil.isWrapperType(primitive, wrapper);

        // assert
        assertTrue(isWrapper);
    }

    @ParameterizedTest
    @MethodSource("getNonWrapperParameters")
    void testIsWrapperTypeFalse(Class<?> primitive, Class<?> wrapper)
    {
        // act
        boolean isWrapper = ReflectionUtil.isWrapperType(primitive, wrapper);

        // assert
        assertFalse(isWrapper);
    }

    private static Stream<Arguments> getWrapperParameters()
    {
        // arrange
        return Stream.of(Arguments.of(boolean.class, Boolean.class),
                Arguments.of(char.class, Character.class),
                Arguments.of(byte.class, Byte.class),
                Arguments.of(short.class, Short.class),
                Arguments.of(int.class, Integer.class),
                Arguments.of(long.class, Long.class),
                Arguments.of(float.class, Float.class),
                Arguments.of(double.class, Double.class),
                Arguments.of(void.class, Void.class));
    }

    private static Stream<Arguments> getNonWrapperParameters()
    {
        // arrange
        return Stream.of(Arguments.of(Boolean.class, boolean.class),
                Arguments.of(char.class, Byte.class));
    }

    private class TestImpl
    {
        @SuppressWarnings("unused")
        private int integerField;
    }

    private class ChildImpl extends TestImpl
    {
    }
}
