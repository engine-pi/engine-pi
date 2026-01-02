/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha-examples/src/main/java/ea/example/showcase/billard/BillardDemo.java
 *
 * Engine Alpha ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2018 Michael Andonie and contributors.
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
package demos.billard;

import java.awt.Color;
import java.awt.event.KeyEvent;

import pi.Game;
import pi.Random;
import pi.Scene;
import pi.Vector;
import pi.event.KeyStrokeListener;

public class BillardDemo extends Scene implements KeyStrokeListener
{
    private static final int WIDTH = 1240;

    private static final int HEIGHT = 812;

    private final Ball whiteBall;

    public BillardDemo()
    {
        Table table = new Table();
        add(table.getActors());
        for (int i = 0; i < 10; i++)
        {
            Ball ball = new Ball();
            ball.setPosition(calculatePosition(i));
            add(ball);
        }
        whiteBall = new Ball();
        whiteBall.setColor(Color.WHITE);
        whiteBall.setPosition(-200, 0);
        add(whiteBall);
        setFocus(table.getBorder());
        getCamera().meter(1);
    }

    private Vector calculatePosition(int i)
    {
        switch (i)
        {
        case 0:
            return new Vector(0, 0);

        case 1:
            return new Vector(Ball.DIAMETER, +Ball.DIAMETER / 2);

        case 2:
            return new Vector(Ball.DIAMETER, -Ball.DIAMETER / 2);

        case 3:
            return new Vector(Ball.DIAMETER * 2, +Ball.DIAMETER);

        case 4:
            return new Vector(Ball.DIAMETER * 2, 0);

        case 5:
            return new Vector(Ball.DIAMETER * 2, -Ball.DIAMETER);

        case 6:
            return new Vector(Ball.DIAMETER * 3, +Ball.DIAMETER);

        case 7:
            return new Vector(Ball.DIAMETER * 3, +Ball.DIAMETER / 2);

        case 8:
            return new Vector(Ball.DIAMETER * 3, -Ball.DIAMETER / 2);

        case 9:
            return new Vector(Ball.DIAMETER * 3, -Ball.DIAMETER);

        default:
            throw new IllegalArgumentException("Invalid index");
        }
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            whiteBall.applyImpulse(
                    new Vector(1000000000, (Random.range() - .5) * 2));
        }
    }

    public static void main(String[] args)
    {
        Game.start(new BillardDemo(), WIDTH, HEIGHT);
    }
}
