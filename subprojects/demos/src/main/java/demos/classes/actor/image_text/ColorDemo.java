/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2024 Josef Friedrich and contributors.
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
package demos.classes.actor.image_text;

import static pi.Controller.colors;

import java.awt.Color;
import java.util.Map;

import pi.Controller;
import pi.Scene;
import pi.actor.ImageText;
import pi.actor.ImageText.CaseSensitivity;
import pi.actor.ImageText.Font;

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/main-classes/actor/image-text.md

/**
 * Demonstriert wie Bilderschriftarttexte <b>eingefärbt</b> werden können.
 *
 * <p>
 * <img alt="Screenshot" src=
 * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/docs/main-classes/actor/image-text/ColorDemo.png">
 * </p>
 *
 * @author Josef Friedrich
 */
public class ColorDemo extends Scene
{
    Font font = new Font("main-classes/actor/image-text/tetris",
            CaseSensitivity.TO_UPPER);

    public ColorDemo()
    {
        backgroundColor("#eeeeee");
        int y = 9;
        for (Map.Entry<String, Color> entry : colors.getAll().entrySet())
        {
            setImageText(entry.getKey(), -5, y);
            y--;
        }
    }

    public void setImageText(String color, int x, int y)
    {
        ImageText textField = new ImageText(font, color, color);
        textField.anchor(x, y);
        add(textField);
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new ColorDemo(), 600, 800);
    }
}
