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
package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colors;
import static de.pirckheimer_gymnasium.engine_pi.Resources.fonts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.engine_pi.util.FontUtil;

/**
 * Eine einzeilige <b>Text</b>box.
 *
 * @author Josef Friedrich
 *
 * @since 0.38.0
 */
public class TextBox extends LeafBox
{
    /**
     * Der <b>Inhalt</b> der Textbox als Zeichenkette.
     *
     * @since 0.38.0
     */
    private String content;

    /**
     *
     */
    private double fontSize = 16;

    /**
     * Die <b>Schriftart</b>, in der der Inhalt dargestellt werden soll.
     *
     * @since 0.38.0
     */
    private Font font = fonts.getDefault().deriveFont((float) fontSize);

    private Color color;

    /**
     * @since 0.38.0
     */
    private int baseline;

    /**
     * Erzeugt eine <b>Text</b>box.
     *
     * @param content Der <b>Inhalt</b> der Textbox als Zeichenkette.
     *
     * @since 0.39.0
     *
     * @see Box#text(String)
     */
    public TextBox(String content)
    {
        this.content = content;
        calculateDimension();
    }

    /**
     * Erzeugt eine <b>Text</b>box.
     *
     * @param content Der <b>Inhalt</b> der Textbox als Zeichenkette.
     * @param font Die <b>Schriftart</b>, in der der Inhalt dargestellt werden
     *     soll.
     *
     * @since 0.38.0
     *
     * @see Box#text(String, Font)
     */
    public TextBox(String content, Font font)
    {
        this.content = content;
        this.font = font;
        calculateDimension();
    }

    protected void calculateDimension()
    {
        var bounds = FontUtil.getStringBoundsNg(content, font);
        width = bounds.getWidth();
        height = bounds.getHeight();
        baseline = bounds.getBaseline();
    }

    /* Setter */

    /**
     * @since 0.39.0
     */
    public TextBox content(String content)
    {
        this.content = content;
        calculateDimension();
        return this;
    }

    /**
     * @since 0.39.0
     */
    public TextBox font(Font font)
    {
        this.font = font;
        calculateDimension();
        return this;
    }

    /**
     * @param fontSize Die Schriftgröße in Punkten (Points pt)
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
    void draw(Graphics2D g)
    {
        Color oldColor = null;
        Font oldFont = g.getFont();
        if (color != null)
        {
            oldColor = g.getColor();
            g.setColor(color);
        }
        g.setFont(font);
        g.drawString(content, x, y + baseline);
        if (oldColor != null)
        {
            g.setColor(oldColor);
        }
        g.setFont(oldFont);
    }
}
