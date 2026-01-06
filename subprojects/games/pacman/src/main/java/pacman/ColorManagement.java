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
package pacman;

import static pi.Controller.colors;

import pi.resources.color.ColorContainer;

public class ColorManagement
{
    public static ColorContainer setColors()
    {
        colors.clear();
        // AllAssetsPalettes.png
        colors.add("Black", 0, 0, 0, "schwarz");
        colors.add("Red", 255, 0, 0, "rot");
        colors.add("Brown", 222, 151, 81, "braun");
        colors.add("Pink", 255, 183, 255, "rosa");
        colors.add("Cyan", 0, 255, 255, "blaugrün");
        colors.add("Blue", 71, 183, 255, "blau");
        colors.add("Orange", 255, 183, 81, "orange");
        colors.add("Yellow", 255, 255, 0, "gelb");
        colors.add("Indigo", 33, 33, 255, "indigo");
        colors.add("Green", 0, 255, 255, "grün");
        colors.add("Teal", 71, 183, 174, "türkis");
        colors.add("Salmon", 255, 183, 174, "lachsfarben");
        colors.add("White", 222, 222, 255, "weiß");
        return colors;
    }
}
