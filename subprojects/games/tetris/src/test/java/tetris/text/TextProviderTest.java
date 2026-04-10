/*
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
package tetris.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import pi.Controller;
import pi.actor.ImageText;
import pi.actor.ImageText.CaseSensitivity;

/**
 * @author Josef Friedrich
 */
@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
class TextProviderTest
{
    @BeforeEach
    void setUp()
    {
        Controller.instantMode(false);
    }

    @Test
    void constructorThrowsUnsupportedOperationException() throws Exception
    {
        Constructor<TextProvider> c = TextProvider.class
            .getDeclaredConstructor();
        c.setAccessible(true);
        InvocationTargetException thrown = assertThrows(
            InvocationTargetException.class,
            c::newInstance);
        assertInstanceOf(UnsupportedOperationException.class,
            thrown.getCause());
    }

    @Nested
    class FontTest
    {
        @Test
        void isNotNull()
        {
            assertNotNull(TextProvider.font());
        }

        @Test
        void isSingleton()
        {
            assertSame(TextProvider.font(), TextProvider.font());
        }

        @Test
        void basePath()
        {
            assertEquals("images/image-text", TextProvider.font().basePath());
        }

        @Test
        void supportsUpperCase()
        {
            assertEquals(CaseSensitivity.UPPER,
                TextProvider.font().supportsCase());
        }
    }

    @Nested
    class TextTest
    {
        @Test
        void isNotNull()
        {
            assertNotNull(TextProvider.text());
        }

        @Test
        void returnsNewInstanceEachTime()
        {
            assertNotSame(TextProvider.text(), TextProvider.text());
        }

        @Test
        void isImageText()
        {
            assertInstanceOf(ImageText.class, TextProvider.text());
        }

        @Test
        void imageDimension()
        {
            var text = TextProvider.text().content("1");
            assertEquals(8, text.image().getWidth());
            assertEquals(8, text.image().getHeight());
        }

    }
}
