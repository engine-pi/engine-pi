package de.pirckheimer_gymnasium.engine_pi.little_engine;

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
    public DrawingWindow.TextIntern symbol;

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
        symbol = (DrawingWindow.TextIntern) DrawingWindow
                .SymbolErzeugen(DrawingWindow.SymbolArt.text);
        symbol.setPosition(x, y);
        symbol.setColor(farbe);
        symbol.setVisibility(sichtbar);
        symbol.setRotation(winkel);
        symbol.TextGrößeSetzen(textgröße);
    }

    /**
     * Setzt die Position (der Grundline) des Textes.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code PositionSetzen}.
     * </p>
     *
     * @param x x-Position der linken Seite der Grundlinie
     * @param y y-Position der Grundlinie
     */
    public void PositionSetzen(int x, int y)
    {
        this.x = x;
        this.y = y;
        symbol.setPosition(x, y);
    }

    /**
     * Setzt den aktuellen Text.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code TextSetzen}.
     * </p>
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
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code TextGrößeSetzen}.
     * </p>
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
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code TextVergrößern}.
     * </p>
     */
    public void TextVergrößern()
    {
        symbol.TextVergrößern();
        textgröße = (int) symbol.size;
    }

    /**
     * Verkleinert den Text.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code TextVerkleinern}.
     * </p>
     */
    public void TextVerkleinern()
    {
        symbol.TextVerkleinern();
        textgröße = (int) symbol.size;
    }

    /**
     * Verschiebt den Text um die angegebenen Werte.
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
        symbol.setPosition(x, y);
    }

    /**
     * Dreht den Text
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
     * Setzt die Farbe des Textes. Erlaubte Farben sind: "weiß", "weiss", "rot",
     * "grün", "gruen", "blau", "gelb", "magenta", "cyan", "hellgelb",
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
     * Setzt den Drehwinkel des Textes. Die Winkelangabe ist in Grad,positive
     * Werte drehen gegen den Uhrzeigersinn, negative Werte drehen im
     * Uhrzeigersinn (mathematisch positiver Drehsinn).
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code WinkelSetzen}.
     * </p>
     *
     * @param winkel der (neue) Drehwinkel des Textes
     */
    public void WinkelSetzen(int winkel)
    {
        this.winkel = winkel;
        symbol.setRotation(winkel);
    }

    /**
     * Schaltet die Sichtbarkeit des Textes ein oder aus. Erlaubte
     * Parameterwerte: true, false
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code SichtbarkeitSetzen}.
     * </p>
     *
     * @param sichtbar (neue) Sichtbarkeit des Textes
     */
    public void SichtbarkeitSetzen(boolean sichtbar)
    {
        this.sichtbar = sichtbar;
        symbol.setVisibility(sichtbar);
    }

    /**
     * Entfernt den Text aus dem Zeichenfenster.
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
     * Bringt den Text eine Ebene nach vorn.
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
     * Bringt den Text in die vorderste Ebene.
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
     * Bringt den Text eine Ebene nach hinten.
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
     * Bringt den Text in die hinterste Ebene.
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
