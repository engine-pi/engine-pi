package de.pirckheimer_gymnasium.blockly_robot.robot.logic.context;

import static de.pirckheimer_gymnasium.blockly_robot.TestHelper.loadContext;
import static de.pirckheimer_gymnasium.blockly_robot.robot.logic.level.Difficulty.EASY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ContextTest
{
    private Context context = loadContext(
            "conditionals_excercises/gems_and_obstacles");

    @Test
    public void testGetRows()
    {
        assertEquals(context.getRows(), 7);
    }

    @Test
    public void testGetCols()
    {
        assertEquals(context.getCols(), 7);
    }

    @Test
    public void testGetRobot()
    {
        assertEquals(context.getRobot().getRow(), 5);
    }

    @Test
    public void testGetTask()
    {
        assertEquals(context.getTask().getTitle(),
                "Edelsteine und Hindernisse");
    }

    @Test
    public void testGetLevel()
    {
        assertEquals(context.getLevel().getDifficulty(), EASY);
    }
}
