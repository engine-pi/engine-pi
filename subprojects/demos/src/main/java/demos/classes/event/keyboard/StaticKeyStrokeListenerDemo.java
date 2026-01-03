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
import pi.Rectangle;
import pi.event.KeyStrokeListener;

public class StaticKeyStrokeListenerDemo extends Scene
{
    Rectangle rectangle;

    public StaticKeyStrokeListenerDemo()
    {
        rectangle = new Rectangle(2, 2);
        rectangle.color(Color.BLUE);
        add(rectangle);
    }

    public void moveLeft()
    {
        rectangle.moveBy(-1, 0);
    }

    public void moveRight()
    {
        rectangle.moveBy(1, 0);
    }

    public static void main(String[] args)
    {
        StaticKeyStrokeListenerDemo scene = new StaticKeyStrokeListenerDemo();
        Game.start(scene, 600, 400);
        Game.addKeyStrokeListener(new KeyStrokeListener()
        {
            public void onKeyDown(KeyEvent e)
            {
                switch (e.getKeyCode())
                {
                case KeyEvent.VK_LEFT:
                    scene.moveLeft();
                    break;

                case KeyEvent.VK_RIGHT:
                    scene.moveRight();
                    break;
                }
            }
        });
    }
}
