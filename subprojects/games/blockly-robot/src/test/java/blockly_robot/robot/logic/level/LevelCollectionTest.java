package blockly_robot.robot.logic.level;

import static blockly_robot.TestHelper.loadTask;
import static blockly_robot.robot.logic.level.Difficulty.EASY;
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
            .levelCollection();
    }

    @Test
    void getLevels()
    {
        assertEquals(levels.levels().size(), 3);
    }

    @Test
    void getNumberOfLevels()
    {
        assertEquals(levels.numberOfLevels(), 3);
    }

    @Test
    void getMaxRows()
    {
        assertEquals(levels.maxRows(), 6);
    }

    @Test
    void getMaxCols()
    {
        assertEquals(levels.maxCols(), 10);
    }

    @Test
    void getNumberOfDifficulties()
    {
        assertEquals(levels.numberOfDifficulties(), 3);
    }

    @Nested
    @DisplayName("Test getter getLevel()")
    class GetLevelTest
    {
        @Test
        void allParameters()
        {
            assertEquals(levels.level(Difficulty.EASY, 0).testIndex(), 0);
        }

        @Test
        void difficultyAsEnum()
        {
            assertEquals(levels.level(Difficulty.EASY).testIndex(), 0);
        }

        @Test
        void difficultyAsInt()
        {
            assertEquals(levels.level(0).testIndex(), 0);
        }
    }

    @Nested
    class FilterTest
    {
        @Test
        void filterByDifficultyAndTestIndex()
        {
            var map = levels.filter(difficulty -> difficulty == EASY,
                level -> level.testIndex() == 0);
            assertEquals(map.size(), 1);
            var level = map.get(EASY).get(0);
            assertEquals(level.borderColor(), "#b4ccc7");
        }

        @Test
        void filterByDifficulty()
        {
            var list = levels.filter(difficulty -> difficulty == EASY);
            assertEquals(list.size(), 1);
        }

        @Test
        void filter()
        {
            assertEquals(levels.filter().size(), 3);
        }

        @Test
        void filterByDifficultyAsString()
        {
            assertEquals(levels.filter("easy").size(), 1);
        }

        @Test
        void filterByDifficultyAsInteger()
        {
            assertEquals(levels.filter(0).size(), 1);
        }

        @Test
        void filterByDifficultyAsIntegerAndTestIndex()
        {
            assertEquals(levels.filter(0, 0).get(EASY).get(0).borderColor(),
                "#b4ccc7");
        }

        @Test
        void filterByDifficultyAsStringAndTestIndex()
        {
            assertEquals(
                levels.filter("easy", 0).get(EASY).get(0).borderColor(),
                "#b4ccc7");
        }

        @Test
        void filterByDifficultyAll()
        {
            assertEquals(levels.filter("all").size(), 3);
        }

        @Test
        void filterByDifficultyNull()
        {
            assertEquals(levels.filter(null, 0).size(), 3);
        }

        @Test
        void filterByDifficultyAsEnum()
        {
            assertEquals(levels.filter(EASY).size(), 1);
        }
    }
}
