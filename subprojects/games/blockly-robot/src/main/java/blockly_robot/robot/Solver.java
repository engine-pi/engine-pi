package blockly_robot.robot;

import blockly_robot.robot.gui.Controller;
import blockly_robot.robot.gui.scenes.LevelsScene;
import blockly_robot.robot.gui.scenes.WindowScene;
import blockly_robot.robot.logic.Task;
import blockly_robot.robot.logic.level.Difficulty;
import blockly_robot.robot.logic.level.Level;
import blockly_robot.robot.logic.robot.RobotWrapper;
import blockly_robot.robot.utils.PackageClassLoader;
import pi.annotations.Setter;

/**
 * Klasse, die verschiedene Methoden beinhaltet, die die verschiedenen Versionen
 * einer Trainingsaufgabe löst.
 */
public abstract class Solver<T>
{
    // Test are falling in Github Actions ?
    // static
    // {
    // pi.Controller.instantMode(false);
    // }

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
        var context = level.context();
        robot.actor = context.robot();
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
        scene.camera().meter(zoom);
        Controller.launchScene((WindowScene) scene);
        scene.assembledLevels().forEach((level) -> {
            new Thread(() -> {
                switch (level.level().difficulty())
                {
                case EASY:
                    easy((T) level.robot());
                    break;

                case MEDIUM:
                    medium((T) level.robot());
                    break;

                case HARD:
                    hard((T) level.robot());
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
        Level level = task.level(difficulty, test);
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

    @Setter
    public static void debug(boolean debug)
    {
        Controller.debug(debug);
    }
}
