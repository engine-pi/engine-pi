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

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/events/collision.md

import java.awt.event.KeyEvent;

import pi.Controller;
import pi.actor.Image;
import pi.event.FrameListener;
import pi.graphics.geom.Vector;

class Frog extends Image implements FrameListener
{
    private boolean jumpEnabled = true;

    private static final double MAX_SPEED = 4;

    public Frog()
    {
        super("Pixel-Adventure-1/Main Characters/Ninja Frog/Jump (32x32).png");
        pixelPerMeter(25);
        makeDynamic();
        rotationLocked(true);
    }

    public void jumpEnabled(boolean jumpEnabled)
    {
        this.jumpEnabled = jumpEnabled;
    }

    public void kill()
    {
        Controller.transitionToScene(new DeathScreen());
    }

    @Override
    public void onFrame(double pastTime)
    {
        // A: Die Blickrichtung des Frosches steuern
        flippedHorizontally(velocityX() < 0);
        // B: Horizontale Bewegung steuern
        if (Controller.isKeyPressed(KeyEvent.VK_A))
        {
            if (velocityX() > 0)
            {
                velocityX(0);
            }
            applyForce(Vector.LEFT.multiply(600));
        }
        else if (Controller.isKeyPressed(KeyEvent.VK_D))
        {
            if (velocityX() < 0)
            {
                velocityX(0);
            }
            applyForce(Vector.RIGHT.multiply(600));
        }
        if (Math.abs(velocityX()) > MAX_SPEED)
        {
            velocityX(MAX_SPEED * Math.signum(velocityX()));
        }
        // C: Wenn möglich den Frosch springen lassen
        if (isGrounded() && velocityY() <= 0 && jumpEnabled)
        {
            velocityY(0);
            applyImpulse(Vector.UP.multiply(180));
        }
    }
}
