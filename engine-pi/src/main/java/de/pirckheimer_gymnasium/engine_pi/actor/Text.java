/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/actor/Text.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2019 Michael Andonie and contributors.
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
package de.pirckheimer_gymnasium.engine_pi.actor;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.annotations.API;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;
import de.pirckheimer_gymnasium.engine_pi.debug.ToStringFormatter;
import de.pirckheimer_gymnasium.engine_pi.physics.FixtureBuilder;
import de.pirckheimer_gymnasium.engine_pi.physics.FixtureData;
import de.pirckheimer_gymnasium.engine_pi.util.FontMetrics;
import de.pirckheimer_gymnasium.engine_pi.Resources;

/**
 * Zur Darstellung von <b>Texten</b>.
 *
 * Möglich ist es auch, Fonts zu laden, die im Projektordner sind. Diese werden
 * zu Anfang einmalig geladen und stehen dauerhaft zur Verfügung.
 *
 * @author Michael Andonie
 * @author Niklas Keller
 */
public class Text extends Geometry
{
    // Needs to be large enough, so we don't have rounding errors due to
    // integers
    // in font metrics
    private static final int SIZE = 1000;

    @Internal
    private static FixtureData createShape(String content, double height,
            Font font)
    {
        Vector sizeInPixels = FontMetrics.getSize(content, font);
        return FixtureBuilder.rectangle(
                sizeInPixels.getX() * height / sizeInPixels.getY(), height);
    }

    /**
     * Die Höhe des Textes in Meter.
     */
    private double height;

    /**
     * Der Stil der Schriftart (<b>fett, kursiv, oder fett und kursiv</b>).
     *
     * <ul>
     * <li>{@code 0}: Normaler Text</li>
     * <li>{@code 1}: Fett</li>
     * <li>{@code 2}: Kursiv</li>
     * <li>{@code 3}: Fett und Kursiv</li>
     * </ul>
     */
    private int fontStyle;

    /**
     * Der Textinhalt, der dargestellt werden soll.
     */
    private String content;

    /**
     * Die Schriftart, in der der Text dargestellt werden soll.
     */
    private Font font;

    private transient int cachedDescent;

    private transient double cachedScaleFactor;

    /**
     * Erstellt einen <b>Text</b> mit spezifischem <b>Inhalt</b>, <b>Höhe</b>,
     * <b>Schriftart</b>, und <b>Schriftstil</b>.
     *
     * @param content Der Textinhalt, der dargestellt werden soll.
     * @param height Die Höhe des Textes in Meter.
     * @param fontName Der Name des zu verwendenden Fonts.<br>
     *     Wird hierfür ein Font verwendet, der in dem Projektordner vorhanden
     *     sein soll, <b>und dies ist immer und in jedem Fall zu empfehlen</b>,
     *     muss der Name der Schriftart hier ebenfalls einfach nur eingegeben
     *     werden, <b>nicht der Name der schriftart-Datei!</b>
     * @param style Der Stil der Schriftart (<b>fett, kursiv, oder fett und
     *     kursiv</b>).
     *     <ul>
     *     <li>{@code 0}: Normaler Text</li>
     *     <li>{@code 1}: Fett</li>
     *     <li>{@code 2}: Kursiv</li>
     *     <li>{@code 3}: Fett und Kursiv</li>
     *     </ul>
     *
     * @see de.pirckheimer_gymnasium.engine_pi.actor.ActorAdder#addText(String,
     *     double, String, int)
     */
    @API
    public Text(String content, double height, String fontName, int style)
    {
        super(() -> createShape(content == null ? "" : content, height,
                Resources.FONTS.get(fontName).deriveFont(style, SIZE)));
        this.content = content == null ? "" : content;
        this.height = height;
        setStyle(style);
        setFont(fontName);
        setColor("black");
    }

    /**
     * Erstellt einen <b>Text</b> mit spezifischem <b>Inhalt</b>, <b>Höhe</b>
     * und <b>Schriftart</b> in <b>nicht fettem und nicht kursiven
     * Schriftstil</b>.
     *
     * @param content Der Textinhalt, der dargestellt werden soll.
     * @param height Die Höhe des Textes in Meter.
     * @param fontName Die Schriftart, in der der Text dargestellt werden soll.
     *
     * @see de.pirckheimer_gymnasium.engine_pi.actor.ActorAdder#addText(String,
     *     double, String)
     */
    @API
    public Text(String content, double height, String fontName)
    {
        this(content, height, fontName, 0);
    }

    /**
     * Erstellt einen <b>Text</b> mit spezifischem <b>Inhalt</b> und <b>Höhe</b>
     * in <b>normaler, serifenfreier Standardschrift</b>.
     *
     * @param content Der Textinhalt, der dargestellt werden soll.
     * @param height Die Höhe des Textes in Meter.
     *
     * @see de.pirckheimer_gymnasium.engine_pi.actor.ActorAdder#addText(String,
     *     double)
     */
    @API
    public Text(String content, double height)
    {
        this(content, height, Font.SANS_SERIF);
    }

    /**
     * Erstellt einen <b>Text</b> mit spezifischem <b>Inhalt</b> in <b>normaler,
     * serifenfreier Standardschrift</b> mit <b>einem Meter Höhe</b>.
     *
     * @param content Der Textinhalt, der dargestellt werden soll.
     *
     * @since 0.27.0
     *
     * @see de.pirckheimer_gymnasium.engine_pi.actor.ActorAdder#addText(String)
     */
    @API
    public Text(String content)
    {
        this(content, 1);
    }

    /**
     * Setzt eine neue Schriftart durch Angabe einer bereits geladenen
     * Schriftart.
     *
     * @param font Eine bereits geladene Schriftart.
     *
     * @return Eine Instanz dieser Textfigur, damit mehrere Setter durch die
     *     Punktschreibweise aneinander gekettet werden können.
     */
    @API
    public Text setFont(Font font)
    {
        this.font = font.deriveFont(fontStyle, SIZE);
        update();
        return this;
    }

    /**
     * Setzt eine neue Schriftart für den Text durch Angabe des Names.
     *
     * @param fontName Die Schriftart, in der der Text dargestellt werden soll.
     *
     * @return Eine Instanz dieser Textfigur, damit mehrere Setter durch die
     *     Punktschreibweise aneinander gekettet werden können.
     */
    @API
    public Text setFont(String fontName)
    {
        setFont(Resources.FONTS.get(fontName));
        return this;
    }

    @API
    public Font getFont()
    {
        return font;
    }

    /**
     * Setzt den Textinhalt, der dargestellt werden soll.
     *
     * @param content Der Textinhalt, der dargestellt werden soll.
     *
     * @return Eine Instanz dieser Textfigur, damit mehrere Setter durch die
     *     Punktschreibweise aneinander gekettet werden können.
     */
    @API
    public Text setContent(String content)
    {
        String normalizedContent = content;
        if (normalizedContent == null)
        {
            normalizedContent = "";
        }
        if (!this.content.equals(normalizedContent))
        {
            this.content = normalizedContent;
            update();
        }
        return this;
    }

    /**
     * Setzt den Inhalt des Textes durch Angabe eines beliebigen Datentyps.
     *
     * @param content Der neue Inhalt des Textes in einem beliebigen Datentyp.
     *
     * @return Eine Instanz dieser Textfigur, damit mehrere Setter durch die
     *     Punktschreibweise aneinander gekettet werden können.
     */
    @API
    public Text setContent(Object content)
    {
        setContent(String.valueOf(content));
        return this;
    }

    /**
     * Gibt den Textinhalt, der dargestellt werden soll, zurück.
     *
     * @return Der Textinhalt, der dargestellt werden soll.
     */
    @API
    public String getContent()
    {
        return content;
    }

    /**
     * Setzt den Stil der Schriftart (<b>fett, kursiv, oder fett und
     * kursiv</b>).
     *
     * @param style Der Stil der Schriftart (<b>fett, kursiv, oder fett und
     *     kursiv</b>).
     *
     *     <ul>
     *     <li>{@code 0}: Normaler Text</li>
     *     <li>{@code 1}: Fett</li>
     *     <li>{@code 2}: Kursiv</li>
     *     <li>{@code 3}: Fett und Kursiv</li>
     *     </ul>
     *
     * @return Eine Instanz dieser Textfigur, damit mehrere Setter durch die
     *     Punktschreibweise aneinander gekettet werden können.
     */
    @API
    public Text setStyle(int style)
    {
        if (style >= 0 && style <= 3 && style != fontStyle)
        {
            fontStyle = style;
            font = font.deriveFont(style, SIZE);
            update();
        }
        return this;
    }

    @API
    public int getStyle()
    {
        return fontStyle;
    }

    /**
     * Setzt die <b>Höhe</b> des Tests in Meter.
     *
     * @param height Die Höhe des Texts in Meter.
     *
     * @return Eine Instanz dieser Textfigur, damit mehrere Setter durch die
     *     Punktschreibweise aneinander gekettet werden können.
     */
    @API
    public Text setHeight(double height)
    {
        if (this.height != height)
        {
            this.height = height;
            update();
        }
        return this;
    }

    @API
    public double getHeight()
    {
        return height;
    }

    /**
     * Gibt die <b>Breite</b> des Texts in Meter zurück.
     *
     * @return Die <b>Breite</b> des Texts in Meter zurück.
     */
    @API
    public double getWidth()
    {
        Vector sizeInPixels = FontMetrics.getSize(content, font);
        return sizeInPixels.getX() * height / sizeInPixels.getY();
    }

    /**
     * Setzt die <b>Breite</b> des Texts in Meter.
     *
     * @param width Die Breite des Texts in Meter.
     *
     * @return Eine Instanz dieser Textfigur, damit mehrere Setter durch die
     *     Punktschreibweise aneinander gekettet werden können.
     */
    @API
    public Text setWidth(double width)
    {
        Vector sizeInPixels = FontMetrics.getSize(content, font);
        setHeight(width / sizeInPixels.getX() * sizeInPixels.getY());
        return this;
    }

    @Internal
    private void update()
    {
        Vector size = FontMetrics.getSize(content, font);
        cachedScaleFactor = height / size.getY();
        cachedDescent = FontMetrics.getDescent(font);
        setFixture(() -> createShape(content, height, font));
    }

    /**
     * Zeichnet die Figur an der Position {@code (0|0)} mit der Rotation
     * {@code 0}.
     *
     * @param g Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
     */
    @Override
    @Internal
    public void render(Graphics2D g, double pixelPerMeter)
    {
        AffineTransform pre = g.getTransform();
        Font preFont = g.getFont();
        g.setColor(getColor());
        g.scale(cachedScaleFactor * pixelPerMeter,
                cachedScaleFactor * pixelPerMeter);
        g.setFont(font);
        g.drawString(content, 0, -cachedDescent);
        g.setFont(preFont);
        g.setTransform(pre);
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        ToStringFormatter formatter = new ToStringFormatter("Text");
        formatter.add("content", content);
        return formatter.format();
    }
}
