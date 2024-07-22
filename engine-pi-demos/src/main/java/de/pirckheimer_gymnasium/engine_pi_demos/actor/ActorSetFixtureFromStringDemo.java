package de.pirckheimer_gymnasium.engine_pi_demos.actor;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Image;

public class ActorSetFixtureFromStringDemo extends Scene
{
    public ActorSetFixtureFromStringDemo()
    {
        setMeter(200);
        Image image = new Image("dude/box/obj_box001.png", 1, 1);
        image.setFixtures("R 0.25,0.25,0.5,0.5");
        add(image);
    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(new ActorSetFixtureFromStringDemo());
    }
}
