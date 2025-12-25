package de.pirckheimer_gymnasium.blockly_robot.jwinf.en.tasks.conditionals_excercises.find_the_destination;

import de.pirckheimer_gymnasium.blockly_robot.robot.logic.robot.RobotWrapper;

/**
 * https://jwinf.de/task/1157
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
     * drehe nach rechts
     */
    public void turnRight()
    {
        actor.turnRight();
    }

    /**
     * drehe nach links
     */
    public void turnLeft()
    {
        actor.turnLeft();
    }

    /**
     * vor Hindernis
     */
    public boolean obstacleInFront()
    {
        return actor.obstacleInFront();
    }

    /**
     * Flagge erobert
     */
    public boolean reachedRedFlag()
    {
        return actor.onExit();
    }
}
