package demos.classes.dsa.threads.philosophers;

import java.util.Random;

import pi.Controller;
import pi.actor.Image;
import pi.Scene;
import pi.actor.Circle;
import pi.graphics.geom.Vector;

/**
 * Ein speisender Philosoph.
 *
 * @author Johannes Neumeyer
 * @author Josef Friedrich
 *
 * @version 1.0
 */
class Philosopher extends Thread
{
    /**
     * Die ID des Philosophen.
     */
    private int id;

    /**
     * Die Zeitangabe in ms als Grundlage für die Bestimmung zufälliger Ess- und
     * Wartezeiten.
     */
    private int waitingTime;

    /**
     * Die Farbe des Tellers.
     */
    private String color;

    /**
     * Die linke Gabel.
     */
    private Fork leftFork;

    /**
     * Die rechte Gabel.
     */
    private Fork rightFork;

    /**
     * Der Zufallsgenerator.
     */
    private Random random;

    /**
     * Konstruktor für Objekte der Klasse Philosoph.
     *
     * @param id Die ID des Philosophen.
     * @param color Die Farbe des Tellers.
     * @param left Die linke Gabel, die der Philosoph nutzt.
     * @param right Die rechte Gabel, die der Philosoph nutzt.
     */
    Philosopher(Scene scene, int id, String name, String color, Fork left,
            Fork right)
    {
        waitingTime = 50;
        this.id = id;
        this.color = color;
        leftFork = left;
        rightFork = right;
        random = new Random();

        // Teller
        scene.add(new Circle(2).color(color)
            .center(Vector.ofAngle(72 * id).multiply(5)));
        // Bild des Philosophen
        scene.add(new Image("philosophers/" + name + ".jpg").pixelPerMeter(50)
            .center(Vector.ofAngle(72 * id).multiply(11)));

    }

    public int id()
    {
        return id;
    }

    private void sleep()
    {
        try
        {
            sleep(random.nextInt(waitingTime));
        }
        catch (InterruptedException e)
        {
        }
    }

    /**
     * Erfülle alle Coffman-Bedingungen.
     */
    public void fullfilCoffman()
    {
        // denken
        sleep();

        // essen
        leftFork.pickUp(color);
        rightFork.pickUp(color);
        sleep();
        leftFork.putDown();
        rightFork.putDown();
    }

    /**
     * Verletzte die Coffman-Bedingung 4 („Zyklisches Warten”)
     */
    public void violateCoffman4()
    {
        // denken
        sleep();

        // essen
        leftFork.pickUp(color);
        if (rightFork.id() > leftFork.id())
        {
            rightFork.pickUp(color);
        }
        else
        {
            leftFork.putDown();
            rightFork.putDown();
            leftFork.putDown();
        }
        sleep();
        leftFork.putDown();
        rightFork.putDown();
    }

    /**
     * Verletze die Coffman-Bedingung 2 („Halten und Warten“)
     */
    public void violateCoffman2()
    {
        // denken
        sleep();

        // essen
        leftFork.pickUp(color);
        if (rightFork.tryPickUp(color))
        {
            sleep();
            rightFork.putDown();
        }
        leftFork.putDown();
    }

    /**
     * Die Arbeitsmethode des Threads mit einer Endlosschleife: Der Philosoph
     * nimmt nach einer Zeit des Denkens die Gabeln auf, isst und legt sie dann
     * wieder ab.
     */
    @Override
    public void run()
    {
        while (true)
        {
            fullfilCoffman();
            // violateCoffman4();
            // violateCoffman2();
        }
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new DiningPhilosophers(), 950, 950);
    }
}
