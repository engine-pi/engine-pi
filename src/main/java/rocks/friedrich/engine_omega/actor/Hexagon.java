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
    public Hexagon()
    {
        super(6);
    }

    public Hexagon(double radius)
    {
        super(6, radius);
    }
}
