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
package demos.classes.actor;

import java.awt.event.KeyEvent;

import pi.Controller;
import pi.Scene;
import pi.Circle;
import pi.actor.Actor;
import pi.actor.Group;
import pi.Rectangle;
import pi.event.KeyStrokeListener;

/**
 * Demonstriert die Klasse {@link Group}.
 *
 * @author Josef Friedrich
 */
public class GroupDemo extends Scene implements KeyStrokeListener
{
    private final Group<Actor> group;

    public GroupDemo()
    {
        Circle leftEye = new Circle(3);
        leftEye.center(-5, 5);

        Circle rightEye = new Circle(3);
        rightEye.center(5, 5);

        Rectangle nose = new Rectangle(1, 5);
        nose.center(0, 2);

        Rectangle mouth = new Rectangle(5, 1);
        mouth.center(0, -4);

        group = new Group<>(leftEye, rightEye, nose, mouth).addToScene(this);
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
        case KeyEvent.VK_UP -> group.forEach(actor -> actor.moveBy(0, 1));
        case KeyEvent.VK_DOWN -> group.forEach(actor -> actor.moveBy(0, -1));
        case KeyEvent.VK_RIGHT -> group.forEach(actor -> actor.moveBy(1, 0));
        case KeyEvent.VK_LEFT -> group.forEach(actor -> actor.moveBy(-1, 0));
        }
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new GroupDemo());
    }
}
