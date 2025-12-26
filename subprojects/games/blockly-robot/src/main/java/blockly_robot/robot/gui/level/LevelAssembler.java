package blockly_robot.robot.gui.level;

import blockly_robot.robot.gui.Color;
import blockly_robot.robot.gui.map.Grid;
import blockly_robot.robot.gui.map.ItemMapPainter;
import blockly_robot.robot.gui.robot.ImageRobot;
import blockly_robot.robot.logic.level.Level;
import blockly_robot.robot.logic.robot.RobotWrapper;
import pi.Scene;
import pi.Vector;

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
        Grid grid = new Grid(level.getCols(), level.getRows());
        grid.setColor(new Color(level.getBorderColor()));
        grid.setBackground(new Color(level.getTask().getBackgroundColor()));
        return grid;
    }

    public RobotWrapper createRobot(AssembledLevel l) throws Exception
    {
        String className = "blockly_robot.jwinf.en.tasks.%s.Robot"
                .formatted(level.getTask().getTaskPath().replace("/", "."));
        RobotWrapper robot = RobotWrapper.class.getClassLoader()
                .loadClass(className).asSubclass(RobotWrapper.class)
                .getDeclaredConstructor().newInstance();
        var context = level.getContext();
        robot.actor = new ImageRobot("images/robots/robot.png",
                context.getRobot(), l);
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
        a.setGrid(createGrid());
        a.getGrid().setPosition(x - SHIFT, y - SHIFT);
        scene.add(a.getGrid());
        // ItemGrid
        new ItemMapPainter(level.getContext()).paint(scene, x - SHIFT,
                y - SHIFT);
        try
        {
            a.setRobot(createRobot(a));
            Vector robotPosition = a.translate.toVector(level.getInitItem().row,
                    level.getInitItem().col);
            ImageRobot robot = (ImageRobot) a.getRobot().actor;
            robot.setCenter(robotPosition.getX(), robotPosition.getY());
            scene.add(robot);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return a;
    }
}
