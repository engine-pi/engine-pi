package de.pirckheimer_gymnasium.engine_pi.demos.actor;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

public class RegularPolygonDemo extends Scene
{
    public RegularPolygonDemo()
    {
        int x = -10;
        for (int i = 3; i < 8; i++)
        {
            createRegularPolygon(i, 2, x, 0);
            createText(i + "", x - 0.25, -4);
            x += 5;
        }
    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(new RegularPolygonDemo());
    }
}
