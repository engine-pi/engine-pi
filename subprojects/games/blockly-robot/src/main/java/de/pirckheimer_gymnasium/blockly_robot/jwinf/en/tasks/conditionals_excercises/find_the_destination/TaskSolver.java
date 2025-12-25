package de.pirckheimer_gymnasium.blockly_robot.jwinf.en.tasks.conditionals_excercises.find_the_destination;

import de.pirckheimer_gymnasium.blockly_robot.robot.Solver;

/**
 * https://jwinf.de/task/1157
 */
public class TaskSolver extends Solver<Robot>
{
    @Override
    public void easy(Robot robot)
    {
        while (!robot.reachedRedFlag())
        {
            robot.turnLeft();
            if (robot.obstacleInFront())
            {
                robot.turnRight();
            }
            robot.forward();
        }
    }

    @Override
    public void medium(Robot robot)
    {
        while (!robot.reachedRedFlag())
        {
            robot.turnLeft();
            if (robot.obstacleInFront())
            {
                robot.turnRight();
                robot.turnRight();
                if (robot.obstacleInFront())
                {
                    robot.turnLeft();
                }
            }
            robot.forward();
        }
    }

    @Override
    public void hard(Robot robot)
    {
        while (!robot.reachedRedFlag())
        {
            robot.turnRight();
            if (robot.obstacleInFront())
            {
                robot.turnLeft();
                if (!robot.obstacleInFront())
                {
                    robot.forward();
                }
                else
                {
                    robot.turnLeft();
                }
            }
            else
            {
                robot.forward();
            }
        }
    }

    public static void main(String[] args)
    {
        new TaskSolver().solve();
    }
}
