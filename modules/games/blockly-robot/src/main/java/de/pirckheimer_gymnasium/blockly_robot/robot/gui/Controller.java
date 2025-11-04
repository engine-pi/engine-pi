package de.pirckheimer_gymnasium.blockly_robot.robot.gui;

import de.pirckheimer_gymnasium.engine_pi.Camera;
import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.animation.interpolation.EaseInOutDouble;
import de.pirckheimer_gymnasium.engine_pi.animation.interpolation.LinearDouble;
import de.pirckheimer_gymnasium.blockly_robot.robot.gui.scenes.WindowScene;
import de.pirckheimer_gymnasium.engine_pi.Bounds;

public class Controller
{

    public static void setDebug(boolean debug)
    {
        Game.setDebug(debug);
    }

    public static void toggleInterpolator()
    {
        if (!(State.interpolator instanceof EaseInOutDouble))
        {
            State.interpolator = new EaseInOutDouble(0, 1);
        }
        else
        {
            State.interpolator = new LinearDouble(0, 1);
        }
    }

    public static void launchScene(int width, int height, Scene scene,
            boolean debug)
    {
        scene.setBackgroundColor(Color.WHITE);
        if (!Game.isRunning())
        {
            Game.start(scene, width, height);
            if (debug)
            {
                Game.setDebug(true);
            }
        }
        else
        {
            Game.transitionToScene(scene);
            Game.setWindowSize(width, height);
        }
    }

    public static void launchScene(WindowScene windowScene)
    {
        Scene scene = (Scene) windowScene;
        Camera camera = scene.getCamera();
        double pixelPerMeter = camera.getMeter();
        Bounds bounds = windowScene.getWindowBounds();
        Vector center = bounds.getCenter();
        camera.setPostion(center.getX(), center.getY());
        Game.setTitle(windowScene.getTitle());
        launchScene((int) Math.round(pixelPerMeter * bounds.width()),
                (int) Math.round(pixelPerMeter * bounds.height()),
                (Scene) windowScene);
    }

    public static void launchScene(Scene scene, boolean debug)
    {
        launchScene(800, 600, scene, debug);
    }

    public static void launchScene(Scene scene)
    {
        launchScene(scene, false);
    }

    public static void launchScene(int width, int height, Scene scene)
    {
        launchScene(width, height, scene, false);
    }
}
