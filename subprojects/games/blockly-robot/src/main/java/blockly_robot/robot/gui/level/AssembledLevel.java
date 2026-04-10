package blockly_robot.robot.gui.level;

import blockly_robot.robot.gui.map.CoordinateSystemTranslator;
import blockly_robot.robot.gui.map.GraphicalItemController;
import pi.actor.Grid;
import blockly_robot.robot.logic.item.Item;
import blockly_robot.robot.logic.level.Level;
import blockly_robot.robot.logic.robot.RobotWrapper;
import pi.Scene;
import pi.annotations.Getter;
import pi.annotations.Setter;

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
        var context = level.context();
        translate = new CoordinateSystemTranslator(context.rows(),
                context.cols(), x, y);
        for (Item item : context.bagPacker())
        {
            item.controller(itemController(item));
        }
    }

    @Getter
    public Level level()
    {
        return level;
    }

    @Getter
    public Grid grid()
    {
        return grid;
    }

    @Setter
    public void grid(Grid grid)
    {
        this.grid = grid;
    }

    @Getter
    public RobotWrapper robot()
    {
        return robot;
    }

    @Setter
    public void robot(RobotWrapper robot)
    {
        this.robot = robot;
    }

    @Getter
    public GraphicalItemController itemController(Item item)
    {
        return new GraphicalItemController(item, translate, scene);
    }
}
