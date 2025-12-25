package de.pirckheimer_gymnasium.demos.classes.actor;

import pi.Game;
import pi.Scene;
import pi.actor.ImageFont;
import pi.actor.ImageFontSpecimen;

/**
 * Demonstriert die Klasse {@link ImageFontSpecimen}.
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
public class ImageFontSpecimenDemo extends Scene
{
    public ImageFontSpecimenDemo(String fontName)
    {
        setBackgroundColor("white");
        ImageFont tetris = new ImageFont("image-font/" + fontName);
        new ImageFontSpecimen(this, tetris);
    }

    public static void main(String[] args)
    {
        Game.start(new ImageFontSpecimenDemo("tetris"), 1000, 1000);
    }
}
