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

import pi.actor.Image;
import pi.actor.label.TextLabel;
import pi.event.FrameListener;
import pi.graphics.boxes.VAlign;

/**
 * Das Bild, das einen Philosophen zeigt.
 *
 * <p>
 * Um die Klasse {@link Philosopher} nicht zu überfrachten mit Engine Pi
 * spezifischen Code, ist der Code zu Erzeugen eines Bildes nicht in dieser
 * Klasse beheimatet.
 * </p>
 */
public class PhilosopherImage extends Image implements FrameListener
{
    private Philosopher philosopher;

    public PhilosopherImage(Seat seat, Philosopher philosopher)
    {
        super("philosophers/" + philosopher.name() + ".png");
        this.philosopher = philosopher;
        pixelPerMeter(30);
        center(seat.table().point(seat.rotation(), 8.5));
        label(philosopher.name());
        label(new TextLabel(philosopher.lifeTime()).fontSize(8)
            .vAlign(VAlign.TOP));
    }

    @Override
    public void onFrame(double pastTime)
    {
        // Hungert der Philosoph, so wird sein Bild durchsichtig.
        opacity(philosopher.isStarving() ? 0.5 : 1);
    }
}
