package de.pirckheimer_gymnasium.engine_pi_demos.actor;

import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Rectangle;

abstract class ActorBaseScene extends Scene
{
    public ActorBaseScene()
    {
        setGravityOfEarth();
        Rectangle ground = new Rectangle(20, 1);
        ground.setPosition(-10, -8);
        ground.makeStatic();
        add(ground);
    }
}
