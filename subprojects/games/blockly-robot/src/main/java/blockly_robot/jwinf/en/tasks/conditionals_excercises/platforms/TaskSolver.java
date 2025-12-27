package blockly_robot.jwinf.en.tasks.conditionals_excercises.platforms;

import blockly_robot.robot.Solver;

/**
 * https://jwinf.de/task/1160
 */
public class TaskSolver extends Solver<Robot>
{
    /**
     * <p>
     * Screenshot des Originals:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/platforms/easy_screenshot-orig.png"
     * alt="">
     *
     * <p>
     * Lösung in Blockly:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/platforms/easy_solution.png"
     * alt="">
     */
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

    /**
     * <p>
     * Screenshot des Originals:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/platforms/medium_screenshot-orig.png"
     * alt="">
     *
     * <p>
     * Lösung in Blockly:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/platforms/medium_solution.png"
     * alt="">
     */
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

    /**
     * <p>
     * Screenshot des Originals:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/platforms/hard_screenshot-orig.png"
     * alt="">
     *
     * <p>
     * Lösung in Blockly:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/platforms/hard_solution.png"
     * alt="">
     */
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
