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

import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.TurtleGraphicsSeries;

/**
 * Zeichnet die
 * <a href="https://de.wikipedia.org/wiki/L%C3%A9vy-C-Kurve">Lévy-C-Kurve</a>.
 *
 * @author Albert Wiedemann
 *
 * @version 1.0
 */
public class LevyCCurveTurtleGraphics extends TurtleGraphicsSeries
{
    private int depth;

    /**
     * Legt die Schildkröte an.
     */
    public LevyCCurveTurtleGraphics()
    {
        depth = 5;
        numberOfSeries = 5;
        initalState.position(-4, 0).speed(20);
    }

    public void draw()
    {
        drawCurve(depth);
    }

    /**
     * Zeichnet die Levy-C-Kurve mit der gegebenen Tiefe in der angegebenen
     * Farbe.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code KurveZeichnen}.
     * </p>
     *
     * @param depth Die Rekursionstiefe.
     */
    void drawCurve(int depth)
    {
        drawPart(7, depth);
    }

    /**
     * Zeichnet ein Element der Kurve durch Ausführen des nächsten
     * Rekursionsschrittes.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code SchrittAusführen}.
     * </p>
     *
     * @param length Die Linienlänge.
     * @param depth Die (restliche) Rekursionstiefe.
     */
    private void drawPart(double length, int depth)
    {
        if (depth > 0)
        {
            turtle.left(45);
            // 1 / Math.sqrt(2) == 0.7071067811865475
            // sqrt -> square root -> Quadratwurzel
            drawPart(length * 0.7071, depth - 1);
            turtle.left(-90);
            drawPart(length * 0.7071, depth - 1);
            turtle.left(45);
        }
        else
        {
            turtle.forward(length);
        }
    }

    public void onDrawEnd()
    {
        depth++;
        initalState.speed(10 * depth);
    }

    public static void main(String[] args)
    {
        new LevyCCurveTurtleGraphics().start();
    }
}
