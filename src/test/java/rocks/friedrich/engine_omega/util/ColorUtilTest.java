/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/test/java/de/gurkenlabs/litiengine/util/ColorUtilTests.java
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
package rocks.friedrich.engine_omega.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.awt.Color;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class ColorUtilTest
{
    @Test
    public void testMalformedColorHexString()
    {
        Logger.getLogger(ColorUtil.class.getName()).setUseParentHandlers(false);
        String red = "~#ff0000";
        String red2 = "#ff0000000";
        Color redDecoded = ColorUtil.decode(red);
        Color redDecoded2 = ColorUtil.decode(red2);
        assertNull(redDecoded);
        assertNull(redDecoded2);
    }

    @ParameterizedTest
    @MethodSource("getColorFromHexString")
    public void testColorFromHexString(String colorHex, Color expectedColor)
    {
        Color colorDecoded = ColorUtil.decode(colorHex);
        assertEquals(expectedColor, colorDecoded);
    }

    private static Stream<Arguments> getColorFromHexString()
    {
        return Stream.of(Arguments.of("#ff0000", Color.RED),
                Arguments.of("#00ff00", Color.GREEN),
                Arguments.of("#0000ff", Color.BLUE));
    }

    @Nested
    public class DecodeTest
    {
        String red200 = "#ff0000c8";

        Color redDecoded = ColorUtil.decode(red200);

        Color alphaRed = new Color(255, 0, 0, 200);

        @Test
        public void testRedFromAlphaHexString()
        {
            assertEquals(alphaRed.getRed(), redDecoded.getRed());
        }

        @Test
        public void testGreenFromAlphaHexString()
        {
            assertEquals(alphaRed.getGreen(), redDecoded.getGreen());
        }

        @Test
        public void testBlueFromAlphaHexString()
        {
            assertEquals(alphaRed.getBlue(), redDecoded.getBlue());
        }

        @Test
        public void testAlphaFromAlphaHexString()
        {
            assertEquals(alphaRed.getAlpha(), redDecoded.getAlpha());
        }

        @Test
        public void testStringArray()
        {
            Color color = ColorUtil.decode(new String[] { "#ff0000ff" })[0];
            assertEquals(color.getRed(), 255);
            assertEquals(color.getGreen(), 0);
            assertEquals(color.getBlue(), 0);
            assertEquals(color.getAlpha(), 255);
        }
    }

    @ParameterizedTest
    @MethodSource("getSolidColorFromAlphaHexString")
    public void testSolidColorFromAlphaHexString(String color, Boolean isSolid,
            Color solidColor)
    {
        Color colorDecoded = ColorUtil.decode(color, isSolid);
        assertEquals(solidColor, colorDecoded);
    }

    private static Stream<Arguments> getSolidColorFromAlphaHexString()
    {
        return Stream.of(Arguments.of("#ff0000c8", true, new Color(228, 0, 0)),
                Arguments.of("#00ff00c8", true, new Color(0, 228, 0)),
                Arguments.of("#0000ffc8", true, new Color(0, 0, 228)),
                Arguments.of("", true, null), Arguments.of(null, true, null));
    }

    @ParameterizedTest
    @MethodSource("getHexStringWithoutHashtag")
    public void testHexStringWithoutHashtag(String color, Color expectedColor)
    {
        Color colorDecoded = ColorUtil.decode(color);
        assertEquals(expectedColor, colorDecoded);
    }

    private static Stream<Arguments> getHexStringWithoutHashtag()
    {
        return Stream.of(Arguments.of("ff0000", Color.RED),
                Arguments.of("00ff00", Color.GREEN),
                Arguments.of("0000ff", Color.BLUE), Arguments.of("000", null));
    }

    @Test
    public void testEncodeColor()
    {
        String redEncoded = ColorUtil.encode(Color.RED);
        String greenEncoded = ColorUtil.encode(Color.GREEN);
        String blueEncoded = ColorUtil.encode(Color.BLUE);
        String invisibleBlack = ColorUtil.encode(new Color(0, 0, 0, 0));
        assertEquals("#ff0000", redEncoded);
        assertEquals("#00ff00", greenEncoded);
        assertEquals("#0000ff", blueEncoded);
        assertEquals("#00000000", invisibleBlack);
        assertNull(ColorUtil.encode(null));
    }

    @Test
    public void testEncodeAlphaColor()
    {
        String redEncoded = ColorUtil.encode(new Color(255, 0, 0, 200));
        String greenEncoded = ColorUtil.encode(new Color(0, 255, 0, 200));
        String blueEncoded = ColorUtil.encode(new Color(0, 0, 255, 200));
        assertEquals("#ff0000c8", redEncoded);
        assertEquals("#00ff00c8", greenEncoded);
        assertEquals("#0000ffc8", blueEncoded);
    }

    @ParameterizedTest(name = "testRgbBounds {0}, colorValue={1}, expectedRgb={2}")
    @CsvSource(
    { "Negative, -10, 0", "Zero, 0, 0", "InRange, 158, 158", "Max, 255, 255",
            "OutOfRange, 300, 255" })
    public void testRgbBounds(String rgbBound, int colorValue, int expectedRgb)
    {
        // arrange, act
        int actualRgb = ColorUtil.ensureColorValueRange(colorValue);
        // assert
        assertEquals(expectedRgb, actualRgb);
    }

    @Test
    public void testPremultiply()
    {
        Color color = new Color(225, 0, 0);
        assertEquals(new Color(225, 0, 0), ColorUtil.premultiply(color));
    }
}
