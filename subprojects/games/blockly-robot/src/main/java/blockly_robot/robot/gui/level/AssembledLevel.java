package blockly_robot.robot.gui.level;

import blockly_robot.robot.gui.map.CoordinateSystemTranslator;
import blockly_robot.robot.gui.map.GraphicalItemController;
import blockly_robot.robot.gui.map.Grid;
import blockly_robot.robot.logic.item.Item;
import blockly_robot.robot.logic.level.Level;
import blockly_robot.robot.logic.robot.RobotWrapper;
import pi.Scene;

/**
 * Die Figuren und Hintergründe, die erzeugt wurden, um eine Version einer
 * Trainingsaufgabe zeichnen zu können.
 */
public class AssembledLevel
{
    private Grid grid;

    private RobotWrapper robot;

    private Scene scene;

    private Level level;

    public CoordinateSystemTranslator translate;

    public AssembledLevel(Level level, Scene scene, double x, double y)
    {
        this.level = level;
        this.scene = scene;
        var context = level.getContext();
        translate = new CoordinateSystemTranslator(context.getRows(),
                context.getCols(), x, y);
        for (Item item : context.getBagPacker())
        {
            item.setController(getItemController(item));
        }
    }

    public Level getLevel()
    {
        return level;
    }

    public Grid getGrid()
    {
        return grid;
    }

    public void setGrid(Grid grid)
    {
        this.grid = grid;
    }

    public RobotWrapper getRobot()
    {
        return robot;
    }

    public void setRobot(RobotWrapper robot)
    {
        this.robot = robot;
    }

    public GraphicalItemController getItemController(Item item)
    {
        return new GraphicalItemController(item, translate, scene);
    }
}
