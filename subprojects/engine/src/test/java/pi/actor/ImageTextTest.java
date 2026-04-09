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

import static pi.CustomAssertions.assertToStringClassName;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import pi.Controller;
import pi.actor.ImageText.CaseSensitivity;
import pi.actor.ImageText.Font;

/**
 * @author Josef Friedrich
 *
 * @since 0.46.0
 */
@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
class ImageTextTest
{
    ImageText text;

    @BeforeEach
    void setUp()
    {
        Controller.instantMode(false);
        text = new ImageText(
                new Font("image-font/tetris", CaseSensitivity.TO_UPPER))
                    .content("test");
    }

    @Nested
    class ToStringTest
    {
        @Test
        void className()
        {
            assertToStringClassName(text);
        }
    }
}
