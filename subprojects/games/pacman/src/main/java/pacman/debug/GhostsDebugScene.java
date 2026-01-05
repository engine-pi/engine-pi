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
package pacman.debug;

import static pacman.actors.GhostState.DOWN;
import static pacman.actors.GhostState.LEFT;
import static pacman.actors.GhostState.RIGHT;
import static pacman.actors.GhostState.STAND;
import static pacman.actors.GhostState.UP;

import java.awt.event.KeyEvent;

import pacman.Main;
import pacman.actors.Blinky;
import pacman.actors.Clyde;
import pacman.actors.Ghost;
import pacman.actors.Inky;
import pacman.actors.Pinky;
import pi.Game;
import pi.Scene;
import pi.event.KeyStrokeListener;

public class GhostsDebugScene extends Scene implements KeyStrokeListener
{
    static
    {
        Game.instantMode(false);
    }

    Ghost blinky;

    Ghost clyde;

    Ghost inky;

    Ghost pinky;

    Ghost current;

    public GhostsDebugScene()
    {
        camera().meter(16);
        blinky = addGhost(Blinky.class, 2, 2);
        clyde = addGhost(Clyde.class, -2, 2);
        inky = addGhost(Inky.class, -2, -2);
        pinky = addGhost(Pinky.class, 2, -2);
        current = clyde;
    }

    private Ghost addGhost(Class<? extends Ghost> clazz, int x, int y)
    {
        Ghost ghost = Ghost.createGhost(clazz);
        assert ghost != null;
        ghost.position(x, y);
        add(ghost);
        return ghost;
    }

    private void setCurrent(Ghost ghost)
    {
        current.state(STAND);
        current = ghost;
        current.state(UP);
    }

    @Override
    public void onKeyDown(KeyEvent event)
    {
        switch (event.getKeyCode())
        {
        case KeyEvent.VK_SPACE -> current.state(STAND);
        case KeyEvent.VK_DOWN -> current.state(DOWN);
        case KeyEvent.VK_UP -> current.state(UP);
        case KeyEvent.VK_LEFT -> current.state(LEFT);
        case KeyEvent.VK_RIGHT -> current.state(RIGHT);
        case KeyEvent.VK_1 -> setCurrent(blinky);
        case KeyEvent.VK_2 -> setCurrent(clyde);
        case KeyEvent.VK_3 -> setCurrent(inky);
        case KeyEvent.VK_4 -> setCurrent(pinky);
        }
    }

    public static void main(String[] args)
    {
        Main.start(new GhostsDebugScene(), 4);
    }
}
