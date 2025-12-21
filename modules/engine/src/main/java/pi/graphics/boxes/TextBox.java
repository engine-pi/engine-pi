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

import static pi.Resources.colors;
import static pi.Resources.fonts;

import java.awt.Color;
import java.awt.Font;

import pi.debug.ToStringFormatter;

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

    /**
     *
     */
    protected double fontSize = 16;

    /**
     * Die <b>Schriftart</b>, in der der Inhalt dargestellt werden soll.
     *
     * @since 0.38.0
     */
    protected Font font = fonts.getDefault().deriveFont((float) fontSize);

    protected Color color;

    /**
     * Erzeugt eine <b>Text</b>box.
     *
     * @param content Der <b>Inhalt</b> der Textbox als Zeichenkette.
     *
     * @since 0.39.0
     *
     * @see Boxes#textLine(String)
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
     * @since 0.39.0
     */
    public TextBox content(Object content)
    {
        this.content = String.valueOf(content);
        calculateDimension();
        return this;
    }

    /**
     * Setzt die <b>Schriftart</b>, in der der Inhalt dargestellt werden soll.
     *
     * @param font Die <b>Schriftart</b>, in der der Inhalt dargestellt werden
     *     soll.
     *
     * @since 0.39.0
     */
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
     */
    public TextBox fontSize(double fontSize)
    {
        font = font.deriveFont((float) fontSize);
        calculateDimension();
        return this;
    }

    public TextBox color(Color color)
    {
        this.color = color;
        return this;
    }

    public TextBox color(String color)
    {
        this.color = colors.get(color);
        return this;
    }

    @Override
    public ToStringFormatter toStringFormatter()
    {
        var formatter = super.toStringFormatter();

        if (content != null)
        {
            formatter.add("content", content);
        }

        if (width > 0)
        {
            formatter.add("width", width);
        }

        if (height > 0)
        {
            formatter.add("height", height);
        }

        if (fontSize != 16)
        {
            formatter.add("fontSize", fontSize);
        }
        return formatter;
    }
}
