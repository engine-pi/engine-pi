package rocks.friedrich.engine_omega.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import rocks.friedrich.engine_omega.Game;

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
