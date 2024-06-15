package de.pirckheimer_gymnasium.engine_pi.resources;

import java.awt.Color;
import java.util.Map.Entry;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.util.ColorUtil;

/**
 * Beschreibt ein <b>Farbschema</b>.
 *
 * <p>
 * Diese Klasse ermöglicht es, verschiedene Farben aufeinander abzustimmen,
 * damit sie gut zusammenpassen. Außerdem besteht durch diese Klasse die
 * Möglichkeit, ein anderes Farbschema zu setzen.
 * </p>
 *
 * <p>
 * Diese Klasse ist inspiriert von dem Farbkreis von Itten: Aus den drei
 * Primärfarben Rot, Gelb und Blau lassen sich weitere Sekundärfarben (Orange,
 * Violett und Grün) mischen. Sechs „Tertiärfarben“ erweitern Ittens Farbkreis
 * auf insgesamt zwölf Farben.
 * </p>
 *
 *
 * <img alt="Farbkreis von Itten" src=
 * "https://upload.wikimedia.org/wikipedia/commons/b/b9/Farbkreis_Itten_1961.svg">
 *
 * <p>
 * Ein Farbschema kann durch eine unterschiedliche Anzahl an Ausgangsfarben
 * erstellt werden. Im einfachsten Fall genügen drei Primärfarben für ein neues
 * Farbschema. Die nicht spezifizierten Farben werden dann aus den
 * spezifizierten Farben gemischt. Da jede Farbe auch einen Setter besitzt, kann
 * jede Farbe auch explizit gesetzt werden. Dadurch enfällt der Mischvorgang.
 * </p>
 *
 * Die zwölf Farben nach dem Farbkreis von Itten:
 *
 * <ol>
 * <li>{@code yellow} (<b>Gelb</b>)</li>
 * <li>{@code gold} (<b>Gold</b>)</li>
 * <li>{@code orange} (<b>Orange</b>)</li>
 * <li>{@code brick} (<b>Ziegelrot</b>)</li>
 * <li>{@code red} (<b>Rot</b>)</li>
 * <li>{@code pink} (<b>Rosa</b>)</li>
 * <li>{@code purple} (<b>Violett</b>)</li>
 * <li>{@code indigo} (<b>Indigo</b>)</li>
 * <li>{@code blue} (<b>Blau</b>)</li>
 * <li>{@code cyan} (<b>Türkis</b>)</li>
 * <li>{@code green} (<b>Grün</b>)</li>
 * <li>{@code lime} (<b>Limettengrün</b>)</li>
 * </ol>
 *
 * Diese Farben sind ebenfalls im Farbschema enthalten (gehören aber nicht zum
 * Farbkreis von Itten)
 *
 * <ul>
 * <li>{@code brown} (<b>Braun</b>)</li>
 * <li>{@code white} (<b>Weiß</b>)</li>
 * <li>{@code gray} (<b>Grau</b>)</li>
 * <li>{@code black} (<b>Black</b>)</li>
 * </ul>
 *
 */
public class ColorScheme
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
     * <b>Gelb</b> und <b>Grün</b>).
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
    public ColorScheme(Color yellow, Color red, Color blue)
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
    public ColorScheme(Color yellow, Color orange, Color red, Color purple,
            Color blue, Color green)
    {
        this(yellow, red, blue);
        this.orange = orange;
        this.purple = purple;
        this.green = green;
    }

    /**
     * Erzeugt ein neues Farbschema durch Angabe von sieben Farben (drei
     * Primärfarben und drei Sekundärfarben und Braun).
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
     * @param brown  Die Farbe <b>Braun</b> (englisch: <b>brown</b>).
     */
    public ColorScheme(Color yellow, Color orange, Color red, Color purple,
            Color blue, Color green, Color brown)
    {
        this(yellow, orange, red, purple, blue, green);
        this.brown = brown;
    }

    private Color mix(Color color1, Color color2, double factor)
    {
        return ColorUtil.interpolate(color1, color2, factor);
    }

    private Color mix(Color color1, Color color2)
    {
        return mix(color1, color2, 0.5);
    }

    private Color decode(String color)
    {
        return ColorUtil.decode(color);
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
     * @param yellow Die Primärfarbe <b>Gelb</b> (englisch: <b>yellow</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setYellow(Color yellow)
    {
        this.yellow = yellow;
        return this;
    }

    /**
     * Setzt die Primärfarbe <b>Gelb</b> (englisch: <b>yellow</b>) in
     * hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @param yellow Die Primärfarbe <b>Gelb</b> (englisch: <b>yellow</b>) in
     *               hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setYellow(String yellow)
    {
        this.yellow = decode(yellow);
        return this;
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
        if (gold == null)
        {
            gold = mix(getYellow(), getOrange());
        }
        return gold;
    }

    /**
     * Setzt die Tertiärfarbe <b>Gold</b> (englisch: <b>gold</b>, Mischung aus
     * <b>Gelb</b> und <b>Orange</b>).
     *
     * @param gold Die Tertiärfarbe <b>Gold</b> (englisch: <b>gold</b>, Mischung
     *             aus <b>Gelb</b> und <b>Orange</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setGold(Color gold)
    {
        this.gold = gold;
        return this;
    }

    /**
     * Setzt die Tertiärfarbe <b>Gold</b> (englisch: <b>gold</b>, Mischung aus
     * <b>Gelb</b> und <b>Orange</b>) in hexadezimaler Codierung (z. B.
     * {@code #ff0000}).
     *
     * @param gold Die Tertiärfarbe <b>Gold</b> (englisch: <b>gold</b>, Mischung
     *             aus <b>Gelb</b> und <b>Orange</b>) in hexadezimaler Codierung
     *             (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setGold(String gold)
    {
        this.gold = decode(gold);
        return this;
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
        if (orange == null)
        {
            orange = mix(getYellow(), getRed());
        }
        return orange;
    }

    /**
     * Setzt die Sekundärfarbe <b>Orange</b> (englisch: <b>orange</b>, Mischung
     * aus <b>Gelb</b> und <b>Rot</b>).
     *
     * @param orange Die Sekundärfarbe <b>Orange</b> (englisch: <b>orange</b>,
     *               Mischung aus <b>Gelb</b> und <b>Rot</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setOrange(Color orange)
    {
        this.orange = orange;
        return this;
    }

    /**
     * Setzt die Sekundärfarbe <b>Orange</b> (englisch: <b>orange</b>, Mischung
     * aus <b>Gelb</b> und <b>Rot</b>) in hexadezimaler Codierung (z. B.
     * {@code #ff0000}).
     *
     * @param orange Die Sekundärfarbe <b>Orange</b> (englisch: <b>orange</b>,
     *               Mischung aus <b>Gelb</b> und <b>Rot</b>) in hexadezimaler
     *               Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setOrange(String orange)
    {
        this.orange = decode(orange);
        return this;
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
        if (brick == null)
        {
            brick = mix(getOrange(), getRed());
        }
        return brick;
    }

    /**
     * Setzt die Tertiärfarbe <b>Ziegelrot</b> (englisch: <b>brick red</b>,
     * Mischung aus <b>Orange</b> und <b>Rot</b>).
     *
     * @param brick Die Tertiärfarbe <b>Ziegelrot</b> (englisch: <b>brick
     *              red</b>, Mischung aus <b>Orange</b> und <b>Rot</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setBrick(Color brick)
    {
        this.brick = brick;
        return this;
    }

    /**
     * Setzt die Tertiärfarbe <b>Ziegelrot</b> (englisch: <b>brick red</b>,
     * Mischung aus <b>Orange</b> und <b>Rot</b>) in hexadezimaler Codierung (z.
     * B. {@code #ff0000}).
     *
     * @param brick Die Tertiärfarbe <b>Ziegelrot</b> (englisch: <b>brick
     *              red</b>, Mischung aus <b>Orange</b> und <b>Rot</b>) in
     *              hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setBrick(String brick)
    {
        this.brick = decode(brick);
        return this;
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
     * Setzt die Primärfarbe <b>Rot</b> (englisch: <b>red</b>).
     *
     * @param red Die Primärfarbe <b>Rot</b> (englisch: <b>red</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setRed(Color red)
    {
        this.red = red;
        return this;
    }

    /**
     * Setzt die Primärfarbe <b>Rot</b> (englisch: <b>red</b>) in hexadezimaler
     * Codierung (z. B. {@code #ff0000}).
     *
     * @param red Die Primärfarbe <b>Rot</b> (englisch: <b>red</b>) in
     *            hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setRed(String red)
    {
        this.red = decode(red);
        return this;
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
        if (pink == null)
        {
            pink = mix(getRed(), getPurple());
        }
        return pink;
    }

    /**
     * Setzt die Tertiärfarbe <b>Rosa</b> (englisch: <b>pink</b>, Mischung aus
     * <b>Rot</b> und <b>Violett</b>).
     *
     * @param pink Die Tertiärfarbe <b>Rosa</b> (englisch: <b>pink</b>, Mischung
     *             aus <b>Rot</b> und <b>Violett</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setPink(Color pink)
    {
        this.pink = pink;
        return this;
    }

    /**
     * Setzt die Tertiärfarbe <b>Rosa</b> (englisch: <b>pink</b>, Mischung aus
     * <b>Rot</b> und <b>Violett</b>) in hexadezimaler Codierung (z. B.
     * {@code #ff0000}).
     *
     * @param pink Die Tertiärfarbe <b>Rosa</b> (englisch: <b>pink</b>, Mischung
     *             aus <b>Rot</b> und <b>Violett</b>) in hexadezimaler Codierung
     *             (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setPink(String pink)
    {
        this.pink = decode(pink);
        return this;
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
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public Color getPurple()
    {
        if (purple == null)
        {
            return purple = mix(getRed(), getBlue());
        }
        return purple;
    }

    /**
     * Setzt die Sekundärfarbe <b>Violett</b> (englisch: <b>purple</b>, Mischung
     * aus <b>Rot</b> und <b>Blau</b>).
     *
     * @param purple Die Sekundärfarbe <b>Violett</b> (englisch: <b>purple</b>,
     *               Mischung aus <b>Rot</b> und <b>Blau</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setPurple(Color purple)
    {
        this.purple = purple;
        return this;
    }

    /**
     * Setzt die Sekundärfarbe <b>Violett</b> (englisch: <b>purple</b>, Mischung
     * aus <b>Rot</b> und <b>Blau</b>) in hexadezimaler Codierung (z. B.
     * {@code #ff0000}).
     *
     * @param purple Die Sekundärfarbe <b>Violett</b> (englisch: <b>purple</b>,
     *               Mischung aus <b>Rot</b> und <b>Blau</b>) in hexadezimaler
     *               Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setPurple(String purple)
    {
        this.purple = decode(purple);
        return this;
    }

    /**
     * Gibt die Tertiärfarbe <b>Indigo</b> (englisch: <b>indigo</b>) - eine
     * Mischung aus <b>Violett</b> und <b>Blau</b> - zurück.
     *
     * @return Die Tertiärfarbe <b>Indigo</b> (englisch: <b>indigo</b>, Mischung
     *         aus <b>Violett</b> und <b>Blau</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public Color getIndigo()
    {
        if (indigo == null)
        {
            indigo = mix(getPurple(), getBlue());
        }
        return indigo;
    }

    /**
     * Setzt die Tertiärfarbe <b>Indigo</b> (englisch: <b>indigo</b>, Mischung
     * aus <b>Violett</b> und <b>Blau</b>).
     *
     * @param indigo Die Tertiärfarbe <b>Indigo</b> (englisch: <b>indigo</b>,
     *               Mischung aus <b>Violett</b> und <b>Blau</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setIndigo(Color indigo)
    {
        this.indigo = indigo;
        return this;
    }

    /**
     * Setzt die Tertiärfarbe <b>Indigo</b> (englisch: <b>indigo</b>, Mischung
     * aus <b>Violett</b> und <b>Blau</b>) in hexadezimaler Codierung (z. B.
     * {@code #ff0000}).
     *
     * @param indigo Die Tertiärfarbe <b>Indigo</b> (englisch: <b>indigo</b>,
     *               Mischung aus <b>Violett</b> und <b>Blau</b>) in
     *               hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setIndigo(String indigo)
    {
        this.indigo = decode(indigo);
        return this;
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
     * Setzt die Primärfarbe <b>Blau</b> (englisch: <b>blue</b>).
     *
     * @param blue Die Primärfarbe <b>Blau</b> (englisch: <b>blue</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setBlue(Color blue)
    {
        this.blue = blue;
        return this;
    }

    /**
     * Setzt die Primärfarbe <b>Blau</b> (englisch: <b>blue</b>) in
     * hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @param blue Die Primärfarbe <b>Blau</b> (englisch: <b>blue</b>) in
     *             hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setBlue(String blue)
    {
        this.blue = decode(blue);
        return this;
    }

    /**
     * Gibt die Tertiärfarbe <b>Türkis</b> (englisch: <b>cyan</b>) - eine
     * Mischung aus <b>Blau</b> und <b>Grün</b> - zurück.
     *
     * @return Die Tertiärfarbe <b>Türkis</b> (englisch: <b>cyan</b>, Mischung
     *         aus <b>Blau</b> und <b>Grün</b>).
     */
    public Color getCyan()
    {
        if (cyan == null)
        {
            cyan = mix(getBlue(), getGreen());
        }
        return cyan;
    }

    /**
     * Setzt die Tertiärfarbe <b>Türkis</b> (englisch: <b>cyan</b>, Mischung aus
     * <b>Blau</b> und <b>Grün</b>).
     *
     * @param cyan Die Tertiärfarbe <b>Türkis</b> (englisch: <b>cyan</b>,
     *             Mischung aus <b>Blau</b> und <b>Grün</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setCyan(Color cyan)
    {
        this.cyan = cyan;
        return this;
    }

    /**
     * Setzt die Tertiärfarbe <b>Türkis</b> (englisch: <b>cyan</b>, Mischung aus
     * <b>Blau</b> und <b>Grün</b>) in hexadezimaler Codierung (z. B.
     * {@code #ff0000}).
     *
     * @param cyan Die Tertiärfarbe <b>Türkis</b> (englisch: <b>cyan</b>,
     *             Mischung aus <b>Blau</b> und <b>Grün</b>) in hexadezimaler
     *             Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setCyan(String cyan)
    {
        this.cyan = decode(cyan);
        return this;
    }

    /**
     * Gibt die Sekundärfarbe <b>Grün</b> (englisch: <b>green</b>) (Mischung aus
     * <b>Gelb</b> und <b>Blau</b>) zurück.
     *
     * @return Die Sekundärfarbe <b>Grün</b> (englisch: <b>green</b>, Mischung
     *         aus <b>Gelb</b> und <b>Blau</b>).
     */
    public Color getGreen()
    {
        if (green == null)
        {
            green = mix(getYellow(), getBlue());
        }
        return green;
    }

    /**
     * Setzt die Sekundärfarbe <b>Grün</b> (Mischung aus <b>Gelb</b> und
     * <b>Blau</b>).
     *
     * @param green Die Sekundärfarbe <b>Grün</b> (Mischung aus <b>Gelb</b> und
     *              <b>Blau</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setGreen(Color green)
    {
        this.green = green;
        return this;
    }

    /**
     * Setzt die Sekundärfarbe <b>Grün</b> (Mischung aus <b>Gelb</b> und
     * <b>Blau</b>) in hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @param green Die Sekundärfarbe <b>Grün</b> (Mischung aus <b>Gelb</b> und
     *              <b>Blau</b>) in hexadezimaler Codierung (z. B.
     *              {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setGreen(String green)
    {
        this.green = decode(green);
        return this;
    }

    /**
     * Gibt die Tertiärfarbe <b>Limettengrün</b> (englisch: <b>lime</b>) - eine
     * Mischung aus <b>Gelb</b> und <b>Grün</b> - zurück.
     *
     * @return Die Tertiärfarbe <b>Limettengrün</b> (englisch: <b>lime</b>,
     *         Mischung aus <b>Gelb</b> und <b>Grün</b>).
     */
    public Color getLime()
    {
        if (lime == null)
        {
            lime = mix(getYellow(), getGreen());
        }
        return lime;
    }

    /**
     * Setzt die Tertiärfarbe <b>Limettengrün</b> (englisch: <b>lime</b>,
     * Mischung aus <b>Gelb</b> und <b>Grün</b>).
     *
     * @param lime Die Tertiärfarbe <b>Limettengrün</b> (englisch: <b>lime</b>,
     *             Mischung aus <b>Gelb</b> und <b>Grün</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setLime(Color lime)
    {
        this.lime = lime;
        return this;
    }

    /**
     * Setzt die Tertiärfarbe <b>Limettengrün</b> (englisch: <b>lime</b>,
     * Mischung aus <b>Gelb</b> und <b>Grün</b>) in hexadezimaler Codierung (z.
     * B. {@code #ff0000}).
     *
     * @param lime Die Tertiärfarbe <b>Limettengrün</b> (englisch: <b>lime</b>,
     *             Mischung aus <b>Gelb</b> und <b>Grün</b>) in hexadezimaler
     *             Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setLime(String lime)
    {
        this.lime = decode(lime);
        return this;
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
     * @return Die Farbe <b>Braun</b> (englisch: <b>brown</b>).u
     */
    public Color getBrown()
    {
        if (brown == null)
        {
            brown = mix(getRed(), getGreen(), 0.35);
        }
        return brown;
    }

    /**
     * Setzt die Farbe <b>Braun</b> (englisch: <b>brown</b>).
     *
     * @param brown Die Farbe <b>Braun</b> (englisch: <b>brown</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setBrown(Color brown)
    {
        this.brown = brown;
        return this;
    }

    /**
     * Setzt die Farbe <b>Braun</b> (englisch: <b>brown</b>) in hexadezimaler
     * Codierung (z. B. {@code #ff0000}).
     *
     * @param brown Die Farbe <b>Braun</b> (englisch: <b>brown</b>) in
     *              hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setBrown(String brown)
    {
        this.brown = decode(brown);
        return this;
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
     * Setzt die Farbe <b>Weiß</b> (englisch: <b>white</b>).
     *
     * @param white Die Farbe <b>Weiß</b> (englisch: <b>white</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setWhite(Color white)
    {
        this.white = white;
        return this;
    }

    /**
     * Setzt die Farbe <b>Weiß</b> (englisch: <b>white</b>) in hexadezimaler
     * Codierung (z. B. {@code #ff0000}).
     *
     * @param white Die Farbe <b>Weiß</b> (englisch: <b>white</b>) in
     *              hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setWhite(String white)
    {
        this.white = decode(white);
        return this;
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
     * Setzt die Farbe <b>Grau</b> (englisch: <b>gray</b>).
     *
     * @param gray Die Farbe <b>Grau</b> (englisch: <b>gray</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setGray(Color gray)
    {
        this.gray = gray;
        return this;
    }

    /**
     * Setzt die Farbe <b>Grau</b> (englisch: <b>gray</b>) in hexadezimaler
     * Codierung (z. B. {@code #ff0000}).
     *
     * @param gray Die Farbe <b>Grau</b> (englisch: <b>gray</b>) in
     *             hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setGray(String gray)
    {
        this.gray = decode(gray);
        return this;
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
     * Setzt die Farbe <b>Schwarz</b> (englisch: <b>black</b>).
     *
     * @param black Die Farbe <b>Schwarz</b> (englisch: <b>black</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setBlack(Color black)
    {
        this.black = black;
        return this;
    }

    /**
     * Setzt die Farbe <b>Schwarz</b> (englisch: <b>black</b>) in hexadezimaler
     * Codierung (z. B. {@code #ff0000}).
     *
     * @param black Die Farbe <b>Schwarz</b> (englisch: <b>black</b>) in
     *              hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setBlack(String black)
    {
        this.black = decode(black);
        return this;
    }

    /**
     * Erzeugt ein Farbschema nach den Farben derSchema
     * <a href="https://developer.gnome.org/hig/reference/palette.html">GNOME
     * Human Interface Guidelines</a>.
     *
     * @return Ein Farbschema nach den Farben der <a href=
     *         "https://developer.gnome.org/hig/reference/palette.html">GNOME
     *         Human Interface Guidelines</a>.
     */
    public static ColorScheme getGnomeScheme()
    {
        return new ColorScheme(
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
                new Color(152, 106, 68));
    }

    /**
     * Erzeugt ein Farbschema, das alle vordefinierten statischen Farbattribute
     * der Java-{@link Color}-Klasse verwendet.
     *
     * @return Ein Farbschema, das alle vordefinierten statischen Farbattribute
     *         der Java-{@link Color}-Klasse verwendet.
     */
    public static ColorScheme getJavaScheme()
    {
        return new ColorScheme(Color.YELLOW, Color.RED, Color.BLUE)
                .setPink(Color.PINK).setGray(Color.GRAY).setCyan(Color.CYAN)
                .setOrange(Color.ORANGE).setPurple(Color.MAGENTA)
                .setGreen(Color.GREEN);
    }

    public static void main(String[] args)
    {
        Game.start(1200, 400, new Scene()
        {
            {
                setBackgroundColor(Resources.getColor("#444444"));
                int x = -16;
                int labelY = -2;
                for (Entry<String, Color> entry : Resources.colors.getAll()
                        .entrySet())
                {
                    createCircle(x, 0, entry.getValue());
                    createText(entry.getKey(), 0.5, x, labelY);
                    x += 2;
                    labelY -= 2;
                    if (labelY < -4)
                    {
                        labelY = -2;
                    }
                }
            }
        });
    }
}
