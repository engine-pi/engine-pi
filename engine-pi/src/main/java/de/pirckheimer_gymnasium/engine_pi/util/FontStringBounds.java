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
package de.pirckheimer_gymnasium.engine_pi.util;

import java.awt.geom.Rectangle2D;

/**
 * Die <b>Abmessungen einer Zeichenkette</b> in einer bestimmten
 * <b>Schriftart</b> in Pixel.
 *
 * <p>
 * Die Methode
 * {@link java.awt.FontMetrics#getStringBounds(String, java.awt.Graphics)}
 * liefert die Abmessungen als {@code double} in einem
 * {@link Rectangle2D}-Objekt. Diese Klasse wählt die benötigten Attribute aus
 * und rundet sie.
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.37.0
 */
public class FontStringBounds
{
    /**
     * Die <b>Breite</b> des Textes in Pixel.
     */
    public int width;

    /**
     * Die <b>Höhe</b> des Textes in Pixel.
     */
    public int height;

    /**
     * Der Abstand der linken oberen Ecke des Rechtecks zur <b>Grundlinie</b>
     * des Textes in Pixel (positiver Wert).
     */
    public int baseline;

    public FontStringBounds(Rectangle2D bounds)
    {
        width = (int) Math.round(bounds.getWidth());
        height = (int) Math.round(bounds.getHeight());
        baseline = -1 * (int) Math.round(bounds.getY());
    }

    /**
     * Gibt die <b>Breite</b> des Textes in Pixel zurück.
     *
     * @return Die <b>Breite</b> des Textes in Pixel.
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Gibt die <b>Höhe</b> des Textes in Pixel zurück.
     *
     * @return Die <b>Höhe</b> des Textes in Pixel.
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * Gibt den Abstand der linken oberen Ecke des Rechtecks zur
     * <b>Grundlinie</b> des Textes in Pixel (positiver Wert) zurück.
     *
     * @return Der Abstand der linken oberen Ecke des Rechtecks zur
     *     <b>Grundlinie</b> des Textes in Pixel (positiver Wert).
     */
    public int getBaseline()
    {
        return baseline;
    }

}
