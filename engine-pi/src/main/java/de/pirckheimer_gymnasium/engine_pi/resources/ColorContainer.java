package de.pirckheimer_gymnasium.engine_pi.resources;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import de.pirckheimer_gymnasium.engine_pi.util.ColorUtil;

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
 * Bei den Farbnamen wird sowohl die Klein- und Großschreibung als auch
 * Leerzeichen ignoriert. In den Farbnamen können sowohl deutschen Umlaute
 * verwendet als auch umschrieben (z. B. ae, oe, ue, ss) werden. Der Binde- und
 * der Unterstrich werden ebenfalls nicht berücksichtigt.
 * </p>
 *
 * <p>
 * Die zwölf Farben nach dem Farbkreis von Itten zusammen mit ihren Aliassen
 * bzw. Synonymen:
 * </p>
 *
 * <ol>
 * <li>{@code yellow}: Gelb</li>
 * <li>{@code yellow orange}: „orange yellow“, „gold“, „Gelb-Orange“,
 * „Orange-Gelb“, „Golden“, „Dunkelgelb“</li>
 * <li>{@code orange}: Orange</li>
 * <li>{@code red orange}: „orange red“, „brick red“, „brick“, „Rot-Orange“,
 * „Orange-Rot“, „Ziegelrot“, „Hellrot“</li>
 * <li>{@code red}: Rot</li>
 * <li>{@code red purple}: „purple red“, „pink“, „Rot-Violett“, „Violett-Rot“,
 * „Rosa“</li>
 * <li>{@code purple}: „Violet“, „Violett“, „Lila“</li>
 * <li>{@code blue purple}: „purple blue“, „indigo“, „Violett Blau“, „Blau
 * Violett“</li>
 * <li>{@code blue}: Blau</li>
 * <li>{@code blue green}: „green blue“, „cyan“, „Blau-Grün“, „Grün-Blau“,
 * „Türkis“</li>
 * <li>{@code green}: Grün</li>
 * <li>{@code yellow green}: „green yellow“, „lime“, „lime green“, „Gelb-Grün“,
 * „Grün-Gelb“, „Limetten Grün“, „Limette“, „Hellgrün“</li>
 * </ol>
 *
 * Diese Farben sind ebenfalls im Speicher für Farben enthalten (gehören aber
 * nicht zum Farbkreis von Itten):
 *
 * <ul>
 * <li>{@code brown}: Braun</li>
 * <li>{@code white}: Weiß</li>
 * <li>{@code gray}: grey, Grau</li>
 * <li>{@code black}: Schwarz</li>
 * </ul>
 *
 * @see de.pirckheimer_gymnasium.engine_pi.Resources#COLORS
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
     *             (beispielsweise {@code Gelb-Grün}).
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
     * @param name  Der Farbname.
     * @param color Die Farbe.
     *
     * @return Die gleiche Farbe, die hinzugefügt wurde.
     */
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
     * @param r    Der Rotanteil der Farbe (0-255).
     * @param g    Der Gelbanteil der Farbe (0-255).
     * @param b    Der Blauanteil der Farbe (0-255).
     * @param a    Der Alphakanal der Farbe (0-255).
     *
     * @return Die gleiche Farbe, die hinzugefügt wurde.
     *
     * @since 0.26.0
     */
    public Color add(String name, int r, int g, int b, int a)
    {
        return add(name, new Color(r, g, b, a));
    }

    /**
     * Fügt dem Speicher für Farben eine <b>Farbe</b> durch Angabe der <b>drei
     * Farbanteile</b>, des <b>Alphakanals</b> in dezimaler Notation und
     * beliebig vieler <b>Aliasse</b> hinzu.
     *
     * @param name  Der Farbname.
     * @param r     Der Rotanteil der Farbe (0-255).
     * @param g     Der Gelbanteil der Farbe (0-255).
     * @param b     Der Blauanteil der Farbe (0-255).
     * @param a     Der Alphakanal der Farbe (0-255).
     * @param alias Beliebig viele weitere Farbnamen, die als Aliasse dienen.
     *
     * @return Die gleiche Farbe, die hinzugefügt wurde.
     *
     * @since 0.26.0
     */
    public Color add(String name, int r, int g, int b, int a, String... alias)
    {
        return add(name, new Color(r, g, b, a), alias);
    }

    /**
     * Fügt dem Speicher für Farben eine <b>Farbe</b> durch Angabe der <b>drei
     * Farbanteile</b> in dezimaler Notation hinzu.
     *
     * @param name Der Farbname.
     * @param r    Der Rotanteil der Farbe (0-255).
     * @param g    Der Gelbanteil der Farbe (0-255).
     * @param b    Der Blauanteil der Farbe (0-255).
     *
     * @return Die gleiche Farbe, die hinzugefügt wurde.
     *
     * @since 0.26.0
     */
    public Color add(String name, int r, int g, int b)
    {
        return add(name, new Color(r, g, b));
    }

    /**
     * Fügt dem Speicher für Farben eine <b>Farbe</b> durch Angabe der <b>drei
     * Farbanteile</b> in dezimaler Notation und beliebig vieler <b>Aliasse</b>
     * hinzu.
     *
     * @param name  Der Farbname.
     * @param r     Der Rotanteil der Farbe (0-255).
     * @param g     Der Gelbanteil der Farbe (0-255).
     * @param b     Der Blauanteil der Farbe (0-255).
     * @param alias Beliebig viele weitere Farbnamen, die als Aliasse dienen.
     *
     * @return Die gleiche Farbe, die hinzugefügt wurde.
     *
     * @since 0.26.0
     */
    public Color add(String name, int r, int g, int b, String... alias)
    {
        return add(name, new Color(r, g, b), alias);
    }

    /**
     * Fügt dem Speicher für Farben eine Farbe in <b>hexadezimaler</b> Codierung
     * unter einem <b>Namen</b> hinzu.
     *
     * @param name  Der Farbname.
     * @param color Die Farbe in hexadezimaler Codierung.
     *
     * @return Die gleiche Farbe, die hinzugefügt wurde.
     */
    public Color add(String name, String color)
    {
        return add(normalizeName(name), ColorUtil.decode(color));
    }

    /**
     * Fügt dem Speicher für Farben eine <b>Farbe</b> unter einem <b>Namen</b>
     * und beliebig vieler <b>Aliasse</b> hinzu.
     *
     * @param name  Der Farbname.
     * @param color Die Farbe.
     * @param alias Beliebig viele weitere Farbnamen, die als Aliasse dienen.
     *
     * @return Die gleiche Farbe, die hinzugefügt wurde.
     */
    public Color add(String name, Color color, String... alias)
    {
        addAlias(name, alias);
        return add(name, color);
    }

    /**
     * Fügt dem Speicher für Farben eine Farbe in <b>hexadezimaler</b> Codierung
     * unter einem Namen und beliebig vieler <b>Aliasse</b> hinzu.
     *
     * @param name  Der Farbname.
     * @param color Die Farbe in hexadezimaler Codierung.
     * @param alias Beliebig viele weitere Farbnamen, die als Aliasse dienen.
     *
     * @return Die gleiche Farbe, die hinzugefügt wurde.
     */
    public Color add(String name, String color, String... alias)
    {
        return add(normalizeName(name), ColorUtil.decode(color), alias);
    }

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
     *               abgelegt werden soll.
     */
    public void addScheme(ColorScheme schema)
    {
        clear();
        // Primärfarbe
        add("yellow", schema.getYellow(), "Gelb");
        // Tertiärfarbe
        add("yellow orange", schema.getYellowOrange(), "orange yellow", "gold",
                "Gelb-Orange", "Orange-Gelb", "Golden", "Dunkelgelb");
        // Sekundärfarbe
        add("orange", schema.getOrange());
        // Tertiärfarbe
        add("red orange", schema.getRedOrange(), "orange red", "brick red",
                "brick", "Rot-Orange", "Orange-Rot", "Ziegelrot", "Hellrot");
        // Primärfarbe
        add("red", schema.getRed(), "Rot");
        // Tertiärfarbe
        add("red purple", schema.getRedPurple(), "purple red", "pink",
                "Rot-Violett", "Violett-Rot", "Rosa");
        // Sekundärfarbe
        add("purple", schema.getPurple(), "Violet", "Violett", "Lila");
        // Tertiärfarbe
        add("blue purple", schema.getBluePurple(), "purple blue", "indigo",
                "Violett Blau", "Blau Violett");
        // Primärfarbe
        add("blue", schema.getBlue(), "Blau");
        // Tertiärfarbe
        add("blue green", schema.getBlueGreen(), "green blue", "cyan",
                "Blau-Grün", "Grün-Blau", "Türkis");
        // Sekundärfarbe
        add("green", schema.getGreen(), "Grün");
        // Tertiärfarbe
        add("yellow green", schema.getYellowGreen(), "green yellow", "lime",
                "lime green", "Gelb-Grün", "Grün-Gelb", "Limetten Grün",
                "Limette", "Hellgrün");
        // andere Zusammensetzung, nicht nach Itten.
        add("brown", schema.getBrown(), "Braun");
        add("white", schema.getWhite(), "Weiß");
        add("gray", schema.getGray(), "grey", "Grau");
        add("black", schema.getBlack(), "Schwarz");
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
     *             Auflistung}) oder eine Farbe in hexadezimaler Codierung (z.
     *             B. {@code #ff0000}).
     *
     * @return Eine vordefinierte Farbe.
     * @see de.pirckheimer_gymnasium.engine_pi.actor.Actor#setColor(String)
     * @see de.pirckheimer_gymnasium.engine_pi.Scene#setBackgroundColor(String)
     */
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
        ColorScheme scheme = ColorSchemeSelection.GNOME.getScheme();
        return switch (normalizeName(name))
        {
        case "yellow", "gelb" -> scheme.getYellow();
        // Tertiärfarbe
        case "yelloworange", "orangeyellow", "gold", "gelborange", "orangegelb",
                "golden", "dunkelgelb" ->
            scheme.getYellowOrange();
        // Sekundärfarbe
        case "orange" -> scheme.getOrange();
        // Tertiärfarbe
        case "redorange", "orangered", "brickred", "brick", "rotorange",
                "orangerot", "ziegelrot", "hellrot" ->
            scheme.getRedOrange();
        // Primärfarbe
        case "red", "rot" -> scheme.getRed();
        // Tertiärfarbe
        case "redpurple", "purplered", "pink", "rotviolett", "violettrot",
                "rosa" ->
            scheme.getRedPurple();
        // Sekundärfarbe
        case "purple", "violet", "violett", "lila" -> scheme.getPurple();
        // Tertiärfarbe
        case "bluepurple", "purpleblue", "indigo", "violettblau",
                "blauviolett" ->
            scheme.getBluePurple();
        // Primärfarbe
        case "blue", "blau" -> scheme.getBlue();
        // Tertiärfarbe
        case "bluegreen", "greenblue", "cyan", "blaugruen", "gruenblau",
                "tuerkis" ->
            scheme.getBlueGreen();
        // Sekundärfarbe
        case "green", "gruen" -> scheme.getGreen();
        // Tertiärfarbe
        case "yellowgreen", "greenyellow", "lime", "limegreen", "gelbgruen",
                "gruengelb", "limettengruen", "limette", "hellgruen" ->
            scheme.getYellowGreen();
        // andere Zusammensetzung, nicht nach Itten.
        case "brown", "braun" -> scheme.getBrown();
        case "white", "weiss" -> scheme.getWhite();
        case "gray", "grey", "grau" -> scheme.getGray();
        case "black", "schwarz" -> scheme.getBlack();
        default -> Color.WHITE;
        };
    }

    public Color getSafe(String name, int alpha)
    {
        return ColorUtil.changeAlpha(getSafe(name), alpha);
    }

    /**
     * Gibt <b>alle</b> Farben samt der Farbnamen als {@link Map} zurück.
     *
     * @return Alle Farben samt der Farbnamen als {@link Map} zurück.
     */
    public Map<String, Color> getAll()
    {
        return resources;
    }

    /**
     * <b>Leert</b> den Speicher für Farben samt der Aliasse.
     */
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
     *             Auflistung}) oder eine Farbe in hexadezimaler Codierung (z.
     *             B. {@code #ff0000}).
     *
     * @return Eine vordefinierte Farbe.
     *
     * @see de.pirckheimer_gymnasium.engine_pi.actor.Actor#setColor(String)
     * @see de.pirckheimer_gymnasium.engine_pi.Scene#setBackgroundColor(String)
     *
     * @throws RuntimeException Fall die Farbe nicht definiert ist.
     */
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

    public NamedColor getNamedColor(String name)
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
     * @param name  Ein Farbname, ein Farbalias ({@link ColorContainer siehe
     *              Auflistung}) oder eine Farbe in hexadezimaler Codierung (z.
     *              B. {@code #ff0000}).
     * @param alpha Der Alphakanal als Ganzzahl von 0 bis 255.
     *
     * @return Eine vordefinierte Farbe.
     */
    public Color get(String name, int alpha)
    {
        return ColorUtil.changeAlpha(get(name), alpha);
    }

    public int count()
    {
        return resources.size();
    }
}
