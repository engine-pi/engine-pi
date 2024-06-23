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

/**
 * Beschreibt ein reguläres bzw. regelmäßiges <b>Sechseck</b> bzw.
 * <b>Hexagon</b>.
 *
 * <p>
 * Alle <em>Seiten</em> des Sechsecks sich <em>gleich lang</em>. Außerdem sind
 * alle <em>Winkel</em> an den sechs Ecken <em>gleich groß</em>.
 * </p>
 *
 * @author Josef Friedrich
 *
 * @see RegularPolygon
 * @see Pentagon
 * @see Hexagon
 * @see Heptagon
 */
public class Hexagon extends RegularPolygon
{
    public Hexagon(double radius)
    {
        super(6, radius);
        setColor("redPurple");
    }

    public Hexagon()
    {
        this(1);
    }

    public static void main(String[] args)
    {
        Game.start(new Scene()
        {
            {
                // Erzeugen mit Hilfe des Konstruktors ohne Parameter.
                Hexagon h1 = new Hexagon();
                add(h1);
                //
                // Erzeugen mit Hilfe des Konstruktors ohne Parameter.
                Hexagon h2 = new Hexagon(4);
                h2.setPosition(-5, -5);
                h2.setColor("yellowOrange");
                add(h2);
                // Erzeugen mit Hilfe der createHexagon()-Methode
                addHexagon(3, 3, "yellowGreen");
                // Erzeugen mit Hilfe der createHexagon()-Methode und Rotation.
                Hexagon h4 = addHexagon(2, -3, 3, "redOrange");
                h4.rotateBy(45);
            }
        });
    }
}
