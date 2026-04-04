package blockly_robot.robot.logic;

import static blockly_robot.robot.logic.level.Difficulty.EASY;
import static blockly_robot.robot.logic.level.Difficulty.HARD;
import static blockly_robot.robot.logic.level.Difficulty.MEDIUM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import blockly_robot.robot.logic.menu.TaskList;

class TaskTest
{
    Task task = Task
        .loadByTaskPath("conditionals_excercises/light_all_candles");

    @Test
    void loadByRelPath()
    {
        // Assuming that the relative path
        // "conditionals_excercises/light_all_candles" is valid
        final Task loadedTask = Task
            .loadByTaskPath("conditionals_excercises/light_all_candles");
        assertNotNull(loadedTask);
        assertEquals(loadedTask.title(), "Kerzen anzünden");
    }

    @Nested
    @DisplayName("static Method extractTaskPath")
    class extractTaskPath
    {
        private void assertPath(String path, String expected)
        {
            assertEquals(Task.extractTaskPath(path), expected);
        }

        @Test
        void jsonFile()
        {
            assertPath(
                "/home/xxx/repos/github/jwinf-java/src/main/resources/data/tasks/conditionals_excercises/find_the_way_to_the_lake.json",
                "conditionals_excercises/find_the_way_to_the_lake");
        }

        @Test
        void classFile()
        {
            assertPath(
                "blockly_robot.jwinf.en.tasks.conditionals_excercises.find_the_way_to_the_lake.TaskSolver.class",
                "conditionals_excercises/find_the_way_to_the_lake");
        }
    }

    @Test
    void title()
    {
        assertEquals(task.title(), "Kerzen anzünden");
    }

    @Test
    void intro()
    {
        assertEquals(task.intro(),
            "Programmiere den Roboter:\n"
                    + "Der Roboter soll alle Kerzen anzünden.");
    }

    @Test
    void getNumberOfLevels()
    {
        assertEquals(task.numberOfLevels(), 3);
    }

    @Test
    void getTaskPath()
    {
        assertEquals(task.taskPath(),
            "conditionals_excercises/light_all_candles");
    }

    @Test
    void getMaxCols()
    {
        assertEquals(task.maxCols(), 10);
    }

    @Test
    void getMaxRows()
    {
        assertEquals(task.maxRows(), 6);
    }

    @Test
    void all() throws IOException
    {
        final TaskList list = TaskList.readFromResources();
        for (final String id : list.getRelPaths())
        {
            final Task task = Task.loadByTaskPath(id);
            assertTrue(task.title() != null);
        }
    }

    @Test
    void getBackgroundColor()
    {
        assertEquals(task.backgroundColor(), "#c5e2dd");
    }

    @Test
    void getBorderColor()
    {
        assertEquals(task.borderColor(), "#b4ccc7");
    }

    @Test
    void getLevelIntDifficulty()
    {
        assertEquals(task.level(0).difficulty(), EASY);
    }

    @Test
    void getLevelEnumDifficulty()
    {
        assertEquals(task.level(MEDIUM).difficulty(), MEDIUM);
    }

    @Test
    void getLevelDifficultyAndTestIndex()
    {
        assertEquals(task.level(HARD, 0).difficulty(), HARD);
    }

    @Test
    void getMaxLevelsPerDifficulty()
    {
        // Assuming that the maximum levels per difficulty is 3
        assertEquals(task.maxLevelsPerDifficulty(), 1);
    }

    @Test
    void getNumberOfDifficulties()
    {
        // Assuming that the number of difficulties is 3 (EASY, MEDIUM, HARD)
        assertEquals(task.numberOfDifficulties(), 3);
    }

    @Nested
    class TaskFromContextTest
    {
        Task task = Task.loadByTaskPath("loops_excercises/collecting_gems");

        @Test
        void getBackgroundColor()
        {
            assertEquals(task.backgroundColor(), "#BF5E47");
        }

        @Test
        void getBorderColor()
        {
            assertEquals(task.borderColor(), "#96413B");
        }

        @Test
        void getItemCreator()
        {
            assertEquals(task.itemCreator().create("gem").getType(), "gem");
        }

        @Test
        void getBagSize()
        {
            assertEquals(task.bagSize(), 100);
        }
    }
}
