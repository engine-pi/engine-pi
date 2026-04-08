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
import pi.actor.ImageText.Font;
import pi.actor.ImageText.Specimen;

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/main-classes/actor/image-text.md

/**
 * Demonstriert die Klasse {@link Specimen}.
 *
 * <h2>tetris</h2>
 *
 * <p>
 * <img alt="ImageFontSpecimenTetris" src=
 * "https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/actor/ImageFontSpecimenTetris.png">
 * </p>
 *
 * <h2>pacman</h2>
 *
 * <p>
 * <img alt="ImageFontSpecimenPacman" src=
 * "https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/actor/ImageFontSpecimenPacman.png">
 * </p>
 *
 * <h2>space-invaders</h2>
 *
 * <p>
 * <img alt="ImageFontSpecimenSpaceInvaders" src=
 * "https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/actor/ImageFontSpecimenSpaceInvaders.png">
 * </p>
 *
 * @since 0.27.0
 */
public class SpecimenDemo extends Scene
{
    public SpecimenDemo(String fontName)
    {
        backgroundColor("white");
        Font tetris = new Font("image-font/" + fontName);
        new Specimen(this, tetris);
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new SpecimenDemo("tetris"), 1000, 1000);
    }
}
