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
    /**
     * Der <b>Hauptname</b> der Farbe.
     */
    private String name;

    /**
     * Die Farbe als AWT-Color-Objekt.
     */
    private Color color;

    /**
     * Weitere Namen beziehungsweise Aliasse der Farbe.
     */
    private List<String> aliases;

    /**
     * @param name Der <b>Hauptname</b> der Farbe.
     * @param color Die Farbe als AWT-Color-Objekt.
     * @param aliases Weitere Namen beziehungsweise Aliasse der Farbe.
     */
    public NamedColor(String name, Color color, List<String> aliases)
    {
        this.name = name;
        this.color = color;
        this.aliases = aliases;
    }

    /**
     * Gibt den <b>Hauptnamen</b> der Farbe zurück.
     *
     * @return Der <b>Hauptname</b> der Farbe.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Setzt den <b>Hauptnamen</b> der Farbe.
     *
     * @param name Der <b>Hauptname</b> der Farbe.
     */
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
