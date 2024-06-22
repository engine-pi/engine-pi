package de.pirckheimer_gymnasium.engine_pi_demos.actor;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.actor.Rectangle;
import de.pirckheimer_gymnasium.engine_pi.physics.FixtureBuilder;

public class RectangleDemo extends ActorBaseScene
{
    public RectangleDemo()
    {
        // Breite und Höhe ist gleich 1 Meter
        Rectangle r1 = new Rectangle();
        // Durch Angabe von Breite und Höhe.
        Rectangle r2 = new Rectangle(2, 2);
        r2.setPosition(3, 0);
        //
        Rectangle r3 = new Rectangle(3, 3, () -> {
            return FixtureBuilder.rectangle(2, 2);
        });
        r3.setPosition(7, 0);
        add(r1, r2, r3);
    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(new RectangleDemo());
    }
}
