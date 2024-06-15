package de.pirckheimer_gymnasium.engine_pi.resources;

import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import de.pirckheimer_gymnasium.engine_pi.util.ColorUtil;

/**
 * Ein Speicher für Farben des Datentyps {@link Color}.
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

    public ColorContainer()
    {
    }

    public Color add(String name, Color color)
    {
        resources.put(name, color);
        return color;
    }

    public Color add(String name, Color color, String... alias)
    {
        for (String a : alias)
        {
            aliases.put(normalizeName(a), name);
        }
        return add(name, color);
    }

    public void addSchema(ColorSchema schema)
    {
        // Primärfarbe
        add("yellow", schema.getYellow(), "Gelb");
        // Tertiärfarbe
        add("gold", schema.getGold(), "Golden", "Gelb Orange", "Orange Gelb");
        // Sekundärfarbe
        add("orange", schema.getOrange(), "Gelb Rot", "Rot Gelb");
        // Tertiärfarbe
        add("brick", schema.getBrick(), "brick red", "Ziegelrot", "Orange Rot",
                "Rot Orange");
        // Primärfarbe
        add("red", schema.getRed(), "Rot");
        // Tertiärfarbe
        add("pink", schema.getPink(), "Rosa", "Rot Violett", "Violett Rot");
        // Sekundärfarbe
        add("purple", schema.getPurple(), "Violett", "Rot Blau", "Blau Rot");
        // Tertiärfarbe
        add("indigo", schema.getIndigo(), "Violett Blau", "Blau Violett");
        // Primärfarbe
        add("blue", schema.getBlue(), "Blau");
        // Tertiärfarbe
        add("cyan", schema.getCyan(), "Türkis", "Blau Grün", "Grün Blau");
        // Sekundärfarbe
        add("green", schema.getGreen(), "Grün", "Gelb Blau", "Blau Gelb");
        // Tertiärfarbe
        add("lime", schema.getLime(), "lime green", "Limetten Grün", "Limette",
                "Gelb Grün", "Grün Gelb");
        // andere Zusammensetzung, nicht nach Itten.
        add("brown", schema.getBrown(), "Braun");
        add("white", schema.getWhite(), "Weiß");
        add("gray", schema.getGray(), "Grau");
        add("black", schema.getBlack(), "Schwarz");
    }

    public Map<String, Color> getAll()
    {
        return resources;
    }

    /**
     * Leert den Farbenspeicher samt der Aliase.
     */
    public void clear()
    {
        resources.clear();
        aliases.clear();
    }

    private String normalizeName(String name)
    {
        name = name.toLowerCase();
        return name.replaceAll("\\s", "").replaceAll("ä", "ae")
                .replaceAll("o", "ue").replaceAll("ü", "ue")
                .replaceAll("ß", "ss");
    }

    /**
     * Gibt eine vordefinierte Farbe zurück. Die Farben können auch in
     * hexadezimaler Schreibweise angegeben werden, z. B. {@code #ff0000}. Groß-
     * und Kleinschreibung spielt keine Rolle. Auch Leerzeichen werden
     * ignoriert.
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
