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
 * Zeichnet die
 * <a href="https://de.wikipedia.org/wiki/Hilbert-Kurve">Hilbert-Kurve</a>.
 *
 * @author Albert Wiedemann
 *
 * @version 1.0
 */
public class HilbertCurveTurtleGraphics extends TurtleGraphics
{
    /**
     * Die Linienlänge.
     */
    private double length;

    /**
     * Die Rekursionstiefe.
     */
    private int depth;

    /**
     * Legt die Schildkröte an und startet die Zeichnung.
     */
    public HilbertCurveTurtleGraphics()
    {
        initalState.position(-7, 7).speed(100);
        depth = 5;
    }

    public void draw()
    {
        length = 15 / Math.pow(2.0, depth);
        drawElement(false, depth);
    }

    /**
     * Dreht um 90° nach links oder rechts.
     *
     * @param left Wenn wahr, dann Linksdrehung.
     */
    private void rotate(boolean left)
    {
        if (left)
        {
            turtle.left(90);
        }
        else
        {
            turtle.left(-90);
        }
    }

    /**
     * Zeichnet ein Element der Kurve.
     *
     * @param left Wenn wahr, dann Linksdrehung.
     * @param depth Die restliche Rekursionstiefe.
     */
    private void drawElement(boolean left, int depth)
    {
        if (depth > 0)
        {
            rotate(left);
            drawElement(!left, depth - 1);
            turtle.forward(length);
            rotate(!left);
            drawElement(left, depth - 1);
            turtle.forward(length);
            drawElement(left, depth - 1);
            rotate(!left);
            turtle.forward(length);
            drawElement(!left, depth - 1);
            rotate(left);
        }
    }

    public static void main(String[] args)
    {
        new HilbertCurveTurtleGraphics().start();
    }
}
