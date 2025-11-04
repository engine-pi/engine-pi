package de.pirckheimer_gymnasium.blockly_robot.robot.gui.map;

import de.pirckheimer_gymnasium.blockly_robot.robot.logic.Task;
import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

public class ItemMapPainterTest extends Scene
{
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
