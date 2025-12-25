package pi.resources;

import java.awt.Color;

/**
 * Dieser Aufzählungstyp sammelt die Farbschemata, die die Engine Pi anbietet.
 * Standardmäßig verwendet die Engine das
 * {@link ColorSchemeSelection#GNOME}-Farbschema.
 *
 * @author Josef Friedrich
 */
public enum ColorSchemeSelection
{
    /**
     * Ein Farbschema nach den Farben der
     * <a href= "https://developer.gnome.org/hig/reference/palette.html">GNOME
     * Human Interface Guidelines</a>.
     */
    GNOME(new ColorScheme(
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
    JAVA(new ColorScheme(Color.YELLOW, Color.RED, Color.BLUE)// Sekundärfarben
    // Orange passt nicht in das Schema, viel zu hell.
    // .setOrange(Color.ORANGE)
            .setGreen(Color.GREEN)// Tertiärfarben
            .setBlueGreen(Color.CYAN)// Pink passt nicht in das Schema.
            // .setRedPurple(Color.PINK)
            .setRedPurple(Color.MAGENTA)// Andere
            .setGray(Color.GRAY)),
    /**
     * Ein Farbschema nach den Farben des <a href=
     * "https://m2.material.io/design/color/the-color-system.html#tools-for-picking-colors">Android
     * Material-Designs </a>.
     */
    ANDROID(new ColorScheme()// Yellow 500
            .setYellow("#FFEB3B")// Amber 500
            .setYellowOrange("#FFC107")// Orange 500
            .setOrange("#FF9800")// Deep Orange 500
            .setRedOrange("#FF5722")// Red 500
            .setRed("#F44336")// Pink 500
            .setRedPurple("#E91E63")// Purple 500
            .setPurple("#9C27B0")// Deep Purple 500
            .setBluePurple("#673AB7")// Blue 500
            .setBlue("#2196F3")// Teal 500
            .setBlueGreen("#009688")// Green 500
            .setGreen("#4CAF50")// Lime 500
            .setYellowGreen("#CDDC39")// Brown 500
            .setBrown("#795548")// Gray 500
            .setGray("#9E9E9E")),
    /**
     * Ein Farbschema nach den Farben der <a href=
     * "https://developer.apple.com/design/human-interface-guidelines/color">IOS
     * Human Interface Guidelines</a> von Apple.
     */
    IOS(new ColorScheme()// Yellow Default (Light)
            .setYellow(255, 204, 0)// Orange Accessible (Dark)
            .setYellowOrange(255, 179, 64)// Orange Default (Light)
            .setOrange(255, 149, 0)// Red Default (Light)
            .setRed(255, 59, 48)// Pink Default (Light)
            .setRedPurple(255, 45, 85)// Purple Default (Light)
            .setPurple(175, 82, 222)// Indigo Default (Light)
            .setBluePurple(88, 86, 214)// Blue Default (Light)
            .setBlue(0, 122, 255)// Teal Default (Light)
            .setBlueGreen(48, 176, 199)// Green Default (Light)
            .setGreen(52, 199, 89)// Brown Default (Light)
            .setBrown(162, 132, 94)// systemGray Default (Light)
            .setGray(142, 142, 147));

    private ColorScheme scheme;

    private ColorSchemeSelection(ColorScheme scheme)
    {
        this.scheme = scheme;
    }

    /**
     * Gibt das Farbschema zurück.
     *
     * @return Das Farbschema.
     */
    public ColorScheme getScheme()
    {
        return scheme;
    }
}
