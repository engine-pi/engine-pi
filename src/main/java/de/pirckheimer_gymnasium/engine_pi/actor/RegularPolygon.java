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
        double angleStep = 2 * Math.PI / numSides;
        for (int i = 0; i < numSides; ++i)
        {
            double angle = i * angleStep;
            vectors[i] = new Vector(radius * Math.cos(angle),
                    radius * Math.sin(angle));
        }
        return vectors;
    }
}
