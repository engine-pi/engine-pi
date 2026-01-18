/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2026 Josef Friedrich and contributors.
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
package pi.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Dimension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pi.graphics.geom.Direction;

/**
 * @author Josef Friedrich
 */
public class GraphicsConfigTest
{
    GraphicsConfig config;

    @BeforeEach
    void setup()
    {
        config = new GraphicsConfig();
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
        config.colorScheme("Java");
        assertEquals("Java", config.colorScheme());
        config.colorScheme("Android");
        assertEquals("Android", config.colorScheme());
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
        config.windowWidth(1280)
            .windowHeight(720)
            .framerate(60)
            .pixelMultiplication(2);

        assertEquals(1280, config.windowWidth());
        assertEquals(720, config.windowHeight());
        assertEquals(60, config.framerate());
        assertEquals(2, config.pixelMultiplication());
    }
}
