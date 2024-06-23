package de.pirckheimer_gymnasium.engine_pi_demos.physics.single_aspects;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Vector;

/**
 * Demonstriert die Klasse
 * {@link de.pirckheimer_gymnasium.engine_pi.actor.DistanceJoint} und die
 * Methode
 * {@link de.pirckheimer_gymnasium.engine_pi.actor.Actor#createDistanceJoint(de.pirckheimer_gymnasium.engine_pi.actor.Actor, Vector, Vector)}
 */
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
