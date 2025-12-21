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

import pi.util.FontUtil;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/graphics/boxes/TextBlockBoxDemo.java

/**
 * Ein mehrzeiliger Textblock
 *
 * @author Josef Friedrich
 *
 * @since 0.41.0
 */
public class TextBlockBox extends TextBox
{
    HAlign hAlignment = HAlign.LEFT;

    List<TextLayout> lines = new ArrayList<>();

    /**
     * Erzeugt eine mehrzeiligen <b>Textblock</b>.
     *
     * @param content Der <b>Inhalt</b> des Textblocks als Zeichenkette.
     *
     * @since 0.41.0
     *
     * @see Boxes#textBlock(Object)
     */
    public TextBlockBox(Object content)
    {
        super(content);
    }

    public TextBlockBox hAlign(HAlign hAlignment)
    {
        this.hAlignment = hAlignment;
        return this;
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

    public TextBlockBox width(int width)
    {
        definedWidth = width;
        return this;
    }

    private static List<TextLayout> splitIntoLines(String content,
            FontRenderContext context, Font font, float wrappingWidth)
    {
        // Source:
        // https://github.com/gurkenlabs/litiengine/blob/0fae965994a30757b078153a67f095fe122ae456/litiengine/src/main/java/de/gurkenlabs/litiengine/graphics/TextRenderer.java#L279-L315
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
        // Source:
        // https://github.com/gurkenlabs/litiengine/blob/0fae965994a30757b078153a67f095fe122ae456/litiengine/src/main/java/de/gurkenlabs/litiengine/graphics/TextRenderer.java#L279-L315
        PixelDimension dim = new PixelDimension();
        float maxWidth = 0;
        float height = 0;
        for (TextLayout line : lines)
        {
            // Advance: Der Vorschub ist der Abstand vom Ursprung bis zum
            // Vorschub des Zeichen ganz rechts.
            float width = line.getAdvance();
            if (width > maxWidth)
            {
                maxWidth = width;
            }
            height +=
                    // Ascent: der Abstand von der oberen rechten Ecke des
                    // Textlayouts zur Grundlinie.
                    line.getAscent() +
                    // Descent: Entfernung von der Grundlinie zum unteren linken
                    // Rand
                    // des Textlayouts
                            line.getDescent() +
                            // Leading: empfohlener Zeilenabstand relativ zur
                            // Grundlinie.
                            // Scheint meistens 0.0 zu sein?
                            line.getLeading();
        }
        dim.width = (int) Math.ceil(maxWidth);
        dim.height = (int) Math.ceil(height);
        return dim;
    }

    @Override
    void draw(Graphics2D g)
    {
        Color oldColor = g.getColor();
        if (color != null)
        {
            g.setColor(color);
        }
        float yCursor = (float) y;
        for (TextLayout line : lines)
        {
            // Ascent: der Abstand von der oberen rechten Ecke des
            // Textlayouts zur Grundlinie.
            yCursor += line.getAscent();
            float xCursor = (float) x;
            // Advance: Der Vorschub ist der Abstand vom Ursprung bis zum
            // Vorschub des Zeichen ganz rechts.
            float lineWidth = line.getAdvance();
            switch (hAlignment)
            {
            case LEFT:
                break;

            case CENTER:
                xCursor += (width - lineWidth) / 2;
                break;

            case RIGHT:
                xCursor += (width - lineWidth);
                break;
            }

            line.draw(g, xCursor, yCursor);
            // Descent: Entfernung von der Grundlinie zum unteren linken Rand
            // des Textlayouts
            yCursor += line.getDescent() +
            // Leading: empfohlener Zeilenabstand relativ zur Grundlinie.
            // Scheint meistens 0.0 zu sein?
                    line.getLeading();
        }
        g.setColor(oldColor);
    }

    @Override
    public String toString()
    {
        var formatter = toStringFormatter();
        if (lines.size() > 1)
        {
            formatter.add("lines", lines.size());
        }
        return formatter.format();
    }
}
