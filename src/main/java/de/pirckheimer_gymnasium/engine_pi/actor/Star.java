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

import de.pirckheimer_gymnasium.engine_pi.Vector;

/**
 * @author Josef Friedrich
 */
public class Star extends Polygon
{
    public Star()
    {
        super(Star.getVectors(0, 0, 2, 1, 7));
    }

    public static Vector[] getVectors(int x, int y, double radius,
            double innerRadius, int numPoints)
    {
        Vector[] vectors = new Vector[numPoints * 2];
        double angleStep = Math.PI / numPoints;
        double startAngle = Math.PI / 2.0;
        for (int i = 0; i < numPoints; i++)
        {
            vectors[i * 2] = new Vector(
                    x + radius * Math.cos(startAngle + 2 * i * angleStep),
                    y - radius * Math.sin(startAngle + 2 * i * angleStep));
            vectors[i * 2 + 1] = new Vector(
                    x + innerRadius
                            * Math.cos(startAngle + (2 * i + 1) * angleStep),
                    y - innerRadius
                            * Math.sin(startAngle + (2 * i + 1) * angleStep));
        }
        return vectors;
    }
}
