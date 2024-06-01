package rocks.friedrich.engine_omega.actor;

import java.awt.Color;
import java.awt.Graphics2D;

import rocks.friedrich.engine_omega.FixtureBuilder;
import rocks.friedrich.engine_omega.annotations.API;

/**
 * Ein Gitter aus quadratischen Zellen.
 */
public class Grid extends Actor
{
    /**
     * Die Anzahl der Spalten in x-Richtung.
     */
    int numX;

    /**
     * Die Anzahl der Reihen in y-Richtung.
     */
    int numY;

    /**
     * Die Größe einer Zelle bzw. eines Quadrats in Pixelmeter. Ist
     * beispielsweise die Einheit Pixelmeter auf 60 Pixel und dieses Attribut
     * auf 2 gesetzt, dann werden die vom Gitter eingeschlossenen Quadrate 120
     * auf 120 Pixel groß.
     */
    double size = 1;

    /**
     * Die Farbe der Gitterlinien.
     */
    Color color = Color.GREEN;

    /**
     * Die Hintergrundfarbe.
     */
    Color background;

    /**
     * @param numX Die Anzahl der Spalten in x-Richtung.
     * @param numY Die Anzahl der Reihen in y-Richtung.
     * @param size Die Größe einer Zelle bzw. eines Quadrats in Pixelmeter. Ist
     *             beispielsweise die Einheit Pixelmeter auf 60 Pixel und dieses
     *             Attribut auf 2 gesetzt, dann werden die vom Gitter
     *             eingeschlossenen Quadrate 120 auf 120 Pixel groß.
     */
    public Grid(int numX, int numY, double size)
    {
        super(() -> FixtureBuilder.createSimpleRectangularFixture(numX * size,
                numY * size));
        this.numX = numX;
        this.numY = numY;
        this.size = size;
    }

    /**
     * Erstellt eine Gitter mit der Zellegröße von einem Pixelmeter.
     *
     * @param numX Die Anzahl der Spalten in x-Richtung.
     * @param numY Die Anzahl der Reihen in y-Richtung.
     */
    public Grid(int numX, int numY)
    {
        this(numX, numY, 1);
    }

    /**
     * Setzt die Farbe der Gitterlinien.
     *
     * @param color Die Farbe der Gitterlinien.
     */
    @API
    public void setColor(Color color)
    {
        this.color = color;
    }

    /**
     * Setzt die Hintergrundfarbe.
     *
     * @param color Die Hintergrundfarbe.
     */
    @API
    public void setBackground(Color color)
    {
        background = color;
    }

    @Override
    public void render(Graphics2D g, double pixelPerMeter)
    {
        int gridSize = (int) Math.round(pixelPerMeter * size);
        if (background != null)
        {
            g.setColor(background);
            g.fillRect(0, -Math.round(gridSize * numY),
                    Math.round(gridSize * numX), Math.round(gridSize * numY));
        }
        g.setColor(color);
        // Zeichnen der vertikalen Linien von links nach rechts.
        for (int gridX = 0; gridX <= numX; gridX++)
        {
            g.fillRect(Math.round(gridX * gridSize),
                    -Math.round(numY * gridSize), 1,
                    Math.round(numY * gridSize));
        }
        // Zeichnen der horizontalen Linien von unten nach oben.
        for (int gridY = 0; gridY <= numY; gridY++)
        {
            g.fillRect(0, -Math.round(gridY * gridSize),
                    Math.round(numX * gridSize), 1);
        }
    }
}
