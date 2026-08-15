/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
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
package demos.classes.graphics.boxes_ng;

import java.awt.Graphics2D;

import demos.graphics2d.Graphics2DComponent;
import pi.graphics.boxes_ng.CompassBox;
import pi.graphics.boxes_ng.GridBox;

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/graphics/boxes/CompassBox.java

/**
 * Demonstriert die Darstellung verschiedener <b>Kompasspfeile</b> in einer
 * Rasteranordnung.
 *
 * <p>
 * Die Demo zeigt unterschiedliche Größen und Richtungen, damit die Anzeige des
 * Kompasspfeils in der Spiel- und Grafik-API nachvollziehbar ist.
 * </p>
 */
public class CompassBoxDemo extends Graphics2DComponent
{
    /**
     * Erstellt eine Gruppe von Kompasspfeilen mit einer festen Richtung und
     * einem gleichmäßigen Abstand zwischen den einzelnen Elementen.
     *
     * @param numberOfCompasses Die Anzahl der zu erzeugenden Kompasspfeile.
     * @param size Die Größe jedes Kompasspfeils in Pixel.
     * @param directionDelta Die Winkeländerung zwischen den einzelnen
     *     Kompasspfeilen in Grad.
     *
     * @return Ein Raster mit den erzeugten Kompasspfeilen.
     */
    private GridBox<CompassBox> getCompasses(int numberOfCompasses, int size,
            int directionDelta)
    {
        int direction = 0;
        CompassBox[] compasses = new CompassBox[numberOfCompasses];
        for (int i = 0; i < numberOfCompasses; i++)
        {
            compasses[i] = new CompassBox(size).direction(direction)
                .showOuterCircle();
            direction += directionDelta;
        }
        GridBox<CompassBox> grid = new GridBox<>(compasses);
        grid.padding(5);
        return grid;
    }

    public void render(Graphics2D g)
    {
        // Ein großer Kompass
        new CompassBox(200).direction(90)
            .showOuterCircle()
            .x(10)
            .y(300)
            .render(g)
            .debug();

        // 4 Kompasse mittlerer Größe
        getCompasses(4, 100, 90).x(250).y(300).render(g).debug();

        // 9 kleine Kompasse
        getCompasses(9, 20, 30).x(500).y(250).render(g).debug();
    }

    public static void main(String[] args)
    {
        new CompassBoxDemo().open();
    }
}
