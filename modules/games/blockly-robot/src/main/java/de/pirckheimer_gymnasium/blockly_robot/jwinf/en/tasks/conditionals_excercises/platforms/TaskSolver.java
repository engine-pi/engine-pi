package de.pirckheimer_gymnasium.blockly_robot.jwinf.en.tasks.conditionals_excercises.platforms;

import de.pirckheimer_gymnasium.blockly_robot.robot.Solver;

/**
 * https://jwinf.de/task/1160
 */
public class TaskSolver extends Solver<Robot>
{
    @Override
    public void easy(Robot robot)
    {
        robot.forward();
        robot.collectFirewood();
        for (int i = 0; i < 15; i++)
        {
            robot.forward();
            if (robot.platformAbove())
            {
                robot.jump();
            }
        }
        robot.dropFirewood();
        robot.forward();
    }

    @Override
    public void medium(Robot robot)
    {
        robot.forward();
        robot.collectFirewood();
        for (int i = 0; i < 40; i++)
        {
            if (robot.platformAbove())
            {
                robot.jump();
            }
            else
            {
                if (robot.obstacleInFront())
                {
                    robot.turnAround();
                }
                robot.forward();
            }
        }
        robot.dropFirewood();
    }

    @Override
    public void hard(Robot robot)
    {
        for (int i = 0; i < 11; i++)
        {
            robot.forward();
            if (robot.onFirewood())
            {
                robot.collectFirewood();
                for (int j = 0; j < 5; j++)
                {
                    robot.jump();
                    if (robot.onHearth())
                    {
                        robot.dropFirewood();
                    }
                }
                robot.forward();
                for (int j = 0; j < 2; j++)
                {
                    robot.backwards();
                    robot.backwards();
                    robot.forward();
                    robot.forward();
                }
            }
        }
    }

    public static void main(String[] args)
    {
        new TaskSolver().solve();
    }
}
