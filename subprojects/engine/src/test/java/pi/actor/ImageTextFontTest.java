/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static pi.CustomAssertions.assertToStringClassName;
import static pi.CustomAssertions.assertToStringFieldOrder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import pi.actor.ImageText.CaseSensitivity;
import pi.actor.ImageText.Font;

/**
 * @author Josef Friedrich
 */
@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
class ImageTextFontTest
{
    Font font;

    @BeforeEach
    void setUp()
    {
        font = new Font("image-font/tetris")
            .supportsCase(CaseSensitivity.UPPER);
    }

    @Test
    void notExistingDirectory()
    {
        assertThrows(RuntimeException.class, () -> new Font("xxxxxxx"));
    }

    @Nested
    class ToStringTest
    {
        @Test
        void className()
        {
            assertToStringClassName(font);
        }

        @Test
        void fieldOrder()
        {
            assertToStringFieldOrder(
                new String[]
                { "basePath", "supportsCase" },
                font.basePath("image-font/tetris")
                    .extension("png")
                    .throwException(false));
        }
    }

}
