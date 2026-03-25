package demos.classes.dsa.threads.philosophen;

import pi.Scene;
import pi.actor.Rectangle;

/**
 * Gabel zwischen zwei Philosophen
 *
 * @author Johannes Neumeyer
 * @author Josef Friedrich
 *
 * @version 1.0
 */
class Gabel
{
    /**
     * gibt an, ob die Gabel aktuell genutzt wird
     */
    private boolean inBenutzung;

    /**
     * Id der Gabel
     */
    private int id;

    /**
     * Darstellung der Gabel
     */
    private Rectangle symbol;

    /**
     * Konstruktor für Objekte der Klasse Gabel
     *
     * @param gabelId Id der Gabel
     */
    Gabel(Scene scene, int gabelId)
    {
        super();
        inBenutzung = false;
        symbol = new Rectangle(1, 2);
        symbol.color("schwarz");
        scene.add(symbol);
        id = gabelId;
    }

    /**
     * Es wird gewartet, bis die Gabel nicht mehr in Benutzung ist; dann wird
     * sie aufgenommen.
     *
     * @param eigeneFarbe Die Farbe des Philosophen, der die Gabel aufnehmen
     *     möchte; die Gabel wird dann auf diese Farbe gesetzt.
     */
    synchronized void Aufnehmen(String eigeneFarbe)
    {
        while (inBenutzung)
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
            }
        }
        inBenutzung = true;
        symbol.color(eigeneFarbe);
    }

    /**
     * Die Gabel wird abgelegt; da sie dann keinen Besitzer mehr hat, wird ihre
     * Farbe auf "schwarz" gesetzt.
     */
    synchronized void Ablegen()
    {
        inBenutzung = false;
        symbol.color("schwarz");
        notify();
    }

    /**
     * Liefert die Id der Gabel.
     *
     * @return Id der Gabel
     */
    int IdGeben()
    {
        return id;
    }

    /**
     * Liefert das Symbol der Gabel.
     *
     * @return Symbol der Gabel
     */
    Rectangle SymbolGeben()
    {
        return symbol;
    }
}
