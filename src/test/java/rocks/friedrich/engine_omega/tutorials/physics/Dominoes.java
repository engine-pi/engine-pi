/*
 * Source: https://github.com/engine-alpha/tutorials/blob/master/src/eatutorials/physics/Dominoes.java
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
package rocks.friedrich.engine_omega.tutorials.physics;

import java.awt.Color;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.Vector;
import rocks.friedrich.engine_omega.actor.BodyType;
import rocks.friedrich.engine_omega.actor.Circle;
import rocks.friedrich.engine_omega.actor.Rectangle;
import rocks.friedrich.engine_omega.event.FrameUpdateListener;
import rocks.friedrich.engine_omega.event.MouseButton;
import rocks.friedrich.engine_omega.event.MouseClickListener;

public class Dominoes extends Scene
        implements FrameUpdateListener, MouseClickListener
{
    private Rectangle ground;

    private Rectangle wall;

    private Circle ball;

    private Rectangle angle;

    public Dominoes()
    {
        setupBasicObjects();
        setupPhysics();
        setupAngle();
        makeDominoes(20, 0.4, 3f);
    }

    private void setupBasicObjects()
    {
        ground = new Rectangle(200, 2);
        ground.setCenter(0, -5);
        ground.setColor(Color.WHITE);
        add(ground);
        ball = new Circle(0.5);
        ball.setColor(Color.RED);
        ball.setPosition(-10, -2);
        add(ball);
        wall = new Rectangle(1, 40);
        wall.setPosition(-14, -4);
        wall.setColor(Color.WHITE);
        add(wall);
    }

    private void setupAngle()
    {
        angle = new Rectangle(1, 0.25);
        angle.setColor(Color.GRAY);
        add(angle);
    }

    private void setupPhysics()
    {
        ground.setBodyType(BodyType.STATIC);
        wall.setBodyType(BodyType.STATIC);
        ball.setBodyType(BodyType.DYNAMIC);
        setGravity(new Vector(0, -9.81));
    }

    private void makeDominoes(int num, double beamWidth, double beamHeight)
    {
        for (int i = 0; i < num; i++)
        {
            Rectangle beam = new Rectangle(beamWidth, beamHeight);
            beam.setPosition(i * 3 * (beamWidth), -4);
            beam.setBodyType(BodyType.DYNAMIC);
            beam.setColor(Color.BLUE);
            add(beam);
        }
    }

    @Override
    public void onFrameUpdate(double deltaSeconds)
    {
        Vector mousePosition = getMousePosition();
        Vector ballCenter = ball.getCenter();
        Vector distance = ballCenter.getDistance(mousePosition);
        angle.setPosition(ball.getCenter());
        angle.setSize(distance.getLength(), 0.25);
        double rot = Vector.RIGHT.getAngle(distance);
        angle.setRotation(rot);
    }

    @Override
    public void onMouseDown(Vector position, MouseButton button)
    {
        Vector impulse = ball.getCenter().getDistance(position).multiply(5);
        ball.applyImpulse(impulse);
    }

    public static void main(String[] args)
    {
        Dominoes dominoes = new Dominoes();
        Game.start(800, 300, dominoes);
    }
}
