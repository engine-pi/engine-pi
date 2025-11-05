/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2024 Josef Friedrich and contributors.
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
package de.pirckheimer_gymnasium.demos.physics.single_aspects;

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Rectangle;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;

/**
 * Demonstriert die Methode
 * {@link de.pirckheimer_gymnasium.engine_pi.actor.Actor#applyForce(double,double)}
 */
public class ApplyForceDemo extends Scene implements KeyStrokeListener
{
    private final Rectangle rectangle;

    public ApplyForceDemo()
    {
        rectangle = new Rectangle(3, 3);
        rectangle.makeDynamic();
        setGravity(0, -1);
        add(rectangle);
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
        case KeyEvent.VK_UP -> rectangle.applyForce(0, 5000);
        case KeyEvent.VK_DOWN -> rectangle.applyForce(0, -5000);
        case KeyEvent.VK_RIGHT -> rectangle.applyForce(5000, 0);
        case KeyEvent.VK_LEFT -> rectangle.applyForce(-5000, 0);
        }
    }

    public static void main(String[] args)
    {
        Game.start(new ApplyForceDemo());
    }
}
