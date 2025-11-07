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
package de.pirckheimer_gymnasium.demos.classes.event.keyboard;

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Circle;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;

public class KeyStrokeListenerAsNormalClassDemo extends Scene
{
    Circle circle;

    KeyStrokeListener keyStrokeListener;

    class MyKeyStrokeListener implements KeyStrokeListener
    {
        @Override
        public void onKeyDown(KeyEvent e)
        {
            switch (e.getKeyCode())
            {
            case KeyEvent.VK_UP:
                circle.moveBy(0, 1);
                break;

            case KeyEvent.VK_RIGHT:
                circle.moveBy(1, 0);
                break;

            case KeyEvent.VK_DOWN:
                circle.moveBy(0, -1);
                break;

            case KeyEvent.VK_LEFT:
                circle.moveBy(-1, 0);
                break;

            case KeyEvent.VK_X:
                circle.removeKeyStrokeListener(keyStrokeListener);
                break;

            case KeyEvent.VK_L:
                circle.addKeyStrokeListener(keyStrokeListener);
                break;
            }
        }
    }

    public KeyStrokeListenerAsNormalClassDemo()
    {
        circle = new Circle(2);
        circle.setColor("red");
        keyStrokeListener = new MyKeyStrokeListener();
        circle.addKeyStrokeListener(keyStrokeListener);
        add(circle);
    }

    public static void main(String[] args)
    {
        Game.start(new KeyStrokeListenerAsNormalClassDemo(), 600, 400);
    }
}
