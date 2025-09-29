package de.pirckheimer_gymnasium.engine_pi.little_engine;

/**
 * Wrapperklasse für ein Dreieck auf der Zeichenfläche.
 *
 * <p>
 * Der ursprüngliche Name der Klasse war {@code Dreieck}.
 * </p>
 *
 * @author Albert Wiedemann
 *
 * @version 1.0
 */
public class Triangle
{
    /**
     * x-Position der Spitze.
     */
    public int x;

    /**
     * y-Position der Spitze.
     */
    public int y;

    /**
     * Breite des umgebenden Rechtecks.
     */
    public int breite;

    /**
     * Höhe des umgebenden Rechtecks.
     */
    public int höhe;

    /**
     * Farbe des Dreiecks.
     */
    public String farbe;

    /**
     * Sichtbarkeit des Dreiecks.
     */
    public boolean sichtbar;

    /**
     * Drehwinkel (mathematisch positiver Drehsinn) des Dreiecks in Grad.
     */
    public int winkel;

    /**
     * Referenz auf das Delegate-Objekt.
     */
    DrawingWindow.GrafikSymbol symbol;

    /**
     * Der Konstruktor erzeugt das Delegate-Objekt
     */
    public Triangle()
    {
        x = 60;
        y = 10;
        breite = 100;
        höhe = 100;
        farbe = "rot";
        sichtbar = true;
        winkel = 0;
        symbol = DrawingWindow.SymbolErzeugen(DrawingWindow.SymbolArt.dreieck);
        symbol.setPosition(x - breite / 2, y);
        symbol.setSize(breite, höhe);
        symbol.setColor(farbe);
        symbol.setVisibility(sichtbar);
        symbol.setRotation(winkel);
    }

    /**
     * Setzt die Position (der Spitze) des Dreiecks.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code PositionSetzen}.
     * </p>
     *
     * @param x x-Position der Spitze
     * @param y y-Position der Spitze
     */
    public void PositionSetzen(int x, int y)
    {
        this.x = x;
        this.y = y;
        symbol.setPosition(x - breite / 2, y);
    }

    /**
     * Verschiebt das Dreieck um die angegebenen Werte.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code Verschieben}.
     * </p>
     *
     * @param deltaX Verschiebung in x-Richtung
     * @param deltaY Verschiebung in y-Richtung
     */
    public void Verschieben(int deltaX, int deltaY)
    {
        x += deltaX;
        y += deltaY;
        symbol.setPosition(x - breite / 2, y);
    }

    /**
     * Dreht das Dreieck
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code Drehen}.
     * </p>
     *
     * @param grad Drehwinkel (mathematisch positiver Drehsinn) im Gradmass
     */
    public void Drehen(int grad)
    {
        winkel += grad;
        symbol.setRotation(winkel);
    }

    /**
     * Setzt die Größe des Dreiecks.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code GrößeSetzen}.
     * </p>
     *
     * @param breite (neue) Breite
     * @param höhe (neue) Höhe
     */
    public void GrößeSetzen(int breite, int höhe)
    {
        this.breite = breite;
        this.höhe = höhe;
        symbol.setSize(breite, höhe);
        symbol.setPosition(x - breite / 2, y);
    }

    /**
     * Setzt die Farbe des Dreiecks. Erlaubte Farben sind: "weiß", "weiss",
     * "rot", "grün", "gruen", "blau", "gelb", "magenta", "cyan", "hellgelb",
     * "hellgrün", "hellgruen", "orange", "braun", "grau", "schwarz" Alle
     * anderen Eingaben werden auf die Farbe schwarz abgebildet.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code FarbeSetzen}.
     * </p>
     *
     * @param farbe (neue) Farbe
     */
    public void FarbeSetzen(String farbe)
    {
        this.farbe = farbe;
        symbol.setColor(farbe);
    }

    /**
     * Setzt den Drehwinkel des Dreiecks. Die Winkelangabe ist in Grad,positive
     * Werte drehen gegen den Uhrzeigersinn, negative Werte drehen im
     * Uhrzeigersinn (mathematisch positiver Drehsinn).
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code WinkelSetzen}.
     * </p>
     *
     * @param winkel der (neue) Drehwinkel des Dreiecks
     */
    public void WinkelSetzen(int winkel)
    {
        this.winkel = winkel;
        symbol.setRotation(winkel);
    }

    /**
     * Schaltet die Sichtbarkeit des Dreiecks ein oder aus. Erlaubte
     * Parameterwerte: true, false
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code SichtbarkeitSetzen}.
     * </p>
     *
     * @param sichtbar (neue) Sichtbarkeit des Dreiecks
     */
    public void SichtbarkeitSetzen(boolean sichtbar)
    {
        this.sichtbar = sichtbar;
        symbol.setVisibility(sichtbar);
    }

    /**
     * Entfernt das Dreieck aus dem Zeichenfenster.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code Entfernen}.
     * </p>
     */
    public void Entfernen()
    {
        symbol.remove();
    }

    /**
     * Bringt das Dreieck eine Ebene nach vorn.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code NachVornBringen}.
     * </p>
     */
    public void NachVornBringen()
    {
        symbol.raiseLayer();
    }

    /**
     * Bringt das Dreieck in die vorderste Ebene.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code GanzNachVornBringen}.
     * </p>
     */
    public void GanzNachVornBringen()
    {
        symbol.bringToFront();
    }

    /**
     * Bringt das Dreieck eine Ebene nach hinten.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code NachHintenBringen}.
     * </p>
     */
    public void NachHintenBringen()
    {
        symbol.lowerLayer();
    }

    /**
     * Bringt das Dreieck in die hinterste Ebene.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code GanzNachHintenBringen}.
     * </p>
     */
    public void GanzNachHintenBringen()
    {
        symbol.bringToBack();
    }
}
