/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2024 Josef Friedrich and contributors.
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
package pi.actor;

import pi.Controller;
import pi.Text;
import pi.annotations.Getter;
import pi.graphics.geom.Vector;

/**
 * Beschreibt ein regelmäßiges <b>Vieleck</b> bzw{@literal .} reguläres
 * <b>Polygon</b>.
 *
 * <p>
 * Bei einem regelmäßigen Polygon sind alle Seiten gleich lang und alle
 * Innenwinkel gleich groß.
 * </p>
 *
 * @author Josef Friedrich
 */
public class RegularPolygon extends Polygon
{
    /**
     * Erzeugt einen reguläres <b>Polygon</b> durch Angabe der Anzahl der Seiten
     * und des Radius.
     *
     * @param numSides Die Anzahl an Seiten, die das Polygon haben soll.
     * @param radius Der Radius in Meter vom Mittelpunkt zum virtuellen Kreis,
     *     auf dem alle Ecken liegen.
     */
    public RegularPolygon(int numSides, double radius)
    {
        super(RegularPolygon.vectors(numSides, radius));
    }

    /**
     * Erzeugt einen reguläres <b>Polygon</b> durch Angabe der Anzahl der Seiten
     * und einem Radius von einem Meter.
     *
     * @param numSides Die Anzahl an Seiten, die das Polygon haben soll.
     */
    public RegularPolygon(int numSides)
    {
        this(numSides, 1);
    }

    @Getter
    private static Vector[] vectors(int numSides, double radius)
    {
        Vector[] vectors = new Vector[numSides];
        double angleStep = 360 / numSides;
        for (int i = 0; i < numSides; ++i)
        {
            vectors[i] = Vector.ofAngle(i * angleStep
                    // Damit unten keine Spitze ist.
                    - (180 - angleStep) / 2).multiply(radius)
                    .add(radius, radius);
        }
        return vectors;
    }

    /**
     * @hidden
     */
    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(scene -> {
            int x = -10;
            for (int i = 3; i < 8; i++)
            {
                scene.add(new RegularPolygon(i, 2).anchor(x, 0));
                scene.add(new Text(i + "").anchor(x - 0.25, -4).color("white"));
                x += 5;
            }
        });
        Controller.windowSize(1000, 500);
    }
}
