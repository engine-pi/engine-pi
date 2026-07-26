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
package pi.resources.font;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import pi.resources.ResourceLoadException;
import pi.resources.Resources;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
class FontContainerTest
{
    // Nicht von Controller importieren, da die Tests auf Github headless
    // laufen.
    FontContainer fonts = Resources.fonts;

    @BeforeEach
    @AfterEach
    void clear()
    {
        fonts.clear();
    }

    @Test
    void loadFromResources()
    {
        var font = fonts.get("fonts/Cantarell-Bold.ttf");
        assertEquals("Cantarell Bold", font.getName());
    }

    @Test
    void loadSystemFonts()
    {
        var font = fonts.get("DejaVu Serif");
        assertEquals("DejaVu Serif", font.getName());
    }

    @Test
    void defaultFont()
    {
        var font = fonts.defaultFont(FontStyle.PLAIN);
        assertEquals("Cantarell Regular", font.getName());
        assertEquals("Cantarell", font.getFamily());
        assertEquals(0, font.getStyle());
    }

    @Test
    void isSystemFont()
    {
        String knownFontName = FontContainer.systemFonts()[0];
        assertTrue(FontContainer.isSystemFont(knownFontName));
        assertFalse(
            FontContainer.isSystemFont("__definitely_not_a_system_font__"));
    }

    @Nested
    class SystemFontsTest
    {
        @Test
        void returnsClone()
        {
            String[] fontsA = FontContainer.systemFonts();
            String[] fontsB = FontContainer.systemFonts();

            assertNotNull(fontsA);
            assertTrue(fontsA.length > 0);
            assertNotEquals(fontsA, fontsB);

            String original = fontsA[0];
            fontsA[0] = "changed";
            assertEquals(original, FontContainer.systemFonts()[0]);
        }

        @Test
        void loadSystemFontByName()
        {
            String knownFontName = FontContainer.systemFonts()[0];
            var font = FontContainer.loadSystemFontByName(knownFontName);

            assertEquals(knownFontName, font.getFamily());
            assertEquals(0, font.getStyle());
            assertEquals(12, font.getSize());
        }

        @Test
        void getByNameAndStyle()
        {
            String knownFontName = FontContainer.systemFonts()[0];
            var font = fonts.get(knownFontName, 1);
            assertEquals(1, font.getStyle());
        }

        @Test
        void getByNameAndSize()
        {
            String knownFontName = FontContainer.systemFonts()[0];
            var font = fonts.get(knownFontName, 22.5);
            assertEquals(22.5f, font.getSize2D());
        }

        @Test
        void getByNameStyleAndSize()
        {
            String knownFontName = FontContainer.systemFonts()[0];
            var font = fonts.get(knownFontName, 3, 19.25);
            assertEquals(3, font.getStyle());
            assertEquals(19.25f, font.getSize2D());
        }
    }

    @Nested
    class DefaultFontTest
    {
        @Test
        void defaultFontByInt()
        {
            var font = fonts.defaultFont(2);
            assertEquals(2, font.getStyle());
        }

        @Test
        void defaultFontWithoutArguments()
        {
            var plain = fonts.defaultFont(FontStyle.PLAIN);
            var defaultFont = fonts.defaultFont();
            assertEquals(plain.getName(), defaultFont.getName());
            assertEquals(plain.getFamily(), defaultFont.getFamily());
            assertEquals(plain.getStyle(), defaultFont.getStyle());
        }
    }

    @Nested
    class DefaultFontStyleTest
    {
        private int getStyle(FontStyle style)
        {
            return fonts.defaultFont(style).getStyle();
        }

        @Test
        void plain()
        {
            assertEquals(0, getStyle(FontStyle.PLAIN));
        }

        @Test
        void bold()
        {
            assertEquals(1, getStyle(FontStyle.BOLD));
        }

        @Test
        void italic()
        {
            assertEquals(2, getStyle(FontStyle.ITALIC));
        }

        @Test
        void boldItalic()
        {
            assertEquals(3, getStyle(FontStyle.BOLD_ITALIC));
        }
    }

    @Test
    void throwsException()
    {
        ResourceLoadException exception = assertThrows(
            ResourceLoadException.class,
            () -> fonts.get("xxx"));
        assertEquals("Die Ressource konnte nicht geladen werden: xxx",
            exception.getMessage());
    }
}
