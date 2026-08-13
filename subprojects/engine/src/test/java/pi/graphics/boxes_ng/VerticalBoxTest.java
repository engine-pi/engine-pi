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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class VerticalBoxTest
{
    @Test
    void measuresWidthAndHeightWithPadding()
    {
        VerticalBox<Box> box = new VerticalBox<>(new DimensionBox(20, 10),
                new DimensionBox(35, 5), new DimensionBox(10, 15));
        box.padding(4);

        box.measure();

        assertEquals(43, box.width());
        assertEquals(46, box.height());
        assertEquals(3, box.numberOfChilds());
    }

    @Test
    void stacksChildrenFromTopToBottomWithHorizontalOffset()
    {
        VerticalBox<Box> box = new VerticalBox<>(new DimensionBox(10, 20),
                new DimensionBox(10, 30));
        box.padding(5);
        box.x(40).y(200);
        box.measure();

        assertEquals(45, ((CellBox) box.childs.get(0)).x);
        assertEquals(160, ((CellBox) box.childs.get(0)).y);
        assertEquals(45, ((CellBox) box.childs.get(1)).x);
        assertEquals(195, ((CellBox) box.childs.get(1)).y);
    }

    @Test
    void hAlignAppliesToAllCellWrappers()
    {
        VerticalBox<Box> box = new VerticalBox<>(new DimensionBox(10, 20),
                new DimensionBox(10, 30));
        box.hAlign(HAlign.CENTER);

        assertEquals(HAlign.CENTER, ((CellBox) box.childs.get(0)).hAlign);
        assertEquals(HAlign.CENTER, ((CellBox) box.childs.get(1)).hAlign);
    }
}
