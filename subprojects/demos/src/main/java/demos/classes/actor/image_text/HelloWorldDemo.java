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

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/main-classes/actor/image-text.md

/**
 * Demonstriert die Schriftart {@link Font} und die Figur {@link ImageText}
 * anhand des Textes <b>Hello, World. Hello, Universe</b>.
 */
public class HelloWorldDemo extends Scene
{
    ImageText helloWorld;

    public HelloWorldDemo()
    {
        backgroundColor("green");
        helloWorld = new ImageText(
                new Font("main-classes/actor/image-text/tetris",
                        CaseSensitivity.TO_UPPER),
                "Hello, World.\nHello, Universe");
        helloWorld.center(0, 0);
        add(helloWorld);
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new HelloWorldDemo(), 600, 200);
    }
}
