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

import de.pirckheimer_gymnasium.engine_pi.actor.Text;
import de.pirckheimer_gymnasium.engine_pi.event.CollisionEvent;
import de.pirckheimer_gymnasium.engine_pi.event.CollisionListener;

class Goodie extends Text implements CollisionListener<Snake>
{
    private SnakeScene scene;

    public Goodie(SnakeScene scene)
    {
        super("Eat Me!", 1);
        setColor("red");
        this.scene = scene;
    }

    @Override
    public void onCollision(CollisionEvent<Snake> collisionEvent)
    {
        scene.increaseScore();
        scene.makeNewHead = true;
        remove();
        scene.placeRandomGoodie();
    }
}
