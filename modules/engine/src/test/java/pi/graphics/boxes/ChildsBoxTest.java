package pi.graphics.boxes;

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
        assertEquals(5, new GridBox<>(EmptyBox.create(5)).numberOfChilds());
    }

    @Test
    void iteratorReturnsChildrenInOrder()
    {
        Box b1 = new EmptyBox();
        Box b2 = new EmptyBox();
        Box b3 = new EmptyBox();
        ChildsBox parent = new VerticalBox<>(b1, b2, b3);
        Iterator<Box> it = parent.iterator();

        assertSame(b1, it.next());
        assertSame(b2, it.next());
        assertSame(b3, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    void iteratorRemoveThrowsUnsupportedOperationException()
    {
        ChildsBox parent = new VerticalBox<>(new EmptyBox());
        Iterator<Box> it = parent.iterator();
        it.next();
        assertThrows(UnsupportedOperationException.class, it::remove);
    }

    @Test
    void forEach()
    {
        ChildsBox parent = new VerticalBox<>(new EmptyBox(), new EmptyBox());
        int counter = 0;
        for (Box box : parent)
        {
            assertSame(parent, box.parent);
            counter++;
        }
        assertEquals(2, counter);
    }
}
