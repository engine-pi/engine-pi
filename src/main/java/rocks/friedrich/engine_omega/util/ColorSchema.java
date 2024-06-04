package rocks.friedrich.engine_omega.util;

import java.awt.Color;

/**
 * Ein Farbschema, damit die verschiedenen Farben aufeinander abgestimmt werden
 * können und gut zusamenpassen.
 *
 * Außerdem besteht durch diese Klasse die Möglichkeit, ein anderes Farbschema
 * zu setzen.
 */
public class ColorSchema
{
    /**
     * Die Primärfarbe <b>Gelb</b>.
     */
    private Color yellow;

    /**
     * Die Sekundärfarbe <b>Orange</b> (Mischung aus <b>Gelb</b> und
     * <b>Rot</b>).
     */
    private Color orange;

    /**
     * Die Primärfarbe <b>Rot</b>.
     */
    private Color red;

    /**
     * Die Sekundärfarbe <b>Violett</b> (Mischung aus <b>Rot</b> und
     * <b>Blau</b>).
     */
    private Color purple;

    /**
     * Die Primärfarbe <b>Blau</b>.
     */
    private Color blue;

    /**
     * Die Sekundärfarbe <b>Grün</b> (Mischung aus <b>Gelb</b> und <b>Blau</b>).
     */
    private Color green;

    /**
     * Die Nicht-Farbe <b>Weiß</b> (nach Itten).
     */
    Color white = Color.WHITE;

    /**
     * Die Nicht-Farbe <b>Weiß</b> (nach Itten).
     */
    Color black = Color.BLACK;

    /**
     * Die Reihenfolge der Farben ist dem <a href=
     * "https://de.wikipedia.org/wiki/Farbkreis#/media/Datei:Farbkreis_Itten_1961.svg">Farbkreis
     * von Itten</a> entnommen.
     *
     * @param yellow Die Primärfarbe <b>Gelb</b>.
     * @param orange Die Sekundärfarbe <b>Orange</b> (Mischung aus <b>Gelb</b>
     *               und <b>Rot</b>).
     * @param red    Die Primärfarbe <b>Rot</b>.
     * @param purple Die Sekundärfarbe <b>Violett</b> (Mischung aus <b>Rot</b>
     *               und <b>Blau</b>).
     * @param blue   Die Primärfarbe <b>Blau</b>.
     * @param green  Die Sekundärfarbe <b>Grün</b> (Mischung aus <b>Gelb</b> und
     *               <b>Blau</b>).
     */
    public ColorSchema(Color yellow, Color orange, Color red, Color purple,
            Color blue, Color green)
    {
        this.yellow = yellow;
        this.orange = orange;
        this.red = red;
        this.purple = purple;
        this.blue = blue;
        this.green = green;
    }

    /**
     * Gibt die Primärfarbe <b>Gelb</b> zurück.
     *
     * @returns Die Primärfarbe <b>Gelb</b>.
     */
    public Color getYellow()
    {
        return yellow;
    }

    /**
     * Gibt die Sekundärfarbe <b>Orange</b> (Mischung aus <b>Gelb</b> und
     * <b>Rot</b>). zurück.
     *
     * @returns Die Sekundärfarbe <b>Orange</b> (Mischung aus <b>Gelb</b> und
     *          <b>Rot</b>).
     */
    public Color getOrange()
    {
        return orange;
    }

    /**
     * Gibt die Primärfarbe <b>Rot</b> zurück.
     *
     * @returns Die Primärfarbe <b>Rot</b>.
     */
    public Color getRed()
    {
        return red;
    }

    /**
     * Gibt die Sekundärfarbe <b>Violett</b> zurück.
     *
     * @returns Die Sekundärfarbe <b>Violett</b> (Mischung aus <b>Rot</b> und
     *          <b>Blau</b>)..
     */
    public Color getPurple()
    {
        return purple;
    }

    /**
     * Gibt die Primärfarbe <b>Blau</b> zurück.
     *
     * @returns Die Primärfarbe <b>Blau</b>.
     */
    public Color getBlue()
    {
        return blue;
    }

    /**
     * Gibt die Sekundärfarbe <b>Grün</b> (Mischung aus <b>Gelb</b> und
     * <b>Blau</b>) zurück.
     *
     * @returns Die Sekundärfarbe <b>Grün</b> (Mischung aus <b>Gelb</b> und
     *          <b>Blau</b>).
     */
    public Color getGreen()
    {
        return green;
    }

    /**
     * Erzeugt ein Farbschema nach dem Farben
     * der<a href="https://developer.gnome.org/hig/reference/palette.html">GNOME
     * Human Interface Guidelines</a>.
     */
    public static ColorSchema getGnomeColorSchema()
    {
        return new ColorSchema(
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
                new Color(51, 209, 122));
    }
}
