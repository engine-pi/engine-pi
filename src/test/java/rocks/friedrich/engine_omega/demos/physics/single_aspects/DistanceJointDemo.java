package rocks.friedrich.engine_omega.demos.physics.single_aspects;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Vector;

public class DistanceJointDemo extends BaseJointScene
{
    public DistanceJointDemo()
    {
        joint = a.createDistanceJoint(b, new Vector(0.25, 0.25),
                new Vector(0.75, 0.75));
        joint.addReleaseListener(() -> {
            System.out.println("Verbindung wurde gelöst");
        });
    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(new DistanceJointDemo());
    }
}