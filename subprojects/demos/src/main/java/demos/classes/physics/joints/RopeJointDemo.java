/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2024 Josef Friedrich and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package demos.classes.physics.joints;

import pi.Controller;
import pi.graphics.geom.Vector;

/**
 * Demonstriert die Klasse {@link pi.physics.joints.RopeJoint} und die Methode
 * {@link pi.actor.Actor#createRopeJoint(pi.actor.Actor, Vector, Vector, double)}
 */
public class RopeJointDemo extends BaseJointScene
{
    public RopeJointDemo()
    {
        super();
        joint = a.createRopeJoint(b, new Vector(0.25, 0.25),
                new Vector(0.75, 0.75), 3);
        joint.addReleaseListener(() -> {
            System.out.println("Verbindung wurde gelöst");
        });
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.debug();
        Controller.start(new RopeJointDemo());
    }
}
