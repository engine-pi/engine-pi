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

import pi.Scene;

/**
 * @author Josef Friedrich
 */
class IDeluxe extends Tetromino
{
    public IDeluxe(Scene scene, Grid grid, int x, int y)
    {
        super(scene, grid, "I", x, y);
        // addBlock(0, "I_h_center", "I_v_center", x, y);
        // addBlock(1, "I_h_left", "I_v_bottom", x - 1, y);
        // addBlock(2, "I_h_center", "I_v_center", x + 1, y);
        // addBlock(3, "I_h_right", "I_v_top", x + 2, y);
    }

    protected void doRotation()
    {
        switch (rotation)
        {
        case 0:
        case 2:
            moveBlock(0, 0, 0);
            moveBlock(1, -1, 1);
            moveBlock(2, 1, -1);
            moveBlock(3, 2, -2);
            break;

        case 1:
        case 3:
            moveBlock(0, 0, 0);
            moveBlock(1, 1, -1);
            moveBlock(2, -1, 1);
            moveBlock(3, -2, 2);
            break;
        }
    }
}
