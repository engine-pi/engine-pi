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

import pi.resources.Resources;

public class ColorContainerTest
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
        public void testPrimaryName()
        {
            assertNotNull(container.getSafe("blue"));
        }

        @Test
        public void testAlias()
        {
            assertNotNull(container.getSafe("blau"));
        }

        @Test
        public void testCaseInsensitivity()
        {
            assertNotNull(container.getSafe("BLUE"));
        }

        @Test
        public void testWhiteSpaces()
        {
            assertNotNull(container.getSafe("b l u e"));
        }

        @Test
        public void testHexCode()
        {
            Color actual = container.getSafe("#aabbccdd");
            Color expected = new Color(0xaa, 0xbb, 0xcc, 0xdd);
            assertEquals(actual.getRed(), expected.getRed());
            assertEquals(actual.getGreen(), expected.getGreen());
            assertEquals(actual.getBlue(), expected.getBlue());
            assertEquals(actual.getAlpha(), expected.getAlpha());
        }

        @Test
        public void testNoException()
        {
            assertNotNull(container.getSafe("XXX"));
        }
    }

    @Nested
    class GetColorTest
    {
        @Test
        public void testPrimaryName()
        {
            assertNotNull(container.get("blue"));
        }

        @Test
        public void testAlias()
        {
            assertNotNull(container.get("blau"));
        }

        @Test
        public void testCaseInsensitivity()
        {
            assertNotNull(container.get("BLUE"));
        }

        @Test
        public void testWhiteSpaces()
        {
            assertNotNull(container.get("b l u e"));
        }

        @Test
        public void testHexCode()
        {
            Color actual = container.get("#aabbccdd");
            Color expected = new Color(0xaa, 0xbb, 0xcc, 0xdd);
            assertEquals(actual.getRed(), expected.getRed());
            assertEquals(actual.getGreen(), expected.getGreen());
            assertEquals(actual.getBlue(), expected.getBlue());
            assertEquals(actual.getAlpha(), expected.getAlpha());
        }

        @Test
        public void testException()
        {
            assertThrows(RuntimeException.class, () -> container.get("XXX"));
        }
    }
}
