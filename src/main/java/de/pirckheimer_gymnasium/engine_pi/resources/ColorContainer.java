package de.pirckheimer_gymnasium.engine_pi.resources;

import java.awt.Color;
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

    public ColorContainer()
    {
    }

    public Color add(String name, Color color)
    {
        resources.put(name, color);
        return color;
    }

    public void addSchema(ColorSchema schema)
    {
        // Primärfarbe
        add("yellow", schema.getYellow());
        // Tertiärfarbe
        add("gold", schema.getGold());
        // Sekundärfarbe
        add("orange", schema.getOrange());
        // Tertiärfarbe
        add("brick", schema.getBrick());
        // Primärfarbe
        add("red", schema.getRed());
        // Tertiärfarbe
        add("pink", schema.getPink());
        // Sekundärfarbe
        add("purple", schema.getPurple());
        // Tertiärfarbe
        add("indigo", schema.getIndigo());
        // Primärfarbe
        add("blue", schema.getBlue());
        // Tertiärfarbe
        add("cyan", schema.getCyan());
        // Sekundärfarbe
        add("green", schema.getGreen());
        add("lime", schema.getLime());
        // andere Zusammensetzung, nicht nach Itten.
        add("brown", schema.getBrown());
        add("white", schema.getWhite());
        add("gray", schema.getGray());
        add("black", schema.getBlack());
    }

    public Map<String, Color> getAll()
    {
        return resources;
    }

    /**
     * Leert den Ressourcenspeicher, indem alle zuvor geladenen Ressourcen
     * entfernt werden.
     */
    public void clear()
    {
        resources.clear();
    }

    public Color get(String name)
    {
        Color color = resources.get(name);
        if (color == null && ColorUtil.isHexColorString(name))
        {
            return ColorUtil.decode(name);
        }
        return color;
    }
}
