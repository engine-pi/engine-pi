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

import pi.Controller;
import pi.Scene;
import pi.actor.ImageText;
import pi.actor.ImageText.CaseSensitivity;
import pi.actor.ImageText.Font;
import pi.util.TextAlignment;

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/main-classes/actor/image-text.md

/**
 * Demonstriert den <b>automatischen</b> und <b>erzwungen Zeilenumbruch</b>
 * eines Bilderschriftarttextes.
 *
 * <p>
 * <img alt="Screenshot" src=
 * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/docs/main-classes/actor/image-text/MultilineDemo.png">
 * </p>
 *
 * @author Josef Friedrich
 *
 * @see ImageText
 */
public class MultilineDemo extends Scene
{
    public MultilineDemo()
    {
        Font font = new Font("main-classes/actor/image-text/tetris")
            .supportsCase(CaseSensitivity.UPPER);
        ImageText textField = new ImageText(font).content(
            "Das ist ein laengerer Text, der in mehrere Zeilen unterteilt ist. "
                    + "Zeilenumbrueche\nkoennen auch\nerzwungen werden.")
            .alignment(TextAlignment.LEFT)
            .lineWidth(20)
            .pixelMultiplication(4);
        add(textField);
        backgroundColor("white");
        focus(textField);
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new MultilineDemo());
    }
}
