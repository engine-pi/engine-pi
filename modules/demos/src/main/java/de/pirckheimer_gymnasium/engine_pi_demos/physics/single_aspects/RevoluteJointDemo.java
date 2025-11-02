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
package de.pirckheimer_gymnasium.engine_pi_demos.physics.single_aspects;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.actor.Circle;
import de.pirckheimer_gymnasium.engine_pi.actor.Rectangle;
import de.pirckheimer_gymnasium.engine_pi.actor.RevoluteJoint;

/**
 * Demonstriert die Klasse
 * {@link de.pirckheimer_gymnasium.engine_pi.actor.RevoluteJoint} und die
 * Methode
 * {@link de.pirckheimer_gymnasium.engine_pi.actor.Actor#createRevoluteJoint(de.pirckheimer_gymnasium.engine_pi.actor.Actor, Vector)}
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
