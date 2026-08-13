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
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class CellBoxTest
{
    @Test
    void childSizeAccessorsReturnCurrentChildSize()
    {
        CellBox cell = new CellBox(new DimensionBox(30, 40));
        cell.measure();

        assertEquals(30, cell.childWidth());
        assertEquals(40, cell.childHeight());

        CellBox empty = new CellBox();
        assertEquals(0, empty.childWidth());
        assertEquals(0, empty.childHeight());
    }

    @Test
    void containerUsesDefinedSizeWhenLargerThanChild()
    {
        CellBox cell = new CellBox(new DimensionBox(20, 10));
        cell.width(50).height(30);
        cell.measure();

        assertEquals(50, cell.width());
        assertEquals(30, cell.height());
    }

    @Test
    void containerUsesChildSizeWhenChildIsLargerThanDefinedSize()
    {
        CellBox cell = new CellBox(new DimensionBox(70, 90));
        cell.width(50).height(30);
        cell.measure();

        assertEquals(70, cell.width());
        assertEquals(90, cell.height());
    }

    @Test
    void childAlignmentUsesHorizontalAndVerticalOffsets()
    {
        CellBox cell = new CellBox(new DimensionBox(10, 20));
        cell.width(30).height(50);
        cell.x(40).y(60);
        cell.hAlign(HAlign.CENTER).vAlign(VAlign.MIDDLE);
        cell.measure();

        assertEquals(50.0, cell.child.x, 0.0);
        assertEquals(45.0, cell.child.y, 0.0);

        CellBox rightBottom = new CellBox(new DimensionBox(10, 20));
        rightBottom.width(30).height(50);
        rightBottom.x(100).y(200);
        rightBottom.hAlign(HAlign.RIGHT).vAlign(VAlign.BOTTOM);
        rightBottom.measure();

        assertEquals(120.0, rightBottom.child.x, 0.0);
        assertEquals(200.0, rightBottom.child.y, 0.0);
    }

    @Test
    void alignmentSettingsAreStoredAndApplied()
    {
        CellBox defaultCell = new CellBox(new DimensionBox(10, 10));
        defaultCell.measure();

        assertEquals(HAlign.LEFT, defaultCell.hAlign);
        assertEquals(VAlign.TOP, defaultCell.vAlign);

        CellBox customCell = new CellBox(new DimensionBox(10, 10));
        customCell.hAlign(HAlign.CENTER).vAlign(VAlign.BOTTOM);
        customCell.measure();

        assertEquals(HAlign.CENTER, customCell.hAlign);
        assertEquals(VAlign.BOTTOM, customCell.vAlign);
        assertNotSame(HAlign.CENTER, defaultCell.hAlign);
        assertSame(VAlign.BOTTOM, customCell.vAlign);
    }
}
