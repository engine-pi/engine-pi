package demos.classes.dsa.threads.philosophers;

import pi.Controller;
import pi.Scene;
import pi.actor.Circle;
import pi.actor.Line;
import pi.graphics.geom.Vector;
import pi.actor.StopWatch;
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
        }

        createPhilosopher(0, "Konfuzius", "#961b2d");
        createPhilosopher(1, "Sokrates", "#e9eae4");
        createPhilosopher(2, "Voltaire", "#845ca5");
        createPhilosopher(3, "Kant", "#767733");
        createPhilosopher(4, "Nietzsche", "#293133");

        for (int i = 0; i < 5; i++)
        {
            philosophers[i].start();
        }
        add(new StopWatch().start().anchor(-13, 13).color("black"));
    }

    private void createPhilosopher(int id, String name, String color)
    {
        philosophers[id] = new Philosopher(this, id, name, color,
                forks[(id + 4) % 5], forks[id]);
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new DiningPhilosophers(), 950, 950);
    }
}
