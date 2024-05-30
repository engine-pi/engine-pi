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
package rocks.friedrich.engine_omega.tutorials.statefulanimation;

import java.awt.event.KeyEvent;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Vector;
import rocks.friedrich.engine_omega.actor.Animation;
import rocks.friedrich.engine_omega.actor.BodyType;
import rocks.friedrich.engine_omega.actor.StatefulAnimation;
import rocks.friedrich.engine_omega.animation.KeyFrames;
import rocks.friedrich.engine_omega.event.FrameUpdateListener;
import rocks.friedrich.engine_omega.event.KeyListener;

public class StatefulPlayerCharacter extends StatefulAnimation<PlayerState>
        implements KeyListener, FrameUpdateListener
{
    private static final double THRESHOLD = 0.01;

    private static final double RUNNING_THRESHOLD = 10;

    private static final double WALKING_THRESHOLD = 1;

    private static final Float MAX_SPEED = 20f;

    private static final double FORCE = 16000;

    private static final double JUMP_IMPULSE = 1100;

    private KeyFrames currentMovement = null;

    public StatefulPlayerCharacter()
    {
        super(3, 3); // All GIFs are 64x64 px, hence: Same width/height. In this
                     // case: 3m each
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
        setStateTransition(PlayerState.MIDAIR, PlayerState.FALLING);
        setStateTransition(PlayerState.LANDING, PlayerState.IDLE);
    }

    private void setupPhysics()
    {
        setBodyType(BodyType.DYNAMIC);
        setRotationLocked(true);
        setRestitution(0);
        setFriction(30f);
        setLinearDamping(.3);
    }

    @Override
    public void onKeyDown(KeyEvent keyEvent)
    {
        switch (keyEvent.getKeyCode())
        {
        case KeyEvent.VK_SPACE:
            attemptJump();
            break;
        }
    }

    private void attemptJump()
    {
        PlayerState state = getCurrentState();
        if (state == PlayerState.IDLE || state == PlayerState.WALKING
                || state == PlayerState.RUNNING)
        {
            if (isGrounded())
            {
                applyImpulse(new Vector(0, JUMP_IMPULSE));
                setState(PlayerState.JUMPING);
            }
        }
    }

    @Override
    public void onFrameUpdate(double dT)
    {
        Vector velocity = getVelocity();
        PlayerState state = getCurrentState();
        if (velocity.getY() < -THRESHOLD)
        {
            switch (state)
            {
            case JUMPING:
                setState(PlayerState.MIDAIR);
                break;

            case IDLE:
            case WALKING:
            case RUNNING:
                setState(PlayerState.FALLING);
                break;

            default:
                break;
            }
        }
        else if (velocity.getY() < THRESHOLD && state == PlayerState.FALLING)
        {
            setState(PlayerState.LANDING);
        }
        if (Math.abs(velocity.getX()) > MAX_SPEED)
        {
            setVelocity(new Vector(Math.signum(velocity.getX()) * MAX_SPEED,
                    velocity.getY()));
        }
        if (Game.isKeyPressed(KeyEvent.VK_A))
        {
            applyForce(new Vector(-FORCE, 0));
        }
        else if (Game.isKeyPressed(KeyEvent.VK_D))
        {
            applyForce(new Vector(FORCE, 0));
        }
        if (state == PlayerState.IDLE || state == PlayerState.WALKING
                || state == PlayerState.RUNNING)
        {
            double velXTotal = Math.abs(velocity.getX());
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
        if (velocity.getX() > 0)
        {
            setFlipHorizontal(false);
        }
        else if (velocity.getX() < 0)
        {
            setFlipHorizontal(true);
        }
    }
}
