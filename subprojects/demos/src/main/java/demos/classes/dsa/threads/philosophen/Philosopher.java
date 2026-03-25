package demos.classes.dsa.threads.philosophen;

import java.util.Random;

import pi.actor.Circle;

/**
 * Speisender Philosoph
 *
 * @author Johannes Neumeyer
 * @author Josef Friedrich
 *
 * @version 1.0
 */
@SuppressWarnings("unused")
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
     * Teller des Philosophen
     */
    private Circle plate;

    /**
     * Farbe des Tellers
     */
    private String plateColor;

    /**
     * Referenz auf die linke Gabel
     */
    private Fork leftFork;

    /**
     * Referenz auf die rechte Gabel
     */
    private Fork rightFork;

    /**
     * Zufallsgenerator
     */
    private Random random;

    /**
     * Konstruktor für Objekte der Klasse Philosoph
     *
     * @param philosopherId Id des Philosophen
     * @param plate der Teller, von dem der Philosoph speist
     * @param plateColor Farbe des Tellers
     * @param left die linke Gabel, die der Philosoph nutzt
     * @param right die rechte Gabel, die der Philosoph nutzt
     */
    Philosopher(int philosopherId, Circle plate, String plateColor, Fork left,
            Fork right)
    {
        waitingTime = 100;
        id = philosopherId;
        this.plate = plate;
        this.plateColor = plateColor;
        leftFork = left;
        rightFork = right;
        random = new Random();
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
            // denken
            try
            {
                sleep(random.nextInt(waitingTime));
            }
            catch (InterruptedException e)
            {
            }
            leftFork.pickUp(plateColor);
            rightFork.pickUp(plateColor);
            // essen
            try
            {
                sleep(random.nextInt(waitingTime));
            }
            catch (InterruptedException e)
            {
            }
            leftFork.putDown();
            rightFork.putDown();
        }
    }
}
