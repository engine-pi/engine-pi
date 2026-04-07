/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2026 Josef Friedrich and contributors.
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
package demos.docs.main_classes.actor.group;

import java.awt.Color;
import java.awt.event.KeyEvent;

import pi.Controller;
import pi.Scene;
import pi.Circle;
import pi.actor.Actor;
import pi.actor.Group;
import pi.Rectangle;
import pi.event.KeyStrokeListener;

import static pi.Controller.colors;

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/main-classes/actor/group.md

/**
 * Demonstriert die Klasse {@link Group}.
 *
 * @author Josef Friedrich
 *
 * @since 0.45.0
 */
public class GroupDemo extends Scene implements KeyStrokeListener
{
    private final Group<Actor> group;

    public GroupDemo()
    {
        info().title("Demonstriert die Klasse Group")
            .help(
                "Cursor: Die Pfeiltasten (Cursor-Tasten) bewegen alle Figuren.",
                "c: Färbt die Kreise (Circle)",
                "r: Färbt die Rechtecke");
        backgroundColor("#cccccc");
        Circle leftEye = new Circle(3);
        leftEye.center(-5, 5);

        Circle rightEye = new Circle(3);
        rightEye.center(5, 5);

        Rectangle nose = new Rectangle(1, 5);
        nose.center(0, 2);

        Rectangle mouth = new Rectangle(5, 1);
        mouth.center(0, -4);

        group = new Group<>(leftEye, rightEye, nose, mouth).addToScene(this);

        for (Actor actor : group)
        {
            System.out.println(actor);
        }
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        Color color = colors.random();
        switch (e.getKeyCode())
        {
        case KeyEvent.VK_UP -> group.forEach(actor -> actor.moveBy(0, 1));
        case KeyEvent.VK_DOWN -> group.forEach(actor -> actor.moveBy(0, -1));
        case KeyEvent.VK_RIGHT -> group.forEach(actor -> actor.moveBy(1, 0));
        case KeyEvent.VK_LEFT -> group.forEach(actor -> actor.moveBy(-1, 0));
        case KeyEvent.VK_R ->
            group.forEach(Rectangle.class, rectangle -> rectangle.color(color));
        case KeyEvent.VK_C ->
            group.forEach(Circle.class, circle -> circle.color(color));
        }
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new GroupDemo());
    }
}
