package de.pirckheimer_gymnasium.blockly_robot.robot.logic.level;

import static de.pirckheimer_gymnasium.blockly_robot.robot.logic.level.Difficulty.EASY;
import static de.pirckheimer_gymnasium.blockly_robot.robot.logic.level.Difficulty.indexOf;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DifficultyTest
{
    void assertEasy(Difficulty difficulty)
    {
        assertEquals(difficulty, EASY);
    }

    @Test
    void testIndexOf()
    {
        assertEasy(indexOf(0));
        assertEasy(indexOf("easy"));
        assertEasy(indexOf("EASY"));
        assertEasy(indexOf("**"));
    }
}
