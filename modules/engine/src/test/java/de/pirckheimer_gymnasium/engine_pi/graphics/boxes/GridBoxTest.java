package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Boxes.empty;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Boxes.grid;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GridBoxTest
{
    GenericGridBox<EmptyBox> gridBox = grid(empty(5));

    @Test
    void columns()
    {
        assertEquals(2, gridBox.columnCount());
    }

    @Test
    void rows()
    {
        assertEquals(3, gridBox.rowCount());
    }

    @Test
    void gridArray()
    {
        assertEquals(3, gridBox.grid.size());
        assertEquals(2, gridBox.grid.get(0).size());
    }

    @Test
    void getRow()
    {
        assertEquals(2, gridBox.getRow(0).size());
        assertEquals(2, gridBox.getRow(1).size());
        assertEquals(2, gridBox.getRow(2).size());
    }

    @Test
    void getColumn()
    {
        assertEquals(3, gridBox.getColumn(0).size());
        assertEquals(3, gridBox.getColumn(1).size());

        GenericGridBox<Box> g2 = grid(empty(27)).columns(4);
        assertEquals(7, g2.getColumn(0).size());
        assertEquals(7, g2.getColumn(1).size());
        assertEquals(7, g2.getColumn(2).size());
        assertEquals(7, g2.getColumn(3).size());
    }
}
