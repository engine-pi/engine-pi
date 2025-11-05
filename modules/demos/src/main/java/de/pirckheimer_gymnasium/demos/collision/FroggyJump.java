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
package de.pirckheimer_gymnasium.demos.collision;

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Camera;
import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Layer;
import de.pirckheimer_gymnasium.engine_pi.Random;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.actor.Image;
import de.pirckheimer_gymnasium.engine_pi.actor.Rectangle;
import de.pirckheimer_gymnasium.engine_pi.actor.Text;
import de.pirckheimer_gymnasium.engine_pi.event.CollisionEvent;
import de.pirckheimer_gymnasium.engine_pi.event.CollisionListener;
import de.pirckheimer_gymnasium.engine_pi.event.FrameUpdateListener;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;

public class FroggyJump extends Scene
{
    private Frog frog;

    public FroggyJump()
    {
        frog = new Frog();
        add(frog);
        setGravity(Vector.DOWN.multiply(10));
        Camera camera = getCamera();
        camera.setFocus(frog);
        camera.setOffset(new Vector(0, 4));
        makeLevel(40);
        makePlatforms(10);
    }

    private void makePlatforms(int heightLevel)
    {
        for (int i = 0; i < heightLevel; i++)
        {
            Platform platform = new Platform(5, 1);
            platform.setPosition(0, i * 4);
            add(platform);
        }
    }

    private void makeLevel(int heightLevel)
    {
        for (int i = 0; i < heightLevel; i++)
        {
            int numPlatforms = Random.range(2) + 1;
            for (int j = 0; j < numPlatforms; j++)
            {
                Platform platform = new Platform(6 / numPlatforms, 1);
                platform.setPosition(
                        numPlatforms * (j + 1) * i * Random.range(), i * 4);
                add(platform);
            }
            if (i > 3)
            {
                for (int j = 0; j < Random.range(3); j++)
                {
                    SpikeBall.setupSpikeBall(Random.range() * (4 + j) * i,
                            Random.range() * 4 + 0.5 + 5 * i, getMainLayer());
                }
            }
        }
    }
}

class DeathScreen extends Scene implements KeyStrokeListener
{
    public DeathScreen()
    {
        Text message = new Text("You Died. Press any button to try again", .6);
        message.setCenter(getCamera().getCenter());
        add(message);
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        Game.transitionToScene(new FroggyJump());
    }
}

class Frog extends Image implements FrameUpdateListener
{
    private boolean canJump = true;

    private static double MAX_SPEED = 4;

    public Frog()
    {
        super("froggy/Frog.png", 25);
        makeDynamic();
        setRotationLocked(true);
    }

    public void setJumpEnabled(boolean jumpEnabled)
    {
        this.canJump = jumpEnabled;
    }

    public void kill()
    {
        Game.transitionToScene(new DeathScreen());
    }

    @Override
    public void onFrameUpdate(double pastTime)
    {
        Vector velocity = this.getVelocity();
        // A: Die Blickrichtung des Frosches steuern
        if (velocity.getX() < 0)
        {
            setFlippedHorizontally(true);
        }
        else
        {
            setFlippedHorizontally(false);
        }
        // B: Horizontale Bewegung steuern
        if (Game.isKeyPressed(KeyEvent.VK_A))
        {
            if (velocity.getX() > 0)
            {
                setVelocity(new Vector(0, velocity.getY()));
            }
            applyForce(Vector.LEFT.multiply(600));
        }
        else if (Game.isKeyPressed(KeyEvent.VK_D))
        {
            if (velocity.getX() < 0)
            {
                setVelocity(new Vector(0, velocity.getY()));
            }
            applyForce(Vector.RIGHT.multiply(600));
        }
        if (Math.abs(velocity.getX()) > MAX_SPEED)
        {
            setVelocity(new Vector(MAX_SPEED * Math.signum(velocity.getX()),
                    velocity.getY()));
        }
        // C: Wenn möglich den Frosch springen lassen
        if (isGrounded() && velocity.getY() <= 0 && canJump)
        {
            setVelocity(new Vector(velocity.getX(), 0));
            applyImpulse(Vector.UP.multiply(180));
        }
    }
}

class Platform extends Rectangle implements CollisionListener<Frog>
{
    public Platform(double width, double height)
    {
        super(width, height);
        makeStatic();
        addCollisionListener(Frog.class, this);
    }

    @Override
    public void onCollision(CollisionEvent<Frog> collisionEvent)
    {
        double frogY = collisionEvent.getColliding().getPosition().getY();
        if (frogY < getY())
        {
            collisionEvent.ignoreCollision();
            collisionEvent.getColliding().setJumpEnabled(false);
        }
    }

    @Override
    public void onCollisionEnd(CollisionEvent<Frog> collisionEvent)
    {
        collisionEvent.getColliding().setJumpEnabled(true);
    }
}

class SpikeBall extends Image implements CollisionListener<Frog>
{
    private static class SpikeSensor extends Rectangle
            implements CollisionListener<Frog>
    {
        private SpikeBall ball;

        public SpikeSensor(SpikeBall ball)
        {
            super(2, 8);
            this.ball = ball;
            setVisible(false);
            makeSensor();
            addCollisionListener(Frog.class, this);
            setGravityScale(0);
        }

        @Override
        public void onCollision(CollisionEvent<Frog> collisionEvent)
        {
            ball.setGravityScale(1);
        }
    }

    public SpikeBall()
    {
        super("froggy/Spiked-Ball.png", 40);
        setGravityScale(0);
        addCollisionListener(Frog.class, this);
    }

    public static SpikeBall setupSpikeBall(double x, double y, Layer layer)
    {
        SpikeBall ball = new SpikeBall();
        ball.setCenter(x, y);
        SpikeSensor sensor = new SpikeSensor(ball);
        sensor.setPosition(x - 1, y - 8);
        layer.add(ball, sensor);
        return ball;
    }

    @Override
    public void onCollision(CollisionEvent<Frog> collisionEvent)
    {
        collisionEvent.getColliding().kill();
    }

    public static void main(String[] args)
    {
        Game.start(new FroggyJump(), 400, 600);
    }
}
