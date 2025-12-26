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
package tetris.tetrominos;

import pi.Scene;

/**
 * Ein Tetromino in Form eines <b>„J“</b>.
 *
 * @author Josef Friedrich
 */
class J extends Tetromino
{
    public J(Scene scene, Grid grid, int x, int y)
    {
        super(scene, grid, "J", x, y);
        addBlock(0, x, y);
        addBlock(1, x - 1, y);
        addBlock(2, x + 1, y);
        addBlock(3, x + 1, y - 1);
    }

    protected void doRotation()
    {
        switch (rotation)
        {
        // 0 -> 1
        case 1:
            moveBlock(1, 1, 1);
            moveBlock(2, -1, -1);
            moveBlock(3, -2, 0);
            break;

        // 1 -> 2
        case 2:
            moveBlock(1, 1, -1);
            moveBlock(2, -1, 1);
            moveBlock(3, 0, 2);
            break;

        // 2 -> 3
        case 3:
            moveBlock(1, -1, -1);
            moveBlock(2, 1, 1);
            moveBlock(3, 2, 0);
            break;

        // 3 -> 0
        case 0:
            moveBlock(1, -1, 1);
            moveBlock(2, 1, -1);
            moveBlock(3, 0, -2);
            break;
        }
    }
}
