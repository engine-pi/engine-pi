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

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/graphics/boxes/VerticalBoxDemo.java

/**
 * @since 0.41.0
 */
public class AlignableVerticalBox extends VerticalBox
{

    AlignBoxOrder alignBoxes;

    /**
     * @since 0.41.0
     */
    public AlignableVerticalBox(Box... childs)
    {
        super();
        alignBoxes = new AlignBoxOrder();
        for (Box box : childs)
        {
            addChild(alignBoxes.addInner(box));
        }
    }

    public AlignableVerticalBox hAlign(HAlign hAlign)
    {
        alignBoxes.hAlign(hAlign);
        return this;
    }

    public AlignableVerticalBox vAlign(VAlign vAlign)
    {
        alignBoxes.vAlign(vAlign);
        return this;
    }

    @Override
    protected void calculateDimension()
    {
        super.calculateDimension();
        alignBoxes.maxChildWidth();
    }
}
