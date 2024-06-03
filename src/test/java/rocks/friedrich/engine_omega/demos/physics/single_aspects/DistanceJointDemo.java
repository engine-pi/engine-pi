package rocks.friedrich.engine_omega.demos.physics.single_aspects;

import java.awt.event.KeyEvent;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.Vector;
import rocks.friedrich.engine_omega.actor.DistanceJoint;
import rocks.friedrich.engine_omega.actor.Rectangle;
import rocks.friedrich.engine_omega.event.KeyListener;

public class DistanceJointDemo extends Scene implements KeyListener
{
    private final Rectangle a;

    private final Rectangle b;

    private final DistanceJoint joint;

    public DistanceJointDemo()
    {
        a = new Rectangle();
        a.setCenter(0, 0);
        a.makeDynamic();
        b = new Rectangle();
        b.setCenter(3, 0);
        b.makeDynamic();
        add(a, b);
        joint = a.createDistanceJoint(b, new Vector(0.25, 0.25),
                new Vector(0.75, 0.75));
        joint.addReleaseListener(() -> {
            System.out.println("Verbindung wurde gelöst");
        });
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
        case KeyEvent.VK_RIGHT -> a.applyImpulse(1, 5);
        case KeyEvent.VK_LEFT -> b.applyImpulse(-1, -5);
        case KeyEvent.VK_X -> joint.release();
        }
    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(new DistanceJointDemo());
    }
}
