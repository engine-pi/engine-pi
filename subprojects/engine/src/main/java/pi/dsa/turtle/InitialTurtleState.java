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

import pi.Vector;
import pi.annotations.Setter;

/**
 * Speichert den <b>Startzustand</b> der Schildkröte, d.h. alle Einstellungen,
 * die die Schildkröte braucht, um mit einem Algorithmus eine Turtle-Grafik zu
 * zeichnen.
 *
 * <p>
 * Diese Klasse kann dazu benutzt werden, um den Anfangszustand zu speichern,
 * wenn derselbe Algorithmus oder verschiedene Algorithmen mehrmals
 * hintereinander ausgeführt werden und die Schildkröte einen definierten
 * Anfangszustand braucht.
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.40.0
 */
public class InitialTurtleState
{
    /**
     * Die <b>Startposition</b> der Schildkröte.
     *
     * @since 0.40.0
     */
    private Vector position = new Vector(0, 0);

    /**
     * Die <b>Richtung</b>, in die die Schildkröte schaut.
     *
     * @since 0.40.0
     */
    private double direction = 0;

    /**
     * Zeigt an, ob die Schildkröte momentan den <b>Stift gesenkt</b> hat und
     * zeichnet oder nicht.
     *
     * @since 0.40.0
     */
    private boolean penDown = true;

    /**
     * Die <b>Geschwindigkeit</b>, mit der sich die Schildkröte bewegt (in Meter
     * pro Sekunde).
     *
     * @since 0.40.0
     */
    private double speed = 6;

    /**
     * Im sogenannte <b>Warp-Modus</b> finden keine Animationen statt. Die
     * Turtle-Grafik wird so schnell wie möglich gezeichnet.
     *
     * @since 0.40.0
     */
    private boolean warpMode = false;

    /**
     * <b>Ignoriert</b> den Anfangszustand bei einer Ausführung des Algorithmus.
     * Die Schildkröte bleibt in dem Zustand, wie sie den vorherigen Algorithmus
     * verlassen hat.
     *
     * @since 0.40.0
     */
    private boolean ignore = false;

    /**
     * @since 0.40.0
     */
    private static InitialTurtleState getDefaults()
    {
        return new InitialTurtleState();
    }

    /**
     * @since 0.40.0
     */
    private static void copy(InitialTurtleState from, InitialTurtleState to)
    {
        to.position = from.position;
        to.direction = from.direction;
        to.penDown = from.penDown;
        to.speed = from.speed;
        to.warpMode = from.warpMode;
        to.ignore = from.ignore;
    }

    /**
     * @since 0.40.0
     */
    @Setter
    public InitialTurtleState set(InitialTurtleState state)
    {
        copy(state, this);
        return this;
    }

    /**
     * @since 0.40.0
     */
    public InitialTurtleState reset()
    {
        copy(getDefaults(), this);
        return this;
    }

    /**
     * @since 0.40.0
     */
    @Setter
    public InitialTurtleState ignore()
    {
        ignore = true;
        return this;
    }

    /**
     * Setzt die <b>Startposition</b> der Schildkröte als {@link Vector}.
     *
     * @param position Die <b>Startposition</b> der Schildkröte als
     *     {@link Vector}.
     *
     * @since 0.40.0
     */
    @Setter
    public InitialTurtleState position(Vector position)
    {
        this.position = position;
        return this;
    }

    /**
     * Setzt die <b>Startposition</b> der Schildkröte als x- und y-Koordinate.
     *
     * @param x Die <b>x</b>-Koordinate der <b>Startposition</b>.
     * @param y Die <b>y</b>-Koordinate der <b>Startposition</b>.
     *
     * @since 0.40.0
     */
    @Setter
    public InitialTurtleState position(double x, double y)
    {
        position(new Vector(x, y));
        return this;
    }

    /**
     * Setzt die <b>Blickrichtung</b> der Schildkröte.
     *
     * @param direction Die Blickrichtung der Schildkröte in Grad: 0°: nach
     *     rechts (Osten), 90°: nach oben (Norden) 180°: nach links (Westen)
     *     270°: nach unten (Süden)
     *
     * @since 0.40.0
     */
    @Setter
    public InitialTurtleState direction(double direction)
    {
        this.direction = direction;
        return this;
    }

    /**
     * @since 0.40.0
     */
    @Setter
    public InitialTurtleState drawLine(boolean drawLine)
    {
        this.penDown = drawLine;
        return this;
    }

    /**
     * Setzt die <b>Geschwindigkeit</b>, mit der sich die Schildkröte bewegt (in
     * Meter pro Sekunde).
     *
     * @param speed die <b>Geschwindigkeit</b>, mit der sich die Schildkröte
     *     bewegt (in Meter pro Sekunde).
     *
     * @since 0.40.0
     */
    @Setter
    public InitialTurtleState speed(double speed)
    {
        this.speed = speed;
        return this;
    }

    /**
     * @since 0.40.0
     */
    @Setter
    public InitialTurtleState warpMode(boolean warpMode)
    {
        this.warpMode = warpMode;
        return this;
    }

    /**
     * Wendet den Anfangszustand auf eine Schildkröte an.
     *
     * @since 0.40.0
     */
    public void apply(TurtleScene turtleScene)
    {
        if (ignore)
        {
            return;
        }
        turtleScene.setPosition(position);
        turtleScene.setDirection(direction);
        turtleScene.pen.set(penDown);
        turtleScene.animation.speed(speed);
        turtleScene.animation.warp(warpMode);
    }
}
