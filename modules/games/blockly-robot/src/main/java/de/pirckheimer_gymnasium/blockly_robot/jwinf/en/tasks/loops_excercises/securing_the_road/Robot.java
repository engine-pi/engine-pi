package de.pirckheimer_gymnasium.blockly_robot.jwinf.en.tasks.loops_excercises.securing_the_road;

import de.pirckheimer_gymnasium.blockly_robot.robot.logic.robot.RobotWrapper;

/**
 * https://jwinf.de/task/1140
 */
public class Robot extends RobotWrapper
{
    /**
     * drehe nach links
     */
    public void turnLeft()
    {
        actor.turnLeft();
    }

    /**
     * drehe nach rechts
     */
    public void turnRight()
    {
        actor.turnRight();
    }

    /**
     * gehe vorwärts
     */
    public void forward()
    {
        actor.forward();
    }

    /**
     * platziere Leitkegel
     */
    public void dropCone()
    {
        actor.drop();
    }
}
