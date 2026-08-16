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
package pi.graphics.boxes_ng;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

/**
 * @since 0.53.0
 */
class ImageBoxTest
{
    private ImageBox box;

    private BufferedImage image;

    @Mock
    private Graphics2D g;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        // Erstelle ein Test-BufferedImage mit bekannten Dimensionen (100x200)
        image = new BufferedImage(100, 200, BufferedImage.TYPE_INT_RGB);
        box = new ImageBox(image);
    }

    @Test
    void constructorWithBufferedImage()
    {
        assertEquals(image, new ImageBox(image).image);
    }

    @Nested
    class WidthHeightTest
    {
        @Test
        void widthSetter()
        {
            ImageBox result = box.width(150);

            assertEquals(result, box);
            assertEquals(150, box.definedWidth);
        }

        @Test
        void heightSetter()
        {
            ImageBox result = box.height(250);

            assertEquals(result, box);
            assertEquals(250, box.definedHeight);
        }

        @Test
        void builderPattern()
        {
            box.width(150).height(250);

            assertEquals(150, box.definedWidth);
            assertEquals(250, box.definedHeight);
        }
    }

    @Nested
    class HFlipTest
    {
        @Test
        void setToFalse()
        {
            box.hFlip(false);
            assertFalse(box.hFlip);
        }

        @Test
        void setToTrue()
        {
            box.hFlip(true);
            assertTrue(box.hFlip);
        }

        @Test
        void noParameter()
        {
            box.hFlip();
            assertTrue(box.hFlip);
        }

        @Test
        void builderPattern()
        {
            ImageBox result = box.hFlip();
            assertEquals(result, box);
        }
    }

    @Nested
    class VFlipTest
    {
        @Test
        void setToFalse()
        {
            box.vFlip(false);
            assertFalse(box.vFlip);
        }

        @Test
        void setToTrue()
        {
            box.vFlip(true);
            assertTrue(box.vFlip);
        }

        @Test
        void noParameter()
        {
            box.vFlip();
            assertTrue(box.vFlip);
        }

        @Test
        void builderPattern()
        {
            ImageBox result = box.vFlip();
            assertEquals(result, box);
        }
    }

    @Nested
    class CalculateDimenstionTest
    {

        @Test
        void noDefinedDimensions()
        {
            box.calculateDimension();

            assertEquals(100, box.width);
            assertEquals(200, box.height);
        }

        @Test
        void withDefinedWidth()
        {
            // Hälfte der Original-Breite
            box.width(50);
            box.calculateDimension();

            assertEquals(50, box.width);
            // Proportional berechnet
            assertEquals(100, box.height);
        }

        @Test
        void withDefinedHeight()
        {
            // Hälfte der Original-Höhe
            box.height(100);
            box.calculateDimension();

            // Proportional berechnet
            assertEquals(50, box.width);
            assertEquals(100, box.height);
        }

        @Test
        void withBothDimensions()
        {
            box.width(150).height(250);
            box.calculateDimension();

            assertEquals(150, box.width);
            assertEquals(250, box.height);
        }

        @Test
        void bothZero()
        {
            box.definedWidth = 0;
            box.definedHeight = 0;
            box.calculateDimension();

            assertEquals(100, box.width);
            assertEquals(200, box.height);
        }
    }

    @Nested
    class DrawTest
    {

        @Test
        void withoutFlip()
        {
            box.x = 10;
            box.y = 20;
            box.width = 100;
            box.height = 200;

            box.draw(g);

            ArgumentCaptor<Integer> xCaptor = ArgumentCaptor
                .forClass(Integer.class);
            ArgumentCaptor<Integer> yCaptor = ArgumentCaptor
                .forClass(Integer.class);

            verify(g, atLeastOnce()).drawImage(eq(image),
                xCaptor.capture(),
                yCaptor.capture(),
                anyInt(),
                anyInt(),
                isNull());
        }

        @Test
        void withHorizontalFlip()
        {
            box.hFlip(true);
            box.x = 10;
            box.y = 20;
            box.width = 100;
            box.height = 200;

            box.draw(g);

            verify(g, atLeastOnce()).drawImage(eq(image),
                // x + width
                eq(110),
                anyInt(),
                // -width
                eq(-100),
                anyInt(),
                isNull());
        }

        @Test
        void WithVerticalFlip()
        {
            box.vFlip(true);
            box.x = 10;
            box.y = 20;
            box.width = 100;
            box.height = 200;

            box.draw(g);

            verify(g, atLeastOnce()).drawImage(eq(image),
                anyInt(),
                anyInt(),
                anyInt(),
                // -height
                eq(-200),
                isNull());
        }

        @Test
        void withBothFlips()
        {
            box.hFlip(true).vFlip(true);
            box.x = 10;
            box.y = 20;
            box.width = 100;
            box.height = 200;

            box.draw(g);

            verify(g, atLeastOnce()).drawImage(eq(image),
                // x + width
                eq(110),
                anyInt(),
                // -width
                eq(-100),
                // -height
                eq(-200),
                isNull());
        }
    }

    @Nested
    class ToStringTest
    {
        @Test
        void withoutFlip()
        {
            String result = box.toString();
            assertFalse(result.contains("hFlip"));
            assertFalse(result.contains("vFlip"));
        }

        @Test
        void withHorizontalFlip()
        {
            box.hFlip(true);
            String result = box.toString();
            assertTrue(result.contains("hFlip"));
        }

        @Test
        void withVerticalFlip()
        {
            box.vFlip(true);
            String result = box.toString();
            assertTrue(result.contains("vFlip"));
        }

        @Test
        void withBothFlips()
        {
            box.hFlip(true).vFlip(true);
            String result = box.toString();
            assertTrue(result.contains("hFlip"));
            assertTrue(result.contains("vFlip"));
        }
    }

    @Nested
    class IntegrationTest
    {
        @Test
        void builderAndDimension()
        {
            box.width(50).height(100);
            box.calculateDimension();

            assertEquals(50, box.width);
            assertEquals(100, box.height);
        }

        @Test
        void completeConfiguration()
        {
            box.width(80).height(160).hFlip(true).vFlip(true);

            box.calculateDimension();

            assertEquals(80, box.width);
            assertEquals(160, box.height);
            assertTrue(box.hFlip);
            assertTrue(box.vFlip);

            String result = box.toString();
            assertTrue(result.contains("hFlip"));
            assertTrue(result.contains("vFlip"));
        }
    }

}
