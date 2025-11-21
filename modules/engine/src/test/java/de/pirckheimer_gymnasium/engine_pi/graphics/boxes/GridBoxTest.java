package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.empty;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.grid;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GridBoxTest
{
    GridBox gridBox = grid(empty(5));

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
        assertEquals(3, gridBox.grid.length);
        assertEquals(2, gridBox.grid[0].length);
    }

    @Test
    void getRow()
    {
        assertEquals(2, gridBox.getRow(0).length);
        assertEquals(2, gridBox.getRow(1).length);
        assertEquals(2, gridBox.getRow(2).length);
    }

    @Test
    void getColumn()
    {
        assertEquals(3, gridBox.getColumn(0).length);
        assertEquals(3, gridBox.getColumn(1).length);

        GridBox g2 = grid(empty(27)).columns(4);
        assertEquals(7, g2.getColumn(0).length);
        assertEquals(7, g2.getColumn(1).length);
        assertEquals(7, g2.getColumn(2).length);
        assertEquals(7, g2.getColumn(3).length);
    }
}
