package demos.classes.dsa.threads.eierautomat;

import java.util.Random;

import pi.Scene;
import pi.Text;

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
    int anzahlVersuche;

    /**
     * Textanzeige
     */
    Text text;

    int anzahlVergeblicheVersuche;

    Text textVergeblich;

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

        text = new Text("");
        text.center(3, 2);

        textVergeblich = new Text("");
        textVergeblich.height(0.8).center(3, 0).color("rot");

        scene.add(text, textVergeblich);
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
            text.content(anzahlVersuche + ". Eierholbesuch");

            // Eierholversuch
            Eierkarton karton = automat.holeEier();

            if (karton == null)
            {
                anzahlVergeblicheVersuche++;
                textVergeblich.content(
                    anzahlVergeblicheVersuche + ". vergeblicher Eierholbesuch");
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
