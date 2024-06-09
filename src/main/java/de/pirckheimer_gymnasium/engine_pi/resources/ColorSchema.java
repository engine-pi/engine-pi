package de.pirckheimer_gymnasium.engine_pi.resources;

import de.pirckheimer_gymnasium.engine_pi.util.ColorUtil;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Beschreibt ein <b>Farbschema</b>.
 *
 * <p>
 * Diese Klasse ermöglicht es, verschiedene Farben aufeinander abzustimmen,
 * damit sie gut zusammenpassen.
 * </p>
 *
 * <p>
 * Außerdem besteht durch diese Klasse die Möglichkeit, ein anderes Farbschema
 * zu setzen.
 * </p>
 */
public class ColorSchema
{
    /**
     * Die Primärfarbe <b>Gelb</b> (englisch: <b>yellow</b>).
     */
    private Color yellow;

    /**
     * Die Tertiärfarbe <b>Gold</b> (englisch: <b>gold</b>, Mischung aus
     * <b>Gelb</b> und <b>Orange</b>).
     */
    private Color gold;

    /**
     * Die Sekundärfarbe <b>Orange</b> (englisch: <b>orange</b>, Mischung aus
     * <b>Gelb</b> und <b>Rot</b>).
     */
    private Color orange;

    /**
     * Die Tertiärfarbe <b>Ziegelrot</b> (englisch: <b>brick red</b>, Mischung
     * aus <b>Orange</b> und <b>Rot</b>)
     */
    private Color brick;

    /**
     * Die Primärfarbe <b>Rot</b> (englisch: <b>red</b>).
     */
    private Color red;

    /**
     * Die Tertiärfarbe <b>Rosa</b> (englisch: <b>pink</b>, Mischung aus
     * <b>Rot</b> und <b>Violett</b>).
     */
    private Color pink;

    /**
     * Sekundärfarbe <b>Violett</b> (englisch: <b>purple</b>, Mischung aus
     * <b>Rot</b> und <b>Blau</b>).
     */
    private Color purple;

    /**
     * Tertiärfarbe <b>Indigo</b> (englisch: <b>indigo</b>, Mischung aus
     * <b>Violett</b> und <b>Blau</b>).
     */
    private Color indigo;

    /**
     * Die Primärfarbe <b>Blau</b> (englisch: <b>blue</b>).
     */
    private Color blue;

    /**
     * Tertiärfarbe <b>Türkis</b> (englisch: <b>cyan</b>, Mischung aus
     * <b>Blau</b> und <b>Grün</b>).
     */
    private Color cyan;

    /**
     * Die Sekundärfarbe <b>Grün</b> (Mischung aus <b>Gelb</b> und <b>Blau</b>).
     */
    private Color green;

    /**
     * Die Tertiärfarbe <b>Limettengrün</b> (englisch: <b>lime</b>, Mischung aus
     * <b>Geld</b> und <b>Grün</b>).
     */
    private Color lime;

    /**
     * Die Farbe <b>Braun</b> (englisch: <b>brown</b>). <b>Braun</b> kommt nicht
     * im <a href=
     * "https://de.wikipedia.org/wiki/Farbkreis#Ittens_Farbkreis">Farbkreis von
     * Itten</a> vor.
     */
    private Color brown;

    /**
     * Die Farbe <b>Weiß</b> (englisch: <b>white</b>).
     */
    Color white = Color.WHITE;

    /**
     * Die Farbe <b>Grau</b> (englisch: <b>gray</b>).
     */
    Color gray = Color.GRAY;

    /**
     * Die Farbe <b>Schwarz</b> (englisch: <b>black</b>).
     */
    Color black = Color.BLACK;

    /**
     * Erzeugt ein neues Farbschema durch Angabe von den drei Primärfarben.
     *
     * <p>
     * Die Reihenfolge der Farben ist dem <a href=
     * "https://de.wikipedia.org/wiki/Farbkreis#Ittens_Farbkreis">Farbkreis von
     * Itten</a> entnommen.
     * </p>
     *
     * @param yellow Die Primärfarbe <b>Gelb</b>.
     * @param red    Die Primärfarbe <b>Rot</b>.
     * @param blue   Die Primärfarbe <b>Blau</b>.
     */
    public ColorSchema(Color yellow, Color red, Color blue)
    {
        this.yellow = yellow;
        this.red = red;
        this.blue = blue;
    }

    /**
     * Erzeugt ein neues Farbschema durch Angabe von sechs Farben (drei
     * Primärfarben und drei Sekundärfarben).
     *
     * <p>
     * Die Reihenfolge der Farben ist dem <a href=
     * "https://de.wikipedia.org/wiki/Farbkreis#Ittens_Farbkreis">Farbkreis von
     * Itten</a> entnommen.
     * </p>
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

    private Color mix(Color color1, Color color2, double factor)
    {
        return ColorUtil.interpolate(color1, color2, factor);
    }

    private Color mix(Color color1, Color color2)
    {
        return mix(color1, color2, 0.5);
    }

    /**
     * Gibt die Primärfarbe <b>Gelb</b> (englisch: <b>yellow</b>) zurück.
     *
     * @return Die Primärfarbe <b>Gelb</b> (englisch: <b>yellow</b>) .
     */
    public Color getYellow()
    {
        return yellow;
    }

    /**
     * Setzt die Primärfarbe <b>Gelb</b> (englisch: <b>yellow</b>).
     *
     * @param yellow Die Primärfarbe <b>Gelb</b> (englisch: <b>yellow</b>) .
     */
    public void setYellow(Color yellow)
    {
        this.yellow = yellow;
    }

    /**
     * Gibt die Tertiärfarbe <b>Gold</b> (englisch: <b>gold</b>) - eine Mischung
     * aus <b>Gelb</b> und <b>Orange</b> - zurück.
     *
     * @return Die Tertiärfarbe <b>Gold</b> (englisch: <b>gold</b>, Mischung aus
     *         <b>Gelb</b> und <b>Orange</b>).
     */
    public Color getGold()
    {
        if (gold != null)
        {
            return gold;
        }
        return mix(getYellow(), getOrange());
    }

    /**
     * Gibt die Sekundärfarbe <b>Orange</b> (englisch: <b>orange</b>) zurück.
     *
     * <p>
     * <b>Orange</b> ist eine Mischung aus <b>Gelb</b> und <b>Rot</b>.
     * </p>
     *
     * @return Die Sekundärfarbe <b>Orange</b> (englisch: <b>orange</b>,
     *         Mischung aus <b>Gelb</b> und <b>Rot</b>).
     */
    public Color getOrange()
    {
        if (orange != null)
        {
            return orange;
        }
        return mix(getYellow(), getRed());
    }

    /**
     * Gibt die Tertiärfarbe <b>Ziegelrot</b> (englisch: <b>brick red</b>) -
     * eine Mischung aus <b>Orange</b> und <b>Rot</b> - zurück.
     *
     * @return Die Tertiärfarbe <b>Ziegelrot</b> (englisch: <b>brick red</b>,
     *         Mischung aus <b>Orange</b> und <b>Rot</b>).
     */
    public Color getBrick()
    {
        if (brick != null)
        {
            return brick;
        }
        return mix(getOrange(), getRed());
    }

    /**
     * Gibt die Primärfarbe <b>Rot</b> (englisch: <b>red</b>) zurück.
     *
     * @return Die Primärfarbe <b>Rot</b> (englisch: <b>red</b>).
     */
    public Color getRed()
    {
        return red;
    }

    /**
     * Gibt die Tertiärfarbe <b>Rosa</b> (englisch: <b>pink</b>) - eine Mischung
     * aus <b>Rot</b> und <b>Violett</b> - zurück.
     *
     * @return Die Tertiärfarbe <b>Rosa</b> (englisch: <b>pink</b>, Mischung aus
     *         <b>Rot</b> und <b>Violett</b>).
     */
    public Color getPink()
    {
        if (pink != null)
        {
            return pink;
        }
        return mix(getRed(), getPurple());
    }

    /**
     * Gibt die Sekundärfarbe <b>Violett</b> (englisch: <b>purple</b>) zurück.
     *
     * <p>
     * <b>Violett</b> ist eine Mischung aus <b>Rot</b> und <b>Blau</b>.
     * </p>
     *
     * @return Die Sekundärfarbe <b>Violett</b> (englisch: <b>purple</b>,
     *         Mischung aus <b>Rot</b> und <b>Blau</b>).
     */
    public Color getPurple()
    {
        if (purple != null)
        {
            return purple;
        }
        return mix(getRed(), getBlue());
    }

    /**
     * Gibt die Tertiärfarbe <b>Indigo</b> (englisch: <b>indigo</b>) - eine
     * Mischung aus <b>Violett</b> und <b>Blau</b> - zurück.
     *
     * @return Die Tertiärfarbe <b>Indigo</b> (englisch: <b>indigo</b>, Mischung
     *         aus <b>Violett</b> und <b>Blau</b>).
     */
    public Color getIndigo()
    {
        if (indigo != null)
        {
            return indigo;
        }
        return mix(getPurple(), getBlue());
    }

    /**
     * Gibt die Primärfarbe <b>Blau</b> (englisch: <b>blue</b>) zurück.
     *
     * @return Die Primärfarbe <b>Blau</b> (englisch: <b>blue</b>).
     */
    public Color getBlue()
    {
        return blue;
    }

    /**
     * Gibt die Tertiärfarbe <b>Türkis</b> (englisch: <b>cyan</b>) - eine
     * Mischung aus <b>Blau</b> und <b>Grün</b> - zurück.
     *
     * @return Tertiärfarbe <b>Türkis</b> (englisch: <b>cyan</b>, Mischung aus
     *         <b>Blau</b> und <b>Grün</b>).
     */
    public Color getCyan()
    {
        if (cyan != null)
        {
            return cyan;
        }
        return mix(getBlue(), getGreen());
    }

    /**
     * Gibt die Sekundärfarbe <b>Grün</b> (englisch: <b></b>) (Mischung aus
     * <b>Gelb</b> und <b>Blau</b>) zurück.
     *
     * @return Die Sekundärfarbe <b>Grün</b> (Mischung aus <b>Gelb</b> und
     *         <b>Blau</b>).
     */
    public Color getGreen()
    {
        if (green != null)
        {
            return green;
        }
        return mix(getYellow(), getBlue());
    }

    /**
     * Gibt die Tertiärfarbe <b>Limettengrün</b> (englisch: <b>lime</b>) - eine
     * Mischung aus <b>Geld</b> und <b>Grün</b> - zurück.
     *
     * @return Die Tertiärfarbe <b>Limettengrün</b> (englisch: <b>lime</b>,
     *         Mischung aus <b>Geld</b> und <b>Grün</b>).
     */
    public Color getLime()
    {
        if (lime != null)
        {
            return lime;
        }
        return mix(getYellow(), getGreen());
    }

    /**
     * Gibt die Farbe <b>Braun</b> (englisch: <b>brown</b>) zurück.
     *
     * <p>
     * Die Farbe <b>Braun</b> ist eine Mischung aus <b>Rot</b> und <b>Grün</b>,
     * wobei der <b>Rotanteil</b> überwiegt. <b>Braun</b> kommt nicht im
     * <a href=
     * "https://de.wikipedia.org/wiki/Farbkreis#Ittens_Farbkreis">Farbkreis von
     * Itten</a> vor.
     * </p>
     *
     * @return Die Farbe <b>Braun</b> (englisch: <b>brown</b>).
     */
    public Color getBrown()
    {
        if (brown != null)
        {
            return brown;
        }
        return mix(getRed(), getGreen(), 0.35);
    }

    /**
     * Gibt die Farbe <b>Weiß</b> (englisch: <b>white</b>) zurück.
     *
     * @return Die Farbe <b>Weiß</b> (englisch: <b>white</b>).
     */
    public Color getWhite()
    {
        return white;
    }

    /**
     * Gibt die Farbe <b>Grau</b> (englisch: <b>gray</b>) zurück.
     *
     * @return Die Farbe <b>Grau</b> (englisch: <b>gray</b>).
     */
    public Color getGray()
    {
        return gray;
    }

    /**
     * Gibt die Farbe <b>Schwarz</b> (englisch: <b>black</b>) zurück.
     *
     * @return Die Farbe <b>Schwarz</b> (englisch: <b>black</b>).
     */
    public Color getBlack()
    {
        return black;
    }

    /**
     * Erzeugt ein Farbschema nach den Farben
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
