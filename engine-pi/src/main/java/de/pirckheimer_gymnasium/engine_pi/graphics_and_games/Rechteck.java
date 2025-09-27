package de.pirckheimer_gymnasium.engine_pi.graphics_and_games;

/**
 * Wrapperklasse für ein Rechteck auf der Zeichenfläche.
 *
 * @author Albert Wiedemann
 *
 * @version 1.0
 */
public class Rechteck
{
    /**
     * x-Position der linken oberen Ecke.
     */
    public int x;

    /**
     * y-Position der linken oberen Ecke.
     */
    public int y;

    /**
     * Breite des Rechtecks.
     */
    public int breite;

    /**
     * Höhe des Rechtecks.
     */
    public int höhe;

    /**
     * Farbe des Rechtecks.
     */
    public String farbe;

    /**
     * Sichtbarkeit des Rechtecks.
     */
    public boolean sichtbar;

    /**
     * Drehwinkel (mathematisch positiver Drehsinn) des Rechtecks in Grad.
     */
    public int winkel;

    /**
     * Referenz auf das Delegate-Objekt.
     */
    Zeichenfenster.GrafikSymbol symbol;

    /**
     * Der Konstruktor erzeugt das Delegate-Objekt
     */
    public Rechteck()
    {
        x = 10;
        y = 10;
        breite = 100;
        höhe = 100;
        farbe = "rot";
        sichtbar = true;
        winkel = 0;
        symbol = Zeichenfenster
                .SymbolErzeugen(Zeichenfenster.SymbolArt.rechteck);
        symbol.PositionSetzen(x, y);
        symbol.GrößeSetzen(breite, höhe);
        symbol.FarbeSetzen(farbe);
        symbol.SichtbarkeitSetzen(sichtbar);
        symbol.WinkelSetzen(winkel);
    }

    /**
     * Setzt die Position (der linken oberen Ecke) des Rechtecks.
     *
     * @param x x-Position der linken oberen Ecke
     * @param y y-Position der linken oberen Ecke
     */
    public void PositionSetzen(int x, int y)
    {
        this.x = x;
        this.y = y;
        symbol.PositionSetzen(x, y);
    }

    /**
     * Verschiebt das Rechteck um die angegebenen Werte.
     *
     * @param deltaX Verschiebung in x-Richtung
     * @param deltaY Verschiebung in y-Richtung
     */
    public void Verschieben(int deltaX, int deltaY)
    {
        x += deltaX;
        y += deltaY;
        symbol.PositionSetzen(x, y);
    }

    /**
     * Dreht das Rechteck
     *
     * @param grad Drehwinkel (mathematisch positiver Drehsinn) im Gradmass
     */
    public void Drehen(int grad)
    {
        winkel += grad;
        symbol.WinkelSetzen(winkel);
    }

    /**
     * Setzt die Größe des Rechtecks.
     *
     * @param breite (neue) Breite
     * @param höhe (neue) Höhe
     */
    public void GrößeSetzen(int breite, int höhe)
    {
        this.breite = breite;
        this.höhe = höhe;
        symbol.GrößeSetzen(breite, höhe);
    }

    /**
     * Setzt die Farbe des Rechtecks. Erlaubte Farben sind: "weiß", "weiss",
     * "rot", "grün", "gruen", "blau", "gelb", "magenta", "cyan", "hellgelb",
     * "hellgrün", "hellgruen", "orange", "braun", "grau", "schwarz" Alle
     * anderen Eingaben werden auf die Farbe schwarz abgebildet.
     *
     * @param farbe (neue) Farbe
     */
    public void FarbeSetzen(String farbe)
    {
        this.farbe = farbe;
        symbol.FarbeSetzen(farbe);
    }

    /**
     * Setzt den Drehwinkel des Rechtecks. Die Winkelangabe ist in Grad,positive
     * Werte drehen gegen den Uhrzeigersinn, negative Werte drehen im
     * Uhrzeigersinn (mathematisch positiver Drehsinn).
     *
     * @param winkel der (neue) Drehwinkel des Rechtecks
     */
    public void WinkelSetzen(int winkel)
    {
        this.winkel = winkel;
        symbol.WinkelSetzen(winkel);
    }

    /**
     * Schaltet die Sichtbarkeit des Rechtecks ein oder aus. Erlaubte
     * Parameterwerte: true, false
     *
     * @param sichtbar (neue) Sichtbarkeit des Rechtecks
     */
    public void SichtbarkeitSetzen(boolean sichtbar)
    {
        this.sichtbar = sichtbar;
        symbol.SichtbarkeitSetzen(sichtbar);
    }

    /**
     * Entfernt das Rechteck aus dem Zeichenfenster.
     */
    public void Entfernen()
    {
        symbol.Entfernen();
    }

    /**
     * Bringt das Rechteck eine Ebene nach vorn.
     */
    public void NachVornBringen()
    {
        symbol.NachVornBringen();
    }

    /**
     * Bringt das Rechteck in die vorderste Ebene.
     */
    public void GanzNachVornBringen()
    {
        symbol.GanzNachVornBringen();
    }

    /**
     * Bringt das Rechteck eine Ebene nach hinten.
     */
    public void NachHintenBringen()
    {
        symbol.NachHintenBringen();
    }

    /**
     * Bringt das Rechteck in die hinterste Ebene.
     */
    public void GanzNachHintenBringen()
    {
        symbol.GanzNachHintenBringen();
    }
}
