package pacman;

import pi.Configuration;
import pi.Direction;
import pi.Game;
import pi.Scene;

public class Main
{
    static
    {
        Game.instantMode(false);
    }

    public static void start(Scene scene, int pixelMultiplication)
    {
        Configuration config = Configuration.get();
        config.debug.enabled(true);
        config.coordinatesystem.linesNMeter(1);
        config.graphics.windowPosition(Direction.RIGHT);
        scene.camera().meter(8);
        // 224 = 28 * 8
        // 288 = 36 * 8
        Game.start(scene, 224, 288, pixelMultiplication);
    }

    /**
     * Start die Szene mit einer Pixelvervielfältigung von 2.
     *
     * @param scene Die Szene, die gestartet werden soll.
     */
    public static void start(Scene scene)
    {
        start(scene, 2);
    }

    public static void main(String[] args)
    {
        Game.start();
    }
}
