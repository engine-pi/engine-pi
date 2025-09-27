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
                AktionAusführen();
            }

            public void Taste(char taste)
            {
                TasteGedrückt(taste);
            }

            public void SonderTaste(int taste)
            {
                SonderTasteGedrückt(taste);
            }

            public void Geklickt(int x, int y, int anzahl)
            {
                MausGeklickt(x, y, anzahl);
            }
        };
        DrawingWindow.AktionsEmpfängerEintragen(aktionsEmpfänger);
    }

    /**
     * Methode wird aufgerufen, wenn die Turtle handeln soll. Die vordefinierte
     * Methode tut nichts.
     */
    public void AktionAusführen()
    {
    }

    /**
     * Die eigentliche Aktionsmethode für gedrückte Tasten. <br>
     * Muss bei Bedarf von einer Unterklasse überschrieben werden.
     *
     * @param taste die gedrückte Taste
     */
    public void TasteGedrückt(char taste)
    {
        // System. out. println ("Taste: " + taste);
    }

    /**
     * Die eigentliche Aktionsmethode für gedrückte Sondertasten. <br>
     * Muss bei Bedarf von einer Unterklasse überschrieben werden.
     *
     * @param taste KeyCode der gedrückten Taste
     */
    public void SonderTasteGedrückt(int taste)
    {
        // System. out. println ("Sondertaste: " + taste);
    }

    /**
     * Die eigentliche Aktionsmethode für einen Mausklick. <br>
     * Muss bei Bedarf von einer Unterklasse überschrieben werden.
     *
     * @param x x-Position des Mausklicks
     * @param y y-Position des Mausklicks
     * @param anzahl Anzahl der aufeinanderfolgenden Mausklicks
     */
    public void MausGeklickt(int x, int y, int anzahl)
    {
        System.out.println("Maus: (" + x + "|" + y + "), " + anzahl + " mal");
    }

    /**
     * Setzt die Position der Turtle.
     *
     * @param x x-Position der Turtle
     * @param y y-Position der Turtle
     */
    public void PositionSetzen(int x, int y)
    {
        this.x = x;
        this.y = y;
        symbol.PositionSetzen(x, y);
    }

    /**
     * Setzt die Größe des Turtlesymbols.
     *
     * @param größe (neue) Größe
     */
    public void GrößeSetzen(int größe)
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
     * @param farbe (neue) Farbe
     */
    public void FarbeSetzen(String farbe)
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
     * @param winkel der (neue) Drehwinkel der Turtle
     */
    public void WinkelSetzen(int winkel)
    {
        this.winkel = winkel;
        symbol.WinkelSetzen(winkel);
    }

    /**
     * Schaltet die Sichtbarkeit der Turtle ein oder aus. Erlaubte
     * Parameterwerte: true, false
     *
     * @param sichtbar (neue) Sichtbarkeit der Turtle
     */
    public void SichtbarkeitSetzen(boolean sichtbar)
    {
        this.sichtbar = sichtbar;
        symbol.SichtbarkeitSetzen(sichtbar);
    }

    /**
     * Entfernt die Turtle aus dem Zeichenfenster.
     */
    public void Entfernen()
    {
        DrawingWindow.AktionsEmpfängerEntfernen(aktionsEmpfänger);
        symbol.Entfernen();
    }

    /**
     * Bringt die Turtle eine Ebene nach vorn.
     */
    public void NachVornBringen()
    {
        symbol.NachVornBringen();
    }

    /**
     * Bringt die Turtle in die vorderste Ebene.
     */
    public void GanzNachVornBringen()
    {
        symbol.GanzNachVornBringen();
    }

    /**
     * Bringt die Turtle eine Ebene nach hinten.
     */
    public void NachHintenBringen()
    {
        symbol.NachHintenBringen();
    }

    /**
     * Bringt die Turtle in die hinterste Ebene.
     */
    public void GanzNachHintenBringen()
    {
        symbol.GanzNachHintenBringen();
    }

    /**
     * Setzt die Turtle wieder an ihre Ausgangsposition.
     */
    public void ZumStartpunktGehen()
    {
        symbol.ZumStartpunktGehen();
        x = symbol.x;
        y = symbol.y;
        winkel = symbol.winkel;
    }

    /**
     * Bewegt die Turtle nach vorne.
     *
     * @param länge Anzahl der Längeneinheiten
     */
    public void Gehen(double länge)
    {
        symbol.Gehen(länge);
        x = symbol.x;
        y = symbol.y;
    }

    /**
     * Dreht die Turtle
     *
     * @param grad Drehwinkel (mathematisch positiver Drehsinn) im Gradmaß
     */
    public void Drehen(int grad)
    {
        symbol.Drehen(grad);
        winkel = symbol.winkel;
    }

    /**
     * Versetzt Zeichenfläche und Turtle in den Ausgangszustand
     */
    public void Löschen()
    {
        symbol.Löschen();
    }

    /**
     * Turtle wechselt in den Modus "nicht zeichnen"
     */
    public void StiftHeben()
    {
        symbol.StiftHeben();
        stiftUnten = false;
    }

    /**
     * Turtle wechselt in den Modus "zeichnen"
     */
    public void StiftSenken()
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
     * @return Winkel im Gradmass
     */
    public int WinkelGeben()
    {
        return winkel;
    }

    /**
     * Gibt die x-Koordinate der Turtle zurück
     *
     * @return x-Koordinate
     */
    public int XPositionGeben()
    {
        return x;
    }

    /**
     * Gibt die y-Koordinate der Turtle zurück
     *
     * @return y-Koordinate
     */
    public int YPositionGeben()
    {
        return y;
    }

    /**
     * Schaltet die Sichtbarkeit des Turtlesymbols ein oder aus. Erlaubte
     * Parameterwerte: true, false
     *
     * @param sichtbar (neue) Sichtbarkeit des Turtlesymbols
     */
    public void SichtbarkeitFürSymbolSetzen(boolean sichtbar)
    {
        symbol.SichtbarkeitFürSymbolSetzen(sichtbar);
    }

    /**
     * Testet, ob die Turtle eine Figur berührt.
     *
     * @return true, wenn die Turtlekoordinaten innerhalb einer Grafikfigur sind
     */
    public boolean Berührt()
    {
        return symbol.Berührt();
    }

    /**
     * Testet, ob die Turtle eine Figur in der angegebenen Farbe berührt.
     *
     * @param farbe die Farbe, die die berührte Figur haben muss.
     *
     * @return true, wenn die Turtlekoordinaten innerhalb einer Grafikfigur in
     *     der angegebenen Farbe sind
     */
    public boolean Berührt(String farbe)
    {
        return symbol.Berührt(farbe);
    }

    /**
     * Testet, ob die Turtle die angegebene Figur berührt.
     *
     * @param objekt das Objekt, das getestet werden soll.
     *
     * @return true, wenn die Turtlekoordinaten innerhalb der angegebenen
     *     Grafikfigur sind
     */
    public boolean Berührt(Object objekt)
    {
        return symbol.Berührt(objekt);
    }
}
