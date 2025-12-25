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

import static pi.Resources.colors;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;
import pi.graphics.boxes.BorderBox;
import pi.graphics.boxes.Box;
import pi.graphics.boxes.TextLineBox;
import pi.graphics.boxes.VerticalBox;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/pi/graphics/boxes/BorderBox.java

public class BorderBoxDemo extends Graphics2DComponent
{
    public void render(Graphics2D g)
    {
        var defaultSettings = new BorderBox(new TextLineBox("default"));

        var lineWidth = new BorderBox(new TextLineBox("lineWidth"))
                .thickness(5);
        var lineColor = new BorderBox(new TextLineBox("lineColor"))
                .color(colors.get("blue"));

        new VerticalBox<Box>(defaultSettings, lineWidth, lineColor).anchor(0, 0)
                .render(g).debug();
    }

    public static void main(String[] args)
    {
        new BorderBoxDemo().show();
    }
}
