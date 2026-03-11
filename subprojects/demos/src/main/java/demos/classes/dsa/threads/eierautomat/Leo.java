package demos.classes.dsa.threads.eierautomat;

import java.util.Random;

import pi.Scene;
import pi.Text;

/**
 * Leo, ein extremer Eierkonsument
 *
 * @author Johannes Neumeyer
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
    int anzahlVersuche;

    /**
     * Textanzeige
     */
    Text ausgabeAnna;

    /**
     * der zu verwendene Eierautomat
     */
    Eierautomat automat;

    /**
     * Konstruktor für Objekte der Klasse Anna
     *
     * @param eierautomat der zu verwendene Eierautomat
     */
    Leo(Scene scene, Eierautomat eierautomat)
    {
        super();
        automat = eierautomat;
        zufallsgenerator = new Random();
        ausgabeAnna = new Text("");
        ausgabeAnna.center(5.5, 2);
        scene.add(ausgabeAnna);
        anzahlVersuche = 0;
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
            // Textausgabe
            anzahlVersuche += 1;
            ausgabeAnna.content(anzahlVersuche + ". Eierholbesuch");

            // Eierholversuch
            automat.EierHolen();

            // Simulation der Zeitdauer zwischen zwei Eierholversuchen
            try
            {
                sleep(zufallsgenerator.nextInt(200));// Zufallszahl aus dem
                                                     // Bereich [0; 200[
            }
            catch (InterruptedException e)
            {
            }
        }
    }
}
