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
        assertEquals(level.task().title(), "Kerzen anzünden");
    }

    @Test
    void getDifficulty()
    {
        assertEquals(level.difficulty(), EASY);
    }

    @Test
    void getTestIndex()
    {
        assertEquals(level.testIndex(), 0);
    }

    @Test
    void getContext()
    {
        assertEquals(level.context().cols(), 9);
    }

    @Test
    void getRows()
    {
        assertEquals(level.rows(), 6);
    }

    @Test
    void getCols()
    {
        assertEquals(level.cols(), 9);
    }

    @Test
    void getInitItem()
    {
        assertEquals(level.initItem().row, 5);
    }

    @Test
    void getBorderColor()
    {
        assertEquals(level.borderColor(), "#b4ccc7");
    }

    @Test
    void getBackgroundColor()
    {
        assertEquals(level.backgroundColor(), "#c5e2dd");
    }
}
