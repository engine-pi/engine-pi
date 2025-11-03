package de.pirckheimer_gymnasium.engine_pi_demos.scenes;

import java.awt.Color;
import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import static de.pirckheimer_gymnasium.engine_pi.Resources.COLORS;

public class OverlayDemo extends Scene
{

    public OverlayDemo()
    {
        addCircle();
    }

    @Override
    public void renderOverlay(Graphics2D g, int width, int height)
    {
        Color old = g.getColor();
        g.setColor(COLORS.get("green", 100));
        g.fillRect(100, 100, width - 200, height - 200);
        g.setColor(old);
    }

    public static void main(String[] args)
    {
        Game.start(new OverlayDemo());
    }

}
