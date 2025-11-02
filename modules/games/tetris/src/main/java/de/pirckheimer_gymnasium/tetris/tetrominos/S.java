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
package de.pirckheimer_gymnasium.tetris.tetrominos;

import de.pirckheimer_gymnasium.engine_pi.Scene;

/**
 * Ein Tetromino in Form eines <b>„S“</b>.
 *
 * @author Josef Friedrich
 */
class S extends Tetromino
{
    public S(Scene scene, Grid grid, int x, int y)
    {
        super(scene, grid, "S", x, y);
        addBlock(0, x, y);
        addBlock(1, x + 1, y);
        addBlock(2, x - 1, y - 1);
        addBlock(3, x, y - 1);
    }

    protected void doRotation()
    {
        switch (rotation)
        {
        // 0 -> 1
        // 2 -> 3
        case 1:
        case 3:
            moveBlock(1, -2, 0);
            moveBlock(2, 0, 2);
            break;

        // 3 -> 0
        // 1 -> 2
        case 0:
        case 2:
            moveBlock(1, 2, 0);
            moveBlock(2, 0, -2);
            break;
        }
    }
}
