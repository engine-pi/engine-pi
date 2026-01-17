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

import static pi.Controller.colorScheme;
import static pi.Controller.colors;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import pi.annotations.API;
import pi.annotations.Getter;
import pi.annotations.Internal;
import pi.annotations.Setter;
import pi.debug.ToStringFormatter;
import pi.graphics.boxes.TextLineBox;
import pi.physics.FixtureBuilder;
import pi.resources.color.ColorContainer;
import pi.resources.font.FontStyle;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/TextDemo.java
// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/TextRandomDemo.java

/**
 * Zur Darstellung von einzeiligen <b>Texten</b>.
 *
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public class Text extends Geometry
{
    private TextLineBox box;

    /**
     * Der Skalierungsfaktor in x-Richtung.
     *
     * <p>
     * Wir erzeugen eine Box der Standardschriftgröße. Mithilfe dieses
     * Skalierungsfaktors wird die Box dann auf die gewünschte Größe skaliert.
     * Damit die Abmessungen einer Zeichenkette nicht bei jedem Einzelbild
     * erneut bestimmt werden müssen, dient dieses Attribut als Cache.
     * </p>
     */
    private double scaleFactorX;

    /**
     * Der Skalierungsfaktor in x-Richtung.
     *
     * <p>
     * Wir erzeugen eine Box der Standardschriftgröße. Mithilfe dieses
     * Skalierungsfaktors wird die Box dann auf die gewünschte Größe skaliert.
     * Damit die Abmessungen einer Zeichenkette nicht bei jedem Einzelbild
     * erneut bestimmt werden müssen, dient dieses Attribut als Cache.
     * </p>
     */
    private double scaleFactorY;

    /**
     * Erstellt einen <b>Text</b> mit spezifischem <b>Inhalt</b>.
     *
     * @param content Der <b>Textinhalt</b>, der dargestellt werden soll.
     *
     * @since 0.42.0
     */
    @API
    public Text(Object content)
    {
        super(null);
        box = new TextLineBox(content);
        Color color = colorScheme.get().white();
        box.color(color);
        color(color);
        syncAttributes();
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
    public Text content(Object content)
    {
        String normalizedContent = String.valueOf(content);
        if (normalizedContent == null)
        {
            normalizedContent = "";
        }
        if (!box.content().equals(normalizedContent))
        {
            box.content(normalizedContent);
            syncAttributes();
        }
        return this;
    }

    /* width */

    /**
     * Die <b>gesetzte Breite</b> in Meter.
     */
    private double definedWidth = 0;

    /**
     * Die <b>berechnete Breite</b> in Meter.
     */
    private double width = 0;

    /**
     * Gibt die <b>Breite</b> des Texts in Meter zurück.
     *
     * @return Die <b>Breite</b> des Texts in Meter zurück.
     *
     * @since 0.42.0
     */
    @API
    @Getter
    public double width()
    {
        return definedWidth;
    }

    /**
     * Setzt die <b>Breite</b> des Texts in Meter.
     *
     * @param width Die <b>Breite</b> des Texts in Meter.
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
    public Text width(double width)
    {
        if (definedWidth != width)
        {
            definedWidth = width;
            syncAttributes();
        }
        return this;
    }

    /* height */

    /**
     * Die <b>gesetzte Höhe</b> in Meter.
     */
    private double definedHeight = 0;

    /**
     * Die <b>berechnete Höhe</b> in Meter.
     */
    private double height = 0;

    /**
     * Gibt die <b>Höhe</b> des Texts in Meter zurück.
     *
     * @return Die <b>Höhe</b> des Texts in Meter.
     *
     * @since 0.42.0
     */
    @API
    @Getter
    public double height()
    {
        return definedHeight;
    }

    /**
     * Setzt die <b>Höhe</b> des Texts in Meter.
     *
     * @param height Die <b>Höhe</b> des Texts in Meter.
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
    public Text height(double height)
    {
        if (definedHeight != height)
        {
            definedHeight = height;
            syncAttributes();
        }
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
     * Setzt die <b>Farbe</b> der Figur auf eine bestimmte Farbe.
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
    public Text color(Color color)
    {
        super.color(color);
        box.color(color);
        return this;
    }

    /**
     * Setzt die <b>Farbe</b> der Figur auf eine bestimmte Farbe, die als
     * <b>Zeichenkette</b> angegeben werden kann.
     *
     * @param color Ein Farbname, ein Farbalias ({@link ColorContainer siehe
     *     Auflistung}) oder eine Farbe in hexadezimaler Codierung (z. B.
     *     {@code #ff0000}).
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
    public Text color(String color)
    {
        return color(colors.get(color));
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
    public Text font(String fontName)
    {
        box.font(fontName);
        syncAttributes();
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
    @Setter
    public Text font(Font font)
    {
        box.font(font);
        syncAttributes();
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
    public Text style(FontStyle style)
    {
        box.fontStyle(style);
        syncAttributes();
        return this;
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
    public Text style(int style)
    {
        box.fontStyle(style);
        syncAttributes();
        return this;
    }

    /**
     * @hidden
     */
    @Internal
    private void syncAttributes()
    {
        box.measure();

        if (definedWidth == 0 && definedHeight == 0)
        {
            height = 1;
            width = box.width() * height / box.height();
        }
        else if (definedWidth == 0)
        {
            width = box.width() * definedHeight / box.height();
            height = definedHeight;
        }
        else if (definedHeight == 0)
        {
            width = definedWidth;
            height = box.height() * definedWidth / box.width();
        }
        else
        {
            width = definedWidth;
            height = definedHeight;
        }

        scaleFactorX = width / (double) box.width();
        scaleFactorY = height / (double) box.height();
        fixture(() -> FixtureBuilder.rectangle(width, height));
    }

    /**
     * @hidden
     */
    @Override
    @Internal
    public void render(Graphics2D g, double pixelPerMeter)
    {
        AffineTransform oldTransform = g.getTransform();
        g.translate(0, -height * pixelPerMeter);
        g.scale(scaleFactorX * pixelPerMeter, scaleFactorY * pixelPerMeter);
        box.render(g);
        g.setTransform(oldTransform);
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        ToStringFormatter formatter = new ToStringFormatter("Text");
        formatter.append("content", content());
        return formatter.format();
    }
}
