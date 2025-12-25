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
package demos.classes.event.keyboard;

import java.awt.Color;
import java.awt.event.KeyEvent;

import pi.Game;
import pi.Scene;
import pi.actor.Circle;
import pi.actor.Rectangle;
import pi.event.KeyStrokeListener;

public class ListenerOnActorsDemo extends Scene
{
    public ListenerOnActorsDemo()
    {
        Rectangle rectangle = new Rectangle(2, 2);
        rectangle.setColor(Color.BLUE);
        rectangle.setPosition(-3, 0);
        rectangle.addKeyStrokeListener(new KeyStrokeListener()
        {
            @Override
            public void onKeyDown(KeyEvent e)
            {
                switch (e.getKeyCode())
                {
                case KeyEvent.VK_UP:
                    rectangle.moveBy(0, 1);
                    break;

                case KeyEvent.VK_RIGHT:
                    rectangle.moveBy(1, 0);
                    break;

                case KeyEvent.VK_DOWN:
                    rectangle.moveBy(0, -1);
                    break;

                case KeyEvent.VK_LEFT:
                    rectangle.moveBy(-1, 0);
                    break;
                }
            }
        });
        add(rectangle);
        // Ein zweiter Actor
        Circle circle = new Circle(2);
        circle.setPosition(3, 0);
        circle.setColor(Color.RED);
        // Als Lambda-Ausdruck
        circle.addKeyStrokeListener(e -> {
            switch (e.getKeyCode())
            {
            case KeyEvent.VK_W:
                circle.moveBy(0, 1);
                break;

            case KeyEvent.VK_D:
                circle.moveBy(1, 0);
                break;

            case KeyEvent.VK_S:
                circle.moveBy(0, -1);
                break;

            case KeyEvent.VK_A:
                circle.moveBy(-1, 0);
                break;
            }
        });
        add(circle);
    }

    public static void main(String[] args)
    {
        Game.start(new ListenerOnActorsDemo(), 600, 400);
    }
}
