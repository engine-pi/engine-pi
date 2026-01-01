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
package pi;

import pi.actor.TriangleActor;
import pi.annotations.API;

/**
 * Beschreibt ein <b>Dreieck</b>.
 *
 * <p>
 * Das Dreieck ist standardmäßig <b>gelb</b> gefärbt. Gelb steht bei
 * <a href="https://www.byk-instruments.com/de/color-determines-shape">Itten</a>
 * für den Geist und das Denken. Gelb zeigt sich kämpferisch und aggressiv,
 * besitzt einen schwerelosen Charakter und diesem Charakter entspricht laut
 * Itten das Dreieck.
 * </p>
 *
 * <p class="development-note">
 * Diese Klasse ist identisch mit {@link pi.actor.TriangleActor}. Sie steht
 * hier, damit sie über das Hauptpaket importiert werden kann, also
 * {@code import pi.Triangle;}
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.42
 */
public class Triangle extends TriangleActor
{
    /**
     * Erzeugt ein <b>gleichseitiges</b> Dreieck mit einer Seitenlänge von <b>1
     * Meter</b>. Die Spitze zeigt nach oben.
     */
    public Triangle()
    {
        super(1);
    }

    /**
     * Erzeugt ein <b>gleichseitiges</b> Dreieck. Die Spitze zeigt nach oben.
     *
     * @param sideLength Die Seitenlänge des gleichseitigen Dreiecks.
     */
    public Triangle(double sideLength)
    {
        super(new Vector(0, 0), new Vector(sideLength, 0),
                new Vector(sideLength / 2.0, Math.sqrt(3) / 2.0 * sideLength));
    }

    /**
     * Erzeugt ein <b>gleichschenkliges</b> Dreieck, dessen Symmetrieachse
     * vertikal ausgerichtet ist. Die Spitze zeigt nach oben.
     *
     * @param width Die Breite des gleichschenkligen Dreiecks - genauer gesagt
     *     die Länge der Basis.
     * @param height Die Höhe der Symmetrieachse.
     */
    public Triangle(double width, double height)
    {
        super(new Vector(0, 0), new Vector(width, 0),
                new Vector(width / 2, height));
    }

    /**
     * Erzeugt ein neues Dreieck durch Angabe der <b>x- und y-Koordinaten</b>
     * von <b>drei Punkten</b>.
     *
     * @param x1 Die x-Koordinate des ersten Eckpunkts.
     * @param y1 Die y-Koordinate des ersten Eckpunkts.
     * @param x2 Die x-Koordinate des zweiten Eckpunkts.
     * @param y2 Die y-Koordinate des zweiten Eckpunkts.
     * @param x3 Die x-Koordinate des dritten Eckpunkts.
     * @param y3 Die y-Koordinate des dritten Eckpunkts.
     */
    @API
    public Triangle(double x1, double y1, double x2, double y2, double x3,
            double y3)
    {
        super(new Vector(x1, y1), new Vector(x2, y2), new Vector(x3, y3));
    }

    /**
     * Erzeugt ein neues Dreieck durch Angabe von <b>drei Punkten</b>.
     *
     * @param point1 Die Koordinate des ersten Eckpunkts.
     * @param point2 Die Koordinate des zweiten Eckpunkts.
     * @param point3 Die Koordinate des dritten Eckpunkts.
     */
    public Triangle(Vector point1, Vector point2, Vector point3)
    {
        super(point1, point2, point3);
    }
    // Mit Absicht leer
}
