package de.pirckheimer_gymnasium.blockly_robot.jwinf.en.tasks.conditionals_excercises.gems_and_obstacles;

import de.pirckheimer_gymnasium.blockly_robot.robot.logic.robot.RobotWrapper;

/**
 * https://jwinf.de/task/1161
 */
public class Robot extends RobotWrapper
{
    /**
     * gehe vorwärts
     */
    public void forward()
    {
        actor.forward();
    }

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
     * vor Hindernis
     */
    public boolean obstacleInFront()
    {
        return actor.obstacleInFront();
    }
}
