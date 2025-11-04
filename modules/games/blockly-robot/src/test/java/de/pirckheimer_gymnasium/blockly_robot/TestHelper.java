package de.pirckheimer_gymnasium.blockly_robot;

import de.pirckheimer_gymnasium.blockly_robot.robot.logic.Task;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.context.Context;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.item.ItemCreator;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.item.relocation.BagPacker;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.level.Difficulty;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.level.Level;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.robot.VirtualRobot;

public class TestHelper
{
    public static Task loadTask(String taskPath)
    {
        return Task.loadByTaskPath(taskPath);
    }

    public static Level loadLevel(String taskPath)
    {
        return loadTask(taskPath).getLevel(Difficulty.EASY);
    }

    public static Context loadContext(String taskPath)
    {
        return loadLevel(taskPath).getContext();
    }

    public static BagPacker loadBagPacker(String taskPath)
    {
        return loadContext(taskPath).getBagPacker();
    }

    public static VirtualRobot loadVirtualRobot(String taskPath)
    {
        return loadContext(taskPath).getRobot();
    }

    public static ItemCreator loadItemCreator(String taskPath)
    {
        return loadTask(taskPath).getItemCreator();
    }
}
