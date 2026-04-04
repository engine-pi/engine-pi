package blockly_robot.robot.logic.robot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import blockly_robot.robot.logic.Task;

public class MovementTest
{
    Robot robot;

    @BeforeEach
    void setUp()
    {
        Task task = Task
            .loadByTaskPath("conditionals_excercises/find_the_destination");
        robot = task.virtualRobot();
    }

    @Test
    void rotationTurnLeft()
    {
        assertEquals(robot.turnLeft().rotation(), -1);
    }

    @Test
    void rotationTurnRight()
    {
        assertEquals(robot.turnRight().rotation(), 1);
    }

    @Test
    void rotationTurnAround()
    {
        assertEquals(robot.turnAround().rotation(), 2);
    }

    @Test
    void rotationEast()
    {
        assertEquals(robot.east().rotation(), 0);
    }

    @Test
    void rotationWest()
    {
        assertEquals(robot.west().rotation(), 0);
    }

    @Test
    void rotationSouth()
    {
        assertEquals(robot.south().rotation(), 0);
    }

    @Test
    void rotationNorth()
    {
        assertEquals(robot.north().rotation(), 0);
    }

    @Test
    void relocatedTrue()
    {
        assertTrue(robot.forward().isRelocated());
    }

    @Test
    void relocatedFalse()
    {
        robot.turnLeft();
        assertFalse(robot.forward().isRelocated());
    }

    @Test
    void relocatedFalseRotation()
    {
        assertFalse(robot.turnLeft().isRelocated());
    }
}
