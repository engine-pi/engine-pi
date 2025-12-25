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

import static pi.Resources.colors;

import pi.graphics.boxes.FramedTextBox;
import pi.graphics.boxes.HAlign;
import pi.graphics.boxes.VAlign;

public class DemoBoxes
{
    public static FramedTextBox demo()
    {
        FramedTextBox box = new FramedTextBox("");
        box.background.color(colors.get("blue", 100));
        box.padding.allSides(10);
        box.container.hAlign(HAlign.CENTER);
        box.container.vAlign(VAlign.MIDDLE);
        return box;
    }

    public static FramedTextBox demo(String content)
    {
        var b = demo();
        b.textLine.content(content);
        return b;
    }

    public static FramedTextBox demo(String content, int width, int height)
    {
        var b = demo(content);
        b.container.width(width);
        b.container.height(height);
        return b;
    }
}
