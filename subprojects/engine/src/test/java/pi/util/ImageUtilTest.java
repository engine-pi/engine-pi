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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static pi.Controller.images;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import pi.Controller;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
public class ImageUtilTest
{
    void assertImageEquals(BufferedImage expected, BufferedImage actual)
    {
        int[] expectedPixels = ((DataBufferInt) expected.getData()
            .getDataBuffer()).getData();
        int[] actualPixels = ((DataBufferInt) actual.getData().getDataBuffer())
            .getData();
        assertArrayEquals(expectedPixels, actualPixels);
    }

    @Test
    public void testCopy()
    {
        BufferedImage image = images.get(
            "Pixel-Adventure-1/Main Characters/Virtual Guy/Idle (32x32).png");
        BufferedImage copy = ImageUtil.copy(image);
        assertImageEquals(image, copy);
    }

    @Nested
    class ReplaceColorsTest
    {
        static Color white = new Color(255, 255, 255);

        static Color gray = new Color(127, 127, 127);

        static Color black = new Color(0, 0, 0);

        static Color red = new Color(255, 0, 0);

        static Color green = new Color(0, 255, 0);

        static Color blue = new Color(0, 0, 255);

        static BufferedImage input = images.get("images/gray.png");

        static BufferedImage expected = images.get("images/rgb.png");

        @Test
        public void testColorArray() throws IOException
        {
            BufferedImage actual = ImageUtil.replaceColors(input,
                new Color[]
                { white, gray, black },
                new Color[]
                { red, green, blue });
            assertImageEquals(expected, actual);
        }

        @Test
        public void testStringArray() throws IOException
        {
            BufferedImage actual = ImageUtil.replaceColors(input,
                new String[]
                { "#ffffff", "#7F7F7F", "#000000" },
                new String[]
                { "#FF0000", "#00FF00", "#0000Ff" });
            assertImageEquals(expected, actual);
        }

        @Test
        public void testMap() throws IOException
        {
            HashMap<Color, Color> map = new HashMap<>();
            map.put(white, red);
            map.put(gray, green);
            map.put(black, blue);
            BufferedImage actual = ImageUtil.replaceColors(input, map);
            assertImageEquals(expected, actual);
        }
    }

    @DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
    @Test
    public void testOptimizeImage()
    {
        assertFalse(GraphicsEnvironment.isHeadless());
        BufferedImage img = null;
        try
        {
            img = ImageIO.read(Controller.class.getResource("/logo/logo.png"));
        }
        catch (Exception e)
        {
            Logger.error("OptimizerTest", e.getLocalizedMessage());
        }
        assertNotNull(img);
        BufferedImage opt = ImageUtil.toCompatibleImage(img);
        assertNotNull(opt);
        assertEquals(img.getWidth(), opt.getWidth());
        assertEquals(img.getHeight(), opt.getHeight());
        BufferedImage opt2 = ImageUtil.toCompatibleImage(opt);
        assertEquals(opt.getColorModel(), opt2.getColorModel());
    }
}
