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

import java.util.Arrays;
import java.util.List;
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
    public void setup()
    {
        Logger.getLogger(ArrayUtil.class.getName()).setUseParentHandlers(false);
    }

    @Test
    void testByteArrayConcat()
    {
        byte[] arr1 = new byte[] { 1, 2, 3, 4, 5 };
        byte[] arr2 = new byte[] { 6, 7, 8, 9 };

        byte[] arr3 = ArrayUtil.concat(arr1, arr2);

        assertArrayEquals(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }, arr3);
    }

    @Test
    void testIntArrayConcat()
    {
        int[] arr1 = new int[] { 1, 2, 3, 4, 5 };
        int[] arr2 = new int[] { 6, 7, 8, 9 };

        int[] arr3 = ArrayUtil.concat(arr1, arr2);

        assertArrayEquals(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }, arr3);
    }

    @Test
    void testLongArrayConcat()
    {
        long[] arr1 = new long[] { 1, 2, 3, 4, 5 };
        long[] arr2 = new long[] { 6, 7, 8, 9 };

        long[] arr3 = ArrayUtil.concat(arr1, arr2);

        assertArrayEquals(new long[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }, arr3);
    }

    @Test
    void testDoubleArrayConcat()
    {
        double[] arr1 = new double[] { 1.0, 2.0, 3.0, 4.0, 5.0 };
        double[] arr2 = new double[] { 6.0, 7.0, 8.0, 9.0 };

        double[] arr3 = ArrayUtil.concat(arr1, arr2);

        assertArrayEquals(
            new double[]
            { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0 },
            arr3);
    }

    @Test
    void testTArrayConcat()
    {
        Integer[] arr1 = { 1, 2, 3, 4, 5 };
        Integer[] arr2 = { 6, 7, 8, 9 };

        Integer[] arr3 = ArrayUtil.concat(arr1, arr2);

        assertArrayEquals(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }, arr3);
    }

    @Test
    void testIntegerArrayFromCommaSeparatedString()
    {
        String testStringWithInts = "100,200,300,1,2,3";
        String testStringWithoutInts = "paslikodja,2asdasd,sadasd";
        String testNull = null;
        String testEmpty = "";
        String testDelimiterEmpty = ",";

        int[] intsFromString = ArrayUtil.splitInt(testStringWithInts);
        int[] stringWithoutInts = ArrayUtil.splitInt(testStringWithoutInts);
        int[] nullString = ArrayUtil.splitInt(testNull);
        int[] emptyString = ArrayUtil.splitInt(testEmpty);
        int[] emptyDelimiterString = ArrayUtil.splitInt(testDelimiterEmpty);

        assertArrayEquals(new int[] { 100, 200, 300, 1, 2, 3 }, intsFromString);
        assertArrayEquals(new int[] { 0, 0, 0 }, stringWithoutInts);
        assertArrayEquals(new int[] {}, nullString);
        assertArrayEquals(new int[] {}, emptyString);
        assertArrayEquals(new int[] {,}, emptyDelimiterString);
    }

    @Test
    void testDoubleArrayFromCommaSeparatedString()
    {
        String testStringWithDoubles = "100.1,200.2,300.3,1.4,2.5,3.6";
        String testStringWithoutDoubles = "paslikodja,2asdasd,sadasd";
        String testNull = null;
        String testEmpty = "";
        String testDelimiterEmpty = ",";

        double[] doublesFromString = ArrayUtil
            .splitDouble(testStringWithDoubles);
        double[] stringWithoutDoubles = ArrayUtil
            .splitDouble(testStringWithoutDoubles);
        double[] nullString = ArrayUtil.splitDouble(testNull);
        double[] emptyString = ArrayUtil.splitDouble(testEmpty);
        double[] emptyDelimiterString = ArrayUtil
            .splitDouble(testDelimiterEmpty);

        assertArrayEquals(new double[] { 100.1, 200.2, 300.3, 1.4, 2.5, 3.6 },
            doublesFromString);
        assertArrayEquals(new double[] { 0, 0, 0 }, stringWithoutDoubles);
        assertArrayEquals(new double[] {}, nullString);
        assertArrayEquals(new double[] {}, emptyString);
        assertArrayEquals(new double[] {}, emptyDelimiterString);
    }

    @Test
    void testCommaSeparatedStringFromIntegerArray()
    {
        int[] intsArr = new int[] { 100, 200, 300, 1, 2, 3 };
        String testStringWithInts = ArrayUtil.join(intsArr);
        assertEquals("100,200,300,1,2,3", testStringWithInts);
    }

    @Test
    void testCommaSeparatedStringFromIntegerArrayEmpty()
    {
        String testEmpty = ArrayUtil.join(new int[] {});
        assertEquals("", testEmpty);
    }

    @Test
    void testCommaSeparatedStringFromIntegerArrayDelimiter()
    {
        int[] intsArr = new int[] { 100, 200, 300, 1, 2, 3 };
        String testStringWithIntsDelimiter = ArrayUtil.join(intsArr, ";");
        assertEquals("100;200;300;1;2;3", testStringWithIntsDelimiter);
    }

    @Test
    void testCommaSeparatedStringFromBooleanArray()
    {
        boolean[] boolArr = new boolean[] { true, false, true };
        String testStringWithBool = ArrayUtil.join(boolArr);
        assertEquals("true,false,true", testStringWithBool);
    }

    @Test
    void testCommaSeparatedStringFromBooleanEmpty()
    {
        String testEmpty = ArrayUtil.join(new boolean[] {});
        assertEquals("", testEmpty);
    }

    @Test
    void testCommaSeparatedStringFromBooleanArrayDelimiter()
    {
        boolean[] boolArr = new boolean[] { true, false, true };
        String testStringWithBoolDelimiter = ArrayUtil.join(boolArr, ";");
        assertEquals("true;false;true", testStringWithBoolDelimiter);
    }

    @Test
    void testCommaSeparatedStringFromDoubleArray()
    {
        double[] doubleArr = new double[] { 100.0, 200.0, 300.0, 1.0, 2.0,
                3.0 };
        String testStringWithDouble = ArrayUtil.join(doubleArr);
        assertEquals("100.0,200.0,300.0,1.0,2.0,3.0", testStringWithDouble);
    }

    @Test
    void testCommaSeparatedStringFromDoubleArrayEmpty()
    {
        String testEmpty = ArrayUtil.join(new double[] {});
        assertEquals("", testEmpty);
    }

    @Test
    void testCommaSeparatedStringFromDoubleArrayDelimiter()
    {
        double[] doubleArr = new double[] { 100.0, 200.0, 300.0, 1.0, 2.0,
                3.0 };
        String testStringWithDoubleDelimiter = ArrayUtil.join(doubleArr, ";");
        assertEquals("100.0;200.0;300.0;1.0;2.0;3.0",
            testStringWithDoubleDelimiter);
    }

    @Test
    void testCommaSeparatedStringFromFloatArray()
    {
        float[] floatArr = new float[] { 100.0f, 200.0f, 300.0f, 1.0f, 2.0f,
                3.0f };
        String testStringWithFloat = ArrayUtil.join(floatArr);
        assertEquals("100.0,200.0,300.0,1.0,2.0,3.0", testStringWithFloat);
    }

    @Test
    void testCommaSeparatedStringFromFloatArrayEmpty()
    {
        String testEmpty = ArrayUtil.join(new double[] {});
        assertEquals("", testEmpty);
    }

    @Test
    void testCommaSeparatedStringFromFloatArrayDelimiter()
    {
        float[] floatArr = new float[] { 100.0f, 200.0f, 300.0f, 1.0f, 2.0f,
                3.0f };
        String testStringWithFloatDelimiter = ArrayUtil.join(floatArr, ";");
        assertEquals("100.0;200.0;300.0;1.0;2.0;3.0",
            testStringWithFloatDelimiter);
    }

    @Test
    void testCommaSeparatedStringFromStringArray()
    {
        String[] stringArr = new String[] { "test", "test2", "test3" };
        String testStringWithString = ArrayUtil.join(stringArr);
        assertEquals("test,test2,test3", testStringWithString);
    }

    @Test
    void testCommaSeparatedStringFromStringArrayEmpty()
    {
        String testEmpty = ArrayUtil.join(new String[] {});
        assertEquals("", testEmpty);
    }

    @Test
    void testCommaSeparatedStringFromStringArrayDelimiter()
    {
        String[] stringArr = new String[] { "test", "test2", "test3" };
        String testStringWithStringDelimiter = ArrayUtil.join(stringArr, ";");
        assertEquals("test;test2;test3", testStringWithStringDelimiter);
    }

    @Test
    void testCommaSeparatedStringFromShortArray()
    {
        short[] shortArr = new short[] { 100, 200, 300, 1, 2, 3 };
        String testStringWithShort = ArrayUtil.join(shortArr);
        assertEquals("100,200,300,1,2,3", testStringWithShort);
    }

    @Test
    void testCommaSeparatedStringFromShortArrayEmpty()
    {
        String testEmpty = ArrayUtil.join(new short[] {});
        assertEquals("", testEmpty);
    }

    @Test
    void testCommaSeparatedStringFromShortArrayDelimiter()
    {
        short[] shortArr = new short[] { 100, 200, 300, 1, 2, 3 };
        String testStringWithShortDelimiter = ArrayUtil.join(shortArr, ";");
        assertEquals("100;200;300;1;2;3", testStringWithShortDelimiter);
    }

    @Test
    void testCommaSeparatedStringFromLongArray()
    {
        long[] longArr = new long[] { 100, 200, 300, 1, 2, 3 };
        String testStringWithLong = ArrayUtil.join(longArr);
        assertEquals("100,200,300,1,2,3", testStringWithLong);
    }

    @Test
    void testCommaSeparatedStringFromLongArrayEmpty()
    {
        long[] longArr = new long[] { 100, 200, 300, 1, 2, 3 };
        String testStringWithLongDelimiter = ArrayUtil.join(longArr, ";");
        assertEquals("100;200;300;1;2;3", testStringWithLongDelimiter);
    }

    @Test
    void testCommaSeparatedStringFromLongArrayDelimiter()
    {
        long[] longArr = new long[] { 100, 200, 300, 1, 2, 3 };
        String testStringWithLongDelimiter = ArrayUtil.join(longArr, ";");
        assertEquals("100;200;300;1;2;3", testStringWithLongDelimiter);
    }

    @Test
    void testCommaSeparatedStringFromByteArray()
    {
        byte[] byteArr = new byte[] { 100, 127, -128, 1, 2, 3 };
        String testStringWithByte = ArrayUtil.join(byteArr);
        assertEquals("100,127,-128,1,2,3", testStringWithByte);
    }

    @Test
    void testCommaSeparatedStringFromByteArrayEmpty()
    {
        String testEmpty = ArrayUtil.join(new byte[] {});
        assertEquals("", testEmpty);
    }

    @Test
    void testCommaSeparatedStringFromByteArrayDelimiter()
    {
        byte[] byteArr = new byte[] { 100, 127, -128, 1, 2, 3 };
        String testStringWithByteDelimiter = ArrayUtil.join(byteArr, ";");
        assertEquals("100;127;-128;1;2;3", testStringWithByteDelimiter);
    }

    @Test
    void testCommaSeparatedStringFromCollectionArray()
    {
        List<String> collectionArr = Arrays.asList("test", "test2", "test3");
        String testStringWithCollectionDelimiter = ArrayUtil.join(collectionArr,
            ";");
        assertEquals("test;test2;test3", testStringWithCollectionDelimiter);
    }

    @Test
    void testCommaSeparatedStringFromCollectionArrayEmpty()
    {
        String testEmpty = ArrayUtil.join(new String[] {});
        assertEquals("", testEmpty);
    }

    @Test
    void testCommaSeparatedStringFromCollectionArrayDelimiter()
    {
        List<String> collectionArr = Arrays.asList("test", "test2", "test3");
        String testStringWithCollectionDelimiter = ArrayUtil.join(collectionArr,
            ";");
        assertEquals("test;test2;test3", testStringWithCollectionDelimiter);
    }

    @ParameterizedTest
    @MethodSource("getArray")
    void testTwoDimensionalArrayToList(List<Integer> expectedValue)
    {
        Integer[][] arr = new Integer[][] { { 0, 0, 0, }, { 1, 1, 1, },
                { 2, 2, 2, }, };

        List<Integer> list = ArrayUtil.toList(arr);
        for (int i = 0; i < list.size(); i++)
        {
            assertEquals(expectedValue.get(i).intValue(),
                list.get(i).intValue());
        }
    }

    private static Stream<Arguments> getArray()
    {
        return Stream
            .of(Arguments.of(Arrays.asList(0, 0, 0, 1, 1, 1, 2, 2, 2)));
    }

    @Test
    void testAppend()
    {
        Integer[] test = new Integer[] { 1, 2, 3, 4, 5 };
        Integer[] result = ArrayUtil.append(test, 6);

        assertArrayEquals(new Integer[] { 1, 2, 3, 4, 5, 6 }, result);
    }

    @Test
    void testDistinct()
    {
        Integer[] first = new Integer[] { 1, 2, 3, 4, 5 };
        Integer[] second = new Integer[] { 1, 2, 3, 4, 5, 6 };
        Integer[] result = ArrayUtil.distinct(first, second);

        assertArrayEquals(new Integer[] { 1, 2, 3, 4, 5, 6 }, result);
    }

    @ParameterizedTest
    @MethodSource("getContains")
    void testContains(Object[] array, Object value, Boolean expected)
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
    void testContainsString(String[] string, String argument,
            Boolean ignoreCase, Boolean expected)
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
    void testRemove()
    {
        Integer[] test = new Integer[] { 1, 2, 3, 4, 5 };
        Integer[] result = ArrayUtil.remove(test, 6);

        assertArrayEquals(new Integer[] { 1, 2, 3, 4, 5 }, result);
    }
}
