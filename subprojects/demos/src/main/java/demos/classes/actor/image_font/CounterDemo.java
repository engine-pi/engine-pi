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
package demos.classes.actor.image_font;

import java.util.concurrent.atomic.AtomicInteger;

import pi.Controller;
import pi.Scene;
import pi.actor.ImageFont;
import pi.actor.ImageFontCaseSensitivity;
import pi.actor.ImageFontText;
import pi.util.TextAlignment;

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/main-classes/actor/image-font.md

/**
 * Demonstriert die Schriftart {@link pi.actor.ImageFont ImageFont} und die
 * Figur {@link pi.actor.ImageFontText} anhand eines <b>Zählers</b>.
 */
public class CounterDemo extends Scene
{
    ImageFontText text;

    public CounterDemo()
    {
        backgroundColor("yellow");
        AtomicInteger counter = new AtomicInteger();
        text = new ImageFontText(
                new ImageFont("image-font/pacman",
                        ImageFontCaseSensitivity.TO_UPPER),
                "0", 5, TextAlignment.RIGHT);
        text.center(-1, 0);
        repeat(0.05, () -> {
            text.content(String.valueOf(counter.getAndIncrement()));
        });
        add(text);
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new CounterDemo(), 400, 300);
    }
}
