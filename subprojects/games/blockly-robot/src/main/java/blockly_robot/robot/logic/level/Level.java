package blockly_robot.robot.logic.level;

import blockly_robot.robot.data.model.ItemData;
import blockly_robot.robot.data.model.LevelData;
import blockly_robot.robot.logic.Task;
import blockly_robot.robot.logic.context.Context;
import blockly_robot.robot.logic.robot.VirtualRobot;
import pi.annotations.Getter;

/**
 * Ein Test bzw. eine Version einer Trainingsaufgabe in einer bestimmen
 * Schwierigkeit.
 *
 * Eine Trainingsaufgabe kann mehrere Versionen unterschiedlicher
 * Schwierigkeitsgrade haben, z.B. eine Zweistern- (<code>Version**</code>,
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
        robot.initPosition(initItem());
        return robot;
    }

    private Context createContext(Task task)
    {
        VirtualRobot robot = createRobot();
        Context context = new Context(data.tiles, task.itemCreator(), robot,
                task, this);
        robot.context(context);
        return context;
    }

    @Getter
    public Task task()
    {
        return task;
    }

    @Getter
    public Difficulty difficulty()
    {
        return difficulty;
    }

    @Getter
    public int testIndex()
    {
        return testIndex;
    }

    @Getter
    public Context context()
    {
        return context;
    }

    @Getter
    public int rows()
    {
        return context.rows();
    }

    @Getter
    public int cols()
    {
        return context.cols();
    }

    @Getter
    public ItemData initItem()
    {
        return data.initItem();
    }

    @Getter
    public String borderColor()
    {
        return task.borderColor();
    }

    @Getter
    public String backgroundColor()
    {
        return task.backgroundColor();
    }
}
