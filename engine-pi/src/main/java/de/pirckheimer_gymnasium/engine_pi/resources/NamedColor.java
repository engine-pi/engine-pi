package de.pirckheimer_gymnasium.engine_pi.resources;

import java.awt.Color;
import java.util.List;

public class NamedColor
{
    private String name;

    private Color color;

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
