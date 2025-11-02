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
 * Ein Tetromino in Form eines <b>„O“</b>.
 *
 * @author Josef Friedrich
 */
class O extends Tetromino
{
    public O(Scene scene, Grid grid, int x, int y)
    {
        super(scene, grid, "O", x, y);
        addBlock(0, x, y);
        addBlock(1, x + 1, y);
        addBlock(2, x, y - 1);
        addBlock(3, x + 1, y - 1);
    }

    protected void doRotation()
    {
        // keine Rotation notwendig bei O
    }
}
