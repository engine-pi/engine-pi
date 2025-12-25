package pi.debug;

import pi.Scene;
import pi.Vector;

/**
 * Bereitet Debuginformationen auf.
 *
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
class DebugInformations
{
    private Scene scene;

    private double frameDuration;

    public DebugInformations(Scene scene, double frameDuration)
    {
        this.scene = scene;
        this.frameDuration = frameDuration;
    }

    public int actorsCount()
    {
        return scene.getWorldHandler().getWorld().getBodyCount();
    }

    /**
     * Bilder pro Sekunde. Frames per Second
     */
    public String fpsFormatted()
    {
        if (frameDuration == 0)
        {
            return "∞";
        }
        else
        {
            return String.valueOf(Math.round(1 / frameDuration));
        }
    }

    public Vector gravity()
    {
        return scene.getGravity();
    }

    /**
     * @return Eine formattierte Zeichenkette oder {@code null} falls keinen
     *     Schwerkraft gesetzt ist.
     */
    public String gravityFormatted()
    {
        Vector gravity = gravity();
        if (!gravity.isNull())
        {
            return String.format("G(x,y): %.2f,%.2f", gravity.getX(),
                    gravity.getY());
        }
        return null;
    }
}
