package demos.debug;

import pi.Controller;
import pi.Resources;
import pi.Scene;
import pi.debug.ColorContainerVisualizer;

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
        Controller.instantMode(false);
        Resources.colors.clear();
        Resources.colors.add("custom", 1, 200, 3, "alias");
        Resources.colors.add("favourite", 117, 4, 36, "alias1", "alias2",
                "Alias 3");
        Controller.start(new Scene()
        {
            {
                new ColorContainerVisualizer(this);
            }
        });
    }
}
