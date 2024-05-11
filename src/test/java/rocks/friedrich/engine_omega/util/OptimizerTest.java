package rocks.friedrich.engine_omega.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import rocks.friedrich.engine_omega.EngineOmega;

public class OptimizerTest
{
    @DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
    @Test
    public void testOptimizeImage()
    {
        assertFalse(GraphicsEnvironment.isHeadless());
        BufferedImage img = null;
        try
        {
            img = ImageIO
                    .read(EngineOmega.class.getResource("/assets/logo.png"));
        }
        catch (Exception e)
        {
            Logger.error("OptimizerTest", e.getLocalizedMessage());
        }
        assertNotNull(img);
        BufferedImage opt = Optimizer.toCompatibleImage(img);
        assertNotNull(opt);
        assertEquals(img.getWidth(), opt.getWidth());
        assertEquals(img.getHeight(), opt.getHeight());
        BufferedImage opt2 = Optimizer.toCompatibleImage(opt);
        assertEquals(opt.getColorModel(), opt2.getColorModel());
    }
}
