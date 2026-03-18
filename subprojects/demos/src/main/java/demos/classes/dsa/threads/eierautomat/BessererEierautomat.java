package demos.classes.dsa.threads.eierautomat;

import pi.Scene;

/**
 * Besserer Eierautomat auf dem Lande
 *
 * @author Johannes Neumeyer
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
    synchronized void Befüllen()
    {
        // So lange der Automat noch Eierkartons enthält, muss mit dem Befüllen
        // gewartet werden.
        if (eierkartons.size() > 0)
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
        for (int zähler = 0; zähler < 10; zähler++)
        {
            Eierkarton eierkarton = new Eierkarton();
            eierkarton.center(0, 0 - 0.4 * zähler);
            scene.add(eierkarton);
            eierkartons.add(eierkarton);
            eierkartons.get(zähler);
        }

        // Der Zustand der Variable in der Wartebedingung hat sich verändert.
        // Ein wartender Thread wird benachrichtigt.
        notify();
    }

    /**
     * Holen eines Eierkartons aus dem Automaten
     *
     * @return Eierkarton oder null bei Fehlversuch
     */
    @Override
    synchronized Eierkarton EierHolen()
    {
        // solange eine bestimmte Bedingung gilt, müssen Abholer abwarten
        if (eierkartons.size() == 0)
        {
            try
            {
                wait(); // Der Thread wechselt in einen Wartezustand.
            }
            catch (InterruptedException e)
            {
            }
        }

        // Ein Eierkarton wird aus dem Feld entfernt und seine Darstellung
        // aus dem Zeichenfenster
        Eierkarton gekaufterKarton = eierkartons.remove(0);
        scene.remove(gekaufterKarton);

        if (eierkartons.size() == 0)
        {
            notify();
        }

        // Rueckgabe des gekauften Kartons
        return gekaufterKarton;
    }
}
