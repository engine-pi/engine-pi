package blockly_robot.jwinf.en.tasks.conditionals_excercises.find_the_way_to_the_lake;

import blockly_robot.robot.logic.robot.RobotWrapper;

/**
 * https://jwinf.de/task/1158
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
     * vor Hindernis
     */
    public boolean obstacleInFront()
    {
        return actor.obstacleInFront();
    }
}
