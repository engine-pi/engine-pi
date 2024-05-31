/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha-examples/src/main/java/ea/example/basics/CollisionTest.java
 *
 * Engine Alpha ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2020 Michael Andonie and contributors.
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
package rocks.friedrich.engine_omega.demos;

import java.awt.Color;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Circle;
import rocks.friedrich.engine_omega.actor.Rectangle;

public class CollisionTest extends Scene
{
    Rectangle wall = new Rectangle(20, 300);

    Circle ball = new Circle(20);

    public CollisionTest()
    {
        wall.setColor(Color.ORANGE);
        ball.setColor(Color.green);
        // ball.position.set(-200, 0);
        // wall.position.set(200, -200);
        add(wall, ball);
        ball.addCollisionListener(wall,
                (collisionEvent) -> System.out.println("COLLISION"));
        if (ball.overlaps(wall))
        {
            System.out.println("OVERLAP");
        }
        getCamera().setZoom(1);
    }

    public static void main(String[] args)
    {
        Game.start(500, 500, new CollisionTest());
    }
}
