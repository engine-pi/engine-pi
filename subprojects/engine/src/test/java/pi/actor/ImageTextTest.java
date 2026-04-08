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

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import pi.actor.ImageText.CaseSensitivity;
import pi.actor.ImageText.Font;
import pi.util.ImageUtil;
import pi.util.TextAlignment;

/**
 * @author Josef Friedrich
 */
@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
class ImageTextTest
{
    Font font;

    @BeforeEach
    void setUp()
    {
        font = new Font("image-font/tetris", CaseSensitivity.TO_UPPER);
    }

    private void write(BufferedImage image, String filename)
    {
        ImageUtil.write(image, "/home/jf/Downloads/" + filename + ".png");
    }

    @Test
    void singleLine()
    {
        write(font.render("Hello, World."), "single-line");
    }

    @Test
    void multiLine()
    {
        write(font.render("Hello,\nWorld.\nHello, Universe."), "multi-line");
    }

    @Test
    void textAlignmentCenter()
    {
        font.alignment(TextAlignment.CENTER);
        write(font.render("Hello,\nWorld.\nHello, Universe."), "center");
    }

    @Test
    void caseSensitivity()
    {
        font.caseSensitivity(CaseSensitivity.TO_LOWER);
        assertThrows(RuntimeException.class, () -> font.render("hello"));
    }

    @Test
    void methodChaining()
    {
        write(
            font.basePath("image-font/tetris")
                .extension("png")
                .caseSensitivity(CaseSensitivity.TO_UPPER)
                .alignment(TextAlignment.LEFT)
                .throwException(false)
                .pixelMultiplication(4)
                .color(Color.BLUE)
                .lineWidth(20)
                .render("chaining"),
            "chaining");
    }

    @Test
    void throwExceptionFalse()
    {
        write(font.throwException(false).render("!"), "throw-no-error");
    }

    @Test
    void throwExceptionTrue()
    {
        assertThrows(RuntimeException.class, () -> font.render("!"));
    }
}
