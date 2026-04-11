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
import java.awt.geom.AffineTransform;

import pi.resources.font.FontUtil;

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/graphics/boxes/TextLineBoxDemo.java

/**
 * Eine einzeilige <b>Text</b>box.
 *
 * @author Josef Friedrich
 *
 * @since 0.38.0
 */
public class TextLineBox extends TextBox
{
    /**
     * Der Abstand der linken oberen Ecke des Rechtecks zur Grundlinie des
     * Textes in Pixel (positiver Wert).
     *
     * @since 0.38.0
     */
    private int baseline;

    /**
     * Der Skalierungsfaktor in x-Richtung.
     */
    private double scaleFactorX;

    /**
     * Der Skalierungsfaktor in y-Richtung.
     */
    private double scaleFactorY;

    /**
     * Erzeugt eine <b>Text</b>box.
     *
     * @param content Der <b>Inhalt</b> der Textbox als Zeichenkette.
     *
     * @since 0.39.0
     */
    public TextLineBox(Object content)
    {
        super(content);
        supportsDefinedDimension = true;
    }

    private boolean hasDefiniedDimension()
    {
        return definedWidth > 0 || definedHeight > 0;
    }

    protected void calculateDimension()
    {
        var bounds = FontUtil.getStringBounds(content, font);
        baseline = bounds.getBaseline();

        // Die Breite des Textes gesetzt in der aktuellen Schriftart in Pixel
        int fontWidth = bounds.getWidth();
        // Die Höhe des Textes gesetzt in der aktuellen Schriftart in Pixel
        int fontHeight = bounds.getHeight();

        // die genaue Breite als Double
        double preciseWidth = 0;
        // die genaue Höhe als Double
        double preciseHeight = 0;

        if (!hasDefiniedDimension())
        {
            preciseWidth = fontWidth;
            preciseHeight = fontHeight;
        }
        else if (definedWidth == 0)
        {
            preciseWidth = (double) fontWidth * definedHeight / fontHeight;
            preciseHeight = definedHeight;
        }
        else if (definedHeight == 0)
        {
            preciseWidth = definedWidth;
            preciseHeight = (double) fontHeight * definedWidth / fontWidth;
        }
        else
        {
            preciseWidth = definedWidth;
            preciseHeight = definedHeight;
        }

        width = round(preciseWidth);
        height = round(preciseHeight);

        scaleFactorX = preciseWidth / fontWidth;
        scaleFactorY = preciseHeight / fontHeight;
    }

    @Override
    void draw(Graphics2D g)
    {
        AffineTransform oldTransform = null;

        if (hasDefiniedDimension())
        {
            oldTransform = g.getTransform();
            // translate muss zuerst stehen
            g.translate(x - x * scaleFactorX, y - y * scaleFactorY);
            g.scale(scaleFactorX, scaleFactorY);
        }

        Color oldColor = null;
        Font oldFont = g.getFont();
        if (color != null)
        {
            oldColor = g.getColor();
            g.setColor(color);
        }
        g.setFont(font);
        g.drawString(content, x, y + baseline);
        g.setColor(oldColor);
        g.setFont(oldFont);

        if (oldTransform != null)
        {
            g.setTransform(oldTransform);
        }
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        return toStringFormatter().format();
    }
}
