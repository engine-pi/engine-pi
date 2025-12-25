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
package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;
import pi.graphics.boxes.BorderBox;
import pi.graphics.boxes.FramedTextBox;
import pi.graphics.boxes.GridBox;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/pi/graphics/boxes/GridBox.java

public class GridBoxDemo extends Graphics2DComponent
{

    private FramedTextBox box(int number)
    {
        var box = new FramedTextBox(number + "");
        box.padding.allSides(number * 3);
        box.background.color("red");
        return box;
    }

    private FramedTextBox[] boxes(int numberOfBoxes)
    {
        FramedTextBox[] boxes = new FramedTextBox[numberOfBoxes];
        for (int i = 0; i < boxes.length; i++)
        {
            boxes[i] = box(i);
        }
        return boxes;
    }

    public void render(Graphics2D g)
    {
        var box = new BorderBox(
                new GridBox<>(boxes(10)).columns(3).padding(10));
        box.anchor(200, 100);
        box.render(g).debug();
    }

    public static void main(String[] args)
    {
        new GridBoxDemo().show();
    }
}
