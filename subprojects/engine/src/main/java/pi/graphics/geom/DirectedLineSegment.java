/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2026 Josef Friedrich and contributors.
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
package pi.graphics.geom;

import pi.annotations.Getter;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/graphics2d/DirectedLineSegmentDemo.java

/**
 * Eine <b>gerichtete Strecke</b>.
 *
 * <p>
 * Diese Strecke ist definiert durch zwei Punkte nämlich dem {@link #from
 * Ursprung} und dem {@link #to Ziel}. Die Strecke liegt auf einer Linie, die
 * durch die beiden Punkte festgelegt ist.
 * </p>
 *
 * @param from Der Ursprung (steht auch für die Zahl 0)
 * @param to Das Ziel (steht auch für die Zahl 1)
 *
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public record DirectedLineSegment(Vector from, Vector to)
{
    /**
     * Gibt den <b>Differenzvektor</b> von Urspung zum Ziel zurück.
     *
     * @return Der <b>Differenzvektor</b> von Urspung zum Ziel.
     *
     * @since 0.42.0
     */
    public Vector difference()
    {
        return to.subtract(from);
    }

    /**
     * Berechnet einen <b>Punkt</b> auf der Linie, dessen Lage im
     * <b>Verhältnis</b> zu den beiden Punkten der Strecke festgelegt ist.
     *
     * @param factor Bei dem Faktor {@code 0} liegt der berechnete Punkt auf dem
     *     Ursprung, bei {@code 1} auf dem Ziel.
     *
     * @return Ein <b>Punkt</b> auf der Linie, dessen Lage im <b>Verhältnis</b>
     *     zu den beiden Punkten der Strecke festgelegt ist.
     *
     * @since 0.42.0
     */
    public Vector proportionalPoint(double factor)
    {
        return from.add(difference().multiply(factor));
    }

    /**
     * Berechnet einen <b>Punkt</b> auf der Linie, der sich in einer bestimmten
     * Entfernung zum Ursprung befindet.
     *
     * <p>
     * Positive Entfernungen liegen in der Richtung des {@link #to Ziels}.
     * </p>
     *
     * @param distance Der <b>Abstand</b> vom Ursprung.
     *
     * @return Ein <b>Punkt</b> auf der Linie, der sich in einer bestimmten
     *     Entfernung vom Ursprung befindet.
     *
     * @since 0.42.0
     */
    public Vector distancePoint(double distance)
    {
        return from.add(difference().normalize().multiply(distance));
    }

    /**
     * Berechnet einen <b>Punkt</b> auf einer Linie, die zur Strecke um einen
     * Winkel gedreht ist.
     *
     * <p>
     * Positive Entfernungen liegen in der Richtung des {@link #to Ziels}.
     * </p>
     *
     * @param distance Der <b>Abstand</b> vom Ursprung.
     * @param angle Positive Werte drehen die Linien gegen den Uhrzeigersinn
     *
     * @return Ein <b>Punkt</b> auf der Linie, der sich in einer bestimmten
     *     Entfernung vom Ursprung befindet.
     *
     * @since 0.42.0
     */
    public Vector distancePoint(double distance, double angle)
    {
        return from.add(difference().normalize().multiply(distance));
    }

    public double angle()
    {
        return difference().angle();
    }

    /**
     * Gibt die <b>Länge</b> der Strecke zurück.
     *
     * @return Die <b>Länge</b> der Strecke.
     *
     * @since 0.42.0
     */
    @Getter
    public double length()
    {
        return difference().length();
    }

    /**
     * Gibt eine in die <b>andere Richtung</b> verlaufende Strecke zurück.
     *
     * @return Eine in die <b>andere Richtung</b> verlaufende Strecke.
     *
     * @since 0.42.0
     */
    public DirectedLineSegment negate()
    {
        return new DirectedLineSegment(to, from);
    }
}
