package de.pirckheimer_gymnasium.engine_pi_demos.physics.single_aspects;

import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Joint;
import de.pirckheimer_gymnasium.engine_pi.actor.Rectangle;

abstract class BaseJointScene extends Scene
{
    protected final Rectangle a;

    protected final Rectangle b;

    protected Joint<?> joint;

    public BaseJointScene()
    {
        getCamera().setMeter(100);
        a = new Rectangle();
        a.setCenter(-1, 0);
        a.makeDynamic();
        b = new Rectangle();
        b.setCenter(1, 0);
        b.makeDynamic();
        add(a, b);
        delay(0.1, () -> {
            a.applyImpulse(1, 5);
            b.applyImpulse(-1, -5);
        });
        delay(5, () -> {
            joint.release();
        });
    }
}
