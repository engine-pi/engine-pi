package de.pirckheimer_gymnasium.engine_pi.debug;

import java.awt.Color;
import java.util.Map;

import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.resources.ColorContainer;
import de.pirckheimer_gymnasium.engine_pi.resources.NamedColor;

/**
 * <b>Visualisiert</b> den gegebenen <b>Speicher für Farben</b>.
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
            NamedColor color = container.getNamedColor(entry.getKey());
            // Rechteck, das die Farbe als Füllfarbe demonstiert
            scene.addRectangle(1, 1).setPosition(-12, y)
                    .setColor(color.getColor());
            // Der dezimale Farbcode
            scene.addText(color.getColorDecFormatted()).setHeight(0.5)
                    .setFont("Monospaced").setPosition(-10, y + 0.25)
                    .setColor(color.getColor());
            // Der hexadezimale Farbcode
            scene.addText(color.getColorHexFormatted()).setHeight(0.5)
                    .setFont("Monospaced").setPosition(-6, y + 0.25)
                    .setColor(color.getColor());
            // Der Hauptname der Farbe
            scene.addText(color.getName()).setHeight(0.5)
                    .setPosition(-3, y + 0.25).setColor(WHITE);
            // Die Aliasse
            scene.addText(color.getAliasesFormatted()).setHeight(0.3)
                    .setColor(WHITE).setPosition(1, y + 0.3);
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
