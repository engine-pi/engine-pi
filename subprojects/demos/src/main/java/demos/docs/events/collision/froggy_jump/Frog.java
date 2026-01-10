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

import java.awt.event.KeyEvent;

import pi.Controller;
import pi.Image;
import pi.Vector;
import pi.event.FrameUpdateListener;

class Frog extends Image implements FrameUpdateListener
{
    private boolean canJump = true;

    private static double MAX_SPEED = 4;

    public Frog()
    {
        super("froggy/Frog.png", 25);
        makeDynamic();
        rotationLocked(true);
    }

    public void setJumpEnabled(boolean jumpEnabled)
    {
        this.canJump = jumpEnabled;
    }

    public void kill()
    {
        Controller.transitionToScene(new DeathScreen());
    }

    @Override
    public void onFrameUpdate(double pastTime)
    {
        Vector velocity = this.velocity();
        // A: Die Blickrichtung des Frosches steuern
        if (velocity.x() < 0)
        {
            flippedHorizontally(true);
        }
        else
        {
            flippedHorizontally(false);
        }
        // B: Horizontale Bewegung steuern
        if (Controller.isKeyPressed(KeyEvent.VK_A))
        {
            if (velocity.x() > 0)
            {
                velocity(new Vector(0, velocity.y()));
            }
            applyForce(Vector.LEFT.multiply(600));
        }
        else if (Controller.isKeyPressed(KeyEvent.VK_D))
        {
            if (velocity.x() < 0)
            {
                velocity(new Vector(0, velocity.y()));
            }
            applyForce(Vector.RIGHT.multiply(600));
        }
        if (Math.abs(velocity.x()) > MAX_SPEED)
        {
            velocity(new Vector(MAX_SPEED * Math.signum(velocity.x()),
                    velocity.y()));
        }
        // C: Wenn möglich den Frosch springen lassen
        if (isGrounded() && velocity.y() <= 0 && canJump)
        {
            velocity(new Vector(velocity.x(), 0));
            applyImpulse(Vector.UP.multiply(180));
        }
    }
}
