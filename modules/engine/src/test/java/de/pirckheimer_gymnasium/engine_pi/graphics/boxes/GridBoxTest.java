package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.empty;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.grid;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GridBoxTest
{
    @Test
    void numberOfRows()
    {
        assertEquals(grid(empty(5)).numberOfRows(), 3);
    }

}
