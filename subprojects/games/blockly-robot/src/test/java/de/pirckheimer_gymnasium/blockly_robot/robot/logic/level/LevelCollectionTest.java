package de.pirckheimer_gymnasium.blockly_robot.robot.logic.level;

import static de.pirckheimer_gymnasium.blockly_robot.TestHelper.loadTask;
import static de.pirckheimer_gymnasium.blockly_robot.robot.logic.level.Difficulty.EASY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class LevelCollectionTest
{
    static LevelCollection levels;

    @BeforeAll
    static void getLevel()
    {
        levels = loadTask("conditionals_excercises/light_all_candles")
                .getLevelCollection();
    }

    @Test
    void testGetLevels()
    {
        assertEquals(levels.getLevels().size(), 3);
    }

    @Test
    void testGetNumberOfLevels()
    {
        assertEquals(levels.getNumberOfLevels(), 3);
    }

    @Test
    void testGetMaxRows()
    {
        assertEquals(levels.getMaxRows(), 6);
    }

    @Test
    void testGetMaxCols()
    {
        assertEquals(levels.getMaxCols(), 10);
    }

    @Test
    void testGetNumberOfDifficulties()
    {
        assertEquals(levels.getNumberOfDifficulties(), 3);
    }

    @Nested
    @DisplayName("Test getter getLevel()")
    class GetLevelTest
    {
        @Test
        void testAllParameters()
        {
            assertEquals(levels.getLevel(Difficulty.EASY, 0).getTestIndex(), 0);
        }

        @Test
        void testDifficultyAsEnum()
        {
            assertEquals(levels.getLevel(Difficulty.EASY).getTestIndex(), 0);
        }

        @Test
        void testDifficultyAsInt()
        {
            assertEquals(levels.getLevel(0).getTestIndex(), 0);
        }
    }

    @Nested
    class FilterTest
    {
        @Test
        void testFilterByDifficultyAndTestIndex()
        {
            var map = levels.filter(difficulty -> difficulty == EASY,
                    level -> level.getTestIndex() == 0);
            assertEquals(map.size(), 1);
            var level = map.get(EASY).get(0);
            assertEquals(level.getBorderColor(), "#b4ccc7");
        }

        @Test
        void testFilterByDifficulty()
        {
            var list = levels.filter(difficulty -> difficulty == EASY);
            assertEquals(list.size(), 1);
        }

        @Test
        void testFilter()
        {
            assertEquals(levels.filter().size(), 3);
        }

        @Test
        void testFilterByDifficultyAsString()
        {
            assertEquals(levels.filter("easy").size(), 1);
        }

        @Test
        void testFilterByDifficultyAsInteger()
        {
            assertEquals(levels.filter(0).size(), 1);
        }

        @Test
        void testFilterByDifficultyAsIntegerAndTestIndex()
        {
            assertEquals(levels.filter(0, 0).get(EASY).get(0).getBorderColor(),
                    "#b4ccc7");
        }

        @Test
        void testFilterByDifficultyAsStringAndTestIndex()
        {
            assertEquals(
                    levels.filter("easy", 0).get(EASY).get(0).getBorderColor(),
                    "#b4ccc7");
        }

        @Test
        void testFilterByDifficultyAll()
        {
            assertEquals(levels.filter("all").size(), 3);
        }

        @Test
        void testFilterByDifficultyNull()
        {
            assertEquals(levels.filter(null, 0).size(), 3);
        }

        @Test
        void testFilterByDifficultyAsEnum()
        {
            assertEquals(levels.filter(EASY).size(), 1);
        }
    }
}
