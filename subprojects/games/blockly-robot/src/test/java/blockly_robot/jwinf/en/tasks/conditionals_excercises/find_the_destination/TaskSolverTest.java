package blockly_robot.jwinf.en.tasks.conditionals_excercises.find_the_destination;

import static blockly_robot.robot.logic.level.Difficulty.EASY;
import static blockly_robot.robot.logic.level.Difficulty.HARD;
import static blockly_robot.robot.logic.level.Difficulty.MEDIUM;

import org.junit.jupiter.api.Test;

import blockly_robot.jwinf.en.tasks.TaskTester;

/**
 * https://jwinf.de/task/1157
 */
public class TaskSolverTest extends TaskTester<Robot>
{
    public TaskSolverTest()
    {
        super(new TaskSolver());
    }

    @Test
    void testEasy0() throws Exception
    {
        assertActions(EASY,
            0,
            1,
            4,
            "turnLeft",
            "turnRight",
            "forward",
            "turnLeft",
            "turnRight",
            "forward",
            "turnLeft",
            "turnRight",
            "forward",
            "turnLeft",
            "forward");
    }

    @Test
    void testEasy1() throws Exception
    {
        assertActions(EASY,
            1,
            1,
            6,
            "turnLeft",
            "turnRight",
            "forward",
            "turnLeft",
            "turnRight",
            "forward",
            "turnLeft",
            "turnRight",
            "forward",
            "turnLeft",
            "turnRight",
            "forward",
            "turnLeft",
            "turnRight",
            "forward",
            "turnLeft",
            "forward");
    }

    @Test
    void testMedium() throws Exception
    {
        assertActions(MEDIUM,
            0,
            3,
            4,
            "turnLeft",
            "turnRight",
            "turnRight",
            "turnLeft",
            "forward",
            "turnLeft",
            "turnRight",
            "turnRight",
            "turnLeft",
            "forward",
            "turnLeft",
            "turnRight",
            "turnRight",
            "turnLeft",
            "forward",
            "turnLeft",
            "turnRight",
            "turnRight",
            "forward");
    }

    @Test
    void testHard() throws Exception
    {
        assertActions(HARD,
            0,
            3,
            4,
            "turnRight",
            "turnLeft",
            "forward",
            "turnRight",
            "forward",
            "turnRight",
            "turnLeft",
            "turnLeft",
            "turnRight",
            "turnLeft",
            "turnLeft",
            "turnRight",
            "turnLeft",
            "forward",
            "turnRight",
            "forward",
            "turnRight",
            "turnLeft",
            "forward",
            "turnRight",
            "forward");
    }
}
