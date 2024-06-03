package rocks.friedrich.engine_omega.demos.physics.single_aspects;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Vector;

public class PrismaticJointDemo extends BaseJointScene
{
    public PrismaticJointDemo()
    {
        joint = a.createPrismaticJoint(b, new Vector(1, 0), 45);
        joint.addReleaseListener(() -> {
            System.out.println("Verbindung wurde gelöst");
        });
    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(new PrismaticJointDemo());
    }
}
