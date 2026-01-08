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
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ColorSchemeContainerTest
{
    ColorSchemeContainer container;

    @BeforeEach
    void setup()
    {
        container = ColorSchemeContainer.getInstance();
        container.reset();
    }

    @Test
    void testInitializationWithPredefinedSchemes()
    {
        for (PredefinedColorScheme predefinedScheme : PredefinedColorScheme
                .values())
        {
            assertEquals(predefinedScheme.getScheme(),
                    container.get(predefinedScheme.getScheme().name()));
        }
    }

    @Test
    void testAddAndGetColorScheme()
    {
        ColorScheme customScheme = new ColorScheme("CustomScheme");
        container.add(customScheme);
        assertEquals(customScheme, container.get("CustomScheme"));
    }

    @Test
    void testGetNonExistentScheme()
    {
        assertThrows(RuntimeException.class, () -> {
            container.get("NonExistentScheme");
        });
    }

    @Test
    void testGetPredefinedScheme()
    {
        assertEquals("Java", container.get("Java").name());
    }

    @Test
    void testOverwriteExistingScheme()
    {
        ColorScheme newScheme = new ColorScheme("Java");
        container.add(newScheme);
        assertEquals(newScheme, container.get("Java"));
    }
}
