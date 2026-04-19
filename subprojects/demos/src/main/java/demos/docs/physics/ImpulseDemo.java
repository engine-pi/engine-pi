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
package demos.docs.physics;

import pi.Controller;
import pi.Scene;
import pi.actor.Circle;
import pi.actor.Line;
import pi.actor.Rectangle;
import pi.event.FrameListener;
import pi.event.MouseButton;
import pi.event.MouseClickListener;
import pi.graphics.geom.Vector;

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/physics/impulse.md

public class ImpulseDemo extends Scene
        implements FrameListener, MouseClickListener
{
    /**
     * Der Boden auf dem die Dominosteine stehen.
     */
    private Rectangle ground;

    /**
     * Der Ball, der die Dominosteine umwerfen soll.
     */
    private Circle ball;

    /**
     * Zeigt den Impuls an, mit dem der Ball gegen die Dominosteine katapultiert
     * wird.
     */
    private Line impulseLine;

    public ImpulseDemo()
    {
        setupBasicObjects();
        setupPhysics();
        setupAngle();
        makeDominoes();
    }

    private void setupBasicObjects()
    {
        ground = (Rectangle) new Rectangle(200, 2).center(0, -5)
            .color("white")
            .friction(0);
        add(ground);

        ball = (Circle) new Circle(0.5).color("red").anchor(-17, -2);
        add(ball);
    }

    private void setupAngle()
    {
        impulseLine = new Line();
        impulseLine.end2.arrow(true);
        impulseLine.strokeWidth(0.05);
        impulseLine.dashed();
        impulseLine.dashPattern(0.1);
        impulseLine.color("gray");
        impulseLine.makeSensor();
        impulseLine.gravityScale(0);
        add(impulseLine);
    }

    private void setupPhysics()
    {
        ground.makeStatic();
        ball.makeDynamic();
        gravityOfEarth();
    }

    private void makeDominoes()
    {
        for (int i = 0; i < 20; i++)
        {
            Rectangle domino = new Rectangle(0.2, 3);
            domino.anchor(-10 + i * 1.3, -4);
            domino.makeDynamic();
            domino.color("blue");
            add(domino);
        }
    }

    @Override
    public void onFrame(double pastTime)
    {
        impulseLine.end1(ball.center());
        impulseLine.end2(mousePosition());
    }

    @Override
    public void onMouseDown(Vector position, MouseButton button)
    {
        Vector impulse = ball.center().distance(position).multiply(5);
        ball.applyImpulse(impulse);
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new ImpulseDemo(), 1200, 300);
    }
}
