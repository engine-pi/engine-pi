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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import pi.annotations.API;
import pi.annotations.Getter;
import pi.resources.Container;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/docs/manual/resources/colors.md

/**
 * Ein <b>Speicher</b> für <b>Farben</b> des Datentyps {@link Color}.
 *
 * <p>
 * Die Farben werden in einer {@link Map} unter einem <b>Farbnamen</b> abgelegt.
 * Neben dem Hauptfarbnamen können weitere Farbnamen als <b>Aliasse</b>
 * gespeichert werden.
 * </p>
 *
 * <p>
 * Bei den Farbnamen werden sowohl die Groß- und Kleinschreibung als auch
 * Leerzeichen ignoriert. In den Farbnamen können deutsche Umlaute verwendet
 * oder umschrieben werden (z. B. ae, oe, ue, ss). Der Binde- und der
 * Unterstrich werden ebenfalls nicht berücksichtigt.
 * </p>
 *
 * <p>
 * Die zwölf Farben des Farbkreises von Itten zusammen mit ihren Aliassen bzw.
 * Synonymen:
 * </p>
 *
 * <ol>
 * <li>{@code yellow}: {@code Gelb}, {@code Hellgelb}</li>
 * <li>{@code yellow orange}: {@code orange yellow}, {@code gold},
 * {@code Gelb-Orange}, {@code Orange-Gelb}, {@code Golden},
 * {@code Dunkelgelb}</li>
 * <li>{@code orange}: {@code Orange}</li>
 * <li>{@code red orange}: {@code orange red}, {@code brick red}, {@code brick},
 * {@code Rot-Orange}, {@code Orange-Rot}, {@code Ziegelrot},
 * {@code Hellrot}</li>
 * <li>{@code red}: {@code Rot}</li>
 * <li>{@code red purple}: {@code purple red}, {@code magenta}, {@code pink},
 * {@code Rot-Violett}, {@code Violett-Rot}, {@code Rosa}</li>
 * <li>{@code purple}: {@code Violet}, {@code Violett}, {@code Lila}</li>
 * <li>{@code blue purple}: {@code purple blue}, {@code indigo},
 * {@code Violett Blau}, {@code Blau Violett}</li>
 * <li>{@code blue}: {@code Blau}</li>
 * <li>{@code blue green}: {@code green blue}, {@code cyan}, {@code Blau-Grün},
 * {@code Grün-Blau}, {@code Türkis}</li>
 * <li>{@code green}: {@code Grün}</li>
 * <li>{@code yellow green}: {@code green yellow}, {@code lime},
 * {@code lime green}, {@code Gelb-Grün}, {@code Grün-Gelb},
 * {@code Limetten Grün}, {@code Limette}, {@code Hellgrün}</li>
 * </ol>
 *
 * Diese Farben sind ebenfalls im Speicher für Farben enthalten (gehören aber
 * nicht zum Farbkreis von Itten):
 *
 * <ul>
 * <li>{@code brown}: {@code Braun}</li>
 * <li>{@code white}: {@code Weiß}</li>
 * <li>{@code gray}: {@code grey}, {@code Grau}</li>
 * <li>{@code black}: {@code Schwarz}</li>
 * </ul>
 *
 * @see pi.Controller#colors
 *
 * @author Josef Friedrich
 */
public class ColorContainer implements Container<Color>
{
    /**
     * Wir verwenden {@link LinkedHashMap}, damit die Einfügereihenfolge
     * erhalten bleibt und die Farbe sortiert ausgegeben werden können.
     */
    private final Map<String, Color> resources = new LinkedHashMap<>();

    /**
     * Ein Speicher für die Aliasse.
     */
    private final Map<String, String> aliases = new HashMap<>();

    /**
     * Normalisiert einen Farbnamen.
     *
     * @param name Ein Farbname, der noch nicht normalisiert wurde
     *     (beispielsweise {@code Gelb-Grün}).
     *
     * @return Ein normalisierter Farbname (beispielsweise {@code gelbgruen}).
     */
    private String normalizeName(String name)
    {
        return name.toLowerCase().replaceAll("\\s", "").replaceAll("-", "")
                .replaceAll("_", "").replaceAll("ä", "ae").replaceAll("ö", "oe")
                .replaceAll("ü", "ue").replaceAll("ß", "ss");
    }

    /**
     * Fügt dem Speicher für Farben eine <b>Farbe</b> unter einem <b>Namen</b>
     * hinzu.
     *
     * @param name Der Farbname.
     * @param color Die Farbe.
     *
     * @return Die gleiche Farbe, die hinzugefügt wurde.
     */
    @API
    public Color add(String name, Color color)
    {
        resources.put(normalizeName(name), color);
        return color;
    }

    /**
     * Fügt dem Speicher für Farben eine <b>Farbe</b> durch Angabe der <b>drei
     * Farbanteile</b> und des <b>Alphakanals</b> in dezimaler Notation hinzu.
     *
     * @param name Der Farbname.
     * @param r Der Rotanteil der Farbe (0-255).
     * @param g Der Gelbanteil der Farbe (0-255).
     * @param b Der Blauanteil der Farbe (0-255).
     * @param a Der
     *     <a href="https://de.wikipedia.org/wiki/Alphakanal">Alphakanal</a> als
     *     Ganzzahl von {@code 0} bis {@code 255}. Dem Wert {@code 0} entspricht
     *     das Attribut <em>„vollständig transparent“</em>, d. h. unsichtbar.
     *     {@code 255} entspricht <em>„nicht transparent“</em>.
     *
     * @return Die gleiche Farbe, die hinzugefügt wurde.
     *
     * @since 0.26.0
     */
    @API
    public Color add(String name, int r, int g, int b, int a)
    {
        return add(name, new Color(r, g, b, a));
    }

    /**
     * Fügt dem Speicher für Farben eine <b>Farbe</b> durch Angabe der <b>drei
     * Farbanteile</b>, des <b>Alphakanals</b> in dezimaler Notation und
     * beliebig vieler <b>Aliasse</b> hinzu.
     *
     * @param name Der Farbname.
     * @param r Der Rotanteil der Farbe (0-255).
     * @param g Der Gelbanteil der Farbe (0-255).
     * @param b Der Blauanteil der Farbe (0-255).
     * @param a Der
     *     <a href="https://de.wikipedia.org/wiki/Alphakanal">Alphakanal</a> als
     *     Ganzzahl von {@code 0} bis {@code 255}. Dem Wert {@code 0} entspricht
     *     das Attribut <em>„vollständig transparent“</em>, d. h. unsichtbar.
     *     {@code 255} entspricht <em>„nicht transparent“</em>.
     * @param alias Beliebig viele weitere Farbnamen, die als Aliasse dienen.
     *
     * @return Die gleiche Farbe, die hinzugefügt wurde.
     *
     * @since 0.26.0
     */
    @API
    public Color add(String name, int r, int g, int b, int a, String... alias)
    {
        return add(name, new Color(r, g, b, a), alias);
    }

    /**
     * Fügt dem Speicher für Farben eine <b>Farbe</b> durch Angabe der <b>drei
     * Farbanteile</b> in dezimaler Notation hinzu.
     *
     * @param name Der Farbname.
     * @param r Der Rotanteil der Farbe (0-255).
     * @param g Der Gelbanteil der Farbe (0-255).
     * @param b Der Blauanteil der Farbe (0-255).
     *
     * @return Die gleiche Farbe, die hinzugefügt wurde.
     *
     * @since 0.26.0
     */
    @API
    public Color add(String name, int r, int g, int b)
    {
        return add(name, new Color(r, g, b));
    }

    /**
     * Fügt dem Speicher für Farben eine <b>Farbe</b> durch Angabe der <b>drei
     * Farbanteile</b> in dezimaler Notation und beliebig vieler <b>Aliasse</b>
     * hinzu.
     *
     * @param name Der Farbname.
     * @param r Der Rotanteil der Farbe (0-255).
     * @param g Der Gelbanteil der Farbe (0-255).
     * @param b Der Blauanteil der Farbe (0-255).
     * @param alias Beliebig viele weitere Farbnamen, die als Aliasse dienen.
     *
     * @return Die gleiche Farbe, die hinzugefügt wurde.
     *
     * @since 0.26.0
     */
    @API
    public Color add(String name, int r, int g, int b, String... alias)
    {
        return add(name, new Color(r, g, b), alias);
    }

    /**
     * Fügt dem Speicher für Farben eine Farbe in <b>hexadezimaler</b> Codierung
     * unter einem <b>Namen</b> hinzu.
     *
     * @param name Der Farbname.
     * @param color Die Farbe in hexadezimaler Codierung.
     *
     * @return Die gleiche Farbe, die hinzugefügt wurde.
     */
    @API
    public Color add(String name, String color)
    {
        return add(normalizeName(name), ColorUtil.decode(color));
    }

    /**
     * Fügt dem Speicher für Farben eine <b>Farbe</b> unter einem <b>Namen</b>
     * und beliebig vieler <b>Aliasse</b> hinzu.
     *
     * @param name Der Farbname.
     * @param color Die Farbe.
     * @param alias Beliebig viele weitere Farbnamen, die als Aliasse dienen.
     *
     * @return Die gleiche Farbe, die hinzugefügt wurde.
     */
    @API
    public Color add(String name, Color color, String... alias)
    {
        addAlias(name, alias);
        return add(name, color);
    }

    /**
     * Fügt dem Speicher für Farben eine Farbe in <b>hexadezimaler</b> Codierung
     * unter einem Namen und beliebig vieler <b>Aliasse</b> hinzu.
     *
     * @param name Der Farbname.
     * @param color Die Farbe in hexadezimaler Codierung.
     * @param alias Beliebig viele weitere Farbnamen, die als Aliasse dienen.
     *
     * @return Die gleiche Farbe, die hinzugefügt wurde.
     */
    @API
    public Color add(String name, String color, String... alias)
    {
        return add(normalizeName(name), ColorUtil.decode(color), alias);
    }

    /**
     * Fügt Aliase für einen Farbnamen hinzu.
     *
     * @param name Der primäre Farbnamen, dem die Aliase zugeordnet werden
     *     sollen. Der Name wird normalisiert, bevor er verwendet wird.
     * @param alias Ein oder mehrere alternative Namen (Aliase) für die Farbe.
     *     Jeder Alias wird normalisiert und dem primären Namen zugeordnet.
     */
    @API
    public void addAlias(String name, String... alias)
    {
        name = normalizeName(name);
        for (String a : alias)
        {
            aliases.put(normalizeName(a), name);
        }
    }

    /**
     * Fügt <b>alle</b> Farben eines <b>Farbschemas</b> dem Speicher für Farben
     * hinzu.
     *
     * <p>
     * Die Farben werden in einer {@link Map} unter dem englischen Farbnamen
     * abgelegt. Neben dem englischen Hauptfarbnamen werden weitere englische
     * und deutsche Farbnamen als Aliasse gespeichert. Auf eine Farbe des
     * Farbenschemas kann deshalb mit mehreren Farbnamen zugegriffen werden.
     * </p>
     *
     * <p>
     * Die Reihenfolge der zusammengesetzten Tertiärfarbnamen ist eigentlich
     * festgelegt: Primärfarbname, dann Sekundärfarbname (Gelb-Orange nicht
     * Orange-Gelb). Wir fügen jedoch auch Namen mit der falschen Reihenfolge
     * zum Speicher hinzu.
     * </p>
     *
     * <p>
     * Wird ein neues Farbschema gesetzt, werden alle sich bereits im Speicher
     * befindenden Farben gelöscht.
     * </p>
     *
     * @param schema Das Farbschema, dessen Farben in den Speicher für Farben
     *     abgelegt werden soll.
     */
    @API
    public void addScheme(ColorScheme schema)
    {
        clear();
        // Primärfarbe
        add("yellow", schema.yellow(), "Gelb", "Hellgelb");
        // Tertiärfarbe
        add("yellow orange", schema.yellowOrange(), "orange yellow", "gold",
                "Gelb-Orange", "Orange-Gelb", "Golden", "Dunkelgelb");
        // Sekundärfarbe
        add("orange", schema.orange());
        // Tertiärfarbe
        add("red orange", schema.redOrange(), "orange red", "brick red",
                "brick", "Rot-Orange", "Orange-Rot", "Ziegelrot", "Hellrot");
        // Primärfarbe
        add("red", schema.red(), "Rot");
        // Tertiärfarbe
        add("red purple", schema.redPurple(), "purple red", "magenta", "pink",
                "Rot-Violett", "Violett-Rot", "Rosa");
        // Sekundärfarbe
        add("purple", schema.purple(), "Violet", "Violett", "Lila");
        // Tertiärfarbe
        add("blue purple", schema.bluePurple(), "purple blue", "indigo",
                "Violett Blau", "Blau Violett");
        // Primärfarbe
        add("blue", schema.blue(), "Blau");
        // Tertiärfarbe
        add("blue green", schema.blueGreen(), "green blue", "cyan", "Blau-Grün",
                "Grün-Blau", "Türkis");
        // Sekundärfarbe
        add("green", schema.green(), "Grün");
        // Tertiärfarbe
        add("yellow green", schema.yellowGreen(), "green yellow", "lime",
                "lime green", "Gelb-Grün", "Grün-Gelb", "Limetten Grün",
                "Limette", "Hellgrün");
        // andere Zusammensetzung, nicht nach Itten.
        add("brown", schema.brown(), "Braun");
        add("white", schema.white(), "Weiß");
        add("gray", schema.gray(), "grey", "Grau");
        add("black", schema.black(), "Schwarz");
    }

    /**
     * Gibt <b>immer</b> eine <b>vordefinierte</b> Farbe zurück und wirft <b>nie
     * eine Ausnahme</b>.
     *
     * <p>
     * Die Farben können auch in hexadezimaler Schreibweise angegeben werden, z.
     * B. {@code #ff0000}. Die Groß- und Kleinschreibung spielt keine Rolle.
     * Auch Leerzeichen werden ignoriert.
     * </p>
     *
     * @param name Ein Farbname, ein Farbalias ({@link ColorContainer siehe
     *     Auflistung}) oder eine Farbe in hexadezimaler Codierung (z. B.
     *     {@code #ff0000}).
     *
     * @return Eine vordefinierte Farbe.
     *
     * @see pi.actor.Actor#color(String)
     * @see pi.Scene#backgroundColor(String)
     */
    @API
    public Color getSafe(String name)
    {
        try
        {
            return get(name);
        }
        catch (Exception e)
        {
            // Ignore
        }
        ColorScheme scheme = PredefinedColorScheme.GNOME.getScheme();
        return switch (normalizeName(name))
        {
        case "yellow", "gelb", "hellgelb" -> scheme.yellow();
        // Tertiärfarbe
        case "yelloworange", "orangeyellow", "gold", "gelborange", "orangegelb",
                "golden", "dunkelgelb" ->
            scheme.yellowOrange();
        // Sekundärfarbe
        case "orange" -> scheme.orange();
        // Tertiärfarbe
        case "redorange", "orangered", "brickred", "brick", "rotorange",
                "orangerot", "ziegelrot", "hellrot" ->
            scheme.redOrange();
        // Primärfarbe
        case "red", "rot" -> scheme.red();
        // Tertiärfarbe
        case "redpurple", "purplered", "magenta", "pink", "rotviolett",
                "violettrot", "rosa" ->
            scheme.redPurple();
        // Sekundärfarbe
        case "purple", "violet", "violett", "lila" -> scheme.purple();
        // Tertiärfarbe
        case "bluepurple", "purpleblue", "indigo", "violettblau",
                "blauviolett" ->
            scheme.bluePurple();
        // Primärfarbe
        case "blue", "blau" -> scheme.blue();
        // Tertiärfarbe
        case "bluegreen", "greenblue", "cyan", "blaugruen", "gruenblau",
                "tuerkis" ->
            scheme.blueGreen();
        // Sekundärfarbe
        case "green", "gruen" -> scheme.green();
        // Tertiärfarbe
        case "yellowgreen", "greenyellow", "lime", "limegreen", "gelbgruen",
                "gruengelb", "limettengruen", "limette", "hellgruen" ->
            scheme.yellowGreen();
        // andere Zusammensetzung, nicht nach Itten.
        case "brown", "braun" -> scheme.brown();
        case "white", "weiss" -> scheme.white();
        case "gray", "grey", "grau" -> scheme.gray();
        case "black", "schwarz" -> scheme.black();
        default -> Color.BLACK;
        };
    }

    /**
     * Gibt <b>immer</b> eine <b>vordefinierte</b> Farbe zurück und wirft <b>nie
     * eine Ausnahme</b>.
     *
     * <p>
     * Die Farben können auch in hexadezimaler Schreibweise angegeben werden, z.
     * B. {@code #ff0000}. Die Groß- und Kleinschreibung spielt keine Rolle.
     * Auch Leerzeichen werden ignoriert.
     * </p>
     *
     * @param name Ein Farbname, ein Farbalias ({@link ColorContainer siehe
     *     Auflistung}) oder eine Farbe in hexadezimaler Codierung (z. B.
     *     {@code #ff0000}).
     * @param alpha Der
     *     <a href="https://de.wikipedia.org/wiki/Alphakanal">Alphakanal</a> als
     *     Ganzzahl von {@code 0} bis {@code 255}. Dem Wert {@code 0} entspricht
     *     das Attribut <em>„vollständig transparent“</em>, d. h. unsichtbar.
     *     {@code 255} entspricht <em>„nicht transparent“</em>.
     *
     * @return Eine vordefinierte Farbe.
     *
     * @see pi.actor.Actor#color(String)
     * @see pi.Scene#backgroundColor(String)
     */
    @API
    public Color getSafe(String name, int alpha)
    {
        return ColorUtil.changeAlpha(getSafe(name), alpha);
    }

    /**
     * Gibt <b>alle</b> Farben samt der Farbnamen als {@link Map} zurück.
     *
     * @return Alle Farben samt der Farbnamen als {@link Map} zurück.
     */
    @API
    public Map<String, Color> getAll()
    {
        return resources;
    }

    /**
     * <b>Leert</b> den Speicher für Farben samt der Aliasse.
     */
    @API
    public void clear()
    {
        resources.clear();
        aliases.clear();
    }

    /**
     * Gibt eine <b>vordefinierte</b> Farbe zurück.
     *
     * <p>
     * Die Farben können auch in hexadezimaler Schreibweise angegeben werden, z.
     * B. {@code #ff0000}. Die Groß- und Kleinschreibung spielt keine Rolle.
     * Auch Leerzeichen werden ignoriert.
     * </p>
     *
     * @param name Ein Farbname, ein Farbalias ({@link ColorContainer siehe
     *     Auflistung}) oder eine Farbe in hexadezimaler Codierung (z. B.
     *     {@code #ff0000}).
     *
     * @return Eine vordefinierte Farbe.
     *
     * @see pi.actor.Actor#color(String)
     * @see pi.Scene#backgroundColor(String)
     *
     * @throws RuntimeException Fall die Farbe nicht definiert ist.
     */
    @API
    public Color get(String name)
    {
        name = normalizeName(name);
        Color color = resources.get(name);
        if (color == null)
        {
            String alias = aliases.get(name);
            if (alias != null)
            {
                return resources.get(alias);
            }
        }
        if (color == null && ColorUtil.isHexColorString(name))
        {
            return ColorUtil.decode(name);
        }
        if (color == null)
        {
            throw new RuntimeException("Unbekannte Farbe: " + name);
        }
        return color;
    }

    /**
     * Ruft eine <b>benannte Farbe</b> anhand ihres Namens ab.
     *
     * @param name Der Name der Farbe. Der Name wird normalisiert, bevor die
     *     Suche durchgeführt wird.
     *
     * @return Ein {@link NamedColor}-Objekt, das die gefundene Farbe, ihren
     *     Namen und alle Aliase enthält, die auf diese Farbe verweisen. Wenn
     *     die Farbe nicht gefunden wird, ist das Farbfeld null.
     */
    @API
    @Getter
    public NamedColor namedColor(String name)
    {
        name = normalizeName(name);
        Color color = resources.get(name);
        if (color == null)
        {
            String nameFromAlias = aliases.get(name);
            if (nameFromAlias != null)
            {
                name = nameFromAlias;
            }
        }
        ArrayList<String> a = new ArrayList<>();
        for (Map.Entry<String, String> entry : aliases.entrySet())
        {
            if (entry.getValue().equals(name))
            {
                a.add(entry.getKey());
            }
        }
        return new NamedColor(name, color, a);
    }

    /**
     * Gibt eine <b>vordefinierte</b> Farbe mit geändertem <b>Alphakanal</b>
     * zurück.
     *
     * <p>
     * Die Farben können auch in hexadezimaler Schreibweise angegeben werden, z.
     * B. {@code #ff0000}. Die Groß- und Kleinschreibung spielt keine Rolle.
     * Auch Leerzeichen werden ignoriert.
     * </p>
     *
     * @param name Ein Farbname, ein Farbalias ({@link ColorContainer siehe
     *     Auflistung}) oder eine Farbe in hexadezimaler Codierung (z. B.
     *     {@code #ff0000}).
     * @param alpha Der
     *     <a href="https://de.wikipedia.org/wiki/Alphakanal">Alphakanal</a> als
     *     Ganzzahl von {@code 0} bis {@code 255}. Dem Wert {@code 0} entspricht
     *     das Attribut <em>„vollständig transparent“</em>, d. h. unsichtbar.
     *     {@code 255} entspricht <em>„nicht transparent“</em>.
     *
     * @return Eine vordefinierte Farbe.
     */
    @API
    public Color get(String name, int alpha)
    {
        return ColorUtil.changeAlpha(get(name), alpha);
    }

    /**
     * Zählt die <b>Anzahl</b> der Farben in diesem Farbenspeicher.
     *
     * @return Die <b>Anzahl</b> der Farben.
     */
    public int count()
    {
        return resources.size();
    }

    /**
     * Gibt eine <b>zufällige</b> Farbe aus dem Farbenspeicher zurück.
     *
     * <p>
     * Diese Methode wählt eine Farbe zufällig aus den verfügbaren Farben im
     * Farbenspeicher aus und gibt sie zurück.
     * </p>
     *
     * @return Eine zufällige Instanz von {@link Color} aus den Farbenspeicher.
     *
     * @since 0.42.0
     */
    @API
    public Color random()
    {
        return (Color) resources.values()
                .toArray()[(int) (Math.random() * resources.size())];
    }
}
