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
package demos;

import java.awt.Color;
import java.awt.event.KeyEvent;

import pi.Controller;
import pi.Scene;
import pi.Vector;
import pi.Image;
import pi.Rectangle;
import pi.physics.FixtureBuilder;

public class BasketballThrowDemo extends Scene
{
    private static final int WIDTH = 1240;

    private static final int HEIGHT = 812;

    private final Ball ball;

    private final Rectangle basket;

    public BasketballThrowDemo()
    {
        gravityOfEarth();
        camera().meter(100);
        Vector ballPosition = new Vector(-1.7, 0.5);
        layer().add(ball = new Ball(ballPosition.x(), ballPosition.y()));
        layer().add(new Wall(-6, -4.5, 12, 1));
        layer().add(new Wall(-6, 3.5, 12, 1));
        layer().add(new Wall(-6.5, -4.5, 1, 9));
        layer().add(new Wall(5.5, -4.5, 1, 9));
        BallShadow ballShadow = new BallShadow(ballPosition.x(),
                ballPosition.y());
        ball.createDistanceJoint(ballShadow, new Vector(.15, .15),
                new Vector(.15, .15));
        layer().add(ballShadow);
        basket = new Rectangle(1.5, 0.05);
        basket.color("red");
        basket.position(3, 0.5);
        basket.makeSensor();
        basket.gravityScale(0);
        basket.addCollisionListener(ball,
                event -> defer(() -> basket.x(-basket.x())));
        layer().add(basket);
        addKeyStrokeListener(e -> {
            if (e.getKeyCode() == KeyEvent.VK_SPACE)
            {
                ball.velocity(new Vector(Math.signum(basket.x()) * 2, 6));
            }
        });
    }

    private static class Ball extends Image
    {
        public Ball(double x, double y)
        {
            super("shots/ball.png", 0.3, 0.3);
            position(x + .15, y + .15);
            fixture(() -> FixtureBuilder.circle(0.15, 0.15, 0.15));
            makeDynamic();
            restitution(0.85);
            friction(0.1);
        }
    }

    private static class BallShadow extends Image
    {
        public BallShadow(double x, double y)
        {
            super("shots/shadow.png", 0.3, 0.3);
            position(x + .15, y + .15);
            fixture(() -> FixtureBuilder.circle(0.15, 0.15, 0.15));
            makeSensor();
            gravityScale(0);
            rotationLocked(true);
        }
    }

    private static class Wall extends Rectangle
    {
        public Wall(double x, double y, double width, double height)
        {
            super(width, height);
            position(x, y);
            color(Color.WHITE);
            makeStatic();
            friction(.05);
            restitution(.3);
            density(150);
        }
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new BasketballThrowDemo(), WIDTH, HEIGHT);
    }
}
