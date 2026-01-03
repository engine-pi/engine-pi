package demos.debug;

import pi.Game;
import pi.Scene;
import pi.debug.ColorContainerVisualizer;

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
        Game.instantMode(false);
        Game.start(new Scene()
        {
            {
                new ColorContainerVisualizer(this);
            }
        });
    }
}
