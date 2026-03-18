package demos.classes.dsa.threads.eierautomat;

import java.util.Random;

import pi.Scene;
import pi.Text;

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
    int anzahlVersuche;

    /**
     * Textanzeige
     */
    Text text;

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
        text = new Text("");
        text.center(-10, 2);
        anzahlVersuche = 0;
        scene.add(text);
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
            // Textausgabe
            anzahlVersuche += 1;
            text.content(anzahlVersuche + ". Befüllbesuch");

            // Automatenbefüllversuch
            automat.befülle();

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
