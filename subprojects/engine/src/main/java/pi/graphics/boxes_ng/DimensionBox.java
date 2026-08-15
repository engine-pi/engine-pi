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
package pi.graphics.boxes_ng;

import java.awt.Graphics2D;

/**
 * Eine leere Box, die auf eine bestimmte <b>Abmessung</b> gesetzt werden kann.
 *
 * @since 0.40.0
 */
public class DimensionBox extends LeafBox
{
    /**
     * Erstellt eine leere Box, deren Abmessungen später explizit gesetzt werden
     * können.
     */
    public DimensionBox()
    {
        super();
        supportsDefinedDimension = true;
    }

    /**
     * Erstellt eine Box mit einer festen Breite und Höhe.
     *
     * @param width Die <b>Breite</b> der Box in Pixel.
     * @param height Die <b>Höhe</b> der Box in Pixel.
     */
    public DimensionBox(double width, double height)
    {
        this();
        definedWidth = width;
        definedHeight = height;
    }

    /**
     * Erstellt ein Array mit der angegebenen Anzahl leerer
     * {@link DimensionBox}-Instanzen.
     *
     * @param number Die Anzahl der zu erstellenden Boxen.
     *
     * @return Ein Array mit der gewünschten Anzahl neuer Boxen.
     */
    public static DimensionBox[] create(int number)
    {
        DimensionBox[] boxes = new DimensionBox[number];
        for (int i = 0; i < number; i++)
        {
            boxes[i] = new DimensionBox();
        }
        return boxes;
    }

    @Override
    protected void calculateDimension()
    {
        width = definedWidth;
        height = definedHeight;
    }

    @Override
    void draw(Graphics2D g)
    {
        // Nicht zu tun.
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
