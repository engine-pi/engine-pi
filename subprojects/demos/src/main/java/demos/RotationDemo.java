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
package demos;

import java.awt.Color;
import java.awt.event.KeyEvent;

import pi.Controller;
import pi.Scene;
import pi.actor.Polygon;
import pi.event.KeyStrokeListener;
import pi.graphics.geom.Vector;

public class RotationDemo extends Scene implements KeyStrokeListener
{
    Polygon triangle;

    public RotationDemo()
    {
        triangle = new Polygon(new Vector(0, 0), new Vector(1, 0),
                new Vector(.5, 3));
        triangle.color(Color.BLUE);
        add(triangle);
    }

    @Override
    public void onKeyDown(KeyEvent keyEvent)
    {
        switch (keyEvent.getKeyCode())
        {
        case KeyEvent.VK_LEFT:
            // Gegen den Uhrzeigersinn
            triangle.rotateBy(90);
            break;

        case KeyEvent.VK_RIGHT:
            // Im Uhrzeigersinn
            triangle.rotateBy(-90);
            break;

        case KeyEvent.VK_1:
            triangle.rotation(0);
            break;

        case KeyEvent.VK_2:
            triangle.rotation(90);
            break;

        case KeyEvent.VK_3:
            triangle.rotation(180);
            break;

        case KeyEvent.VK_4:
            triangle.rotation(270);
            break;
        }
    }

    public static void main(String[] args)
    {
        Controller.start(new RotationDemo(), 600, 400);
    }
}
