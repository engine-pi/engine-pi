/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha-examples/src/main/java/ea/example/showcase/billard/Edge.java
 *
 * Engine Alpha ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2018 Michael Andonie and contributors.
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
package demos.billard;

import java.awt.Color;

import pi.actor.Polygon;
import pi.graphics.geom.Vector;

public class Edge extends Polygon
{
    public static final double WIDTH = 500;

    public static final double HEIGHT = 20;

    public Edge(double x, double y)
    {
        super(new Vector(0, 0), new Vector(HEIGHT, HEIGHT),
                new Vector(WIDTH - HEIGHT, HEIGHT), new Vector(WIDTH, 0));
        position(x, y);
        color(new Color(45, 90, 40));
        makeStatic();
    }
}
