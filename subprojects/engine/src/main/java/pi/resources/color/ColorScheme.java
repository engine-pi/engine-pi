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
import java.util.Map.Entry;

import pi.Circle;
import pi.Controller;
import pi.Scene;
import pi.Text;
import pi.annotations.Getter;
import pi.annotations.Setter;
import static pi.Controller.colors;

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
 * Diese Klasse ist inspiriert durch den Farbkreis von Itten: Aus den drei
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
 * jede Farbe auch explizit gesetzt werden. Dadurch entfällt der Mischvorgang.
 * </p>
 *
 * <p>
 * Die zwölf Farben nach dem Farbkreis von Itten:
 * </p>
 *
 * <ol>
 * <li>{@code yellow} (<b>Gelb</b>)</li>
 * <li>{@code yellow orange} (<b>Gelb-Orange</b>)</li>
 * <li>{@code orange} (<b>Orange</b>)</li>
 * <li>{@code red orange} (<b>Rot-Orange</b>)</li>
 * <li>{@code red} (<b>Rot</b>)</li>
 * <li>{@code red purple} (<b>Rot-Violett</b>)</li>
 * <li>{@code purple} (<b>Violett</b>)</li>
 * <li>{@code blue purple} (<b>Blau-Violett</b>)</li>
 * <li>{@code blue} (<b>Blau</b>)</li>
 * <li>{@code blue green} (<b>Blau-Grün</b>)</li>
 * <li>{@code green} (<b>Grün</b>)</li>
 * <li>{@code yellow green} (<b>Gelb-Grün</b>)</li>
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
 * @see pi.Resources#colorScheme
 *
 * @author Josef Friedrich
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
     * Erzeugt ein neues Farbschema aus den drei Primärfarben Gelb (255,255,0),
     * Rot (255,0,0) und Blue (0,0,255).
     */
    public ColorScheme()
    {
        this.yellow = new Color(255, 255, 0);
        this.red = new Color(255, 0, 0);
        this.blue = new Color(0, 0, 255);
    }

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
     * @param red Die Primärfarbe <b>Rot</b>.
     * @param blue Die Primärfarbe <b>Blau</b>.
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
     *     und <b>Rot</b>).
     * @param red Die Primärfarbe <b>Rot</b>.
     * @param purple Die Sekundärfarbe <b>Violett</b> (Mischung aus <b>Rot</b>
     *     und <b>Blau</b>).
     * @param blue Die Primärfarbe <b>Blau</b>.
     * @param green Die Sekundärfarbe <b>Grün</b> (Mischung aus <b>Gelb</b> und
     *     <b>Blau</b>).
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
     *     und <b>Rot</b>).
     * @param red Die Primärfarbe <b>Rot</b>.
     * @param purple Die Sekundärfarbe <b>Violett</b> (Mischung aus <b>Rot</b>
     *     und <b>Blau</b>).
     * @param blue Die Primärfarbe <b>Blau</b>.
     * @param green Die Sekundärfarbe <b>Grün</b> (Mischung aus <b>Gelb</b> und
     *     <b>Blau</b>).
     * @param brown Die Farbe <b>Braun</b> (englisch: <b>brown</b>).
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
    @Getter
    public Color yellow()
    {
        return yellow;
    }

    /**
     * Setzt die Primärfarbe <b>Gelb</b> (englisch: <b>yellow</b>).
     *
     * @param yellow Die Primärfarbe <b>Gelb</b> (englisch: <b>yellow</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme yellow(Color yellow)
    {
        this.yellow = yellow;
        return this;
    }

    /**
     * Setzt die Primärfarbe <b>Gelb</b> (englisch: <b>yellow</b>) durch Angabe
     * des <b>Rot-, Grün- und Blau-Anteils</b>.
     *
     * @param r Der Rot-Anteil der Farbe im Bereich von {@code 0} - {@code 255}.
     * @param g Der Grün-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     * @param b Der Blau-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme yellow(int r, int g, int b)
    {
        yellow = new Color(r, g, b);
        return this;
    }

    /**
     * Setzt die Primärfarbe <b>Gelb</b> (englisch: <b>yellow</b>) in
     * hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @param yellow Die Primärfarbe <b>Gelb</b> (englisch: <b>yellow</b>) in
     *     hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme yellow(String yellow)
    {
        this.yellow = decode(yellow);
        return this;
    }

    /**
     * Gibt die Tertiärfarbe <b>Gelb-Orange</b> (englisch: <b>yellow orange</b>)
     * - eine Mischung aus <b>Gelb</b> und <b>Orange</b> - zurück.
     *
     * @return Die Tertiärfarbe <b>Gelb-Orange</b> (englisch: <b>yellow
     *     orange</b>, Mischung aus <b>Gelb</b> und <b>Orange</b>).
     */
    @Getter
    public Color yellowOrange()
    {
        if (yellowOrange == null)
        {
            yellowOrange = mix(yellow(), orange());
        }
        return yellowOrange;
    }

    /**
     * Setzt die Tertiärfarbe <b>Gelb-Orange</b> (englisch: <b>yellow
     * orange</b>, Mischung aus <b>Gelb</b> und <b>Orange</b>).
     *
     * @param yellowOrange Die Tertiärfarbe <b>Gelb-Orange</b> (englisch:
     *     <b>yellow orange</b>, Mischung aus <b>Gelb</b> und <b>Orange</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme yellowOrange(Color yellowOrange)
    {
        this.yellowOrange = yellowOrange;
        return this;
    }

    /**
     * Setzt die Tertiärfarbe <b>Gelb-Orange</b> (englisch: <b>yellow
     * orange</b>, Mischung aus <b>Gelb</b> und <b>Orange</b>) durch Angabe des
     * <b>Rot-, Grün- und Blau-Anteils</b>.
     *
     * @param r Der Rot-Anteil der Farbe im Bereich von {@code 0} - {@code 255}.
     * @param g Der Grün-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     * @param b Der Blau-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme yellowOrange(int r, int g, int b)
    {
        yellowOrange = new Color(r, g, b);
        return this;
    }

    /**
     * Setzt die Tertiärfarbe <b>Gelb-Orange</b> (englisch: <b>yellow
     * orange</b>, Mischung aus <b>Gelb</b> und <b>Orange</b>) in hexadezimaler
     * Codierung (z. B. {@code #ff0000}).
     *
     * @param yellowOrange Die Tertiärfarbe <b>Gelb-Orange</b> (englisch:
     *     <b>yellow orange</b>, Mischung aus <b>Gelb</b> und <b>Orange</b>) in
     *     hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme yellowOrange(String yellowOrange)
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
     *     Mischung aus <b>Gelb</b> und <b>Rot</b>).
     */
    @Getter
    public Color orange()
    {
        if (orange == null)
        {
            orange = mix(yellow(), red());
        }
        return orange;
    }

    /**
     * Setzt die Sekundärfarbe <b>Orange</b> (englisch: <b>orange</b>, Mischung
     * aus <b>Gelb</b> und <b>Rot</b>).
     *
     * @param orange Die Sekundärfarbe <b>Orange</b> (englisch: <b>orange</b>,
     *     Mischung aus <b>Gelb</b> und <b>Rot</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme orange(Color orange)
    {
        this.orange = orange;
        return this;
    }

    /**
     * Setzt die Sekundärfarbe <b>Orange</b> (englisch: <b>orange</b>, Mischung
     * aus <b>Gelb</b> und <b>Rot</b>) durch Angabe des <b>Rot-, Grün- und
     * Blau-Anteils</b>.
     *
     * @param r Der Rot-Anteil der Farbe im Bereich von {@code 0} - {@code 255}.
     * @param g Der Grün-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     * @param b Der Blau-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    public ColorScheme orange(int r, int g, int b)
    {
        orange = new Color(r, g, b);
        return this;
    }

    /**
     * Setzt die Sekundärfarbe <b>Orange</b> (englisch: <b>orange</b>, Mischung
     * aus <b>Gelb</b> und <b>Rot</b>) in hexadezimaler Codierung (z. B.
     * {@code #ff0000}).
     *
     * @param orange Die Sekundärfarbe <b>Orange</b> (englisch: <b>orange</b>,
     *     Mischung aus <b>Gelb</b> und <b>Rot</b>) in hexadezimaler Codierung
     *     (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme orange(String orange)
    {
        this.orange = decode(orange);
        return this;
    }

    /**
     * Gibt die Tertiärfarbe <b>Rot-Orange</b> (englisch: <b>red orange</b>) -
     * eine Mischung aus <b>Orange</b> und <b>Rot</b> - zurück.
     *
     * @return Die Tertiärfarbe <b>Rot-Orange</b> (englisch: <b>red orange</b>,
     *     Mischung aus <b>Orange</b> und <b>Rot</b>).
     */
    @Getter
    public Color redOrange()
    {
        if (redOrange == null)
        {
            redOrange = mix(orange(), red());
        }
        return redOrange;
    }

    /**
     * Setzt die Tertiärfarbe <b>Rot-Orange</b> (englisch: <b>red orange</b>,
     * Mischung aus <b>Orange</b> und <b>Rot</b>).
     *
     * @param redOrange Die Tertiärfarbe <b>Rot-Orange</b> (englisch: <b>red
     *     orange</b>, Mischung aus <b>Orange</b> und <b>Rot</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme redOrange(Color redOrange)
    {
        this.redOrange = redOrange;
        return this;
    }

    /**
     * Setzt die Tertiärfarbe <b>Rot-Orange</b> (englisch: <b>red orange</b>,
     * Mischung aus <b>Orange</b> und <b>Rot</b>) durch Angabe des <b>Rot-,
     * Grün- und Blau-Anteils</b>.
     *
     * @param r Der Rot-Anteil der Farbe im Bereich von {@code 0} - {@code 255}.
     * @param g Der Grün-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     * @param b Der Blau-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme redOrange(int r, int g, int b)
    {
        redOrange = new Color(r, g, b);
        return this;
    }

    /**
     * Setzt die Tertiärfarbe <b>Rot-Orange</b> (englisch: <b>red orange</b>,
     * Mischung aus <b>Orange</b> und <b>Rot</b>) in hexadezimaler Codierung (z.
     * B. {@code #ff0000}).
     *
     * @param redOrange Die Tertiärfarbe <b>Rot-Orange</b> (englisch: <b>red
     *     orange</b>, Mischung aus <b>Orange</b> und <b>Rot</b>) in
     *     hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme redOrange(String redOrange)
    {
        this.redOrange = decode(redOrange);
        return this;
    }

    /**
     * Gibt die Primärfarbe <b>Rot</b> (englisch: <b>red</b>) zurück.
     *
     * @return Die Primärfarbe <b>Rot</b> (englisch: <b>red</b>).
     */
    @Getter
    public Color red()
    {
        return red;
    }

    /**
     * Setzt die Primärfarbe <b>Rot</b> (englisch: <b>red</b>).
     *
     * @param red Die Primärfarbe <b>Rot</b> (englisch: <b>red</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme red(Color red)
    {
        this.red = red;
        return this;
    }

    /**
     * Setzt die Primärfarbe <b>Rot</b> (englisch: <b>red</b>) durch Angabe des
     * <b>Rot-, Grün- und Blau-Anteils</b>.
     *
     * @param r Der Rot-Anteil der Farbe im Bereich von {@code 0} - {@code 255}.
     * @param g Der Grün-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     * @param b Der Blau-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme red(int r, int g, int b)
    {
        red = new Color(r, g, b);
        return this;
    }

    /**
     * Setzt die Primärfarbe <b>Rot</b> (englisch: <b>red</b>) in hexadezimaler
     * Codierung (z. B. {@code #ff0000}).
     *
     * @param red Die Primärfarbe <b>Rot</b> (englisch: <b>red</b>) in
     *     hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme red(String red)
    {
        this.red = decode(red);
        return this;
    }

    /**
     * Gibt die Tertiärfarbe <b>Rot-Violett</b> (englisch: <b>red purple</b>) -
     * eine Mischung aus <b>Rot</b> und <b>Violett</b> - zurück.
     *
     * @return Die Tertiärfarbe <b>Rot-Violett</b> (englisch: <b>red purple</b>,
     *     Mischung aus <b>Rot</b> und <b>Violett</b>).
     */
    @Getter
    public Color redPurple()
    {
        if (redPurple == null)
        {
            redPurple = mix(red(), purple());
        }
        return redPurple;
    }

    /**
     * Setzt die Tertiärfarbe <b>Rot-Violett</b> (englisch: <b>red purple</b>,
     * Mischung aus <b>Rot</b> und <b>Violett</b>).
     *
     * @param redPurple Die Tertiärfarbe <b>Rot-Violett</b> (englisch: <b>red
     *     purple</b>, Mischung aus <b>Rot</b> und <b>Violett</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme redPurple(Color redPurple)
    {
        this.redPurple = redPurple;
        return this;
    }

    /**
     * Setzt die Tertiärfarbe <b>Rot-Violett</b> (englisch: <b>red purple</b>,
     * Mischung aus <b>Rot</b> und <b>Violett</b>) durch Angabe des <b>Rot-,
     * Grün- und Blau-Anteils</b>.
     *
     * @param r Der Rot-Anteil der Farbe im Bereich von {@code 0} - {@code 255}.
     * @param g Der Grün-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     * @param b Der Blau-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme redPurple(int r, int g, int b)
    {
        redPurple = new Color(r, g, b);
        return this;
    }

    /**
     * Setzt die Tertiärfarbe <b>Rot-Violett</b> (englisch: <b>red purple</b>,
     * Mischung aus <b>Rot</b> und <b>Violett</b>) in hexadezimaler Codierung
     * (z. B. {@code #ff0000}).
     *
     * @param redPurple Die Tertiärfarbe <b>Rot-Violett</b> (englisch: <b>red
     *     purple</b>, Mischung aus <b>Rot</b> und <b>Violett</b>) in
     *     hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme redPurple(String redPurple)
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
     *     Mischung aus <b>Rot</b> und <b>Blau</b>).
     */
    @Getter
    public Color purple()
    {
        if (purple == null)
        {
            return purple = mix(red(), blue());
        }
        return purple;
    }

    /**
     * Setzt die Sekundärfarbe <b>Violett</b> (englisch: <b>purple</b>, Mischung
     * aus <b>Rot</b> und <b>Blau</b>).
     *
     * @param purple Die Sekundärfarbe <b>Violett</b> (englisch: <b>purple</b>,
     *     Mischung aus <b>Rot</b> und <b>Blau</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme purple(Color purple)
    {
        this.purple = purple;
        return this;
    }

    /**
     * Setzt die Sekundärfarbe <b>Violett</b> (englisch: <b>purple</b>, Mischung
     * aus <b>Rot</b> und <b>Blau</b>) durch Angabe des <b>Rot-, Grün- und
     * Blau-Anteils</b>.
     *
     * @param r Der Rot-Anteil der Farbe im Bereich von {@code 0} - {@code 255}.
     * @param g Der Grün-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     * @param b Der Blau-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme purple(int r, int g, int b)
    {
        purple = new Color(r, g, b);
        return this;
    }

    /**
     * Setzt die Sekundärfarbe <b>Violett</b> (englisch: <b>purple</b>, Mischung
     * aus <b>Rot</b> und <b>Blau</b>) in hexadezimaler Codierung (z. B.
     * {@code #ff0000}).
     *
     * @param purple Die Sekundärfarbe <b>Violett</b> (englisch: <b>purple</b>,
     *     Mischung aus <b>Rot</b> und <b>Blau</b>) in hexadezimaler Codierung
     *     (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme purple(String purple)
    {
        this.purple = decode(purple);
        return this;
    }

    /**
     * Gibt die Tertiärfarbe <b>Blau-Violett</b> (englisch: <b>blue purple</b>)
     * - eine Mischung aus <b>Violett</b> und <b>Blau</b> - zurück.
     *
     * @return Die Tertiärfarbe <b>Blau-Violett</b> (englisch: <b>blue
     *     purple</b>, Mischung aus <b>Violett</b> und <b>Blau</b>).
     */
    @Getter
    public Color bluePurple()
    {
        if (bluePurple == null)
        {
            bluePurple = mix(purple(), blue());
        }
        return bluePurple;
    }

    /**
     * Setzt die Tertiärfarbe <b>Blau-Violett</b> (englisch: <b>blue purple</b>,
     * Mischung aus <b>Violett</b> und <b>Blau</b>).
     *
     * @param bluePurple Die Tertiärfarbe <b>Blau-Violett</b> (englisch: <b>blue
     *     purple</b>, Mischung aus <b>Violett</b> und <b>Blau</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme bluePurple(Color bluePurple)
    {
        this.bluePurple = bluePurple;
        return this;
    }

    /**
     * Setzt die Tertiärfarbe <b>Blau-Violett</b> (englisch: <b>blue purple</b>,
     * Mischung aus <b>Violett</b> und <b>Blau</b>) durch Angabe des <b>Rot-,
     * Grün- und Blau-Anteils</b>.
     *
     * @param r Der Rot-Anteil der Farbe im Bereich von {@code 0} - {@code 255}.
     * @param g Der Grün-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     * @param b Der Blau-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme bluePurple(int r, int g, int b)
    {
        bluePurple = new Color(r, g, b);
        return this;
    }

    /**
     * Setzt die Tertiärfarbe <b>Blau-Violett</b> (englisch: <b>blue purple</b>,
     * Mischung aus <b>Violett</b> und <b>Blau</b>) in hexadezimaler Codierung
     * (z. B. {@code #ff0000}).
     *
     * @param bluePurple Die Tertiärfarbe <b>Blau-Violett</b> (englisch: <b>blue
     *     purple</b>, Mischung aus <b>Violett</b> und <b>Blau</b>) in
     *     hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme bluePurple(String bluePurple)
    {
        this.bluePurple = decode(bluePurple);
        return this;
    }

    /**
     * Gibt die Primärfarbe <b>Blau</b> (englisch: <b>blue</b>) zurück.
     *
     * @return Die Primärfarbe <b>Blau</b> (englisch: <b>blue</b>).
     */
    @Getter
    public Color blue()
    {
        return blue;
    }

    /**
     * Setzt die Primärfarbe <b>Blau</b> (englisch: <b>blue</b>).
     *
     * @param blue Die Primärfarbe <b>Blau</b> (englisch: <b>blue</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme blue(Color blue)
    {
        this.blue = blue;
        return this;
    }

    /**
     * Setzt die Primärfarbe <b>Blau</b> (englisch: <b>blue</b>) durch Angabe
     * des <b>Rot-, Grün- und Blau-Anteils</b>.
     *
     * @param r Der Rot-Anteil der Farbe im Bereich von {@code 0} - {@code 255}.
     * @param g Der Grün-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     * @param b Der Blau-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme blue(int r, int g, int b)
    {
        blue = new Color(r, g, b);
        return this;
    }

    /**
     * Setzt die Primärfarbe <b>Blau</b> (englisch: <b>blue</b>) in
     * hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @param blue Die Primärfarbe <b>Blau</b> (englisch: <b>blue</b>) in
     *     hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme blue(String blue)
    {
        this.blue = decode(blue);
        return this;
    }

    /**
     * Gibt die Tertiärfarbe <b>Blau-Grün</b> (englisch: <b>blue green</b>) -
     * eine Mischung aus <b>Blau</b> und <b>Grün</b> - zurück.
     *
     * @return Die Tertiärfarbe <b>Blau-Grün</b> (englisch: <b>blue green</b>,
     *     Mischung aus <b>Blau</b> und <b>Grün</b>).
     */
    @Getter
    public Color blueGreen()
    {
        if (blueGreen == null)
        {
            blueGreen = mix(blue(), green());
        }
        return blueGreen;
    }

    /**
     * Setzt die Tertiärfarbe <b>Blau-Grün</b> (englisch: <b>blue green</b>,
     * Mischung aus <b>Blau</b> und <b>Grün</b>).
     *
     * @param blueGreen Die Tertiärfarbe <b>Blau-Grün</b> (englisch: <b>blue
     *     green</b>, Mischung aus <b>Blau</b> und <b>Grün</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme blueGreen(Color blueGreen)
    {
        this.blueGreen = blueGreen;
        return this;
    }

    /**
     * Setzt die Tertiärfarbe <b>Blau-Grün</b> (englisch: <b>blue green</b>,
     * Mischung aus <b>Blau</b> und <b>Grün</b>) durch Angabe des <b>Rot-, Grün-
     * und Blau-Anteils</b>.
     *
     * @param r Der Rot-Anteil der Farbe im Bereich von {@code 0} - {@code 255}.
     * @param g Der Grün-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     * @param b Der Blau-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme blueGreen(int r, int g, int b)
    {
        blueGreen = new Color(r, g, b);
        return this;
    }

    /**
     * Setzt die Tertiärfarbe <b>Blau-Grün</b> (englisch: <b>blue green</b>,
     * Mischung aus <b>Blau</b> und <b>Grün</b>) in hexadezimaler Codierung (z.
     * B. {@code #ff0000}).
     *
     * @param blueGreen Die Tertiärfarbe <b>Blau-Grün</b> (englisch: <b>blue
     *     green</b>, Mischung aus <b>Blau</b> und <b>Grün</b>) in hexadezimaler
     *     Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme blueGreen(String blueGreen)
    {
        this.blueGreen = decode(blueGreen);
        return this;
    }

    /**
     * Gibt die Sekundärfarbe <b>Grün</b> (englisch: <b>green</b>) (Mischung aus
     * <b>Gelb</b> und <b>Blau</b>) zurück.
     *
     * @return Die Sekundärfarbe <b>Grün</b> (englisch: <b>green</b>, Mischung
     *     aus <b>Gelb</b> und <b>Blau</b>).
     */
    @Getter
    public Color green()
    {
        if (green == null)
        {
            green = mix(yellow(), blue());
        }
        return green;
    }

    /**
     * Setzt die Sekundärfarbe <b>Grün</b> (Mischung aus <b>Gelb</b> und
     * <b>Blau</b>).
     *
     * @param green Die Sekundärfarbe <b>Grün</b> (Mischung aus <b>Gelb</b> und
     *     <b>Blau</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme green(Color green)
    {
        this.green = green;
        return this;
    }

    /**
     * Setzt die Sekundärfarbe <b>Grün</b> (Mischung aus <b>Gelb</b> und
     * <b>Blau</b>) durch Angabe des <b>Rot-, Grün- und Blau-Anteils</b>.
     *
     * @param r Der Rot-Anteil der Farbe im Bereich von {@code 0} - {@code 255}.
     * @param g Der Grün-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     * @param b Der Blau-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme green(int r, int g, int b)
    {
        green = new Color(r, g, b);
        return this;
    }

    /**
     * Setzt die Sekundärfarbe <b>Grün</b> (Mischung aus <b>Gelb</b> und
     * <b>Blau</b>) in hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @param green Die Sekundärfarbe <b>Grün</b> (Mischung aus <b>Gelb</b> und
     *     <b>Blau</b>) in hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme green(String green)
    {
        this.green = decode(green);
        return this;
    }

    /**
     * Gibt die Tertiärfarbe <b>Gelb-Grün</b> (englisch: <b>yellow green</b>) -
     * eine Mischung aus <b>Gelb</b> und <b>Grün</b> - zurück.
     *
     * @return Die Tertiärfarbe <b>Gelb-Grün</b> (englisch: <b>yellow green</b>,
     *     Mischung aus <b>Gelb</b> und <b>Grün</b>).
     */
    @Getter
    public Color yellowGreen()
    {
        if (yellowGreen == null)
        {
            yellowGreen = mix(yellow(), green());
        }
        return yellowGreen;
    }

    /**
     * Setzt die Tertiärfarbe <b>Gelb-Grün</b> (englisch: <b>yellow green</b>,
     * Mischung aus <b>Gelb</b> und <b>Grün</b>).
     *
     * @param yellowGreen Die Tertiärfarbe <b>Gelb-Grün</b> (englisch: <b>yellow
     *     green</b>, Mischung aus <b>Gelb</b> und <b>Grün</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme yellowGreen(Color yellowGreen)
    {
        this.yellowGreen = yellowGreen;
        return this;
    }

    /**
     * Setzt die Tertiärfarbe <b>Gelb-Grün</b> (englisch: <b>yellow green</b>,
     * Mischung aus <b>Gelb</b> und <b>Grün</b>) durch Angabe des <b>Rot-, Grün-
     * und Blau-Anteils</b>.
     *
     * @param r Der Rot-Anteil der Farbe im Bereich von {@code 0} - {@code 255}.
     * @param g Der Grün-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     * @param b Der Blau-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme yellowGreen(int r, int g, int b)
    {
        yellowGreen = new Color(r, g, b);
        return this;
    }

    /**
     * Setzt die Tertiärfarbe <b>Gelb-Grün</b> (englisch: <b>yellow green</b>,
     * Mischung aus <b>Gelb</b> und <b>Grün</b>) in hexadezimaler Codierung (z.
     * B. {@code #ff0000}).
     *
     * @param yellowGreen Die Tertiärfarbe <b>Gelb-Grün</b> (englisch: <b>yellow
     *     green</b>, Mischung aus <b>Gelb</b> und <b>Grün</b>) in hexadezimaler
     *     Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme yellowGreen(String yellowGreen)
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
    @Getter
    public Color brown()
    {
        if (brown == null)
        {
            brown = mix(red(), green(), 0.35);
        }
        return brown;
    }

    /**
     * Setzt die Farbe <b>Braun</b> (englisch: <b>brown</b>).
     *
     * @param brown Die Farbe <b>Braun</b> (englisch: <b>brown</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme brown(Color brown)
    {
        this.brown = brown;
        return this;
    }

    /**
     * Setzt die Farbe <b>Braun</b> (englisch: <b>brown</b>) durch Angabe des
     * <b>Rot-, Grün- und Blau-Anteils</b>.
     *
     * @param r Der Rot-Anteil der Farbe im Bereich von {@code 0} - {@code 255}.
     * @param g Der Grün-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     * @param b Der Blau-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme brown(int r, int g, int b)
    {
        brown = new Color(r, g, b);
        return this;
    }

    /**
     * Setzt die Farbe <b>Braun</b> (englisch: <b>brown</b>) in hexadezimaler
     * Codierung (z. B. {@code #ff0000}).
     *
     * @param brown Die Farbe <b>Braun</b> (englisch: <b>brown</b>) in
     *     hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme brown(String brown)
    {
        this.brown = decode(brown);
        return this;
    }

    /**
     * Gibt die Farbe <b>Weiß</b> (englisch: <b>white</b>) zurück.
     *
     * @return Die Farbe <b>Weiß</b> (englisch: <b>white</b>).
     */
    @Getter
    public Color white()
    {
        return white;
    }

    /**
     * Setzt die Farbe <b>Weiß</b> (englisch: <b>white</b>).
     *
     * @param white Die Farbe <b>Weiß</b> (englisch: <b>white</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme white(Color white)
    {
        this.white = white;
        return this;
    }

    /**
     * Setzt die Farbe <b>Weiß</b> (englisch: <b>white</b>) durch Angabe des
     * <b>Rot-, Grün- und Blau-Anteils</b>.
     *
     * @param r Der Rot-Anteil der Farbe im Bereich von {@code 0} - {@code 255}.
     * @param g Der Grün-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     * @param b Der Blau-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme white(int r, int g, int b)
    {
        white = new Color(r, g, b);
        return this;
    }

    /**
     * Setzt die Farbe <b>Weiß</b> (englisch: <b>white</b>) in hexadezimaler
     * Codierung (z. B. {@code #ff0000}).
     *
     * @param white Die Farbe <b>Weiß</b> (englisch: <b>white</b>) in
     *     hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme white(String white)
    {
        this.white = decode(white);
        return this;
    }

    /**
     * Gibt die Farbe <b>Grau</b> (englisch: <b>gray</b>) zurück.
     *
     * @return Die Farbe <b>Grau</b> (englisch: <b>gray</b>).
     */
    @Getter
    public Color gray()
    {
        return gray;
    }

    /**
     * Setzt die Farbe <b>Grau</b> (englisch: <b>gray</b>).
     *
     * @param gray Die Farbe <b>Grau</b> (englisch: <b>gray</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme gray(Color gray)
    {
        this.gray = gray;
        return this;
    }

    /**
     * Setzt die Farbe <b>Grau</b> (englisch: <b>gray</b>) durch Angabe des
     * <b>Rot-, Grün- und Blau-Anteils</b>.
     *
     * @param r Der Rot-Anteil der Farbe im Bereich von {@code 0} - {@code 255}.
     * @param g Der Grün-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     * @param b Der Blau-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme gray(int r, int g, int b)
    {
        gray = new Color(r, g, b);
        return this;
    }

    /**
     * Setzt die Farbe <b>Grau</b> (englisch: <b>gray</b>) in hexadezimaler
     * Codierung (z. B. {@code #ff0000}).
     *
     * @param gray Die Farbe <b>Grau</b> (englisch: <b>gray</b>) in
     *     hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme gray(String gray)
    {
        this.gray = decode(gray);
        return this;
    }

    /**
     * Gibt die Farbe <b>Schwarz</b> (englisch: <b>black</b>) zurück.
     *
     * @return Die Farbe <b>Schwarz</b> (englisch: <b>black</b>).
     */
    @Getter
    public Color black()
    {
        return black;
    }

    /**
     * Setzt die Farbe <b>Schwarz</b> (englisch: <b>black</b>).
     *
     * @param black Die Farbe <b>Schwarz</b> (englisch: <b>black</b>).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme black(Color black)
    {
        this.black = black;
        return this;
    }

    /**
     * Setzt die Farbe <b>Schwarz</b> (englisch: <b>black</b>) durch Angabe des
     * <b>Rot-, Grün- und Blau-Anteils</b>.
     *
     * @param r Der Rot-Anteil der Farbe im Bereich von {@code 0} - {@code 255}.
     * @param g Der Grün-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     * @param b Der Blau-Anteil der Farbe im Bereich von {@code 0} -
     *     {@code 255}.
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme black(int r, int g, int b)
    {
        black = new Color(r, g, b);
        return this;
    }

    /**
     * Setzt die Farbe <b>Schwarz</b> (englisch: <b>black</b>) in hexadezimaler
     * Codierung (z. B. {@code #ff0000}).
     *
     * @param black Die Farbe <b>Schwarz</b> (englisch: <b>black</b>) in
     *     hexadezimaler Codierung (z. B. {@code #ff0000}).
     *
     * @return Die Instanz dieses Farbschemas, damit mehrere Setter mit der
     *     Punktschreibweise hintereinander geschrieben werden können, z. B.
     *     {@code scheme.green(..).blue(..)}.
     */
    @Setter
    public ColorScheme black(String black)
    {
        this.black = decode(black);
        return this;
    }

    /**
     * Gibt die zwölf Farben des Farbkreises von Itten in der Reihenfolge von
     * Gelb ausgehend zurück.
     *
     * <ol>
     * <li>{@code yellow} (<b>Gelb</b>)</li>
     * <li>{@code yellow orange} (<b>Gelb-Orange</b>)</li>
     * <li>{@code orange} (<b>Orange</b>)</li>
     * <li>{@code red orange} (<b>Rot-Orange</b>)</li>
     * <li>{@code red} (<b>Rot</b>)</li>
     * <li>{@code red purple} (<b>Rot-Violett</b>)</li>
     * <li>{@code purple} (<b>Violett</b>)</li>
     * <li>{@code blue purple} (<b>Blau-Violett</b>)</li>
     * <li>{@code blue} (<b>Blau</b>)</li>
     * <li>{@code blue green} (<b>Blau-Grün</b>)</li>
     * <li>{@code green} (<b>Grün</b>)</li>
     * <li>{@code yellow green} (<b>Gelb-Grün</b>)</li>
     * </ol>
     *
     * @return Die zwölf Farben des Farbkreises von Itten in der richtigen
     *     Reihenfolge.
     */
    @Getter
    public Color[] wheelColors()
    {
        return new Color[] { yellow(), yellowOrange(), orange(), redOrange(),
                red(), redPurple(), purple(), bluePurple(), blue(), blueGreen(),
                green(), yellowGreen() };
    }

    /**
     * Gibt die drei Primärfarben des Farbkreises von Itten in der Reihenfolge
     * <b>Gelb</b>, <b>Rot</b> und <b>Blau</b> aus.
     *
     * @return Die drei Primärfarben des Farbkreises.
     */
    @Getter
    public Color[] primaryColors()
    {
        return new Color[] { yellow(), red(), blue() };
    }

    /**
     * Gibt die drei Sekundärfarben des Farbkreises von Itten in der Reihenfolge
     * <b>Orange</b>, <b>Violett</b> und <b>Grün</b> aus.
     *
     * @return Die drei Sekundärfarben des Farbkreises.
     */
    @Getter
    public Color[] secondaryColors()
    {
        return new Color[] { orange(), purple(), green() };
    }

    /**
     * Gibt vier <b>zusätzlichen</b> Farben aus: <b>Braun</b>, <b>Weiß</b>,
     * <b>Grau</b> und <b>Schwarz</b>.
     *
     * @return Die vier <b>zusätzlichen</b> Farben <b>Braun</b>, <b>Weiß</b>,
     *     <b>Grau</b> und <b>Schwarz</b>.
     */
    @Getter
    public Color[] extraColors()
    {
        return new Color[] { brown(), white(), gray(), black() };
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new Scene()
        {
            {
                backgroundColor(colors.get("#444444"));
                int x = -16;
                int labelY = -2;
                for (Entry<String, Color> entry : colors.getAll().entrySet())
                {
                    add(new Circle().position(x, 0).color(entry.getValue()));
                    add(new Text(entry.getKey(), 0.5).position(x, labelY)
                            .color("white"));
                    x += 2;
                    labelY -= 2;
                    if (labelY < -4)
                    {
                        labelY = -2;
                    }
                }
            }
        }, 1200, 400);
    }
}
