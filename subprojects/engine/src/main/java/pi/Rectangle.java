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

import java.util.function.Supplier;

import pi.actor.ActorAdder;
import pi.annotations.API;
import pi.physics.FixtureData;

/**
 * Beschreibt ein <b>Rechteck</b>.
 *
 * <p>
 * Das Rechteck ist standardmäßig <b>rot</b> gefärbt. Die Farbe Rot stellt für
 * <a href="https://www.byk-instruments.com/de/color-determines-shape">Itten</a>
 * die körperhafte Materie dar. Sie wirkt statisch und schwer. Er ordnet deshalb
 * der Farbe die statische Form des Quadrates zu.
 * </p>
 *
 * <p class="development-note">
 * Diese Klasse ist identisch mit {@link pi.actor.Rectangle}. Sie steht hier,
 * damit sie über das Hauptpaket importiert werden kann, also
 * {@code import pi.Rectangle;}
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.42
 */
public class Rectangle extends pi.actor.Rectangle
{
    /**
     * Erzeugt ein <b>Quadrat</b> mit der Seitenlängen von <b>einem Meter</b>.
     *
     * @see ActorAdder#addRectangle()
     */
    @API
    public Rectangle()
    {
        super();
    }

    /**
     * Erzeugt ein <b>Quadrat</b> unter Angabe der <b>Seitenlänge</b>.
     *
     * @param sideLength Die <b>Seitenlänge</b> des Quadrats in Meter.
     *
     * @see ActorAdder#addRectangle(double)
     *
     * @since 0.34.0
     */
    @API
    public Rectangle(double sideLength)
    {
        super(sideLength);
    }

    /**
     * Erzeugt ein <b>Rechteck</b> durch Angabe der <b>Breite</b> und
     * <b>Höhe</b>.
     *
     * @param width Die <b>Breite</b> des Rechtecks in Meter.
     * @param height Die <b>Höhe</b> des Rechtecks in Meter.
     *
     * @see ActorAdder#addRectangle(double, double)
     */
    @API
    public Rectangle(double width, double height)
    {
        super(width, height);
    }

    /**
     * Erzeugt ein <b>Rechteck</b> durch Angabe der <b>Breite</b> und
     * <b>Höhe</b>.
     *
     * @param width Die <b>Breite</b> des Rechtecks in Meter.
     * @param height Die <b>Höhe</b> des Rechtecks in Meter.
     * @param shapeSupplier Eine Lamda-Funktion, die den <b>Umriss</b> liefert.
     */
    public Rectangle(double width, double height,
            Supplier<FixtureData> shapeSupplier)
    {
        super(width, height, shapeSupplier);
    }

    // Mit Absicht leer
}
