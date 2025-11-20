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
        assertEquals(2, gridBox.columns());
    }

    @Test
    void rows()
    {
        assertEquals(3, gridBox.rows());
    }

    @Test
    void getRow()
    {
        assertEquals(2, gridBox.getRow(0).size());
        assertEquals(2, gridBox.getRow(1).size());
        assertEquals(1, gridBox.getRow(2).size());
    }

    @Test
    void getColumn()
    {
        assertEquals(3, gridBox.getColumn(0).size());
        assertEquals(2, gridBox.getColumn(1).size());

        GridBox g2 = grid(empty(27)).columns(4);
        assertEquals(7, g2.getColumn(0).size());
        assertEquals(7, g2.getColumn(1).size());
        assertEquals(7, g2.getColumn(2).size());
        assertEquals(6, g2.getColumn(3).size());
    }
}
