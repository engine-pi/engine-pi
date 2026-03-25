package demos.classes.dsa.threads.philosophen;

import pi.actor.Line;

/**
 * Gabel zwischen zwei Philosophen
 *
 * @author Johannes Neumeyer
 * @author Josef Friedrich
 *
 * @version 1.0
 */
class Fork
{
    /**
     * gibt an, ob die Gabel aktuell genutzt wird
     */
    private boolean used;

    /**
     * Id der Gabel
     */
    private int id;

    /**
     * Darstellung der Gabel
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
     * Liefert die Id der Gabel.
     *
     * @return Id der Gabel
     */
    int id()
    {
        return id;
    }
}
