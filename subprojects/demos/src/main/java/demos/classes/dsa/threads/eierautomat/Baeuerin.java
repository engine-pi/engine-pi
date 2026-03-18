package demos.classes.dsa.threads.eierautomat;

import java.util.Random;

import pi.Scene;
import pi.Text;

/**
 * Bäuerin, die einen Eierautomaten betreibt
 *
 * @author Johannes Neumeyer
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
    Text ausgabeBäuerin;

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
        super();
        automat = eierautomat;
        zufallsgenerator = new Random();
        ausgabeBäuerin = new Text("");
        ausgabeBäuerin.center(-10, 2);
        anzahlVersuche = 0;
        scene.add(ausgabeBäuerin);
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
            ausgabeBäuerin.content(anzahlVersuche + ". Befüllbesuch");

            // Automatenbefüllversuch
            automat.Befüllen();

            // Simulation der Zeitdauer zwischen zwei Befüllversuchen
            try
            {
                sleep(zufallsgenerator.nextInt(4000)); // Zufallszahl aus dem
                                                       // Bereich [0; 4000[
            }
            catch (InterruptedException e)
            {
            }
        }
    }
}
