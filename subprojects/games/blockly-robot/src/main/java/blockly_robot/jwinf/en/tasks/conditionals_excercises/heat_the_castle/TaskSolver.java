package blockly_robot.jwinf.en.tasks.conditionals_excercises.heat_the_castle;

import blockly_robot.robot.Solver;
import pi.Controller;

/**
 * https://jwinf.de/task/1159
 */
public class TaskSolver extends Solver<Robot>
{
    /**
     * <p>
     * Screenshot des Originals:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/heat_the_castle/easy_screenshot-orig.png"
     * alt="">
     *
     * <p>
     * Lösung in Blockly:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/heat_the_castle/easy_solution.png"
     * alt="">
     */
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

    /**
     * <p>
     * Screenshot des Originals:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/heat_the_castle/medium_screenshot-orig.png"
     * alt="">
     *
     * <p>
     * Lösung in Blockly:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/heat_the_castle/medium_solution.png"
     * alt="">
     */
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

    /**
     * <p>
     * Screenshot des Originals:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/heat_the_castle/hard_screenshot-orig.png"
     * alt="">
     *
     * <p>
     * Lösung in Blockly:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/heat_the_castle/hard_solution.png"
     * alt="">
     */
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
        Controller.instantMode(false);
        new TaskSolver().solve();
    }
}
