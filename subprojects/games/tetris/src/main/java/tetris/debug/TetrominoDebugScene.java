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
package tetris.debug;

import java.awt.Color;
import java.awt.event.KeyEvent;

import pi.Game;
import pi.Scene;
import pi.Text;
import pi.event.KeyStrokeListener;
import tetris.Tetris;
import tetris.tetrominos.Grid;
import tetris.tetrominos.Tetromino;

/**
 * @author Josef Friedrich
 */
public class TetrominoDebugScene extends Scene implements KeyStrokeListener
{
    static
    {
        Game.instantMode(false);
    }

    private final Text rotation;

    Tetromino[] t;

    Grid grid;

    public TetrominoDebugScene()
    {
        rotation = new Text("0", 2);
        rotation.color(Color.WHITE);
        rotation.position(2, (double) Tetris.HEIGHT / 2);
        grid = new Grid(Tetris.WIDTH, Tetris.HEIGHT);
        camera().focus((double) Tetris.WIDTH / 2, (double) Tetris.HEIGHT / 2);
        add(rotation);
        t = new Tetromino[7];
        // L I J
        // O
        // Z T S
        int x1 = 3;
        int x2 = 9;
        int x3 = 16;
        int y1 = 3;
        int y2 = 8;
        int y3 = 13;
        t[0] = createTetromino("L", x1, y3);
        t[1] = createTetromino("I", x2, y3);
        t[2] = createTetromino("J", x3, y3);
        t[3] = createTetromino("O", x2, y2);
        t[4] = createTetromino("Z", x1, y1);
        t[5] = createTetromino("T", x2, y1);
        t[6] = createTetromino("S", x3, y1);
    }

    private Tetromino createTetromino(String name, int x, int y)
    {
        return Tetromino.create(this, grid, name, x, y);
    }

    @Override
    public void onKeyDown(KeyEvent keyEvent)
    {
        switch (keyEvent.getKeyCode())
        {
        case KeyEvent.VK_LEFT:
            for (Tetromino i : t)
            {
                i.moveLeft();
            }
            break;

        case KeyEvent.VK_RIGHT:
            for (Tetromino i : t)
            {
                i.moveRight();
            }
            break;

        case KeyEvent.VK_DOWN:
            for (Tetromino i : t)
            {
                i.moveDown();
            }
            break;

        case KeyEvent.VK_SPACE:
            for (Tetromino i : t)
            {
                i.rotate();
            }
            rotation.content(String.valueOf(t[0].rotation));
            break;
        }
    }

    public static void start(boolean debug)
    {
        // Muss vorher gesetzt werden
        Game.debug(debug);
        Tetris.start(new TetrominoDebugScene(), debug);
    }

    public static void main(String[] args)
    {
        start(false);
    }
}
