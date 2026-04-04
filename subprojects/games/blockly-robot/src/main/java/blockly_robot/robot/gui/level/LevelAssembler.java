package blockly_robot.robot.gui.level;

import blockly_robot.robot.gui.Color;
import blockly_robot.robot.gui.map.Grid;
import blockly_robot.robot.gui.map.ItemMapPainter;
import blockly_robot.robot.gui.robot.ImageRobot;
import blockly_robot.robot.logic.level.Level;
import blockly_robot.robot.logic.robot.RobotWrapper;
import pi.Scene;
import pi.graphics.geom.Vector;

/**
 * Klasse, die eine Version einer Trainingsaufgabe zusammenbaut.
 */
public class LevelAssembler
{
    public static final double SHIFT = 0.5f;

    Level level;

    public LevelAssembler(Level level)
    {
        this.level = level;
    }

    public Grid createGrid()
    {
        Grid grid = new Grid(level.cols(), level.rows());
        grid.color(new Color(level.borderColor()));
        grid.background(new Color(level.task().backgroundColor()));
        return grid;
    }

    public RobotWrapper createRobot(AssembledLevel l) throws Exception
    {
        String className = "blockly_robot.jwinf.en.tasks.%s.Robot"
            .formatted(level.task().taskPath().replace("/", "."));
        RobotWrapper robot = RobotWrapper.class.getClassLoader()
            .loadClass(className)
            .asSubclass(RobotWrapper.class)
            .getDeclaredConstructor()
            .newInstance();
        var context = level.context();
        robot.actor = new ImageRobot("images/robots/robot.png", context.robot(),
                l);
        return robot;
    }

    /**
     * @param x - x-Koordinate der linken unteren Ecke
     * @param y - y-Koordinate der linken unteren Ecke
     */
    public AssembledLevel placeActorsInScene(Scene scene, double x, double y)
    {
        AssembledLevel a = new AssembledLevel(level, scene, x, y);
        // Grid
        a.grid(createGrid());
        a.grid().anchor(x - SHIFT, y - SHIFT);
        scene.add(a.grid());
        // ItemGrid
        new ItemMapPainter(level.context()).paint(scene, x - SHIFT, y - SHIFT);
        try
        {
            a.robot(createRobot(a));
            Vector robotPosition = a.translate.toVector(level.initItem().row,
                level.initItem().col);
            ImageRobot robot = (ImageRobot) a.robot().actor;
            robot.center(robotPosition.x(), robotPosition.y());
            scene.add(robot);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return a;
    }
}
