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

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/graphics/boxes/FramedTextBoxDemo.java

/**
 * Legt einen <b>Rahmen</b> um eine enthaltene Kind-Box.
 *
 * <p>
 * Die Konzeption der Klasse ist inspiriert von dem
 * <a href="https://en.wikipedia.org/wiki/CSS_box_model">CSS-Box-Model</a>.
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.40.0
 */
public class FramedBox extends CombinedChildBox
{
    public final MarginBox margin;

    public final BorderBox border;

    public final BackgroundBox background;

    public final MarginBox padding;

    public final DimensionBox dimension;

    public final AlignBox align;

    public final Box content;

    public FramedBox(Box child)
    {
        content = child;
        align = new AlignBox(child);
        dimension = new DimensionBox(align);
        padding = new MarginBox(dimension);
        background = new BackgroundBox(padding);
        border = new BorderBox(background);
        margin = new MarginBox(border);
        addChild(margin);
    }
}
