package blockly_robot.robot.logic;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import blockly_robot.robot.data.JsonLoader;
import blockly_robot.robot.data.model.BagInit;
import blockly_robot.robot.data.model.GridInfosData;
import blockly_robot.robot.data.model.ItemData;
import blockly_robot.robot.data.model.TaskData;
import blockly_robot.robot.logic.context.Context;
import blockly_robot.robot.logic.item.ItemCreator;
import blockly_robot.robot.logic.level.Difficulty;
import blockly_robot.robot.logic.level.Level;
import blockly_robot.robot.logic.level.LevelCollection;
import blockly_robot.robot.logic.robot.VirtualRobot;
import pi.annotations.Getter;

/**
 * Eine Trainingsaufgabe (Task) besteht aus mehreren (in der Regel 3)
 * Schwierigkeitsgraden (Difficulty). Ein Schwierigkeitsgrad kann einen oder
 * mehrere Tests (Level) haben.
 */
public class Task
{
    /**
     * @param taskPath Der relative Pfad zu resources/data/tasks
     */
    public static Task loadByTaskPath(String taskPath)
    {
        return new Task("data/tasks/%s.json".formatted(taskPath));
    }

    public static String extractTaskPath(String path)
    {
        path = path.replace(".json", "")
            .replace(".class", "")
            .replace(".", "/")
            .replaceAll(".*/tasks/", "")
            .replaceAll("^/", "")
            .replaceAll("/$", "");
        String[] segments = path.split("/");
        return "%s/%s".formatted(segments[0], segments[1]);
    }

    String taskPath;

    private TaskData data;

    private GridInfosData contextData;

    /**
     * Zum Beispiel „Edelsteine einsammeln“
     */
    private String title;

    /**
     * Zum Beispiel „Programmiere den Roboter“
     */
    private String intro;

    private LevelCollection levels;

    private ItemCreator itemCreator;

    /**
     * Die Anzahl an Tests (Level) der Schwierigkeitsstufe mit den meisten
     * Tests.
     */
    private int maxLevelsPerDifficulty;

    private String borderColor;

    private String backgroundColor;

    private int bagSize;

    private boolean autoWithdraw;

    public Task(String filePath)
    {
        try
        {
            data = JsonLoader.loadTask(filePath);
            taskPath = extractTaskPath(filePath);
            title = data.title;
            intro = data.intro;
            if (data.gridInfos.contextType != null)
            {
                var contexts = JsonLoader.loadContexts();
                contextData = contexts.get(data.gridInfos.contextType);
            }
            itemCreator = setupItemCreator();
            levels = new LevelCollection(data.data, this);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private ItemCreator setupItemCreator()
    {
        Map<String, ItemData> fromTask = data.gridInfos.itemTypes;
        Map<String, ItemData> fromContext = null;
        if (contextData != null && contextData.itemTypes != null)
        {
            fromContext = contextData.itemTypes;
        }
        if (fromTask != null && fromContext != null)
        {
            fromTask.putAll(fromContext);
            return new ItemCreator(fromTask);
        }
        else if (data.gridInfos.itemTypes != null)
        {
            return new ItemCreator(data.gridInfos.itemTypes);
        }
        else if (contextData != null && contextData.itemTypes != null)
        {
            return new ItemCreator(contextData.itemTypes);
        }
        return null;
    }

    @Getter
    public String taskPath()
    {
        return taskPath;
    }

    @Getter
    public TaskData data()
    {
        return data;
    }

    @Getter
    public String title()
    {
        return title;
    }

    @Getter
    public String intro()
    {
        return intro;
    }

    @Getter
    public ItemCreator itemCreator()
    {
        return itemCreator;
    }

    /**
     * Returns the grid color.
     *
     * @return the grid color as a String.
     */
    @Getter
    public String borderColor()
    {
        if (borderColor != null)
        {
            return borderColor;
        }
        if (data.gridInfos.borderColor != null)
        {
            borderColor = data.gridInfos.borderColor;
        }
        else if (contextData != null && contextData.borderColor != null)
        {
            borderColor = contextData.borderColor;
        }
        else
        {
            borderColor = "#cccccc";
        }
        return borderColor;
    }

    /**
     * Returns the background color.
     *
     * @return the background color as a String.
     */
    @Getter
    public String backgroundColor()
    {
        if (backgroundColor != null)
        {
            return backgroundColor;
        }
        if (data.gridInfos.backgroundColor != null)
        {
            backgroundColor = data.gridInfos.backgroundColor;
        }
        else if (contextData != null && contextData.backgroundColor != null)
        {
            backgroundColor = contextData.backgroundColor;
        }
        else
        {
            backgroundColor = "#ffffff";
        }
        return backgroundColor;
    }

    @Getter
    public int bagSize()
    {
        if (bagSize > 0)
        {
            return bagSize;
        }
        if (data.gridInfos.bagSize > 0)
        {
            bagSize = data.gridInfos.bagSize;
        }
        else if (contextData != null && contextData.bagSize > 0)
        {
            bagSize = contextData.bagSize;
        }
        return bagSize;
    }

    @Getter
    public int maxFallAltitude()
    {
        int max = data.gridInfos.maxFallAltitude;
        if (max == 0)
        {
            return 2;
        }
        return max;
    }

    @Getter
    public int nbPlatforms()
    {
        return data.gridInfos.nbPlatforms;
    }

    /**
     * Returns a map of difficulty levels and their corresponding list of
     * levels.
     *
     * @return the map of difficulty levels and their corresponding list of
     *     levels
     */
    @Getter
    public Map<Difficulty, List<Level>> levels()
    {
        return levels.levels();
    }

    /**
     * Retrieves the level for a given difficulty and test.
     *
     * @param difficulty the difficulty of the task
     * @param test the test index (0 is the first test)
     *
     * @return the level corresponding to the given difficulty and test
     */
    @Getter
    public Level level(Difficulty difficulty, int test)
    {
        return levels.level(difficulty, test);
    }

    /**
     * Returns the level associated with the given difficulty.
     *
     * @param difficulty the difficulty of the task
     *
     * @return the level associated with the difficulty
     */
    @Getter
    public Level level(Difficulty difficulty)
    {
        return level(difficulty, 0);
    }

    /**
     * Retrieves the level based on the given difficulty.
     *
     * @param difficulty the difficulty level as an integer (0 is easy, 1 is
     *     medium, 2 is hard)
     *
     * @return the corresponding level
     */
    @Getter
    public Level level(int difficulty)
    {
        return level(Difficulty.indexOf(difficulty), 0);
    }

    /**
     * Returns the first version of a training task with the easiest difficulty.
     * /
     *
     * <i>Gibt die erste Version einer Trainingsaufgabe mit dem leichtesten
     * Schwierigkeitsgrad zurück.</i>
     */
    @Getter
    public Level level()
    {
        return level(Difficulty.EASY, 0);
    }

    @Getter
    public LevelCollection levelCollection()
    {
        return levels;
    }

    @Getter
    public Context contextData()
    {
        return level().context();
    }

    /**
     * Returns the virtual robot of the first version of a training task with
     * the easiest difficulty. /
     *
     * <i>Gibt einen virtuellen Roboter der erste Version einer Trainingsaufgabe
     * mit dem leichtesten Schwierigkeitsgrad zurück.</i>
     */
    @Getter
    public VirtualRobot virtualRobot()
    {
        return level().context().robot();
    }

    /**
     * Die Anzahl der Kacheln einer Spalte, des Tests (Level) mit der größten
     * horizontalen Ausdehnung.
     */
    @Getter
    public int maxCols()
    {
        return levels.maxCols();
    }

    /**
     * Die Anzahl der Kacheln einer Zeile, des Tests (Level) mit der größten
     * vertikalen Ausdehnung.
     */
    @Getter
    public int maxRows()
    {
        return levels.maxRows();
    }

    /**
     * Die Anzahl an Schwierigkeitsgraden (In der Regel 3).
     */
    @Getter
    public int numberOfDifficulties()
    {
        return levels.numberOfDifficulties();
    }

    /**
     * Die Anzahl der Tests des Schwierigkeitsgrads mit den meisten Tests.
     */
    @Getter
    public int maxLevelsPerDifficulty()
    {
        levels().forEach((difficulty, levels) -> {
            if (maxLevelsPerDifficulty < levels.size())
            {
                maxLevelsPerDifficulty = levels.size();
            }
        });
        return maxLevelsPerDifficulty;
    }

    /**
     * Die Anzahl aller Tests (Level).
     */
    @Getter
    public int numberOfLevels()
    {
        return levels.numberOfLevels();
    }

    public boolean hasGravity()
    {
        return data.gridInfos.hasGravity;
    }

    @Getter
    public boolean autoWithdraw()
    {
        if (autoWithdraw)
        {
            return autoWithdraw;
        }
        autoWithdraw = data.gridInfos.autoWithdraw;
        if (!autoWithdraw && contextData != null && contextData.autoWithdraw)
        {
            autoWithdraw = contextData.autoWithdraw;
        }
        return autoWithdraw;
    }

    @Getter
    public BagInit bagInit()
    {
        if (data.gridInfos.bagInit != null)
        {
            return data.gridInfos.bagInit;
        }
        if (contextData != null && contextData.bagInit != null)
        {
            return contextData.bagInit;
        }
        return null;
    }
}
