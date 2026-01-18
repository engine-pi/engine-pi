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

import pi.util.ImageUtil;
import pi.util.TextAlignment;

/**
 * @author Josef Friedrich
 */
@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
public class ImageFontTest
{
    ImageFont imageFont;

    @BeforeEach
    public void createImageFont()
    {
        imageFont = new ImageFont("image-font/tetris",
                ImageFontCaseSensitivity.TO_UPPER);
    }

    private void write(BufferedImage image, String filename)
    {
        ImageUtil.write(image, "/home/jf/Downloads/" + filename + ".png");
    }

    @Test
    public void testSingleLine()
    {
        write(imageFont.render("Hello, World."), "single-line");
    }

    @Test
    public void testMultiLine()
    {
        write(imageFont.render("Hello,\nWorld.\nHello, Universe."),
            "multi-line");
    }

    @Test
    public void testTextAlignmentCenter()
    {
        imageFont.alignment(TextAlignment.CENTER);
        write(imageFont.render("Hello,\nWorld.\nHello, Universe."), "center");
    }

    @Test
    public void testCaseSensitivity()
    {
        imageFont.caseSensitivity(ImageFontCaseSensitivity.TO_LOWER);
        assertThrows(RuntimeException.class, () -> imageFont.render("hello"));
    }

    @Test
    public void testMethodChaining()
    {
        write(
            imageFont.basePath("image-font/tetris")
                .extension("png")
                .caseSensitivity(ImageFontCaseSensitivity.TO_UPPER)
                .alignment(TextAlignment.LEFT)
                .throwException(false)
                .pixelMultiplication(4)
                .color(Color.BLUE)
                .lineWidth(20)
                .render("chaining"),
            "chaining");
    }

    @Test
    public void testThrowException()
    {
        write(imageFont.throwException(false).render("!"), "throw-no-error");
    }

    @Test
    public void testThrows()
    {
        assertThrows(RuntimeException.class, () -> imageFont.render("!"));
    }
}
