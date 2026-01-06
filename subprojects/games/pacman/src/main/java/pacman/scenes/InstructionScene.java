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
package pacman.scenes;

import pacman.Main;
import pacman.actors.Blinky;
import pacman.actors.Clyde;
import pacman.actors.Ghost;
import pacman.actors.Inky;
import pacman.actors.Pinky;
import pacman.actors.Text;
import pi.Controller;

public class InstructionScene extends BaseScene
{
    static
    {
        Controller.instantMode(false);
    }

    public InstructionScene()
    {
        addImageFontText("1UP   HIGH SCORE   2UP", "white", 3, 35);
        addImageFontText("00", "white", 5, 34);
        addImageFontText("CHARACTER / NICKNAME", "white", 7, 30);
        // Blinky
        int BLINKY_Y = 28;
        addGhost(Blinky.class, BLINKY_Y);
        addImageFontText("-SHADOW    \"BLINKY\"", "red", 7, BLINKY_Y);
        // Pinky
        int PINKY_Y = 25;
        addGhost(Pinky.class, PINKY_Y);
        addImageFontText("-SPEEDY    \"PINKY\"", "pink", 7, PINKY_Y);
        // Inky
        int INKY_Y = 22;
        addGhost(Inky.class, INKY_Y);
        addImageFontText("-BASHFUL   \"INKY\"", "cyan", 7, INKY_Y);
        // Clyde
        int CLYDE_Y = 19;
        addGhost(Clyde.class, CLYDE_Y);
        addImageFontText("-POKEY     \"CLYDE\"", "orange", 7, CLYDE_Y);
        addImageFontText("© 1980 MIDWAY MFG.CO.", "pink", 4, 5);
        addImageFontText("CREDIT  0", "white", 2, 0);
    }

    public void addGhost(Class<? extends Ghost> clazz, double y)
    {
        Ghost ghost = Ghost.createGhost(clazz);
        assert ghost != null;
        ghost.position(4, y - 0.25);
        add(ghost);
    }

    public void addImageFontText(String content, String color, int x, int y)
    {
        Text text = new Text(content, color);
        text.position(x, y);
        add(text);
    }

    public static void main(String[] args)
    {
        Main.start(new InstructionScene(), 4);
    }
}
