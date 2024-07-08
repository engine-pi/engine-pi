package de.pirckheimer_gymnasium.engine_pi.actor;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.pirckheimer_gymnasium.engine_pi.resources.ResourceLoadException;
import de.pirckheimer_gymnasium.engine_pi.util.ImageUtil;
import org.junit.jupiter.api.condition.DisabledIf;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
public class ImageFontTest
{
    ImageFont imageFont = new ImageFont("image-font/tetris",
            ImageFontCaseSensitivity.TO_UPPER);

    @Test
    public void testSingleLine()
    {
        ImageUtil.write(imageFont.render("Hello, World."),
                "/home/jf/Downloads/image-font.png");
    }

    @Test
    public void testThrows()
    {
        assertThrows(ResourceLoadException.class, () -> imageFont.render("!"));
    }
}
