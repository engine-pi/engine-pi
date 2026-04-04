package pi.resources;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

public class CodecTest
{
    @Test
    public void constructorThrowsUnsupportedOperationException()
            throws ReflectiveOperationException
    {
        Constructor<Codec> constructor = Codec.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        InvocationTargetException exception = assertThrows(
            InvocationTargetException.class,
            constructor::newInstance);

        assertTrue(
            exception.getCause() instanceof UnsupportedOperationException);
    }

    @Test
    public void encodeAngleAndDecodeAngleRoundTrip()
    {
        float[] angles = { -450f, -10f, 0f, 90f, 179.9f, 359.99f, 721f };

        for (float angle : angles)
        {
            byte encoded = Codec.encodeAngle(angle);
            float decoded = Codec.decodeAngle(encoded);

            float normalized = ((angle % 360) + 360) % 360;
            assertEquals(normalized,
                decoded,
                1.5f,
                "Encoded/decoded byte-angle should stay within codec precision");
        }
    }

    @Test
    public void encodeAnglePreciseAndDecodeAngleRoundTrip()
    {
        float[] angles = { -30.5f, 0f, 45.12f, 180.99f, 359.99f, 390.15f };

        for (float angle : angles)
        {
            short encoded = Codec.encodeAnglePrecise(angle);
            float decoded = Codec.decodeAngle(encoded);

            float normalized = ((angle % 360) + 360) % 360;
            assertEquals(normalized,
                decoded,
                0.02f,
                "Precise angle codec should preserve two decimals");
        }
    }

    @Test
    public void encodeAndDecodeSmallFloatingPointNumberRoundTrip()
    {
        short encoded = Codec.encodeSmallFloatingPointNumber(12.34f, 2);

        float decoded = Codec.decodeSmallFloatingPointNumber(encoded, 2);

        assertEquals(12.35f, decoded, 0.001f);
    }

    @Test
    public void encodeSmallFloatingPointNumberRejectsOutOfRange()
    {
        assertThrows(IllegalArgumentException.class,
            () -> Codec.encodeSmallFloatingPointNumber(-0.01f, 2));
        assertThrows(IllegalArgumentException.class,
            () -> Codec.encodeSmallFloatingPointNumber(700f, 2));
    }

    @Test
    public void encodeAndDecodeBytesRoundTrip()
    {
        byte[] bytes = "codec-bytes".getBytes(StandardCharsets.UTF_8);

        String encoded = Codec.encode(bytes);
        byte[] decoded = Codec.decode(encoded);

        assertArrayEquals(bytes, decoded);
    }

    @Test
    public void encodeImageRejectsNullImage()
    {
        assertThrows(IllegalArgumentException.class,
            () -> Codec.encode((BufferedImage) null));
        assertThrows(IllegalArgumentException.class,
            () -> Codec.encode(null, ImageFormat.PNG));
    }

    @Test
    public void decodeImageRejectsNullImageString()
    {
        assertThrows(IllegalArgumentException.class,
            () -> Codec.decodeImage(null));
    }

    @Test
    public void decodeImageThrowsRuntimeExceptionForInvalidInput()
    {
        assertThrows(RuntimeException.class,
            () -> Codec.decodeImage("this-is-not-a-valid-image"));
    }

    @Test
    @DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
    public void encodeImageWithUnsupportedFormatFallsBackToPng()
    {
        BufferedImage image = new BufferedImage(2, 2,
                BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 0, 0xFFFF0000);
        image.setRGB(1, 1, 0xFF0000FF);

        String encoded = Codec.encode(image, ImageFormat.UNSUPPORTED);

        assertNotNull(encoded);

        BufferedImage decoded = Codec.decodeImage(encoded);
        assertNotNull(decoded);
        assertEquals(2, decoded.getWidth());
        assertEquals(2, decoded.getHeight());
    }
}
