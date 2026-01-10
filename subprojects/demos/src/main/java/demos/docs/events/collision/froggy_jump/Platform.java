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

import pi.Rectangle;
import pi.event.CollisionEvent;
import pi.event.CollisionListener;

class Platform extends Rectangle implements CollisionListener<Frog>
{
    public Platform(double width, double height)
    {
        super(width, height);
        makeStatic();
        addCollisionListener(Frog.class, this);
        color("brown");
    }

    @Override
    public void onCollision(CollisionEvent<Frog> collisionEvent)
    {
        double frogY = collisionEvent.colliding().anchor().y();
        if (frogY < y())
        {
            collisionEvent.ignoreCollision();
            collisionEvent.colliding().setJumpEnabled(false);
        }
    }

    @Override
    public void onCollisionEnd(CollisionEvent<Frog> collisionEvent)
    {
        collisionEvent.colliding().setJumpEnabled(true);
    }
}
