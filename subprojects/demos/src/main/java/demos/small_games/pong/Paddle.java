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

import pi.actor.BodyType;
import pi.actor.Rectangle;

/**
 * Heißt im Deutschen oft Schläger
 *
 * @repolink https://github.com/MeghnaSaha/PongGame-JAVA/blob/master/Paddle.java
 *
 * @author Josef Friedrich
 */
public class Paddle extends Rectangle
{
    /**
     * Entfernung, wie viele Meter ein Schläger bei einem Tastendruck nach oben
     * oder nach unten bewegt werden soll.
     */
    private final double DISTANCE = 1.5;

    /**
     * Im automatischen Modus folgt der Schläger dem Ball.
     */
    private final boolean automatic = false;

    public Paddle()
    {
        super(0.5, 5);
        setColor("white");
        setBodyType(BodyType.STATIC);
        setElasticity(1);
    }

    public void moveUp()
    {
        if (automatic)
        {
            return;
        }
        moveBy(0, DISTANCE);
    }

    public void moveDown()
    {
        if (automatic)
        {
            return;
        }
        moveBy(0, -DISTANCE);
    }
}
