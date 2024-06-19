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
 * <p>
 * Die zwölf Farben nach dem Farbkreis von Itten:
 * </p>
 *
 * <ol>
 * <li>{@code yellow} (<b>Gelb</b>)</li>
 * <li>{@code yellowOrange} (<b>Gelb-Orange</b>)</li>
 * <li>{@code orange} (<b>Orange</b>)</li>
 * <li>{@code redOrange} (<b>Rot-Orange</b>)</li>
 * <li>{@code red} (<b>Rot</b>)</li>
 * <li>{@code redPurple} (<b>Rot-Violett</b>)</li>
 * <li>{@code purple} (<b>Violett</b>)</li>
 * <li>{@code bluePurple} (<b>Blau-Violett</b>)</li>
 * <li>{@code blue} (<b>Blau</b>)</li>
 * <li>{@code blueGreen} (<b>Blau-Grün</b>)</li>
 * <li>{@code green} (<b>Grün</b>)</li>
 * <li>{@code yellowGreen} (<b>Gelb-Grün</b>)</li>
 * </ol>
 *
 * <p>
 * Diese Farben sind ebenfalls im Farbschema enthalten (gehören aber nicht zum
 * Farbkreis von Itten)
 * </p>
 *
 * <ul>
 * <li>{@code brown} (<b>Braun</b>)</li>
 * <li>{@code white} (<b>Weiß</b>)</li>
 * <li>{@code gray} (<b>Grau</b>)</li>
 * <li>{@code black} (<b>Schwarz</b>)</li>
 * </ul>
 *
 * Anderes Farbschema:
 * https://commons.wikimedia.org/wiki/File:Color_star-en_(tertiary_names).svg
 *
 * @see de.pirckheimer_gymnasium.engine_pi.Resources#colorScheme
 */
public class ColorScheme
{
    /**
     * Die Primärfarbe <b>Gelb</b> (englisch: <b>yellow</b>).
     */
    private Color yellow;

    /**
     * Die Tertiärfarbe <b>Gelb-Orange</b> (englisch: <b>yellow orange</b>,
     * Mischung aus <b>Gelb</b> und <b>Orange</b>).
     */
    private Color yellowOrange;

    /**
     * Die Sekundärfarbe <b>Orange</b> (englisch: <b>orange</b>, Mischung aus
     * <b>Gelb</b> und <b>Rot</b>).
     */
    private Color orange;

    /**
     * Die Tertiärfarbe <b>Rot-Orange</b> (englisch: <b>red orange</b>, Mischung
     * aus <b>Orange</b> und <b>Rot</b>)
     */
    private Color redOrange;

    /**
     * Die Primärfarbe <b>Rot</b> (englisch: <b>red</b>).
     */
    private Color red;

    /**
     * Die Tertiärfarbe <b>Rot-Violett</b> (englisch: <b>red purple</b>,
     * Mischung aus <b>Rot</b> und <b>Violett</b>).
     */
    private Color redPurple;

    /**
     * Sekundärfarbe <b>Violett</b> (englisch: <b>purple</b>, Mischung aus
     * <b>Rot</b> und <b>Blau</b>).
     */
    private Color purple;

    /**
     * Tertiärfarbe <b>Blau-Violett</b> (englisch: <b>blue purple</b>, Mischung
     * aus <b>Violett</b> und <b>Blau</b>).
     */
    private Color bluePurple;

    /**
     * Die Primärfarbe <b>Blau</b> (englisch: <b>blue</b>).
     */
    private Color blue;

    /**
     * Tertiärfarbe <b>Blau-Grün</b> (englisch: <b>blue green</b>, Mischung aus
     * <b>Blau</b> und <b>Grün</b>).
     */
    private Color blueGreen;

    /**
     * Die Sekundärfarbe <b>Grün</b> (Mischung aus <b>Gelb</b> und <b>Blau</b>).
     */
    private Color green;

    /**
     * Die Tertiärfarbe <b>Gelb-Grün</b> (englisch: <b>yellow green</b>,
     * Mischung aus <b>Gelb</b> und <b>Grün</b>).
     */
    private Color yellowGreen;

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
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
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
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setYellow(String yellow)
    {
        this.yellow = decode(yellow);
        return this;
    }

    /**
     * Gibt die Tertiärfarbe <b>Gelb-Orange</b> (englisch: <b>yellow orange</b>)
     * - eine Mischung aus <b>Gelb</b> und <b>Orange</b> - zurück.
     *
     * @return Die Tertiärfarbe <b>Gelb-Orange</b> (englisch: <b>yellow
     *         orange</b>, Mischung aus <b>Gelb</b> und <b>Orange</b>).
     */
    public Color getYellowOrange()
    {
        if (yellowOrange == null)
        {
            yellowOrange = mix(getYellow(), getOrange());
        }
        return yellowOrange;
    }

    /**
     * Setzt die Tertiärfarbe <b>Gelb-Orange</b> (englisch: <b>yellow
     * orange</b>, Mischung aus <b>Gelb</b> und <b>Orange</b>).
     *
     * @param yellowOrange Die Tertiärfarbe <b>Gelb-Orange</b> (englisch:
     *                     <b>yellow orange</b>, Mischung aus <b>Gelb</b> und
     *                     <b>Orange</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setYellowOrange(Color yellowOrange)
    {
        this.yellowOrange = yellowOrange;
        return this;
    }

    /**
     * Setzt die Tertiärfarbe <b>Gelb-Orange</b> (englisch: <b>yellow
     * orange</b>, Mischung aus <b>Gelb</b> und <b>Orange</b>) in hexadezimaler
     * Codierung (z. B. {@code #ff0000}).
     *
     * @param yellowOrange Die Tertiärfarbe <b>Gelb-Orange</b> (englisch:
     *                     <b>yellow orange</b>, Mischung aus <b>Gelb</b> und
     *                     <b>Orange</b>) in hexadezimaler Codierung (z. B.
     *                     {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setYellowOrange(String yellowOrange)
    {
        this.yellowOrange = decode(yellowOrange);
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
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
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
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setOrange(String orange)
    {
        this.orange = decode(orange);
        return this;
    }

    /**
     * Gibt die Tertiärfarbe <b>Rot-Orange</b> (englisch: <b>red orange</b>) -
     * eine Mischung aus <b>Orange</b> und <b>Rot</b> - zurück.
     *
     * @return Die Tertiärfarbe <b>Rot-Orange</b> (englisch: <b>red orange</b>,
     *         Mischung aus <b>Orange</b> und <b>Rot</b>).
     */
    public Color getRedOrange()
    {
        if (redOrange == null)
        {
            redOrange = mix(getOrange(), getRed());
        }
        return redOrange;
    }

    /**
     * Setzt die Tertiärfarbe <b>Rot-Orange</b> (englisch: <b>red orange</b>,
     * Mischung aus <b>Orange</b> und <b>Rot</b>).
     *
     * @param redOrange Die Tertiärfarbe <b>Rot-Orange</b> (englisch: <b>red
     *                  orange</b>, Mischung aus <b>Orange</b> und <b>Rot</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setRedOrange(Color redOrange)
    {
        this.redOrange = redOrange;
        return this;
    }

    /**
     * Setzt die Tertiärfarbe <b>Rot-Orange</b> (englisch: <b>red orange</b>,
     * Mischung aus <b>Orange</b> und <b>Rot</b>) in hexadezimaler Codierung (z.
     * B. {@code #ff0000}).
     *
     * @param redOrange Die Tertiärfarbe <b>Rot-Orange</b> (englisch: <b>red
     *                  orange</b>, Mischung aus <b>Orange</b> und <b>Rot</b>)
     *                  in hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setRedOrange(String redOrange)
    {
        this.redOrange = decode(redOrange);
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
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
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
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setRed(String red)
    {
        this.red = decode(red);
        return this;
    }

    /**
     * Gibt die Tertiärfarbe <b>Rot-Violett</b> (englisch: <b>red purple</b>) -
     * eine Mischung aus <b>Rot</b> und <b>Violett</b> - zurück.
     *
     * @return Die Tertiärfarbe <b>Rot-Violett</b> (englisch: <b>red purple</b>,
     *         Mischung aus <b>Rot</b> und <b>Violett</b>).
     */
    public Color getRedPurple()
    {
        if (redPurple == null)
        {
            redPurple = mix(getRed(), getPurple());
        }
        return redPurple;
    }

    /**
     * Setzt die Tertiärfarbe <b>Rot-Violett</b> (englisch: <b>red purple</b>,
     * Mischung aus <b>Rot</b> und <b>Violett</b>).
     *
     * @param redPurple Die Tertiärfarbe <b>Rot-Violett</b> (englisch: <b>red
     *                  purple</b>, Mischung aus <b>Rot</b> und <b>Violett</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setRedPurple(Color redPurple)
    {
        this.redPurple = redPurple;
        return this;
    }

    /**
     * Setzt die Tertiärfarbe <b>Rot-Violett</b> (englisch: <b>red purple</b>,
     * Mischung aus <b>Rot</b> und <b>Violett</b>) in hexadezimaler Codierung
     * (z. B. {@code #ff0000}).
     *
     * @param redPurple Die Tertiärfarbe <b>Rot-Violett</b> (englisch: <b>red
     *                  purple</b>, Mischung aus <b>Rot</b> und <b>Violett</b>)
     *                  in hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setRedPurple(String redPurple)
    {
        this.redPurple = decode(redPurple);
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
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
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
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setPurple(String purple)
    {
        this.purple = decode(purple);
        return this;
    }

    /**
     * Gibt die Tertiärfarbe <b>Blau-Violett</b> (englisch: <b>blue purple</b>)
     * - eine Mischung aus <b>Violett</b> und <b>Blau</b> - zurück.
     *
     * @return Die Tertiärfarbe <b>Blau-Violett</b> (englisch: <b>blue
     *         purple</b>, Mischung aus <b>Violett</b> und <b>Blau</b>).
     */
    public Color getBluePurple()
    {
        if (bluePurple == null)
        {
            bluePurple = mix(getPurple(), getBlue());
        }
        return bluePurple;
    }

    /**
     * Setzt die Tertiärfarbe <b>Blau-Violett</b> (englisch: <b>blue purple</b>,
     * Mischung aus <b>Violett</b> und <b>Blau</b>).
     *
     * @param bluePurple Die Tertiärfarbe <b>Blau-Violett</b> (englisch: <b>blue
     *                   purple</b>, Mischung aus <b>Violett</b> und
     *                   <b>Blau</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setBluePurple(Color bluePurple)
    {
        this.bluePurple = bluePurple;
        return this;
    }

    /**
     * Setzt die Tertiärfarbe <b>Blau-Violett</b> (englisch: <b>blue purple</b>,
     * Mischung aus <b>Violett</b> und <b>Blau</b>) in hexadezimaler Codierung
     * (z. B. {@code #ff0000}).
     *
     * @param bluePurple Die Tertiärfarbe <b>Blau-Violett</b> (englisch: <b>blue
     *                   purple</b>, Mischung aus <b>Violett</b> und
     *                   <b>Blau</b>) in hexadezimaler Codierung (z. B.
     *                   {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setBluePurple(String bluePurple)
    {
        this.bluePurple = decode(bluePurple);
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
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
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
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setBlue(String blue)
    {
        this.blue = decode(blue);
        return this;
    }

    /**
     * Gibt die Tertiärfarbe <b>Blau-Grün</b> (englisch: <b>blue green</b>) -
     * eine Mischung aus <b>Blau</b> und <b>Grün</b> - zurück.
     *
     * @return Die Tertiärfarbe <b>Blau-Grün</b> (englisch: <b>blue green</b>,
     *         Mischung aus <b>Blau</b> und <b>Grün</b>).
     */
    public Color getBlueGreen()
    {
        if (blueGreen == null)
        {
            blueGreen = mix(getBlue(), getGreen());
        }
        return blueGreen;
    }

    /**
     * Setzt die Tertiärfarbe <b>Blau-Grün</b> (englisch: <b>blue green</b>,
     * Mischung aus <b>Blau</b> und <b>Grün</b>).
     *
     * @param blueGreen Die Tertiärfarbe <b>Blau-Grün</b> (englisch: <b>blue
     *                  green</b>, Mischung aus <b>Blau</b> und <b>Grün</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setBlueGreen(Color blueGreen)
    {
        this.blueGreen = blueGreen;
        return this;
    }

    /**
     * Setzt die Tertiärfarbe <b>Blau-Grün</b> (englisch: <b>blue green</b>,
     * Mischung aus <b>Blau</b> und <b>Grün</b>) in hexadezimaler Codierung (z.
     * B. {@code #ff0000}).
     *
     * @param blueGreen Die Tertiärfarbe <b>Blau-Grün</b> (englisch: <b>blue
     *                  green</b>, Mischung aus <b>Blau</b> und <b>Grün</b>) in
     *                  hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setBlueGreen(String blueGreen)
    {
        this.blueGreen = decode(blueGreen);
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
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
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
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setGreen(String green)
    {
        this.green = decode(green);
        return this;
    }

    /**
     * Gibt die Tertiärfarbe <b>Gelb-Grün</b> (englisch: <b>yellow green</b>) -
     * eine Mischung aus <b>Gelb</b> und <b>Grün</b> - zurück.
     *
     * @return Die Tertiärfarbe <b>Gelb-Grün</b> (englisch: <b>yellow green</b>,
     *         Mischung aus <b>Gelb</b> und <b>Grün</b>).
     */
    public Color getYellowGreen()
    {
        if (yellowGreen == null)
        {
            yellowGreen = mix(getYellow(), getGreen());
        }
        return yellowGreen;
    }

    /**
     * Setzt die Tertiärfarbe <b>Gelb-Grün</b> (englisch: <b>yellow green</b>,
     * Mischung aus <b>Gelb</b> und <b>Grün</b>).
     *
     * @param yellowGreen Die Tertiärfarbe <b>Gelb-Grün</b> (englisch: <b>yellow
     *                    green</b>, Mischung aus <b>Gelb</b> und <b>Grün</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setYellowGreen(Color yellowGreen)
    {
        this.yellowGreen = yellowGreen;
        return this;
    }

    /**
     * Setzt die Tertiärfarbe <b>Gelb-Grün</b> (englisch: <b>yellow green</b>,
     * Mischung aus <b>Gelb</b> und <b>Grün</b>) in hexadezimaler Codierung (z.
     * B. {@code #ff0000}).
     *
     * @param yellowGreen Die Tertiärfarbe <b>Gelb-Grün</b> (englisch: <b>yellow
     *                    green</b>, Mischung aus <b>Gelb</b> und <b>Grün</b>)
     *                    in hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
     *         {@code scheme.setGreen(..).setBlue(..)}.
     */
    public ColorScheme setYellowGreen(String yellowGreen)
    {
        this.yellowGreen = decode(yellowGreen);
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
     * @return Die Farbe <b>Braun</b> (englisch: <b>brown</b>).
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
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
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
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
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
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
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
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
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
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
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
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
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
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
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
     *         Punktschreibweise hintereinander geschrieben werden können, z. B.
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
                // Sekundärfarben
                // Orange passt nicht in das Schema, viel zu hell.
                // .setOrange(Color.ORANGE)
                .setGreen(Color.GREEN)
                // Tertiärfarben
                .setBlueGreen(Color.CYAN)
                // Pink passt nicht in das Schema.
                // .setRedPurple(Color.PINK)
                .setRedPurple(Color.MAGENTA)
                // Andere
                .setGray(Color.GRAY);
    }

    /**
     * Gibt die zwölf Farben des Farbkreises von Itten in der richtigen
     * Reihenfolge von Gelb ausgehend zurück.
     *
     * @return Die zwölf Farben des Farbkreises von Itten in der richtigen
     *         Reihenfolge.
     */
    public Color[] getWheelColors()
    {
        return new Color[] { getYellow(), getYellowOrange(), getOrange(),
                getRedOrange(), getRed(), getRedPurple(), getPurple(),
                getBluePurple(), getBlue(), getBlueGreen(), getGreen(),
                getYellowGreen() };
    }

    /**
     * Gibt die drei Primärfarben des Farbkreises von Itten in der Reihenfolge
     * <b>Gelb</b>, <b>Rot</b> und <b>Blau</b> aus.
     *
     * @return Die drei Primärfarben des Farbkreises.
     */
    public Color[] getPrimaryColors()
    {
        return new Color[] { getYellow(), getRed(), getBlue() };
    }

    /**
     * Gibt die drei Sekundärfarben des Farbkreises von Itten in der Reihenfolge
     * <b>Orange</b>, <b>Violett</b> und <b>Grün</b> aus.
     *
     * @return Die drei Sekundärfarben des Farbkreises.
     */
    public Color[] getSecondaryColors()
    {
        return new Color[] { getOrange(), getPurple(), getGreen() };
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
