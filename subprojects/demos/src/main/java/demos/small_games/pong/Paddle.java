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
package demos.small_games.pong;

import pi.Controller;
import pi.Rectangle;
import pi.physics.BodyType;

/**
 * Ein Schläger des Ping-Pong-Spiels.
 *
 * <p>
 * Dieser Schläger wird als weißes Rechteck dargestellt.
 * </p>
 *
 * @repolink https://github.com/MeghnaSaha/PongGame-JAVA/blob/master/Paddle.java
 *
 * @author Josef Friedrich
 */
public class Paddle extends Rectangle
{
    static
    {
        Controller.instantMode(false);
    }

    /**
     * e Die sichtbare Fläche der des Ping-Pong-Tisches in Meter.
     */
    private final PongTable table;

    /**
     * Entfernung, wie viele Meter ein Schläger bei einem Tastendruck nach oben
     * oder nach unten bewegt werden soll.
     */
    private final double MOVEMENT_DISTANCE = 1;

    /**
     * Wie viele Meter ein Schläger aus dem Spielfeld ragen darf.
     */
    private final double HIDDEN_LENGTH = 2;

    /**
     * Abstand vom Spielfeldrand.
     */
    private final double BORDER_PADDING = 1;

    /**
     * Im automatischen Modus folgt der Schläger dem Ball.
     */
    private final boolean automatic = false;

    /**
     * @param sideSign Gibt an, auf welcher Seite sich der Schläger befindet.
     *
     *     <p>
     *     {@code +1} steht für rechts und {@code -1} für links. Da das Zentrum
     *     des Koordinatensystems in der Mitte des Spielfensters liegt, kann
     *     einfache Multiplikation mit diesem Attribut dazu verwendet werden, um
     *     das Objekt richtig zu platzieren.
     *     </p>
     * @param table Die sichtbare Fläche der des Ping-Pong-Tisches in Meter.
     */
    public Paddle(int sideSign, PongTable table)
    {
        super(0.5, 5);
        this.table = table;
        color("white");
        bodyType(BodyType.STATIC);
        restitution(1);
        center(sideSign * (table.bounds.xRight() - BORDER_PADDING), 0);
    }

    public void moveUp()
    {
        if (automatic)
        {
            return;
        }

        // Damit die Schläger nicht aus dem Spielfeld bewegt werden.
        if (y() + height() + MOVEMENT_DISTANCE - HIDDEN_LENGTH > table.bounds
            .yTop())
        {
            return;
        }

        moveBy(0, MOVEMENT_DISTANCE);
    }

    public void moveDown()
    {
        if (automatic)
        {
            return;
        }

        // Damit die Schläger nicht aus dem Spielfeld bewegt werden.
        if (y() - MOVEMENT_DISTANCE + HIDDEN_LENGTH < table.bounds.yBottom())
        {
            return;
        }

        moveBy(0, -MOVEMENT_DISTANCE);
    }

    public static void main(String[] args)
    {
        Controller.start(new PongTable());
    }
}
