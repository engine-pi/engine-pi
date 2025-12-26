package blockly_robot.jwinf.en.tasks.conditionals_excercises.gems_and_obstacles;

import static blockly_robot.robot.logic.level.Difficulty.EASY;
import static blockly_robot.robot.logic.level.Difficulty.HARD;
import static blockly_robot.robot.logic.level.Difficulty.MEDIUM;

import org.junit.jupiter.api.Test;

import blockly_robot.jwinf.en.tasks.TaskTester;

/**
 * https://jwinf.de/task/1161
 */
public class TaskSolverTest extends TaskTester<Robot>
{
    public TaskSolverTest()
    {
        super(new TaskSolver());
    }

    @Test
    void testEasy() throws Exception
    {
        assertActions(EASY, 0, 2, 3, "forward", "withdrawAuto", "forward",
                "withdrawAuto", "forward", "withdrawAuto", "forward",
                "withdrawAuto", "forward", "withdrawAuto", "turnLeft",
                "forward", "withdrawAuto", "forward", "withdrawAuto", "forward",
                "withdrawAuto", "forward", "withdrawAuto", "turnLeft",
                "forward", "withdrawAuto", "forward", "withdrawAuto",
                "turnLeft", "forward", "withdrawAuto", "turnLeft");
    }

    @Test
    void testMedium() throws Exception
    {
        assertActions(MEDIUM, 0, 6, 4, "forward", "withdrawAuto", "forward",
                "withdrawAuto", "forward", "withdrawAuto", "forward",
                "withdrawAuto", "forward", "withdrawAuto", "forward",
                "withdrawAuto", "turnLeft", "forward", "withdrawAuto",
                "forward", "withdrawAuto", "forward", "withdrawAuto", "forward",
                "withdrawAuto", "forward", "withdrawAuto", "turnLeft",
                "turnRight", "turnRight", "forward", "withdrawAuto", "turnLeft",
                "forward", "withdrawAuto", "forward", "withdrawAuto",
                "turnLeft", "forward", "withdrawAuto", "forward",
                "withdrawAuto", "forward", "withdrawAuto", "forward",
                "withdrawAuto", "turnLeft", "forward", "withdrawAuto",
                "forward", "withdrawAuto", "forward", "withdrawAuto", "forward",
                "withdrawAuto", "forward", "withdrawAuto", "turnLeft",
                "forward", "withdrawAuto", "turnLeft", "turnRight",
                "turnRight");
    }

    @Test
    void testHard() throws Exception
    {
        assertActions(HARD, 0, 8, 3, "turnRight", "turnLeft", "forward",
                "withdrawAuto", "turnRight", "turnLeft", "forward",
                "withdrawAuto", "turnRight", "forward", "withdrawAuto",
                "turnRight", "turnLeft", "turnLeft", "forward", "withdrawAuto",
                "turnRight", "forward", "withdrawAuto", "turnRight", "turnLeft",
                "turnLeft", "forward", "withdrawAuto", "turnRight", "turnLeft",
                "forward", "withdrawAuto", "turnRight", "forward",
                "withdrawAuto", "turnRight", "turnLeft", "turnLeft", "forward",
                "withdrawAuto", "turnRight", "forward", "withdrawAuto",
                "turnRight", "turnLeft", "forward", "withdrawAuto", "turnRight",
                "forward", "withdrawAuto", "turnRight", "turnLeft", "turnLeft",
                "forward", "withdrawAuto", "turnRight", "turnLeft", "turnLeft",
                "forward", "withdrawAuto", "turnRight", "forward",
                "withdrawAuto", "turnRight", "turnLeft", "forward",
                "withdrawAuto", "turnRight", "forward", "withdrawAuto",
                "turnRight", "turnLeft", "turnLeft", "forward", "withdrawAuto",
                "turnRight", "forward", "withdrawAuto", "turnRight", "turnLeft",
                "turnLeft", "forward", "withdrawAuto", "turnRight", "forward",
                "withdrawAuto", "turnRight", "turnLeft", "forward",
                "withdrawAuto", "turnRight", "turnLeft", "forward",
                "withdrawAuto", "turnRight", "forward", "withdrawAuto",
                "turnRight", "turnLeft", "forward", "withdrawAuto", "turnRight",
                "turnLeft", "turnLeft", "turnLeft", "forward", "withdrawAuto",
                "turnRight", "turnLeft", "forward", "withdrawAuto", "turnRight",
                "forward", "withdrawAuto", "turnRight", "turnLeft", "forward",
                "withdrawAuto", "turnRight", "forward", "withdrawAuto",
                "turnRight", "turnLeft", "turnLeft", "forward", "withdrawAuto",
                "turnRight", "forward", "withdrawAuto", "turnRight", "turnLeft",
                "turnLeft", "forward", "withdrawAuto", "turnRight", "forward",
                "withdrawAuto", "turnRight", "turnLeft", "forward",
                "withdrawAuto", "turnRight", "turnLeft", "forward",
                "withdrawAuto", "turnRight", "turnLeft", "forward",
                "withdrawAuto", "turnRight", "turnLeft", "forward",
                "withdrawAuto", "turnRight", "forward", "withdrawAuto",
                "turnRight", "turnLeft", "turnLeft", "forward", "withdrawAuto");
    }
}
