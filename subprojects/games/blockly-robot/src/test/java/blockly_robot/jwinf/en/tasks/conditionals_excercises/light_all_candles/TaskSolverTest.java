package blockly_robot.jwinf.en.tasks.conditionals_excercises.light_all_candles;

import static blockly_robot.robot.logic.level.Difficulty.EASY;
import static blockly_robot.robot.logic.level.Difficulty.HARD;
import static blockly_robot.robot.logic.level.Difficulty.MEDIUM;

import org.junit.jupiter.api.Test;

import blockly_robot.jwinf.en.tasks.TaskTester;

/**
 * https://jwinf.de/task/1156
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
        assertActions(EASY, 0, 2, 4, "east", "east", "east", "north", "north",
                "north", "dropWithdrawable");
    }

    @Test
    void testMedium() throws Exception
    {
        assertActions(MEDIUM, 0, 5, 6, "east", "north", "north", "north",
                "north", "dropWithdrawable", "south", "south", "south", "south",
                "east", "north", "north", "north", "north", "dropWithdrawable",
                "south", "south", "south", "south", "east", "north", "north",
                "north", "north", "dropWithdrawable", "south", "south", "south",
                "south", "east", "north", "north", "north", "north",
                "dropWithdrawable", "south", "south", "south", "south", "east",
                "north", "north", "north", "north", "dropWithdrawable", "south",
                "south", "south", "south");
    }

    @Test
    void testHard() throws Exception
    {
        assertActions(HARD, 0, 5, 8, "east", "north", "north", "north",
                "dropWithdrawable", "south", "south", "south", "east", "east",
                "north", "north", "north", "dropWithdrawable", "south", "south",
                "south", "east", "north", "north", "north", "dropWithdrawable",
                "south", "south", "south", "east", "east", "east", "north",
                "north", "north", "dropWithdrawable", "south", "south",
                "south");
    }
}
