package blockly_robot.robot.logic.robot;

import static blockly_robot.TestHelper.loadVirtualRobot;
import static blockly_robot.robot.logic.navigation.Compass.EAST;
import static blockly_robot.robot.logic.navigation.Compass.WEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import blockly_robot.robot.logic.log.Movement;
import blockly_robot.robot.logic.navigation.Compass;

/**
 * https://jwinf.de/task/1158
 */
public class VirtualRobotTest
{
    VirtualRobot robot;

    @BeforeEach
    public void setUp()
    {
        robot = loadVirtualRobot(
            "conditionals_excercises/find_the_way_to_the_lake");
    }

    private void assertMovement(Movement movement, int row, int col,
            Compass dir, boolean successful)
    {
        assertEquals(movement.to().row(), row);
        assertEquals(movement.to().col(), col);
        assertEquals(movement.to().dir(), dir);
        assertEquals(movement.isRelocated(), successful);
    }

    @Test
    public void row()
    {
        assertEquals(robot.row(), 8);
    }

    @Test
    public void col()
    {
        assertEquals(robot.col(), 1);
    }

    @Test
    public void dir()
    {
        assertEquals(robot.dir, EAST);
    }

    @Test
    public void east()
    {
        assertMovement(robot.east(), 8, 2, EAST, true);
        assertEquals(robot.row(), 8);
        assertEquals(robot.col(), 2);
    }

    @Test
    public void south()
    {
        assertMovement(robot.south(), 8, 1, EAST, false);
    }

    @Test
    public void west()
    {
        assertMovement(robot.west(), 8, 0, WEST, true);
        assertEquals(robot.row(), 8);
        assertEquals(robot.col(), 0);
    }

    @Test
    public void north()
    {
        assertMovement(robot.north(), 8, 1, EAST, false);
    }

    @Test
    public void cantMoveInFrontOfObstacles()
    {
        robot.east();
        assertTrue(robot.movementSuccessful);
        robot.north();
        assertFalse(robot.movementSuccessful);
    }

    @Test
    @DisplayName("Can't move if on the edge")
    public void cantMoveOnTheEdge()
    {
        robot.west();
        assertTrue(robot.movementSuccessful);
        robot.west();
        assertFalse(robot.movementSuccessful);
    }

    @Test
    @DisplayName("Can't move if obstacle in front")
    public void obstacleInFront()
    {
        assertFalse(robot.obstacleInFront());
        robot.turnLeft();
        assertEquals(robot.col(), 1);
        assertEquals(robot.row(), 8);
        assertTrue(robot.obstacleInFront());
        robot.turnLeft();
        assertFalse(robot.obstacleInFront());
        robot.turnLeft();
        assertTrue(robot.obstacleInFront());
        assertEquals(robot.col(), 1);
        assertEquals(robot.row(), 8);
    }
}
