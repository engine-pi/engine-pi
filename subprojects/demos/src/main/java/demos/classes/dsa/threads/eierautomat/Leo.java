package demos.classes.dsa.threads.eierautomat;

import java.util.Random;

import pi.Scene;
import pi.actor.Counter;
import pi.Image;

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

        // Bild von Leo
        scene.add(new Image("eierautomat/leo.png").size(4, 4).center(6, 3));

        // Zähler aller Eierholbesuche
        versuche = new Counter().suffix(". Eierholbesuch");
        versuche.anchor(3, -2);
        scene.add(versuche);

        // Zähler der vergeblichen Eierholbesuche
        vergeblicheVersuche = new Counter()
            .suffix(". vergeblicher Eierholbesuch");
        vergeblicheVersuche.height(0.8).anchor(3, -4).color("green");
        scene.add(vergeblicheVersuche);
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
                vergeblicheVersuche.color("red");
            }

            // Simulation der Zeitdauer zwischen zwei Eierholversuchen
            try
            {
                // Zufallszahl aus dem Bereich [50; 250[
                sleep(50 + zufallsgenerator.nextInt(200));
            }
            catch (InterruptedException e)
            {
            }
        }
    }
}
