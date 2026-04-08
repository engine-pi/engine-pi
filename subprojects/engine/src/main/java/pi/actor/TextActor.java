/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2026 Josef Friedrich and contributors.
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
package pi.actor;

import static pi.Controller.colors;

import java.awt.Color;
import java.awt.Font;

import pi.annotations.API;
import pi.annotations.ChainableMethod;
import pi.annotations.Getter;
import pi.annotations.Setter;
import pi.debug.ToStringFormatter;
import pi.graphics.boxes.TextBox;
import pi.resources.color.ColorContainer;
import pi.resources.font.FontStyle;

/**
 * Die Superklasse für {@link Text} und {@link TextBlock}.
 *
 * @author Josef Friedrich
 *
 * @since 0.45.0
 */
public abstract class TextActor<T extends TextBox> extends BoxActor<T>
{
    /**
     * Erstellt einen <b>Text</b>.
     *
     * @param box Die <b>Textbox</b>, die den Inhalt darstellen soll.
     *
     * @since 0.42.0
     */
    @API
    public TextActor(T box)
    {
        super(box);
    }

    /* content */

    /**
     * Gibt den <b>Textinhalt</b>, der dargestellt werden soll, zurück.
     *
     * @return Der <b>Textinhalt</b>, der dargestellt werden soll.
     *
     * @since 0.42.0
     */
    @API
    @Getter
    public String content()
    {
        return box.content();
    }

    /**
     * Setzt den <b>Textinhalt</b>, der dargestellt werden soll.
     *
     * @param content Der <b>Textinhalt</b>, der dargestellt werden soll.
     *
     * @return Eine Referenz auf die eigene Instanz des Textes, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften des Textes durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code text.content(..).height(..)}.
     *
     * @since 0.42.0
     */
    @API
    @Setter
    @ChainableMethod
    public TextActor<T> content(Object content)
    {
        String normalizedContent = String.valueOf(content);
        if (normalizedContent == null)
        {
            normalizedContent = "";
        }
        if (!box.content().equals(normalizedContent))
        {
            box.content(normalizedContent);
            update();
        }
        return this;
    }

    /* font */

    /**
     * Gibt die <b>Schriftart</b> zurück, in der der Inhalt dargestellt wird.
     *
     * @return Die <b>Schriftart</b>, in der der Inhalt dargestellt wird.
     *
     * @since 0.42.0
     */
    @API
    @Getter
    public Font font()
    {
        return box.font();
    }

    /**
     * Setzt eine neue <b>Schriftart</b> durch Angabe des <b>Names</b>.
     *
     * @param fontName Der <b>Name</b> der Schriftart, falls es sich um eine
     *     Systemschriftart handelt, oder der <b>Pfad</b> zu einer Schriftdatei.
     *
     * @return Eine Referenz auf die eigene Instanz des Textes, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften des Textes durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code text.content(..).height(..)}.
     *
     * @since 0.42.0
     */
    @API
    @Setter
    @ChainableMethod
    public TextActor<T> font(String fontName)
    {
        box.font(fontName);
        update();
        return this;
    }

    /**
     * Setzt die <b>Schriftart</b>, in der der Inhalt dargestellt werden soll.
     *
     * @param font Die <b>Schriftart</b>, in der der Inhalt dargestellt werden
     *     soll.
     *
     * @return Eine Referenz auf die eigene Instanz des Textes, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften des Textes durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code text.content(..).height(..)}.
     *
     * @since 0.42.0
     */
    @API
    @Setter
    @ChainableMethod
    public TextActor<T> font(Font font)
    {
        box.font(font);
        update();
        return this;
    }

    /* style */

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
    @API
    @Getter
    public FontStyle style()
    {
        return box.fontStyle();
    }

    /**
     * Setzt den <b>Stil</b> der Schriftart als <b>Ganzzahl</b>.
     *
     * @param style Der <b>Stil</b> der Schriftart (<i>fett</i>, <i>kursiv</i>
     *     oder <i>fett und kursiv</i>) als Ganzzahl.
     *
     *     <ul>
     *     <li>{@code 0}: Normaler Text</li>
     *     <li>{@code 1}: Fett</li>
     *     <li>{@code 2}: Kursiv</li>
     *     <li>{@code 3}: Fett und Kursiv</li>
     *     </ul>
     *
     * @return Eine Referenz auf die eigene Instanz des Textes, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften des Textes durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code text.content(..).height(..)}.
     */
    @API
    @Setter
    @ChainableMethod
    public TextActor<T> style(int style)
    {
        box.fontStyle(style);
        update();
        return this;
    }

    /**
     * Setzt den <b>Stil</b> der Schriftart als <b>Aufzählungstyp</b>.
     *
     * @param style Der <b>Stil</b> der Schriftart (<i>fett</i>, <i>kursiv</i>
     *     oder <i>fett und kursiv</i>) als Aufzählungstyp.
     *
     *     <ul>
     *     <li>{@link FontStyle#PLAIN} — normaler Text ({@code 0})</li>
     *     <li>{@link FontStyle#BOLD} — fetter Text ({@code 1})</li>
     *     <li>{@link FontStyle#ITALIC} — kursiver Text ({@code 2})</li>
     *     <li>{@link FontStyle#BOLD_ITALIC} — fett und kursiv kombiniert
     *     ({@code 3})</li>
     *     </ul>
     *
     * @return Eine Referenz auf die eigene Instanz des Textes, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften des Textes durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code text.content(..).height(..)}.
     */
    @API
    @Setter
    @ChainableMethod
    public TextActor<T> style(FontStyle style)
    {
        box.fontStyle(style);
        update();
        return this;
    }

    /* color */

    /**
     * Setzt die <b>Farbe</b> des Textes auf eine bestimmte Farbe.
     *
     * @return Die <b>Farbe</b> des Textes.
     *
     * @since 0.42.0
     */
    @API
    @Getter
    @Override
    public Color color()
    {
        return box.color();
    }

    /**
     * Setzt die <b>Farbe</b> des Textes auf eine bestimmte Farbe, die als
     * <b>Zeichenkette</b> angegeben werden kann.
     *
     * @param color Ein Farbname ({@link ColorContainer siehe Auflistung}) oder
     *     eine Farbe in hexadezimaler Codierung (z.B. {@code #ff0000}).
     *
     * @return Eine Referenz auf die eigene Instanz des Textes, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften des Textes durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code text.content(..).height(..)}.
     *
     * @since 0.42.0
     *
     * @see pi.resources.color.ColorContainer#get(String)
     */
    @API
    @Setter
    @Override
    @ChainableMethod
    public TextActor<T> color(String color)
    {
        return color(colors.get(color));
    }

    /**
     * Setzt die <b>Farbe</b> des Textes auf eine bestimmte Farbe.
     *
     * @param color Die neue <b>Farbe</b>.
     *
     * @return Eine Referenz auf die eigene Instanz des Textes, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften des Textes durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code text.content(..).height(..)}.
     *
     * @since 0.42.0
     */
    @API
    @Setter
    @Override
    public TextActor<T> color(Color color)
    {
        super.color(color);
        box.color(color);
        return this;
    }

    protected ToStringFormatter toStringFormatter()
    {
        var formatter = super.toStringFormatter();
        if (style() != FontStyle.PLAIN)
        {
            formatter.prepend("style", style());
        }

        formatter.prepend("content", content());
        return formatter;
    }
}
