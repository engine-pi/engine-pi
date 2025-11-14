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
package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

/**
 * Ein <b>Außenabstand</b> um die enthaltene Kind-Box.
 *
 * @author Josef Friedrich
 *
 * @since 0.40.0
 */
public class MarginBox extends SingleChildBoxContainer
{
    /**
     * Der <b>Außenabstand</b> in Pixel.
     *
     * @since 0.40.0
     */
    int margin = 0;

    /**
     * Erzeugt einen neuen <b>Außenabstand</b> durch die Angabe der enthaltenen
     * Kind-Box. Rahmen
     *
     * @param child Die <b>Kind-Box</b>, die umrahmt werden soll.
     *
     * @since 0.40.0
     */
    public MarginBox(Box child)
    {
        super(child);
    }

    /* Setter */

    /**
     * Setzt den <b>Außenabstand</b> in Pixel.
     *
     * @param margin Der <b>Außenabstand</b> in Pixel.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.40.0
     */
    public MarginBox margin(int margin)
    {
        this.margin = margin;
        return this;
    }

    @Override
    protected void calculateDimension()
    {
        child.calculateDimension();
        width = child.width + 2 * margin;
        height = child.height + 2 * margin;
    }

    @Override
    protected void calculateAnchors()
    {
        child.x = x + margin;
        child.y = y + margin;
    }
}
