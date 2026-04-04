package blockly_robot.robot.logic.context;

import static blockly_robot.TestHelper.loadContext;
import static blockly_robot.robot.logic.level.Difficulty.EASY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ContextTest
{
    private Context context = loadContext(
        "conditionals_excercises/gems_and_obstacles");

    @Test
    public void getRows()
    {
        assertEquals(context.rows(), 7);
    }

    @Test
    public void getCols()
    {
        assertEquals(context.cols(), 7);
    }

    @Test
    public void getRobot()
    {
        assertEquals(context.robot().row(), 5);
    }

    @Test
    public void getTask()
    {
        assertEquals(context.task().title(), "Edelsteine und Hindernisse");
    }

    @Test
    public void getLevel()
    {
        assertEquals(context.level().difficulty(), EASY);
    }
}
