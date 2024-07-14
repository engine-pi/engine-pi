package de.pirckheimer_gymnasium.engine_pi.resources;

import de.pirckheimer_gymnasium.engine_pi.util.ColorUtil;

import java.awt.Color;
import java.util.List;

/**
 * Speichert eine <b>Farbe</b> zusammen mit einem <b>Hauptnamen</b> und optional
 * mehreren <b>Aliassen</b>.
 *
 * @author Josef Friedrich
 *
 * @since 0.26.0
 */
public class NamedColor
{
    private String name;

    private Color color;

    /**
     * Weitere Namen beziehungsweise Aliasse der Farbe.
     */
    private List<String> aliases;

    public NamedColor(String name, Color color, List<String> aliases)
    {
        this.name = name;
        this.color = color;
        this.aliases = aliases;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Color getColor()
    {
        return color;
    }

    public String getColorDecFormatted()
    {
        return String.format("%s, %s, %s", color.getRed(), color.getGreen(),
                color.getBlue());
    }

    public String getColorHexFormatted()
    {
        return ColorUtil.encode(color);
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public List<String> getAliases()
    {
        return aliases;
    }

    public String getAliasesFormatted()
    {
        return String.join(", ", aliases);
    }

    public void setAliases(List<String> aliases)
    {
        this.aliases = aliases;
    }
}
