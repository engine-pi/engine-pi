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
package demos.classes.graphics.boxes_ng;

import java.awt.Graphics2D;

import demos.graphics2d.Graphics2DComponent;
import pi.graphics.boxes_ng.EllipseBox;
import pi.graphics.boxes_ng.VerticalBox;

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/graphics/boxes_ng/VerticalBox.java

public class VerticalBoxDemo extends Graphics2DComponent
{
    public void render(Graphics2D g)
    {
        var box = new VerticalBox<>(new EllipseBox(100, 100).color("red"),
                new EllipseBox(100, 200).color("green"),
                new EllipseBox(200, 100).color("blue")).padding(10);
        box.anchor(200, 100);
        box.render(g).debug();
    }

    public static void main(String[] args)
    {
        new VerticalBoxDemo().open();
    }
}
