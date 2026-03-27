package blockly_robot.robot.logic.level;

import static blockly_robot.TestHelper.loadLevel;
import static blockly_robot.robot.logic.level.Difficulty.EASY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class LevelTest
{
    static Level level;

    @BeforeAll
    static void getLevel()
    {
        level = loadLevel("conditionals_excercises/light_all_candles");
    }

    @Test
    void getTask()
    {
        assertEquals(level.getTask().getTitle(), "Kerzen anzünden");
    }

    @Test
    void getDifficulty()
    {
        assertEquals(level.getDifficulty(), EASY);
    }

    @Test
    void getTestIndex()
    {
        assertEquals(level.getTestIndex(), 0);
    }

    @Test
    void getContext()
    {
        assertEquals(level.getContext().getCols(), 9);
    }

    @Test
    void getRows()
    {
        assertEquals(level.getRows(), 6);
    }

    @Test
    void getCols()
    {
        assertEquals(level.getCols(), 9);
    }

    @Test
    void getInitItem()
    {
        assertEquals(level.getInitItem().row, 5);
    }

    @Test
    void getBorderColor()
    {
        assertEquals(level.getBorderColor(), "#b4ccc7");
    }

    @Test
    void getBackgroundColor()
    {
        assertEquals(level.getBackgroundColor(), "#c5e2dd");
    }
}
