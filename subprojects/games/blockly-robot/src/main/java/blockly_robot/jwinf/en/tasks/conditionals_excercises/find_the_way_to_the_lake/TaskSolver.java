package blockly_robot.jwinf.en.tasks.conditionals_excercises.find_the_way_to_the_lake;

import blockly_robot.robot.Solver;

/**
 * https://jwinf.de/task/1158
 */
public class TaskSolver extends Solver<Robot>
{
    @Override
    public void easy(Robot robot)
    {
        for (int i = 0; i < 17; i++)
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
        for (int i = 0; i < 17; i++)
        {
            robot.forward();
            if (robot.obstacleInFront())
            {
                robot.turnRight();
                if (robot.obstacleInFront())
                {
                    robot.turnLeft();
                    robot.turnLeft();
                }
            }
        }
    }

    @Override
    public void hard(Robot robot)
    {
        for (int i = 0; i < 19; i++)
        {
            robot.forward();
            if (robot.obstacleInFront())
            {
                robot.turnRight();
                if (robot.obstacleInFront())
                {
                    robot.turnLeft();
                    robot.turnLeft();
                }
            }
            else
            {
                robot.turnLeft();
                if (!robot.obstacleInFront())
                {
                    robot.forward();
                }
                else
                {
                    robot.turnRight();
                }
            }
        }
    }

    public static void main(String[] args)
    {
        new TaskSolver().solve();
    }
}
