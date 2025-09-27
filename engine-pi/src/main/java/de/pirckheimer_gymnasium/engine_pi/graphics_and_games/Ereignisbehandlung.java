package de.pirckheimer_gymnasium.engine_pi.graphics_and_games;

/**
 * Zugriff auf die Ereignisse einschließlich Taktgeber.
 *
 * @author Albert Wiedemann
 *
 * @version 1.0
 */
public class Ereignisbehandlung
{
    /**
     * Der Konstruktor meldet den Taktgeber und die Eventlistener bei der
     * Zeichenfläche an.
     */
    public Ereignisbehandlung()
    {
        Zeichenfenster.AktionsEmpfängerEintragen(
                new Zeichenfenster.AktionsEmpfaenger()
                {
                    public void Ausführen()
                    {
                        TaktImpulsAusführen();
                    }

                    public void Taste(char taste)
                    {
                        TasteGedrückt(taste);
                    }

                    public void SonderTaste(int taste)
                    {
                        SonderTasteGedrückt(taste);
                    }

                    public void Geklickt(int x, int y, int anzahl)
                    {
                        MausGeklickt(x, y, anzahl);
                    }
                });
    }

    /**
     * Die eigentliche Aktionsmethode des Zeitgebers. <br>
     * Muss bei Bedarf von einer Unterklasse überschrieben werden.
     */
    public void TaktImpulsAusführen()
    {
        System.out.println("Tick");
    }

    /**
     * Zeitgeber starten.
     */
    public void Starten()
    {
        Zeichenfenster.TaktgeberStarten();
    }

    /**
     * Zeitgeber anhalten.
     */
    public void Anhalten()
    {
        Zeichenfenster.TaktgeberStoppen();
    }

    /**
     * Ablaufgeschwindigkeit des Zeitgebers einstellen.
     *
     * @param dauer: Angabe in Millisekunden
     */
    public void TaktdauerSetzen(int dauer)
    {
        Zeichenfenster.TaktdauerSetzen(dauer);
    }

    /**
     * Die eigentliche Aktionsmethode für gedrückte Tasten. <br>
     * Muss bei Bedarf von einer Unterklasse überschrieben werden.
     *
     * @param taste die gedrückte Taste
     */
    public void TasteGedrückt(char taste)
    {
        System.out.println("Taste: " + taste);
    }

    /**
     * Die eigentliche Aktionsmethode für gedrückte Sondertasten. <br>
     * Muss bei Bedarf von einer Unterklasse überschrieben werden.
     *
     * @param taste KeyCode der gedrückten Taste
     */
    public void SonderTasteGedrückt(int taste)
    {
        System.out.println("Sondertaste: " + taste);
    }

    /**
     * Die eigentliche Aktionsmethode für einen Mausklick. <br>
     * Muss bei Bedarf von einer Unterklasse überschrieben werden.
     *
     * @param x x-Position des Mausklicks
     * @param y y-Position des Mausklicks
     * @param anzahl Anzahl der aufeinanderfolgenden Mausklicks
     */
    public void MausGeklickt(int x, int y, int anzahl)
    {
        System.out.println("Maus: (" + x + "|" + y + "), " + anzahl + " mal");
    }
}
