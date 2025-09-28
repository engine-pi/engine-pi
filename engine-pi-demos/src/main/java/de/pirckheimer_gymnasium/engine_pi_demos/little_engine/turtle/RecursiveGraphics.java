package de.pirckheimer_gymnasium.engine_pi_demos.little_engine.turtle;

import de.pirckheimer_gymnasium.engine_pi.little_engine.Turtle;

/**
 * Rahmenklasse zum Zeichen von Mustern aus Quadraten
 *
 * <p>
 * Die ursprünglich deutsche Name dieser Klasse war {@code RekursiveGrafik}.
 * </p>
 *
 *
 * @author Albert Wiedemann
 *
 * @version 1.0
 */
class RecursiveDrawing
{
    /**
     * Die Turtle zum Zeichnen
     */
    Turtle turtle;

    /**
     * Der Konstruktor legt die Turtle an.
     */
    RecursiveDrawing()
    {
        turtle = new Turtle();
        turtle.setColor("blau");
        turtle.setPosition(400, 200);
    }

    /**
     * Zeichnet ein Quadrat mit gegebener Seitenlänge an der Stelle der Turtle
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code QuadratZeichnen}.
     * </p>
     *
     * @param length Seitenlänge des Quadrats
     */
    private void drawSquare(double length)
    {
        for (int i = 0; i < 4; i += 1)
        {
            turtle.move(length);
            turtle.rotate(-90);
        }
    }

    /**
     * Zeichnet ein Element der Perlenkette
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code PerlenketteZeichnen}.
     * </p>
     *
     * @param numberOfBeads (Rest-)anzahl der Perlen (Quadrate)
     */
    void drawBeadChain(int numberOfBeads)
    {
        if (numberOfBeads == 1)
        {
            drawSquare(10);
        }
        else
        {
            drawBeadChain(numberOfBeads - 1);
            turtle.move(20);
            drawSquare(10);
        }
    }

    /**
     * Zeichnet ein Element der Quadratmuster
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code QuadratmusterZeichnen}.
     * </p>
     *
     * @param anzahl (Rest-)anzahl der Quadrate
     */
    void drawSquarePattern(int anzahl)
    {
        if (anzahl > 0)
        {
            drawSquare(anzahl * 10);
            drawSquarePattern(anzahl - 1);
        }
    }

    /**
     * Zeichnet Quadratmuster im Kreis
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code MusterZeichnen}.
     * </p>
     *
     * @param gesamtanzahlQuadrate insgesamte Anzahl der Quadrate
     * @param nochZuZeichnendeQuadrate Anzahl der noch zu zeichnenden Quadrate
     */
    void MusterZeichnen(int gesamtanzahlQuadrate, int nochZuZeichnendeQuadrate)
    {
        if (nochZuZeichnendeQuadrate > 0)
        {
            turtle.liftPen();
            turtle.rotate(360 / gesamtanzahlQuadrate);
            turtle.move(50);
            turtle.lowerPen();
            drawSquare(20);
            turtle.liftPen();
            turtle.rotate(180);
            turtle.move(50);
            turtle.lowerPen();
            turtle.rotate(180);
            MusterZeichnen(gesamtanzahlQuadrate, nochZuZeichnendeQuadrate - 1);
        }
    }

    /**
     * Zeichnet Quadratmuster im Kreis mit variablem Radius
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code MusterZeichnen2}.
     * </p>
     *
     * @param anzahlQuadrate Anzahl der Quadrate
     * @param quadratNummer Nummer des aktuellen Quadrats
     * @param radius der Radius des Kreises mit den Quadraten
     */
    private void MusterZeichnen2(int anzahlQuadrate, int quadratNummer,
            int radius)
    {
        if (quadratNummer > 0)
        {
            turtle.liftPen();
            turtle.rotate(360 / anzahlQuadrate);
            turtle.move(radius);
            turtle.lowerPen();
            drawSquare(radius / 2);
            turtle.liftPen();
            turtle.rotate(180);
            turtle.move(radius);
            turtle.lowerPen();
            turtle.rotate(180);
            MusterZeichnen2(anzahlQuadrate, quadratNummer - 1, radius);
        }
    }

    /**
     * Zeichnet eine Folge immer kleinerer Quadratkreise
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code QuadratkreiseZeichnen}.
     * </p>
     *
     * @param anzahlQuadrate Anzahl der Quadrate
     * @param radius der Radius des Kreises mit den Quadraten
     */
    void QuadratkreiseZeichnen(int anzahlQuadrate, int radius)
    {
        if (radius >= 30)
        {
            MusterZeichnen2(anzahlQuadrate, anzahlQuadrate, radius);
            QuadratkreiseZeichnen(anzahlQuadrate, radius / 2);
        }
    }

    public static void main(String[] args)
    {
        RecursiveDrawing drawing = new RecursiveDrawing();

        drawing.drawBeadChain(10);
        // drawing.MusterZeichnen(10, 5);
        // grafik.MusterZeichnen2(4, 3, 2);
    }
}
