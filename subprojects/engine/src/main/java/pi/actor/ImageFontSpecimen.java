/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
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
package pi.actor;

import pi.Scene;
import pi.Text;

/**
 * Zeichnet in eine Szene ein <b>Schriftmuster</b> ein.
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
 * @author Josef Friedrich
 *
 * @since 0.27.0
 */
public class ImageFontSpecimen
{
    /**
     * @param scene Die Szene, in der das Schriftmuster eingezeichnet werden
     *     soll.
     * @param font Die Bilderschrift, von der alle Zeichen dargestellt werden
     *     sollen.
     * @param glyphsPerRow Wie viele Zeichen in einer Zeile dargestellt werden
     *     sollen.
     * @param x Die x-Koordinate des linken, oberen Zeichens.
     * @param y Die y-Koordinate des linken, oberen Zeichens.
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
            scene.add(new Text(glyph.getContent(), 1).setPosition(x + 2, y)
                    .setColor("gray"));
            scene.add(new Text(glyph.getUnicodeName(), 0.3, "Monospaced")
                    .setPosition(x, y - 0.4).setColor("gray"));
            scene.add(new Text(glyph.getHexNumber(), 0.3, "Monospaced")
                    .setPosition(x, y - 0.8).setColor("gray"));
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
     *     soll.
     * @param font Die Bilderschrift, von der alle Zeichen dargestellt werden
     *     sollen.
     */
    public ImageFontSpecimen(Scene scene, ImageFont font)
    {
        this(scene, font, 5, -10, 8);
    }
}
