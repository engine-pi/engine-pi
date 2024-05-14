package rocks.friedrich.engine_omega.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import rocks.friedrich.engine_omega.Game;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
public class ImagesContainerTest
{
    @Test
    public void testLoad()
    {
        var image = Game.getImages()
                .get("Pixel-Adventure-1/Background/Blue.png");
        assertEquals(64, image.getWidth());
    }
}
