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
 *     "https://inf-schule.de/algorithmen/rekursivealgorithmen/selbstaehnlichkeit/uebungen">inf-schule.de</a>
 */
public class SierpinskiTriangleTurtleGraphics extends TurtleGraphics
{

    public SierpinskiTriangleTurtleGraphics()
    {
        initalState.speed(20);
    }

    public void drawTriangle(double sideLength)
    {
        if (sideLength < 0.2)
        {
            return;
        }
        for (int i = 0; i < 3; i++)
        {

            turtle.forward(sideLength);
            turtle.left(120);
            drawTriangle(sideLength / 2);
        }
    }

    public void draw()
    {
        drawTriangle(8);
    }

    public static void main(String[] args)
    {
        new SierpinskiTriangleTurtleGraphics().start();
    }
}
