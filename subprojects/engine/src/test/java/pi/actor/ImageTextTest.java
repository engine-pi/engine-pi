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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pi.CustomAssertions.assertToStringClassName;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.condition.DisabledIf;

import pi.Controller;
import pi.actor.ImageText.CaseSensitivity;
import pi.actor.ImageText.Font;
import pi.util.ImageUtil;
import pi.graphics.boxes.HAlign;

/**
 * @author Josef Friedrich
 *
 * @since 0.46.0
 */
// @DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
class ImageTextTest
{
    ImageText text;

    Font font;

    @BeforeEach
    void setUp()
    {
        Controller.instantMode(false);
        font = new Font("image-text").supportsCase(CaseSensitivity.UPPER);
        text = new ImageText(font).content("test");
    }

    @Test
    void imageDimension()
    {
        assertEquals(32, text.image().getWidth());
        assertEquals(8, text.image().getHeight());
        text.content("1");
        assertEquals(8, text.image().getWidth());
    }

    @Nested
    class WriteTest
    {
        private Path tmpDir;

        @BeforeEach
        void setUpTemporaryDirectory() throws IOException
        {
            tmpDir = Files.createTempDirectory("image-text-test-");
        }

        private Path write(ImageText text, String filename)
        {
            Path outputPath = tmpDir.resolve(filename + ".png");
            ImageUtil.write(text.image(), outputPath.toString());
            return outputPath;
        }

        @Test
        void singleLine()
        {
            assertTrue(Files
                .exists(write(text.content("Hello, World."), "single-line")));
        }

        @Test
        void multiLine()
        {
            assertTrue(Files
                .exists(write(text.content("Hello,\nWorld.\nHello, Universe."),
                    "multi-line")));
        }

        @Test
        void methodChaining()
        {
            assertTrue(Files.exists(
                write(text.content("Test").hAlign(HAlign.RIGHT), "chaining")));
        }

        @Test
        void throwExceptionTrue()
        {
            assertThrows(RuntimeException.class, () -> text.content("!"));
        }

        @Test
        void throwExceptionFalse()
        {
            assertDoesNotThrow(() -> write(
                new ImageText(font.throwException(false)).content("!"),
                "throw-no-error"));
            assertTrue(Files.exists(tmpDir.resolve("throw-no-error.png")));
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
    }
}
