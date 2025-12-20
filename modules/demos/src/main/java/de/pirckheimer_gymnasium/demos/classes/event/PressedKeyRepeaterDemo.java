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
package de.pirckheimer_gymnasium.demos.classes.event;

import java.awt.event.KeyEvent;

import pi.Game;
import pi.Scene;
import pi.event.KeyStrokeListener;
import pi.event.PressedKeyRepeater;

/**
 * Demonstriert die Klasse {@link pi.event.PressedKeyRepeater}.
 */
public class PressedKeyRepeaterDemo extends Scene implements KeyStrokeListener
{
    PressedKeyRepeater repeater;

    public PressedKeyRepeaterDemo()
    {
        repeater = new PressedKeyRepeater(0.5, 1);
        repeater.addListener(KeyEvent.VK_RIGHT, () -> {
            System.out.println("right");
        });
        repeater.addListener(KeyEvent.VK_LEFT,
                () -> System.out.println("left initial"),
                () -> System.out.println("left"),
                () -> System.out.println("left final"));
    }

    public void onKeyDown(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            System.out.println("stop");
            repeater.stop();
        }
    }

    public static void main(String[] args)
    {
        Game.start(new PressedKeyRepeaterDemo(), 400, 300);
    }
}
