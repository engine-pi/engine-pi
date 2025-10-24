/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
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
package de.pirckheimer_gymnasium.engine_pi_demos.algorithms.graph;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.actor.Circle;
import de.pirckheimer_gymnasium.engine_pi.actor.RopeJoint;

public class GraphDemo extends Scene

{
    public GraphDemo()
    {
        setGravityOfEarth();
        Circle node1 = new Circle();
        node1.makeStatic();
        Circle node2 = new Circle();
        node2.setPosition(0, -1);

        node2.makeDynamic();

        var joint = node2.createRopeJoint(node1, new Vector(0.5, 0.5),
                new Vector(0.5, 0.5), 5);

        // var joint = node2.createDistanceJoint(node1, new Vector(0.5, 0.5),
        // new Vector(0.5, 0.5));

        // var joint = node2.createPrismaticJoint(node1, new Vector(0.5, 0.5),
        // 90);
        // joint.setLimits(5, 10);
        // joint.setMotorEnabled(true);
        // joint.setMaximumMotorForce(7);

        add(node1, node2);
    }

    public static void main(String[] args)
    {
        Game.debug();
        Game.start(new GraphDemo());
    }
}
