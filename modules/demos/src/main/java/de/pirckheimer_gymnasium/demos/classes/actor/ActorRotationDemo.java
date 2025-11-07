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
package de.pirckheimer_gymnasium.demos.classes.actor;

import static de.pirckheimer_gymnasium.engine_pi.Vector.v;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Polygon;

public class ActorRotationDemo extends Scene
{
    public ActorRotationDemo()
    {
        createPolygon().setColor("yellow");
        createPolygon().setColor("green").setRotation(90);
        createPolygon().setColor("red").setRotation(180);
        createPolygon().setColor("blue").setRotation(270);
        getCamera().setMeter(60);
    }

    private Polygon createPolygon()
    {
        Polygon polygon = new Polygon(v(0, 0), v(1, 0.3), v(3, 0), v(1, -0.3));
        add(polygon);
        return polygon;
    }

    public static void main(String[] args)
    {
        Game.start(new ActorRotationDemo());
    }
}
