package de.pirckheimer_gymnasium.blockly_robot.robot.logic.navigation;

import static de.pirckheimer_gymnasium.blockly_robot.robot.logic.navigation.Compass.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CompassTest
{
    @Test
    public void methodRotateByNumber()
    {
        assertEquals(EAST.rotate(0), EAST);
        assertEquals(EAST.rotate(1), SOUTH);
        assertEquals(EAST.rotate(2), WEST);
        assertEquals(EAST.rotate(3), NORTH);
        assertEquals(EAST.rotate(4), EAST);
    }
}
