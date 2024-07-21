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
package de.pirckheimer_gymnasium.engine_pi.actor;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;

/**
 * @author Josef Friedrich
 */
public class RegularPolygon extends Polygon
{
    public RegularPolygon(int numSides, double radius)
    {
        super(RegularPolygon.getVectors(numSides, radius));
    }

    public RegularPolygon(int numSides)
    {
        this(numSides, 1);
    }

    public static Vector[] getVectors(int numSides, double radius)
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

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(new Scene()
        {
            {
                int x = -10;
                for (int i = 3; i < 8; i++)
                {
                    addRegularPolygon(i, 2, x, 0);
                    addText(i + "").setPosition(x - 0.25, -4).setColor("white");
                    x += 5;
                }
            }
        });
    }
}
