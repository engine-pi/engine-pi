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
 * Eine Box, die aus mehreren primitiven Kind-Boxen <b>kombiniert</b> ist.
 *
 * <pre>
 * {@code
 * public class FramedBox extends CombinedChildBox
 * {
 *     // parent -> child
 *     public final DimensionBox dimension;
 *
 *     public final AlignBox align;
 *
 *     public final Box content;
 *
 *     public FramedBox(Box child)
 *     {
 *         // child -> parent
 *         content = child;
 *         align = new AlignBox(child);
 *         dimension = new DimensionBox(align);
 *         addChild(dimension);
 *     }
 * }
 * }
 * </pre>
 *
 * <p>
 * Es handelt sich also um eine eine virtuelle, transparente Box, die keine
 * Zeichnenroutinen ausführt.
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.40.0
 */
public abstract class CombinedChildBox extends ChildBox
{
    @Override
    protected void calculateDimension()
    {
        child.calculateDimension();
        width = child.width;
        height = child.height;
    }

    @Override
    protected void calculateAnchors()
    {
        child.x = x;
        child.y = y;
        child.calculateAnchors();
    }
}
