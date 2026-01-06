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

import java.util.concurrent.atomic.AtomicInteger;

import pi.Controller;
import pi.Scene;
import pi.actor.ImageFont;
import pi.actor.ImageFontCaseSensitivity;
import pi.actor.ImageFontText;
import pi.util.TextAlignment;

public class ImageFontTextDemo extends Scene
{
    ImageFontText helloWorld;

    ImageFontText counterText;

    public ImageFontTextDemo()
    {
        backgroundColor("white");
        ImageFont font = new ImageFont("image-font/tetris",
                ImageFontCaseSensitivity.TO_UPPER);
        helloWorld = new ImageFontText(font, "Hello, World.\nHello, Universe");
        AtomicInteger counter = new AtomicInteger();
        counterText = new ImageFontText(font, "0", 10, TextAlignment.RIGHT);
        counterText.position(0, -4);
        add(helloWorld);
        repeat(0.05, () -> {
            counterText.content(String.valueOf(counter.getAndIncrement()));
        });
        add(counterText);
    }

    public static void main(String[] args)
    {
        Controller.start(new ImageFontTextDemo());
    }
}
