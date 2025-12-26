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
    void testRotationTurnLeft()
    {
        assertEquals(robot.turnLeft().getRotation(), -1);
    }

    @Test
    void testRotationTurnRight()
    {
        assertEquals(robot.turnRight().getRotation(), 1);
    }

    @Test
    void testRotationTurnAround()
    {
        assertEquals(robot.turnAround().getRotation(), 2);
    }

    @Test
    void testRotationEast()
    {
        assertEquals(robot.east().getRotation(), 0);
    }

    @Test
    void testRotationWest()
    {
        assertEquals(robot.west().getRotation(), 0);
    }

    @Test
    void testRotationSouth()
    {
        assertEquals(robot.south().getRotation(), 0);
    }

    @Test
    void testRotationNorth()
    {
        assertEquals(robot.north().getRotation(), 0);
    }

    @Test
    void testRelocatedTrue()
    {
        assertTrue(robot.forward().isRelocated());
    }

    @Test
    void testRelocatedFalse()
    {
        robot.turnLeft();
        assertFalse(robot.forward().isRelocated());
    }

    @Test
    void testRelocatedFalseRotation()
    {
        assertFalse(robot.turnLeft().isRelocated());
    }
}
