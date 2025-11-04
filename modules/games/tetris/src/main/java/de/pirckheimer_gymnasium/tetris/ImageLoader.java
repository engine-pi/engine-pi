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
package de.pirckheimer_gymnasium.tetris;

import static de.pirckheimer_gymnasium.tetris.Tetris.COLOR_SCHEME_GRAY;
import static de.pirckheimer_gymnasium.tetris.Tetris.COLOR_SCHEME_GREEN;

import java.awt.image.BufferedImage;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.actor.Image;
import de.pirckheimer_gymnasium.engine_pi.util.ImageUtil;

/**
 * Bereitet die Bilder für die Verwendung in Tetris vor.
 */
public class ImageLoader
{
    /**
     * Gibt ein vergrößertes und eingefärbtes Bild zurück.
     *
     * <p>
     * Die Ausgangsbilder haben als Farben vier verschiedene Grautöne bzw. zwei
     * Grautöne und schwarz und weiß. Mit Hilfe dieser Methode ist es möglich,
     * die Bilder z. B. grünlich einzufärben, sodass sie dem klassischen
     * Gameboy-Farben ähneln. So müssen wir uns nicht für ein bestimmtes
     * Farbschema entscheiden und dann viele Bilddateien erstellen, die dann
     * wieder geändert werden müssten, wenn wir ein anderes Fahrschema nutzen
     * wollen.
     * </p>
     *
     * @param pathname Der relative Pfad zu {@code src/main/resources}.
     *
     * @return Das vergrößerte und eingefärbte Bild.
     */
    public static Image get(String pathname)
    {
        BufferedImage image = Resources.images.get(pathname);
        image = ImageUtil.replaceColors(image, COLOR_SCHEME_GRAY.getColors(),
                COLOR_SCHEME_GREEN.getColors());
        return new Image(image,
                Tetris.BLOCK_SIZE * Game.getPixelMultiplication());
    }
}
