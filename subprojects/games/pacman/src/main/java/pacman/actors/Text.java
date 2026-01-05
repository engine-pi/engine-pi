/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2024, 2025 Josef Friedrich and contributors.
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
package pacman.actors;

import pi.Resources;
import pi.actor.ImageFont;
import pi.actor.ImageFontCaseSensitivity;
import pi.actor.ImageFontText;
import pi.util.TextAlignment;

public class Text extends ImageFontText
{
    private static final ImageFont font = new ImageFont("images/image-font",
            ImageFontCaseSensitivity.TO_UPPER);

    public Text(String content, String color)
    {
        super(font, content, 28, TextAlignment.LEFT,
                Resources.colors.get(color), 1, 8);
    }

    public Text(String content)
    {
        this(content, "red");
    }
}
