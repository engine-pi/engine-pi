package de.pirckheimer_gymnasium.blockly_robot.robot;

import de.pirckheimer_gymnasium.blockly_robot.robot.gui.Controller;
import de.pirckheimer_gymnasium.blockly_robot.robot.gui.scenes.LevelsScene;
import de.pirckheimer_gymnasium.blockly_robot.robot.gui.scenes.WindowScene;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.Task;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.level.Difficulty;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.level.Level;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.robot.RobotWrapper;
import de.pirckheimer_gymnasium.blockly_robot.robot.utils.PackageClassLoader;

/**
 * Klasse, die verschiedene Methoden beinhaltet, die die verschiedenen Versionen
 * einer Trainingsaufgabe löst.
 */
public abstract class Solver<T>
{
    public String taskPath;

    private static double zoom = 60;

    public Solver(String taskPath)
    {
        this.taskPath = taskPath;
    }

    public Solver()
    {
        taskPath = findTaskPathInClassHierarchy();
    }

    public String findTaskPathInClassHierarchy()
    {
        Class<?> clazz = getClass();
        while (clazz != null)
        {
            String classPath = clazz.getName();
            if (classPath.indexOf("en.tasks") != -1)
            {
                return Task.extractTaskPath(classPath);
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    public RobotWrapper createRobot(Level level) throws Exception
    {
        RobotWrapper robot = PackageClassLoader
                .instantiateClass("en.tasks.%s.Robot".formatted(taskPath));
        var context = level.getContext();
        robot.actor = context.getRobot();
        return robot;
    }

    public void easy(T robot)
    {
    }

    public void medium(T robot)
    {
    }

    public void hard(T robot)
    {
    }

    public void all(T robot)
    {
    }

    public void solve()
    {
        solve("all", 0);
    }

    public void solve(Object difficutly)
    {
        solve(difficutly, 0);
    }

    @SuppressWarnings("unchecked")
    public void solve(Object difficutly, int test)
    {
        LevelsScene scene = new LevelsScene(taskPath, difficutly, test);
        scene.getCamera().setMeter(zoom);
        Controller.launchScene((WindowScene) scene);
        scene.getAssembledLevels().forEach((level) -> {
            new Thread(() -> {
                switch (level.getLevel().getDifficulty())
                {
                case EASY:
                    easy((T) level.getRobot());
                    break;

                case MEDIUM:
                    medium((T) level.getRobot());
                    break;

                case HARD:
                    hard((T) level.getRobot());
                    break;

                default:
                    break;
                }
            }).start();
        });
    }

    @SuppressWarnings("unchecked")
    public RobotWrapper solveVirtual(Difficulty difficulty, int test)
            throws Exception
    {
        Task task = Task.loadByTaskPath(taskPath);
        Level level = task.getLevel(difficulty, test);
        RobotWrapper robot = createRobot(level);
        switch (difficulty)
        {
        case EASY:
            easy((T) robot);
            break;

        case MEDIUM:
            medium((T) robot);
            break;

        case HARD:
            hard((T) robot);
            break;

        default:
            break;
        }
        return robot;
    }

    public static void setZoom(double zoom)
    {
        Solver.zoom = zoom;
    }

    public static void setDebug(boolean debug)
    {
        Controller.setDebug(debug);
    }
}
