/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha-examples/src/main/java/ea/example/showcase/dude/PlayerCharacter.java
 *
 * Engine Alpha ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2019 Michael Andonie and contributors.
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
package demos.small_games.dude;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.HashSet;

import pi.Controller;
import pi.Random;
import pi.actor.Actor;
import pi.actor.Animation;
import pi.Circle;
import pi.actor.StatefulAnimation;
import pi.animation.Interpolator;
import pi.animation.ValueAnimator;
import pi.animation.interpolation.SinusDouble;
import pi.event.CollisionEvent;
import pi.event.CollisionListener;
import pi.event.FrameUpdateListener;
import pi.event.KeyStrokeListener;
import pi.graphics.geom.Vector;

public class PlayerCharacter extends StatefulAnimation<PlayerState> implements
        CollisionListener<Actor>, FrameUpdateListener, KeyStrokeListener
{
    private static final double MAX_SPEED = 100;

    public static final int JUMP_FORCE = +150;

    public static final int SMASH_FORCE = -1500;

    public static final int BOTTOM_OUT = -500 / 30;

    private static final int DOUBLE_JUMP_COST = 3;

    private static final int MANA_PICKUP_BONUS = 50;

    private static final int ROCKETCOST_PER_FRAME = 5;

    private static final boolean GOD_MODE = true;

    private static final double FRICTION = 0.5;

    private static final double RESTITUTION = 0;
    /*
     * private final Sound walk = new Sound("dude/audio/footstep.wav"); private
     * final Sound jump = new Sound("dude/audio/footstep.wav"); private final
     * Sound pickup_yellowOrange = new
     * Sound("dude/audio/pickup_yellowOrange.wav");
     */

    private boolean didDoubleJump = false;

    private boolean rocketMode = false;

    private final GameData gameData;

    private final Collection<Platform> ignoredPlatformForCollision = new HashSet<>();

    /**
     * Beschreibt die drei Zustände, die ein Character bezüglich seiner
     * horizontalen Bewegung haben kann.
     */
    private enum HorizontalMovement
    {
        LEFT(-MAX_SPEED), RIGHT(MAX_SPEED), IDLE(0);

        private final double targetVelocityX;

        HorizontalMovement(double targetVelocityX)
        {
            this.targetVelocityX = targetVelocityX;
        }

        public double getTargetXVelocity()
        {
            return targetVelocityX;
        }
    }

    private HorizontalMovement horizontalMovement = HorizontalMovement.IDLE;

    private Vector smashForce = Vector.NULL;

    public PlayerCharacter(GameData gameData)
    {
        super(1, 1);
        this.gameData = gameData;
        // Alle einzuladenden Dateien teilen den Großteil des Paths (Ordner
        // sowie gemeinsame Dateipräfixe)
        String basePath = "dude/char/spr_m_traveler_";
        addState(PlayerState.Idle, Animation
                .createFromAnimatedGif(basePath + "idle_anim.gif", 1, 1));
        addState(PlayerState.Walking, Animation
                .createFromAnimatedGif(basePath + "walk_anim.gif", 1, 1));
        addState(PlayerState.Running, Animation
                .createFromAnimatedGif(basePath + "run_anim.gif", 1, 1));
        addState(PlayerState.JumpingUp, Animation
                .createFromAnimatedGif(basePath + "jump_1up_anim.gif", 1, 1));
        addState(PlayerState.Midair, Animation.createFromAnimatedGif(
                basePath + "jump_2midair_anim.gif", 1, 1));
        addState(PlayerState.Falling, Animation
                .createFromAnimatedGif(basePath + "jump_3down_anim.gif", 1, 1));
        addState(PlayerState.Landing, Animation
                .createFromAnimatedGif(basePath + "jump_4land_anim.gif", 1, 1));
        addState(PlayerState.Smashing, Animation
                .createFromAnimatedGif(basePath + "jump_4land_anim.gif", 1, 1));
        stateTransition(PlayerState.Midair, PlayerState.Falling);
        stateTransition(PlayerState.Landing, PlayerState.Idle);
        friction(FRICTION);
        restitution(RESTITUTION);
        fixtures("C0.5,0.3,0.3&C0.5,0.6,0.3");
        /*
         * setFixtures(() -> { List<Shape> shapeList = new ArrayList<>(2);
         * shapeList.add(FixtureBuilder.createAxisParallelRectangularShape(0.2,
         * 0, 0.6, 1f)); shapeList.add(FixtureBuilder.createCircleShape(.3, .3,
         * 0.3)); return shapeList; });
         */
        // setMass(MASS);
        addCollisionListener(this);
    }

    /**
     * Wird ausgeführt, wenn ein Sprungbefehl (W) angekommen ist.
     */
    public void tryJumping()
    {
        if (isGrounded())
        {
            applyImpulse(new Vector(0, JUMP_FORCE));
            state(PlayerState.JumpingUp);
        }
        else if (!didDoubleJump && gameData.getMana() >= DOUBLE_JUMP_COST
                && !state().equals("smashing"))
        {
            // Double Jump!
            didDoubleJump = true;
            gameData.consumeMana(DOUBLE_JUMP_COST);
            velocity(new Vector(velocity().x(), 0));
            applyImpulse(new Vector(0, JUMP_FORCE * 0.8));
            state(PlayerState.JumpingUp);
        }
    }

    public void setHorizontalMovement(HorizontalMovement state)
    {
        switch (state)
        {
        case LEFT:
            flipHorizontal(true);
            break;

        case RIGHT:
            flipHorizontal(false);
            break;

        default:
            // do nothing
        }
        this.horizontalMovement = state;
    }

    public HorizontalMovement getHorizontalMovement()
    {
        return this.horizontalMovement;
    }

    /**
     * Diese Methode wird aufgerufen, wenn der Character ein Item berührt hat.
     */
    public void gotItem(Item item)
    {
        switch (item)
        {
        case Coin:
            gameData.addMoney(1);
            break;

        case ManaPickup:
            gameData.addMana(MANA_PICKUP_BONUS);
            break;
        }
    }

    public void smash()
    {
        PlayerState currentState = state();
        if (currentState == PlayerState.Falling
                || currentState == PlayerState.JumpingUp
                || currentState == PlayerState.Midair)
        {
            state(PlayerState.Smashing);
            smashForce = new Vector(0, SMASH_FORCE);
        }
    }

    @Override
    public void onFrameUpdate(double pastTime)
    {
        Vector velocity = velocity();
        gameData.setPlayerVelocity(velocity.length());
        // kümmere dich um die horizontale Bewegung
        double desiredVelocity = horizontalMovement.getTargetXVelocity();
        double impulse;
        if (desiredVelocity == 0)
        {
            impulse = 0;
            velocity(new Vector(velocity.x() * 0.95, velocity.y()));
        }
        else
        {
            impulse = (desiredVelocity - velocity.x()) * 4;
            applyForce(new Vector(impulse, 0));
        }
        if (rocketMode && (gameData.getMana() > 0 || GOD_MODE))
        {
            gameData.consumeMana(ROCKETCOST_PER_FRAME);
            applyImpulse(new Vector(0, 5));
            Circle particle = new Circle(0.1);
            particle.position(
                    center().subtract(new Vector(Math.random() * 0.1, .45)));
            particle.color(Color.RED);
            particle.layerPosition(-1);
            particle.animateParticle(.5);
            particle.animateColor(.25, Color.YELLOW);
            particle.applyImpulse(
                    new Vector(0.005 * -impulse + (Math.random() - 0.5),
                            -2 * (Math.random())));
            particle.addCollisionListener((e) -> {
                if (e.colliding() instanceof Platform)
                {
                    Platform platform = (Platform) e.colliding();
                    if (ignoredPlatformForCollision.contains(platform))
                    {
                        e.ignoreCollision();
                    }
                }
            });
            layer().add(particle);
        }
        switch (state())
        {
        case JumpingUp:
            if (velocity.y() < 0)
            {
                state(PlayerState.Midair);
            }
            break;

        case Idle:
        case Running:
        case Walking:
            // if(standing) {
            didDoubleJump = false;
            if (velocity.y() > 0.1)
            {
                state(PlayerState.Midair);
            }
            else if (Math.abs(velocity.x()) > 5.5)
            {
                changeState(PlayerState.Running);
            }
            else if (Math.abs(velocity.x()) > .1)
            {
                changeState(PlayerState.Walking);
            }
            else
            {
                changeState(PlayerState.Idle);
            }
            // }
            break;

        default:
            // do nothing
        }
        applyForce(smashForce);
        if (y() < BOTTOM_OUT)
        {
            resetMovement();
            position(0, 0);
            state(PlayerState.Falling);
        }
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        /*
         * if (getLayer().getParent().isPhysicsPaused()) { //Pause --> Ignoriere
         * Key Input return; }
         */
        // TODO Pause handling
        switch (e.getKeyCode())
        {
        case KeyEvent.VK_A: // Move left
            if (horizontalMovement != PlayerCharacter.HorizontalMovement.RIGHT)
            {
                // Wir bewegen uns gerade NICHT schon nach rechts-> Dann auf
                // nach links
                setHorizontalMovement(PlayerCharacter.HorizontalMovement.LEFT);
            }
            break;

        case KeyEvent.VK_S:
            smash();
            break;

        case KeyEvent.VK_D:// Move right
            if (getHorizontalMovement() != PlayerCharacter.HorizontalMovement.LEFT)
            {
                // Wir bewegen uns gerade NICHT schon nach links → Dann auf nach
                // rechts
                setHorizontalMovement(PlayerCharacter.HorizontalMovement.RIGHT);
            }
            break;

        case KeyEvent.VK_SPACE:
        case KeyEvent.VK_W: // Sprungbefehl
            tryJumping();
            break;

        case KeyEvent.VK_SHIFT:
            rocketMode = true;
            break;
        }
    }

    @Override
    public void onKeyUp(KeyEvent e)
    {
        // TODO Add pause handling
        switch (e.getKeyCode())
        {
        case KeyEvent.VK_A: // Links losgelassen
            if (horizontalMovement == PlayerCharacter.HorizontalMovement.LEFT)
            {
                // Wir haben uns bisher nach links bewegt und das soll jetzt
                // aufhören
                if (Controller.isKeyPressed(KeyEvent.VK_D))
                {
                    // D ist auch gedrückt, wir wollen also ab jetzt nach rechts
                    setHorizontalMovement(
                            PlayerCharacter.HorizontalMovement.RIGHT);
                }
                else
                {
                    setHorizontalMovement(
                            PlayerCharacter.HorizontalMovement.IDLE);
                }
            }
            break;

        case KeyEvent.VK_D: // Rechts losgelassen
            if (getHorizontalMovement() == PlayerCharacter.HorizontalMovement.RIGHT)
            {
                // Wir haben uns bisher nach rechts bewegt und das soll jetzt
                // aufhören
                if (Controller.isKeyPressed(KeyEvent.VK_A))
                {
                    // A ist gedrückt, wir wollen also ab jetzt nach links
                    setHorizontalMovement(
                            PlayerCharacter.HorizontalMovement.LEFT);
                }
                else
                {
                    setHorizontalMovement(
                            PlayerCharacter.HorizontalMovement.IDLE);
                }
            }
            break;

        case KeyEvent.VK_SHIFT:
            rocketMode = false;
            break;
        }
    }

    @Override
    public void onCollision(CollisionEvent<Actor> collisionEvent)
    {
        if (collisionEvent.colliding() instanceof Platform)
        {
            Platform platform = (Platform) collisionEvent.colliding();
            if (velocity().y() > 0
                    || ignoredPlatformForCollision.contains(platform))
            {
                ignoredPlatformForCollision.add(platform);
                collisionEvent.ignoreCollision();
            }
        }
        boolean falling = state() == PlayerState.Falling;
        boolean smashing = state() == PlayerState.Smashing;
        if ((falling || smashing) && isGrounded())
        {
            state(PlayerState.Landing);
            smashForce = Vector.NULL;
            if (smashing)
            {
                Vector originalOffset = layer().scene().camera().offset();
                Interpolator<Double> interpolator = new SinusDouble(0,
                        -0.0004 * velocity().y());
                ValueAnimator<Double> valueAnimator = new ValueAnimator<>(.1,
                        y -> layer().scene().camera()
                                .offset(originalOffset.add(new Vector(0, y))),
                        interpolator, layer());
                layer().addFrameUpdateListener(valueAnimator);
                valueAnimator.addCompletionListener(value -> layer()
                        .frameUpdateListeners().remove(valueAnimator));
            }
            Vector speed = physicsHandler().velocity();
            Vector transformedSpeed = Math.abs(speed.x()) < .1
                    ? speed.add(100 * (Math.random() - .5), 0)
                    : speed;
            for (int i = 0; i < 100; i++)
            {
                Circle particle = new Circle(Random.range() * .02 + .02);
                particle.position(center().add(0, -32));
                particle.applyImpulse(
                        transformedSpeed.negate().multiply(Math.random() * 0.1)
                                .multiplyY(Math.random() * 0.1));
                particle.color(Color.GRAY);
                particle.layerPosition(-1);
                particle.animateParticle(.5);
                layer().add(particle);
            }
        }
    }

    @Override
    public void onCollisionEnd(CollisionEvent<Actor> collisionEvent)
    {
        if (collisionEvent.colliding() instanceof Platform)
        {
            Platform platform = (Platform) collisionEvent.colliding();
            ignoredPlatformForCollision.remove(platform);
        }
    }
}
