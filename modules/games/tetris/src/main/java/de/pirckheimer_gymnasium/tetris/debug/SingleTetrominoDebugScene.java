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
package de.pirckheimer_gymnasium.tetris.debug;

import java.awt.Color;
import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Text;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;
import de.pirckheimer_gymnasium.tetris.Tetris;
import de.pirckheimer_gymnasium.tetris.tetrominos.Grid;
import de.pirckheimer_gymnasium.tetris.tetrominos.Tetromino;

/**
 * @author Josef Friedrich
 */
public class SingleTetrominoDebugScene extends Scene
        implements KeyStrokeListener
{
    private final Text rotation;

    Tetromino tetromino;

    Grid grid;

    public SingleTetrominoDebugScene()
    {
        rotation = new Text("0", 1);
        rotation.setColor(Color.WHITE);
        rotation.setPosition(0, 0);
        grid = new Grid(20, 18);
        add(rotation);
        createTetromino("L");
    }

    public void createTetromino(String name)
    {
        if (tetromino != null)
        {
            tetromino.remove();
        }
        tetromino = Tetromino.create(this, grid, name, 3, 3);
    }

    @Override
    public void onKeyDown(KeyEvent keyEvent)
    {
        switch (keyEvent.getKeyCode())
        {
        case KeyEvent.VK_L:
            createTetromino("L");
            break;

        case KeyEvent.VK_J:
            createTetromino("J");
            break;

        case KeyEvent.VK_I:
            createTetromino("I");
            break;

        case KeyEvent.VK_O:
            createTetromino("O");
            break;

        case KeyEvent.VK_Z:
            createTetromino("Z");
            break;

        case KeyEvent.VK_S:
            createTetromino("S");
            break;

        case KeyEvent.VK_T:
            createTetromino("T");
            break;

        case KeyEvent.VK_LEFT:
            tetromino.moveLeft();
            break;

        case KeyEvent.VK_RIGHT:
            tetromino.moveRight();
            break;

        case KeyEvent.VK_DOWN:
            tetromino.moveDown();
            break;

        case KeyEvent.VK_SPACE:
            tetromino.rotate();
            rotation.setContent(tetromino.rotation + "");
            break;
        }
    }

    public static void main(String[] args)
    {
        Tetris.start(new SingleTetrominoDebugScene(), true);
    }
}
