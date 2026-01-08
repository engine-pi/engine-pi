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

    /**
     * @hidden
     */
    @Internal
    private static FixtureData createShape(String content, double height,
            Font font)
    {
        var sizeInPixels = FontUtil.getStringBounds(content, font);
        return FixtureBuilder.rectangle(
                sizeInPixels.getWidth() * height / sizeInPixels.getHeight(),
                height);
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
     * Erstellt einen <b>Text</b> mit spezifischem <b>Inhalt</b> in <b>normaler,
     * serifenfreier Standardschrift</b> mit <b>einem Meter Höhe</b>.
     *
     * @param content Der <b>Textinhalt</b>, der dargestellt werden soll.
     *
     * @since 0.27.0
     */
    @API
    public Text(String content)
    {
        this(content, 1);
    }

    /**
     * Erstellt einen <b>Text</b> mit spezifischem <b>Inhalt</b> und <b>Höhe</b>
     * in <b>normaler, serifenfreier Standardschrift</b>.
     *
     * @param content Der <b>Textinhalt</b>, der dargestellt werden soll.
     * @param height Die <b>Höhe</b> des Textes in Meter.
     */
    @API
    public Text(String content, double height)
    {
        this(content, height, fonts.defaultFont().deriveFont((float) SIZE), 0);
    }

    /**
     * Erstellt einen <b>Text</b> mit spezifischem <b>Inhalt</b>, <b>Höhe</b>
     * und <b>Schriftart</b> in <b>nicht fettem und nicht kursiven
     * Schriftstil</b>.
     *
     * @param content Der <b>Textinhalt</b>, der dargestellt werden soll.
     * @param height Die <b>Höhe</b> des Textes in Meter.
     * @param fontName Der <b>Name</b> der Schriftart, falls es sich um eine
     *     Systemschriftart handelt, oder der <b>Pfad</b> zu einer Schriftdatei.
     */
    @API
    public Text(String content, double height, String fontName)
    {
        this(content, height, fontName, 0);
    }

    /**
     * Erstellt einen <b>Text</b> mit spezifischem <b>Inhalt</b>, <b>Höhe</b>,
     * <b>Schriftart</b>, und <b>Schriftstil</b>.
     *
     * @param content Der <b>Textinhalt</b>, der dargestellt werden soll.
     * @param height Die <b>Höhe</b> des Textes in Meter.
     * @param fontName Der <b>Name</b> der Schriftart, falls es sich um eine
     *     Systemschriftart handelt, oder der <b>Pfad</b> zu einer Schriftdatei.
     * @param style Der Stil der Schriftart (<b>fett, kursiv, oder fett und
     *     kursiv</b>).
     *     <ul>
     *     <li>{@code 0}: Normaler Text</li>
     *     <li>{@code 1}: Fett</li>
     *     <li>{@code 2}: Kursiv</li>
     *     <li>{@code 3}: Fett und Kursiv</li>
     *     </ul>
     */
    @API
    public Text(String content, double height, String fontName, int style)
    {
        super(() -> createShape(content == null ? "" : content, height,
                fonts.get(fontName).deriveFont(style, SIZE)));
        this.content = content == null ? "" : content;
        this.height = height;
        setStyle(style);
        font(fontName);
        color(colorScheme.get().white());
    }

    public Text(String content, double height, Font font, int style)
    {
        super(() -> createShape(content == null ? "" : content, height,
                font.deriveFont(style, SIZE)));
        this.content = content == null ? "" : content;
        this.height = height;
        setStyle(style);
        font(font);
        color(colorScheme.get().white());
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
        this.font = font.deriveFont(fontStyle, SIZE);
        update();
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

    @API
    @Getter
    public Font font()
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
    @Setter
    public Text content(String content)
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
    @Setter
    public Text content(Object content)
    {
        content(String.valueOf(content));
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
    @Setter
    public Text height(double height)
    {
        if (this.height != height)
        {
            this.height = height;
            update();
        }
        return this;
    }

    @API
    @Getter
    public double height()
    {
        return height;
    }

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
    public Text setWidth(double width)
    {
        var sizeInPixels = FontUtil.getStringBounds(content, font);
        height(width / sizeInPixels.getWidth() * sizeInPixels.getHeight());
        return this;
    }

    /**
     * @hidden
     */
    @Internal
    private void update()
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
