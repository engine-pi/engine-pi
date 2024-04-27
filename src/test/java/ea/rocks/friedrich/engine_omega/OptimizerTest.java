package ea.rocks.friedrich.engine_omega;

import rocks.friedrich.engine_omega.internal.util.Logger;
import rocks.friedrich.engine_omega.internal.util.Optimizer;
import rocks.friedrich.engine_omega.EngineOmega;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeFalse;

public class OptimizerTest {
    @Test
    public void optimizeImage () {
        assumeFalse(GraphicsEnvironment.isHeadless());

        BufferedImage img = null;

        try {
            img = ImageIO.read(EngineOmega.class.getResource("/assets/logo.png"));
        } catch (Exception e) {
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
