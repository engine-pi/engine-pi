package rocks.friedrich.engine_omega.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.HashMap;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import rocks.friedrich.engine_omega.io.ImageLoader;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
public class ImageUtilTest
{
        void assertImageEquals(BufferedImage expected, BufferedImage actual)
        {
                int[] expectedPixels = ((DataBufferInt) expected.getData()
                                .getDataBuffer()).getData();
                int[] actualPixels = ((DataBufferInt) actual.getData()
                                .getDataBuffer()).getData();
                assertArrayEquals(expectedPixels, actualPixels);
        }

        @Test
        public void testCopy()
        {
                BufferedImage image = ImageLoader.load(
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

                static BufferedImage input = ImageLoader
                                .load("images/gray.png");

                static BufferedImage expected = ImageLoader
                                .load("images/rgb.png");

                @Test
                public void testArgArray() throws IOException
                {
                        BufferedImage actual = ImageUtil.replaceColors(input,
                                        new Color[]
                                        { white, gray, black },
                                        new Color[]
                                        { red, green, blue });
                        assertImageEquals(expected, actual);
                }

                @Test
                public void testArgMap() throws IOException
                {
                        HashMap<Color, Color> map = new HashMap<>();
                        map.put(white, red);
                        map.put(gray, green);
                        map.put(black, blue);
                        BufferedImage actual = ImageUtil.replaceColors(input,
                                        map);
                        assertImageEquals(expected, actual);
                }
        }
}
