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
package demos.docs.physics.impulse;

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

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/jbox2d/testbed/src/main/java/org/jbox2d/testbed/tests/DominoTest.java
// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/jbox2d/testbed/src/main/java/org/jbox2d/testbed/tests/DominoTower.java

// https://de.wikipedia.org/wiki/Domino: Professionelle Spielsteine
// haben im Allgemeinen eine Größe von ca. 56 × 28 × 13 mm.

/**
 * Das fertig Dominos-Demo.
 */
public class DominosDemoFinal extends Scene
        implements FrameListener, MouseClickListener
{
    /**
     * Der Boden, auf dem die Dominosteine stehen.
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
    private Line line;

    public DominosDemoFinal()
    {
        setupBasicObjects();
        makeDominoes();
        setupPhysics();
        setupLine();
    }

    private void setupBasicObjects()
    {
        ground = new Rectangle(20, 0.2);
        ground.center(0, -0.5);
        ground.color("blue");
        ground.friction(1);
        add(ground);

        ball = new Circle(0.15);
        ball.color("red");
        ball.anchor(-3, -0.2);
        add(ball);
    }

    private void makeDominoes()
    {
        for (int i = 0; i < 10; i++)
        {
            Rectangle domino = new Rectangle(0.13, 0.56);
            domino.anchor(-1 + i * 0.5, -0.4);
            domino.color("white");
            domino.makeDynamic();
            domino.friction(1);
            domino.density(5);
            add(domino);
        }
    }

    // -->
    private void setupPhysics()
    {
        ground.makeStatic();
        ball.makeDynamic();
        gravityOfEarth();
    }
    // <--

    // -->
    private void setupLine()
    {
        line = new Line();
        line.end2.arrow(true).arrowSideLength(0.1);
        line.strokeWidth(0.01);
        line.dashed();
        line.dashPattern(0.03);
        line.color("gray");
        line.makeSensor();
        line.gravityScale(0);
        add(line);
    }
    // <--

    // -->
    @Override
    public void onFrame(double pastTime)
    {
        line.end1(ball.center());
        line.end2(mousePosition());
    }
    // <--

    // -->
    @Override
    public void onMouseDown(Vector position, MouseButton button)
    {
        Vector impulse = ball.center().distance(position);
        ball.applyImpulse(impulse);
    }
    // <--

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.config.graphics.pixelPerMeter(128);
        Controller.start(new DominosDemoFinal(), 1200, 300);
    }
}
