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
package pi.dsa.turtle;

import static pi.Controller.colors;

import java.awt.Color;

import pi.actor.Actor;
import pi.graphics.geom.Vector;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtlePenControllerDemo.java

/**
 * Steuert den <b>Zeichenstift</b> der Schildkröte.
 *
 * @see TurtleController#pen
 *
 * @author Josef Friedrich
 *
 * @since 0.40.0
 */
public class TurtlePenController
{
    /**
     * Zeigt an, ob die Schildkröte momentan den <b>Stift gesenkt</b> hat und
     * zeichnet oder nicht.
     *
     * @since 0.40.0
     */
    boolean isDown = true;

    /**
     * Die <b>Linienstärke</b> des Zeichenstifts in Pixel.
     *
     * @since 0.40.0
     */
    public int thickness = 1;

    /**
     * Die <b>Farbe</b> des Zeichenstifts.
     *
     * @since 0.40.0
     */
    public Color color = colors.get("yellow");

    /**
     * Die aktuelle <b>Position des Zeichenstifts</b>.
     *
     * <p>
     * Diese Position wird bewegt und das Zentrum der Figur wird auf diese
     * Position gesetzt. Es reicht nicht, die Stiftposition über die Methode
     * {@link Actor#center()} der Schildkrötenfigur zu bestimmen, denn bei einer
     * Rotation ändert sich das Zentrum.
     * </p>
     *
     * @since 0.40.0
     */
    Vector position = new Vector(0, 0);

    /**
     * Die aktuelle Zeichen<b>richtung</b>.
     *
     * <p>
     * Wir speichern die aktuelle Rotation und verwenden nicht die Rotation der
     * grafischen Repräsentation. Möglicherweise läuft die Physics-Engine in
     * einem anderen Thread. Im Warp-Modus entstehen komisch verzerrte Grafiken.
     * </p>
     *
     * @since 0.40.0
     */
    double direction = 0;

    /**
     * Setzt die <b>Linienstärke</b> des Zeichenstifts in Pixel.
     *
     * @param thickness Die <b>Linienstärke</b> des Zeichenstifts in Pixel.
     *
     * @since 0.38.0
     */
    public TurtlePenController thickness(int thickness)
    {
        this.thickness = thickness;
        return this;
    }

    /**
     * <b>Ändert</b> die aktuelle <b>Linienstärke</b> des Zeichenstifts um einen
     * gegebenen Wert.
     *
     * <p>
     * Positive Werte erhöhen die Linienstärke, negative Werte verringern sie.
     * Führt die Änderung zu einer negativen Linienstärke, wird die Änderung
     * verworfen und der vorhandene Wert bleibt unverändert.
     * </p>
     *
     * @param thickness Die Differenz der Linienstärke (positiv zum Erhöhen,
     *     negativ zum Verringern); wird ignoriert, wenn die resultierende
     *     Linienstärke negativ wäre.
     *
     * @since 0.38.0
     */
    public TurtlePenController changeThickness(int thickness)
    {
        if (this.thickness + thickness < 1)
        {
            return this;
        }
        this.thickness += thickness;
        return this;
    }

    /**
     * Setzt die <b>Farbe</b> des Zeichenstifts als {@link Color}-Objekt.
     *
     * @param color Die <b>Farbe</b> des Zeichenstifts.
     *
     * @since 0.40.0
     */
    public TurtlePenController color(Color color)
    {
        this.color = color;
        return this;
    }

    /**
     * Setzt die <b>Farbe</b> des Zeichenstifts als Zeichenkette.
     *
     * @param color Die <b>Farbe</b> des Zeichenstifts.
     *
     * @since 0.40.0
     */
    public TurtlePenController color(String color)
    {
        this.color = colors.get(color);
        return this;
    }

    /**
     * <b>Senkt</b> den Zeichenstift, sodass die Schildkröte zeichnet.
     *
     * @since 0.40.0
     */
    public TurtlePenController lower()
    {
        isDown = true;
        return this;
    }

    /**
     * <b>Hebt</b> den Zeichenstift an, sodass die Schildkröte nicht zeichnet.
     *
     * @since 0.40.0
     */
    public TurtlePenController lift()
    {
        isDown = false;
        return this;
    }

    /**
     * <b>Setzt</b> den Zustand des Zeichenstifts.
     *
     * @param isDown Wird wahr übergeben, so schreibt der Stift. Wird falsch
     *     übergeben, so schreibt er nicht.
     *
     * @since 0.40.0
     */
    public TurtlePenController set(boolean isDown)
    {
        this.isDown = isDown;
        return this;
    }
}
