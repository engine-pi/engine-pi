package de.pirckheimer_gymnasium.engine_pi.actor;

import de.pirckheimer_gymnasium.engine_pi.Scene;

/**
 * Zeichnet in eine Szene ein <b>Schriftmuster</b> ein.
 *
 * @author Josef Friedrich
 *
 * @since 0.27.0
 */
public class ImageFontSpecimen
{
    /**
     * @param scene        Die Szene, in der das Schriftmuster eingezeichnet
     *                     werden soll.
     * @param font         Die Bilderschrift, von der alle Zeichen dargestellt
     *                     werden sollen.
     * @param glyphsPerRow Wie viele Zeichen in einer Zeile dargestellt werden
     *                     sollen.
     * @param x            Die x-Koordinate des linken, oberen Zeichens.
     * @param y            Die y-Koordinate des linken, oberen Zeichens.
     */
    public ImageFontSpecimen(Scene scene, ImageFont font, int glyphsPerRow,
            double x, double y)
    {
        double startX = x;
        int i = 0;
        for (ImageFontGlyph glyph : font.getGlyphs())
        {
            ImageFontText text = new ImageFontText(font, glyph.getGlyph() + "");
            text.setPosition(x, y);
            scene.add(text);
            scene.addText(glyph.getContent(), 1).setPosition(x + 2, y)
                    .setColor("gray");
            scene.addText(glyph.getUnicodeName(), 0.3, "Monospaced")
                    .setPosition(x, y - 0.4).setColor("gray");
            scene.addText(glyph.getHexNumber(), 0.3, "Monospaced")
                    .setPosition(x, y - 0.8).setColor("gray");
            x += 4;
            i++;
            if (i % glyphsPerRow == 0)
            {
                x = startX;
                y -= 2;
            }
        }
    }

    /**
     * @param scene Die Szene, in der das Schriftmuster eingezeichnet werden
     *              soll.
     * @param font  Die Bilderschrift, von der alle Zeichen dargestellt werden
     *              sollen.
     */
    public ImageFontSpecimen(Scene scene, ImageFont font)
    {
        this(scene, font, 5, -10, 8);
    }
}
