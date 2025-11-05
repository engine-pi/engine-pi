package de.pirckheimer_gymnasium.demos.debug;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.debug.ColorContainerVisualizer;

/**
 * Demonstriert die Klasse {@link ColorContainerVisualizer} mit dem
 * <b>Standard-Farben-Speicher</b>.
 *
 * <p>
 * <img alt="ColorContainerVisualizerDefault" src=
 * "https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/debug/ColorContainerVisualizerDefault.png">
 * </p>
 *
 * @author Josef Friedrich
 */
public class ColorContainerVisualizerDefaultDemo
{
    public static void main(String[] args)
    {
        Game.start(new Scene()
        {
            {
                new ColorContainerVisualizer(this);
            }
        });
    }
}
