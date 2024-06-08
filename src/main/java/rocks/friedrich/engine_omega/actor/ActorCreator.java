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

import java.awt.Color;

import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.Vector;
import rocks.friedrich.engine_omega.annotations.API;

/**
 * Schnittstelle um {@link Actor}-Objekte leicher erzeugen zu können.
 *
 * <p>
 * Mit Hilfe dieses Interfaces können die Klassen {@link Scene} and
 * {@link rocks.friedrich.engine_omega.Layer Layer} um einige Hilfsmethoden
 * erweitert werden, ohne sie dabei mit vielen weiteren Methoden zu
 * überfrachten. Die erzeugten {@link Actor}-Objekt werden gleich zur Szene bzw.
 * zur Ebene hinzugefügt.
 * </p>
 *
 * @author Josef Friedrich
 *
 * @see Scene
 * @see rocks.friedrich.engine_omega.Layer
 */
public interface ActorCreator
{
    Scene getScene();
    /* ___ Circle (Kreis) ___________________________________________________ */

    /**
     * Erzeugt einen Kreis durch Angabe des Durchmessers.
     *
     * @param diameter Der Durchmesser des Kreises.
     * @param x        Die neue <code>x</code>-Koordinate.
     * @param y        Die neue <code>y</code>-Koordinate.
     *
     * @return Ein Kreis, der bereits zur Szene hinzugefügt wurde.
     */
    default Circle createCircle(double diameter, double x, double y)
    {
        Circle actor = new Circle(diameter);
        actor.setPosition(x, y);
        getScene().add(actor);
        return actor;
    }

    /**
     * Erzeugt einen Kreis mit einem Meter Durchmesser an einer neuen Position.
     *
     * @param x Die neue <code>x</code>-Koordinate.
     * @param y Die neue <code>y</code>-Koordinate.
     *
     * @return Ein Kreis, der bereits zur Szene hinzugefügt wurde.
     */
    default Circle createCircle(double x, double y)
    {
        return createCircle(1, x, y);
    }

    /**
     * Erzeugt einen Kreis mit einem Meter Durchmesser an einer neuen Position.
     *
     * @param x     Die neue <code>x</code>-Koordinate.
     * @param y     Die neue <code>y</code>-Koordinate.
     * @param color Die Farbe des Kreises.
     *
     * @return Ein Kreis, der bereits zur Szene hinzugefügt wurde.
     */
    default Circle createCircle(double x, double y, Color color)
    {
        Circle actor = createCircle(1, x, y);
        actor.setColor(color);
        return actor;
    }
    /* ___ Rectangle (Rechteck) _____________________________________________ */

    /**
     * Erzeugt ein Reckteck durch Angabe der Breite und Höhe sowie der Position.
     *
     * @param width  Die Breite des Rechtecks.
     * @param height Die Höhe des Rechtecks.
     * @param x      Die neue <code>x</code>-Koordinate.
     * @param y      Die neue <code>y</code>-Koordinate.
     *
     * @return Ein Rechteck, das bereits zur Szene hinzugefügt wurde.
     *
     * @see Rectangle#Rectangle(double, double)
     */
    default Rectangle createRectangle(double width, double height, double x,
            double y)
    {
        Rectangle actor = new Rectangle(width, height);
        actor.setPosition(x, y);
        getScene().add(actor);
        return actor;
    }

    /**
     * Erzeugt ein Reckteck durch Angabe der Breite und Höhe.
     *
     * @param width  Die Breite des Rechtecks.
     * @param height Die Höhe des Rechtecks.
     *
     * @return Ein Rechteck, das bereits zur Szene hinzugefügt wurde.
     *
     * @see Rectangle#Rectangle(double, double)
     */
    default Rectangle createRectangle(double width, double height)
    {
        return createRectangle(width, height, 0, 0);
    }

    /**
     * Erzeugt ein Quadrat mit der Seitenlängen von einem Meter an eine bestimme
     * Position.
     *
     * @param x Die neue <code>x</code>-Koordinate.
     * @param y Die neue <code>y</code>-Koordinate.
     *
     * @return Ein Rechteck, das bereits zur Szene hinzugefügt wurde.
     *
     * @see Rectangle#Rectangle()
     */
    default Rectangle createRectangle(int x, int y)
    {
        return createRectangle(1, 1, x, y);
    }
    /* ___ Triangle (Dreieck) _______________________________________________ */

    /**
     * Erzeugt ein neues Dreieck durch Angabe von drei Punkten.
     *
     * @param point1 Die Koordinate des ersten Eckpunkts.
     * @param point2 Die Koordinate des zweiten Eckpunkts.
     * @param point3 Die Koordinate des dritten Eckpunkts.
     *
     * @return Ein Dreieck, das bereits zur Szene hinzugefügt wurde.
     *
     * @see Triangle#Triangle(Vector, Vector, Vector)
     */
    default Triangle createTriangle(Vector point1, Vector point2, Vector point3)
    {
        Triangle actor = new Triangle(point1, point2, point3);
        getScene().add(actor);
        return actor;
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
     * @return Ein Dreieck, das bereits zur Szene hinzugefügt wurde.
     *
     * @see Triangle#Triangle(double, double, double, double, double, double)
     */
    @API
    default Triangle createTriangle(double x1, double y1, double x2, double y2,
            double x3, double y3)
    {
        Triangle actor = new Triangle(x1, y1, x2, y2, x3, y3);
        getScene().add(actor);
        return actor;
    }

    /**
     * Erzeugt ein gleichschenkliges Dreieck, dessen Symmetrieachse vertikal
     * ausgerichtet ist. Die Spitze zeigt nach oben.
     *
     * @param width  Die Breite des gleichschenkligen Dreicks - genauer gesagt
     *               die Länge der Basis.
     * @param height Die Höhe der Symmetrieachse.
     *
     * @return Ein Dreieck, das bereits zur Szene hinzugefügt wurde.
     *
     * @see Triangle#Triangle(double, double)
     */
    default Triangle createTriangle(double width, double height)
    {
        Triangle actor = new Triangle(width, height);
        getScene().add(actor);
        return actor;
    }

    default RegularPolygon createRegularPolygon(int numSides, double radius)
    {
        RegularPolygon actor = new RegularPolygon(numSides, radius);
        getScene().add(actor);
        return actor;
    }

    default RegularPolygon createRegularPolygon(int numSides)
    {
        return createRegularPolygon(numSides, 1);
    }

    default RegularPolygon createRegularPolygon(int numSides, double radius,
            double x, double y)
    {
        RegularPolygon actor = createRegularPolygon(numSides, radius);
        actor.setPosition(x, y);
        return actor;
    }

    default RegularPolygon createRegularPolygon(int numSides, double x,
            double y)
    {
        return createRegularPolygon(numSides, 1, x, y);
    }
    /* ___ Text _______________________________________________ */

    default Text createText(String content, double height)
    {
        Text actor = new Text(content, height);
        getScene().add(actor);
        return actor;
    }

    default Text createText(String content, double height, double x, double y)
    {
        Text actor = createText(content, height);
        actor.setPosition(x, y);
        return actor;
    }

    default Text createText(String content, double x, double y)
    {
        return createText(content, 1, x, y);
    }
}
