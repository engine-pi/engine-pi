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
        assertEquals(gridBox.columns(), 2);
    }

    @Test
    void rows()
    {
        assertEquals(gridBox.rows(), 3);
    }

    @Test
    void getRow()
    {
        assertEquals(gridBox.getRow(0).size(), 2);
        assertEquals(gridBox.getRow(1).size(), 2);
        assertEquals(gridBox.getRow(2).size(), 1);
    }

    @Test
    void getColumn()
    {
        assertEquals(gridBox.getColumn(0).size(), 3);
        assertEquals(gridBox.getColumn(1).size(), 2);

        GridBox g2 = grid(empty(27)).columns(4);
        assertEquals(g2.getColumn(0).size(), 7);
        assertEquals(g2.getColumn(1).size(), 7);
        assertEquals(g2.getColumn(2).size(), 7);
        assertEquals(g2.getColumn(3).size(), 6);
    }
}
