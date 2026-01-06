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

import pi.Controller;
import pi.Scene;
import pi.Circle;

public class KeyStrokeListenerAsLambdaDemo extends Scene
{
    public KeyStrokeListenerAsLambdaDemo()
    {
        Circle circle = new Circle(2);
        circle.color(Color.RED);
        circle.addKeyStrokeListener(e -> {
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
            }
        });
        add(circle);
    }

    public static void main(String[] args)
    {
        Controller.start(new KeyStrokeListenerAsLambdaDemo(), 600, 400);
    }
}
