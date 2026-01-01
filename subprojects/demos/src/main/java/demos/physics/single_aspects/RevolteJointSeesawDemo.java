/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha-examples/src/main/java/ea/example/showcase/JointDemo.java
 *
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

import static pi.Vector.v;

import pi.Circle;
import pi.Controller;
import pi.Rectangle;
import pi.Scene;
import pi.Vector;
import pi.actor.Polygon;

/**
 * Demonstriert die Klasse {@link pi.actor.RevoluteJoint} und die Methode
 * {@link pi.actor.Actor#createRevoluteJoint(pi.actor.Actor, Vector)} anhand
 * einer Wippe.
 */
public class RevolteJointSeesawDemo extends Scene
{
    public RevolteJointSeesawDemo()
    {
        Polygon base = new Polygon(v(0, 0), v(1, 0), v(0.5, 1));
        base.makeStatic();
        base.setColor("white");
        add(base);
        Rectangle seesaw = new Rectangle(5, 0.4);
        seesaw.makeDynamic();
        seesaw.setCenter(0.5, 1);
        seesaw.setColor("gray");
        seesaw.createRevoluteJoint(base, v(2.5, 0.2));
        add(seesaw);
        add(new Circle().setPosition(-2, 2).makeDynamic());
        add(new Circle().setPosition(2, 2.2).makeDynamic());
        setGravityOfEarth();
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.debug();
        Controller.start(new RevolteJointSeesawDemo());
    }
}
