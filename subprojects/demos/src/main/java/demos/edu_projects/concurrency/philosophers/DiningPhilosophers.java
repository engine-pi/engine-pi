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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pi.Controller;
import pi.Scene;
import pi.actor.StopWatch;
import pi.event.FrameListener;

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
     * Verwaltet alle Philosophen.
     */
    private List<Philosopher> allPhilosophers;

    /**
     * Die zufaellig ausgewaehlten Philosophen, die am Tisch essen.
     */
    private List<Philosopher> eatingPhilosophers;

    /**
     * Verhungern die Philosophen?
     */
    boolean arePhilosophersStarving = false;

    StopWatch stopWatch;

    /**
     * Beteiligte Objekte (Philosophen, Teller, Gabeln, ...) werden passend
     * erstellt und die Philosophenthreads gestartet.
     */
    DiningPhilosophers(int numbeOfEatingPhilosophers)
    {
        info().description(
            "Abgelegte Gabeln sind schwarz, aufgenommene Gabeln haben die Farbe ihres aktuellen Besitzers.");
        allPhilosophers = new ArrayList<>(30);

        // Die die Philosophenbilder eine weiße Hintergrundfarbe und die
        // Philosophen nicht freigestellt sind, verwenden wir als
        // Hintergrundfarbe für die Szene weiß.
        backgroundColor("weiß");

        createPhilosopher("Aquin", "#2a3234", 1225, 1274);
        createPhilosopher("Aristoteles", "#0fa0e3", -384, -322);
        createPhilosopher("Augustinus", "#ffb61c", 354, 430);
        createPhilosopher("Averroes", "#dac66e", 1126, 1198);
        createPhilosopher("Bacon", "#377296", 1561, 1626);
        createPhilosopher("Buddha", "#ff7a18", -563, -483);
        createPhilosopher("Descartes", "#1f2628", 1596, 1650);
        createPhilosopher("Hegel", "#7f5437", 1770, 1831);
        createPhilosopher("Hume", "#f7333f", 1711, 1776);
        createPhilosopher("Jesus", "#fa323f", -4, 30);
        createPhilosopher("Kant", "#767733", 1724, 1804);
        createPhilosopher("Kierkegaard", "#471008", 1813, 1855);
        createPhilosopher("Konfuzius", "#8b101d", -551, -479);
        createPhilosopher("Laozi", "#114ea6", -600, -550);
        createPhilosopher("Machiavelli", "#f7323d", 1469, 1527);
        createPhilosopher("Marx", "#1b2223", 1818, 1883);
        createPhilosopher("Montesquieu", "#87111b", 1689, 1755);
        createPhilosopher("Nietzsche", "#293133", 1844, 1900);
        createPhilosopher("Platon", "#f2f3eb", -427, -348);
        createPhilosopher("Rousseau", "#3c7eaa", 1712, 1778);
        createPhilosopher("Schopenhauer", "#323835", 1788, 1860);
        createPhilosopher("Seneca", "#f2f3eb", 1, 65);
        createPhilosopher("Sokrates", "#f2f3eb", -469, -399);
        createPhilosopher("Thales", "#0fa2e3", -623, -548);
        createPhilosopher("Unamuno", "#232a2c", 1864, 1936);
        createPhilosopher("Voltaire", "#764b9c", 1694, 1778);

        eatingPhilosophers = selectPhilosophers(numbeOfEatingPhilosophers);

        new Table(this, eatingPhilosophers);

        stopWatch = (StopWatch) new StopWatch().start()
            .anchor(-10, 10)
            .color("black");
        add(stopWatch);

        for (Philosopher philosopher : allPhilosophers)
        {
            philosopher.start();
        }
    }

    private void createPhilosopher(String name, String color, int birth,
            int death)
    {
        allPhilosophers.add(new Philosopher(name, color, birth, death));
    }

    private List<Philosopher> selectPhilosophers(int numbeOfEatingPhilosophers)
    {
        if (numbeOfEatingPhilosophers < 0
                || numbeOfEatingPhilosophers > allPhilosophers.size())
        {
            throw new IllegalArgumentException(
                    "Anzahl der essenden Philosophen ist ungueltig: "
                            + numbeOfEatingPhilosophers);
        }

        List<Philosopher> shuffledPhilosophers = new ArrayList<>(
                allPhilosophers);
        Collections.shuffle(shuffledPhilosophers);

        List<Philosopher> selectedPhilosophers = new ArrayList<>(
                shuffledPhilosophers.subList(0, numbeOfEatingPhilosophers));

        // Wir sortieren aufsteigenend nach dem Geburtsjahr.
        selectedPhilosophers.sort(Comparator.comparingInt(Philosopher::birth));

        return selectedPhilosophers;
    }

    @Override
    public void onFrame(double pastTime)
    {
        arePhilosophersStarving = true;
        for (Philosopher philosopher : eatingPhilosophers)
        {
            if (!philosopher.isStarving())
            {
                arePhilosophersStarving = false;
            }
        }

        if (arePhilosophersStarving)
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
