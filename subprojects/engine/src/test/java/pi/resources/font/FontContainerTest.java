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
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    FontContainer container = Resources.fonts;

    @BeforeEach
    @AfterEach
    void clear()
    {
        container.clear();
    }

    @Test
    void loadFromResources()
    {
        var font = container.get("fonts/Cantarell-Bold.ttf");
        assertEquals("Cantarell Bold", font.getName());
    }

    @Test
    void loadSystemFonts()
    {
        var font = container.get("DejaVu Serif");
        assertEquals("DejaVu Serif", font.getName());
    }

    @Test
    void defaultFont()
    {
        var font = container.defaultFont(FontStyle.PLAIN);
        assertEquals("Cantarell Regular", font.getName());
        assertEquals(0, font.getStyle());
    }

    @Nested
    class DefaultTest
    {

        private int getStyle(FontStyle style)
        {
            return container.defaultFont(style).getStyle();
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
            () -> container.get("xxx"));
        assertEquals("Die Ressource konnte nicht geladen werden: xxx",
            exception.getMessage());
    }
}
