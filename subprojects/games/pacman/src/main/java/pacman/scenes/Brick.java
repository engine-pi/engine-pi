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

import pi.Image;

public enum Brick
{
    // corner
    CORNER_BL("R 0.5, 0.5, 0.5, 0.5"),
    CORNER_BR("R 0, 0.5, 0.5, 0.5"),
    CORNER_TL("R 0.5, 0, 0.5, 0.5"),
    CORNER_TR("R 0, 0, 0.5, 0.5"),
    // double
    DOUBLE_B("R 0, 0, 1, 0.5"),
    DOUBLE_L("R 0, 0, 0.5, 1"),
    DOUBLE_R("R 0.5, 0, 0.5, 1"),
    DOUBLE_T("R 0, 0.5, 1, 0.5"),
    // double corner
    DOUBLE_CORNER_BL("R0,0,0.5,1 & R0.5,0,0.5,0.5"),
    DOUBLE_CORNER_BR("R0,0,0.5,0.5 & R0.5,0,0.5,1"),
    DOUBLE_CORNER_TL("R0,0,0.5,0.5 & R0,0.5,1,0.5"),
    DOUBLE_CORNER_TR("R0.5,0,0.5,0.5 & R0,0.5,1,0.5"),
    FILL("R0,0,1,1"),
    GATE(""),
    // outer corner
    OUTER_CORNER_BL("R 0.5, 0.5, 0.5, 0.5"),
    OUTER_CORNER_BR("R 0, 0.5, 0.5, 0.5"),
    OUTER_CORNER_TL("R 0.5, 0, 0.5, 0.5"),
    OUTER_CORNER_TR("R 0, 0, 0.5, 0.5"),
    // inner corner
    INNER_CORNER_BL("R0.5,0,0.5,0.5 & R0,0.5,1,0.5"),
    INNER_CORNER_BL_BORDER_T("R0.5,0,0.5,0.5 & R0,0.5,1,0.5"),
    INNER_CORNER_BL_BORDER_R("R0.5,0,0.5,0.5 & R0,0.5,1,0.5"),
    INNER_CORNER_BR("R0,0,0.5,0.5 & R0,0.5,1,0.5"),
    INNER_CORNER_BR_BORDER_T("R0,0,0.5,0.5 & R0,0.5,1,0.5"),
    INNER_CORNER_BR_BORDER_L("R0,0,0.5,0.5 & R0,0.5,1,0.5"),
    INNER_CORNER_TL("R0,0,1,0.5 & R0.5,0.5,0.5,0.5"),
    INNER_CORNER_TL_BORDER_R("R0,0,1,0.5 & R0.5,0.5,0.5,0.5"),
    INNER_CORNER_TR("R0,0,1,0.5 & R0,0.5,0.5,0.5"),
    INNER_CORNER_TR_BORDER_L("R0,0,1,0.5 & R0,0.5,0.5,0.5"),
    // single
    SINGLE_B("R 0, 0.5, 1, 0.5"),
    SINGLE_L("R 0.5, 0, 0.5, 1"),
    SINGLE_R("R 0, 0, 0.5, 1"),
    SINGLE_T("R 0, 0, 1, 0.5");

    private final String fixture;

    Brick(String fixture)
    {
        this.fixture = fixture;
    }

    public Image getImage()
    {
        Image image = new Image("images/maze/" + name() + ".png", 8);
        image.fixtures(fixture);
        return image;
    }
}
