package de.pirckheimer_gymnasium.engine_pi.graphics_and_games;

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
     * x-Position der Turtle.
     */
    public int x;

    /**
     * y-Position der Turtle.
     */
    public int y;

    /**
     * Größe der Turtle.
     */
    public int größe;

    /**
     * Farbe der Turtle.
     */
    public String farbe;

    /**
     * Sichtbarkeit der Turtles.
     */
    public boolean sichtbar;

    /**
     * Drehwinkel (mathemtisch positiver Drehsinn) der Turtle in Grad.
     */
    public int winkel;

    /**
     * Stiftposition
     */
    public boolean stiftUnten;

    /**
     * Referenz auf das echte Turtlesybol.
     */
    DrawingWindow.TurtleIntern symbol;

    /**
     * Referenz auf das Aktionsempfängerobjekt.
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
        symbol.SichtbarkeitSetzen(true);
        x = symbol.x;
        y = symbol.y;
        winkel = symbol.winkel;
        größe = symbol.b;
        stiftUnten = symbol.stiftUnten;
        sichtbar = symbol.sichtbar;
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
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code AktionAusführen}.
     * </p>
     *
     */
    public void performAction()
    {
    }

    /**
     * Die eigentliche Aktionsmethode für gedrückte Tasten. <br>
     * Muss bei Bedarf von einer Unterklasse überschrieben werden.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code TasteGedrückt}.
     * </p>
     *
     * @param taste die gedrückte Taste
     */
    public void onKeyPressed(char taste)
    {
        // System. out. println ("Taste: " + taste);
    }

    /**
     * Die eigentliche Aktionsmethode für gedrückte Sondertasten. <br>
     * Muss bei Bedarf von einer Unterklasse überschrieben werden.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code SonderTasteGedrückt}.
     * </p>
     *
     * @param taste KeyCode der gedrückten Taste
     */
    public void onSpecialKeyPressed(int taste)
    {
        // System. out. println ("Sondertaste: " + taste);
    }

    /**
     * Die eigentliche Aktionsmethode für einen Mausklick. <br>
     * Muss bei Bedarf von einer Unterklasse überschrieben werden.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code MausGeklickt}.
     * </p>
     *
     * @param x x-Position des Mausklicks
     * @param y y-Position des Mausklicks
     * @param anzahl Anzahl der aufeinanderfolgenden Mausklicks
     */
    public void onMouseClick(int x, int y, int anzahl)
    {
        System.out.println("Maus: (" + x + "|" + y + "), " + anzahl + " mal");
    }

    /**
     * Setzt die Position der Turtle.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code PositionSetzen}.
     * </p>
     *
     * @param x x-Position der Turtle
     * @param y y-Position der Turtle
     */
    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
        symbol.PositionSetzen(x, y);
    }

    /**
     * Setzt die Größe des Turtlesymbols.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code GrößeSetzen}.
     * </p>
     *
     * @param größe (neue) Größe
     */
    public void setSize(int größe)
    {
        this.größe = größe;
        symbol.GrößeSetzen(größe, größe);
    }

    /**
     * Setzt die Farbe der Linie. Erlaubte Farben sind: "weiß", "weiss", "rot",
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
    public void setColor(String farbe)
    {
        this.farbe = farbe;
        symbol.FarbeSetzen(farbe);
    }

    /**
     * Setzt den Drehwinkel der Turtle. Die Winkelangabe ist in Grad, positive
     * Werte drehen gegen den Uhrzeigersinn, negative Werte drehen im
     * Uhrzeigersinn (mathematisch positiver Drehsinn), d. h. 0˚: Turtle schaut
     * nach rechts, 90˚: Turtle schaut nach oben, 180˚: Turtle schaut nach
     * links, 270˚bzw. -90˚: Turtle schaut nach unten
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code WinkelSetzen}.
     * </p>
     *
     * @param winkel der (neue) Drehwinkel der Turtle
     */
    public void setAngle(int winkel)
    {
        this.winkel = winkel;
        symbol.WinkelSetzen(winkel);
    }

    /**
     * Schaltet die Sichtbarkeit der Turtle ein oder aus. Erlaubte
     * Parameterwerte: true, false
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code SichtbarkeitSetzen}.
     * </p>
     *
     * @param sichtbar (neue) Sichtbarkeit der Turtle
     */
    public void setVisibility(boolean sichtbar)
    {
        this.sichtbar = sichtbar;
        symbol.SichtbarkeitSetzen(sichtbar);
    }

    /**
     * Entfernt die Turtle aus dem Zeichenfenster.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code Entfernen}.
     * </p>
     *
     */
    public void remove()
    {
        DrawingWindow.AktionsEmpfängerEntfernen(aktionsEmpfänger);
        symbol.Entfernen();
    }

    /**
     * Bringt die Turtle eine Ebene nach vorn.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code NachVornBringen}.
     * </p>
     *
     */
    public void NachVornBringen()
    {
        symbol.NachVornBringen();
    }

    /**
     * Bringt die Turtle in die vorderste Ebene.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code GanzNachVornBringen}.
     * </p>
     *
     */
    public void GanzNachVornBringen()
    {
        symbol.GanzNachVornBringen();
    }

    /**
     * Bringt die Turtle eine Ebene nach hinten.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code NachHintenBringen}.
     * </p>
     *
     */
    public void NachHintenBringen()
    {
        symbol.NachHintenBringen();
    }

    /**
     * Bringt die Turtle in die hinterste Ebene.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code GanzNachHintenBringen}.
     * </p>
     *
     */
    public void GanzNachHintenBringen()
    {
        symbol.GanzNachHintenBringen();
    }

    /**
     * Setzt die Turtle wieder an ihre Ausgangsposition.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code ZumStartpunktGehen}.
     * </p>
     *
     */
    public void moveToStartPoint()
    {
        symbol.ZumStartpunktGehen();
        x = symbol.x;
        y = symbol.y;
        winkel = symbol.winkel;
    }

    /**
     * Bewegt die Turtle nach vorne.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code Gehen}.
     * </p>
     *
     * @param länge Anzahl der Längeneinheiten
     */
    public void move(double länge)
    {
        symbol.Gehen(länge);
        x = symbol.x;
        y = symbol.y;
    }

    /**
     * Dreht die Turtle
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code Drehen}.
     * </p>
     *
     * @param grad Drehwinkel (mathematisch positiver Drehsinn) im Gradmaß
     */
    public void rotate(int grad)
    {
        symbol.Drehen(grad);
        winkel = symbol.winkel;
    }

    /**
     * Versetzt Zeichenfläche und Turtle in den Ausgangszustand.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code Löschen}.
     * </p>
     *
     */
    public void reset()
    {
        symbol.Löschen();
    }

    /**
     * Turtle wechselt in den Modus "nicht zeichnen"
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code StiftHeben}.
     * </p>
     *
     */
    public void liftPen()
    {
        symbol.StiftHeben();
        stiftUnten = false;
    }

    /**
     * Turtle wechselt in den Modus "zeichnen"
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code StiftSenken}.
     * </p>
     */
    public void lowerPen()
    {
        symbol.StiftSenken();
        stiftUnten = true;
    }

    /**
     * Gibt den aktuellen Winkel der Turtle zurück. Die Winkelangabe ist in
     * Grad, positive Werte drehen gegen den Uhrzeigersinn, negative Werte
     * drehen im Uhrzeigersinn (mathematisch positiver Drehsinn), d. h. 0˚:
     * Turtle schaut nach rechts, 90˚: Turtle schaut nach oben, 180˚: Turtle
     * schaut nach links, 270˚bzw. -90˚: Turtle schaut nach unten
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code WinkelGeben}.
     * </p>
     *
     * @return Winkel im Gradmass
     */
    public int getAngle()
    {
        return winkel;
    }

    /**
     * Gibt die x-Koordinate der Turtle zurück
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code XPositionGeben}.
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
     * Die ursprünglich deutsche Name dieser Methode war {@code YPositionGeben}.
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
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code SichtbarkeitFürSymbolSetzen}.
     * </p>
     *
     * @param sichtbar (neue) Sichtbarkeit des Turtlesymbols
     */
    public void setSymbolVisibility(boolean sichtbar)
    {
        symbol.SichtbarkeitFürSymbolSetzen(sichtbar);
    }

    /**
     * Testet, ob die Turtle eine Figur berührt.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code Berührt}.
     * </p>
     *
     * @return true, wenn die Turtlekoordinaten innerhalb einer Grafikfigur sind
     */
    public boolean isTouching()
    {
        return symbol.Berührt();
    }

    /**
     * Testet, ob die Turtle eine Figur in der angegebenen Farbe berührt.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code Berührt}.
     * </p>
     *
     * @param farbe die Farbe, die die berührte Figur haben muss.
     *
     * @return true, wenn die Turtlekoordinaten innerhalb einer Grafikfigur in
     *     der angegebenen Farbe sind
     */
    public boolean isTouching(String farbe)
    {
        return symbol.Berührt(farbe);
    }

    /**
     * Testet, ob die Turtle die angegebene Figur berührt.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code Berührt}.
     * </p>
     *
     * @param objekt das Objekt, das getestet werden soll.
     *
     * @return true, wenn die Turtlekoordinaten innerhalb der angegebenen
     *     Grafikfigur sind
     */
    public boolean isTouching(Object objekt)
    {
        return symbol.Berührt(objekt);
    }
}
