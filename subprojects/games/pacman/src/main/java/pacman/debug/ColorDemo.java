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

import pacman.scenes.BaseScene;
import pi.Controller;
import pi.Text;

public class ColorDemo extends BaseScene
{
    public ColorDemo()
    {
        add(new Text("Blinky").color("teal").anchor(10, 10));
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new ColorDemo(), 1000, 1000);
    }
}
