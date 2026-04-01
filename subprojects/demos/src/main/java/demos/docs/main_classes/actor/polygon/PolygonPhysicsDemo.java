/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2026 Josef Friedrich and contributors.
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
package demos.docs.main_classes.actor.polygon;

import pi.Controller;
import pi.Scene;
import pi.actor.Polygon;
import pi.actor.Rectangle;
import pi.graphics.geom.Vector;

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/main-classes/actor/polygon.md

/**
 * Demonstiert, wie sich die Figur {@link Polygon} in einer Physik-Simulation
 * verhält.
 *
 * @author Josef Friedrich
 *
 * @since 0.45.0
 */
public class PolygonPhysicsDemo extends Scene
{
    public PolygonPhysicsDemo()
    {
        backgroundColor("blue green");
        gravityOfEarth();

        add(new Polygon(new Vector(1, 1), new Vector(0.5, 10), new Vector(7, 8),
                new Vector(3, 5), new Vector(3, 1)).density(1)
                    .restitution(0.95)
                    .center(-4, 7)
                    .color("yellow")
                    .rotateBy(60)
                    .makeDynamic()
                    .applyImpulse(new Vector(200, 0)));

        add(new Rectangle(15, 1).center(0, -7).makeStatic());
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new PolygonPhysicsDemo());
    }
}
