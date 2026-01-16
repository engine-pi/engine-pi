package blockly_robot.jwinf.en.tasks.conditionals_excercises.find_the_way_to_the_lake;

import blockly_robot.robot.Solver;
import pi.Controller;

/**
 * https://jwinf.de/task/1158
 */
public class TaskSolver extends Solver<Robot>
{
    /**
     * <p>
     * Screenshot des Originals:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/find_the_way_to_the_lake/easy_screenshot-orig.png"
     * alt="">
     *
     * <p>
     * Lösung in Blockly:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/find_the_way_to_the_lake/easy_solution.png"
     * alt="">
     */
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

    /**
     * <p>
     * Screenshot des Originals:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/find_the_way_to_the_lake/medium_screenshot-orig.png"
     * alt="">
     *
     * <p>
     * Lösung in Blockly:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/find_the_way_to_the_lake/medium_solution.png"
     * alt="">
     */
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

    /**
     * <p>
     * Screenshot des Originals:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/find_the_way_to_the_lake/hard_screenshot-orig.png"
     * alt="">
     *
     * <p>
     * Lösung in Blockly:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/find_the_way_to_the_lake/hard_solution.png"
     * alt="">
     */
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
        Controller.instantMode(false);
        new TaskSolver().solve();
    }
}
