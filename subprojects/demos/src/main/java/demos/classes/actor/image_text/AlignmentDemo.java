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

import static pi.util.TextAlignment.CENTER;
import static pi.util.TextAlignment.LEFT;
import static pi.util.TextAlignment.RIGHT;

import pi.Controller;
import pi.Scene;
import pi.actor.ImageText;
import pi.actor.ImageText.CaseSensitivity;
import pi.actor.ImageText.Font;
import pi.util.TextAlignment;

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/main-classes/actor/image-text.md

/**
 * Demonstriert die <b>Textausrichtung</b> eines Bilderschriftarttextes.
 *
 * <p>
 * <img alt="Screenshot" src=
 * "https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/actor/ImageFontTextAlignmentDemo.png">
 * </p>
 *
 * @author Josef Friedrich
 *
 * @see pi.util.TextAlignment
 * @see pi.actor.ImageText
 */
public class AlignmentDemo extends Scene
{
    Font font = new Font("image-font/tetris", CaseSensitivity.TO_UPPER);

    public AlignmentDemo()
    {
        camera().meter(32);
        backgroundColor("blue");
        createText(3, "Dieser Text ist linksbuendig ausgerichtet.", LEFT);
        createText(-2, "Dieser Text ist zentriert ausgerichtet.", CENTER);
        createText(-7, "Dieser Text ist rechtsbuendig ausgerichtet.", RIGHT);
    }

    private void createText(int y, String content, TextAlignment alignment)
    {
        ImageText line = new ImageText(font, content, 18, alignment);
        line.anchor(-9, y);
        add(line);
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new AlignmentDemo());
    }
}
