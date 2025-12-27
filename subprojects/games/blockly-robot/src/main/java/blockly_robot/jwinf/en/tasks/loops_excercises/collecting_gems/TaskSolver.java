package blockly_robot.jwinf.en.tasks.loops_excercises.collecting_gems;

import blockly_robot.robot.Solver;

/**
 * https://jwinf.de/task/1139
 */
public class TaskSolver extends Solver<Robot>
{
    /**
     * <p>
     * Screenshot des Originals:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/loops_excercises/collecting_gems/easy_screenshot-orig.png"
     * alt="">
     *
     * <p>
     * Lösung in Blockly:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/loops_excercises/collecting_gems/easy_solution.png"
     * alt="">
     */
    @Override
    public void easy(Robot robot)
    {
        robot.forward();
        robot.forward();
        robot.turnLeft();
        robot.forward();
        robot.forward();
    }

    /**
     * <p>
     * Screenshot des Originals:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/loops_excercises/collecting_gems/medium_screenshot-orig.png"
     * alt="">
     *
     * <p>
     * Lösung in Blockly:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/loops_excercises/collecting_gems/medium_solution.png"
     * alt="">
     */
    @Override
    public void medium(Robot robot)
    {
        for (int i = 0; i < 6; i++)
        {
            robot.forward();
        }
        robot.turnLeft();
        for (int i = 0; i < 8; i++)
        {
            robot.forward();
        }
        robot.turnLeft();
        for (int i = 0; i < 3; i++)
        {
            robot.forward();
        }
        robot.turnLeft();
        for (int i = 0; i < 4; i++)
        {
            robot.forward();
        }
    }

    /**
     * <p>
     * Screenshot des Originals:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/loops_excercises/collecting_gems/hard_screenshot-orig.png"
     * alt="">
     *
     * <p>
     * Lösung in Blockly:
     * </p>
     *
     * <img src=
     * "https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/blockly-robot/misc/solutions/loops_excercises/collecting_gems/hard_solution.png"
     * alt="">
     */
    @Override
    public void hard(Robot robot)
    {
        robot.forward();
        robot.forward();
        for (int i = 0; i < 10; i++)
        {
            robot.turnLeft();
            robot.forward();
            robot.turnRight();
            robot.forward();
            robot.forward();
            robot.forward();
            robot.turnLeft();
            robot.turnLeft();
            robot.forward();
            robot.forward();
            robot.turnRight();
            robot.turnRight();
        }
    }

    public static void main(String[] args)
    {
        new TaskSolver().solve();
    }
}
