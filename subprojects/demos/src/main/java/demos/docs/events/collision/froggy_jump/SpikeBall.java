/*
 * Source: https://github.com/engine-alpha/tutorials/blob/master/src/eatutorials/collision/FroggyJump.java
 *
 * Engine Alpha ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2024 Michael Andonie and contributors.
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
package demos.docs.events.collision.froggy_jump;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/docs/manual/events/collision.md

import pi.Image;
import pi.Layer;
import pi.event.CollisionEvent;
import pi.event.CollisionListener;

class SpikeBall extends Image implements CollisionListener<Frog>
{
    public SpikeBall()
    {
        super("froggy/Spiked-Ball.png", 40);
        gravityScale(0);
        addCollisionListener(Frog.class, this);
    }

    public static SpikeBall setupSpikeBall(double x, double y, Layer layer)
    {
        SpikeBall ball = new SpikeBall();
        ball.center(x, y);
        SpikeSensor sensor = new SpikeSensor(ball);
        sensor.position(x - 1, y - 8);
        layer.add(ball, sensor);
        return ball;
    }

    @Override
    public void onCollision(CollisionEvent<Frog> collisionEvent)
    {
        collisionEvent.colliding().kill();
    }
}
