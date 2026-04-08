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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pi.CustomAssertions.assertToStringClassName;
import static pi.debug.ToStringFormatter.clean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import pi.Controller;

class TextBlockTest
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

    /**
     * Testet die lines()-Methode der TextBlock-Klasse.
     *
     * <p>
     * Überprüft die korrekte Aufteilung von Textinhalten in einzelne Zeilen und
     * validiert das Verhalten bei verschiedenen Eingabeszenarien.
     * </p>
     */
    @Nested
    class LinesTest
    {
        @Test
        void singleLine()
        {
            String[] lines = text.lines();
            assertNotNull(lines);
            assertEquals(1, lines.length);
        }

        @Test
        void multipleLines()
        {
            String[] lines = new TextBlock("Line 1\nLine 2\nLine 3").lines();
            assertNotNull(lines);
            assertEquals(3, lines.length);
            assertEquals("Line 1", lines[0]);
            assertEquals("Line 2", lines[1]);
            assertEquals("Line 3", lines[2]);
        }

        @Test
        void nullContent()
        {
            assertNotNull(new TextBlock(null).lines());
        }

        @Test
        void emptyContent()
        {
            assertNotNull(new TextBlock("").lines());
        }
    }

    @Nested
    class ToStringTest
    {
        @Test
        void className()
        {
            assertToStringClassName(text);
        }

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
