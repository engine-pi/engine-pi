package de.pirckheimer_gymnasium.engine_pi.demos.actor;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.actor.Pentagon;

public class PentagonDemo extends ActorBaseScene
{
    public PentagonDemo()
    {
        Pentagon pentagon = new Pentagon();
        pentagon.makeDynamic();
        add(pentagon);
    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(new PentagonDemo());
    }
}
