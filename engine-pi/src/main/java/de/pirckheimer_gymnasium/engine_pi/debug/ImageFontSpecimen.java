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
        int START_X = -10;
        int i = 0;
        int x = START_X;
        int y = 8;
        for (ImageFontGlyph glyph : font.getGlyphs())
        {
            ImageFontText text = new ImageFontText(font, glyph.getGlyph() + "");
            text.setPosition(x, y);
            scene.add(text);
            scene.addText(glyph.getContent(), 1, "Noto Sans").setPosition(x + 2,
                    y).setColor("gray");
            scene.addText(glyph.getUnicodeName(), 0.3, "Monospaced")
                    .setPosition(x, y - 0.4).setColor("gray");
            scene.addText(glyph.getHexNumber(), 0.3, "Monospaced")
                    .setPosition(x, y - 0.8).setColor("gray");
            x += 4;
            i++;
            if (i % 5 == 0)
            {
                x = START_X;
                y -= 2;
            }
        }
    }
}
