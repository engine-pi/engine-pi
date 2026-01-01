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

import pi.Game;
import pi.Scene;
import pi.Circle;
import pi.Rectangle;
import pi.event.KeyStrokeListener;

/**
 * Demonstriert die Methode {@link pi.Scene#setGravity(double, double)}
 */
public class GravityDemo extends Scene implements KeyStrokeListener
{
    private final Circle circle;

    public GravityDemo()
    {
        getCamera().setMeter(45);
        circle = new Circle();
        circle.makeDynamic();
        setGravity(0, -9.81);
        // oben
        createBorder(-5, 4.5, false);
        // unten
        createBorder(-5, -5, false);
        // links
        createBorder(-5, -5, true);
        // rechts
        createBorder(4.5, -5, true);
    }

    private Rectangle createBorder(double x, double y, boolean vertical)
    {
        Rectangle rectangle = !vertical ? new Rectangle(10, 0.5)
                : new Rectangle(0.5, 10);
        rectangle.setPosition(x, y);
        rectangle.makeStatic();
        return rectangle;
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
        case KeyEvent.VK_UP -> setGravity(0, 9.81);
        case KeyEvent.VK_DOWN -> setGravity(0, -9.81);
        case KeyEvent.VK_RIGHT -> setGravity(9.81, 0);
        case KeyEvent.VK_LEFT -> setGravity(-9.81, 0);
        case KeyEvent.VK_SPACE -> circle.sleep();
        }
    }

    @Override
    public void setGravity(double x, double y)
    {
        // Die Figur muss aufgeweckt werden, falls sie zur Ruhe gekommen ist.
        // Sonst wirkt eine Änderung der Gravitation nicht.
        circle.awake();
        super.setGravity(x, y);
    }

    public static void main(String[] args)
    {
        Game.debug();
        Game.start(new GravityDemo());
    }
}
