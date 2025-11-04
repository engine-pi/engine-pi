package de.pirckheimer_gymnasium.engine_pi_demos.debug;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.debug.ColorContainerVisualizer;

/**
 * Demonstriert die Klasse {@link ColorContainerVisualizer} mit einem
 * <b>eigenen</b> Farben-Speicher.
 *
 * <p>
 * <img alt="ColorContainerVisualizerCustom" src=
 * "https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/debug/ColorContainerVisualizerCustom.png">
 * </p>
 */
public class ColorContainerVisualizerCustomDemo
{
    public static void main(String[] args)
    {
        Resources.colors.clear();
        Resources.colors.add("custom", 1, 200, 3, "alias");
        Resources.colors.add("favourite", 117, 4, 36, "alias1", "alias2",
                "Alias 3");
        Game.start(new Scene()
        {
            {
                new ColorContainerVisualizer(this);
            }
        });
    }
}
