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
package tetris.text;

import pi.Scene;
import pi.actor.ImageFontText;
import pi.util.TextAlignment;

public class NumberDisplay
{
    private int number;

    private ImageFontText font;

    public NumberDisplay(Scene scene, int x, int y, int maxDigits)
    {
        font = new ImageFontText(Font.getFont(), "0", maxDigits,
                TextAlignment.RIGHT);
        font.anchor(x, y);
        scene.add(font);
        set(0);
    }

    public void write(int number)
    {
        font.content(String.valueOf(number));
    }

    public void set(int number)
    {
        this.number = number;
        write(number);
    }

    public int get()
    {
        assert number > -1;
        return number;
    }

    public void add(int number)
    {
        assert number > -1 : "Add only supports positiv values, got " + number;
        this.number += number;
        write(this.number);
    }
}
