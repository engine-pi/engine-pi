/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2024 Josef Friedrich and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package pi.debug;

import java.awt.Color;
import java.util.Map;

import pi.Game;
import pi.Resources;
import pi.Scene;
import pi.resources.ColorContainer;
import pi.resources.NamedColor;

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
     * @param scene Die Szene in der die Visualisierung eingezeichnet werden
     *     soll.
     */
    public ColorContainerVisualizer(ColorContainer container, Scene scene)
    {
        scene.setBackgroundColor("#222222");
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
     * Dabei wir der <b>Standard-Speicher für Farben</b> verwendet
     *
     * @param scene Die Szene in der die Visualisierung eingezeichnet werden
     *     soll.
     *
     * @see Resources#colors
     */
    public ColorContainerVisualizer(Scene scene)
    {
        this(Resources.colors, scene);
    }

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
