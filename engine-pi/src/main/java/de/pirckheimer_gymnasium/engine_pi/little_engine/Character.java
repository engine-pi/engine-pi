package de.pirckheimer_gymnasium.engine_pi.little_engine;

/**
 * Wrapperklasse für eine Figur auf der Zeichenfläche.
 *
 * <p>
 * Der ursprüngliche Name der Klasse war {@code Figur}.
 * </p>
 *
 * @author Albert Wiedemann
 *
 * @version 1.0
 */
public class Character
{
    /**
     * Die x-Position der Figur.
     */
    public int x;

    /**
     * Die y-Position der Figur.
     */
    public int y;

    /**
     * Die Größe der Figur.
     *
     * <p>
     * Der ursprünglich deutsche Name dieses Attributs war {@code größe}.
     * </p>
     */
    public int size;

    /**
     * Die Farbe der Figur.
     *
     * <p>
     * Der ursprünglich deutsche Name dieses Attributs war {@code farbe}.
     * </p>
     */
    public String color;

    /**
     * Die Sichtbarkeit der Figur.
     *
     * <p>
     * Der ursprünglich deutsche Name dieses Attributs war {@code sichtbar}.
     * </p>
     */
    public boolean visible;

    /**
     * Der Drehwinkel (mathemtisch positiver Drehsinn) der Turtle in Grad.
     *
     * <p>
     * Der ursprünglich deutsche Name dieses Attributs war {@code rotation}.
     * </p>
     */
    public int rotation;

    /**
     * Referenz auf das echte Figursymbol.
     */
    DrawingWindow.CharacterInternal symbol;

    /**
     * Referenz auf das Aktionsempfängerobjekt.
     */
    DrawingWindow.AktionsEmpfaenger aktionsEmpfänger;

    /**
     * Konstruktor der Figur Erzeugt eine Figur und versetzt sie in einen
     * gültigen Startzustand.
     */
    public Character()
    {
        symbol = (DrawingWindow.CharacterInternal) DrawingWindow
                .SymbolErzeugen(DrawingWindow.SymbolArt.figur);
        symbol.setVisibility(true);
        x = symbol.x;
        y = symbol.y;
        rotation = symbol.winkel;
        size = symbol.b;
        visible = symbol.sichtbar;
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
     * Methode wird aufgerufen, wenn die Figur handeln soll. Die vordefinierte
     * Methode tut nichts.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code AktionAusführen}.
     * </p>
     */
    public void AktionAusführen()
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
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code SonderTasteGedrückt}.
     * </p>
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
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code MausGeklickt}.
     * </p>
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
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code PositionSetzen}.
     * </p>
     *
     * @param x x-Position der Figur
     * @param y y-Position der Figur
     */
    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
        symbol.setPosition(x, y);
    }

    /**
     * Setzt die Größe des Figurensymbols.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code GrößeSetzen}.
     * </p>
     *
     * @param größe (neue) Größe
     */
    public void setSize(int größe)
    {
        this.size = größe;
        symbol.setSize(größe, größe);
    }

    /**
     * Setzt den Drehwinkel der Figur. Die Winkelangabe ist in Grad, positive
     * Werte drehen gegen den Uhrzeigersinn, negative Werte drehen im
     * Uhrzeigersinn (mathematisch positiver Drehsinn), d. h. 0˚: Figur schaut
     * nach rechts, 90˚: Figur schaut nach oben, 180˚: Figur schaut nach links,
     * 270˚bzw. -90˚: Figur schaut nach unten
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code WinkelSetzen}.
     * </p>
     *
     * @param winkel der (neue) Drehwinkel der Figur
     */
    public void setRotation(int winkel)
    {
        this.rotation = winkel;
        symbol.setRotation(winkel);
    }

    /**
     * Schaltet die Sichtbarkeit der Figur ein oder aus. Erlaubte
     * Parameterwerte: true, false
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code SichtbarkeitSetzen}.
     * </p>
     *
     * @param sichtbar (neue) Sichtbarkeit der Figur
     */
    public void setVisibility(boolean sichtbar)
    {
        this.visible = sichtbar;
        symbol.setVisibility(sichtbar);
    }

    /**
     * Entfernt die Figur aus dem Zeichenfenster.
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
     * Bringt die Figur eine Ebene nach vorn.
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
     * Bringt die Figur in die vorderste Ebene.
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
     * Bringt die Figur eine Ebene nach hinten.
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
     * Bringt die Figur in die hinterste Ebene.
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
     * Setzt die Figur wieder an ihre Ausgangsposition.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code ZumStartpunktGehen}.
     * </p>
     */
    public void ZumStartpunktGehen()
    {
        symbol.ZumStartpunktGehen();
        x = symbol.x;
        y = symbol.y;
        rotation = symbol.winkel;
    }

    /**
     * Bewegt die Figur nach vorne.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code Gehen}.
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
     * Dreht die Figur
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code Drehen}.
     * </p>
     *
     * @param grad Drehwinkel (mathematisch positiver Drehsinn) im Gradmaß
     */
    public void rotate(int grad)
    {
        symbol.Drehen(grad);
        rotation = symbol.winkel;
    }

    /**
     * Gibt den aktuellen Winkel der Figur zurück. Die Winkelangabe ist in Grad,
     * positive Werte drehen gegen den Uhrzeigersinn, negative Werte drehen im
     * Uhrzeigersinn (mathematisch positiver Drehsinn), d. h. 0˚: Figur schaut
     * nach rechts, 90˚: Figur schaut nach oben, 180˚: Figur schaut nach links,
     * 270˚bzw. -90˚: Figur schaut nach unten
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code WinkelGeben}.
     * </p>
     *
     * @return Winkel im Gradmaß
     */
    public int getRotation()
    {
        return rotation;
    }

    /**
     * Gibt die x-Koordinate der Figur zurück.
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
     * Gibt die y-Koordinate der Figur zurück.
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
     * Testet, ob die Figur eine Grafik-Figur berührt.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code Berührt}.
     * </p>
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
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code Berührt}.
     * </p>
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
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code Berührt}.
     * </p>
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
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code FigurteilFestlegenRechteck}.
     * </p>
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
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code FigurteilFestlegenEllipse}.
     * </p>
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
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code FigurteilFestlegenDreieck}.
     * </p>
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
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code EigeneFigurLöschen}.
     * </p>
     */
    public void EigeneFigurLöschen()
    {
        symbol.EigeneFigurLöschen();
    }
}
