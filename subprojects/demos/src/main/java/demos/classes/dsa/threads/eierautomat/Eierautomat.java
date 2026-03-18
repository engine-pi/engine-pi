package demos.classes.dsa.threads.eierautomat;

import java.util.ArrayList;

import pi.Rectangle;
import pi.Scene;

/**
 * Eierautomat auf dem Lande
 *
 * @author Johannes Neumeyer
 *
 * @version 1.0
 */
class Eierautomat
{
    /**
     * Feld, das die Eierkartons im Automaten verwaltet
     */
    protected ArrayList<Eierkarton> eierkartons;

    Scene scene;

    /**
     * Besetzt die Attribute vor.
     */
    Eierautomat(Scene scene)
    {
        Rectangle automat = new Rectangle();
        automat.size(2, 8);
        automat.color("grau");
        automat.center(0, 0);
        eierkartons = new ArrayList<Eierkarton>();
        this.scene = scene;
        scene.add(automat);
    }

    /**
     * Befüllen des Eierautomaten mit neuen Eierkartons
     */
    synchronized void Befüllen()
    {
        if (eierkartons.size() == 0)
        {
            // Der leere Automat wird mit zehn neuen Kartons befüllt.
            for (int zähler = 0; zähler < 10; zähler++)
            {
                Eierkarton eierkarton = new Eierkarton();
                eierkarton.center(0, 0 - 0.4 * zähler);
                scene.add(eierkarton);
                eierkartons.add(eierkarton);
                eierkartons.get(zähler);
            }
        }
    }

    /**
     * Holen eines Eierkartons aus dem Automaten
     *
     * @return Eierkarton oder null bei Fehlversuch
     */
    synchronized Eierkarton EierHolen()
    {
        if (eierkartons.size() > 0)
        {
            // Ein Eierkarton wird aus dem Feld entfernt und seine Darstellung
            // aus dem Zeichenfenster
            Eierkarton gekaufterKarton = eierkartons.remove(0);
            scene.remove(gekaufterKarton);

            // Rueckgabe des gekauften Kartons
            return gekaufterKarton;
        }
        return null;
    }
}
