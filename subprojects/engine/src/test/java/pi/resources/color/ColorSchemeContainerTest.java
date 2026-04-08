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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ColorSchemeContainerTest
{
    ColorSchemeContainer container;

    @BeforeEach
    void setUp()
    {
        container = ColorSchemeContainer.getInstance();
        container.reset();
    }

    @Test
    void initializationWithPredefinedSchemes()
    {
        for (PredefinedColorScheme predefinedScheme : PredefinedColorScheme
            .values())
        {
            assertEquals(predefinedScheme.getScheme(),
                container.get(predefinedScheme.getScheme().name()));
        }
    }

    @Test
    void addAndGetColorScheme()
    {
        ColorScheme customScheme = new ColorScheme("CustomScheme");
        container.add(customScheme);
        assertEquals(customScheme, container.get("CustomScheme"));
    }

    @Test
    void getNonExistentScheme()
    {
        assertThrows(RuntimeException.class, () -> {
            container.get("NonExistentScheme");
        });
    }

    @Test
    void getPredefinedScheme()
    {
        assertEquals("Java", container.get("Java").name());
    }

    @Test
    void overwriteExistingScheme()
    {
        ColorScheme newScheme = new ColorScheme("Java");
        container.add(newScheme);
        assertEquals(newScheme, container.get("Java"));
    }

    @Nested
    class TestNames
    {
        @Test
        void namesReturnsAllSchemeNames()
        {
            String[] expectedNames = { "Gnome", "Java", "Android", "iOS",
                    "Tailwind CSS", "CustomScheme" };
            ColorScheme customScheme = new ColorScheme("CustomScheme");
            container.add(customScheme);
            String[] actualNames = container.names();
            assertArrayEquals(expectedNames, actualNames);
        }

        @Test
        void namesReturnsEmptyArrayAfterClear()
        {
            container.clear();
            String[] actualNames = container.names();
            assertEquals(0, actualNames.length);
        }
    }

    @Nested
    class TestNext
    {
        @Test
        void nextReturnsFirstScheme()
        {
            ColorScheme firstScheme = container.next();
            assertEquals("Gnome", firstScheme.name());
        }

        @Test
        void nextAdvancesToNextScheme()
        {
            container.next();
            ColorScheme secondScheme = container.next();
            assertEquals("Java", secondScheme.name());
        }

        @Test
        void nextCyclesBackToFirstScheme()
        {
            String[] schemeNames = container.names();
            for (int i = 0; i < schemeNames.length; i++)
            {
                container.next();
            }
            ColorScheme cycledScheme = container.next();
            assertEquals("Gnome", cycledScheme.name());
        }

        @Test
        void nextSequentialOrder()
        {
            String[] expectedNames = container.names();
            for (String expectedName : expectedNames)
            {
                ColorScheme scheme = container.next();
                assertEquals(expectedName, scheme.name());
            }
        }
    }
}
