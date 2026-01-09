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

import pi.annotations.Getter;
import pi.annotations.Setter;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/docs/manual/resources/colors.md

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
    @Getter
    public String name()
    {
        return name;
    }

    /**
     * Setzt den <b>Hauptnamen</b> der Farbe.
     *
     * @param name Der <b>Hauptname</b> der Farbe.
     */
    @Setter
    public void name(String name)
    {
        this.name = name;
    }

    @Getter
    public Color color()
    {
        return color;
    }

    @Getter
    public String colorDecFormatted()
    {
        return String.format("%s, %s, %s", color.getRed(), color.getGreen(),
                color.getBlue());
    }

    @Getter
    public String colorHexFormatted()
    {
        return ColorUtil.encode(color);
    }

    @Setter
    public void color(Color color)
    {
        this.color = color;
    }

    @Getter
    public List<String> aliases()
    {
        return aliases;
    }

    @Getter
    public String aliasesFormatted()
    {
        return String.join(", ", aliases);
    }

    @Setter
    public void aliases(List<String> aliases)
    {
        this.aliases = aliases;
    }
}
