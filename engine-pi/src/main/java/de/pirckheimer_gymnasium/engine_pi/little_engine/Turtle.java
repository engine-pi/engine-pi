package de.pirckheimer_gymnasium.engine_pi.little_engine;

/**
 * Wrapperklasse für die Turtle auf der Zeichenfläche.
 *
 * @author Albert Wiedemann
 *
 * @version 1.0
 */
public class Turtle
{
    /**
     * Die x-Position der Turtle.
     */
    public int x;

    /**
     * Die y-Position der Turtle.
     */
    public int y;

    /**
     * Größe der Turtle.
     *
     * <p>
     * Der ursprünglich deutsche Name dieses Attributs war {@code größe}.
     * </p>
     */
    public int size;

    /**
     * Farbe der Turtle.
     *
     * <p>
     * Der ursprünglich deutsche Name dieses Attributs war {@code farbe}.
     * </p>
     */
    public String color;

    /**
     * Sichtbarkeit der Turtles.
     *
     * <p>
     * Der ursprünglich deutsche Name dieses Attributs war {@code sichtbar}.
     * </p>
     */
    public boolean visible;

    /**
     * Drehwinkel (mathemtisch positiver Drehsinn) der Turtle in Grad.
     *
     * <p>
     * Der ursprünglich deutsche Name dieses Attributs war {@code winkel}.
     * </p>
     */
    public int rotation;

    /**
     * Stiftposition
     *
     * <p>
     * Der ursprünglich deutsche Name dieses Attributs war {@code stiftUnten}.
     * </p>
     */
    public boolean penDown;

    /**
     * Referenz auf das echte Turtlesybol.
     */
    DrawingWindow.TurtleIntern symbol;

    /**
     * Referenz auf das Aktionsempfängerobjekt.
     *
     * <p>
     * Der ursprünglich deutsche Name dieses Attributs war
     * {@code aktionsEmpfänger}.
     * </p>
     */
    DrawingWindow.AktionsEmpfaenger aktionsEmpfänger;

    /**
     * Konstruktor der Turtle Erzeugt eine Turtle und versetzt sie in einen
     * gültigen Startzustand.
     */
    public Turtle()
    {
        symbol = (DrawingWindow.TurtleIntern) DrawingWindow
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
     * Methode wird aufgerufen, wenn die Turtle handeln soll. Die vordefinierte
     * Methode tut nichts.
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
     * Setzt die Position der Turtle.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code PositionSetzen}.
     * </p>
     *
     * @param x x-Position der Turtle
     * @param y y-Position der Turtle
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
     * Setzt die Farbe der Linie. Erlaubte Farben sind: "weiß", "weiss", "rot",
     * "grün", "gruen", "blau", "gelb", "magenta", "cyan", "hellgelb",
     * "hellgrün", "hellgruen", "orange", "braun", "grau", "schwarz" Alle
     * anderen Eingaben werden auf die Farbe schwarz abgebildet.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code FarbeSetzen}.
     * </p>
     *
     * @param color (neue) Farbe
     */
    public void setColor(String color)
    {
        this.color = color;
        symbol.setColor(color);
    }

    /**
     * Setzt den Drehwinkel der Turtle. Die Winkelangabe ist in Grad, positive
     * Werte drehen gegen den Uhrzeigersinn, negative Werte drehen im
     * Uhrzeigersinn (mathematisch positiver Drehsinn), d. h. 0˚: Turtle schaut
     * nach rechts, 90˚: Turtle schaut nach oben, 180˚: Turtle schaut nach
     * links, 270˚bzw. -90˚: Turtle schaut nach unten
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code WinkelSetzen}.
     * </p>
     *
     * @param rotation der (neue) Drehwinkel der Turtle
     */
    public void setRotation(int rotation)
    {
        this.rotation = rotation;
        symbol.setRotation(rotation);
    }

    /**
     * Schaltet die Sichtbarkeit der Turtle ein oder aus. Erlaubte
     * Parameterwerte: true, false
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code SichtbarkeitSetzen}.
     * </p>
     *
     * @param visible (neue) Sichtbarkeit der Turtle
     */
    public void setVisibility(boolean visible)
    {
        this.visible = visible;
        symbol.setVisibility(visible);
    }

    /**
     * Entfernt die Turtle aus dem Zeichenfenster.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code Entfernen}.
     * </p>
     *
     */
    public void remove()
    {
        DrawingWindow.AktionsEmpfängerEntfernen(aktionsEmpfänger);
        symbol.remove();
    }

    /**
     * Bringt die Turtle eine Ebene nach vorn.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code NachVornBringen}.
     * </p>
     *
     */
    public void raiseLayer()
    {
        symbol.raiseLayer();
    }

    /**
     * Bringt die Turtle in die vorderste Ebene.
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
     * Bringt die Turtle eine Ebene nach hinten.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code NachHintenBringen}.
     * </p>
     *
     */
    public void lowerLayer()
    {
        symbol.lowerLayer();
    }

    /**
     * Bringt die Turtle in die hinterste Ebene.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code GanzNachHintenBringen}.
     * </p>
     *
     */
    public void bringToBack()
    {
        symbol.bringToBack();
    }

    /**
     * Setzt die Turtle wieder an ihre Ausgangsposition.
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
     * Bewegt die Turtle nach vorne.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code Gehen}.
     * </p>
     *
     * @param length Anzahl der Längeneinheiten
     */
    public void move(double length)
    {
        symbol.move(length);
        x = symbol.x;
        y = symbol.y;
    }

    /**
     * Dreht die Turtle
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code Drehen}.
     * </p>
     *
     * @param rotation Drehwinkel (mathematisch positiver Drehsinn) im Gradmaß
     */
    public void rotate(int rotation)
    {
        symbol.rotate(rotation);
        rotation = symbol.winkel;
    }

    /**
     * Versetzt Zeichenfläche und Turtle in den Ausgangszustand.
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
     * Turtle wechselt in den Modus "nicht zeichnen"
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
     * Turtle wechselt in den Modus "zeichnen"
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
     * Gibt den aktuellen Winkel der Turtle zurück. Die Winkelangabe ist in
     * Grad, positive Werte drehen gegen den Uhrzeigersinn, negative Werte
     * drehen im Uhrzeigersinn (mathematisch positiver Drehsinn), d. h. 0˚:
     * Turtle schaut nach rechts, 90˚: Turtle schaut nach oben, 180˚: Turtle
     * schaut nach links, 270˚bzw. -90˚: Turtle schaut nach unten
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code WinkelGeben}.
     * </p>
     *
     * @return Winkel im Gradmass
     */
    public int getRotation()
    {
        return rotation;
    }

    /**
     * Gibt die x-Koordinate der Turtle zurück
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code XPositionGeben}.
     * </p>
     *
     * @return x-Koordinate
     */
    public int getX()
    {
        return x;
    }

    /**
     * Gibt die y-Koordinate der Turtle zurück.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code YPositionGeben}.
     * </p>
     *
     * @return y-Koordinate
     */
    public int getY()
    {
        return y;
    }

    /**
     * Schaltet die Sichtbarkeit des Turtlesymbols ein oder aus. Erlaubte
     * Parameterwerte: true, false
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code SichtbarkeitFürSymbolSetzen}.
     * </p>
     *
     * @param visible (neue) Sichtbarkeit des Turtlesymbols
     */
    public void setSymbolVisibility(boolean visible)
    {
        symbol.setSymbolVisibility(visible);
    }

    /**
     * Testet, ob die Turtle eine Figur berührt.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code Berührt}.
     * </p>
     *
     * @return true, wenn die Turtlekoordinaten innerhalb einer Grafikfigur sind
     */
    public boolean isTouching()
    {
        return symbol.isTouching();
    }

    /**
     * Testet, ob die Turtle eine Figur in der angegebenen Farbe berührt.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code Berührt}.
     * </p>
     *
     * @param color die Farbe, die die berührte Figur haben muss.
     *
     * @return true, wenn die Turtlekoordinaten innerhalb einer Grafikfigur in
     *     der angegebenen Farbe sind
     */
    public boolean isTouching(String color)
    {
        return symbol.isTouching(color);
    }

    /**
     * Testet, ob die Turtle die angegebene Figur berührt.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code Berührt}.
     * </p>
     *
     * @param object das Objekt, das getestet werden soll.
     *
     * @return true, wenn die Turtlekoordinaten innerhalb der angegebenen
     *     Grafikfigur sind
     */
    public boolean isTouching(Object object)
    {
        return symbol.isTouching(object);
    }
}
