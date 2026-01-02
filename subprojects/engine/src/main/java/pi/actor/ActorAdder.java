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

import pi.Scene;

/**
 * Erzeugt verschiedene {@link Actor}-Objekte und fügt sie gleich zur Szene bzw.
 * zur Ebene hinzu.
 *
 * <p>
 * Mit Hilfe dieses Interfaces können die Klassen {@link Scene} and
 * {@link pi.Layer Layer} um einige Hilfsmethoden erweitert werden, ohne sie
 * dabei mit vielen weiteren Methoden zu überfrachten. Die erzeugten
 * {@link Actor}-Objekt werden gleich zur Szene bzw. zur Ebene hinzugefügt.
 * </p>
 *
 * @author Josef Friedrich
 *
 * @see Scene
 * @see pi.Layer
 */
public interface ActorAdder
{
    Scene getScene();

    /* ___ Hexagon (Sechseck) _____________________________________________ */

    default Hexagon addHexagon(double radius)
    {
        Hexagon actor = new Hexagon(radius);
        getScene().add(actor);
        return actor;
    }

    default Hexagon addHexagon(double radius, double x, double y)
    {
        Hexagon actor = addHexagon(radius);
        actor.setPosition(x, y);
        return actor;
    }

    default Hexagon addHexagon(double x, double y)
    {
        return addHexagon(0.5, x, y);
    }

    default Hexagon addHexagon(double x, double y, String color)
    {
        Hexagon actor = addHexagon(x, y);
        actor.setColor(color);
        return actor;
    }

    default Hexagon addHexagon(double radius, double x, double y, String color)
    {
        Hexagon actor = addHexagon(radius, x, y);
        actor.setColor(color);
        return actor;
    }
    /* ___ Image (Bild) _____________________________________________________ */

    default Image addImage(String filePath, double pixelPerMeter)
    {
        Image actor = new Image(filePath, pixelPerMeter);
        getScene().add(actor);
        return actor;
    }

    default Image addImage(String filePath, double width, double height)
    {
        Image actor = new Image(filePath, width, height);
        getScene().add(actor);
        return actor;
    }
    /* ___ Pentagon (Fünfeck) _______________________________________________ */

    default Pentagon addPentagon(double radius)
    {
        Pentagon actor = new Pentagon(radius);
        getScene().add(actor);
        return actor;
    }

    default Pentagon addPentagon(double radius, double x, double y)
    {
        Pentagon actor = addPentagon(radius);
        actor.setPosition(x, y);
        return actor;
    }

    default Pentagon addPentagon(double x, double y)
    {
        return addPentagon(0.5, x, y);
    }

    /* ___ RegularPolygon (Reguläres Vieleck) _______________________________ */

    default RegularPolygon addRegularPolygon(int numSides, double radius)
    {
        RegularPolygon actor = new RegularPolygon(numSides, radius);
        getScene().add(actor);
        return actor;
    }

    default RegularPolygon addRegularPolygon(int numSides)
    {
        return addRegularPolygon(numSides, 1);
    }

    default RegularPolygon addRegularPolygon(int numSides, double radius,
            double x, double y)
    {
        RegularPolygon actor = addRegularPolygon(numSides, radius);
        actor.setPosition(x, y);
        return actor;
    }

    default RegularPolygon addRegularPolygon(int numSides, double x, double y)
    {
        return addRegularPolygon(numSides, 1, x, y);
    }
}
