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
 * Demonstriert die Klasse {@link pi.physics.joints.PrismaticJoint} und die
 * Methode
 * {@link pi.actor.Actor#createPrismaticJoint(pi.actor.Actor, Vector, double)}
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
        Controller.instantMode(false);
        Controller.debug();
        Controller.start(new PrismaticJointDemo());
    }
}
