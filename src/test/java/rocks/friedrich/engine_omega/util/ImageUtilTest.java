package rocks.friedrich.engine_omega.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import rocks.friedrich.engine_omega.io.ImageLoader;

class ImageUtilTest
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
        void testCopy()
        {
                BufferedImage image = ImageLoader.load(
                                "Pixel-Adventure-1/Main Characters/Virtual Guy/Idle (32x32).png");
                BufferedImage copy = ImageUtil.copy(image);
                assertImageEquals(image, copy);
        }

        @Test
        void testReplaceColors() throws IOException
        {
                BufferedImage actual = ImageUtil.replaceColors(
                                ImageLoader.load("images/gray.png"), new Color[]
                                { new Color(255, 255, 255),
                                                new Color(127, 127, 127),
                                                new Color(0, 0, 0) },
                                new Color[]
                                { new Color(255, 0, 0), new Color(0, 255, 0),
                                                new Color(0, 0, 255) });
                BufferedImage expected = ImageLoader.load("images/rgb.png");
                assertImageEquals(expected, actual);
        }
}
