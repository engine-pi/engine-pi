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

import java.awt.Graphics2D;

import demos.graphics2d.Graphics2DComponent;
import pi.graphics.boxes.BorderBox;
import pi.graphics.boxes.HAlign;
import pi.graphics.boxes.TextTableBox;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/pi/graphics/boxes/TextTableBox.java

public class TextTableBoxDemo extends Graphics2DComponent
{
    public void render(Graphics2D g)
    {
        TextTableBox table = new TextTableBox("Cell 1", "Cell 2", "Cell 3",
                "Cell 4\nhas\nmultiple lines");
        table.forEachRowBox(0, b -> b.cell.width(300));
        table.forEachColumnBox(0, b -> {
            b.cell.hAlign(HAlign.RIGHT);
            b.box.color("green");
        });
        table.forBox(0, 0, b -> {
            b.box.color("red");
            b.box.content("This is cell 0,0");
        });
        table.padding(30);
        table.forEachColumnBox(1, b -> {
            b.cell.hAlign(HAlign.RIGHT);
            b.box.color("green");
        });
        new BorderBox(table).color("black")
                .render(g)
                .debug();
    }

    public static void main(String[] args)
    {
        new TextTableBoxDemo().show();
    }
}
