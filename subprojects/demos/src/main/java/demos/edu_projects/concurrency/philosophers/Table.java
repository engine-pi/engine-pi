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

import java.util.List;

import pi.Controller;
import pi.Scene;
import pi.actor.Circle;
import pi.actor.Image;
import pi.actor.Line;
import pi.annotations.Getter;
import pi.graphics.geom.Vector;

/**
 * Ein runder Tisch an dem die Philosphen essen.
 */
public class Table
{
    private Scene scene;

    private Seat[] seats;

    /**
     * Verwaltet alle Gabeln.
     */
    private Fork[] forks;

    /**
     * @param philosophers die Philosophen, die am Tisch sitzen und essen.
     */
    public Table(Scene scene, List<Philosopher> philosophers)
    {
        this.scene = scene;
        // Ein großer Kreis als runder Tisch.
        scene.add(new Circle(8).center(0, 0).color("braun"));

        // Anzahl an essenden Philosophen
        int count = philosophers.size();

        seats = new Seat[count];
        forks = new Fork[count];

        double rotation = 360.0 / count;

        double halfRoation = rotation / 2;

        for (int i = 0; i < count; i++)
        {
            Philosopher philosopher = philosophers.get(i);

            seats[i] = new Seat(this, i, philosopher);
            double currentRotation = i * rotation;
            double lineRotation = currentRotation + halfRoation;
            // Gabeln
            Line line = (Line) new Line(
                    Vector.ofAngle(lineRotation).multiply(2),
                    Vector.ofAngle(lineRotation).multiply(3)).strokeWidth(0.2)
                        .color("black");
            scene.add(line);
            forks[i] = new Fork(i, line);
        }

        // Die Gabeln zuordnen
        for (int i = 0; i < count; i++)
        {
            Philosopher philosopher = philosophers.get(i);
            philosopher.forks(forks[(i + count - 1) % count], forks[i]);
        }

        scene.addFrameListener(deltaTime -> {
            for (int i = 0; i < count; i++)
            {
                Philosopher philosopher = philosophers.get(i);
                Image image = seats[i].philosopherImage();
                // Hungert der Philosoph, so wird sein Bild durchsichtig.
                image.opacity(philosopher.isStarving() ? 0.5 : 1);
            }
        });
    }

    /**
     * Die Anzahl an Sitzen, also wie viele Philosophen am Tisch sitzen können.
     */
    @Getter
    public int seatCount()
    {
        return forks.length;
    }

    /**
     * Die Drehwinkel in Grad, von Mittelpunkt des Tisches zu zwei benachbarten
     * Philosophen.
     */
    @Getter
    public double rotation()
    {
        return 360.0 / seatCount();
    }

    public double halfRotation()
    {
        return 360.0 / seatCount();
    }

    /**
     *
     * @param distanceFromCenter
     *
     * @return
     */
    public Vector point(double rotation, double distanceFromCenter)
    {
        return Vector.ofAngle(rotation).multiply(distanceFromCenter);
    }

    public Scene scene()
    {
        return scene;
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new DiningPhilosophers(5), 800, 800);
    }
}
