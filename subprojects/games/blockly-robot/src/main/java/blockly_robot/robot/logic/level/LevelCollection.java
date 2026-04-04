package blockly_robot.robot.logic.level;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import blockly_robot.robot.data.model.LevelCollectionData;
import blockly_robot.robot.data.model.LevelData;
import blockly_robot.robot.logic.Task;
import pi.annotations.Getter;

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
            if (level.cols() > maxCols)
            {
                maxCols = level.cols();
            }
            if (level.rows() > maxRows)
            {
                maxRows = level.rows();
            }
        }
    }

    @Getter
    public Map<Difficulty, List<Level>> levels()
    {
        return levels;
    }

    @Getter
    public int numberOfLevels()
    {
        return numberOfLevels;
    }

    @Getter
    public int maxRows()
    {
        return maxRows;
    }

    @Getter
    public int maxCols()
    {
        return maxCols;
    }

    @Getter
    public int numberOfDifficulties()
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
    @Getter
    public Level level(Difficulty difficulty, int testIndex)
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
    @Getter
    public Level level(Difficulty difficulty)
    {
        return level(difficulty, 0);
    }

    /**
     * Retrieves the level with the specified difficulty.
     *
     * @param difficulty the difficulty level of the level to retrieve
     *
     * @return the level with the specified difficulty
     */
    @Getter
    public Level level(int difficulty)
    {
        return level(Difficulty.indexOf(difficulty), 0);
    }

    public Map<Difficulty, List<Level>> filter(Predicate<Difficulty> difficulty,
            Predicate<Level> level)
    {
        return levels.entrySet()
            .stream()
            .filter((entry) -> difficulty.test(entry.getKey()))
            .collect(Collectors.toMap(map -> map.getKey(), map -> {
                return map.getValue()
                    .stream()
                    .filter(l -> level.test(l))
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
            levelFilter = l -> l.testIndex() == testIndex;
        }
        return filter(difficultyFilter, levelFilter);
    }

    public Map<Difficulty, List<Level>> filter(Object difficulty)
    {
        return filter(difficulty, 0);
    }

    public static int maxColsOfList(List<Level> levels)
    {
        int maxCols = 0;
        for (Level level : levels)
        {
            if (level.cols() > maxCols)
            {
                maxCols = level.cols();
            }
        }
        return maxCols;
    }
}
