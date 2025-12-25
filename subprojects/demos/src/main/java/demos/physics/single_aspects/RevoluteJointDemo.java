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
package demos.physics.single_aspects;

import pi.Game;
import pi.Scene;
import pi.Vector;
import pi.actor.Circle;
import pi.actor.Rectangle;
import pi.actor.RevoluteJoint;

/**
 * Demonstriert die Klasse {@link pi.actor.RevoluteJoint} und die Methode
 * {@link pi.actor.Actor#createRevoluteJoint(pi.actor.Actor, Vector)}
 */
public class RevoluteJointDemo extends Scene
{
    public RevoluteJointDemo()
    {
        Rectangle rectangle = addRectangle(1, 1);
        Circle circle = addCircle();
        RevoluteJoint joint = rectangle.createRevoluteJoint(circle,
                new Vector(0.25, 0.25));
        joint.addReleaseListener(() -> {
            System.out.println("Verbindung wurde gelöst");
        });
    }

    public static void main(String[] args)
    {
        Game.debug();
        Game.start(new RevoluteJointDemo());
    }
}
