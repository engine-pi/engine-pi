package blockly_robot.robot.gui.scenes;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import blockly_robot.robot.gui.Controller;
import blockly_robot.robot.gui.TextMaker;
import blockly_robot.robot.gui.level.AssembledLevel;
import blockly_robot.robot.gui.level.LevelAssembler;
import blockly_robot.robot.logic.Task;
import blockly_robot.robot.logic.level.Difficulty;
import blockly_robot.robot.logic.level.Level;
import blockly_robot.robot.logic.level.LevelCollection;
import blockly_robot.robot.logic.menu.TaskList;
import pi.Scene;
import pi.Text;
import pi.annotations.Getter;
import pi.event.KeyStrokeListener;
import pi.graphics.geom.Bounds;

public class LevelsScene extends Scene implements WindowScene, KeyStrokeListener
{
    static TaskList taskList = TaskList.readFromMenu();

    static double SHIFT = LevelAssembler.SHIFT;

    private final ArrayList<AssembledLevel> assembledLevels = new ArrayList<>();

    private Task task;

    private final double INITIAL_X = 0;

    private final double INITIAL_Y = 0;

    /**
     * Abstand zwischen den Tests.
     */
    private final double MARGIN = 1f;

    /**
     * aktuelle x-Position
     */
    private double x = 0;

    private double xMax = 0;

    /**
     * aktuelle y-Position
     */
    private double y = 0;

    private double yMin = 0;

    private Map<Difficulty, List<Level>> levels;

    public LevelsScene(Task task, Object difficulty, int testIndex)
    {
        this.task = task;
        levels = task.levelCollection().filter(difficulty, testIndex);
        paintLevels();
    }

    public LevelsScene(String taskPath, Object difficulty, int testIndex)
    {
        this(Task.loadByTaskPath(taskPath), difficulty, testIndex);
    }

    public LevelsScene(String taskPath)
    {
        this(taskPath, "all", 0);
    }

    private void setY(double y)
    {
        if (y < yMin)
        {
            yMin = y;
        }
        this.y = y;
    }

    private void setX(double x)
    {
        if (x > xMax)
        {
            xMax = x;
        }
        this.x = x;
    }

    public double getWidth()
    {
        int numDiff = task.numberOfDifficulties();
        return (task.maxCols() * numDiff) + (MARGIN * numDiff - 1);
    }

    public double getHeight()
    {
        int numLevels = task.maxLevelsPerDifficulty();
        return (task.maxRows() * numLevels) + (MARGIN * numLevels - 1);
    }

    public String title()
    {
        return task.title();
    }

    public Bounds windowBounds()
    {
        double shift = SHIFT * 2;
        return new Bounds(INITIAL_X - shift, yMin - shift, xMax,
                (yMin * -1) + shift + SHIFT);
    }

    @Getter
    public List<AssembledLevel> assembledLevels()
    {
        return assembledLevels;
    }

    private void writeVersionHeading(Difficulty difficulty)
    {
        Text text = TextMaker
            .createText("Version " + "*".repeat(difficulty.index() + 2), 1);
        text.anchor(x - SHIFT, y - SHIFT);
        setY(y - 1);
        add(text);
    }

    private void writeTestIndexHeading(List<Level> levelList, Level level)
    {
        if (levelList.size() > 1)
        {
            if (level.testIndex() > 0)
            {
                setY(y - 1);
            }
            var text = TextMaker
                .createText("Test %d".formatted(level.testIndex() + 1), 0.75f);
            text.anchor(x - SHIFT, y - SHIFT);
            setY(y - 1);
            add(text);
        }
    }

    private void paintLevels()
    {
        setX(INITIAL_X);
        levels.forEach((difficulty, levelList) -> {
            setY(INITIAL_Y);
            writeVersionHeading(difficulty);
            levelList.forEach((level) -> {
                writeTestIndexHeading(levelList, level);
                var assembler = new LevelAssembler(level);
                setY(y - (level.rows() - 1));
                assembledLevels.add(assembler.placeActorsInScene(this, x, y));
            });
            setX(x + LevelCollection.maxColsOfList(levelList) + 1);
        });
    }

    public static void launch(String taskPath, Object difficulty, int testIndex)
    {
        var scene = new LevelsScene(taskPath, difficulty, testIndex);
        Controller.launchScene((WindowScene) scene);
    }

    public static void launch(String taskPath)
    {
        launch(taskPath, null, 0);
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
        // n = next
        case KeyEvent.VK_N:
            launch(taskList.next());
            break;

        // p = previous
        case KeyEvent.VK_P:
            launch(taskList.previous());
            break;
        }
    }

    public static void main(String[] args)
    {
        // launch("conditionals_excercises/light_all_candles");
        // launch("conditionals_excercises/platforms");
        // launch("conditionals_excercises/gems_and_obstacles");
        launch("conditionals_excercises/heat_the_castle");
        // launch("conditionals_excercises/find_the_destination", "easy", 0);
    }
}
