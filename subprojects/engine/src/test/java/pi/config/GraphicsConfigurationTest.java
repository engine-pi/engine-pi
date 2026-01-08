package pi.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.awt.Dimension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pi.Direction;
import pi.resources.color.PredefinedColorScheme;
import pi.resources.color.ColorUtil;

public class GraphicsConfigurationTest
{
    GraphicsConfiguration config;

    @BeforeEach
    void setup()
    {
        config = new GraphicsConfiguration();
    }

    @Test
    void testWindowWidth()
    {
        config.windowWidth(1024);
        assertEquals(1024, config.windowWidth());
    }

    @Test
    void testWindowHeight()
    {
        config.windowHeight(768);
        assertEquals(768, config.windowHeight());
    }

    @Test
    void testWindowPosition()
    {
        config.windowPosition(Direction.UP);
        assertEquals(Direction.UP, config.windowPosition());
    }

    @Test
    void testFramerate()
    {
        config.framerate(120);
        assertEquals(120, config.framerate());
    }

    @Test
    void testColorScheme()
    {
        config.colorScheme(PredefinedColorScheme.JAVA);
        assertEquals(Color.RED, config.colorScheme().red());
        config.colorScheme(PredefinedColorScheme.ANDROID);
        assertEquals(ColorUtil.decode("#F44336"), config.colorScheme().red());
    }

    @Test
    void testPixelMultiplication()
    {
        config.pixelMultiplication(3);
        assertEquals(3, config.pixelMultiplication());
        assertTrue(config.isPixelMultiplication());
    }

    @Test
    void testPixelMultiplicationDisabled()
    {
        config.pixelMultiplication(1);
        assertEquals(1, config.pixelMultiplication());
        assertFalse(config.isPixelMultiplication());
    }

    @Test
    void testScreenRecordingNFrames()
    {
        config.screenRecordingNFrames(5);
        assertEquals(5, config.screenRecordingNFrames());
    }

    @Test
    void testWindowDimension()
    {
        config.windowDimension(800, 600);
        assertEquals(800, config.windowWidth());
        assertEquals(600, config.windowHeight());
        assertEquals(new Dimension(800, 600), config.windowDimension());
    }

    @Test
    void testBuilderPattern()
    {
        config.windowWidth(1280).windowHeight(720).framerate(60)
                .pixelMultiplication(2);

        assertEquals(1280, config.windowWidth());
        assertEquals(720, config.windowHeight());
        assertEquals(60, config.framerate());
        assertEquals(2, config.pixelMultiplication());
    }

}
