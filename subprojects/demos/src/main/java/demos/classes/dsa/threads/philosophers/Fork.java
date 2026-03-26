package demos.classes.dsa.threads.philosophers;

import pi.Controller;
import pi.actor.Line;

/**
 * Die Gabel zwischen zwei Philosophen.
 *
 * @author Johannes Neumeyer
 * @author Josef Friedrich
 *
 * @version 1.0
 */
class Fork
{
    /**
     * Gibt an, ob die Gabel aktuell benutzt wird.
     */
    private boolean used;

    /**
     * Die ID der Gabel.
     */
    private int id;

    /**
     * Doe Darstellung der Gabel als Linie.
     */
    private Line line;

    /**
     * Konstruktor für Objekte der Klasse Gabel
     *
     * @param id Id der Gabel
     */
    Fork(int id, Line line)
    {
        super();
        used = false;
        this.line = line;
        this.id = id;
    }

    /**
     * Es wird gewartet, bis die Gabel nicht mehr in Benutzung ist; dann wird
     * sie aufgenommen.
     *
     * @param color Die Farbe des Philosophen, der die Gabel aufnehmen möchte;
     *     die Gabel wird dann auf diese Farbe gesetzt.
     */
    synchronized void pickUp(String color)
    {
        while (used)
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
            }
        }
        used = true;
        line.color(color);
    }

    /**
     * Es wird mit kurzen Wartepausen immer wieder versucht, eine Gabel
     * aufzunehmen. Gelingt dies nicht, wird die Methode nach maximal 100
     * Versuchen beendet.
     *
     * @param color Die Farbe des Philosophen, der die Gabel aufnehmen möchte;
     *     die Gabel wird bei erfolgreicher Aufnahme auf diese Farbe gesetzt.
     */
    synchronized boolean tryPickUp(String color)
    {
        int i = 0;
        while (i < 100)
        {
            if (!used)
            {
                used = true;
                line.color(color);
                return true;
            }
            else
            {
                try
                {
                    wait(10);
                }
                catch (InterruptedException e)
                {
                }
                i++;
            }
        }
        return false;
    }

    /**
     * Die Gabel wird abgelegt; da sie dann keinen Besitzer mehr hat, wird ihre
     * Farbe auf "schwarz" gesetzt.
     */
    synchronized void putDown()
    {
        used = false;
        line.color("schwarz");
        notify();
    }

    /**
     * Liefert die ID der Gabel.
     *
     * @return Die ID der Gabel.
     */
    int id()
    {
        return id;
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new DiningPhilosophers(), 950, 950);
    }
}
