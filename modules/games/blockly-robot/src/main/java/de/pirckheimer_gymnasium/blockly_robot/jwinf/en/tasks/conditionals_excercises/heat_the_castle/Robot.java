package de.pirckheimer_gymnasium.blockly_robot.jwinf.en.tasks.conditionals_excercises.heat_the_castle;

import de.pirckheimer_gymnasium.blockly_robot.robot.logic.robot.RobotWrapper;

/**
 * https://jwinf.de/task/1159
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
     * baue eine Plattform vorne
     */
    public void constructPlatformInFront()
    {
        actor.dropPlatformInFront();
    }

    /**
     * baue eine Plattform oben
     */
    public void constructPlatformAbove()
    {
        actor.dropPlatformAbove();
    }

    /**
     * Plattform vorne
     */
    public boolean platformInFront()
    {
        return actor.platformInFront();
    }

    /**
     * Plattform oben
     */
    public boolean platformAbove()
    {
        return actor.platformAbove();
    }

    /**
     * auf Holz
     */
    public boolean onFirewood()
    {
        return actor.onWithdrawable();
    }

    /**
     * beim Kamin
     */
    public boolean onHearth()
    {
        return actor.onContainer();
    }

    /**
     * drehe um
     */
    public void turnAround()
    {
        actor.turnAround();
    }

    /**
     * spring hoch
     */
    public void jump()
    {
        actor.jump();
    }
}
