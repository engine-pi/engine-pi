package blockly_robot.jwinf.en.tasks.conditionals_excercises.light_all_candles;

import blockly_robot.robot.Solver;
import pi.Controller;

/**
 * https://jwinf.de/task/1156
 */
public class TaskSolver extends Solver<Robot>
{
    /**
     * <p>
     * Screenshot des Originals:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/light_all_candles/easy_screenshot-orig.png"
     * alt="">
     *
     * <p>
     * Lösung in Blockly:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/light_all_candles/easy_solution.png"
     * alt="">
     */
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

    /**
     * <p>
     * Screenshot des Originals:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/light_all_candles/medium_screenshot-orig.png"
     * alt="">
     *
     * <p>
     * Lösung in Blockly:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/light_all_candles/medium_solution.png"
     * alt="">
     */
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

    /**
     * <p>
     * Screenshot des Originals:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/light_all_candles/hard_screenshot-orig.png"
     * alt="">
     *
     * <p>
     * Lösung in Blockly:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/light_all_candles/hard_solution.png"
     * alt="">
     */
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
        Controller.instantMode(false);
        new TaskSolver().solve();
    }
}
