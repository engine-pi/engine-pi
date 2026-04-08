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
import static pi.CustomAssertions.assertToStringClassName;
import static pi.CustomAssertions.assertToStringFieldOrder;
import static pi.CustomAssertions.assertToStringFieldValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import pi.Controller;

class TextTest
{
    Text text;

    @BeforeEach
    void setUp()
    {
        Controller.instantMode(false);
        text = new Text("Hello World");
    }

    @Test
    void textCreation()
    {
        assertNotNull(text);
    }

    @Test
    void nullContent()
    {
        Text text = new Text(null);
        assertNotNull(text);
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
            assertToStringFieldValue("content", "Hello World", text);
        }

        @Test
        void differentContent()
        {
            assertToStringFieldValue("content", "42", new Text(42));
        }

        @Test
        void fieldOrder()
        {
            assertToStringFieldOrder(new String[] { "content", "x", "y" },
                new Text(42));
        }
    }
}
