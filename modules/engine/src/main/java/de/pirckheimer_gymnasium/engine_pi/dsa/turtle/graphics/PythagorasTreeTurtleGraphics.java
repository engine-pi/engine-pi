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
package de.pirckheimer_gymnasium.engine_pi.dsa.turtle.graphics;

import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.TurtleGraphics;

/**
 * Zeichnet den
 * <a href="https://de.wikipedia.org/wiki/Pythagoras-Baum">Pythagoras-Baum</a>.
 *
 * <a href=
 * "https://programmierkonzepte.ch/engl/index.php?inhalt_links=&inhalt_mitte=turtle/rekursionen.inc.php">https://programmierkonzepte.ch</a>
 */
public class PythagorasTreeTurtleGraphics extends TurtleGraphics
{

    public PythagorasTreeTurtleGraphics()
    {
        initalState.speed(1000).position(-1.5, -5).direction(90)
                .warpMode(false);
    }

    public void draw()
    {
        drawTree(3, 7);
    }

    private void drawTree(double sideLength, int depth)
    {
        if (depth < 1)
        {
            return;
        }
        drawSquare(sideLength);
        turtle.forward(sideLength);
        double s1 = sideLength / Math.sqrt(2);
        turtle.left(45);
        drawTree(s1, depth - 1);
        turtle.right(90);
        turtle.forward(s1);
        drawTree(s1, depth - 1);
        turtle.backward(s1);
        turtle.left(45);
        turtle.backward(sideLength);
    }

    private void drawSquare(double sideLength)
    {
        turtle.lowerPen();
        for (int i = 0; i < 4; i++)
        {
            turtle.forward(sideLength);
            turtle.right(90);
        }
        turtle.liftPen();
    }

    public static void main(String[] args)
    {
        new PythagorasTreeTurtleGraphics().start();
    }
}
