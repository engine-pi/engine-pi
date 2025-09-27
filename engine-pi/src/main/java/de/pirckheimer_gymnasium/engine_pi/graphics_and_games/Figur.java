package de.pirckheimer_gymnasium.engine_pi.graphics_and_games;

/**
 * Wrapperklasse für die Turtle auf der Zeichenfläche.
 *
 * @author Albert Wiedemann
 *
 * @version 1.0
 */
public class Figur
{
    /**
     * x-Position der Figur.
     */
    public int x;

    /**
     * y-Position der Figur.
     */
    public int y;

    /**
     * Größe der Figur.
     */
    public int größe;

    /**
     * Farbe der Figur.
     */
    public String farbe;

    /**
     * Sichtbarkeit der Figur.
     */
    public boolean sichtbar;

    /**
     * Drehwinkel (mathemtisch positiver Drehsinn) der Turtle in Grad.
     */
    public int winkel;

    /**
     * Referenz auf das echte Figursymbol.
     */
    Zeichenfenster.FigurIntern symbol;

    /**
     * Referenz auf das Aktionsempfängerobjekt.
     */
    Zeichenfenster.AktionsEmpfaenger aktionsEmpfänger;

    /**
     * Konstruktor der Figur Erzeugt eine Figur und versetzt sie in einen
     * gültigen Startzustand.
     */
    public Figur()
    {
        symbol = (Zeichenfenster.FigurIntern) Zeichenfenster
                .SymbolErzeugen(Zeichenfenster.SymbolArt.figur);
        symbol.SichtbarkeitSetzen(true);
        x = symbol.x;
        y = symbol.y;
        winkel = symbol.winkel;
        größe = symbol.b;
        sichtbar = symbol.sichtbar;
        aktionsEmpfänger = new Zeichenfenster.AktionsEmpfaenger()
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
        Zeichenfenster.AktionsEmpfängerEintragen(aktionsEmpfänger);
    }

    /**
     * Methode wird aufgerufen, wenn die Figur handeln soll. Die vordefinierte
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
        // System. out. println ("Maus: (" + x + "|" + y + "), " + anzahl + "
        // mal");
    }

    /**
     * Setzt die Position der Figur.
     *
     * @param x x-Position der Figur
     * @param y y-Position der Figur
     */
    public void PositionSetzen(int x, int y)
    {
        this.x = x;
        this.y = y;
        symbol.PositionSetzen(x, y);
    }

    /**
     * Setzt die Größe des Figurensymbols.
     *
     * @param größe (neue) Größe
     */
    public void GrößeSetzen(int größe)
    {
        this.größe = größe;
        symbol.GrößeSetzen(größe, größe);
    }

    /**
     * Setzt den Drehwinkel der Figur. Die Winkelangabe ist in Grad, positive
     * Werte drehen gegen den Uhrzeigersinn, negative Werte drehen im
     * Uhrzeigersinn (mathematisch positiver Drehsinn), d. h. 0˚: Figur schaut
     * nach rechts, 90˚: Figur schaut nach oben, 180˚: Figur schaut nach links,
     * 270˚bzw. -90˚: Figur schaut nach unten
     *
     * @param winkel der (neue) Drehwinkel der Figur
     */
    public void WinkelSetzen(int winkel)
    {
        this.winkel = winkel;
        symbol.WinkelSetzen(winkel);
    }

    /**
     * Schaltet die Sichtbarkeit der Figur ein oder aus. Erlaubte
     * Parameterwerte: true, false
     *
     * @param sichtbar (neue) Sichtbarkeit der Figur
     */
    public void SichtbarkeitSetzen(boolean sichtbar)
    {
        this.sichtbar = sichtbar;
        symbol.SichtbarkeitSetzen(sichtbar);
    }

    /**
     * Entfernt die Figur aus dem Zeichenfenster.
     */
    public void Entfernen()
    {
        Zeichenfenster.AktionsEmpfängerEntfernen(aktionsEmpfänger);
        symbol.Entfernen();
    }

    /**
     * Bringt die Figur eine Ebene nach vorn.
     */
    public void NachVornBringen()
    {
        symbol.NachVornBringen();
    }

    /**
     * Bringt die Figur in die vorderste Ebene.
     */
    public void GanzNachVornBringen()
    {
        symbol.GanzNachVornBringen();
    }

    /**
     * Bringt die Figur eine Ebene nach hinten.
     */
    public void NachHintenBringen()
    {
        symbol.NachHintenBringen();
    }

    /**
     * Bringt die Figur in die hinterste Ebene.
     */
    public void GanzNachHintenBringen()
    {
        symbol.GanzNachHintenBringen();
    }

    /**
     * Setzt die Figur wieder an ihre Ausgangsposition.
     */
    public void ZumStartpunktGehen()
    {
        symbol.ZumStartpunktGehen();
        x = symbol.x;
        y = symbol.y;
        winkel = symbol.winkel;
    }

    /**
     * Bewegt die Figur nach vorne.
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
     * Dreht die Figur
     *
     * @param grad Drehwinkel (mathematisch positiver Drehsinn) im Gradmaß
     */
    public void Drehen(int grad)
    {
        symbol.Drehen(grad);
        winkel = symbol.winkel;
    }

    /**
     * Gibt den aktuellen Winkel der Figur zurück. Die Winkelangabe ist in Grad,
     * positive Werte drehen gegen den Uhrzeigersinn, negative Werte drehen im
     * Uhrzeigersinn (mathematisch positiver Drehsinn), d. h. 0˚: Figur schaut
     * nach rechts, 90˚: Figur schaut nach oben, 180˚: Figur schaut nach links,
     * 270˚bzw. -90˚: Figur schaut nach unten
     *
     * @return Winkel im Gradmaß
     */
    public int WinkelGeben()
    {
        return winkel;
    }

    /**
     * Gibt die x-Koordinate der Figur zurück.
     *
     * @return x-Koordinate
     */
    public int XPositionGeben()
    {
        return x;
    }

    /**
     * Gibt die y-Koordinate der Figur zurück.
     *
     * @return y-Koordinate
     */
    public int YPositionGeben()
    {
        return y;
    }

    /**
     * Testet, ob die Figur eine Grafik-Figur berührt.
     *
     * @return true, wenn die Figur und eine Grafikfigur überlappen
     */
    public boolean Berührt()
    {
        return symbol.Berührt();
    }

    /**
     * Testet, ob die Figur eine Grafik-Figur in der angegebenen Farbe berührt.
     *
     * @param farbe die Farbe, die die berührte Figur haben muss
     *
     * @return true, wenn die Figur und eine Grafikfigur in der angegebenen
     *     Farbe überlappen
     */
    public boolean Berührt(String farbe)
    {
        return symbol.Berührt(farbe);
    }

    /**
     * Testet, ob die Figur die angegebene Figur berührt.
     *
     * @param objekt das Objekt, das getestet werden soll
     *
     * @return true, wenn die Figur die angegebene Grafikfigur überlappen
     */
    public boolean Berührt(Object objekt)
    {
        return symbol.Berührt(objekt);
    }

    /**
     * Erzeugt ein neues, rechteckiges Element einer eigenen Darstellung der
     * Figur. Alle Werte beziehen sich auf eine Figur der Größe 100x100 und den
     * Koordinaten (0|0) in der Mitte des Quadrats
     *
     * @param x x-Wert der linken oberen Ecke des Rechtecks
     * @param y y-Wert der linken oberen Ecke des Rechtecks
     * @param breite Breite des Rechtecks
     * @param höhe Höhe des Rechtecks
     * @param farbe (Füll-)Farbe des Rechtecks
     */
    public void FigurteilFestlegenRechteck(int x, int y, int breite, int höhe,
            String farbe)
    {
        symbol.FigurteilFestlegenRechteck(x, y, breite, höhe, farbe);
    }

    /**
     * Erzeugt ein neues, elliptisches Element einer eigenen Darstellung der
     * Figur. Alle Werte beziehen sich auf eine Figur der Größe 100x100 und den
     * Koordinaten (0|0) in der Mitte des Quadrats
     *
     * @param x x-Wert der linken oberen Ecke des umgebenden Rechtecks der
     *     Ellipse
     * @param y y-Wert der linken oberen Ecke des umgebenden Rechtecks der
     *     Ellipse
     * @param breite Breite des umgebenden Rechtecks der Ellipse
     * @param höhe Höhe des umgebenden Rechtecks der Ellipse
     * @param farbe (Füll-)Farbe der Ellipse
     */
    public void FigurteilFestlegenEllipse(int x, int y, int breite, int höhe,
            String farbe)
    {
        symbol.FigurteilFestlegenEllipse(x, y, breite, höhe, farbe);
    }

    /**
     * Erzeugt ein neues, dreieckiges Element einer eigenen Darstellung der
     * Figur. Alle Werte beziehen sich auf eine Figur der Größe 100x100 und den
     * Koordinaten (0|0) in der Mitte des Quadrats
     *
     * @param x1 x-Wert des ersten Punkts des Dreiecks
     * @param y1 y-Wert des ersten Punkts des Dreiecks
     * @param x2 x-Wert des zweiten Punkts des Dreiecks
     * @param y2 y-Wert des zweiten Punkts des Dreiecks
     * @param x3 x-Wert des dritten Punkts des Dreiecks
     * @param y3 y-Wert des dritten Punkts des Dreiecks
     * @param farbe (Füll)Farbe des Dreiecks
     */
    public void FigurteilFestlegenDreieck(int x1, int y1, int x2, int y2,
            int x3, int y3, String farbe)
    {
        symbol.FigurteilFestlegenDreieck(x1, y1, x2, y2, x3, y3, farbe);
    }

    /**
     * Löscht die Vereinbarung für die eigene Darstellung der Figur. Die Figur
     * wird wieder durch die Originalfigur dargestellt.
     */
    public void EigeneFigurLöschen()
    {
        symbol.EigeneFigurLöschen();
    }
}
