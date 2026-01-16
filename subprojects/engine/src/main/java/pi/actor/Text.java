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
package pi.actor;

import static pi.Controller.colorScheme;
import static pi.Controller.fonts;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import pi.annotations.API;
import pi.annotations.Getter;
import pi.annotations.Internal;
import pi.annotations.Setter;
import pi.debug.ToStringFormatter;
import pi.physics.FixtureBuilder;
import pi.physics.FixtureData;
import pi.resources.font.FontUtil;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/TextDemo.java

/**
 * Zur Darstellung von <b>Texten</b>.
 *
 * @author Michael Andonie
 * @author Niklas Keller
 */
public class Text extends Geometry
{
    /**
     * Needs to be large enough, so we don't have rounding errors due to
     * integers in font metrics
     */
    private static final int SIZE = 1000;

    private transient int cachedDescent;

    private transient double cachedScaleFactor;

    /**
     * Erstellt einen <b>Text</b> mit spezifischem <b>Inhalt</b>, <b>Höhe</b>,
     * <b>Schriftart</b>, und <b>Schriftstil</b>.
     *
     * @param content Der <b>Textinhalt</b>, der dargestellt werden soll.
     */
    @API
    public Text(Object content)
    {
        super(null);
        this.content = content == null ? "" : String.valueOf(content);
        color(colorScheme.get().white());
        syncAttributes();
    }

    /**
     * @hidden
     */
    @Internal
    private static FixtureData createShape(Object content, double height,
            Font font)
    {
        var sizeInPixels = FontUtil.getStringBounds(String.valueOf(content),
                font);
        return FixtureBuilder.rectangle(
                sizeInPixels.getWidth() * height / sizeInPixels.getHeight(),
                height);
    }

    /* content */

    /**
     * Der Textinhalt, der dargestellt werden soll.
     */
    private String content;

    /**
     * Setzt den Textinhalt, der dargestellt werden soll.
     *
     * @param content Der Textinhalt, der dargestellt werden soll.
     *
     * @return Eine Instanz dieser Textfigur, damit mehrere Setter durch die
     *     Punktschreibweise aneinander gekettet werden können.
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
        if (!this.content.equals(normalizedContent))
        {
            this.content = normalizedContent;
        }
        syncAttributes();
        return this;
    }

    /**
     * Gibt den Textinhalt, der dargestellt werden soll, zurück.
     *
     * @return Der Textinhalt, der dargestellt werden soll.
     */
    @API
    @Getter
    public String content()
    {
        return content;
    }

    /* font */

    /**
     * Die Schriftart, in der der Text dargestellt werden soll.
     */
    private Font font = fonts.defaultFont().deriveFont((float) SIZE);

    @API
    @Getter
    public Font font()
    {
        return font;
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
    @Setter
    public Text font(Font font)
    {
        this.font = font.deriveFont(style, SIZE);
        syncAttributes();
        return this;
    }

    /**
     * Setzt eine neue Schriftart für den Text durch Angabe des Names.
     *
     * @param fontName Der <b>Name</b> der Schriftart, falls es sich um eine
     *     Systemschriftart handelt, oder der <b>Pfad</b> zu einer Schriftdatei.
     *
     * @return Eine Instanz dieser Textfigur, damit mehrere Setter durch die
     *     Punktschreibweise aneinander gekettet werden können.
     */
    @API
    @Setter
    public Text font(String fontName)
    {
        font(fonts.get(fontName));
        return this;
    }

    /* style */

    /**
     * Der <b>Stil</b> der Schriftart (<i>fett, kursiv, oder fett und
     * kursiv</i>).
     *
     * <ul>
     * <li>{@code 0}: Normaler Text</li>
     * <li>{@code 1}: Fett</li>
     * <li>{@code 2}: Kursiv</li>
     * <li>{@code 3}: Fett und Kursiv</li>
     * </ul>
     */
    private int style;

    /**
     * Setzt den <b>Stil</b> der Schriftart (<i>fett, kursiv, oder fett und
     * kursiv</i>).
     *
     * @param style Der Stil der Schriftart (<i>fett, kursiv, oder fett und
     *     kursiv</i>)..
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
    @Setter
    public Text style(int style)
    {
        if (style >= 0 && style <= 3 && this.style != style)
        {
            this.style = style;
            font = font.deriveFont(style, SIZE);
            syncAttributes();
        }
        return this;
    }

    @API
    @Getter
    public int style()
    {
        return style;
    }

    /* width */

    @SuppressWarnings("unused")
    private double width;

    /**
     * Gibt die <b>Breite</b> des Texts in Meter zurück.
     *
     * @return Die <b>Breite</b> des Texts in Meter zurück.
     */
    @API
    @Getter
    public double width()
    {
        var sizeInPixels = FontUtil.getStringBounds(content, font);
        return sizeInPixels.getWidth() * height / sizeInPixels.getHeight();
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
    @Setter
    public Text width(double width)
    {
        this.width = width;
        var sizeInPixels = FontUtil.getStringBounds(content, font);
        height(width / sizeInPixels.getWidth() * sizeInPixels.getHeight());
        return this;
    }

    /* height */

    /**
     * Die Höhe des Textes in Meter.
     */
    private double height = 1;

    @API
    @Getter
    public double height()
    {
        return height;
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
    @Setter
    public Text height(double height)
    {
        if (this.height != height)
        {
            this.height = height;
            syncAttributes();
        }
        return this;
    }

    /**
     * @hidden
     */
    @Internal
    private void syncAttributes()
    {
        var size = FontUtil.getStringBounds(content, font);
        cachedScaleFactor = height / size.getHeight();
        cachedDescent = FontUtil.getDescent(font);
        fixture(() -> createShape(content, height, font));
    }

    /**
     * @hidden
     */
    @Override
    @Internal
    public void render(Graphics2D g, double pixelPerMeter)
    {
        AffineTransform pre = g.getTransform();
        Font preFont = g.getFont();
        g.setColor(color());
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
        formatter.append("content", content);
        return formatter.format();
    }
}
