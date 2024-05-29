package rocks.friedrich.engine_omega.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import rocks.friedrich.engine_omega.Game;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
public class ImagesContainerTest
{
    ImagesContainer container = Game.getImages();

    @Test
    public void testLoad()
    {
        var listener = container.addContainerListener((resourceName, image) -> {
            return null;
        });
        var image = container.get("Pixel-Adventure-1/Background/Blue.png");
        assertEquals(64, image.getWidth());
        container.removeContainerListener(listener);
    }

    @Test
    public void testReplaceByListener() throws IOException
    {
        BufferedImage newImage = ImageIO.read(ResourceLoader
                .loadAsFile("Pixel-Adventure-1/Background/Blue.png"));
        var listener = container.addContainerListener((resourceName, image) -> {
            return newImage;
        });
        var image = container.get("Pixel-Adventure-1/Background/Blue.png");
        assertEquals(352, image.getWidth());
        container.removeContainerListener(listener);
    }
}
