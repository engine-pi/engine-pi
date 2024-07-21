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
package de.pirckheimer_gymnasium.engine_pi_demos.game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;

/**
 * Demonstriert die Methode
 * {@link de.pirckheimer_gymnasium.engine_pi.Game#setWindowPosition(int, int)}
 */
public class SetWindowPositionDemo
{
    public static void main(String[] args)
    {
        Game.start(new Scene());
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        Vector vector = Game.getWindowSize();
        int windowWidth = (int) vector.getX();
        int windowHeight = (int) vector.getY();
        Game.addKeyStrokeListener((event) -> {
            switch (event.getKeyCode())
            {
            case KeyEvent.VK_1 ->
                // links oben
                Game.setWindowPosition(0, 0);
            case KeyEvent.VK_2 ->
                // rechts oben
                Game.setWindowPosition(screenWidth - windowWidth, 0);
            case KeyEvent.VK_3 ->
                // links unten
                Game.setWindowPosition(0, screenHeight - windowHeight);
            case KeyEvent.VK_4 ->
                // rechts unten
                Game.setWindowPosition(screenWidth - windowWidth,
                        screenHeight - windowHeight);
            case KeyEvent.VK_5 ->
                // zentrieren
                Game.setWindowPosition((screenWidth - windowWidth) / 2,
                        (screenHeight - windowHeight) / 2);
            }
        });
    }
}
