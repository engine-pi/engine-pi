package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;
import de.pirckheimer_gymnasium.engine_pi.graphics.PaintingSurface;

/**
 * Eine <b>Szenen</b> mit einer <b>Malfläche</b> als Hintergrund.
 *
 * @author Josef Friedrich
 *
 * @since 0.40.0
 */
public class PaintingSurfaceScene extends Scene
{
    /**
     * Ein <b>Malfläche</b>, in die gemalt werden kann und dir als
     * Hintergrundbild angezeigt wird. Es kann zum Beispiel für Turtle-Grafiken
     * verwendet werden oder zur Simulation eines Malprogramms.
     *
     * @since 0.40.0
     */
    protected PaintingSurface paintingSurface;

    /**
     * @since 0.40.0
     */
    public PaintingSurface getPaintingSurface()
    {
        if (paintingSurface == null)
        {
            paintingSurface = new PaintingSurface(this);
        }
        return paintingSurface;
    }

    /**
     * @since 0.40.0
     */
    @Internal
    public final void render(Graphics2D g, int width, int height)
    {
        // Die Malfläche muss zuerst eingezeichnet werden, damit sie als
        // Hintergrund erscheint.
        if (paintingSurface != null)
        {
            g.drawImage(paintingSurface.getImage(), 0, 0, null);
        }
        super.render(g, width, height);
    }
}
