package demos.classes.dsa.threads.philosophen;

import java.util.ArrayList;

import pi.Controller;
import pi.Scene;
import pi.actor.Circle;
import pi.actor.Line;
import pi.graphics.geom.Vector;

/**
 * Visualisierung des Problems der speisenden Philosophen
 *
 * @author Johannes Neumeyer
 * @author Josef Friedrich
 *
 * @version 1.0
 */
class DiningPhilosophers extends Scene
{
    /**
     * verwaltet alle Gabeln
     */
    private ArrayList<Fork> forks;

    /**
     * verwaltet alle Teller
     */
    private ArrayList<Circle> plates;

    /**
     * verwaltet alle Tellerfarben
     */
    private ArrayList<String> plateColors;

    /**
     * verwaltet alle Philosophen
     */
    private ArrayList<Philosopher> philosophers;

    /**
     * Beteiligte Objekte (Philosophen, Teller, Gabeln, ...) werden passend
     * erstellt und die Philosophenthreads gestartet.
     */
    DiningPhilosophers()
    {
        info().description(
            "Abgelegte Gabeln sind schwarz, aufgenommene Gabeln haben die Farbe ihres aktuellen Besitzers.");
        forks = new ArrayList<Fork>();
        plates = new ArrayList<Circle>();
        plateColors = new ArrayList<String>();
        plateColors.add("rot");
        plateColors.add("blau");
        plateColors.add("grün");
        plateColors.add("magenta");
        plateColors.add("grau");
        philosophers = new ArrayList<Philosopher>();

        for (int i = 0; i < 5; i++)
        {
            // Gabeln
            Line line = new Line(Vector.ofAngle(36 + 72 * i).multiply(4),
                    Vector.ofAngle(36 + 72 * i).multiply(6));
            line.strokeWidth(0.5);
            add(line);
            forks.add(new Fork(i, line));

            // Teller
            Circle circle = new Circle(2);
            add(circle);
            circle.color(plateColors.get(i));
            plates.add(new Circle());
            circle.center(Vector.ofAngle(72 * i).multiply(5));
            plates.add(circle);
        }

        for (int i = 0; i < 5; i++)
        {
            philosophers.add(new Philosopher(i, plateColors.get(i),
                    forks.get((i - 1 + 5) % 5), forks.get(i)));
            philosophers.get(i).start();
        }
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new DiningPhilosophers());
    }
}
