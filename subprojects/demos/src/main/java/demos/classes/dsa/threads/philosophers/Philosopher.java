package demos.classes.dsa.threads.philosophers;

import java.util.Random;

import pi.Controller;

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
    private String plateColor;

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
     * @param philosopherId Die ID des Philosophen.
     * @param plateColor Die Farbe des Tellers.
     * @param left Die linke Gabel, die der Philosoph nutzt.
     * @param right Die rechte Gabel, die der Philosoph nutzt.
     */
    Philosopher(int philosopherId, String plateColor, Fork left, Fork right)
    {
        waitingTime = 50;
        id = philosopherId;
        this.plateColor = plateColor;
        leftFork = left;
        rightFork = right;
        random = new Random();
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
        leftFork.pickUp(plateColor);
        rightFork.pickUp(plateColor);
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
        leftFork.pickUp(plateColor);
        if (rightFork.id() > leftFork.id())
        {
            rightFork.pickUp(plateColor);
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
        leftFork.pickUp(plateColor);
        if (rightFork.tryPickUp(plateColor))
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
        Controller.start(new DiningPhilosophers());
    }
}
