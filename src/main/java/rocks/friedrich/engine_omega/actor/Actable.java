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

/**
 * @author Josef Friedrich
 */
public interface Actable
{
    /**
     * Verschiebt die Gruppe ohne Bedingungen auf der Zeichenebene.
     *
     * @param vector Der Vektor, der die Verschiebung der Gruppe angibt.
     * @see Vector
     * @see #moveBy(double, double)
     */
    @API
    public void moveBy(Vector vector);

    /**
     * Verschiebt die Gruppe.
     *
     * <p>
     * Hierbei wird nichts anderes gemacht, als <code>moveBy(new
     * Vector(dx, dy))</code> auszuführen. Insofern ist diese Methode dafür gut,
     * sich nicht mit der Klasse {@link Vector} auseinandersetzen zu müssen.
     *
     * @param dX Die Verschiebung in Richtung X.
     * @param dY Die Verschiebung in Richtung Y.
     * @see #moveBy(Vector)
     */
    @API
    public void moveBy(double dX, double dY);
}
