package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.empty;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.grid;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.vertical;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

class ChildsBoxTest
{

    @Test
    void numberOfChilds()
    {
        assertEquals(grid(empty(5)).numberOfChilds(), 5);
    }

    @Test
    void iteratorReturnsChildrenInOrder()
    {
        Box b1 = empty();
        Box b2 = empty();
        Box b3 = empty();
        ChildsBox parent = vertical(b1, b2, b3);
        Iterator<Box> it = parent.iterator();

        assertSame(b1, it.next());
        assertSame(b2, it.next());
        assertSame(b3, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    void iteratorRemoveThrowsUnsupportedOperationException()
    {
        ChildsBox parent = vertical(empty());
        Iterator<Box> it = parent.iterator();
        it.next();
        assertThrows(UnsupportedOperationException.class, it::remove);
    }

    @Test
    void forEach()
    {
        ChildsBox parent = vertical(empty(), empty());
        int counter = 0;
        for (Box box : parent)
        {
            assertSame(parent, box.parent);
            counter++;
        }
        assertEquals(2, counter);
    }
}
