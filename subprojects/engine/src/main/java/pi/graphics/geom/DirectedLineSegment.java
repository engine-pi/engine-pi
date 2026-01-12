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
import pi.annotations.Setter;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/graphics2d/DirectedLineSegmentDemo.java
// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/graphics/geom/DirectedLineSegmentDemo.java

/**
 * Eine <b>gerichtete Strecke</b>.
 *
 * <p>
 * Diese Strecke ist definiert durch zwei Punkte: den {@link #from Ursprung} und
 * das {@link #to Ziel}. Sie liegt auf einer Linie, die durch diese beiden
 * Punkte festgelegt ist.
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public class DirectedLineSegment
{
    /**
     * Der <b>Ursprung</b> (steht auch für die Zahl {@code 0}).
     */
    Vector from;

    /**
     * Das <b>Ziel</b> (steht auch für die Zahl {@code 1}).
     */
    Vector to;

    public DirectedLineSegment(Vector from, Vector to)
    {
        this.from = from;
        this.to = to;
    }

    /**
     * Gibt den <b>Ursprung</b> (steht auch für die Zahl {@code 0}) zurück.
     *
     * @return Der <b>Ursprung</b> (steht auch für die Zahl {@code 0}).
     */
    @Getter
    public Vector from()
    {
        return from;
    }

    /**
     * Setzt den <b>Ursprung</b> (steht auch für die Zahl {@code 0}).
     *
     * @param from Der <b>Ursprung</b> (steht auch für die Zahl {@code 0}).
     *
     * @return Eine Referenz auf die eigene Instanz der gerichteten Strecke,
     *     damit nach dem Erbauer/Builder-Entwurfsmuster die Eigenschaften der
     *     Strecke durch aneinander gekettete Setter festgelegt werden können,
     *     z. B. {@code lineSegment.from(..).to(..)}.
     */
    @Setter
    public DirectedLineSegment from(Vector from)
    {
        this.from = from;
        return this;
    }

    /**
     * Gibt das <b>Ziel</b> (steht auch für die Zahl {@code 1}) zurück.
     *
     * @return Das <b>Ziel</b> (steht auch für die Zahl {@code 1}).
     */
    @Getter
    public Vector to()
    {
        return to;
    }

    /**
     * Setzt das <b>Ziel</b> (steht auch für die Zahl {@code 1}).
     *
     * @param to Das <b>Ziel</b> (steht auch für die Zahl {@code 1}).
     *
     * @return Eine Referenz auf die eigene Instanz der gerichteten Strecke,
     *     damit nach dem Erbauer/Builder-Entwurfsmuster die Eigenschaften der
     *     Strecke durch aneinander gekettete Setter festgelegt werden können,
     *     z. B. {@code lineSegment.from(..).to(..)}.
     */
    @Setter
    public DirectedLineSegment to(Vector to)
    {
        this.to = to;
        return this;
    }

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

    public Vector proportionalPoint(double factor, double deltaAngle)
    {
        return from.add(Vector.ofAngle(angle() + deltaAngle).multiply(length())
                .multiply(factor));
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
     * @param distance Der <b>Abstand</b> vom Ursprung. Positive Entfernungen
     *     liegen in der Richtung des {@link #to Ziels}.
     * @param deltaAngle Der <b>Winkel</b>, um den die Linie gedreht wird.
     *     Positive Werte drehen die Linien gegen den Uhrzeigersinn.
     *
     * @return Ein <b>Punkt</b> auf der Linie, der sich in einer bestimmten
     *     Entfernung vom Ursprung befindet.
     *
     * @since 0.42.0
     */
    public Vector distancePoint(double distance, double deltaAngle)
    {
        return from
                .add(Vector.ofAngle(angle() + deltaAngle).multiply(distance));
    }

    /**
     * Ein Punkt, der auf einer senkrechten (rechten Winkel zur kreuzenden) Line
     * liegt.
     *
     * @param distanceOnLineSegment Der <b>Abstand</b> vom Ursprung. Positive
     *     Entfernungen liegen in der Richtung des {@link #to Ziels}.
     * @param distanceOnVertical
     *
     * @return
     */
    public Vector verticalPoint(double distanceOnLineSegment,
            double distanceOnVertical)
    {
        return distancePoint(distanceOnLineSegment);
    }

    /**
     * Gibt den <b>Winkel</b> dieser gerichteten Strecke zurück.
     *
     * <p>
     * Zeigt die gerichtete Strecke nach ...
     * </p>
     *
     * <ul>
     * <li>rechts: {@code 0} Grad</li>
     * <li>oben: {@code 90} Grad</li>
     * <li>links: {@code 180} Grad</li>
     * <li>unten: {@code -90} Grad</li>
     * </ul>
     *
     * @return der <b>Winkel</b> dieser gerichteten Strecke in Grad.
     *
     * @since 0.42.0
     */
    @Getter
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
