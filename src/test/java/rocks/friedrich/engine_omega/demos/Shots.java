/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha-examples/src/main/java/ea/example/showcase/shot/Shots.java
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
package rocks.friedrich.engine_omega.demos;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.Vector;
import rocks.friedrich.engine_omega.actor.BodyType;
import rocks.friedrich.engine_omega.actor.Image;
import rocks.friedrich.engine_omega.actor.Rectangle;
import rocks.friedrich.engine_omega.event.FrameUpdateListener;
import rocks.friedrich.engine_omega.physics.FixtureBuilder;

public class Shots extends ShowcaseDemo implements FrameUpdateListener
{
    private final Ball ball;

    private final Rectangle basket;

    private final List<Image> net = new ArrayList<>();

    public Shots(Scene parent)
    {
        super(parent);
        setGravity(new Vector(0, -9.81));
        getCamera().setZoom(100);
        Vector ballPosition = new Vector(-1.7, 0.5);
        getMainLayer()
                .add(ball = new Ball(ballPosition.getX(), ballPosition.getY()));
        getMainLayer().add(new Wall(-6, -4.5, 12, 1));
        getMainLayer().add(new Wall(-6, 3.5, 12, 1));
        getMainLayer().add(new Wall(-6.5, -4.5, 1, 9));
        getMainLayer().add(new Wall(5.5, -4.5, 1, 9));
        BallShadow ballShadow = new BallShadow(ballPosition.getX(),
                ballPosition.getY());
        ball.createDistanceJoint(ballShadow, new Vector(.15, .15),
                new Vector(.15, .15));
        getMainLayer().add(ballShadow);
        basket = new Rectangle(1.5, 0.05);
        basket.setColor(Color.RED);
        basket.setPosition(3, 0.5);
        basket.setBodyType(BodyType.SENSOR);
        basket.setGravityScale(0);
        basket.addCollisionListener(ball,
                event -> defer(() -> basket.setX(-basket.getX())));
        getMainLayer().add(basket);
        addKeyListener(e -> {
            if (e.getKeyCode() == KeyEvent.VK_SPACE)
            {
                ball.setVelocity(new Vector(Math.signum(basket.getX()) * 2, 6));
            }
        });
    }

    @Override
    public void onFrameUpdate(double deltaSeconds)
    {
    }

    private static class Ball extends Image
    {
        public Ball(double x, double y)
        {
            super("shots/ball.png", 0.3, 0.3);
            setPosition(x + .15, y + .15);
            setFixture(
                    () -> FixtureBuilder.circle(0.15, 0.15, 0.15));
            setBodyType(BodyType.DYNAMIC);
            setRestitution(0.85);
            setFriction(0.1);
        }
    }

    private static class BallShadow extends Image
    {
        public BallShadow(double x, double y)
        {
            super("shots/shadow.png", 0.3, 0.3);
            setPosition(x + .15, y + .15);
            setFixture(
                    () -> FixtureBuilder.circle(0.15, 0.15, 0.15));
            setBodyType(BodyType.SENSOR);
            setGravityScale(0);
            setRotationLocked(true);
        }
    }

    private static class Wall extends Rectangle
    {
        public Wall(double x, double y, double width, double height)
        {
            super(width, height);
            setPosition(x, y);
            setColor(Color.WHITE);
            setBodyType(BodyType.STATIC);
            setFriction(.05);
            setRestitution(.3);
            setDensity(150);
        }
    }

    public static void main(String[] args)
    {
        Game.start(Showcases.WIDTH, Showcases.HEIGHT, new Shots(null));
    }
}
