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
package demos.classes.actor;

import static pi.Vector.v;

import pi.Controller;
import pi.Scene;
import pi.actor.Polygon;

public class ActorRotationDemo extends Scene
{
    public ActorRotationDemo()
    {
        createPolygon().color("yellow");
        createPolygon().color("green").rotation(90);
        createPolygon().color("red").rotation(180);
        createPolygon().color("blue").rotation(270);
        camera().meter(60);
    }

    private Polygon createPolygon()
    {
        Polygon polygon = new Polygon(v(0, 0), v(1, 0.3), v(3, 0), v(1, -0.3));
        add(polygon);
        return polygon;
    }

    public static void main(String[] args)
    {
        Controller.start(new ActorRotationDemo());
    }
}
