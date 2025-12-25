package de.pirckheimer_gymnasium.blockly_robot.robot.data.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.pirckheimer_gymnasium.blockly_robot.robot.logic.level.Difficulty;

public class LevelCollectionData
{
    public LevelData[] easy;

    public LevelData[] medium;

    public LevelData[] hard;

    public List<LevelData> list;

    public List<LevelData> getLevelList()
    {
        if (list != null)
        {
            return list;
        }
        List<LevelData> list = new ArrayList<>();
        prepareList(easy, Difficulty.EASY, list);
        prepareList(medium, Difficulty.MEDIUM, list);
        prepareList(hard, Difficulty.HARD, list);
        return list;
    }

    private void prepareList(LevelData[] levels, Difficulty difficulty,
            Collection<LevelData> collection)
    {
        for (int i = 0; i < levels.length; i++)
        {
            LevelData level = levels[i];
            level.testIndex = i;
            level.difficulty = difficulty;
            collection.add(level);
        }
    }

    public LevelData getLevel(Difficulty difficulty, int test)
    {
        LevelData[] levels;
        switch (difficulty)
        {
        default:
        case EASY:
            levels = easy;
            break;

        case MEDIUM:
            levels = medium;
            break;

        case HARD:
            levels = hard;
            break;
        }
        return levels[test];
    }

    public LevelData getLevel(Difficulty difficulty)
    {
        return getLevel(difficulty, 0);
    }

    public LevelData getLevel(int difficulty)
    {
        return getLevel(Difficulty.indexOf(difficulty), 0);
    }
}
