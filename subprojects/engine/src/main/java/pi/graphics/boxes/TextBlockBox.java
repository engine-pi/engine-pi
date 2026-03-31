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

import pi.annotations.API;
import pi.annotations.ChainableMethod;
import pi.annotations.Setter;
import pi.resources.font.FontUtil;

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/graphics/boxes/TextBlockBoxDemo.java

/**
 * Ein mehrzeiliger <b>Textblock</b>.
 *
 * @author Josef Friedrich
 *
 * @since 0.41.0
 */
public class TextBlockBox extends TextBox
{
    /**
     * Eine Zeile des Textblocks.
     *
     * @param layout Das vom Font-Renderer berechnete Layout der Zeile.
     * @param lineContent Der tatsächliche Textinhalt dieser Zeile.
     * @param parentContent Der ursprüngliche vollständige Inhalt des
     *     Textblocks.
     * @param startIndex Der Startindex (inklusive) innerhalb des vollständigen
     *     Inhalts.
     * @param endIndex Der Endindex (exklusive) innerhalb des vollständigen
     *     Inhalts.
     *
     * @since 0.45.0
     */
    public static record TextLayoutLine(TextLayout layout, String lineContent,
            String parentContent, int startIndex, int endIndex)
    {
    }

    /**
     * Erzeugt einen mehrzeiligen <b>Textblock</b>.
     *
     * @param content Der <b>Inhalt</b> des Textblocks als Zeichenkette.
     *
     * @since 0.41.0
     */
    public TextBlockBox(Object content)
    {
        super(content);
    }

    private List<TextLayoutLine> lines = new ArrayList<>();

    /**
     * Gibt die intern berechneten, umgebrochenen Textzeilen zurück.
     *
     * @return Eine Liste aus Layout-/Text-Paaren je Zeile.
     */
    public List<TextLayoutLine> lines()
    {
        return lines;
    }

    /**
     * Gibt den Textinhalt der einzelnen Zeilen zurück.
     *
     * @return Eine Liste aus Layout-/Text-Paaren je Zeile.
     */
    public String[] linesText()
    {
        return lines.stream()
            .map(TextLayoutLine::lineContent)
            .toArray(String[]::new);
    }

    /* hAlign */

    private HAlign hAlign = HAlign.LEFT;

    /**
     * Setzt die horizontale Ausrichtung der einzelnen Textzeilen.
     *
     * @param hAlign Die gewünschte Ausrichtung.
     *
     * @return Dieses Objekt für Methodenverkettung.
     */
    @API
    @Setter
    @ChainableMethod
    public TextBlockBox hAlign(HAlign hAlign)
    {
        this.hAlign = hAlign;
        return this;
    }

    protected void calculateDimension()
    {
        width = definedWidth > 0 ? definedWidth : 300;
        lines = splitIntoLines(content,
            FontUtil.getFontRenderContext(),
            font,
            width);
        var dim = measureLines(lines);
        width = dim.width;
        height = dim.height;
    }

    /**
     * Setzt die Breite des Textblocks für den Zeilenumbruch.
     *
     * @param width Die Zielbreite in Pixeln.
     *
     * @return Dieses Objekt für Methodenverkettung.
     */
    @API
    @Setter
    @ChainableMethod
    public TextBlockBox width(int width)
    {
        definedWidth = width;
        return this;
    }

    private static List<TextLayoutLine> splitIntoLines(String content,
            FontRenderContext context, Font font, float wrappingWidth)
    {
        // Source:
        // https://github.com/gurkenlabs/litiengine/blob/0fae965994a30757b078153a67f095fe122ae456/litiengine/src/main/java/de/gurkenlabs/litiengine/graphics/TextRenderer.java#L279-L315
        ArrayList<TextLayoutLine> lines = new ArrayList<>();
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

                // Die Klasse „TextLayout“ bietet keine direkte Methode zum
                // Extrahieren der ursprünglichen Zeichenfolge. Wir haben jedoch
                // Zugriff auf den „AttributedCharacterIterator“, der die
                // Positionen der Zeichen enthält. Indem wir die Start- und
                // Endpositionen des aktuellen Layouts verwenden, können wir den
                // entsprechenden Abschnitt der ursprünglichen Zeichenfolge
                // extrahieren, um den tatsächlichen Textinhalt der Zeile zu
                // erhalten.
                int end = measurer.getPosition();
                int start = end - nextLayout.getCharacterCount();
                String lineContent = line.substring(start, end);
                lines.add(new TextLayoutLine(nextLayout, lineContent, content,
                        start, end));

                if (measurer.getPosition() >= line.length())
                {
                    break;
                }
            }
        }
        return lines;
    }

    private static PixelDimension measureLines(List<TextLayoutLine> lines)
    {
        // Source:
        // https://github.com/gurkenlabs/litiengine/blob/0fae965994a30757b078153a67f095fe122ae456/litiengine/src/main/java/de/gurkenlabs/litiengine/graphics/TextRenderer.java#L279-L315
        PixelDimension dim = new PixelDimension();
        float maxWidth = 0;
        float height = 0;
        for (TextLayoutLine line : lines)
        {
            TextLayout layout = line.layout();
            // Advance: Der Vorschub ist der Abstand vom Ursprung bis zum
            // Vorschub des Zeichens ganz rechts.
            float width = layout.getAdvance();
            if (width > maxWidth)
            {
                maxWidth = width;
            }
            height +=
                    // Ascent: der Abstand von der oberen rechten Ecke des
                    // Textlayouts zur Grundlinie.
                    layout.getAscent() +
                    // Descent: Entfernung von der Grundlinie zum unteren linken
                    // Rand des Textlayouts
                            layout.getDescent() +
                            // Leading: empfohlener Zeilenabstand relativ zur
                            // Grundlinie.
                            // Scheint meistens 0.0 zu sein?
                            layout.getLeading();
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
        for (TextLayoutLine line : lines)
        {
            TextLayout layout = line.layout();
            // Ascent: der Abstand von der oberen rechten Ecke des
            // Textlayouts zur Grundlinie.
            yCursor += layout.getAscent();
            float xCursor = (float) x;
            // Advance: Der Vorschub ist der Abstand vom Ursprung bis zum
            // Vorschub des Zeichens ganz rechts.
            float lineWidth = layout.getAdvance();
            switch (hAlign)
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

            layout.draw(g, xCursor, yCursor);
            // Descent: Entfernung von der Grundlinie zum unteren linken Rand
            // des Textlayouts
            yCursor += layout.getDescent() +
            // Leading: empfohlener Zeilenabstand relativ zur Grundlinie.
            // Scheint meistens 0.0 zu sein?
                    layout.getLeading();
        }
        g.setColor(oldColor);
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        var formatter = toStringFormatter();
        if (lines.size() > 1)
        {
            formatter.append("lines", lines.size());
        }
        return formatter.format();
    }
}
