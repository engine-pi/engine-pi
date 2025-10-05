package de.pirckheimer_gymnasium.engine_pi.little_engine;

import de.pirckheimer_gymnasium.engine_pi.resources.ColorContainer;

/**
 * Eine <b>Schildkröte</b> auf der Zeichenfläche.
 *
 * @author Albert Wiedemann
 *
 * @version 1.0
 */
public class Turtle
{
    /**
     * Die x-Position der Schildkröte.
     */
    public int x;

    /**
     * Die y-Position der Schildkröte.
     */
    public int y;

    /**
     * Die Größe der Schildkröte.
     *
     * <p>
     * Der ursprünglich deutsche Name dieses Attributs war {@code größe}.
     * </p>
     */
    public int size;

    /**
     * Die Farbe der Schildkröte.
     *
     * <p>
     * Der ursprünglich deutsche Name dieses Attributs war {@code farbe}.
     * </p>
     */
    public String color;

    /**
     * Die Sichtbarkeit der Schildkröte.
     *
     * <p>
     * Der ursprünglich deutsche Name dieses Attributs war {@code sichtbar}.
     * </p>
     */
    public boolean visible;

    /**
     * Der Drehwinkel (mathemtisch positiver Drehsinn) der Schildkröte in Grad.
     *
     * <p>
     * Der ursprünglich deutsche Name dieses Attributs war {@code winkel}.
     * </p>
     */
    public int rotation;

    /**
     * Die Stiftposition. Wenn wahr, dann ist der Stift unten, er schreibt also.
     *
     * <p>
     * Der ursprünglich deutsche Name dieses Attributs war {@code stiftUnten}.
     * </p>
     */
    public boolean penDown;

    /**
     * Eine Referenz auf das echte Turtlesymbol.
     */
    DrawingWindow.TurtleInternal symbol;

    /**
     * Eine Referenz auf das Aktionsempfängerobjekt.
     *
     * <p>
     * Der ursprünglich deutsche Name dieses Attributs war
     * {@code aktionsEmpfänger}.
     * </p>
     */
    DrawingWindow.AktionsEmpfaenger aktionsEmpfänger;

    /**
     * Konstruktor der Schildkröte Erzeugt eine Turtle und versetzt sie in einen
     * gültigen Startzustand.
     */
    public Turtle()
    {
        symbol = (DrawingWindow.TurtleInternal) DrawingWindow
                .SymbolErzeugen(DrawingWindow.SymbolArt.turtle);
        symbol.setVisibility(true);
        x = symbol.x;
        y = symbol.y;
        rotation = symbol.winkel;
        size = symbol.b;
        penDown = symbol.stiftUnten;
        visible = symbol.sichtbar;
        aktionsEmpfänger = new DrawingWindow.AktionsEmpfaenger()
        {
            public void Ausführen()
            {
                performAction();
            }

            public void Taste(char taste)
            {
                onKeyPressed(taste);
            }

            public void SonderTaste(int taste)
            {
                onSpecialKeyPressed(taste);
            }

            public void Geklickt(int x, int y, int anzahl)
            {
                onMouseClick(x, y, anzahl);
            }
        };
        DrawingWindow.AktionsEmpfängerEintragen(aktionsEmpfänger);
    }

    /**
     * Methode wird aufgerufen, wenn die Schildkröte handeln soll. Die
     * vordefinierte Methode tut nichts.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code AktionAusführen}.
     * </p>
     */
    public void performAction()
    {
    }

    /**
     * Die eigentliche Aktionsmethode für gedrückte Tasten. <br>
     * Muss bei Bedarf von einer Unterklasse überschrieben werden.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code TasteGedrückt}.
     * </p>
     *
     * @param key die gedrückte Taste
     */
    public void onKeyPressed(char key)
    {
        // System. out. println ("Taste: " + taste);
    }

    /**
     * Die eigentliche Aktionsmethode für gedrückte Sondertasten. <br>
     * Muss bei Bedarf von einer Unterklasse überschrieben werden.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code SonderTasteGedrückt}.
     * </p>
     *
     * @param key KeyCode der gedrückten Taste
     */
    public void onSpecialKeyPressed(int key)
    {
        // System. out. println ("Sondertaste: " + taste);
    }

    /**
     * Die eigentliche Aktionsmethode für einen Mausklick. <br>
     * Muss bei Bedarf von einer Unterklasse überschrieben werden.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code MausGeklickt}.
     * </p>
     *
     * @param x x-Position des Mausklicks
     * @param y y-Position des Mausklicks
     * @param clickCount Anzahl der aufeinanderfolgenden Mausklicks
     */
    public void onMouseClick(int x, int y, int clickCount)
    {
        System.out
                .println("Maus: (" + x + "|" + y + "), " + clickCount + " mal");
    }

    /**
     * Setzt die Position der Schildkröte.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code PositionSetzen}.
     * </p>
     *
     * @param x x-Position der Schildkröte
     * @param y y-Position der Schildkröte
     */
    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
        symbol.setPosition(x, y);
    }

    /**
     * Setzt die Größe des Turtlesymbols.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code GrößeSetzen}.
     * </p>
     *
     * @param size (neue) Größe
     */
    public void setSize(int size)
    {
        this.size = size;
        symbol.setSize(size, size);
    }

    /**
     * Setzt die Farbe der Linie.
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
     * Setzt den Drehwinkel der Schildkröte.
     *
     * <p>
     * Die Winkelangabe ist in Grad, positive Werte drehen gegen den
     * Uhrzeigersinn, negative Werte drehen im Uhrzeigersinn (mathematisch
     * positiver Drehsinn), d. h. 0˚: Die Schilkröte schaut nach rechts, 90˚:
     * Die Schilkröte schaut nach oben, 180˚: Die Schilkröte schaut nach links,
     * 270˚bzw. -90˚: Die Schilkröte schaut nach unten.
     * </p>
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code WinkelSetzen}.
     * </p>
     *
     * @param rotation der (neue) Drehwinkel der Schildkröte
     */
    public void setRotation(int rotation)
    {
        this.rotation = rotation;
        symbol.setRotation(rotation);
    }

    /**
     * Schaltet die Sichtbarkeit der Schildkröte ein oder aus. Erlaubte
     * Parameterwerte: true, false
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code SichtbarkeitSetzen}.
     * </p>
     *
     * @param visible (neue) Sichtbarkeit der Schildkröte
     */
    public void setVisibility(boolean visible)
    {
        this.visible = visible;
        symbol.setVisibility(visible);
    }

    /**
     * Entfernt die Schildkröte aus dem Zeichenfenster.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code Entfernen}.
     * </p>
     */
    public void remove()
    {
        DrawingWindow.AktionsEmpfängerEntfernen(aktionsEmpfänger);
        symbol.remove();
    }

    /**
     * Bringt die Schildkröte eine Ebene nach vorn.
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
     * Bringt die Schildkröte in die vorderste Ebene.
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
     * Bringt die Schildkröte eine Ebene nach hinten.
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
     * Bringt die Schildkröte in die hinterste Ebene.
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

    /**
     * Setzt die Schildkröte wieder an ihre Ausgangsposition.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code ZumStartpunktGehen}.
     * </p>
     */
    public void moveToStartPoint()
    {
        symbol.moveToStartPoint();
        x = symbol.x;
        y = symbol.y;
        rotation = symbol.winkel;
    }

    /**
     * Bewegt die Schildkröte nach vorne.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code Gehen}.
     * </p>
     *
     * @param length Die Anzahl der Längeneinheiten.
     */
    public void move(double length)
    {
        symbol.move(length);
        x = symbol.x;
        y = symbol.y;
    }

    /**
     * Dreht die Schildkröte.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code Drehen}.
     * </p>
     *
     * @param rotation Der Drehwinkel (mathematisch positiver Drehsinn) im
     *     Gradmaß.
     */
    public void rotate(int rotation)
    {
        symbol.rotate(rotation);
        rotation = symbol.winkel;
    }

    /**
     * Versetzt die Zeichenfläche und die Schilkröte in den Ausgangszustand.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code Löschen}.
     * </p>
     */
    public void reset()
    {
        symbol.reset();
    }

    /**
     * Die Schildkröte wechselt in den Modus „nicht zeichnen“.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code StiftHeben}.
     * </p>
     */
    public void liftPen()
    {
        symbol.liftPen();
        penDown = false;
    }

    /**
     * Die Schildkröte wechselt in den Modus „zeichnen“.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code StiftSenken}.
     * </p>
     */
    public void lowerPen()
    {
        symbol.lowerPen();
        penDown = true;
    }

    /**
     * Gibt den aktuellen Winkel der Schildkröte zurück.
     *
     * <p>
     * Die Winkelangabe ist in Grad, positive Werte drehen gegen den
     * Uhrzeigersinn, negative Werte drehen im Uhrzeigersinn (mathematisch
     * positiver Drehsinn), d. h. 0˚: Die Schildkröte schaut nach rechts, 90˚:
     * Die Schildkröte schaut nach oben, 180˚: Die Schildkröte schaut nach
     * links, 270˚ bzw. -90˚: Die Schildkröte schaut nach unten.
     * </p>
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code WinkelGeben}.
     * </p>
     *
     * @return Derr Winkel im Gradmaß.
     */
    public int getRotation()
    {
        return rotation;
    }

    /**
     * Gibt die x-Koordinate der Schildkröte zurück.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code XPositionGeben}.
     * </p>
     *
     * @return Die x-Koordinate.
     */
    public int getX()
    {
        return x;
    }

    /**
     * Gibt die y-Koordinate der Schildkröte zurück.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code YPositionGeben}.
     * </p>
     *
     * @return Die y-Koordinate.
     */
    public int getY()
    {
        return y;
    }

    /**
     * Schaltet die Sichtbarkeit der Schildkröte ein oder aus.
     *
     * <p>
     * Erlaubte Parameterwerte: {@code true}, {@code false}.
     * </p>
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code SichtbarkeitFürSymbolSetzen}.
     * </p>
     *
     * @param visible Die (neue) Sichtbarkeit der Schildkröte.
     */
    public void setSymbolVisibility(boolean visible)
    {
        symbol.setSymbolVisibility(visible);
    }

    /**
     * Testet, ob die Schildkröte eine Figur berührt.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code Berührt}.
     * </p>
     *
     * @return {@code true}, wenn die Schildkrötekoordinaten innerhalb einer
     *     Grafikfigur sind.
     */
    public boolean isTouching()
    {
        return symbol.isTouching();
    }

    /**
     * Testet, ob die Schildkröte eine Figur in der angegebenen Farbe berührt.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code Berührt}.
     * </p>
     *
     * @param color Die Farbe, die die berührte Figur haben muss.
     *
     * @return {@code true}, wenn die Schildkrötekoordinaten innerhalb einer
     *     Grafikfigur in der angegebenen Farbe sindl
     */
    public boolean isTouching(String color)
    {
        return symbol.isTouching(color);
    }

    /**
     * Testet, ob die Schildkröte die angegebene Figur berührt.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code Berührt}.
     * </p>
     *
     * @param object Das Objekt, das getestet werden soll.
     *
     * @return {@code true}, wenn die Schildkrötekoordinaten innerhalb der
     *     angegebenen Grafikfigur sind.
     */
    public boolean isTouching(Object object)
    {
        return symbol.isTouching(object);
    }
}
