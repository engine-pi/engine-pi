package de.pirckheimer_gymnasium.engine_pi.little_engine;

import de.pirckheimer_gymnasium.engine_pi.resources.ColorContainer;

/**
 * Ein <b>Kreis</b> auf der Zeichenfläche.
 *
 * <p>
 * Der ursprüngliche Name der Klasse war {@code Kreis}.
 * </p>
 *
 * @author Albert Wiedemann
 *
 * @version 1.0
 */
public class Circle
{
    /**
     * Die x-Position des Kreismittelpunktes.
     */
    public int x;

    /**
     * Die y-Position des Kreismittelpunktes.
     */
    public int y;

    /**
     * Der Radius des Kreises.
     */
    public int radius;

    /**
     * Die Farbe des Kreises.
     *
     * <p>
     * Der ursprünglich deutsche Name dieses Attributs war {@code farbe}.
     * </p>
     */
    public String color;

    /**
     * Die Sichtbarkeit des Kreises.
     *
     * <p>
     * Der ursprünglich deutsche Name dieses Attributs war {@code sichtbar}.
     * </p>
     */
    public boolean visible;

    /**
     * Der Drehwinkel (mathematisch positiver Drehsinn) des Kreises in Grad.
     *
     * <p>
     * Der ursprünglich deutsche Name dieses Attributs war {@code winkel}.
     * </p>
     */
    public int rotation;

    /**
     * Referenz auf das Delegate-Objekt.
     */
    DrawingWindow.GrafikSymbol symbol;

    /**
     * Erzeugt einen neuen roten Kreis an der Position (60|60) mit einem Radius
     * von 50px.
     */
    public Circle()
    {
        x = 60;
        y = 60;
        radius = 50;
        color = "rot";
        visible = true;
        rotation = 0;
        symbol = DrawingWindow.SymbolErzeugen(DrawingWindow.SymbolArt.kreis);
        symbol.setPosition(x - radius, y - radius);
        symbol.setSize(radius * 2, radius * 2);
        symbol.setColor(color);
        symbol.setVisibility(visible);
        symbol.setRotation(rotation);
    }

    /**
     * Setzt die Position (des Mittelpunkts) des Kreises.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code PositionSetzen}.
     * </p>
     *
     * @param x Die x-Position des Mittelpunkts.
     * @param y Die y-Position des Mittelpunkts.
     */
    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
        symbol.setPosition(x - radius, y - radius);
    }

    /**
     * Verschiebt den Kreis um die angegebenen Werte.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code Verschieben}.
     * </p>
     *
     * @param deltaX Verschiebung in x-Richtung
     * @param deltaY Verschiebung in y-Richtung
     */
    public void move(int deltaX, int deltaY)
    {
        x += deltaX;
        y += deltaY;
        symbol.setPosition(x - radius, y - radius);
    }

    /**
     * Dreht den Kreis
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code Drehen}.
     * </p>
     *
     * @param angle Drehwinkel (mathematisch positiver Drehsinn) im Gradmass
     */
    public void rotate(int angle)
    {
        this.rotation += angle;
        symbol.setRotation(rotation);
    }

    /**
     * Setzt den Radius des Kreises.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code RadiusSetzen}.
     * </p>
     *
     * @param radius (neuer) Radius
     */
    public void setRadius(int radius)
    {
        this.radius = radius;
        symbol.setSize(radius * 2, radius * 2);
        symbol.setPosition(x - radius, y - radius);
    }

    /**
     * Setzt die Farbe des Kreises.
     *
     * <p>
     * Die möglichen Farbnamen sind über die Dokumentation der Klasse
     * {@link ColorContainer ColorContainer} einzusehen.
     * </p>
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code FarbeSetzen}.
     * </p>
     *
     * @param color Der (neue) Farbname (Mögliche Farbnamen:
     *     {@link ColorContainer siehe Auflistung}).
     */
    public void setColor(String color)
    {
        this.color = color;
        symbol.setColor(color);
    }

    /**
     * Setzt den Drehwinkel des Kreises. Die Winkelangabe ist in Grad,positive
     * Werte drehen gegen den Uhrzeigersinn, negative Werte drehen im
     * Uhrzeigersinn (mathematisch positiver Drehsinn).
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code WinkelSetzen}.
     * </p>
     *
     * @param rotation der (neue) Drehwinkel des Kreises
     */
    public void setRotation(int rotation)
    {
        this.rotation = rotation;
        symbol.setRotation(rotation);
    }

    /**
     * Schaltet die Sichtbarkeit des Kreises ein oder aus. Erlaubte
     * Parameterwerte: true, false
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code SichtbarkeitSetzen}.
     * </p>
     *
     * @param sichtbar (neue) Sichtbarkeit des Kreises
     */
    public void setVisibility(boolean sichtbar)
    {
        this.visible = sichtbar;
        symbol.setVisibility(sichtbar);
    }

    /**
     * Entfernt den Kreis aus dem Zeichenfenster.
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
     * Bringt den Kreis eine Ebene nach vorn.
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
     * Bringt den Kreis in die vorderste Ebene.
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
     * Bringt den Kreis eine Ebene nach hinten.
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
     * Bringt den Kreis in die hinterste Ebene.
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
