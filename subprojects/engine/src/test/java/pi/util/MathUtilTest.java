/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/test/java/de/gurkenlabs/litiengine/util/MathUtilitiesTests.java
 *
 * MIT License
 *
 * Copyright (c) 2016 - 2024 Gurkenlabs
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MathUtilTest
{
    @Test
    void round()
    {
        float value1 = 4.3f;
        float value2 = 10.4f;
        float value3 = 6.6f;
        assertEquals(4, MathUtil.round(value1, 0), 0.0001);
        assertEquals(10.4, MathUtil.round(value2, 1), 0.0001);
        assertEquals(6.6, MathUtil.round(value3, 2), 0.0001);
    }

    @Test
    void intClamp()
    {
        int toLow = 4;
        int toHigh = 11;
        int inRange = 6;
        assertEquals(5, MathUtil.clamp(toLow, 5, 10));
        assertEquals(10, MathUtil.clamp(toHigh, 5, 10));
        assertEquals(6, MathUtil.clamp(inRange, 5, 10));
    }

    @Test
    void longClamp()
    {
        long toLow = 4000000000L;
        long toHigh = 11000000000L;
        long inRange = 6600000000L;
        assertEquals(5000000000L,
            MathUtil.clamp(toLow, 5000000000L, 10000000000L));
        assertEquals(10000000000L,
            MathUtil.clamp(toHigh, 5000000000L, 10000000000L));
        assertEquals(6600000000L,
            MathUtil.clamp(inRange, 5000000000L, 10000000000L));
    }

    @Test
    void doubleClamp()
    {
        double toLow = 4.3;
        double toHigh = 10.4;
        double inRange = 6.6;
        assertEquals(5.0, MathUtil.clamp(toLow, 5, 10), 0.0001);
        assertEquals(10.0, MathUtil.clamp(toHigh, 5, 10), 0.0001);
        assertEquals(6.6, MathUtil.clamp(inRange, 5, 10), 0.0001);
    }

    @Test
    void floatClamp()
    {
        float toLow = 4.3f;
        float toHigh = 10.4f;
        float inRange = 6.6f;
        assertEquals(5.0f, MathUtil.clamp(toLow, 5, 10), 0.0001);
        assertEquals(10.0f, MathUtil.clamp(toHigh, 5, 10), 0.0001);
        assertEquals(6.6f, MathUtil.clamp(inRange, 5, 10), 0.0001);
    }

    @ParameterizedTest(name = "{0}: (value={1}, min={2}, max={3}) = {4}")
    @CsvSource(
    { "'value < min',42, 64, 100, 64",
            "'value >= min && value > max',42, 10, 40, 40",
            "'value >= min && value <= max',42, 10, 100, 42" })
    void byteClamp(String partition, byte value, byte min, byte max,
            byte result)
    {
        assertEquals(result, MathUtil.clamp(value, min, max));
    }

    @ParameterizedTest(name = "{0}: (value={1}, min={2}, max={3}) = {4}")
    @CsvSource(
    { "'value < min', 4200, 7344, 12567, 7344",
            "'value >= min && value > max', 4200, 1200, 3511, 3511",
            "'value >= min && value <= max', 4200, 1337, 28111, 4200" })
    void shortClamp(String partition, short value, short min, short max,
            short result)
    {
        assertEquals(result, MathUtil.clamp(value, min, max));
    }

    @Test
    void getAverageInt()
    {
        int avg = MathUtil.getAverage(new int[] { 2, 2, 1, 1, 1, 2 });
        int avg2 = MathUtil
            .getAverage(new int[]
            { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 });
        assertEquals(1, avg);
        assertEquals(5, avg2);
    }

    @Test
    void getAverageDouble()
    {
        double avg = MathUtil.getAverage(new double[] { 2, 2, 1, 1, 1, 2 });
        double avg2 = MathUtil
            .getAverage(new double[]
            { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 });
        assertEquals(1.5, avg, 0.0001);
        assertEquals(5.5, avg2, 0.0001);
    }

    @Test
    void getAverageFloat()
    {
        float avg = MathUtil.getAverage(new float[] { 2, 2, 1, 1, 1, 2 });
        float avg2 = MathUtil
            .getAverage(new float[]
            { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 });
        assertEquals(1.5f, avg, 0.0001);
        assertEquals(5.5f, avg2, 0.0001);
    }

    @Test
    void getMaxInt()
    {
        int max = MathUtil.getMax(new int[] { 2, 2, 1, 1, 1, 2 });
        int max2 = MathUtil.getMax(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 });
        assertEquals(2, max);
        assertEquals(10, max2);
    }

    @Test
    void isOddNumber()
    {
        int oddNumber = 1;
        int evenNumber = 2;
        assertTrue(MathUtil.isOddNumber(oddNumber));
        assertFalse(MathUtil.isOddNumber(evenNumber));
    }

    @ParameterizedTest(name = "testGetFullPercent percent={0}, fraction={2}, expectedValue={3}")
    @CsvSource(
    { "4.3d, 2.15d, 50.0d", "0, 2.15d, 0", "0, 2.6d, 0", "10.4, 2.6d, 25.0d" })
    void getFullPercent(double percent, double fraction, double expectedValue)
    {
        assertEquals(expectedValue, MathUtil.getFullPercent(percent, fraction));
    }

    @Test
    void getPercent()
    {
        double percent1 = 4.3;
        double percent2 = 0.0;
        double percent3 = 0;
        double percent4 = 10.4;
        double fraction1 = 2.15;
        double fraction2 = 2.6;
        assertEquals(50.0, MathUtil.getPercent(percent1, fraction1), 0.0001);
        assertEquals(0, MathUtil.getPercent(percent2, fraction1));
        assertEquals(0, MathUtil.getPercent(percent3, fraction2));
        assertEquals(25.0, MathUtil.getPercent(percent4, fraction2), 0.0001);
    }
}
