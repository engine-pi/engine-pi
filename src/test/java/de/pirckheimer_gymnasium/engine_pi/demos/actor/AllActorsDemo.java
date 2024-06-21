package de.pirckheimer_gymnasium.engine_pi.demos.actor;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

public class AllActorsDemo extends Scene
{
    public AllActorsDemo()
    {
        createTriangle(-6, 0);
        createRectangle(-4, 0);
        createPentagon(-2, 0);
        createCircle();
        getCamera().setMeter(60);
    }

    public static void main(String[] args)
    {
        Game.start(new AllActorsDemo());
    }
}
