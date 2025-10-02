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
     * Die Breite des umgebenden Rechtecks.
     *
     * <p>
     * Die ursprünglich deutsche Name dieses Attributs war {@code breite}.
     * </p>
     */
    public int width;

    /**
     * Die Höhe des umgebenden Rechtecks.
     *
     * <p>
     * Die ursprünglich deutsche Name dieses Attributs war {@code höhe}.
     * </p>
     */
    public int height;

    /**
     * Die Farbe des Dreiecks.
     *
     * <p>
     * Die ursprünglich deutsche Name dieses Attributs war {@code farbe}.
     * </p>
     */
    public String color;

    /**
     * Die Sichtbarkeit des Dreiecks.
     *
     * <p>
     * Die ursprünglich deutsche Name dieses Attributs war {@code sichtbar}.
     * </p>
     */
    public boolean visible;

    /**
     * Der Drehwinkel (mathematisch positiver Drehsinn) des Dreiecks in Grad.
     *
     * <p>
     * Die ursprünglich deutsche Name dieses Attributs war {@code winkel}.
     * </p>
     */
    public int rotation;

    /**
     * Referenz auf das Delegate-Objekt.
     */
    DrawingWindow.GrafikSymbol symbol;

    /**
     * Der Konstruktor erzeugt das Delegate-Objekt.
     */
    public Triangle()
    {
        x = 60;
        y = 10;
        width = 100;
        height = 100;
        color = "rot";
        visible = true;
        rotation = 0;
        symbol = DrawingWindow.SymbolErzeugen(DrawingWindow.SymbolArt.dreieck);
        symbol.setPosition(x - width / 2, y);
        symbol.setSize(width, height);
        symbol.setColor(color);
        symbol.setVisibility(visible);
        symbol.setRotation(rotation);
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
    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
        symbol.setPosition(x - width / 2, y);
    }

    /**
     * Verschiebt das Dreieck um die angegebenen Werte.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code Verschieben}.
     * </p>
     *
     * @param deltaX Die Verschiebung in x-Richtung.
     * @param deltaY Die Verschiebung in y-Richtung.
     */
    public void move(int deltaX, int deltaY)
    {
        x += deltaX;
        y += deltaY;
        symbol.setPosition(x - width / 2, y);
    }

    /**
     * Dreht das Dreieck.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code Drehen}.
     * </p>
     *
     * @param angle Der Drehwinkel (mathematisch positiver Drehsinn) im Gradmaß.
     */
    public void rotate(int angle)
    {
        rotation += angle;
        symbol.setRotation(rotation);
    }

    /**
     * Setzt die Größe des Dreiecks.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code GrößeSetzen}.
     * </p>
     *
     * @param width Die (neue) Breite.
     * @param height Die (neue) Höhe.
     */
    public void setSize(int width, int height)
    {
        this.width = width;
        this.height = height;
        symbol.setSize(width, height);
        symbol.setPosition(x - width / 2, y);
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
     * @param color Die (neue) Farbe.
     */
    public void setColor(String color)
    {
        this.color = color;
        symbol.setColor(color);
    }

    /**
     * Setzt den Drehwinkel des Dreiecks. Die Winkelangabe ist in Grad, positive
     * Werte drehen gegen den Uhrzeigersinn, negative Werte drehen im
     * Uhrzeigersinn (mathematisch positiver Drehsinn).
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code WinkelSetzen}.
     * </p>
     *
     * @param rotation Der (neue) Drehwinkel des Dreiecks.
     */
    public void setRotation(int rotation)
    {
        this.rotation = rotation;
        symbol.setRotation(rotation);
    }

    /**
     * Schaltet die Sichtbarkeit des Dreiecks ein oder aus. Erlaubte
     * Parameterwerte: {@code true}, {@code false}
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code SichtbarkeitSetzen}.
     * </p>
     *
     * @param visible Die (neue) Sichtbarkeit des Dreiecks.
     */
    public void setVisibility(boolean visible)
    {
        this.visible = visible;
        symbol.setVisibility(visible);
    }

    /**
     * Entfernt das Dreieck aus dem Zeichenfenster.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code Entfernen}.
     * </p>
     */
    public void remove()
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
    public void raiseLayer()
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
    public void bringToFront()
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
    public void lowerLayer()
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
    public void bringToBack()
    {
        symbol.bringToBack();
    }
}
