package blockly_robot.robot.gui;

import blockly_robot.robot.gui.scenes.WindowScene;
import pi.Bounds;
import pi.Camera;
import pi.Game;
import pi.Scene;
import pi.Vector;
import pi.animation.interpolation.EaseInOutDouble;
import pi.animation.interpolation.LinearDouble;

public class Controller
{
    static
    {
        Game.instantMode(false);
    }

    public static void setDebug(boolean debug)
    {
        Game.debug(debug);
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
        scene.backgroundColor(Color.WHITE);
        if (!Game.isRunning())
        {
            Game.start(scene, width, height);
            if (debug)
            {
                Game.debug(true);
            }
        }
        else
        {
            Game.transitionToScene(scene);
            Game.windowSize(width, height);
        }
    }

    public static void launchScene(WindowScene windowScene)
    {
        Scene scene = (Scene) windowScene;
        Camera camera = scene.camera();
        double pixelPerMeter = camera.meter();
        Bounds bounds = windowScene.getWindowBounds();
        Vector center = bounds.center();
        camera.focus(center.x(), center.y());
        Game.title(windowScene.getTitle());
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
