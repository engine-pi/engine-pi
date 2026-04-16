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

import java.awt.Graphics2D;
import java.util.List;

import pi.Controller;
import pi.Scene;
import pi.actor.StopWatch;
import pi.event.FrameListener;
import pi.graphics.boxes.TextTableBox;

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/projects/philosophers.md

/**
 * Visualisierung des Problems der speisenden Philosophen.
 *
 * @author Johannes Neumeyer
 * @author Josef Friedrich
 *
 * @version 1.0
 */
class DiningPhilosophers extends Scene implements FrameListener
{
    /**
     * Die zufällig ausgewählten Philosophen, die am Tisch essen.
     */
    private List<Philosopher> philosophers;

    /**
     * Verhungern die Philosophen?
     */
    boolean starving = false;

    StopWatch stopWatch;

    TextTableBox table;

    /**
     * Beteiligte Objekte (Philosophen, Teller, Gabeln, ...) werden passend
     * erstellt und die Philosophenthreads gestartet.
     *
     * @param count Die Anzahl der Philosophen, die am Tisch sitzen und essen.
     */
    DiningPhilosophers(int count)
    {
        info().description(
            "Abgelegte Gabeln sind schwarz, aufgenommene Gabeln haben die Farbe ihres aktuellen Besitzers.");

        // Die die Philosophenbilder eine weiße Hintergrundfarbe und die
        // Philosophen nicht freigestellt sind, verwenden wir als
        // Hintergrundfarbe für die Szene weiß.
        backgroundColor("weiß");

        philosophers = new PhilosophersCollection().select(count);

        new Table(this, philosophers);

        stopWatch = (StopWatch) new StopWatch().start()
            .anchor(-10, 10)
            .color("black");
        add(stopWatch);

        table = new TextTableBox();
        table.columns(2).anchor(600, 600);
        table.padding(5);

        for (Philosopher philosopher : philosophers)
        {
            philosopher.start();

            table.addCell(philosopher.name());
            table.addCell(philosopher.eatCounter());
        }

    }

    @Override
    public void renderOverlay(Graphics2D g, int width, int height)
    {
        for (int i = 0; i < philosophers.size(); i++)
        {
            final Philosopher philosopher = philosophers.get(i);
            table.forBox(i,
                1,
                cell -> cell.box.content(philosopher.eatCounter()));
        }
        table.render(g);
    }

    @Override
    public void onFrame(double pastTime)
    {
        starving = true;
        for (Philosopher philosopher : philosophers)
        {
            if (!philosopher.isStarving())
            {
                starving = false;
            }
        }

        if (starving)
        {
            stopWatch.stop();
        }
        else
        {
            stopWatch.start();
        }
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new DiningPhilosophers(5), 800, 800);
    }
}
