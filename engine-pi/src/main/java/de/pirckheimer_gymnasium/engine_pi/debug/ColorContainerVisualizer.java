package de.pirckheimer_gymnasium.engine_pi.debug;

import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.resources.ColorContainer;
import de.pirckheimer_gymnasium.engine_pi.resources.NamedColor;

import java.awt.*;
import java.util.Map;

/**
 * Visualisiert den gegebenen Speicher für Farben.
 *
 * @author Josef Friedrich
 *
 * @since 0.26.0
 */
public class ColorContainerVisualizer
{
    private static final Color WHITE = new Color(255, 255, 255);

    /**
     * Zeichnet eine Visualisierung eines Speichers für Farben in eine Szene.
     *
     * @param container Der Speicher für Farben, der visualisiert werden soll.
     * @param scene     Die Szene in der die Visualisierung eingezeichnet werden
     *                  soll.
     */
    public ColorContainerVisualizer(ColorContainer container, Scene scene)
    {
        double y = 8;
        for (Map.Entry<String, Color> entry : container.getAll().entrySet())
        {
            NamedColor namedColor = container.getNamedColor(entry.getKey());
            scene.addRectangle(1, 1).setPosition(-6, y)
                    .setColor(namedColor.getColor());
            scene.addText(namedColor.getName()).setHeight(0.5)
                    .setPosition(-4, y + 0.25).setColor(WHITE);
            scene.addText(namedColor.getColorHexFormatted()).setHeight(0.5).setPosition(0, y).setColor(WHITE);
            scene.addText(namedColor.getAliasesFormatted()).setHeight(0.3)
                    .setColor(WHITE).setPosition(2, y + 0.3);
            y--;
        }
    }

    /**
     * Zeichnet eine Visualisierung eines Speichers für Farben in eine Szene.
     * Dabei wir der Standard Speicher für Farben verwendet
     *
     * @param scene Die Szene in der die Visualisierung eingezeichnet werden
     *              soll.
     *
     * @see Resources#COLORS
     */
    public ColorContainerVisualizer(Scene scene)
    {
        this(Resources.COLORS, scene);
    }
}
