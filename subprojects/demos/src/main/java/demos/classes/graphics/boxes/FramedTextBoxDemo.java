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
import static pi.Resources.fonts;

import pi.graphics.boxes.Box;
import pi.graphics.boxes.FramedTextBox;
import pi.graphics.boxes.VerticalBox;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import demos.graphics2d.Graphics2DComponent;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/pi/graphics/boxes/FramedTextBox.java

public class FramedTextBoxDemo extends Graphics2DComponent
{
    Font font = fonts.getDefault()
            .deriveFont(32f);

    Color backgroundColor = colors.get("red");

    Color borderColor = colors.get("blue");

    public void render(Graphics2D g)
    {
        var allFeatures = new FramedTextBox("All features");
        allFeatures.background.color(backgroundColor);
        allFeatures.border.color(borderColor);
        allFeatures.border.thickness(21);
        allFeatures.margin.allSides(10);
        allFeatures.padding.allSides(40);

        var onlyBackground = new FramedTextBox("only background");
        onlyBackground.background.color(backgroundColor);

        var onlyBorderColor = new FramedTextBox("only borderColor");
        onlyBorderColor.border.color(borderColor);

        var onlyBorderSize = new FramedTextBox("only borderSize");
        onlyBorderSize.border.thickness(13);
        new VerticalBox<Box>(allFeatures, onlyBackground, onlyBorderColor,
                onlyBorderSize).anchor(0, 0)
                .render(g)
                .debug();

        new FramedTextBox("default").x(300)
                .render(g)
                .debug();
    }

    public static void main(String[] args)
    {
        new FramedTextBoxDemo().show();
    }
}
