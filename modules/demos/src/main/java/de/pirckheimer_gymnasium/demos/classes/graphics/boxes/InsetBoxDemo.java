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
import pi.graphics.boxes.CombinedChildBox;
import pi.graphics.boxes.InsetBox;
import pi.graphics.boxes.TextLineBox;
import pi.graphics.boxes.VerticalBox;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/pi/graphics/boxes/InsetBox.java

class InsetTestBox extends CombinedChildBox
{
    BorderBox outerBorder;

    InsetBox margin;

    BorderBox innerBorder;

    TextLineBox textLine;

    public InsetTestBox(String content)
    {
        textLine = new TextLineBox(content);
        innerBorder = new BorderBox(textLine);
        innerBorder.thickness(1).color("gray");
        margin = new InsetBox(innerBorder);
        outerBorder = new BorderBox(margin);
        outerBorder.thickness(2).color("blue");
        addChild(outerBorder);
    }

    @Override
    public String toString()
    {
        return getToStringFormatter().format();
    }
}

public class InsetBoxDemo extends Graphics2DComponent
{
    private InsetTestBox box(String content)
    {
        return new InsetTestBox(content);
    }

    public void render(Graphics2D g)
    {
        var defaultSettings = box("default");
        var allSides = box(".allSides(50)");
        allSides.margin.allSides(50);
        var different = box(".top(5).right(10).bottom(15).left(20)");
        different.margin.top(5).right(10).bottom(15).left(20);
        new VerticalBox<>(defaultSettings, allSides, different).anchor(50, 50)
                .render(g).debug();
    }

    public static void main(String[] args)
    {
        new InsetBoxDemo().show();
    }
}
