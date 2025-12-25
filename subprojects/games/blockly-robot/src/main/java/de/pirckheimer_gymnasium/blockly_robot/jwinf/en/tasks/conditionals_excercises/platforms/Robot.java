package de.pirckheimer_gymnasium.blockly_robot.jwinf.en.tasks.conditionals_excercises.platforms;

import de.pirckheimer_gymnasium.blockly_robot.robot.logic.robot.RobotWrapper;

/**
 * https://jwinf.de/task/1160
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
     * spring hoch
     */
    public void jump()
    {
        actor.jump();
    }

    /**
     * Holz einsammeln
     */
    public void collectFirewood()
    {
        actor.withdraw();
    }

    /**
     * Holz ablegen
     */
    public void dropFirewood()
    {
        actor.drop();
    }

    /**
     * drehe um
     */
    public void turnAround()
    {
        actor.turnAround();
    }

    /**
     * gehe rückwärts
     */
    public void backwards()
    {
        actor.backwards();
    }

    /**
     * Plattform oben
     */
    public boolean platformAbove()
    {
        return actor.platformAbove();
    }

    /**
     * vor Hindernis
     */
    public boolean obstacleInFront()
    {
        return actor.obstacleInFront();
    }

    /**
     * beim Kamin
     */
    public boolean onHearth()
    {
        return actor.onContainer();
    }

    /**
     * auf Holz
     */
    public boolean onFirewood()
    {
        return actor.onWithdrawable();
    }
}
