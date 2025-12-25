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
package pi.dsa.turtle.graphics;

import pi.dsa.turtle.TurtleGraphics;

/**
 * @see <a href=
 *     "https://inf-schule.de/algorithmen/rekursivealgorithmen/selbstaehnlichkeit/uebungen/quadratbaum">inf-schule.de</a>
 */
public class SquareTreeTurtleGraphics extends TurtleGraphics
{
    public SquareTreeTurtleGraphics()
    {
        initalState.position(0, -6).speed(20);
    }

    private void drawTree(double sideLength)
    {
        if (sideLength > 0.2)
        {
            turtle.left(90);
            turtle.forward(sideLength / 2);
            turtle.right(90);
            turtle.forward(sideLength);
            turtle.left(45);
            drawTree(sideLength / 2);
            turtle.right(135);
            turtle.forward(sideLength);
            turtle.left(45);
            drawTree(sideLength / 2);
            turtle.right(135);
            turtle.forward(sideLength);
            turtle.right(90);
            turtle.forward(sideLength / 2);
            turtle.right(90);
        }
    }

    public void draw()
    {
        // Damit der Baum nach oben wächst
        turtle.setDirection(90);
        drawTree(5);
    }

    public static void main(String[] args)
    {
        new SquareTreeTurtleGraphics().start();
    }
}
