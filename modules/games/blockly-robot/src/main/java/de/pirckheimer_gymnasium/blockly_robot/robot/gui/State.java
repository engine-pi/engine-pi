package de.pirckheimer_gymnasium.blockly_robot.robot.gui;

import de.pirckheimer_gymnasium.blockly_robot.robot.gui.robot.ImageRobot;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.Task;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.level.Level;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.menu.Menu;
import de.pirckheimer_gymnasium.engine_pi.animation.Interpolator;
import de.pirckheimer_gymnasium.engine_pi.animation.interpolation.EaseInOutDouble;

public class State
{
    public static int pixelPerMeter = 60;

    public static Interpolator<Double> interpolator = new EaseInOutDouble(0, 1);

    public static int speed = 1;

    /**
     * Die aktuelle Trainingsaufgabe.
     */
    public static Task task;

    /**
     * Der aktuelle Schwierigkeitsgrad.
     */
    public static Level level;

    /**
     * Die aktuelle Figur.
     */
    public static ImageRobot actor;

    public static Menu menu = new Menu();
}
