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
    }

    @Test
    public void testImageConstructorWithDimensions()
    {
        image = new Image(bufferedImage, 50.0);
        assertEquals(2.0, image.width(), 0.01);
        assertEquals(1.0, image.height(), 0.01);
    }

    @Test
    public void testImageGetterAndSetter()
    {
        image = new Image(bufferedImage, 50.0);
        assertEquals(bufferedImage, image.image());
    }

    @Test
    public void testImageWidth()
    {
        image = new Image(bufferedImage, 50);
        assertEquals(2.0, image.width(), 0.01);
    }

    @Test
    public void testImageHeight()
    {
        image = new Image(bufferedImage, 50);
        assertEquals(1.0, image.height(), 0.01);
    }

    @Test
    public void testImageSizeInPixels()
    {
        image = new Image(bufferedImage, 50.0);
        Dimension dim = image.sizeInPx();
        assertEquals(100, dim.width);
        assertEquals(50, dim.height);
    }

    @Test
    public void testImageSizeSetterWithDimensions()
    {
        image = new Image(bufferedImage, 50.0);
        image.size(3.0, 2.0);
        assertEquals(3.0, image.width(), 0.01);
        assertEquals(2.0, image.height(), 0.01);
    }

    @Test
    public void testImageSizeSetterWithPixelPerMeter()
    {
        image = new Image(bufferedImage, 50.0);
        image.pixelPerMeter(25.0);
        assertEquals(4.0, image.width(), 0.01);
        assertEquals(2.0, image.height(), 0.01);
    }

    @Test
    public void testFlipVertically()
    {
        image = new Image(bufferedImage, 50.0);
        assertFalse(image.flippedVertically());
        image.toggleFlipVertically();
        assertTrue(image.flippedVertically());
    }

    @Test
    public void testFlippedVerticallyToggle()
    {
        image = new Image(bufferedImage, 50.0);
        image.toggleFlipVertically();
        image.toggleFlipVertically();
        assertFalse(image.flippedVertically());
    }

    @Test
    public void testFlippedVerticallySetterTrue()
    {
        image = new Image(bufferedImage, 50.0);
        image.flippedVertically(true);
        assertTrue(image.flippedVertically());
    }

    @Test
    public void testFlippedVerticallySetterFalse()
    {
        image = new Image(bufferedImage, 50.0);
        image.flippedVertically(true);
        image.flippedVertically(false);
        assertFalse(image.flippedVertically());
    }

    @Test
    public void testFlipHorizontally()
    {
        image = new Image(bufferedImage, 50.0);
        assertFalse(image.flippedHorizontally());
        image.toggleFlipHorizontally();
        assertTrue(image.flippedHorizontally());
    }

    @Test
    public void testFlippedHorizontallyToggle()
    {
        image = new Image(bufferedImage, 50.0);
        image.toggleFlipHorizontally();
        image.toggleFlipHorizontally();
        assertFalse(image.flippedHorizontally());
    }

    @Test
    public void testFlippedHorizontallySetterTrue()
    {
        image = new Image(bufferedImage, 50.0);
        image.flippedHorizontally(true);
        assertTrue(image.flippedHorizontally());
    }

    @Test
    public void testFlippedHorizontallySetterFalse()
    {
        image = new Image(bufferedImage, 50.0);
        image.flippedHorizontally(true);
        image.flippedHorizontally(false);
        assertFalse(image.flippedHorizontally());
    }

    // @Test(expected = IllegalArgumentException.class)
    // public void testImageSizeWithZeroWidth()
    // {
    // image = new Image(bufferedImage, 50.0);
    // image.imageSize(0.0, 1.0);
    // }

    // @Test(expected = IllegalArgumentException.class)
    // public void testImageSizeWithNegativeHeight()
    // {
    // image = new Image(bufferedImage, 50.0);
    // image.imageSize(1.0, -1.0);
    // }

    // @Test(expected = IllegalArgumentException.class)
    // public void testImageSizeWithZeroPixelPerMeter()
    // {
    // image = new Image(bufferedImage, 50.0);
    // image.imageSize(0.0);
    // }

    @Test
    public void testToString()
    {
        image = new Image(bufferedImage, 50);
        String result = image.toString();
        assertNotNull(result);
        assertTrue(result.contains("Image"));
    }

}
