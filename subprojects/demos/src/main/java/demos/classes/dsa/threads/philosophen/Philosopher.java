package demos.classes.dsa.threads.philosophen;

import java.util.Random;

/**
 * Speisender Philosoph
 *
 * @author Johannes Neumeyer
 * @author Josef Friedrich
 *
 * @version 1.0
 */
class Philosopher extends Thread
{
    /**
     * Id des Philosophen
     */
    private int id;

    /**
     * Zeitangabe in ms als Grundlage für die Bestimmung zufälliger Ess- und
     * Wartezeiten
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
     * Konstruktor für Objekte der Klasse Philosoph
     *
     * @param philosopherId Id des Philosophen
     * @param plateColor Farbe des Tellers
     * @param left die linke Gabel, die der Philosoph nutzt
     * @param right die rechte Gabel, die der Philosoph nutzt
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

    private void sleepRandomly(int milliseconds)
    {
        try
        {
            sleep(random.nextInt(milliseconds));
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
        sleepRandomly(waitingTime);
        leftFork.pickUp(plateColor);
        rightFork.pickUp(plateColor);

        // essen
        sleepRandomly(waitingTime);
        leftFork.putDown();
        rightFork.putDown();
    }

    /**
     * Verletzte die Coffman-Bedingung 4 („Zyklisches Warten”)
     */
    public void violateCoffman4()
    {
        // denken
        sleepRandomly(waitingTime);
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

        // essen
        sleepRandomly(waitingTime);
        leftFork.putDown();
        rightFork.putDown();
    }

    /**
     * Verletze die Coffman-Bedingung 2 („Halten und Warten“)
     */
    public void violateCoffmann2()
    {
        // denken
        sleepRandomly(waitingTime);
        leftFork.pickUp(plateColor);

        if (rightFork.tryPickUp(plateColor))
        {
            // essen
            sleepRandomly(waitingTime);
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
            // violateCoffmann2();
        }
    }
}
