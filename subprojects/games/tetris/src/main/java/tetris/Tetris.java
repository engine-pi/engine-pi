/*
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
package tetris;

import pi.Controller;
import pi.Scene;
import tetris.scenes.CopyrightScene;

public class Tetris
{
    static
    {
        Controller.instantMode(false);
    }

    /**
     * Die Größe eines Blocks in Pixeln. In dem klassischen Gameboy-Tetris hat
     * ein Block die Größe {@code 8x8}, die Blockgröße beträgt also {@code 8}
     * Pixel.
     */
    public static final int BLOCK_SIZE = 8;

    /**
     * Die Breite des Spielfelds in Blöcken. In dem klassischen Gameboy-Tetris
     * passen 20 Blöcken in die Breite.
     */
    public static final int WIDTH = 20;

    /**
     * Die Höhe des Spielfelds in Blöcken. In dem klassischen Gameboy-Tetris
     * passen 18 Blöcken in die Höhe.
     */
    public static final int HEIGHT = 18;

    /**
     * Das Spielfeld hat rechts einen Abstand zum Bildschirmrand von 8 Blöcken.
     */
    public static final int GRID_WIDTH = 10;

    public static final ColorScheme COLOR_SCHEME_GRAY = ColorScheme
        .createGrayColorScheme();

    public static final ColorScheme COLOR_SCHEME_GREEN = ColorScheme
        .createGreenColorScheme();

    /**
     * Aktiviert und deaktiviert den Debug-Modus der Engine Pi und des Spiels
     * Tetris.
     *
     * <p>
     * Die Methode ist eine Hüll-Methode um {@link Controller#debug(boolean)}.
     * </p>
     *
     * @param value ist dieser Wert true, wird Tetris ab sofort im Debug-Modus
     *     ausgeführt.
     */
    public static void setDebug(boolean value)
    {
        Controller.debug(value);
    }

    /**
     * Startet das Spiel mit der angegebenen Szene und bietet darüber hinaus die
     * Möglichkeit an, den Debug-Modus zu aktivieren oder zu deaktivieren.
     *
     * @param scene Die Szene, mit der das Spiel gestartet werden soll.
     */
    public static void start(Scene scene, boolean debug)
    {
        setDebug(debug);
        start(scene);
    }

    /**
     * Startet das Spiel mit der angegebenen Szene.
     *
     * @param scene Die Szene, mit der das Spiel gestartet werden soll.
     */
    public static void start(Scene scene)
    {
        Controller.title("Tetris im Gameboy-Stil auf der Engine Pi");
        scene.camera().meter(Tetris.BLOCK_SIZE);
        if (Controller.isRunning())
        {
            Controller.transitionToScene(scene);
        }
        else
        {
            Controller.start(scene, BLOCK_SIZE * WIDTH, BLOCK_SIZE * HEIGHT);
        }
    }

    /**
     * Startet das Spiel mit der {@link CopyrightScene}.
     */
    public static void start()
    {
        start(new CopyrightScene());
    }

    public static void main(String[] args)
    {
        Tetris.start();
    }
}
