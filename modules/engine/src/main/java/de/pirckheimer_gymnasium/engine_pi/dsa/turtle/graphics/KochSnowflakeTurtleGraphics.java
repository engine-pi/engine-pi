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
package de.pirckheimer_gymnasium.engine_pi.dsa.turtle.graphics;

import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.TurtleGraphicsSeries;

/**
 * Zeichnet die <a href="https://de.wikipedia.org/wiki/Koch-Kurve">kochschen
 * Schneeflocke</a>
 */
public class KochSnowflakeTurtleGraphics extends TurtleGraphicsSeries
{
    private double length;

    private int depth;

    public KochSnowflakeTurtleGraphics()
    {
        length = 10;
        depth = 1;
        numberOfSeries = -1;
        initalState.speed(50);
    }

    public void draw()
    {
        drawSnowFlake(length, depth);
    }

    private void drawSnowFlake(double length, int depth)
    {
        turtle.liftPen();
        turtle.left(180);
        turtle.forward(5);
        turtle.left(-90);
        turtle.forward(3);
        turtle.left(-90);
        turtle.lowerPen();

        for (int i = 0; i < 3; i++)
        {
            drawCurve(length, depth);
            turtle.left(-120);
        }

        turtle.liftPen();
        turtle.left(180);
        turtle.forward(-5);
        turtle.left(-90);
        turtle.forward(-3);
        turtle.left(-90);
        turtle.lowerPen();
    }

    private void drawCurve(double length, int depth)
    {
        if (depth == 0)
        {
            turtle.forward(length);
        }
        else
        {
            drawCurve(length / 3, depth - 1);
            turtle.left(60);
            drawCurve(length / 3, depth - 1);
            turtle.left(-120);
            drawCurve(length / 3, depth - 1);
            turtle.left(60);
            drawCurve(length / 3, depth - 1);
        }
    }

    protected void onDrawEnd()
    {
        depth++;
    }

    public static void main(String[] args)
    {
        new KochSnowflakeTurtleGraphics().start();
    }

}
