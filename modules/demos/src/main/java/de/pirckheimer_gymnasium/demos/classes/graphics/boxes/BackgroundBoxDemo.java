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
import pi.graphics.boxes.BackgroundBox;
import pi.graphics.boxes.MarginBox;
import pi.graphics.boxes.TextLineBox;
import pi.graphics.boxes.VerticalBox;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/pi/graphics/boxes/BackgroundBox.java

public class BackgroundBoxDemo extends Graphics2DComponent
{
    public void render(Graphics2D g)
    {
        var defaultSettings = new BackgroundBox(new TextLineBox("default"));

        var custom = new BackgroundBox(new TextLineBox("custom"))
                .color(colors.get("red"));

        var nested = new BackgroundBox(
                new MarginBox(new BackgroundBox(new TextLineBox("nested"))
                        .color(colors.get("yellow"))).allSides(10))
                .color(colors.get("green"));

        new VerticalBox<>(defaultSettings, custom, nested).anchor(0, 0)
                .render(g).debug();
    }

    public static void main(String[] args)
    {
        new BackgroundBoxDemo().show();
    }
}
