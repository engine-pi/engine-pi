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
import static pi.graphics.boxes.HAlign.CENTER;
import static pi.graphics.boxes.HAlign.LEFT;
import static pi.graphics.boxes.HAlign.RIGHT;
import static pi.graphics.boxes.VAlign.BOTTOM;
import static pi.graphics.boxes.VAlign.MIDDLE;
import static pi.graphics.boxes.VAlign.TOP;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;
import pi.graphics.boxes.BackgroundBox;
import pi.graphics.boxes.ContainerBox;
import pi.graphics.boxes.HAlign;
import pi.graphics.boxes.TextLineBox;
import pi.graphics.boxes.VAlign;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/pi/graphics/boxes/ContainerBox.java

public class ContainerBoxDemo extends Graphics2DComponent
{
    private void makeBox(Graphics2D g, HAlign hAlign, VAlign vAlign)
    {
        int x = 0;
        int y = 0;
        int space = 200;
        switch (hAlign)
        {
        case LEFT:
            break;

        case CENTER:
            x = space;
            break;

        case RIGHT:
            x = 2 * space;
            break;
        }

        switch (vAlign)
        {
        case TOP:
            break;

        case MIDDLE:
            y = space;
            break;

        case BOTTOM:
            y = 2 * space;
            break;
        }

        new BackgroundBox(new ContainerBox(
                new TextLineBox(String.format("%s %s", hAlign, vAlign)))
                .hAlign(hAlign).vAlign(vAlign).width(180).height(150))
                .color(colors.get("red")).anchor(x, y).render(g).debug();
    }

    public void render(Graphics2D g)
    {
        makeBox(g, LEFT, TOP);
        makeBox(g, LEFT, MIDDLE);
        makeBox(g, LEFT, BOTTOM);

        makeBox(g, CENTER, TOP);
        makeBox(g, CENTER, MIDDLE);
        makeBox(g, CENTER, BOTTOM);

        makeBox(g, RIGHT, TOP);
        makeBox(g, RIGHT, MIDDLE);
        makeBox(g, RIGHT, BOTTOM);
    }

    public static void main(String[] args)
    {
        new ContainerBoxDemo().show();
    }
}
