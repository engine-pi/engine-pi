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
    Scene scene();

    /* ___ Image (Bild) _____________________________________________________ */

    default Image addImage(String filePath, double pixelPerMeter)
    {
        Image actor = new Image(filePath, pixelPerMeter);
        scene().add(actor);
        return actor;
    }

    default Image addImage(String filePath, double width, double height)
    {
        Image actor = new Image(filePath, width, height);
        scene().add(actor);
        return actor;
    }

    /* ___ RegularPolygon (Reguläres Vieleck) _______________________________ */

    default RegularPolygon addRegularPolygon(int numSides, double radius)
    {
        RegularPolygon actor = new RegularPolygon(numSides, radius);
        scene().add(actor);
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
        actor.position(x, y);
        return actor;
    }

    default RegularPolygon addRegularPolygon(int numSides, double x, double y)
    {
        return addRegularPolygon(numSides, 1, x, y);
    }
}
