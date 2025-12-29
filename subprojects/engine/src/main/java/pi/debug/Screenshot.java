package pi.debug;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import pi.Configuration;
import pi.annotations.Internal;
import pi.util.FileUtil;
import pi.util.ImageUtil;

/**
 *
 *
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public class Screenshot
{
    /**
     * Speichert ein Bildschirmfoto des aktuellen Spielfensters in den Ordner
     * {@code ~/engine-pi}.
     */
    @Internal
    public void takeScreenshot()
    {
        BufferedImage screenshot = new BufferedImage(
                Configuration.windowWidthPx, Configuration.windowHeightPx,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) screenshot.getGraphics();
        // render(source -> source.render(g, Configuration.windowWidthPx,
        // Configuration.windowHeightPx));
        String dir = FileUtil.getHome() + "/engine-pi";
        FileUtil.createDir(dir);
        ImageUtil.write(screenshot,
                dir + "/screenshot_" + System.nanoTime() + ".png");
    }

}
