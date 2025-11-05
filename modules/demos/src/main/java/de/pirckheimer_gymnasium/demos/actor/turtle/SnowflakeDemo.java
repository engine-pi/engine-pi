/*
 * https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha-edu/src/main/java/ea/edu/turtle/Turtle.java
 *
 * Engine Alpha ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2020 Michael Andonie and contributors.
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
package de.pirckheimer_gymnasium.demos.actor.turtle;

import de.pirckheimer_gymnasium.engine_pi.actor.Turtle;

public class SnowflakeDemo
{

    private Turtle turtle;

    public SnowflakeDemo()
    {
        turtle = new Turtle();
        drawSnowFlake(10, 3);
    }

    private void drawSnowFlake(double length, int depth)
    {
        turtle.liftPen();
        turtle.rotate(180);
        turtle.move(5);
        turtle.rotate(-90);
        turtle.move(3);
        turtle.rotate(-90);
        turtle.lowerPen();

        for (int i = 0; i < 3; i++)
        {
            drawCurve(length, depth);
            turtle.rotate(-120);
        }

        turtle.liftPen();
        turtle.rotate(180);
        turtle.move(-5);
        turtle.rotate(-90);
        turtle.move(-3);
        turtle.rotate(-90);
        turtle.lowerPen();
    }

    private void drawCurve(double length, int depth)
    {
        if (depth == 0)
        {
            turtle.move(length);
        }
        else
        {
            drawCurve(length / 3, depth - 1);
            turtle.rotate(60);
            drawCurve(length / 3, depth - 1);
            turtle.rotate(-120);
            drawCurve(length / 3, depth - 1);
            turtle.rotate(60);
            drawCurve(length / 3, depth - 1);
        }
    }

    public static void main(String[] args)
    {
        new SnowflakeDemo();
    }

}
