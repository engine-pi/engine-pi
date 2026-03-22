package demos.classes.dsa.threads.eierautomat;

import pi.Scene;

/**
 * Besserer Eierautomat auf dem Lande
 *
 * @author Johannes Neumeyer
 * @author Josef Friedrich
 *
 * @version 1.0
 */
class BessererEierautomat extends Eierautomat
{
    /**
     * Besetzt die Attribute vor.
     */
    BessererEierautomat(Scene scene)
    {
        super(scene);
    }

    /**
     * Befüllen des Eierautomaten mit neuen Eierkartons
     */
    @Override
    synchronized boolean befülle()
    {
        // So lange der Automat noch Eierkartons enthält, muss mit dem Befüllen
        // gewartet werden.
        while (eierkartons.size() > 0)
        {
            try
            {
                wait(); // Der Thread wechselt in einen Wartezustand.
            }
            catch (InterruptedException e)
            {
            }
        }

        // Der leere Automat wird mit zehn neuen Kartons befüllt.
        for (int i = 0; i < 10; i++)
        {
            fügeEierkartonHinzu(i);
        }

        // Der Zustand der Variable in der Wartebedingung hat sich verändert.
        // Ein wartender Thread wird benachrichtigt.
        notify();
        return true;
    }

    /**
     * Holen eines Eierkartons aus dem Automaten
     *
     * @return Eierkarton oder null bei Fehlversuch
     */
    @Override
    synchronized Eierkarton holeEier()
    {
        // solange eine bestimmte Bedingung gilt, müssen Abholer abwarten
        // Die Bedingung um die folgenden Anweisungen könnte dann
        // entfernt werden, ebenso die Rückgabe der leeren Referenz.
        while (eierkartons.size() == 0)
        {
            try
            {
                wait(); // Der Thread wechselt in einen Wartezustand.
            }
            catch (InterruptedException e)
            {
            }
        }

        Eierkarton gekaufterKarton = entferneEierkarton();

        // Unter einer bestimmten Bedingung muss die Bäuerin informiert
        // werden
        if (eierkartons.size() == 0)
        {
            notify();
        }

        return gekaufterKarton;
    }
}
