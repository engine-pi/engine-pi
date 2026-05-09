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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Type;
import java.util.stream.Stream;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static pi.util.ReflectionUtil.getField;
import static pi.util.ReflectionUtil.setValue;
import static pi.util.ReflectionUtil.isWrapperType;
import static pi.util.ReflectionUtil.getGenericOfInterface;

class ReflectionUtilTests
{
    @Test
    void testGetField()
    {
        assertNotNull(getField(TestImpl.class, "integerField"));
        assertNotNull(getField(ChildImpl.class, "integerField"));
        assertNull(getField(TestImpl.class, "nananananan"));
    }

    @Test
    void testSetValue()
    {
        var test = new TestImpl();
        assertDoesNotThrow(
            () -> setValue(TestImpl.class, test, "integerField", 12));
        assertEquals(12, test.integerField);
    }

    @Nested
    class IsWrapperTypeTest
    {
        @ParameterizedTest
        @MethodSource("getWrapperParameters")
        void isTrue(Class<?> primitive, Class<?> wrapper)
        {
            // act
            boolean isWrapper = isWrapperType(primitive, wrapper);

            // assert
            assertTrue(isWrapper);
        }

        @ParameterizedTest
        @MethodSource("getNonWrapperParameters")
        void isFalse(Class<?> primitive, Class<?> wrapper)
        {
            // act
            boolean isWrapper = isWrapperType(primitive, wrapper);

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

    }

    private class TestImpl
    {
        private int integerField;
    }

    @Nested
    class GetGenericOfInterfaceTest
    {

        @Test
        void returnsGenericForMatchingInterface()
        {
            Type genericType = getGenericOfInterface(StringProcessor.class,
                Processor.class);

            assertEquals(String.class, genericType);
        }

        @Test
        void returnsGenericForSuperclassImplementation()
        {
            Type genericType = getGenericOfInterface(
                InheritedStringProcessor.class,
                Processor.class);

            assertEquals(String.class, genericType);
        }

        @Test
        void returnsNullForNonMatchingInterface()
        {
            Type genericType = getGenericOfInterface(StringProcessor.class,
                Runnable.class);

            assertNull(genericType);
        }

        private interface Processor<T>
        {
        }

        private static class StringProcessor
                implements Processor<String>, Marker
        {
        }

        private static class InheritedStringProcessor extends StringProcessor
        {
        }
    }

    private class ChildImpl extends TestImpl
    {
    }

    private interface Marker
    {
    }

}
