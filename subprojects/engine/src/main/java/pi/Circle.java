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

import pi.actor.ActorAdder;

/**
 * Beschreibt einen <b>Kreis</b>.
 *
 * <p>
 * Der Kreis ist standardmäßig <b>blau</b> gefärbt. Die Farbe Blau wirkt für
 * <a href="https://www.byk-instruments.com/de/color-determines-shape">Itten</a>
 * rund, erweckt ein Gefühl der Entspanntheit und Bewegung und steht für den
 * <em>„in sich bewegten Geist“</em>, wie er sich ausdrückt. Der Kreis
 * entspricht der Farbe Blau, da er ein Symbol der <em>„stetigen Bewegung“</em>
 * darstelle.
 * </p>
 *
 * <p class="development-note">
 * Diese Klasse ist identisch mit {@link pi.actor.Circle}. Sie steht hier, damit
 * sie über das Hauptpaket importiert werden kann, also
 * {@code import pi.Circle;}
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.42
 */
public class Circle extends pi.actor.Circle
{
    /**
     * Erzeugt einen <b>Kreis</b> mit <b>einem Meter Durchmesser</b>.
     *
     * @see ActorAdder#addCircle()
     *
     * @author Josef Friedrich
     */
    public Circle()
    {
        super(1);
    }

    /**
     * Erzeugt einen <b>Kreis</b> durch Angabe des <b>Durchmessers</b>.
     *
     * @param diameter Der <b>Durchmesser</b> des Kreises.
     *
     * @see ActorAdder#addCircle(double)
     */
    public Circle(double diameter)
    {
        super(diameter);
    }

    // Mit Absicht leer
}
