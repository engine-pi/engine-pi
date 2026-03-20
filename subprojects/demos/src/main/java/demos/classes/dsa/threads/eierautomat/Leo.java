package demos.classes.dsa.threads.eierautomat;

import java.util.Random;

import pi.Scene;
import pi.actor.Counter;

/**
 * Leo, ein extremer Eierkonsument
 *
 * @author Johannes Neumeyer
 * @author Josef Friedrich
 *
 * @version 1.0
 */
class Leo extends Thread
{
    /**
     * Zufallsgenertator zum Erzeugen einer zufälligen Wartezeit
     */
    Random zufallsgenerator;

    /**
     * Zähler für die Anzahl der Zugriffsversuche
     */
    Counter versuche;

    Counter vergeblicheVersuche;

    /**
     * der zu verwendene Eierautomat
     */
    Eierautomat automat;

    /**
     * Konstruktor für Objekte der Klasse Leo
     *
     * @param eierautomat der zu verwendene Eierautomat
     */
    Leo(Scene scene, Eierautomat eierautomat)
    {
        automat = eierautomat;
        zufallsgenerator = new Random();

        versuche = new Counter().suffix(". Eierholbesuch");
        versuche.anchor(3, 2);

        vergeblicheVersuche = new Counter()
            .suffix(". vergeblicher Eierholbesuch");
        vergeblicheVersuche.height(0.8).anchor(3, 0).color("rot").update();

        scene.add(versuche, vergeblicheVersuche);
    }

    /**
     * Leo versucht in unregelmäßigen Zeitabständen, einen Eierkarton aus dem
     * Automaten zu holen.
     */
    @Override
    public void run()
    {
        while (true)
        {
            // Eierholversuch
            Eierkarton karton = automat.holeEier();

            versuche.increase();

            if (karton == null)
            {
                vergeblicheVersuche.increase();
            }

            // Simulation der Zeitdauer zwischen zwei Eierholversuchen
            try
            {
                // Zufallszahl aus dem Bereich [10; 160[
                sleep(10 + zufallsgenerator.nextInt(150));
            }
            catch (InterruptedException e)
            {
            }
        }
    }
}
