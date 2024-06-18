package de.pirckheimer_gymnasium.engine_pi.debug;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colors;

import java.awt.Color;
import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;
import de.pirckheimer_gymnasium.engine_pi.util.Graphics2DUtil;

/**
 * Zeichnet einige <b>Informationsboxen</b> in das linke obere Eck.
 */
public final class InfoBoxDrawer
{
    private static void drawGravityVector(Graphics2D g, int x, int y,
            Vector gravity, Color color)
    {
        Graphics2DUtil.drawArrowLine(g, x, y, x + (int) gravity.getX() * 2,
                y + (int) gravity.getY() * -2, 5, 5, color);
    }

    /**
     * Zeichnet einige <b>Informationsboxen</b> in das linke obere Eck.
     *
     * @param g Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     */
    @Internal
    public static void draw(Graphics2D g, Scene scene, double frameDuration,
            int actorsCount)
    {
        // Einzelbilder pro Sekunden
        Graphics2DUtil.drawTextBox(g, "FPS: "
                + (frameDuration == 0 ? "∞" : Math.round(1 / frameDuration)),
                10, colors.get("blue"));
        // Anzahl an Figuren
        Graphics2DUtil.drawTextBox(g, "Actors: " + actorsCount, 50,
                colors.get("green"));
        // Schwerkraft
        Vector gravity = scene.getGravity();
        Color gravityColor = Resources.colorScheme.getIndigo();
        if (!gravity.isNull())
        {
            Graphics2DUtil.drawTextBox(g, String.format("G(x,y): %.2f,%.2f",
                    gravity.getX(), gravity.getY()), 90, gravityColor);
            drawGravityVector(g, 40, 145, gravity, gravityColor);
        }
    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start();
    }
}
