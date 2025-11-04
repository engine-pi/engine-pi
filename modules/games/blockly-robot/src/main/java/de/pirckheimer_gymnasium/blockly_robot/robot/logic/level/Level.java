package de.pirckheimer_gymnasium.blockly_robot.robot.logic.level;

import de.pirckheimer_gymnasium.blockly_robot.robot.data.model.ItemData;
import de.pirckheimer_gymnasium.blockly_robot.robot.data.model.LevelData;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.Task;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.context.Context;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.robot.VirtualRobot;

/**
 * Ein Test bzw. eine Version einer Trainingsaufgabe in einer bestimmen
 * Schwierigkeit.
 *
 * Eine Trainingsaufgabe kann mehrere Versionen unterschiedlicher
 * Schwierigkeitsgrade haben, z. B. eine Zweistern- (<code>Version**</code>,
 * <em>easy</em>), Dreistern-(<code>Version***</code>, <em>medium</em>), und
 * eine Vierstern-Version (<code>Version****</code>, <em>hard</em>).
 */
public class Level
{
    private LevelData data;

    private Task task;

    private Difficulty difficulty;

    private int testIndex;

    private Context context;

    public Level(LevelData data, Task task)
    {
        this.data = data;
        this.task = task;
        context = createContext(task);
        difficulty = data.difficulty;
        testIndex = data.testIndex;
    }

    public VirtualRobot createRobot()
    {
        var robot = new VirtualRobot(this);
        robot.addDefaultMovementListener();
        robot.setInitPosition(getInitItem());
        return robot;
    }

    private Context createContext(Task task)
    {
        VirtualRobot robot = createRobot();
        Context context = new Context(data.tiles, task.getItemCreator(), robot,
                task, this);
        robot.setContext(context);
        return context;
    }

    public Task getTask()
    {
        return task;
    }

    public Difficulty getDifficulty()
    {
        return difficulty;
    }

    public int getTestIndex()
    {
        return testIndex;
    }

    public Context getContext()
    {
        return context;
    }

    public int getRows()
    {
        return context.getRows();
    }

    public int getCols()
    {
        return context.getCols();
    }

    public ItemData getInitItem()
    {
        return data.getInitItem();
    }

    public String getBorderColor()
    {
        return task.getBorderColor();
    }

    public String getBackgroundColor()
    {
        return task.getBackgroundColor();
    }
}
