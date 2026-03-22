package demos.classes.dsa.threads.eierautomat;

import pi.Image;
import java.util.Random;

import pi.Scene;
import pi.actor.Counter;

/**
 * Bäuerin, die einen Eierautomaten betreibt
 *
 * @author Johannes Neumeyer
 * @author Josef Friedrich
 *
 * @version 1.0
 */
class Baeuerin extends Thread
{
    /**
     * Zufallsgenertator zum Erzeugen einer zufälligen Wartezeit
     */
    Random zufallsgenerator;

    /**
     * Zähler für die Anzahl der Zugriffsversuche
     */
    Counter versuche;

    /**
     * Zähler für die Anzahl der Zugriffsversuche
     */
    Counter vergeblicheVersuche;

    /**
     * der zu verwendene Eierautomat
     */
    Eierautomat automat;

    /**
     * Konstruktor für Objekte der Klasse Baeuerin
     *
     * @param eierautomat der zu verwendene Eierautomat
     */
    Baeuerin(Scene scene, Eierautomat eierautomat)
    {
        automat = eierautomat;
        zufallsgenerator = new Random();

        // Bild der Bäuerin
        scene.add(
            new Image("eierautomat/baeuerin.png").size(6, 6).center(-7, 3));

        // Zähler aller Befüllbesuche
        versuche = new Counter().suffix(". Befüllbesuch");
        versuche.anchor(-11, -2);
        scene.add(versuche);

        // Zähler vergebliche Befüllbesuche
        vergeblicheVersuche = new Counter()
            .suffix(". vergeblicher Befüllbesuch");
        vergeblicheVersuche.height(0.8).color("green").anchor(-11, -4);
        scene.add(vergeblicheVersuche);
    }

    /**
     * Die Bäuerin versucht in unregelmäßigen Zeitabständen, den Eierautomaten
     * wieder neu zu befüllen.
     */
    @Override
    public void run()
    {
        while (true)
        {
            // Automatenbefüllversuch
            boolean erfolgreich = automat.befülle();
            if (!erfolgreich)
            {
                vergeblicheVersuche.increase();
                vergeblicheVersuche.color("red");
            }

            versuche.increase();

            // Simulation der Zeitdauer zwischen zwei Befüllversuchen
            try
            {
                // Zufallszahl aus dem Bereich [500; 4500[
                sleep(500 + zufallsgenerator.nextInt(4000));
            }
            catch (InterruptedException e)
            {
            }
        }
    }
}
