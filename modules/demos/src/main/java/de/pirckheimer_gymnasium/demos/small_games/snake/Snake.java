/*
 * Nach: https://github.com/engine-alpha/tutorials/blob/master/src/eatutorials/gameloop/ActualSnake.java
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
package de.pirckheimer_gymnasium.demos.small_games.snake;

import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.actor.Square;

class Snake extends Square
{
    Snake next = null;

    public Snake()
    {
        super(0.75);
        setColor("white");
    }

    void moveHead(double dX, double dY)
    {
        Vector mycenter = getCenter();
        moveBy(dX, dY);
        if (next != null)
        {
            next.moveChildren(mycenter);
        }
    }

    void moveChildren(Vector newCenter)
    {
        Vector mycenter = getCenter();
        setCenter(newCenter);
        // Je größer die Verzögerung, desto größer ist der Abstand zwischen den
        // einzelnen Körperteilen der Schlange
        delay(0.05, () -> {
            if (next != null)
            {
                next.moveChildren(mycenter);
            }
        });
    }
}
