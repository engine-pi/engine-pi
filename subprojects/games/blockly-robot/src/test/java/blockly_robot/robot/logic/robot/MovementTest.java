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
        robot = task.getVirtualRobot();
    }

    @Test
    void rotationTurnLeft()
    {
        assertEquals(robot.turnLeft().getRotation(), -1);
    }

    @Test
    void rotationTurnRight()
    {
        assertEquals(robot.turnRight().getRotation(), 1);
    }

    @Test
    void rotationTurnAround()
    {
        assertEquals(robot.turnAround().getRotation(), 2);
    }

    @Test
    void rotationEast()
    {
        assertEquals(robot.east().getRotation(), 0);
    }

    @Test
    void rotationWest()
    {
        assertEquals(robot.west().getRotation(), 0);
    }

    @Test
    void rotationSouth()
    {
        assertEquals(robot.south().getRotation(), 0);
    }

    @Test
    void rotationNorth()
    {
        assertEquals(robot.north().getRotation(), 0);
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
