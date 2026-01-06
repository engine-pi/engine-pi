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

import pi.Controller;
import pi.Rectangle;
import pi.Scene;
import pi.Text;
import pi.resources.color.ColorContainer;
import pi.resources.color.NamedColor;
import static pi.Controller.colors;

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
        scene.backgroundColor("#222222");
        double y = 8;
        for (Map.Entry<String, Color> entry : container.getAll().entrySet())
        {
            NamedColor color = container.namedColor(entry.getKey());
            // Rechteck, das die Farbe als Füllfarbe demonstiert
            scene.add(
                    new Rectangle(1, 1).position(-12, y).color(color.color()));
            // Der dezimale Farbcode
            scene.add(new Text(color.colorDecFormatted()).height(0.5)
                    .font("Monospaced").position(-10, y + 0.25)
                    .color(color.color()));
            // Der hexadezimale Farbcode
            scene.add(new Text(color.colorHexFormatted()).height(0.5)
                    .font("Monospaced").position(-6, y + 0.25)
                    .color(color.color()));
            // Der Hauptname der Farbe
            scene.add(new Text(color.name()).height(0.5).position(-3, y + 0.25)
                    .color(WHITE));
            // Die Aliasse
            scene.add(new Text(color.aliasesFormatted()).height(0.3)
                    .color(WHITE).position(1, y + 0.3));
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
     * @see Controller#colors
     */
    public ColorContainerVisualizer(Scene scene)
    {
        this(colors, scene);
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new Scene()
        {
            {
                new ColorContainerVisualizer(this);
            }
        });
    }
}
