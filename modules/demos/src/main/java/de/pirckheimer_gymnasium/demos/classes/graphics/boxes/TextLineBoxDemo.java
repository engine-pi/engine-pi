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

import static pi.Resources.fonts;
import pi.graphics.boxes.TextLineBox;
import pi.graphics.boxes.VerticalBox;

import java.awt.Font;
import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/pi/graphics/boxes/TextLineBox.java

public class TextLineBoxDemo extends Graphics2DComponent
{
    Font font = fonts.getDefault().deriveFont(64f);

    public void render(Graphics2D g)
    {
        new TextLineBox("as standalone box").fontSize(32).anchor(500, 400)
                .render(g);

        var box = new VerticalBox<>(new TextLineBox("default"),
                new TextLineBox("different fontSize").fontSize(42),
                new TextLineBox("custom Font").font(font),
                new TextLineBox("custom color").color("orange"),
                new TextLineBox("custom content").content("updated content"));
        box.anchor(200, 100);
        box.render(g).debug();
    }

    public static void main(String[] args)
    {
        new TextLineBoxDemo().show();
    }
}
