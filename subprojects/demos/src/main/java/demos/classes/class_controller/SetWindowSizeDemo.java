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
package demos.classes.class_controller;

import pi.Game;
import pi.Scene;

import java.awt.event.KeyEvent;

/**
 * Demonstriert die Methode {@link Game#windowPosition(int, int)}
 */
public class SetWindowSizeDemo
{
    public static void main(String[] args)
    {
        Game.start(new Scene());
        Game.addKeyStrokeListener((event) -> {
            switch (event.getKeyCode())
            {
            case KeyEvent.VK_1 -> {
                Game.windowSize(600, 400);
            }
            case KeyEvent.VK_2 -> {
                Game.windowSize(300, 200);
            }
            }
        });
    }
}
