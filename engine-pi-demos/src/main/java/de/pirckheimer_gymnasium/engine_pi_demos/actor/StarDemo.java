package de.pirckheimer_gymnasium.engine_pi_demos.actor;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.actor.Star;

public class StarDemo extends ActorBaseScene
{
    public StarDemo()
    {
        // Kippt beim Aufprall
        Star triangle = new Star();
        triangle.makeStatic();
        triangle.rotateBy(45);
        add(triangle);
    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(new StarDemo());
    }
}
