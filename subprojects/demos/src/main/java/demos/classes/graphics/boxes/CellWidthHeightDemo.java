/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
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
package demos.classes.graphics.boxes;

import static pi.Controller.colors;

import java.awt.Graphics2D;

import demos.graphics2d.Graphics2DComponent;
import pi.graphics.boxes.BackgroundBox;
import pi.graphics.boxes.CellBox;
import pi.graphics.boxes.DimensionBox;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/pi/graphics/boxes/CellBox.java

public class CellWidthHeightDemo extends Graphics2DComponent
{
    public void render(Graphics2D g)
    {
        new BackgroundBox(
                new CellBox(new DimensionBox()).width(200).height(200))
                    .color(colors.get("red"))
                    .render(g)
                    .debug();
    }

    public static void main(String[] args)
    {
        new CellWidthHeightDemo().show();
    }
}
