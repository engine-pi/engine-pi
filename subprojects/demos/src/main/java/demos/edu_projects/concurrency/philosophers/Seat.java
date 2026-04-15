/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2026 Josef Friedrich and contributors.
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
package demos.edu_projects.concurrency.philosophers;

import pi.actor.Circle;
import pi.actor.Image;
import pi.actor.label.TextLabel;
import pi.annotations.Getter;
import pi.graphics.boxes.VAlign;

/**
 * Ein Sitzplatz am Tisch.
 *
 * Der Sitzplatz hat eine Sitzplatznummer und ihm wird ein Philosoph zugeordnet.
 */
public class Seat
{
    private Table table;

    /**
     * Die Sitzplatznummer beginnend bei 0.
     */
    private int no;

    private Philosopher philosopher;

    /**
     * Das Bild eines Philosophen.
     */
    private Image philosopherImage;

    public Seat(Table table, int no, Philosopher philosopher)
    {
        this.table = table;
        this.no = no;
        this.philosopher = philosopher;

        var scene = table.scene();

        scene.add(createPlate());

        philosopherImage = createPhilosopherImage();
        scene.add(philosopherImage);
    }

    /**
     * Erzeugt einen Teller, der in der Farbe des Philosophen gefärbt ist.
     */
    private Circle createPlate()
    {
        return (Circle) new Circle(1).color(philosopher.color())
            .center(table.point(rotation(), 3));
    }

    private Image createPhilosopherImage()
    {
        return (Image) new Image("philosophers/" + philosopher.name() + ".png")
            .pixelPerMeter(30)
            .center(table.point(rotation(), 9))
            .label(philosopher.name())
            .label(new TextLabel(philosopher.lifeTime()).fontSize(8)
                .vAlign(VAlign.TOP));
    }

    public double rotation()
    {
        return table.rotation() * no;
    }

    @Getter
    public Image philosopherImage()
    {
        if (philosopherImage == null)
        {
            table.scene().add(philosopherImage);
        }
        return philosopherImage;
    }
}
