package de.pirckheimer_gymnasium.engine_pi_demos.actor;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.actor.Pentagon;

/**
 * Demonstriert die Figur <b>Fünfeck</b> ({@link Pentagon}).
 *
 * @author Josef Friedrich
 */
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
