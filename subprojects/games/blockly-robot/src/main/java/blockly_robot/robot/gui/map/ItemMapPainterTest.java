package blockly_robot.robot.gui.map;

import blockly_robot.robot.logic.Task;
import pi.Controller;
import pi.Scene;

public class ItemMapPainterTest extends Scene
{
    static
    {
        Controller.instantMode(false);
    }

    public static void main(String[] args)
    {
        new ItemMapPainterTest();
    }

    public ItemMapPainterTest()
    {
        if (!Controller.isRunning())
        {
            Controller.start(this, 1000, 1000);
            Controller.debug(true);
        }
        else
        {
            Controller.transitionToScene(this);
        }
        Task task = Task
                .loadByTaskPath("conditionals_excercises/find_the_destination");
        ItemMapPainter painter = new ItemMapPainter(task.getContextData());
        painter.paint(this);
    }
}
