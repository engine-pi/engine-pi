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
import pi.graphics.boxes.ImageBox;
import pi.graphics.boxes.VerticalBox;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/pi/graphics/boxes/ImageBox.java

public class ImageBoxDemo extends Graphics2DComponent
{
    private ImageBox box()
    {
        return new ImageBox("dude/box/obj_box001.png");
    }

    private ImageBox car()
    {
        return new ImageBox("car/truck-240px.png");
    }

    public void render(Graphics2D g)
    {
        new VerticalBox<ImageBox>(box(), box().width(300).height(200),
                box().width(16).height(16)).render(g).debug();

        new VerticalBox<ImageBox>(car(), car().flippedVertically(),
                car().flippedHorizontally(),
                car().flippedVertically().flippedHorizontally()).anchor(400, 0)
                .render(g).debug();
    }

    public static void main(String[] args)
    {
        new ImageBoxDemo().show();
    }
}
