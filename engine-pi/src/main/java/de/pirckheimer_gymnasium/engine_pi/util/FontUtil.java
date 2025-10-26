/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/internal/util/FontMetrics.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2018 Michael Andonie and contributors.
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
package de.pirckheimer_gymnasium.engine_pi.util;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.geom.Rectangle2D;

/**
 * Statische Hilfsmethoden um Abmessungen von Zeichenketten in bestimmen
 * Schriftarten zu bestimmen.
 */
public final class FontUtil
{
    private static final ThreadLocal<Canvas> canvas = ThreadLocal
            .withInitial(Canvas::new);

    /**
     * Bestimmt den <b>Unterlängenabstand</b> der Schriftart.
     *
     * <p>
     * Der Unterlängenabstand ist der Abstand zwischen der Grundlinie der
     * Schriftart und der Unterkante. Buchstaben wie zum Beispiel {@code g} oder
     * {@code p} ragen unter der Grundline heraus und haben deshalb einen
     * Unterlängenabstand.
     * </p>
     *
     * @param font Die <b>Schriftart</b>, von der der Unterlängenabstand
     *     bestimmt werden soll.
     *
     * @return Der <b>Unterlängenabstand</b> in Pixel.
     */
    public static int getDescent(Font font)
    {
        return canvas.get().getFontMetrics(font).getDescent();
    }

    /**
     * Bestimmt die Abmessungen einer Zeichenkette in einer bestimmten
     * Schriftart in Pixel.
     *
     * @param content Die Zeichenkette, von die Abmessungen bestimmt werden
     *     sollen.
     * @param font Die <b>Schriftart</b>, von der der Unterlängenabstand
     *     bestimmt werden soll.
     *
     * @return Das umgebende Rechteck in Pixel. Mit der Methode
     *     {@link Rectangle2D#getWidth()} kann die Breite, mit der Methode
     *     {@link Rectangle2D#getHeight()} kann die Höhe ausgegeben werden und
     *     mit {@link Rectangle2D#getY()} der Unterlängenabstand.
     */
    public static Rectangle2D getStringBounds(String content, Font font)
    {
        Canvas canvas = FontUtil.canvas.get();
        return canvas.getFontMetrics(font).getStringBounds(content,
                canvas.getGraphics());
    }

}
