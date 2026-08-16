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
import pi.graphics.boxes_ng.BorderBox;
import pi.graphics.boxes_ng.Box;
import pi.graphics.boxes_ng.ImageBox;
import pi.graphics.boxes_ng.VerticalBox;

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/graphics/boxes/ImageBox.java

/**
 * Demonstriert die Darstellung von <b>Bildboxen</b> mit unterschiedlichen
 * Größen und Spiegelungen.
 */
public class ImageBoxDemo extends Graphics2DComponent
{
    /**
     * Erstellt eine Beispielbox mit einem Würfelbild.
     *
     * @return Eine neue {@link ImageBox} mit dem Bild eines Würfels.
     */
    private ImageBox box()
    {
        return new ImageBox("dude/box/obj_box001.png");
    }

    /**
     * Erstellt eine Beispielbox mit einem Fahrzeugbild.
     *
     * @return Eine neue {@link ImageBox} mit dem Bild eines Autos.
     */
    private ImageBox car()
    {
        return new ImageBox("car/truck-240px.png");
    }

    /**
     * Umhüllt ein Bild mit einem dünnen Rahmen.
     *
     * @param image Die Bildbox, die mit einem Rahmen versehen werden soll.
     *
     * @return Eine neue {@link BorderBox} mit dem Bild als Inhalt.
     */
    private BorderBox b(Box image)
    {
        return new BorderBox(image).thickness(1);
    }

    /**
     * Rendert eine Demo mit Bildboxen in verschiedenen Größen und
     * Spiegelungsvarianten.
     *
     * @param g Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     */
    public void render(Graphics2D g)
    {
        new VerticalBox<BorderBox>(
                // Höhe und Breite nicht angegeben (Bild hat eine Abmessung von
                // 96x96 Pixel)
                b(box()),
                // Breite angegeben
                b(box().width(16)),
                // Höhe angegeben
                b(box().height(32)),
                // Höhe und Breite angegeben
                b(box().width(150).height(50)),
                // Breite in Meter gesetzt
                b(box().widthMeter(2).pixelPerMeter(32))
        // Ende Konstruktor
        ).padding(5).y(400).render(g).debug();

        new VerticalBox<BorderBox>(
                // nicht gespiegelt
                b(car()),
                // vertikal gespiegelt
                b(car().vFlip()),
                // horizontal gespiegelt
                b(car().hFlip()),
                // vertikal und horizontal gespiegelt
                b(car().vFlip().hFlip())
        // Ende Konstruktor
        ).padding(5).anchor(400, 500).render(g).debug();
    }

    /**
     * Startet die Demo für die Bilddarstellung.
     *
     * @param args Die Programargumente.
     */
    public static void main(String[] args)
    {
        new ImageBoxDemo().open();
    }
}
