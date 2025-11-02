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
package de.pirckheimer_gymnasium.engine_pi.actor;

import java.awt.Color;

import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.util.TextAlignment;

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
                pixelMultiplication, imageFont.getGlyphWidth());
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
        this(imageFont, content, imageFont.getLineWidth(),
                imageFont.getAlignment(), color,
                imageFont.getPixelMultiplication(), imageFont.getGlyphWidth());
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
        this(imageFont, content, Resources.COLORS.get(color));
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
        this(imageFont, content, lineWidth, alignment, imageFont.getColor(),
                imageFont.getPixelMultiplication());
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
        this(imageFont, content, imageFont.getLineWidth(),
                imageFont.getAlignment());
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
    public void setContent(String content, int lineWidth,
            TextAlignment alignment, Color color, int pixelMultiplication)
    {
        setImage(imageFont.render(content, lineWidth, alignment, color,
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
    public void setContent(String content)
    {
        setContent(content, lineWidth, alignment, color,
                getPixelMultiplication());
    }

    /**
     * Setzt den <b>Textinhalt</b> zusammen mit einer <b>Farbe</b> neu.
     *
     * @param content Der Textinhalt, der in das Bild geschrieben werden soll.
     * @param color Die Farbe, in der die schwarze Farbe der Ausgangsbilder
     *     umgefärbt werden soll.
     */
    public void setContent(String content, Color color)
    {
        setContent(content, lineWidth, alignment, color,
                getPixelMultiplication());
    }

    /**
     * Setzt den <b>Textinhalt</b> zusammen mit einer <b>Farbe</b> neu.
     *
     * @param content Der Textinhalt, der in das Bild geschrieben werden soll.
     * @param color Die Farbe, in der die schwarze Farbe der Ausgangsbilder
     *     umgefärbt werden soll.
     */
    public void setContent(String content, String color)
    {
        setContent(content, lineWidth, alignment, Resources.COLORS.get(color),
                getPixelMultiplication());
    }

    /**
     * Gibt den Textinhalt, der in das Bild geschrieben werden soll, zurück.
     *
     * @return Der Textinhalt, der in das Bild geschrieben werden soll.
     *
     * @since 0.25.0
     */
    public String getContent()
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
    public int getPixelMultiplication()
    {
        return Math.max(pixelMultiplication, 1);
    }

    @Override
    public String toString()
    {
        return String.format("ImageFontText[\n  %s,\n  %s\n]", imageFont,
                super.toString());
    }
}
