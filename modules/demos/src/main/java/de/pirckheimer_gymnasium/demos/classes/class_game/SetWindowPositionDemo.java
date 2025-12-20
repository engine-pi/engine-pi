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
package de.pirckheimer_gymnasium.demos.classes.class_game;

import java.awt.event.KeyEvent;

import pi.Direction;
import pi.Game;
import pi.Scene;
import pi.debug.DebugConfiguration;

/**
 * Demonstriert die Methode {@link pi.Game#setWindowPosition(int, int)}
 */
public class SetWindowPositionDemo
{
    public static void main(String[] args)
    {
        DebugConfiguration.windowPosition = Direction.UP_LEFT;
        Game.start(new Scene());
        Game.addKeyStrokeListener((event) -> {
            switch (event.getKeyCode())
            {
            case KeyEvent.VK_1 -> Game.setWindowPosition(Direction.UP);
            case KeyEvent.VK_2 -> Game.setWindowPosition(Direction.UP_RIGHT);
            case KeyEvent.VK_3 -> Game.setWindowPosition(Direction.RIGHT);
            case KeyEvent.VK_4 -> Game.setWindowPosition(Direction.DOWN_RIGHT);
            case KeyEvent.VK_5 -> Game.setWindowPosition(Direction.DOWN);
            case KeyEvent.VK_6 -> Game.setWindowPosition(Direction.DOWN_LEFT);
            case KeyEvent.VK_7 -> Game.setWindowPosition(Direction.LEFT);
            case KeyEvent.VK_8 -> Game.setWindowPosition(Direction.UP_LEFT);
            case KeyEvent.VK_9 -> Game.setWindowPosition(Direction.NONE);
            }
        });
    }
}
