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
package de.pirckheimer_gymnasium.engine_pi_demos.actor;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.ImageFont;
import de.pirckheimer_gymnasium.engine_pi.actor.ImageFontCaseSensitivity;
import de.pirckheimer_gymnasium.engine_pi.actor.ImageFontText;
import de.pirckheimer_gymnasium.engine_pi.util.TextAlignment;

/**
 * Demonstriert den <b>automatischen</b> und <b>erzwungen Zeilenumbruch</b>
 * eines Bilderschriftarttextes.
 *
 * <p>
 * <img src=
 * "https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/actor/ImageFontTextMultilineDemo.png">
 * </p>
 *
 * @author Josef Friedrich
 *
 * @see ImageFontText
 */
public class ImageFontTextMultilineDemo extends Scene
{
    public ImageFontTextMultilineDemo()
    {
        ImageFont font = new ImageFont("image-font/tetris",
                ImageFontCaseSensitivity.TO_UPPER);
        ImageFontText textField = new ImageFontText(font,
                "Das ist ein laengerer Text, der in mehrere Zeilen unterteilt ist. "
                        + "Zeilenumbrueche\nkoennen auch\nerzwungen werden.",
                20, TextAlignment.LEFT);
        add(textField);
        setBackgroundColor("white");
        setFocus(textField);
    }

    public static void main(String[] args)
    {
        Game.start(new ImageFontTextMultilineDemo());
    }
}
