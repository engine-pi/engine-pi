/*
 * Source: https://github.com/engine-alpha/tutorials/blob/master/src/eatutorials/statefulanimation/StatefulPlayerCharacter.java
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
package demos.docs.main_classes.actor.stateful_animation;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/docs/manual/main-classes/actor/stateful-animation.md

import java.awt.event.KeyEvent;

import pi.Controller;
import pi.actor.Animation;
import pi.actor.StatefulAnimation;
import pi.event.FrameUpdateListener;
import pi.event.KeyStrokeListener;
import pi.graphics.geom.Vector;

public class StatefulPlayerCharacter extends StatefulAnimation<PlayerState>
        implements KeyStrokeListener, FrameUpdateListener
{
    private static final double THRESHOLD = 0.01;

    private static final double RUNNING_THRESHOLD = 10;

    private static final double WALKING_THRESHOLD = 1;

    private static final Float MAX_SPEED = 20f;

    private static final double FORCE = 16000;

    private static final double JUMP_IMPULSE = 1100;

    public StatefulPlayerCharacter()
    {
        // Alle Bilder haben die Abmessung 64x64px und deshalb die gleiche
        // Breite
        // und Höhe. Wir verwenden drei Meter.
        super(3, 3);
        setupPlayerStates();
        setupAutomaticTransitions();
        setupPhysics();
    }

    private void setupPlayerStates()
    {
        for (PlayerState state : PlayerState.values())
        {
            Animation animationOfState = Animation
                .createFromAnimatedGif(state.getGifFileLocation(), 3, 3);
            addState(state, animationOfState);
        }
    }

    private void setupAutomaticTransitions()
    {
        stateTransition(PlayerState.MIDAIR, PlayerState.FALLING);
        stateTransition(PlayerState.LANDING, PlayerState.IDLE);
    }

    private void setupPhysics()
    {
        makeDynamic();
        rotationLocked(true);
        restitution(0);
        friction(30);
        linearDamping(.3);
    }

    @Override
    public void onKeyDown(KeyEvent keyEvent)
    {
        if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE)
        {
            attemptJump();
        }
    }

    private void attemptJump()
    {
        PlayerState state = state();
        if (state == PlayerState.IDLE || state == PlayerState.WALKING
                || state == PlayerState.RUNNING)
        {
            if (isGrounded())
            {
                applyImpulse(new Vector(0, JUMP_IMPULSE));
                state(PlayerState.JUMPING);
            }
        }
    }

    @Override
    public void onFrameUpdate(double dT)
    {
        Vector velocity = velocity();
        PlayerState state = state();
        if (velocity.y() < -THRESHOLD)
        {
            switch (state)
            {
            case JUMPING:
                state(PlayerState.MIDAIR);
                break;

            case IDLE:
            case WALKING:
            case RUNNING:
                state(PlayerState.FALLING);
                break;

            default:
                break;
            }
        }
        else if (velocity.y() < THRESHOLD && state == PlayerState.FALLING)
        {
            state(PlayerState.LANDING);
        }
        if (Math.abs(velocity.x()) > MAX_SPEED)
        {
            velocity(new Vector(Math.signum(velocity.x()) * MAX_SPEED,
                    velocity.y()));
        }
        if (Controller.isKeyPressed(KeyEvent.VK_A))
        {
            applyForce(new Vector(-FORCE, 0));
        }
        else if (Controller.isKeyPressed(KeyEvent.VK_D))
        {
            applyForce(new Vector(FORCE, 0));
        }
        if (state == PlayerState.IDLE || state == PlayerState.WALKING
                || state == PlayerState.RUNNING)
        {
            double velXTotal = Math.abs(velocity.x());
            if (velXTotal > RUNNING_THRESHOLD)
            {
                changeState(PlayerState.RUNNING);
            }
            else if (velXTotal > WALKING_THRESHOLD)
            {
                changeState(PlayerState.WALKING);
            }
            else
            {
                changeState(PlayerState.IDLE);
            }
        }
        if (velocity.x() > 0)
        {
            flipHorizontal(false);
        }
        else if (velocity.x() < 0)
        {
            flipHorizontal(true);
        }
    }
}
