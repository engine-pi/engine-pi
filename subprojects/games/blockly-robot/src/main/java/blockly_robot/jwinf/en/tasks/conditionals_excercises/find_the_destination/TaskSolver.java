package blockly_robot.jwinf.en.tasks.conditionals_excercises.find_the_destination;

import blockly_robot.robot.Solver;

/**
 * https://jwinf.de/task/1157
 */
public class TaskSolver extends Solver<Robot>
{
    // Test laufen nicht in Github actions
    // static
    // {
    // Game.instantMode(false);
    // }

    /**
     * <p>
     * Screenshot des Originals:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/find_the_destination/easy_screenshot-orig.png"
     * alt="">
     *
     * <p>
     * Lösung in Blockly:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/find_the_destination/easy_solution.png"
     * alt="">
     */
    @Override
    public void easy(Robot robot)
    {
        while (!robot.reachedRedFlag())
        {
            robot.turnLeft();
            if (robot.obstacleInFront())
            {
                robot.turnRight();
            }
            robot.forward();
        }
    }

    /**
     * <p>
     * Screenshot des Originals:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/find_the_destination/medium_screenshot-orig.png"
     * alt="">
     *
     * <p>
     * Lösung in Blockly:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/find_the_destination/medium_solution.png"
     * alt="">
     */
    @Override
    public void medium(Robot robot)
    {
        while (!robot.reachedRedFlag())
        {
            robot.turnLeft();
            if (robot.obstacleInFront())
            {
                robot.turnRight();
                robot.turnRight();
                if (robot.obstacleInFront())
                {
                    robot.turnLeft();
                }
            }
            robot.forward();
        }
    }

    /**
     * <p>
     * Screenshot des Originals:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/find_the_destination/hard_screenshot-orig.png"
     * alt="">
     *
     * <p>
     * Lösung in Blockly:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/conditionals_excercises/find_the_destination/hard_solution.png"
     * alt="">
     */
    @Override
    public void hard(Robot robot)
    {
        while (!robot.reachedRedFlag())
        {
            robot.turnRight();
            if (robot.obstacleInFront())
            {
                robot.turnLeft();
                if (!robot.obstacleInFront())
                {
                    robot.forward();
                }
                else
                {
                    robot.turnLeft();
                }
            }
            else
            {
                robot.forward();
            }
        }
    }

    public static void main(String[] args)
    {
        new TaskSolver().solve();
    }
}
