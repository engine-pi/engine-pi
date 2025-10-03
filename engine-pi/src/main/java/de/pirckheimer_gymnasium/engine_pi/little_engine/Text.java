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
     * Die x-Position der linken Seite der Grundlinie.
     */
    public int x;

    /**
     * Die y-Position der Grundlinie.
     */
    public int y;

    /**
     * Die Farbe des Textes.
     *
     * <p>
     * Der ursprünglich deutsche Name dieses Attributs war {@code farbe}.
     * </p>
     */
    public String color;

    /**
     * Die Sichtbarkeit des Textes.
     *
     * <p>
     * Der ursprünglich deutsche Name dieses Attributs war {@code sichtbar}.
     * </p>
     */
    public boolean visible;

    /**
     * Der Drehwinkel (mathematisch positiver Drehsinn) des Textes in Grad.
     *
     * <p>
     * Der ursprünglich deutsche Name dieses Attributs war {@code winkel}.
     * </p>
     */
    public int rotation;

    /**
     * Die Größe des Textes in Punkten.
     *
     * <p>
     * Der ursprünglich deutsche Name dieses Attributs war {@code textgröße}.
     * </p>
     */
    public int size;

    /**
     * Referenz auf das Delegate-Objekt.
     */
    public DrawingWindow.TextInternal symbol;

    /**
     * Der Konstruktor erzeugt das Delegate-Objekt
     */
    public Text()
    {
        x = 10;
        y = 10;
        color = "schwarz";
        visible = true;
        rotation = 0;
        size = 12;
        symbol = (DrawingWindow.TextInternal) DrawingWindow
                .SymbolErzeugen(DrawingWindow.SymbolArt.text);
        symbol.setPosition(x, y);
        symbol.setColor(color);
        symbol.setVisibility(visible);
        symbol.setRotation(rotation);
        symbol.TextGrößeSetzen(size);
    }

    /**
     * Setzt die Position (der Grundline) des Textes.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code PositionSetzen}.
     * </p>
     *
     * @param x x-Position der linken Seite der Grundlinie
     * @param y y-Position der Grundlinie
     */
    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
        symbol.setPosition(x, y);
    }

    /**
     * Setzt den aktuellen Text.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code TextSetzen}.
     * </p>
     *
     * @param text der neue Text
     */
    public void setText(String text)
    {
        symbol.TextSetzen(text);
    }

    /**
     * Setzt die Größe des Textes.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code TextGrößeSetzen}.
     * </p>
     *
     * @param size die (neue) Textgröße
     */
    public void setSize(int size)
    {
        this.size = size;
        symbol.TextGrößeSetzen(size);
    }

    /**
     * Vergrößert den Text.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code TextVergrößern}.
     * </p>
     */
    public void TextVergrößern()
    {
        symbol.TextVergrößern();
        size = (int) symbol.size;
    }

    /**
     * Verkleinert den Text.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code TextVerkleinern}.
     * </p>
     */
    public void TextVerkleinern()
    {
        symbol.TextVerkleinern();
        size = (int) symbol.size;
    }

    /**
     * Verschiebt den Text um die angegebenen Werte.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code Verschieben}.
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
     * Der ursprünglich deutsche Name dieser Methode war {@code Drehen}.
     * </p>
     *
     * @param grad Drehwinkel (mathematisch positiver Drehsinn) im Gradmass
     */
    public void Drehen(int grad)
    {
        rotation += grad;
        symbol.setRotation(rotation);
    }

    /**
     * Setzt die Farbe des Textes. Erlaubte Farben sind: "weiß", "weiss", "rot",
     * "grün", "gruen", "blau", "gelb", "magenta", "cyan", "hellgelb",
     * "hellgrün", "hellgruen", "orange", "braun", "grau", "schwarz" Alle
     * anderen Eingaben werden auf die Farbe schwarz abgebildet.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code FarbeSetzen}.
     * </p>
     *
     * @param farbe (neue) Farbe
     */
    public void setColor(String farbe)
    {
        this.color = farbe;
        symbol.setColor(farbe);
    }

    /**
     * Setzt den Drehwinkel des Textes. Die Winkelangabe ist in Grad,positive
     * Werte drehen gegen den Uhrzeigersinn, negative Werte drehen im
     * Uhrzeigersinn (mathematisch positiver Drehsinn).
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code WinkelSetzen}.
     * </p>
     *
     * @param winkel der (neue) Drehwinkel des Textes
     */
    public void setRotation(int winkel)
    {
        this.rotation = winkel;
        symbol.setRotation(winkel);
    }

    /**
     * Schaltet die Sichtbarkeit des Textes ein oder aus. Erlaubte
     * Parameterwerte: true, false
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code SichtbarkeitSetzen}.
     * </p>
     *
     * @param sichtbar (neue) Sichtbarkeit des Textes
     */
    public void setVisibility(boolean sichtbar)
    {
        this.visible = sichtbar;
        symbol.setVisibility(sichtbar);
    }

    /**
     * Entfernt den Text aus dem Zeichenfenster.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code Entfernen}.
     * </p>
     */
    public void remove()
    {
        symbol.remove();
    }

    /**
     * Bringt den Text eine Ebene nach vorn.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code NachVornBringen}.
     * </p>
     */
    public void raiseLayer()
    {
        symbol.raiseLayer();
    }

    /**
     * Bringt den Text in die vorderste Ebene.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code GanzNachVornBringen}.
     * </p>
     */
    public void bringToFront()
    {
        symbol.bringToFront();
    }

    /**
     * Bringt den Text eine Ebene nach hinten.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code NachHintenBringen}.
     * </p>
     */
    public void lowerLayer()
    {
        symbol.lowerLayer();
    }

    /**
     * Bringt den Text in die hinterste Ebene.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code GanzNachHintenBringen}.
     * </p>
     */
    public void bringToBack()
    {
        symbol.bringToBack();
    }
}
