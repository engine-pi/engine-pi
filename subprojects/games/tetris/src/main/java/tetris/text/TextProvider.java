/*
 * Copyright (c) 2024, 2026 Josef Friedrich and contributors.
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
package tetris.text;

import pi.actor.ImageText;
import pi.actor.ImageText.CaseSensitivity;
import pi.annotations.Getter;

import static tetris.Tetris.COLOR_SCHEME_GREEN;

/**
 * @author Josef Friedrich
 */
public class TextProvider
{
    /**
     * Dieser private Konstruktor dient dazu, den öffentlichen Konstruktor zu
     * verbergen. Dadurch ist es nicht möglich, Instanzen dieser Klasse zu
     * erstellen.
     *
     * @throws UnsupportedOperationException Falls eine Instanz der Klasse
     *     erzeugt wird.
     */
    private TextProvider()
    {
        throw new UnsupportedOperationException();
    }

    private static ImageText.Font font;

    @Getter
    public static ImageText.Font font()
    {
        if (font == null)
        {
            font = new ImageText.Font("images/image-font")
                .addMapping('\uE000', "e000_quotation-mark-and-dot")
                .supportsCase(CaseSensitivity.UPPER);
        }
        return font;
    }

    /**
     * Gibt einen vorkonfigurierte Figur der Klasse {@link ImageText} aus.
     *
     * <p>
     * Bei dem Bildertext ist bereits die passende Farbe gesetzt.
     * </p>
     *
     * @return Eine vorkonfigurierte Figur der Klasse {@link ImageText}.
     */
    @Getter
    public static ImageText text()
    {
        return new ImageText(font()).color(COLOR_SCHEME_GREEN.getBlack());
    }
}
