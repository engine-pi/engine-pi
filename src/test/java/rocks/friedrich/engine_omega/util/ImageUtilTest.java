package rocks.friedrich.engine_omega.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import org.junit.jupiter.api.Test;

import rocks.friedrich.engine_omega.io.ImageLoader;

class ImageUtilTest
{
    @Test
    void testCopy()
    {
        BufferedImage image = ImageLoader.load(
                "Pixel-Adventure-1/Main Characters/Virtual Guy/Idle (32x32).png");
        int[] expectedPixels = ((DataBufferInt) image.getData().getDataBuffer())
                .getData();
        BufferedImage copy = ImageUtil.copy(image);
        int[] actualPixels = ((DataBufferInt) copy.getData().getDataBuffer())
                .getData();
        assertArrayEquals(expectedPixels, actualPixels);
    }
}
