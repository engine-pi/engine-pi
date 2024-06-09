package de.pirckheimer_gymnasium.engine_pi.resources;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Ein Speicher für Farben vom Datentyp {@link Color}.
 */
public class ColorContainer implements Container<Color>
{
    private final Map<String, Color> resources = new HashMap<>();

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
        add("yellow", schema.getYellow());
        add("yelloworange", schema.getGold());
        add("orange", schema.getOrange());
        add("orangered", schema.getBrick());
        add("red", schema.getRed());
        add("redpurple", schema.getPink());
        add("purple", schema.getPurple());
        add("purpleblue", schema.getIndigo());
        add("blue", schema.getBlue());
        add("bluegreen", schema.getCyan());
        add("green", schema.getGreen());
        add("yellowgreen", schema.getLime());
        add("brown", schema.getBrown());
    }

    /**
     * Leert den Ressourcenspeicher, indem alle zuvor geladenen Ressourcen
     * entfernt werden.
     */
    public void clear()
    {
        this.resources.clear();
    }

    public Color get(String name)
    {
        return resources.get(name);
    }
}
