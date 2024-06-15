package de.pirckheimer_gymnasium.engine_pi.resources;

import java.awt.Color;
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
 * gespeichert.
 * </p>
 *
 * <p>
 * Bei den Farbennamen wird sowohl die Klein- und Großschreibung als auch
 * Leerzeichen ignoriert. In den Farbennamen können sowohl deutschen Umlaute
 * sowohl normal als auch umschrieben (ae, ue) werden.
 * </p>
 *
 * @see de.pirckheimer_gymnasium.engine_pi.Resources#colors
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

    private String normalizeName(String name)
    {
        return name.toLowerCase().replaceAll("\\s", "").replaceAll("ä", "ae")
                .replaceAll("ö", "oe").replaceAll("ü", "ue")
                .replaceAll("ß", "ss");
    }

    /**
     * Fügt dem Farbenspeicher eine Farbe unter einem Namen hinzu.
     *
     * @param name  Der Farbname.
     * @param color Die Farbe.
     *
     * @return Die gleiche Farbe, die hinzugefügt wurde.
     */
    public Color add(String name, Color color)
    {
        resources.put(name, color);
        return color;
    }

    /**
     * Fügt dem Farbenspeicher eine Farbe in <b>hexadezimaler</b> Codierung
     * unter einem <b>Namen</b> hinzu.
     *
     * @param name  Der Farbname.
     * @param color Die Farbe in hexadezimaler Codierung.
     *
     * @return Die gleiche Farbe, die hinzugefügt wurde.
     */
    public Color add(String name, String color)
    {
        return add(name, ColorUtil.decode(color));
    }

    /**
     * Fügt dem Farbenspeicher eine <b>Farbe</b> unter einem <b>Namen</b> und
     * beliebig vieler <b>Aliasse</b> hinzu.
     *
     * @param name  Der Farbname.
     * @param color Die Farbe.
     * @param alias Beliebig viele weitere Farbnamen, die als Aliasse dienen.
     *
     * @return Die gleiche Farbe, die hinzugefügt wurde.
     */
    public Color add(String name, Color color, String... alias)
    {
        for (String a : alias)
        {
            aliases.put(normalizeName(a), name);
        }
        return add(name, color);
    }

    /**
     * Fügt dem Farbenspeicher eine Farbe in <b>hexadezimaler</b> Codierung
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
        return add(name, ColorUtil.decode(color), alias);
    }

    /**
     * Fügt <b>alle</b> Farben eines <b>Farbschemas</b> dem Farbenspeicher
     * hinzu.
     *
     * <p>
     * Die Farben werden in einer {@link Map} unter dem englischen Farbnamen
     * abgelegt. Neben dem englischen Hauptfarbnamen werden sowie weitere
     * englische und deutsche Farbnamen als Aliasse gespeichert. Auf eine Farbe
     * des Farbenschema kann deshalb mit mehreren Farbnamen zugegriffen werden.
     * </p>
     *
     * <p>
     * Wird eines neues Farbschema gesetzt werden alle sich bereits im Speicher
     * befindenen Farben gelöscht.
     * </p>
     *
     * @param schema Das Farbschema, dessen Farben in den Farbenspeicher
     *               abgelegt werden soll.
     */
    public void addScheme(ColorScheme schema)
    {
        clear();
        // Primärfarbe
        add("yellow", schema.getYellow(), "Gelb");
        // Tertiärfarbe
        add("gold", schema.getGold(), "Golden", "Gelb Orange", "Orange Gelb");
        // Sekundärfarbe
        add("orange", schema.getOrange());
        // Tertiärfarbe
        add("brick", schema.getBrick(), "brick red", "Ziegelrot", "Orange Rot",
                "Rot Orange");
        // Primärfarbe
        add("red", schema.getRed(), "Rot");
        // Tertiärfarbe
        add("pink", schema.getPink(), "Rosa");
        // Sekundärfarbe
        add("purple", schema.getPurple(), "Violett");
        // Tertiärfarbe
        add("indigo", schema.getIndigo(), "Violett Blau", "Blau Violett");
        // Primärfarbe
        add("blue", schema.getBlue(), "Blau");
        // Tertiärfarbe
        add("cyan", schema.getCyan(), "Türkis", "Blau Grün", "Grün Blau");
        // Sekundärfarbe
        add("green", schema.getGreen(), "Grün");
        // Tertiärfarbe
        add("lime", schema.getLime(), "lime green", "Limetten Grün", "Limette",
                "Gelb Grün", "Grün Gelb");
        // andere Zusammensetzung, nicht nach Itten.
        add("brown", schema.getBrown(), "Braun");
        add("white", schema.getWhite(), "Weiß");
        add("gray", schema.getGray(), "Grau");
        add("black", schema.getBlack(), "Schwarz");
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
     * <b>Leert</b> den Farbenspeicher samt der Aliasse.
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
     * B. {@code #ff0000}. Groß- und Kleinschreibung spielt keine Rolle. Auch
     * Leerzeichen werden ignoriert.
     * </p>
     *
     * @param name Der Farbname oder ein Farbalias, eine Farbe in hexadezimaler
     *             Codierung.
     *
     * @return Eine vordefinierte Farbe.
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
}
