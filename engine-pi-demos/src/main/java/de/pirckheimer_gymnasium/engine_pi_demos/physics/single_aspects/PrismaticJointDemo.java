package de.pirckheimer_gymnasium.engine_pi_demos.physics.single_aspects;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Vector;

/**
 * Demonstriert die Klasse
 * {@link de.pirckheimer_gymnasium.engine_pi.actor.PrismaticJoint} und die
 * Methode
 * {@link de.pirckheimer_gymnasium.engine_pi.actor.Actor#createPrismaticJoint(de.pirckheimer_gymnasium.engine_pi.actor.Actor, Vector, double)}
 */
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
