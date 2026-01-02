package blockly_robot.robot.gui.map;

import blockly_robot.robot.logic.Task;
import pi.Game;
import pi.Scene;

public class ItemMapPainterTest extends Scene
{
    static
    {
        Game.instantMode(false);
    }

    public static void main(String[] args)
    {
        new ItemMapPainterTest();
    }

    public ItemMapPainterTest()
    {
        if (!Game.isRunning())
        {
            Game.start(this, 1000, 1000);
            Game.setDebug(true);
        }
        else
        {
            Game.transitionToScene(this);
        }
        Task task = Task
                .loadByTaskPath("conditionals_excercises/find_the_destination");
        ItemMapPainter painter = new ItemMapPainter(task.getContextData());
        painter.paint(this);
    }
}
