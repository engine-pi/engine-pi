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
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;

import de.pirckheimer_gymnasium.engine_pi.util.FontUtil;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/graphics/boxes/TextBlockBoxDemo.java

/**
 * Ein mehrzeiliger Textblock
 *
 * @author Josef Friedrich
 *
 * @since 0.41.0
 */
public class TextBlockBox extends LeafBox
{
    /**
     * Der <b>Inhalt</b> des Textblocks als Zeichenkette.
     *
     * @since 0.41.0
     */
    private String content;

    List<TextLayout> lines = new ArrayList<>();

    /**
     * @since 0.41.0
     */
    private double fontSize = 16;

    /**
     * Die <b>Schriftart</b>, in der der Inhalt dargestellt werden soll.
     *
     * @since 0.41.0
     */
    private Font font = fonts.getDefault().deriveFont((float) fontSize);

    private Color color;

    /**
     * Erzeugt eineen <b>Text</b>block.
     *
     * @param content Der <b>Inhalt</b> des Textblocks als Zeichenkette.
     *
     * @since @since 0.41.0
     *
     * @see Box#textLine(String)
     */
    public TextBlockBox(String content)
    {
        this.content = content;
        calculateDimension();
    }

    /**
     * Erzeugt eineen <b>Text</b>block.
     *
     * @param content Der <b>Inhalt</b> des Textblocks als Zeichenkette.
     * @param font Die <b>Schriftart</b>, in der der Inhalt dargestellt werden
     *     soll.
     *
     * @since 0.41.0
     *
     * @see Box#textLine(String, Font)
     */
    public TextBlockBox(String content, Font font)
    {
        this.content = content;
        this.font = font;
        calculateDimension();
    }

    protected void calculateDimension()
    {
        width = definedWidth > 0 ? definedWidth : 300;
        lines = splitIntoLines(content, FontUtil.getFontRenderContext(), font,
                width);
        var dim = measureLines(lines);
        width = dim.width;
        height = dim.height;
    }

    /* Setter */

    public TextBlockBox width(int width)
    {
        definedWidth = width;
        return this;
    }

    /**
     * Setzt den <b>Inhalt</b> und berechnet dabei die Abmessungen neu.
     *
     * @param content Der <b>Inhalt</b> des Textblocks als Zeichenkette.
     *
     * @see FramedTextBox#content(String)
     *
     * @since 0.39.0
     */
    public TextBlockBox content(String content)
    {
        this.content = content;
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
    public TextBlockBox font(Font font)
    {
        this.font = font;
        calculateDimension();
        return this;
    }

    /**
     * @param fontSize Die Schriftgröße in Punkten (Points pt)
     */
    public TextBlockBox fontSize(double fontSize)
    {
        font = font.deriveFont((float) fontSize);
        calculateDimension();
        return this;
    }

    public TextBlockBox color(Color color)
    {
        this.color = color;
        return this;
    }

    public TextBlockBox color(String color)
    {
        this.color = colors.get(color);
        return this;
    }

    private static List<TextLayout> splitIntoLines(String content,
            FontRenderContext context, Font font, float wrappingWidth)
    {
        ArrayList<TextLayout> lines = new ArrayList<>();
        for (String line : content.split(System.lineSeparator()))
        {
            final AttributedString styledText = new AttributedString(line);
            styledText.addAttribute(TextAttribute.FONT, font);
            final AttributedCharacterIterator iterator = styledText
                    .getIterator();
            final LineBreakMeasurer measurer = new LineBreakMeasurer(iterator,
                    context);
            while (true)
            {
                TextLayout nextLayout = measurer.nextLayout(wrappingWidth);
                lines.add(nextLayout);
                if (measurer.getPosition() >= line.length())
                {
                    break;
                }
            }
        }
        return lines;
    }

    private static PixelDimension measureLines(List<TextLayout> lines)
    {
        PixelDimension dim = new PixelDimension();
        float maxWidth = 0;
        float height = 0;
        for (TextLayout line : lines)
        {
            float width = line.getAdvance();
            if (width > maxWidth)
            {
                maxWidth = width;
            }
            height += line.getAscent() + line.getDescent() + line.getLeading();
        }

        dim.width = (int) Math.ceil(maxWidth);
        dim.height = (int) Math.ceil(height);
        return dim;
    }

    @Override
    void draw(Graphics2D g)
    {
        float textY = (float) y;
        for (TextLayout line : lines)
        {
            textY += line.getAscent();
            line.draw(g, (float) (x), textY);
            textY += line.getDescent() + line.getLeading();
        }
    }
}
