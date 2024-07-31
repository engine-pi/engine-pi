/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha-examples/src/main/java/ea/example/showcase/JointDemo.java
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
package de.pirckheimer_gymnasium.engine_pi_demos;

import java.awt.Color;
import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.actor.BodyType;
import de.pirckheimer_gymnasium.engine_pi.actor.Circle;
import de.pirckheimer_gymnasium.engine_pi.actor.Polygon;
import de.pirckheimer_gymnasium.engine_pi.actor.Rectangle;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;

/**
 * Demonstriert die <b>Verbindungen</b> (Joints) in der Engine.
 *
 * @version 12.04.2017
 *
 * @author Michael Andonie
 */
public class JointDemo extends ForceKlickEnvironment
        implements KeyStrokeListener
{
    private boolean isGravityActive = false;

    private Rectangle seesaw;

    private Rectangle[] chain;

    private Circle ball;

    public JointDemo()
    {
        initialize2();
    }

    public void initialize2()
    {
        buildChain(15);
        buildLeash();
        buildHoverHolder();
        ball = new Circle(1);
        add(ball);
        ball.setColor(Color.BLUE);
        ball.setPosition(new Vector(300, 200));
        ball.makeDynamic();
    }

    private void buildHoverHolder()
    {
        final int FACT = 2;
        Polygon halter = new Polygon(new Vector(0, 50 * FACT),
                new Vector(25 * FACT, 75 * FACT),
                new Vector(50 * FACT, 75 * FACT),
                new Vector(75 * FACT, 50 * FACT),
                new Vector(75 * FACT, 100 * FACT), new Vector(0, 100 * FACT));
        add(halter);
        halter.setColor("blue green");
        halter.makeDynamic();
        Rectangle item = new Rectangle(35 * FACT, 20 * FACT);
        add(item);
        item.setPosition(30 * FACT, 0);
        item.setColor("red");
        item.makeDynamic();
        halter.createDistanceJoint(item, halter.getCenter(), item.getCenter());
    }

    /**
     * Eine Leine bauen
     */
    private void buildLeash()
    {
        Circle kx = new Circle(30);
        kx.setColor("blue");
        kx.makeDynamic();
        Circle ky = new Circle(50);
        ky.setPosition(50, 0);
        ky.setColor("green");
        ky.makeDynamic();
        kx.createRopeJoint(ky, new Vector(15, 15), new Vector(25, 25), 4);
    }

    /**
     * Baut eine Kette
     *
     * @param length Die Anzahl der Kettenglieder.
     */
    private void buildChain(int length)
    {
        chain = new Rectangle[length];
        for (int i = 0; i < chain.length; i++)
        {
            chain[i] = new Rectangle(50, 10);
            Vector posrel = new Vector(45 * i, 30);
            chain[i].moveBy(posrel);
            chain[i].setColor("green");
            chain[i].setBodyType(i == 0 ? BodyType.STATIC : BodyType.DYNAMIC);
            if (i != 0)
            {
                chain[i - 1].createRevoluteJoint(chain[i],
                        new Vector(0, 5).add(posrel));
            }
        }
        add(chain);
        Circle weight = new Circle(100);
        weight.setColor(Color.WHITE);
        weight.makeDynamic();
        Vector vektor = new Vector(45 * chain.length, 35);
        weight.setCenter(new Vector(vektor.getX(), vektor.getY()));
        weight.createRevoluteJoint(chain[chain.length - 1], vektor);
        add(weight);
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_S)
        {
            isGravityActive = !isGravityActive;
            setGravity(isGravityActive ? new Vector(0, -10) : Vector.NULL);
        }
    }

    public static void main(String[] args)
    {
        Game.start(new JointDemo(), 1000, 800);
    }
}
