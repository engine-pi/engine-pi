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
package demos.classes.actor;

import static pi.Resources.fonts;

import java.awt.Font;

import pi.Game;
import pi.Scene;
import pi.Text;
import pi.resources.font.FontStyle;

/**
 * Demonstiert die Figur <b>Text</b>.
 *
 * @author Josef Friedrich
 *
 * @since 0.37.0
 */
public class TextDemo extends Scene
{

    public TextDemo()
    {
        backgroundColor("green");

        add(new Text("Das ist die mitgelieferte Schrift Can\ntarell", 1,
                "fonts/Cantarell-Regular.ttf").position(-7, 0));
        Font cantarell = fonts.get("fonts/Cantarell-Regular.ttf");

        // Demonstiert, ob die Grundline zweier Text-Figuren mit gleicher
        // y-Koordinate übereinstimmt.
        add(new Text("Mit Unterlängen", 2).font(cantarell).position(-7, -2));
        add(new Text("... ohne", 2).font(cantarell).position(4, -2));

        demonstrateFontStyle(0, 3);
        demonstrateFontStyle(1, 4);
        demonstrateFontStyle(2, 5);
        demonstrateFontStyle(3, 6);
    }

    public void demonstrateFontStyle(int style, int y)
    {
        add(new Text(FontStyle.getStyle(style).toString())
                .font(fonts.defaultFont(style)).position(3, y));
    }

    public static void main(String[] args)
    {
        Game.title("Cantarell");
        Game.start(new TextDemo());
    }
}
