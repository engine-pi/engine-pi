package blockly_robot.jwinf.en.tasks.loops_excercises.securing_the_road;

import blockly_robot.robot.Solver;

/**
 * https://jwinf.de/task/1140
 */
public class TaskSolver extends Solver<Robot>
{
    /**
     * <p>
     * Screenshot des Originals:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/loops_excercises/securing_the_road/easy_screenshot-orig.png"
     * alt="">
     *
     * <p>
     * Lösung in Blockly:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/loops_excercises/securing_the_road/easy_solution.png"
     * alt="">
     */
    @Override
    public void easy(Robot robot)
    {
        robot.forward();
        robot.forward();
        for (int i = 0; i < 8; i++)
        {
            robot.dropCone();
            robot.forward();
        }
    }

    /**
     * <p>
     * Screenshot des Originals:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/loops_excercises/securing_the_road/medium_screenshot-orig.png"
     * alt="">
     *
     * <p>
     * Lösung in Blockly:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/loops_excercises/securing_the_road/medium_solution.png"
     * alt="">
     */
    @Override
    public void medium(Robot robot)
    {
        for (int i = 0; i < 4; i++)
        {
            robot.forward();
            robot.turnLeft();
            robot.forward();
            robot.dropCone();
            robot.turnRight();
            robot.forward();
            robot.turnRight();
            robot.forward();
            robot.turnLeft();
            robot.forward();
        }
    }

    /**
     * <p>
     * Screenshot des Originals:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/loops_excercises/securing_the_road/hard_screenshot-orig.png"
     * alt="">
     *
     * <p>
     * Lösung in Blockly:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/loops_excercises/securing_the_road/hard_solution.png"
     * alt="">
     */
    @Override
    public void hard(Robot robot)
    {
        robot.forward();
        robot.forward();
        for (int i = 0; i < 4; i++)
        {
            robot.dropCone();
            robot.forward();
            robot.dropCone();
            robot.turnLeft();
            robot.forward();
            robot.dropCone();
            robot.turnRight();
            robot.forward();
            robot.turnRight();
            robot.forward();
            robot.turnLeft();
        }
    }

    public static void main(String[] args)
    {
        new TaskSolver().solve();
    }
}
