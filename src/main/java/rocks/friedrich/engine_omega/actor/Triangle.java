/*
 * Engine Omega ist eine anfängerorientierte 2D-Gaming Engine.
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
package rocks.friedrich.engine_omega.actor;

import rocks.friedrich.engine_omega.Vector;
import rocks.friedrich.engine_omega.annotations.API;
import rocks.friedrich.engine_omega.resources.Container;

/**
 * Beschreibt ein Dreieck.
 *
 * @author Josef Friedrich
 *
 * @see Triangle
 * @see Rectangle
 * @see Circle
 */
public class Triangle extends Polygon
{
    /**
     * Erzeugt ein neues Dreieck durch Angabe von drei Punkten.
     *
     * @param point1 Die Koordinate des ersten Eckpunkts.
     * @param point2 Die Koordinate des zweiten Eckpunkts.
     * @param point3 Die Koordinate des dritten Eckpunkts.
     *
     * @see ActorCreator#createTriangle(Vector, Vector, Vector)
     */
    public Triangle(Vector point1, Vector point2, Vector point3)
    {
        super(point1, point2, point3);
        setColor(Container.colors.getRed());
    }

    /**
     * Erzeugt ein neues Dreieck durch Angabe der x- und y-Koordinate von drei
     * Punkten.
     *
     * @param x1 Die x-Koordinate des ersten Eckpunkts.
     * @param y1 Die y-Koordinate des ersten Eckpunkts.
     * @param x2 Die x-Koordinate des zweiten Eckpunkts.
     * @param y2 Die y-Koordinate des zweiten Eckpunkts.
     * @param x3 Die x-Koordinate des dritten Eckpunkts.
     * @param y3 Die y-Koordinate des dritten Eckpunkts.
     *
     * @see ActorCreator#createTriangle(double, double, double, double, double,
     *      double)
     */
    @API
    public Triangle(double x1, double y1, double x2, double y2, double x3,
            double y3)
    {
        this(new Vector(x1, y1), new Vector(x2, y2), new Vector(x3, y3));
    }

    /**
     * Erzeugt ein gleichschenkliges Dreieck, dessen Symmetrieachse vertikal
     * ausgerichtet ist. Die Spitze zeigt nach oben.
     *
     * @param width  Die Breite des gleichschenkligen Dreicks - genauer gesagt
     *               die Länge der Basis.
     * @param height Die Höhe der Symmetrieachse.
     *
     * @see ActorCreator#createTriangle(double, double)
     */
    public Triangle(double width, double height)
    {
        this(new Vector(0, 0), new Vector(width, 0),
                new Vector(width / 2, height));
    }
}
