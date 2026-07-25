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
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.awt.Font;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

/**
 * @author Josef Friedrich
 */
@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
class FontTest
{
    @Test
    void fontProperties()
    {
        Font font = new Font("Monospaced", Font.PLAIN, 16);
        assertEquals("Monospaced", font.getFamily());
        assertEquals("Monospaced", font.getName());
        assertEquals(16, font.getSize());
        assertFalse(font.isBold());
        assertFalse(font.isItalic());
    }
}
