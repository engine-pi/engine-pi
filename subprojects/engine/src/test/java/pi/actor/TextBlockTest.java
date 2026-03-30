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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pi.debug.ToStringFormatter.clean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import pi.Controller;

public class TextBlockTest
{
    TextBlock text;

    @BeforeEach
    void setUp()
    {
        Controller.instantMode(false);
        text = new TextBlock("Hello World");
    }

    @Test
    void textCreation()
    {
        assertNotNull(text);
    }

    @Test
    void nullContent()
    {
        TextBlock text = new TextBlock(null);
        assertNotNull(text);
    }

    @Nested
    class ToStringTest
    {
        @Test
        void content()
        {
            String result = clean(text.toString());
            assertTrue(result.contains("TextBlock"));
            assertTrue(result.contains("content=\"Hello World\""));
        }

        @Test
        void differentContent()
        {
            String result = clean(new Text(42).toString());
            assertNotNull(result);
            assertTrue(result.contains("content=\"42\""));
        }
    }
}
