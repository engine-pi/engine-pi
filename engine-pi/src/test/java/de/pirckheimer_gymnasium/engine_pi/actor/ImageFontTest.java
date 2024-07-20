package de.pirckheimer_gymnasium.engine_pi.actor;

import static org.junit.jupiter.api.Assertions.assertThrows;

import de.pirckheimer_gymnasium.engine_pi.util.TextAlignment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.pirckheimer_gymnasium.engine_pi.util.ImageUtil;
import org.junit.jupiter.api.condition.DisabledIf;

import java.awt.*;
import java.awt.image.BufferedImage;

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
        imageFont.setAlignment(TextAlignment.CENTER);
        write(imageFont.render("Hello,\nWorld.\nHello, Universe."), "center");
    }

    @Test
    public void testCaseSensitivity()
    {
        imageFont.setCaseSensitivity(ImageFontCaseSensitivity.TO_LOWER);
        assertThrows(RuntimeException.class, () -> imageFont.render("hello"));
    }

    @Test
    public void testMethodChaining()
    {
        write(imageFont.setBasePath("image-font/tetris").setExtension("png")
                .setCaseSensitivity(ImageFontCaseSensitivity.TO_UPPER)
                .setAlignment(TextAlignment.LEFT).setThrowException(false)
                .setPixelMultiplication(4).setColor(Color.BLUE)
                .render("chaining"), "chaining");
    }

    @Test
    public void testThrowException()
    {
        write(imageFont.setThrowException(false).render("!"), "throw-no-error");
    }

    @Test
    public void testThrows()
    {
        assertThrows(RuntimeException.class, () -> imageFont.render("!"));
    }
}
