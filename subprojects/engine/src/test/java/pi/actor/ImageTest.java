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
package pi.actor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pi.Controller;

public class ImageTest
{
    private Image image;

    private BufferedImage bufferedImage;

    @Mock
    private Graphics2D graphics2D;

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);
        bufferedImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_RGB);
        Controller.instantMode(false);
        image = new Image(bufferedImage, 50.0);
    }

    @Test
    public void testImageConstructorWithDimensions()
    {
        assertEquals(2.0, image.width(), 0.01);
        assertEquals(1.0, image.height(), 0.01);
    }

    @Test
    public void testImageGetterAndSetter()
    {
        assertEquals(bufferedImage, image.image());
    }

    @Test
    public void testImageWidth()
    {
        assertEquals(2.0, image.width(), 0.01);
    }

    @Test
    public void testImageHeight()
    {
        assertEquals(1.0, image.height(), 0.01);
    }

    @Test
    public void testImageSizeInPixels()
    {
        Dimension dim = image.sizeInPx();
        assertEquals(100, dim.width);
        assertEquals(50, dim.height);
    }

    @Test
    public void testImageSizeSetterWithDimensions()
    {
        image.size(3.0, 2.0);
        assertEquals(3.0, image.width(), 0.01);
        assertEquals(2.0, image.height(), 0.01);
    }

    @Test
    public void testImageSizeSetterWithPixelPerMeter()
    {
        image.pixelPerMeter(25.0);
        assertEquals(4.0, image.width(), 0.01);
        assertEquals(2.0, image.height(), 0.01);
    }

    @Test
    public void testFlipVertically()
    {
        assertFalse(image.flippedVertically());
        image.toggleFlipVertically();
        assertTrue(image.flippedVertically());
    }

    @Test
    public void testFlippedVerticallyToggle()
    {
        image.toggleFlipVertically();
        image.toggleFlipVertically();
        assertFalse(image.flippedVertically());
    }

    @Test
    public void testFlippedVerticallySetterTrue()
    {
        image.flippedVertically(true);
        assertTrue(image.flippedVertically());
    }

    @Test
    public void testFlippedVerticallySetterFalse()
    {
        image.flippedVertically(true);
        image.flippedVertically(false);
        assertFalse(image.flippedVertically());
    }

    @Test
    public void testFlipHorizontally()
    {
        assertFalse(image.flippedHorizontally());
        image.toggleFlipHorizontally();
        assertTrue(image.flippedHorizontally());
    }

    @Test
    public void testFlippedHorizontallyToggle()
    {
        image.toggleFlipHorizontally();
        image.toggleFlipHorizontally();
        assertFalse(image.flippedHorizontally());
    }

    @Test
    public void testFlippedHorizontallySetterTrue()
    {
        image.flippedHorizontally(true);
        assertTrue(image.flippedHorizontally());
    }

    @Test
    public void testFlippedHorizontallySetterFalse()
    {
        image.flippedHorizontally(true);
        image.flippedHorizontally(false);
        assertFalse(image.flippedHorizontally());
    }

    @Test
    public void testImageSizeWithZeroWidth()
    {
        assertThrows(IllegalArgumentException.class,
            () -> image.size(0.0, 1.0));
    }

    @Test
    public void testImageSizeWithNegativeHeight()
    {
        assertThrows(IllegalArgumentException.class,
            () -> image.size(1.0, -1.0));
    }

    @Test
    public void testImageSizeWithZeroPixelPerMeter()
    {
        assertThrows(IllegalArgumentException.class,
            () -> image.pixelPerMeter(0.0));
    }

    @Test
    public void testToString()
    {
        String result = image.toString();
        assertNotNull(result);
        assertTrue(result.contains("Image"));
    }

}
