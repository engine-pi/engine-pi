/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2024, 2025 Josef Friedrich and contributors.
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
package pi.resources.color;

import java.awt.Color;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/docs/manual/resources/colors.md

/**
 * Dieser Aufzählungstyp sammelt die Farbschemata, die die Engine Pi anbietet.
 *
 * <p>
 * Standardmäßig verwendet die Engine das
 * {@link PredefinedColorScheme#GNOME}-Farbschema.
 * </p>
 *
 * @author Josef Friedrich
 */
public enum PredefinedColorScheme
{
    /**
     * Ein Farbschema nach den Farben der
     * <a href= "https://developer.gnome.org/hig/reference/palette.html">GNOME
     * Human Interface Guidelines</a>.
     */
    GNOME(new ColorScheme("Gnome",

            // yellow3
            new Color(246, 211, 45),

            // organe3
            new Color(255, 120, 0),

            // red3
            new Color(224, 27, 36),

            // purple3
            new Color(145, 65, 172),

            // blue3
            new Color(53, 132, 228),

            // green3
            new Color(51, 209, 122),

            // brown3
            new Color(152, 106, 68))),

    /**
     * Ein Farbschema, das einige vordefinierten statischen Farbattribute der
     * JAVA-{@link Color}-Klasse verwendet.
     */
    JAVA(new ColorScheme("Java", Color.YELLOW, Color.RED, Color.BLUE)

    // Sekundärfarben
    // Orange passt nicht in das Schema, viel zu hell.
    // .orange(Color.ORANGE)

            .green(Color.GREEN)

            // Tertiärfarben
            .blueGreen(Color.CYAN)

            // Pink passt nicht in das Schema.
            // .redPurple(Color.PINK)

            .redPurple(Color.MAGENTA)

            // Andere
            .gray(Color.GRAY)),

    /**
     * Ein Farbschema nach den Farben des <a href=
     * "https://m2.material.io/design/color/the-color-system.html#tools-for-picking-colors">Android
     * Material-Designs </a>.
     */
    ANDROID(new ColorScheme("Android")

    // Yellow 500
            .yellow("#FFEB3B")

            // Amber 500
            .yellowOrange("#FFC107")

            // Orange 500
            .orange("#FF9800")

            // Deep Orange 500
            .redOrange("#FF5722")

            // Red 500
            .red("#F44336")

            // Pink 500
            .redPurple("#E91E63")

            // Purple 500
            .purple("#9C27B0")

            // Deep Purple 500
            .bluePurple("#673AB7")

            // Blue 500
            .blue("#2196F3")

            // Teal 500
            .blueGreen("#009688")

            // Green 500
            .green("#4CAF50")

            // Lime 500
            .yellowGreen("#CDDC39")

            // Brown 500
            .brown("#795548")

            // Gray 500
            .gray("#9E9E9E")),

    /**
     * Ein Farbschema nach den Farben der <a href=
     * "https://developer.apple.com/design/human-interface-guidelines/color">IOS
     * Human Interface Guidelines</a> von Apple.
     */
    IOS(new ColorScheme("iOS")

    // Yellow Default (Light)
            .yellow(255, 204, 0)

            // Orange Accessible (Dark)
            .yellowOrange(255, 179, 64)

            // Orange Default (Light)
            .orange(255, 149, 0)

            // Red Default (Light)
            .red(255, 59, 48)

            // Pink Default (Light)
            .redPurple(255, 45, 85)

            // Purple Default (Light)
            .purple(175, 82, 222)

            // Indigo Default (Light)
            .bluePurple(88, 86, 214)

            // Blue Default (Light)
            .blue(0, 122, 255)

            // Teal Default (Light)
            .blueGreen(48, 176, 199)

            // Green Default (Light)
            .green(52, 199, 89)

            // Brown Default (Light)
            .brown(162, 132, 94)

            // systemGray Default (Light)
            .gray(142, 142, 147)),

    /**
     * Ein Farbschema nach der
     * <a href= "https://tailwindcss.com/docs/colors">Farbpallette</a> von
     * <a href="https://en.wikipedia.org/wiki/Tailwind_CSS">Tailwind CSS</a>.
     */
    TAILWIND(new ColorScheme("Tailwind CSS")

    // Yellow 500 oklch(79.5% 0.184 86.047)
            .yellow(239, 177, 0)

            // Amber 500 oklch(76.9% 0.188 70.08)
            .yellowOrange(253, 154, 0)

            // Orange 500 oklch(70.5% 0.213 47.604)
            .orange(255, 105, 0)

            // .redOrange(1,1,1)

            // Red 500 oklch(63.7% 0.237 25.331)
            .red(251, 44, 54)

            // Ping 500 oklch(65.6% 0.241 354.308)
            .redPurple(246, 51, 154)

            // Purple 500 oklch(62.7% 0.265 303.9)
            .purple(173, 70, 255)

            //.bluePurple(88, 86, 214)

            // Blue 500 oklch(62.3% 0.214 259.815)
            .blue(43, 127, 255)

            // Teal 500 oklch(70.4% 0.14 182.503)
            .blueGreen(0, 187, 167)

            // Green 500 oklch(72.3% 0.219 149.579)
            .green(0, 201, 81)

            // Lime 500 oklch(76.8% 0.233 130.85)
            .yellowGreen(124, 207, 0)

            //.brown(162, 132, 94)

            // oklch(70.7% 0.022 261.325)
            .gray(153, 161, 175));

    private ColorScheme scheme;

    private PredefinedColorScheme(ColorScheme scheme)
    {
        this.scheme = scheme;
    }

    /**
     * Gibt das <b>Farbschema</b> zurück.
     *
     * @return Das <b>Farbschema</b>.
     */
    public ColorScheme getScheme()
    {
        return scheme;
    }
}
