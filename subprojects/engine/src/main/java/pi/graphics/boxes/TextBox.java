/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
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
package pi.graphics.boxes;

import static pi.Controller.colors;
import static pi.Controller.fonts;

import java.awt.Color;
import java.awt.Font;

import pi.annotations.API;
import pi.annotations.Getter;
import pi.annotations.Setter;
import pi.debug.ToStringFormatter;
import pi.resources.font.FontStyle;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/graphics/boxes/TextLineBoxDemo.java

/**
 * Eine Box, die sich um die Verwaltung einer Zeichenkette, einer Schriftart und
 * einer Schriftfarbe kümmert.
 *
 * @author Josef Friedrich
 *
 * @since 0.41.0
 */
public abstract class TextBox extends LeafBox
{
    /**
     * Der <b>Inhalt</b> der Textbox als Zeichenkette.
     *
     * @since 0.38.0
     */
    protected String content;

    protected double fontSize = 16;

    /**
     * Die <b>Schriftart</b>, in der der Inhalt dargestellt werden soll.
     *
     * @since 0.38.0
     */
    protected Font font = fonts.defaultFont().deriveFont((float) fontSize);

    protected Color color = colors.getSafe("gray");

    /**
     * Erzeugt eine <b>Text</b>box.
     *
     * @param content Der <b>Inhalt</b> der Textbox als Zeichenkette.
     *
     * @since 0.39.0
     */
    public TextBox(Object content)
    {
        content(content);
    }

    /* Setter */

    /**
     * Setzt den <b>Inhalt</b> und berechnet dabei die Abmessungen neu.
     *
     * @param content Der <b>Inhalt</b> der Textbox als Zeichenkette.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.39.0
     */
    @Setter
    public TextBox content(Object content)
    {
        if (content == null)
        {
            content = " ";
        }
        this.content = String.valueOf(content);
        calculateDimension();
        return this;
    }

    /**
     * Setzt eine neue <b>Schriftart</b> durch Angabe des <b>Names</b>.
     *
     * @param fontName Der <b>Name</b> der Schriftart, falls es sich um eine
     *     Systemschriftart handelt, oder der <b>Pfad</b> zu einer Schriftdatei.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.42.0
     */
    @API
    @Setter
    public TextBox font(String fontName)
    {
        font(fonts.get(fontName));
        return this;
    }

    /**
     * Setzt die <b>Schriftart</b>, in der der Inhalt dargestellt werden soll.
     *
     * @param font Die <b>Schriftart</b>, in der der Inhalt dargestellt werden
     *     soll.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.39.0
     */
    @Setter
    public TextBox font(Font font)
    {
        this.font = font;
        calculateDimension();
        return this;
    }

    /**
     * Setzt die <b>Schriftgröße</b> in Punkten (Points pt).
     *
     * @param fontSize Die <b>Schriftgröße</b> in Punkten (Points pt).
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     */
    @Setter
    public TextBox fontSize(double fontSize)
    {
        font = font.deriveFont((float) fontSize);
        calculateDimension();
        return this;
    }

    /**
     * Setzt den <b>Stil</b> der Schriftart als <b>Aufzählungstyp</b>.
     *
     * @param fontStyle Der <b>Stil</b> der Schriftart (<i>fett</i>,
     *     <i>kursiv</i> oder <i>fett und kursiv</i>) als Aufzählungstyp.
     *
     *     <ul>
     *     <li>{@link FontStyle#PLAIN} — normaler Text ({@code 0})</li>
     *     <li>{@link FontStyle#BOLD} — fetter Text ({@code 1})</li>
     *     <li>{@link FontStyle#ITALIC} — kursiver Text ({@code 2})</li>
     *     <li>{@link FontStyle#BOLD_ITALIC} — fett und kursiv kombiniert
     *     ({@code 3})</li>
     *     </ul>
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    public TextBox fontStyle(FontStyle fontStyle)
    {
        font = font.deriveFont(fontStyle.getStyle());
        calculateDimension();
        return this;
    }

    /**
     * Setzt den <b>Stil</b> der Schriftart als <b>Ganzzahl</b>.
     *
     * @param fontStyle Der <b>Stil</b> der Schriftart (<i>fett</i>,
     *     <i>kursiv</i> oder <i>fett und kursiv</i>) als Ganzzahl.
     *
     *     <ul>
     *     <li>{@code 0}: Normaler Text</li>
     *     <li>{@code 1}: Fett</li>
     *     <li>{@code 2}: Kursiv</li>
     *     <li>{@code 3}: Fett und Kursiv</li>
     *     </ul>
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    public TextBox fontStyle(int fontStyle)
    {
        font = font.deriveFont(fontStyle);
        calculateDimension();
        return this;
    }

    /**
     * Gibt den <b>Stil</b> der Schriftart als <b>Aufzählungstyp</b> zurück.
     *
     * @return Der <b>Stil</b> der Schriftart (<i>fett</i>, <i>kursiv</i> oder
     *     <i>fett und kursiv</i>) als Aufzählungstyp.
     *
     *     <ul>
     *     <li>{@link FontStyle#PLAIN} — normaler Text ({@code 0})</li>
     *     <li>{@link FontStyle#BOLD} — fetter Text ({@code 1})</li>
     *     <li>{@link FontStyle#ITALIC} — kursiver Text ({@code 2})</li>
     *     <li>{@link FontStyle#BOLD_ITALIC} — fett und kursiv kombiniert
     *     ({@code 3})</li>
     *     </ul>
     *
     * @since 0.42.0
     */
    @Getter
    public FontStyle fontStyle()
    {
        return FontStyle.getStyle(font.getStyle());
    }

    /**
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     */
    @Setter
    public TextBox color(Color color)
    {
        this.color = color;
        return this;
    }

    /**
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     */
    @Setter
    public TextBox color(String color)
    {
        this.color = colors.get(color);
        return this;
    }

    @Override
    public ToStringFormatter toStringFormatter()
    {
        var formatter = super.toStringFormatter();

        if (fontSize != 16)
        {
            formatter.prepend("fontSize", fontSize);
        }
        if (content != null)
        {
            formatter.prepend("content", content);
        }

        return formatter;
    }
}
