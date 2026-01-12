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
package demos.classes.dsa.recursion;

import pi.Controller;
import pi.Scene;
import pi.Triangle;

/**
 * Erzeugt das Sierpinski-Dreieck.
 *
 * <p>
 * Der ursprünglich deutsche Name dieser Klasse war {@code SierpinskiDreieck}.
 * </p>
 *
 * @author Albert Wiedemann
 * @author Josef Friedrich
 *
 * @version 1.0
 *
 * @since 0.33.0
 */
public class SierpinskiTriangle
{

    /**
     * Legt das Grunddreieck an und stößt die Rekursion an.
     *
     * @param depth Die Rekursionstiefe.
     */
    public SierpinskiTriangle(int depth)
    {
        double width = 100;
        double height = 75;
        Triangle triangle = new Triangle(width, height);
        triangle.color("weiß");
        Scene scene = Controller.startedScene();
        scene.backgroundColor("yellow");
        scene.camera().meter(7);
        scene.camera().focus(triangle);
        makeStep(0, 0, width, height, depth);
    }

    /**
     * Ermittelt den Rekursionsschritt.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code SchrittAusführen}.
     * </p>
     *
     * @param x Die x-Position der Dreiecksspitze.
     * @param y Die y-Position der Dreiecksspitze.
     * @param width Die Breite des Umgebungsdreiecks.
     * @param height Die Höhe des Umgebungsdreiecks.
     * @param depth Die restliche Rekursionstiefe.
     */
    private void makeStep(double x, double y, double width, double height,
            int depth)
    {
        if (depth > 0)
        {
            height = height / 2;
            width = width / 2;
            depth = depth - 1;
            // Dreieck links unten
            makeStep(x, y, width, height, depth);
            // Dreieck rechts unten
            makeStep(x + width, y, width, height, depth);
            // Dreieck oben
            makeStep(x + width / 2, y + height, width, height, depth);
        }
        else
        {
            new Triangle(width, height).color("black").anchor(x, y);
        }
    }

    public static void main(String[] args)
    {
        new SierpinskiTriangle(5);
    }
}
