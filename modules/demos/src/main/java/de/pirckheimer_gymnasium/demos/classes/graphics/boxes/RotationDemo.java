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
import java.awt.geom.AffineTransform;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;
import pi.graphics.boxes.BorderBox;
import pi.graphics.boxes.MarginBox;
import pi.graphics.boxes.TextLineBox;
import pi.graphics.boxes.VerticalBox;

public class RotationDemo extends Graphics2DComponent
{

    public void render(Graphics2D g)
    {
        var defaultSettings = new BorderBox(
                new MarginBox(new BorderBox(new TextLineBox("default"))));

        var manuel = new MarginBox(new TextLineBox("default")).allSides(50);

        AffineTransform oldTransform = g.getTransform();

        AffineTransform newTransform = new AffineTransform();
        newTransform.rotate(Math.toRadians(-45));
        g.setTransform(newTransform);

        new VerticalBox<>(defaultSettings, manuel).anchor(-100, 100).render(g)
                .debug();
        g.setTransform(oldTransform);
    }

    public static void main(String[] args)
    {
        new RotationDemo().show();
    }
}
