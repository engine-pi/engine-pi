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
package tetris;

import java.awt.Color;

import pi.util.ColorUtil;

/**
 * Das klassische Gameboy-Spiel hat ein <b>Farbschema</b>, das aus nur <b>vier
 * Farben</b> besteht.
 *
 * <p>
 * Wir verwenden als Farbnamen <em>Weiß</em> ({@code white}), <em>Hell</em>
 * ({@code light}), <em>Dunkel</em> ({@code dark}) und <em>Schwarz</em>
 * ({@code black}), obwohl diese Farbnamen nicht den tatsächlichen Farben
 * entsprechen.
 * </p>
 */
public class ColorScheme
{
    private final Color[] colors;

    /**
     * Erzeugt eine <b>neues Farbschema</b> durch Angabe von
     * <b>{@link Color}-Objekten</b>.
     *
     * @param white Die Farbe <em>Weiß</em> ({@code white}).
     * @param light Die Farbe <em>Hell</em> ({@code light}).
     * @param dark Die Farbe <em>Dunkel</em> ({@code dark}).
     * @param black Die Farbe <em>Schwarz</em> ({@code black}).
     */
    public ColorScheme(Color white, Color light, Color dark, Color black)
    {
        colors = new Color[4];
        colors[0] = white;
        colors[1] = light;
        colors[2] = dark;
        colors[3] = black;
    }

    /**
     * Erzeugt eine <b>neues Farbschema</b> durch Angabe von Zeichenketten, die
     * die Farbe in <b>hexadezimaler Notation</b> codieren.
     *
     * @param white Die Farbe <em>Weiß</em> ({@code white}) als Zeichenkette in
     *     die in <b>hexadezimaler Notation</b>.
     * @param light Die Farbe <em>Hell</em> ({@code light}) als Zeichenkette in
     *     die in <b>hexadezimaler Notation</b>.
     * @param dark Die Farbe <em>Dunkel</em> ({@code dark}) als Zeichenkette in
     *     die in <b>hexadezimaler Notation</b>.
     * @param black Die Farbe <em>Schwarz</em> ({@code black}) als Zeichenkette
     *     in die in <b>hexadezimaler Notation</b>.
     */
    public ColorScheme(String white, String light, String dark, String black)
    {
        this(ColorUtil.decode(white), ColorUtil.decode(light),
                ColorUtil.decode(dark), ColorUtil.decode(black));
    }

    /**
     * Gibt ein Feld aller vier Farben zurück.
     *
     * <ol>
     * <li><em>Weiß</em> ({@code white})</li>
     * <li><em>Hell</em> ({@code light})</li>
     * <li><em>Dunkel</em> ({@code dark})</li>
     * <li><em>Schwarz</em> ({@code black})</li>
     * </ol>
     *
     * @return Ein Feld aller vier Farben.
     */
    public Color[] getColors()
    {
        return colors;
    }

    /**
     * Gibt die Farbe <em>Weiß</em> ({@code white}) zurück.
     *
     * @return Die Farbe <em>Weiß</em> ({@code white}).
     */
    public Color getWhite()
    {
        return colors[0];
    }

    /**
     * Gibt die Farbe <em>Hell</em> ({@code light}) zurück.
     *
     * @return Die Farbe <em>Hell</em> ({@code light}).
     */
    public Color getLight()
    {
        return colors[1];
    }

    /**
     * Gibt die Farbe <em>Dunkel</em> ({@code dark}) zurück.
     *
     * @return Die Farbe <em>Dunkel</em> ({@code dark}).
     */
    public Color getDark()
    {
        return colors[2];
    }

    /**
     * Gibt die Farbe <em>Schwarz</em> ({@code black}) zurück.
     *
     * @return Die Farbe <em>Schwarz</em> ({@code black}).
     */
    public Color getBlack()
    {
        return colors[3];
    }

    /**
     * Erzeugt ein graues Farbschema. Screenshots, die mit dem Emulator Gameboy
     * Advanced als Graustufenbilder exportiert wurden, haben diese vier Farben.
     *
     * @return Ein graues Farbschema.
     */
    public static ColorScheme createGrayColorScheme()
    {
        return new ColorScheme("#ffffff", "#adadad", "#525252", "#000000");
    }

    /**
     * Erzeugt ein grünes Farbschema. Diese vier grünen Farben wurden mithilfe
     * eines Youtube-Videos ermittelt, das das Gameboy-Display abgefilmt zeigt.
     *
     * @return Ein grünes Farbschema.
     */
    public static ColorScheme createGreenColorScheme()
    {
        return new ColorScheme("#aaaa00", "#556633", "#335544", "#223322");
    }
}
