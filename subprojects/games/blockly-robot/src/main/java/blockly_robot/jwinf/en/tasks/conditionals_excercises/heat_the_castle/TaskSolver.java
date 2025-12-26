package blockly_robot.jwinf.en.tasks.conditionals_excercises.heat_the_castle;

import blockly_robot.robot.Solver;

/**
 * https://jwinf.de/task/1159
 */
public class TaskSolver extends Solver<Robot>
{
    @Override
    public void easy(Robot robot)
    {
        if (!robot.platformInFront())
        {
            robot.constructPlatformInFront();
        }
        robot.forward();
        robot.forward();
        robot.collectFirewood();
        robot.forward();
        robot.dropFirewood();
    }

    @Override
    public void medium(Robot robot)
    {
        for (int i = 0; i < 11; i++)
        {
            if (!robot.platformInFront())
            {
                robot.constructPlatformInFront();
            }
            robot.forward();
            if (robot.onFirewood())
            {
                robot.collectFirewood();
            }
            if (robot.onHearth())
            {
                robot.dropFirewood();
            }
        }
    }

    @Override
    public void hard(Robot robot)
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (!robot.platformInFront())
                {
                    robot.constructPlatformInFront();
                }
                robot.forward();
                if (robot.onFirewood())
                {
                    robot.collectFirewood();
                }
            }
            robot.turnAround();
            for (int j = 0; j < 3; j++)
            {
                robot.forward();
                if (robot.onHearth())
                {
                    robot.dropFirewood();
                }
            }
            robot.turnAround();
            for (int j = 0; j < 3; j++)
            {
                robot.forward();
            }
            if (!robot.platformAbove())
            {
                robot.constructPlatformAbove();
            }
            robot.jump();
        }
    }

    public static void main(String[] args)
    {
        new TaskSolver().solve();
    }
}
