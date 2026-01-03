/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2024 Josef Friedrich and contributors.
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

import java.awt.Color;

import pi.Resources;
import pi.annotations.Getter;
import pi.annotations.Setter;
import pi.util.TextAlignment;

/**
 * Zur Darstellung von <b>Texten</b> durch eine <b>Bilderschriftart</b>.
 *
 * @author Josef Friedrich
 *
 * @since 0.23.0
 *
 * @see ImageFont
 */
public class ImageFontText extends Image
{
    /**
     * Die Bilderschriftart.
     */
    private final ImageFont imageFont;

    /**
     * Der Textinhalt, der in das Bild geschrieben werden soll.
     */
    private String content;

    /**
     * Die maximale Anzahl an Zeichen, die eine Zeile aufnehmen kann.
     */
    private int lineWidth;

    /**
     * Die Textausrichtung.
     */
    private TextAlignment alignment;

    /**
     * Die Farbe in der die schwarze Farbe der Ausgangsbilder umgefärbt werden
     * soll.
     */
    private Color color;

    /**
     * Wie oft ein Pixel vervielfältigt werden soll. Beispielsweise verwandelt
     * die Zahl {@code 3} ein Pixel in {@code 9} Pixel der Abmessung
     * {@code 3x3}.
     */
    private int pixelMultiplication;

    /**
     * Erzeugt einen neuen <b>Text</b>, der durch eine <b>Bilderschriftart</b>
     * dargestellt wird.
     *
     * @param imageFont Die Bilderschriftart.
     * @param content Der Textinhalt, der in das Bild geschrieben werden soll.
     * @param lineWidth Die maximale Anzahl an Zeichen, die eine Zeile aufnehmen
     *     kann.
     * @param alignment Die Textausrichtung.
     * @param color Die Farbe in der die schwarze Farbe der Ausgangsbilder
     *     umgefärbt werden soll.
     * @param pixelMultiplication Wie oft ein Pixel vervielfältigt werden soll.
     *     Beispielsweise verwandelt die Zahl {@code 3} ein Pixel in {@code 9}
     *     Pixel der Abmessung {@code 3x3}.
     * @param pixelPerMeter Wie viele Pixel ein Meter des resultierenden Bilds
     *     groß sein soll.
     */
    public ImageFontText(ImageFont imageFont, String content, int lineWidth,
            TextAlignment alignment, Color color, int pixelMultiplication,
            int pixelPerMeter)
    {
        super(imageFont.render(content, lineWidth, alignment, color,
                pixelMultiplication), pixelPerMeter);
        this.imageFont = imageFont;
        this.content = content;
        this.lineWidth = lineWidth;
        this.alignment = alignment;
        this.color = color;
        this.pixelMultiplication = pixelMultiplication;
    }

    /**
     * Erzeugt einen neuen <b>Text</b>, der durch eine <b>Bilderschriftart</b>
     * dargestellt wird.
     *
     * @param imageFont Die Bilderschriftart.
     * @param content Der Textinhalt, der in das Bild geschrieben werden soll.
     * @param lineWidth Die maximale Anzahl an Zeichen, die eine Zeile aufnehmen
     *     kann.
     * @param alignment Die Textausrichtung.
     * @param color Die Farbe, in der die schwarze Farbe der Ausgangsbilder
     *     umgefärbt werden soll.
     * @param pixelMultiplication Wie oft ein Pixel vervielfältigt werden soll.
     *     Beispielsweise verwandelt die Zahl {@code 3} ein Pixel in {@code 9}
     *     Pixel der Abmessung {@code 3x3}.
     */
    public ImageFontText(ImageFont imageFont, String content, int lineWidth,
            TextAlignment alignment, Color color, int pixelMultiplication)
    {
        this(imageFont, content, lineWidth, alignment, color,
                pixelMultiplication, imageFont.glyphWidth());
    }

    /**
     * Erzeugt einen neuen <b>Text</b>, der durch eine <b>Bilderschriftart</b>
     * dargestellt wird.
     *
     * @param imageFont Die Bilderschriftart.
     * @param content Der Textinhalt, der in das Bild geschrieben werden soll.
     * @param color Die Farbe, in der die schwarze Farbe der Ausgangsbilder
     *     umgefärbt werden soll.
     *
     */
    public ImageFontText(ImageFont imageFont, String content, Color color)
    {
        this(imageFont, content, imageFont.lineWidth(), imageFont.alignment(),
                color, imageFont.pixelMultiplication(), imageFont.glyphWidth());
    }

    /**
     * Erzeugt einen neuen <b>Text</b>, der durch eine <b>Bilderschriftart</b>
     * dargestellt wird.
     *
     * @param imageFont Die Bilderschriftart.
     * @param content Der Textinhalt, der in das Bild geschrieben werden soll.
     * @param color Die Farbe, in der die schwarze Farbe der Ausgangsbilder
     *     umgefärbt werden soll.
     *
     */
    public ImageFontText(ImageFont imageFont, String content, String color)
    {
        this(imageFont, content, Resources.colors.get(color));
    }

    /**
     * Erzeugt einen neuen <b>Text</b>, der durch eine <b>Bilderschriftart</b>
     * dargestellt wird.
     *
     * @param imageFont Die Bilderschriftart.
     * @param content Der Textinhalt, der in das Bild geschrieben werden soll.
     * @param lineWidth Die maximale Anzahl an Zeichen, die eine Zeile aufnehmen
     *     kann.
     * @param alignment Die Textausrichtung.
     */
    public ImageFontText(ImageFont imageFont, String content, int lineWidth,
            TextAlignment alignment)
    {
        this(imageFont, content, lineWidth, alignment, imageFont.color(),
                imageFont.pixelMultiplication());
    }

    /**
     * Erzeugt einen neuen <b>Text</b>, der durch eine <b>Bilderschriftart</b>
     * dargestellt wird.
     *
     * @param imageFont Die Bilderschriftart.
     * @param content Der Textinhalt, der in das Bild geschrieben werden soll.
     */
    public ImageFontText(ImageFont imageFont, String content)
    {
        this(imageFont, content, imageFont.lineWidth(), imageFont.alignment());
    }

    /**
     * Erzeugt einen neuen <b>Text</b>, der durch eine <b>Bilderschriftart</b>
     * dargestellt wird.
     *
     * @param content Der Textinhalt, der in das Bild geschrieben werden soll.
     * @param lineWidth Die maximale Anzahl an Zeichen, die eine Zeile aufnehmen
     *     kann.
     * @param alignment Die Textausrichtung.
     * @param color Die Farbe, in der die schwarze Farbe der Ausgangsbilder
     *     umgefärbt werden soll.
     * @param pixelMultiplication Wie oft ein Pixel vervielfältigt werden soll.
     *     Beispielsweise verwandelt die Zahl {@code 3} ein Pixel in {@code 9}
     *     Pixel der Abmessung {@code 3x3}.
     */
    @Setter
    public void content(String content, int lineWidth, TextAlignment alignment,
            Color color, int pixelMultiplication)
    {
        image(imageFont.render(content, lineWidth, alignment, color,
                pixelMultiplication));
        this.content = content;
        this.lineWidth = lineWidth;
        this.alignment = alignment;
        this.color = color;
        this.pixelMultiplication = pixelMultiplication;
    }

    /**
     * Setzt den <b>Textinhalt</b> neu.
     *
     * @param content Der Textinhalt, der in das Bild geschrieben werden soll.
     */
    @Setter
    public void content(String content)
    {
        content(content, lineWidth, alignment, color, pixelMultiplication());
    }

    /**
     * Setzt den <b>Textinhalt</b> zusammen mit einer <b>Farbe</b> neu.
     *
     * @param content Der Textinhalt, der in das Bild geschrieben werden soll.
     * @param color Die Farbe, in der die schwarze Farbe der Ausgangsbilder
     *     umgefärbt werden soll.
     */
    @Setter
    public void content(String content, Color color)
    {
        content(content, lineWidth, alignment, color, pixelMultiplication());
    }

    /**
     * Setzt den <b>Textinhalt</b> zusammen mit einer <b>Farbe</b> neu.
     *
     * @param content Der Textinhalt, der in das Bild geschrieben werden soll.
     * @param color Die Farbe, in der die schwarze Farbe der Ausgangsbilder
     *     umgefärbt werden soll.
     */
    @Setter
    public void content(String content, String color)
    {
        content(content, lineWidth, alignment, Resources.colors.get(color),
                pixelMultiplication());
    }

    /**
     * Gibt den Textinhalt, der in das Bild geschrieben werden soll, zurück.
     *
     * @return Der Textinhalt, der in das Bild geschrieben werden soll.
     *
     * @since 0.25.0
     */
    @Getter
    public String content()
    {
        return content;
    }

    /**
     * Gibt zurück, wie oft ein Pixel vervielfältigt werden soll.
     *
     * @return Wie oft ein Pixel vervielfältigt werden soll. Beispielsweise
     *     verwandelt die Zahl {@code 3} ein Pixel in {@code 9 Pixel} der
     *     Abmessung {@code 3x3}.
     *
     * @since 0.25.0
     */
    @Getter
    public int pixelMultiplication()
    {
        return Math.max(pixelMultiplication, 1);
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        return String.format("ImageFontText[\n  %s,\n  %s\n]", imageFont,
                super.toString());
    }
}
