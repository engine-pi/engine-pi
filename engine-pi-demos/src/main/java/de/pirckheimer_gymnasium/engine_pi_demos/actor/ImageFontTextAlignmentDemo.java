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

import static de.pirckheimer_gymnasium.engine_pi.util.TextAlignment.CENTER;
import static de.pirckheimer_gymnasium.engine_pi.util.TextAlignment.LEFT;
import static de.pirckheimer_gymnasium.engine_pi.util.TextAlignment.RIGHT;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.ImageFont;
import de.pirckheimer_gymnasium.engine_pi.actor.ImageFontCaseSensitivity;
import de.pirckheimer_gymnasium.engine_pi.actor.ImageFontText;
import de.pirckheimer_gymnasium.engine_pi.util.TextAlignment;

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
 * @see de.pirckheimer_gymnasium.engine_pi.util.TextAlignment
 * @see de.pirckheimer_gymnasium.engine_pi.actor.ImageFontText
 */
public class ImageFontTextAlignmentDemo extends Scene
{
    ImageFont font = new ImageFont("image-font/tetris",
            ImageFontCaseSensitivity.TO_UPPER);

    public ImageFontTextAlignmentDemo()
    {
        getCamera().setMeter(32);
        setBackgroundColor("white");
        createTextLine(3, "Dieser Text ist linksbuendig ausgerichtet.", LEFT);
        createTextLine(-2, "Dieser Text ist zentriert ausgerichtet.", CENTER);
        createTextLine(-7, "Dieser Text ist rechtsbuendig ausgerichtet.",
                RIGHT);
    }

    private void createTextLine(int y, String content, TextAlignment alignment)
    {
        ImageFontText line = new ImageFontText(font, content, 18, alignment);
        line.setPosition(-9, y);
        add(line);
    }

    public static void main(String[] args)
    {
        Game.start(new ImageFontTextAlignmentDemo());
    }
}
