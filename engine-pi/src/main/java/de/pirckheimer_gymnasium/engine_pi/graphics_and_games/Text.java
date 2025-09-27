package de.pirckheimer_gymnasium.engine_pi.graphics_and_games;

/**
 * Wrapperklasse für einen Text auf der Zeichenfläche.
 *
 * @author Albert Wiedemann
 *
 * @version 1.0
 */
public class Text
{
    /**
     * x-Position der linken Seite der Grundlinie.
     */
    public int x;

    /**
     * y-Position der Grundlinie.
     */
    public int y;

    /**
     * Farbe des Textes.
     */
    public String farbe;

    /**
     * Sichtbarkeit des Textes.
     */
    public boolean sichtbar;

    /**
     * Drehwinkel (mathematisch positiver Drehsinn) des Textes in Grad.
     */
    public int winkel;

    /**
     * Größe des Textes in Punkten.
     */
    public int textgröße;

    /**
     * Referenz auf das Delegate-Objekt.
     */
    public Zeichenfenster.TextIntern symbol;

    /**
     * Der Konstruktor erzeugt das Delegate-Objekt
     */
    public Text()
    {
        x = 10;
        y = 10;
        farbe = "schwarz";
        sichtbar = true;
        winkel = 0;
        textgröße = 12;
        symbol = (Zeichenfenster.TextIntern) Zeichenfenster
                .SymbolErzeugen(Zeichenfenster.SymbolArt.text);
        symbol.PositionSetzen(x, y);
        symbol.FarbeSetzen(farbe);
        symbol.SichtbarkeitSetzen(sichtbar);
        symbol.WinkelSetzen(winkel);
        symbol.TextGrößeSetzen(textgröße);
    }

    /**
     * Setzt die Position (der Grundline) des Textes.
     *
     * @param x x-Position der linken Seite der Grundlinie
     * @param y y-Position der Grundlinie
     */
    public void PositionSetzen(int x, int y)
    {
        this.x = x;
        this.y = y;
        symbol.PositionSetzen(x, y);
    }

    /**
     * Setzt den aktuellen Text.
     *
     * @param text der neue Text
     */
    public void TextSetzen(String text)
    {
        symbol.TextSetzen(text);
    }

    /**
     * Setzt die Größe des Textes.
     *
     * @param größe die (neue) Textgröße
     */
    public void TextGrößeSetzen(int größe)
    {
        textgröße = größe;
        symbol.TextGrößeSetzen(größe);
    }

    /**
     * Vergrößert den Text.
     */
    public void TextVergrößern()
    {
        symbol.TextVergrößern();
        textgröße = (int) symbol.size;
    }

    /**
     * Verkleinert den Text.
     */
    public void TextVerkleinern()
    {
        symbol.TextVerkleinern();
        textgröße = (int) symbol.size;
    }

    /**
     * Verschiebt den Text um die angegebenen Werte.
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
     * Dreht den Text
     *
     * @param grad Drehwinkel (mathematisch positiver Drehsinn) im Gradmass
     */
    public void Drehen(int grad)
    {
        winkel += grad;
        symbol.WinkelSetzen(winkel);
    }

    /**
     * Setzt die Farbe des Textes. Erlaubte Farben sind: "weiß", "weiss", "rot",
     * "grün", "gruen", "blau", "gelb", "magenta", "cyan", "hellgelb",
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
     * Setzt den Drehwinkel des Textes. Die Winkelangabe ist in Grad,positive
     * Werte drehen gegen den Uhrzeigersinn, negative Werte drehen im
     * Uhrzeigersinn (mathematisch positiver Drehsinn).
     *
     * @param winkel der (neue) Drehwinkel des Textes
     */
    public void WinkelSetzen(int winkel)
    {
        this.winkel = winkel;
        symbol.WinkelSetzen(winkel);
    }

    /**
     * Schaltet die Sichtbarkeit des Textes ein oder aus. Erlaubte
     * Parameterwerte: true, false
     *
     * @param sichtbar (neue) Sichtbarkeit des Textes
     */
    public void SichtbarkeitSetzen(boolean sichtbar)
    {
        this.sichtbar = sichtbar;
        symbol.SichtbarkeitSetzen(sichtbar);
    }

    /**
     * Entfernt den Text aus dem Zeichenfenster.
     */
    public void Entfernen()
    {
        symbol.Entfernen();
    }

    /**
     * Bringt den Text eine Ebene nach vorn.
     */
    public void NachVornBringen()
    {
        symbol.NachVornBringen();
    }

    /**
     * Bringt den Text in die vorderste Ebene.
     */
    public void GanzNachVornBringen()
    {
        symbol.GanzNachVornBringen();
    }

    /**
     * Bringt den Text eine Ebene nach hinten.
     */
    public void NachHintenBringen()
    {
        symbol.NachHintenBringen();
    }

    /**
     * Bringt den Text in die hinterste Ebene.
     */
    public void GanzNachHintenBringen()
    {
        symbol.GanzNachHintenBringen();
    }
}
