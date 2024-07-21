package de.pirckheimer_gymnasium.engine_pi.debug;

import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.ImageFont;
import de.pirckheimer_gymnasium.engine_pi.actor.ImageFontGlyph;
import de.pirckheimer_gymnasium.engine_pi.actor.ImageFontText;

/**
 * @since 0.27.0
 */
public class ImageFontSpecimen
{
    public ImageFontSpecimen(Scene scene, ImageFont font)
    {


        for(ImageFontGlyph glyph : font.getGlyphs()) {
            new ImageFontText(font, glyph.getGlyph() + "");

        }
    }
}
