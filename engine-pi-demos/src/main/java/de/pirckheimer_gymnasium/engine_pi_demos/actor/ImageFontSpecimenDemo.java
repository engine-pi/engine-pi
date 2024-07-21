package de.pirckheimer_gymnasium.engine_pi_demos.actor;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.ImageFont;
import de.pirckheimer_gymnasium.engine_pi.actor.ImageFontSpecimen;

/**
 * Demonstriert die Klasse {@link ImageFontSpecimen}.
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
