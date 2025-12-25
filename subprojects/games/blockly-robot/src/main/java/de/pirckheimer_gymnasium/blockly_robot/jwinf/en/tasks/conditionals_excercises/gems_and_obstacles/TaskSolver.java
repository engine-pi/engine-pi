package de.pirckheimer_gymnasium.blockly_robot.jwinf.en.tasks.conditionals_excercises.gems_and_obstacles;

import de.pirckheimer_gymnasium.blockly_robot.robot.Solver;

/**
 * https://jwinf.de/task/1161
 */
public class TaskSolver extends Solver<Robot>
{
    @Override
    public void easy(Robot robot)
    {
        for (int i = 0; i < 12; i++)
        {
            robot.forward();
            if (robot.obstacleInFront())
            {
                robot.turnLeft();
            }
        }
    }

    @Override
    public void medium(Robot robot)
    {
        for (int i = 0; i < 24; i++)
        {
            robot.forward();
            if (robot.obstacleInFront())
            {
                robot.turnLeft();
            }
            if (robot.obstacleInFront())
            {
                robot.turnRight();
                robot.turnRight();
            }
        }
    }

    @Override
    public void hard(Robot robot)
    {
        for (int i = 0; i < 40; i++)
        {
            robot.turnRight();
            for (int j = 0; j < 4; j++)
            {
                if (robot.obstacleInFront())
                {
                    robot.turnLeft();
                }
            }
            robot.forward();
        }
    }

    public static void main(String[] args)
    {
        new TaskSolver().solve();
    }
}
