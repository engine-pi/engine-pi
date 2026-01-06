/*
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
package demos.classes.actor;

import java.awt.Color;
import java.util.Map;

import pi.Controller;
import pi.Resources;
import pi.Scene;
import pi.actor.ImageFont;
import pi.actor.ImageFontCaseSensitivity;
import pi.actor.ImageFontText;

/**
 * Demonstriert wie Bilderschriftarttexte <b>eingefärbt</b> werden können.
 *
 * <p>
 * <img alt="Screenshot" src=
 * "https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/actor/ImageFontTextColorDemo.png">
 * </p>
 *
 * @author Josef Friedrich
 */
public class ImageFontTextColorDemo extends Scene
{
    ImageFont font = new ImageFont("image-font/tetris",
            ImageFontCaseSensitivity.TO_UPPER);

    public ImageFontTextColorDemo()
    {
        backgroundColor("#eeeeee");
        int y = 9;
        for (Map.Entry<String, Color> entry : Resources.colors.getAll()
                .entrySet())
        {
            setImageFontText(entry.getKey(), -5, y);
            y--;
        }
    }

    public void setImageFontText(String color, int x, int y)
    {
        ImageFontText textField = new ImageFontText(font, color, color);
        textField.position(x, y);
        add(textField);
    }

    public static void main(String[] args)
    {
        Controller.start(new ImageFontTextColorDemo(), 600, 800);
    }
}
