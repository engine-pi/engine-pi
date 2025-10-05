package de.pirckheimer_gymnasium.engine_pi.little_engine;

import de.pirckheimer_gymnasium.engine_pi.resources.ColorContainer;

/**
 * Ein <b>Rechteck</b> auf der Zeichenfläche.
 *
 * <p>
 * Der ursprüngliche Name der Klasse war {@code Rechteck}.
 * </p>
 *
 * @author Albert Wiedemann
 *
 * @version 1.0
 */
public class Rectangle
{
    /**
     * Die x-Position der linken oberen Ecke.
     */
    public int x;

    /**
     * Die y-Position der linken oberen Ecke.
     */
    public int y;

    /**
     * Die Breite des Rechtecks.
     *
     * <p>
     * Der ursprünglich deutsche Name dieses Attributs war {@code breite}.
     * </p>
     */
    public int width;

    /**
     * Die Höhe des Rechtecks.
     *
     * <p>
     * Der ursprünglich deutsche Name dieses Attributs war {@code höhe}.
     * </p>
     */
    public int height;

    /**
     * Die Farbe des Rechtecks.
     *
     * <p>
     * Der ursprünglich deutsche Name dieses Attributs war {@code farbe}.
     * </p>
     */
    public String color;

    /**
     * Die Sichtbarkeit des Rechtecks.
     *
     * <p>
     * Der ursprünglich deutsche Name dieses Attributs war {@code sichtbar}.
     * </p>
     */
    public boolean visible;

    /**
     * Der Drehwinkel (mathematisch positiver Drehsinn) des Rechtecks in Grad.
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
     * Der Konstruktor erzeugt das Delegate-Objekt
     */
    public Rectangle()
    {
        x = 10;
        y = 10;
        width = 100;
        height = 100;
        color = "rot";
        visible = true;
        rotation = 0;
        symbol = DrawingWindow.SymbolErzeugen(DrawingWindow.SymbolArt.rechteck);
        symbol.setPosition(x, y);
        symbol.setSize(width, height);
        symbol.setColor(color);
        symbol.setVisibility(visible);
        symbol.setRotation(rotation);
    }

    /**
     * Setzt die Position (der linken oberen Ecke) des Rechtecks.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code PositionSetzen}.
     * </p>
     *
     * @param x x-Position der linken oberen Ecke
     * @param y y-Position der linken oberen Ecke
     */
    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
        symbol.setPosition(x, y);
    }

    /**
     * Verschiebt das Rechteck um die angegebenen Werte.
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
        symbol.setPosition(x, y);
    }

    /**
     * Dreht das Rechteck
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code Drehen}.
     * </p>
     *
     * @param angle Drehwinkel (mathematisch positiver Drehsinn) im Gradmass
     */
    public void rotate(int angle)
    {
        rotation += angle;
        symbol.setRotation(rotation);
    }

    /**
     * Setzt die Größe des Rechtecks.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code GrößeSetzen}.
     * </p>
     *
     * @param width (neue) Breite
     * @param height (neue) Höhe
     */
    public void setSize(int width, int height)
    {
        this.width = width;
        this.height = height;
        symbol.setSize(width, height);
    }

    /**
     * Setzt die Farbe des Rechtecks.
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
     * Setzt den Drehwinkel des Rechtecks. Die Winkelangabe ist in Grad,positive
     * Werte drehen gegen den Uhrzeigersinn, negative Werte drehen im
     * Uhrzeigersinn (mathematisch positiver Drehsinn).
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code WinkelSetzen}.
     * </p>
     *
     * @param rotation der (neue) Drehwinkel des Rechtecks
     */
    public void setRotation(int rotation)
    {
        this.rotation = rotation;
        symbol.setRotation(rotation);
    }

    /**
     * Schaltet die Sichtbarkeit des Rechtecks ein oder aus. Erlaubte
     * Parameterwerte: true, false
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code SichtbarkeitSetzen}.
     * </p>
     *
     * @param sichtbar (neue) Sichtbarkeit des Rechtecks
     */
    public void setVisibility(boolean sichtbar)
    {
        this.visible = sichtbar;
        symbol.setVisibility(sichtbar);
    }

    /**
     * Entfernt das Rechteck aus dem Zeichenfenster.
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
     * Bringt das Rechteck eine Ebene nach vorn.
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
     * Bringt das Rechteck in die vorderste Ebene.
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
     * Bringt das Rechteck eine Ebene nach hinten.
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
     * Bringt das Rechteck in die hinterste Ebene.
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
