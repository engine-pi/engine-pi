package de.pirckheimer_gymnasium.engine_pi.debug;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import de.pirckheimer_gymnasium.engine_pi.Camera;
import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;

/**
 * Zeichnet das <b>Koordinatensystem</b>.
 */
public final class CoordinateSystemDrawer
{
    private static final int GRID_SIZE_IN_PIXELS = 150;

    private static final int GRID_SIZE_METER_LIMIT = 100000;

    private static final int DEBUG_TEXT_SIZE = 12;

    /**
     * Zeichnet das <b>Koordinatensystem</b>.
     *
     * @param g      Das {@link Graphics2D}-Objekt, in das gezeichnet werden
     *               soll.
     * @param scene  Die Szene, über die das Koordinatensystem gezeichnet werden
     *               soll.
     * @param width  Die Breite in Pixel.
     * @param height Die Höhe in Pixel.
     */
    @Internal
    public static void draw(Graphics2D g, Scene scene, int width, int height)
    {
        AffineTransform pre = g.getTransform();
        Camera camera = scene.getCamera();
        Vector position = camera.getPosition();
        double rotation = -camera.getRotation();
        g.setClip(0, 0, width, height);
        g.translate(width / 2, height / 2);
        double pixelPerMeter = camera.getMeter();
        g.rotate(Math.toRadians(rotation), 0, 0);
        g.translate(-position.getX() * pixelPerMeter,
                position.getY() * pixelPerMeter);
        int gridSizeInMeters = (int) Math
                .round(GRID_SIZE_IN_PIXELS / pixelPerMeter);
        double gridSizeInPixels = gridSizeInMeters * pixelPerMeter;
        double gridSizeFactor = gridSizeInPixels / gridSizeInMeters;
        if (gridSizeInMeters > 0 && gridSizeInMeters < GRID_SIZE_METER_LIMIT)
        {
            int windowSizeInPixels = Math.max(width, height);
            int startX = (int) (position.getX()
                    - windowSizeInPixels / 2 / pixelPerMeter);
            int startY = (int) ((-1 * position.getY())
                    - windowSizeInPixels / 2 / pixelPerMeter);
            startX -= (startX % gridSizeInMeters) + gridSizeInMeters;
            startY -= (startY % gridSizeInMeters) + gridSizeInMeters;
            startX -= gridSizeInMeters;
            int stopX = (int) (startX + windowSizeInPixels / pixelPerMeter
                    + gridSizeInMeters * 2);
            int stopY = (int) (startY + windowSizeInPixels / pixelPerMeter
                    + gridSizeInMeters * 2);
            g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, DEBUG_TEXT_SIZE));
            // Setzen der Gitterfarbe
            g.setColor(Resources.colors.get("white", 150));
            for (int x = startX; x <= stopX; x += gridSizeInMeters)
            {
                g.fillRect((int) (x * gridSizeFactor) - 1,
                        (int) ((startY - 1) * gridSizeFactor), 2,
                        (int) (windowSizeInPixels + 3 * gridSizeInPixels));
            }
            for (int y = startY; y <= stopY; y += gridSizeInMeters)
            {
                g.fillRect((int) ((startX - 1) * gridSizeFactor),
                        (int) (y * gridSizeFactor - 1),
                        (int) (windowSizeInPixels + 3 * gridSizeInPixels), 2);
            }
            for (int x = startX; x <= stopX; x += gridSizeInMeters)
            {
                for (int y = startY; y <= stopY; y += gridSizeInMeters)
                {
                    g.drawString(x + " / " + -y, (int) (x * gridSizeFactor + 5),
                            (int) (y * gridSizeFactor - 5));
                }
            }
        }
        g.setTransform(pre);
    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start();
    }
}
