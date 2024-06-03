package rocks.friedrich.engine_omega.demos.physics.single_aspects;

import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Joint;
import rocks.friedrich.engine_omega.actor.Rectangle;

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
