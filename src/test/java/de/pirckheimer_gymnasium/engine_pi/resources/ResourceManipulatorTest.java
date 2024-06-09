package de.pirckheimer_gymnasium.engine_pi.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.util.ImageUtil;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
public class ResourceManipulatorTest
{
    ImageContainer container = Game.getImages();

    String resourceName = "Pixel-Adventure-1/Background/Blue.png";

    @BeforeEach
    void clear()
    {
        container.clear();
        container.removeManipulator();
    }

    @Test
    public void testDoNotManpulateReturnNull()
    {
        container.addManipulator((resourceName, image) -> {
            return null;
        });
        var image = container.get(resourceName);
        assertEquals(64, image.getWidth());
    }

    @Test
    public void testManipulation()
    {
        container.addManipulator((resourceName, image) -> {
            return ImageUtil.scale(image, 2);
        });
        var image = container.get(resourceName);
        assertEquals(128, image.getWidth());
    }
}
