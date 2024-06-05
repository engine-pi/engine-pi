package rocks.friedrich.engine_omega.demos.actor;

import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Rectangle;

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
