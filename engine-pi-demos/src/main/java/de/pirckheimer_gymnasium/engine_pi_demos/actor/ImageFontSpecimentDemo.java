package de.pirckheimer_gymnasium.engine_pi_demos.actor;

import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.ImageFont;
import de.pirckheimer_gymnasium.engine_pi.debug.ImageFontSpecimen;

/**
 * @since 0.27.0
 */
public class ImageFontSpecimentDemo extends Scene
{
    public ImageFontSpecimentDemo()
    {
        ImageFont tetris = new ImageFont("image-font/tetris");
        new ImageFontSpecimen(this, tetris);
    }
}
