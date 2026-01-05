/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2024, 2025 Josef Friedrich and contributors.
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
package pacman;

import static pi.Controller.config;

import pi.Direction;
import pi.Game;
import pi.Scene;

public class Main
{
    static
    {
        Game.instantMode(false);
    }

    public static void start(Scene scene, int pixelMultiplication)
    {
        config.debug.enabled(true);
        config.coordinatesystem.linesNMeter(1);
        config.graphics.windowPosition(Direction.RIGHT);
        scene.camera().meter(8);
        // 224 = 28 * 8
        // 288 = 36 * 8
        config.graphics.pixelMultiplication(pixelMultiplication);
        Game.start(scene, 224, 288);
    }

    /**
     * Start die Szene mit einer Pixelvervielfältigung von 2.
     *
     * @param scene Die Szene, die gestartet werden soll.
     */
    public static void start(Scene scene)
    {
        start(scene, 2);
    }

    public static void main(String[] args)
    {
        Game.start();
    }
}
