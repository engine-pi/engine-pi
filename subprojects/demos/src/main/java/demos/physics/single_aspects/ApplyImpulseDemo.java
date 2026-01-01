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
package demos.physics.single_aspects;

import java.awt.event.KeyEvent;

import pi.Controller;
import pi.Rectangle;
import pi.Scene;
import pi.event.KeyStrokeListener;

/**
 * Demonstriert die Methode {@link pi.actor.Actor#applyImpulse(double,double)}
 */
public class ApplyImpulseDemo extends Scene implements KeyStrokeListener
{
    private final Rectangle rectangle;

    public ApplyImpulseDemo()
    {
        rectangle = new Rectangle(3, 3);
        rectangle.makeDynamic();
        add(rectangle);
        setGravity(0, -1);
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
        case KeyEvent.VK_UP -> rectangle.applyImpulse(0, 100);
        case KeyEvent.VK_DOWN -> rectangle.applyImpulse(0, -100);
        case KeyEvent.VK_RIGHT -> rectangle.applyImpulse(100, 0);
        case KeyEvent.VK_LEFT -> rectangle.applyImpulse(-100, 0);
        }
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new ApplyImpulseDemo());
    }
}
