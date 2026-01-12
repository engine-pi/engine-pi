/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025, 2026 Josef Friedrich and contributors.
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
package demos.classes.dsa.turtle;

import pi.Turtle;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/de/pirckheimer_gymnasium/engine_pi/dsa/turtle/TurtlePenController.java

/**
 * Demonstiert, wie mit <b>minimalen</b> Programmieraufwand eine
 * <b>Turtle</b>-Grafik gezeichnet werden kann.
 */
public class TurtlePenControllerDemo
{
    public static void main(String[] args)
    {
        Turtle turtle = new Turtle();
        turtle.pen.color("red").thickness(1);
        turtle.forward(5);

        turtle.pen.color("green").thickness(3);
        turtle.left(120);
        turtle.forward(5);

        turtle.pen.color("blue").thickness(5);
        turtle.left(120);
        turtle.forward(5);

        turtle.pen.lift();

        turtle.forward(7);
        turtle.pen.lower().color("brown");
        turtle.forward(2);
    }
}
