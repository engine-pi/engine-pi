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

import java.util.concurrent.atomic.AtomicInteger;

import pi.Controller;
import pi.Scene;
import pi.actor.ImageText;
import pi.actor.ImageText.Font;
import pi.graphics.boxes.HAlign;

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/main-classes/actor/image-text.md

/**
 * Demonstriert die Schriftart {@link Font} und die Figur {@link ImageText
 * ImageText} anhand eines <b>Zählers</b>.
 */
public class CounterDemo extends Scene
{
    ImageText text;

    public CounterDemo()
    {
        backgroundColor("yellow");
        AtomicInteger counter = new AtomicInteger();
        text = new ImageText(
                new ImageText.Font("main-classes/actor/image-text/pacman"))
                    .content("0")
                    .lineWidth(5)
                    .hAlign(HAlign.RIGHT)
                    .pixelMultiplication(8);
        // Um die toString-Methode zu testen
        text.center(-1, 0).debug();
        repeat(0.05,
            () -> text.content(String.valueOf(counter.getAndIncrement())));
        add(text);
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new CounterDemo(), 400, 300);
    }
}
