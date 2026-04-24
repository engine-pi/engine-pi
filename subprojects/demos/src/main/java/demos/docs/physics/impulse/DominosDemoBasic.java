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
import pi.actor.Rectangle;

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/physics/impulse.md

/**
 * Die Basis-Figuren werden in die Szene platziert.
 */
@SuppressWarnings("squid:S1450")
public class DominosDemoBasic extends Scene
{
    /**
     * Der Boden, auf dem die Dominosteine stehen.
     */
    private Rectangle ground;

    /**
     * Der Ball, der die Dominosteine umwerfen soll.
     */
    private Circle ball;

    public DominosDemoBasic()
    {
        setupBasicObjects();
        makeDominoes();
    }

    private void setupBasicObjects()
    {
        ground = new Rectangle(200, 2);
        ground.center(0, -5);
        ground.color("blue");
        ground.friction(1);
        add(ground);

        ball = new Circle(0.5);
        ball.color("red");
        ball.anchor(-17, -2);
        add(ball);
    }

    private void makeDominoes()
    {
        for (int i = 0; i < 20; i++)
        {
            Rectangle domino = new Rectangle(0.2, 3);
            domino.anchor(-10 + i * 1.3, -4);
            domino.color("white");
            add(domino);
        }
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new DominosDemoBasic(), 1200, 300);
    }
}
