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
package pi.resources.color;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import pi.resources.ResourceLoadException;
import pi.resources.Resources;

class ColorContainerTest
{
    // Nicht von Controller importieren, da die Tests auf Github headless
    // laufen.
    ColorContainer container = Resources.colors;

    @BeforeEach
    void clear()
    {
        container.clear();
        container.addScheme(PredefinedColorScheme.GNOME.getScheme());
    }

    @Nested
    class GetColorSafeTest
    {
        @Test
        void primaryName()
        {
            assertNotNull(container.getSafe("blue"));
        }

        @Test
        void alias()
        {
            assertNotNull(container.getSafe("blau"));
        }

        @Test
        void caseInsensitivity()
        {
            assertNotNull(container.getSafe("BLUE"));
        }

        @Test
        void whiteSpaces()
        {
            assertNotNull(container.getSafe("b l u e"));
        }

        @Test
        void hexCode()
        {
            Color actual = container.getSafe("#aabbccdd");
            Color expected = new Color(0xaa, 0xbb, 0xcc, 0xdd);
            assertEquals(actual.getRed(), expected.getRed());
            assertEquals(actual.getGreen(), expected.getGreen());
            assertEquals(actual.getBlue(), expected.getBlue());
            assertEquals(actual.getAlpha(), expected.getAlpha());
        }

        @Test
        void noException()
        {
            assertNotNull(container.getSafe("XXX"));
        }
    }

    @Nested
    class GetColorTest
    {
        @Test
        void primaryName()
        {
            assertNotNull(container.get("blue"));
        }

        @Test
        void alias()
        {
            assertNotNull(container.get("blau"));
        }

        @Test
        void caseInsensitivity()
        {
            assertNotNull(container.get("BLUE"));
        }

        @Test
        void whiteSpaces()
        {
            assertNotNull(container.get("b l u e"));
        }

        @Test
        void hexCode()
        {
            Color actual = container.get("#aabbccdd");
            Color expected = new Color(0xaa, 0xbb, 0xcc, 0xdd);
            assertEquals(actual.getRed(), expected.getRed());
            assertEquals(actual.getGreen(), expected.getGreen());
            assertEquals(actual.getBlue(), expected.getBlue());
            assertEquals(actual.getAlpha(), expected.getAlpha());
        }

        @Test
        void throwsException()
        {
            ResourceLoadException exception = assertThrows(
                ResourceLoadException.class,
                () -> container.get("xxx"));
            assertEquals("Unbekannte Farbe: xxx", exception.getMessage());;
        }
    }
}
