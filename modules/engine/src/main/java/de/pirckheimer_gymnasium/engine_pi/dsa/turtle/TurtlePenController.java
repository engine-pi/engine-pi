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
package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colors;

import java.awt.Color;

import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.actor.Actor;

// Go to file: file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtlePenControllerDemo.java

/**
 * Steuert den <b>Mal- bzw{@literal .} Zeichenstift</b> der Schildkröte.
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
     * Die <b>Linienstärke</b> in Pixel.
     *
     * @since 0.40.0
     */
    public int thickness = 1;

    /**
     * Die <b>Farbe</b> der Linie.
     *
     * @since 0.40.0
     */
    public Color color = colors.get("yellow");

    /**
     * Die aktuelle <b>Position des Stifts</b>.
     *
     * <p>
     * Diese Position wird bewegt und das Zentrum der Figur wird auf diese
     * Position gesetzt. Es reicht nicht, die Stiftposition über die Methode
     * {@link Actor#getCenter()} der Schildkrötenfigur zu bestimmen, denn bei
     * einer Rotation ändert sich das Zentrum.
     * </p>
     *
     * @since 0.40.0
     */
    Vector position = new Vector(0, 0);

    /**
     * Die aktuelle Schreib<b>richtung</b>.
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

    public TurtlePenController thickness(int thickness)
    {
        this.thickness = thickness;
        return this;
    }

    /**
     * @since 0.40.0
     */
    public TurtlePenController color(Color color)
    {
        this.color = color;
        return this;
    }

    /**
     * @since 0.40.0
     */
    public TurtlePenController color(String color)
    {
        this.color = colors.get(color);
        return this;
    }

    /**
     * @since 0.40.0
     */
    public TurtlePenController lower()
    {
        isDown = true;
        return this;
    }

    /**
     * @since 0.40.0
     */
    public TurtlePenController lift()
    {
        isDown = false;
        return this;
    }
}
