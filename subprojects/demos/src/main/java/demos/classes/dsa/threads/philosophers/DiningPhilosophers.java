package demos.classes.dsa.threads.philosophers;

import pi.Controller;
import pi.Scene;
import pi.actor.Circle;
import pi.actor.Line;
import pi.graphics.geom.Vector;

/**
 * Visualisierung des Problems der speisenden Philosophen.
 *
 * @author Johannes Neumeyer
 * @author Josef Friedrich
 *
 * @version 1.0
 */
class DiningPhilosophers extends Scene
{
    /**
     * Verwaltet alle Gabeln.
     */
    private Fork[] forks;

    /**
     * Verwaltet alle Teller.
     */
    private Circle[] plates;

    /**
     * Verwaltet alle Tellerfarben.
     */
    private String[] plateColors;

    /**
     * Verwaltet alle Philosophen.
     */
    private Philosopher[] philosophers;

    /**
     * Beteiligte Objekte (Philosophen, Teller, Gabeln, ...) werden passend
     * erstellt und die Philosophenthreads gestartet.
     */
    DiningPhilosophers()
    {
        info().description(
            "Abgelegte Gabeln sind schwarz, aufgenommene Gabeln haben die Farbe ihres aktuellen Besitzers.");
        forks = new Fork[5];
        plates = new Circle[5];
        plateColors = new String[] { "rot", "blau", "grün", "magenta", "grau" };
        philosophers = new Philosopher[5];

        add(new Circle(13).center(0, 0).color("braun"));

        backgroundColor("weiß");

        for (int i = 0; i < 5; i++)
        {
            // Gabeln
            Line line = new Line(Vector.ofAngle(36 + 72 * i).multiply(4),
                    Vector.ofAngle(36 + 72 * i).multiply(5));
            line.strokeWidth(0.5);
            add(line);
            forks[i] = new Fork(i, line);

            // Teller
            Circle circle = new Circle(2);
            circle.color(plateColors[i])
                .center(Vector.ofAngle(72 * i).multiply(5));
            add(circle);
            plates[i] = circle;
        }

        for (int i = 0; i < 5; i++)
        {
            Philosopher philosopher = new Philosopher(i, plateColors[i],
                    forks[(i - 1 + 5) % 5], forks[i]);
            philosopher.start();
            philosophers[i] = philosopher;
        }
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new DiningPhilosophers());
    }
}
