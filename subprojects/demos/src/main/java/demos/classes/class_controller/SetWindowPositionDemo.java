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

import java.awt.event.KeyEvent;

import pi.Controller;
import pi.Scene;
import pi.graphics.geom.Direction;

/**
 * Demonstriert die Methode {@link pi.Controller#windowPosition(int, int)}
 */
public class SetWindowPositionDemo
{
    public static void main(String[] args)
    {
        Controller.windowPosition(Direction.UP_LEFT);
        Controller.start(new Scene());
        Controller.addKeyStrokeListener((event) -> {
            switch (event.getKeyCode())
            {
            case KeyEvent.VK_1 -> Controller.windowPosition(Direction.UP);
            case KeyEvent.VK_2 -> Controller.windowPosition(Direction.UP_RIGHT);
            case KeyEvent.VK_3 -> Controller.windowPosition(Direction.RIGHT);
            case KeyEvent.VK_4 ->
                Controller.windowPosition(Direction.DOWN_RIGHT);
            case KeyEvent.VK_5 -> Controller.windowPosition(Direction.DOWN);
            case KeyEvent.VK_6 ->
                Controller.windowPosition(Direction.DOWN_LEFT);
            case KeyEvent.VK_7 -> Controller.windowPosition(Direction.LEFT);
            case KeyEvent.VK_8 -> Controller.windowPosition(Direction.UP_LEFT);
            case KeyEvent.VK_9 -> Controller.windowPosition(Direction.NONE);
            }
        });
    }
}
