package blockly_robot.jwinf.en.tasks.conditionals_excercises.light_all_candles;

import blockly_robot.robot.Solver;

/**
 * https://jwinf.de/task/1156
 */
public class TaskSolver extends Solver<Robot>
{
    @Override
    public void easy(Robot robot)
    {
        for (int i = 0; i < 3; i++)
        {
            robot.right();
        }
        for (int i = 0; i < 3; i++)
        {
            robot.up();
        }
        robot.lightCandle();
    }

    @Override
    public void medium(Robot robot)
    {
        for (int i = 0; i < 5; i++)
        {
            robot.right();
            for (int j = 0; j < 4; j++)
            {
                robot.up();
            }
            robot.lightCandle();
            for (int j = 0; j < 4; j++)
            {
                robot.down();
            }
        }
    }

    @Override
    public void hard(Robot robot)
    {
        for (int i = 0; i < 7; i++)
        {
            robot.right();
            if (robot.onCandle())
            {
                for (int j = 0; j < 3; j++)
                {
                    robot.up();
                }
                robot.lightCandle();
                for (int j = 0; j < 3; j++)
                {
                    robot.down();
                }
            }
        }
    }

    public static void main(String[] args)
    {
        new TaskSolver().solve();
    }
}
