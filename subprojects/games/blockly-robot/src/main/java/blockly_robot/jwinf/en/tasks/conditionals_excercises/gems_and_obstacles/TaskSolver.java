package blockly_robot.jwinf.en.tasks.conditionals_excercises.gems_and_obstacles;

import blockly_robot.robot.Solver;
import pi.Controller;

/**
 * https://jwinf.de/task/1161
 */
public class TaskSolver extends Solver<Robot>
{
    /**
     * <p>
     * Screenshot des Originals:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/gems_and_obstacles/easy_screenshot-orig.png"
     * alt="">
     *
     * <p>
     * Lösung in Blockly:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/gems_and_obstacles/easy_solution.png"
     * alt="">
     */
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

    /**
     * <p>
     * Screenshot des Originals:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/gems_and_obstacles/medium_screenshot-orig.png"
     * alt="">
     *
     * <p>
     * Lösung in Blockly:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/gems_and_obstacles/medium_solution.png"
     * alt="">
     */
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

    /**
     * <p>
     * Screenshot des Originals:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/gems_and_obstacles/hard_screenshot-orig.png"
     * alt="">
     *
     * <p>
     * Lösung in Blockly:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/gems_and_obstacles/hard_solution.png"
     * alt="">
     */
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
        Controller.instantMode(false);
        new TaskSolver().solve();
    }
}
