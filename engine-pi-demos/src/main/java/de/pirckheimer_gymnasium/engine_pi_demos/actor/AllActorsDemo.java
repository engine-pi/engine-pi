package de.pirckheimer_gymnasium.engine_pi_demos.actor;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

public class AllActorsDemo extends Scene
{
    public AllActorsDemo()
    {
        addTriangle(-6, 0);
        addRectangle(-4, 0);
        addPentagon(-2, 0);
        addCircle();
        getCamera().setMeter(120);
    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(new AllActorsDemo());
    }
}
