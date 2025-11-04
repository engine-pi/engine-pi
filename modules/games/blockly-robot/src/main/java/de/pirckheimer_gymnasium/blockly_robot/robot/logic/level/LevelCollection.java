package de.pirckheimer_gymnasium.blockly_robot.robot.logic.level;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import de.pirckheimer_gymnasium.blockly_robot.robot.data.model.LevelCollectionData;
import de.pirckheimer_gymnasium.blockly_robot.robot.data.model.LevelData;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.Task;

/**
 * Die Tests (Level) nach Schwierigkeitsgraden geordnet.
 *
 * Represents a collection of levels categorized by difficulty.
 */
public class LevelCollection
{
    private Map<Difficulty, List<Level>> levels;

    private List<Level> list;

    private int numberOfLevels;

    private int maxRows;

    private int maxCols;

    public LevelCollection(LevelCollectionData data, Task task)
    {
        list = new ArrayList<>();
        levels = new EnumMap<>(Difficulty.class);
        for (LevelData levelData : data.getLevelList())
        {
            Difficulty difficulty = levelData.difficulty;
            List<Level> levelList = levels.get(difficulty);
            if (levelList == null)
            {
                levelList = new ArrayList<Level>();
                levels.put(difficulty, levelList);
            }
            Level level = new Level(levelData, task);
            levelList.add(level);
            list.add(level);
        }
        numberOfLevels = list.size();
        setMaxRowsAndCols();
    }

    private void setMaxRowsAndCols()
    {
        for (Level level : list)
        {
            if (level.getCols() > maxCols)
            {
                maxCols = level.getCols();
            }
            if (level.getRows() > maxRows)
            {
                maxRows = level.getRows();
            }
        }
    }

    public Map<Difficulty, List<Level>> getLevels()
    {
        return levels;
    }

    public int getNumberOfLevels()
    {
        return numberOfLevels;
    }

    public int getMaxRows()
    {
        return maxRows;
    }

    public int getMaxCols()
    {
        return maxCols;
    }

    public int getNumberOfDifficulties()
    {
        return levels.size();
    }

    /**
     * Retrieves the level for the given difficulty and test index.
     *
     * @param difficulty the difficulty of the level
     * @param testIndex the test index of the level (0 is the first test)
     *
     * @return the level corresponding to the given difficulty and test index
     */
    public Level getLevel(Difficulty difficulty, int testIndex)
    {
        return levels.get(difficulty).get(testIndex);
    }

    /**
     * Retrieves the level for the specified difficulty.
     *
     * @param difficulty the difficulty of the level to retrieve
     *
     * @return the level for the specified difficulty
     */
    public Level getLevel(Difficulty difficulty)
    {
        return getLevel(difficulty, 0);
    }

    /**
     * Retrieves the level with the specified difficulty.
     *
     * @param difficulty the difficulty level of the level to retrieve
     *
     * @return the level with the specified difficulty
     */
    public Level getLevel(int difficulty)
    {
        return getLevel(Difficulty.indexOf(difficulty), 0);
    }

    public Map<Difficulty, List<Level>> filter(Predicate<Difficulty> difficulty,
            Predicate<Level> level)
    {
        return levels.entrySet().stream()
                .filter((entry) -> difficulty.test(entry.getKey()))
                .collect(Collectors.toMap(map -> map.getKey(), map -> {
                    return map.getValue().stream().filter(l -> level.test(l))
                            .collect(Collectors.toList());
                }));
    }

    public Map<Difficulty, List<Level>> filter(Predicate<Difficulty> difficulty)
    {
        return filter(difficulty, level -> true);
    }

    public Map<Difficulty, List<Level>> filter()
    {
        return filter(difficulty -> true);
    }

    public Map<Difficulty, List<Level>> filter(Object difficulty, int testIndex)
    {
        Predicate<Difficulty> difficultyFilter = null;
        Predicate<Level> levelFilter = null;
        if (difficulty == null || difficulty.equals("all"))
        {
            difficultyFilter = d -> true;
            levelFilter = l -> true;
        }
        else
        {
            difficultyFilter = d -> d == Difficulty.indexOf(difficulty);
            levelFilter = l -> l.getTestIndex() == testIndex;
        }
        return filter(difficultyFilter, levelFilter);
    }

    public Map<Difficulty, List<Level>> filter(Object difficulty)
    {
        return filter(difficulty, 0);
    }

    public static int getMaxColsOfList(List<Level> levels)
    {
        int maxCols = 0;
        for (Level level : levels)
        {
            if (level.getCols() > maxCols)
            {
                maxCols = level.getCols();
            }
        }
        return maxCols;
    }
}
