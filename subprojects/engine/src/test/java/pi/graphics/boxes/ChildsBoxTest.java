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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

/**
 * @author Josef Friedrich
 */
class ChildsBoxTest
{

    @Test
    void numberOfChilds()
    {
        assertEquals(5, new GridBox<>(DimensionBox.create(5)).numberOfChilds());
    }

    @Test
    void iteratorReturnsChildrenInOrder()
    {
        Box b1 = new DimensionBox();
        Box b2 = new DimensionBox();
        Box b3 = new DimensionBox();
        ChildsBox<Box> parent = new VerticalBox<>(b1, b2, b3);
        Iterator<Box> it = parent.iterator();

        assertSame(b1, it.next().childs.get(0));
        assertSame(b2, it.next().childs.get(0));
        assertSame(b3, it.next().childs.get(0));
        assertFalse(it.hasNext());
    }

    @Test
    void iteratorRemoveThrowsUnsupportedOperationException()
    {
        ChildsBox<Box> parent = new VerticalBox<>(new DimensionBox());
        Iterator<Box> it = parent.iterator();
        it.next();
        assertThrows(UnsupportedOperationException.class, it::remove);
    }

    @Test
    void forEach()
    {
        ChildsBox<Box> parent = new VerticalBox<>(new DimensionBox(),
                new DimensionBox());
        int counter = 0;
        for (Box box : parent)
        {
            assertSame(parent, box.parent);
            counter++;
        }
        assertEquals(2, counter);
    }
}
