package de.pirckheimer_gymnasium.engine_pi.little_engine;

/**
 * <p>
 * Zellen als Bestandteile der Welt, in der sich die Figuren bewegen können.
 * Jede Zelle hat die Größe 46x46 Pixel. Beachte Ränder um die Zellen, bei der
 * Anordnung in der Welt!
 * </p>
 *
 * <p>
 * Diese Klasse ist nicht im ursprünglichen GraphicsAndGames-Paket enthalten.
 * Sie wird aber in mehreren Projekten verwendet.
 * </p>
 *
 * <p>
 * Der ursprüngliche Name der Klasse war {@code Zelle}.
 * </p>
 *
 * @author Peter Brichzin
 *
 * @version 1.0
 */
public class Cell extends Rectangle
{
    /**
     * Erzeugt und platziert eine Zelle in der gewünschten Farbe.
     *
     * @param xLinksOben x-Wert der Ecke links oben der Zelle
     * @param yLinksOben y-Wert der Ecke links oben der Zelle
     * @param farbeNeu Farbe der Zelle
     */
    public Cell(int xLinksOben, int yLinksOben, String farbeNeu)
    {
        super();
        PositionSetzen(xLinksOben, yLinksOben);
        GrößeSetzen(46, 46);
        FarbeSetzen(farbeNeu);
    }
}
