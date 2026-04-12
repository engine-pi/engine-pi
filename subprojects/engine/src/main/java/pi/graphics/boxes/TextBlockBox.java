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
import pi.annotations.Getter;
import pi.annotations.Setter;
import pi.resources.font.FontUtil;
import pi.util.TextUtil;

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/graphics/boxes/TextBlockBoxDemo.java

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/test/java/pi/graphics/boxes/TextBlockBoxTest.java

/**
 * Ein mehrzeiliger <b>Textblock</b>.
 *
 * @author Josef Friedrich
 *
 * @since 0.41.0
 */
public class TextBlockBox extends TextBox
{
    private static final long WRAPPING_WIDTH_PX = 300;

    /**
     * Eine Zeile des Textblocks.
     *
     * @param layout Das vom Font-Renderer berechnete Layout der Zeile.
     * @param lineContent Der Textinhalt dieser Zeile.
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
     * @param content Der <b>Inhalt</b> des Textblocks. Es können mehrere
     *     Eingabewerte angegeben werden. Jeder Eingabewert wird in eine eigene
     *     Zeile gesetzt.
     *
     * @since 0.41.0
     */
    public TextBlockBox(Object... content)
    {
        super(TextUtil.convertToMultilineString(content));
        wrap();
        calculateDimension();
    }

    /* content */

    /**
     * Setzt den <b>Inhalt</b> des Textblocks.
     *
     * @param content Der <b>Inhalt</b> des Textblocks. Es können mehrere
     *     Eingabewerte angegeben werden. Jeder Eingabewert wird in eine eigene
     *     Zeile gesetzt.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.45.0
     */
    public TextBlockBox content(Object... content)
    {
        super.content(TextUtil.convertToMultilineString(content));
        wrap();
        calculateDimension();
        return this;
    }

    /* width */

    /**
     * Setzt die <b>Breite</b> des Textblocks.
     *
     * @param width Die <b>Breite</b> des Textblocks in Pixeln.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code box.x(..).y(..)}.
     */
    @API
    @Setter
    @ChainableMethod
    @Override
    public TextBlockBox width(int width)
    {
        if (width < 1)
        {
            throw new IllegalArgumentException(
                    "Die Breite des Textblocks muss mindestens 1 Pixel betragen.");
        }
        definedWidth = width;
        charsPerLine = 0;
        calculateDimension();
        return this;
    }

    /* charsPerLine */

    private int charsPerLine = 0;

    /**
     * Gibt die maximale Zeichenbreite pro Zeile des Textblocks.
     *
     * @return Die maximale Zeichenbreite pro Zeile.
     */
    @API
    @Getter
    public int charsPerLine()
    {
        return charsPerLine;
    }

    /**
     * Setzt die maximale Zeichenbreite pro Zeile des Textblocks.
     *
     * @param charsPerLine Die maximale Zeichenbreite pro Zeile.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code box.x(..).y(..)}.
     */
    @API
    @Setter
    @ChainableMethod
    public TextBlockBox charsPerLine(int charsPerLine)
    {
        if (charsPerLine < 1)
        {
            throw new IllegalArgumentException(
                    "Die Zeilenbreite muss mindestens 1 Zeichen betragen, damit der Text umbrochen werden kann.");
        }
        this.charsPerLine = charsPerLine;
        // Damit wird die Zeilenbreite für die automatische Berechnung der
        // Box-Breite verwendet.
        definedWidth = 0;
        wrap();
        calculateDimension();
        return this;
    }

    private void wrap()
    {
        if (charsPerLine > 0)
        {
            content = TextUtil.wrap(content, charsPerLine);
        }
    }

    /* hAlign */

    private HAlign hAlign = HAlign.LEFT;

    /**
     * Gibt die <b>horizontale</b> Ausrichtung der einzelnen Textzeilen.
     *
     * @return Die <b>horizontale</b> Ausrichtung.
     */
    @API
    @Getter
    public HAlign hAlign()
    {
        return hAlign;
    }

    /**
     * Setzt die <b>horizontale</b> Ausrichtung der einzelnen Textzeilen.
     *
     * @param hAlign Die gewünschte <b>horizontale</b> Ausrichtung.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code box.x(..).y(..)}.
     */
    @API
    @Setter
    @ChainableMethod
    public TextBlockBox hAlign(HAlign hAlign)
    {
        this.hAlign = hAlign;
        return this;
    }

    /* lineSpacing */

    private double lineSpacing = 1;

    /**
     * Gibt den <b>Zeilenabstand</b>.
     *
     * <p>
     * Der Zeilenabstand ist ein Faktor, der den Abstand zwischen den Zeilen
     * relativ zum Standardabstand bestimmt. Ein Wert von {@code 1} bedeutet den
     * normalen Zeilenabstand, Werte größer als {@code 1} erhöhen den Abstand,
     * während Werte zwischen {@code 0} und {@code 1} den Abstand verringern.
     * Zum Beispiel würde ein Wert von {@code 1.5} den Zeilenabstand um
     * {@code 50%} erhöhen, während ein Wert von {@code 0.75} den Zeilenabstand
     * um {@code 25%} verringern würde.
     * </p>
     *
     * @return Der <b>Zeilenabstand</b>.
     */
    @API
    @Getter
    public double lineSpacing()
    {
        return lineSpacing;
    }

    /**
     * Setzt den <b>Zeilenabstand</b>.
     *
     * <p>
     * Der Zeilenabstand ist ein Faktor, der den Abstand zwischen den Zeilen
     * relativ zum Standardabstand bestimmt. Ein Wert von {@code 1} bedeutet den
     * normalen Zeilenabstand, Werte größer als {@code 1} erhöhen den Abstand,
     * während Werte zwischen {@code 0} und {@code 1} den Abstand verringern.
     * Zum Beispiel würde ein Wert von {@code 1.5} den Zeilenabstand um
     * {@code 50%} erhöhen, während ein Wert von {@code 0.75} den Zeilenabstand
     * um {@code 25%} verringern würde.
     * </p>
     *
     * @param lineSpacing Der <b>Zeilenabstand</b>.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code box.x(..).y(..)}.
     */
    @API
    @Setter
    @ChainableMethod
    public TextBlockBox lineSpacing(double lineSpacing)
    {
        if (lineSpacing <= 0)
        {
            throw new IllegalArgumentException(
                    "Der Zeilenabstand muss ein positiver Wert sein.");
        }
        this.lineSpacing = lineSpacing;
        calculateDimension();
        return this;
    }

    /* lines */

    private List<TextLayoutLine> lines = new ArrayList<>();

    /**
     * Gibt die intern berechneten, umgebrochenen <b>Textzeilen</b> zurück.
     *
     * @return Eine Liste aus Layout-/Text-Paaren je Zeile.
     *
     * @since 0.45.0
     */
    @API
    @Getter
    public List<TextLayoutLine> lines()
    {
        return lines;
    }

    /**
     * Gibt den <b>Textinhalt</b> der einzelnen Zeilen zurück.
     *
     * @return Jede Zeile ist ein Element des Arrays.
     *
     * @since 0.45.0
     */
    @API
    public String[] linesText()
    {
        return lines.stream()
            .map(TextLayoutLine::lineContent)
            .toArray(String[]::new);
    }

    /**
     * Gibt die <b>Anzahl der Zeilen</b> in diesem Textblock zurück.
     *
     * @return Die <b>Anzahl der Zeilen</b>.
     *
     * @since 0.45.0
     */
    @API
    public int linesCount()
    {
        return lines.size();
    }

    protected void calculateDimension()
    {
        float wrappingWidth;

        if (charsPerLine == 0 && definedWidth == 0)
        {
            wrappingWidth = WRAPPING_WIDTH_PX;
        }
        else if (charsPerLine > 0)
        {
            wrappingWidth = Float.MAX_VALUE;
        }
        else
        {
            wrappingWidth = definedWidth;
        }
        lines = splitIntoLines(content,
            FontUtil.getFontRenderContext(),
            font,
            wrappingWidth);
        var dim = measureLines(lines);
        width = dim.width;
        height = dim.height;
    }

    private List<TextLayoutLine> splitIntoLines(String content,
            FontRenderContext context, Font font, float wrappingWidth)
    {
        // Source:
        // https://github.com/gurkenlabs/litiengine/blob/0fae965994a30757b078153a67f095fe122ae456/litiengine/src/main/java/de/gurkenlabs/litiengine/graphics/TextRenderer.java#L279-L315
        ArrayList<TextLayoutLine> resultingLines = new ArrayList<>();
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
                resultingLines.add(new TextLayoutLine(nextLayout, lineContent,
                        content, start, end));

                if (measurer.getPosition() >= line.length())
                {
                    break;
                }
            }
        }
        return resultingLines;
    }

    private PixelDimension measureLines(List<TextLayoutLine> lines)
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
                    layout.getAscent() * lineSpacing +
                    // Descent: Entfernung von der Grundlinie zum unteren linken
                    // Rand des Textlayouts
                            layout.getDescent() * lineSpacing +
                            // Leading: empfohlener Zeilenabstand relativ zur
                            // Grundlinie.
                            // Scheint meistens 0.0 zu sein?
                            layout.getLeading() * lineSpacing;
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
        float yCursor = y;
        for (TextLayoutLine line : lines)
        {
            TextLayout layout = line.layout();
            // Ascent: der Abstand von der oberen rechten Ecke des
            // Textlayouts zur Grundlinie.
            yCursor += layout.getAscent() * lineSpacing;
            float xCursor = x;
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
            yCursor += layout.getDescent() * lineSpacing +
            // Leading: empfohlener Zeilenabstand relativ zur Grundlinie.
            // Scheint meistens 0.0 zu sein?
                    layout.getLeading() * lineSpacing;
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
        if (linesCount() > 1)
        {
            formatter.append("linesCount", linesCount());
        }
        return formatter.format();
    }
}
