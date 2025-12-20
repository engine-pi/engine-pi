package pi.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import pi.Resources;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import pi.util.ImageUtil;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
public class ResourceManipulatorTest
{
    ImageContainer container = Resources.images;

    String resourceName = "Pixel-Adventure-1/Background/Blue.png";

    @BeforeEach
    @AfterEach
    void clear()
    {
        container.clear();
        container.removeManipulator();
    }

    @Test
    public void testDoNotManipulateReturnNull()
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
            return ImageUtil.multiplyPixel(image, 2);
        });
        var image = container.get(resourceName);
        assertEquals(128, image.getWidth());
    }
}
