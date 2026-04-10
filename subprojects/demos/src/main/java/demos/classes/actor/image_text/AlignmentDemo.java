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

import static pi.graphics.boxes.HAlign.CENTER;
import static pi.graphics.boxes.HAlign.LEFT;
import static pi.graphics.boxes.HAlign.RIGHT;

import pi.Controller;
import pi.Scene;
import pi.actor.ImageText;
import pi.actor.ImageText.CaseSensitivity;
import pi.graphics.boxes.HAlign;

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/main-classes/actor/image-text.md

/**
 * Demonstriert die <b>Textausrichtung</b> eines Bilderschriftarttextes.
 *
 * <p>
 * <img alt="Screenshot" src=
 * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/docs/main-classes/actor/image-text/AlignmentDemo.png">
 * </p>
 *
 * @author Josef Friedrich
 *
 * @see pi.graphics.boxes.HAlign
 * @see pi.actor.ImageText
 */
public class AlignmentDemo extends Scene
{
    ImageText.Font font = new ImageText.Font(
            "main-classes/actor/image-text/tetris")
                .supportsCase(CaseSensitivity.UPPER);

    public AlignmentDemo()
    {
        camera().meter(32);
        backgroundColor("blue");
        createText(3, "Dieser Text ist linksbuendig ausgerichtet.", LEFT);
        createText(-2, "Dieser Text ist zentriert ausgerichtet.", CENTER);
        createText(-7, "Dieser Text ist rechtsbuendig ausgerichtet.", RIGHT);
    }

    private void createText(int y, String content, HAlign alignment)
    {
        ImageText line = new ImageText(font).content(content)
            .lineWidth(18)
            .hAlign(alignment)
            .pixelMultiplication(4);
        line.anchor(-9, y);
        // Um die toString-Methode zu testen
        add(line.debug());
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new AlignmentDemo());
    }
}
