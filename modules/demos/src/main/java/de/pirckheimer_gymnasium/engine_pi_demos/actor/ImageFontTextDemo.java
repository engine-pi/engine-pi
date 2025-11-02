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
package de.pirckheimer_gymnasium.engine_pi_demos.actor;

import java.util.concurrent.atomic.AtomicInteger;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.ImageFont;
import de.pirckheimer_gymnasium.engine_pi.actor.ImageFontCaseSensitivity;
import de.pirckheimer_gymnasium.engine_pi.actor.ImageFontText;
import de.pirckheimer_gymnasium.engine_pi.util.TextAlignment;

public class ImageFontTextDemo extends Scene
{
    ImageFontText helloWorld;

    ImageFontText counterText;

    public ImageFontTextDemo()
    {
        setBackgroundColor("white");
        ImageFont font = new ImageFont("image-font/tetris",
                ImageFontCaseSensitivity.TO_UPPER);
        helloWorld = new ImageFontText(font, "Hello, World.\nHello, Universe");
        AtomicInteger counter = new AtomicInteger();
        counterText = new ImageFontText(font, "0", 10, TextAlignment.RIGHT);
        counterText.setPosition(0, -4);
        add(helloWorld);
        repeat(0.05, () -> {
            counterText.setContent(String.valueOf(counter.getAndIncrement()));
        });
        add(counterText);
    }

    public static void main(String[] args)
    {
        Game.start(new ImageFontTextDemo());
    }
}
