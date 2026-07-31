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
package demos.physics;

import static pi.Controller.config;

import pi.Controller;
import pi.Scene;
import pi.actor.ActorCreator;
import pi.actor.Polygon;
import pi.actor.Rectangle;
import pi.graphics.geom.Vector;

/**
 * Demonstriert den <b>ausgerichtete Begrenzungsrahmen</b> (OBB = oriented
 * bounding box) der verschiedenen Figuren.
 *
 * @author Josef Friedrich
 *
 * @since 0.53.0
 */
public class OBBDemo extends Scene
{

    public OBBDemo()
    {
        info().description(
            "Demonstriert den ausgerichtete Begrenzungsrahmen (OBB = oriented bounding box) der verschiedenen Figuren.");
        ActorCreator.createCage(this);

        add(new Rectangle(4, 2).makeDynamic()
            .applyForce(16000, 31000)
            .restitution(1)
            .rotateBy(42));

        add(new Polygon(new Vector(1, 1), new Vector(0.5, 10), new Vector(7, 8),
                new Vector(3, 5), new Vector(3, 1)).density(1)
                    .restitution(0.95)
                    .color("yellow")
                    .makeDynamic()
                    .applyImpulse(new Vector(200, 0)));
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        config.debug.enabled(true).renderOBBs(true);
        Controller.start(new OBBDemo());
    }
}
