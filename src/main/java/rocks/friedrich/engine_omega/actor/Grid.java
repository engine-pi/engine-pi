package rocks.friedrich.engine_omega.actor;

import java.awt.Color;
import java.awt.Graphics2D;

import rocks.friedrich.engine_omega.annotations.API;
import rocks.friedrich.engine_omega.physics.FixtureBuilder;

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
     * Die Dicke der Linien in Pixelmeter.
     */
    double lineThickness = 0.02;

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
        super(() -> FixtureBuilder.rectangle(numX * size, numY * size));
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
     * Setzt die Dicke der Linien in Pixelmeter.
     *
     * @param lineThickness Die Dicke der Linien in Pixelmeter.
     */
    public void setLineThickness(double lineThickness)
    {
        this.lineThickness = lineThickness;
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
        // Die Größe in Pixel einer Zelle.
        int cellSize = (int) Math.round(pixelPerMeter * size);
        // Zeichnen eines Rechtecks, dass die Hintergrundfarbe darstellt.
        if (background != null)
        {
            g.setColor(background);
            g.fillRect(0, -Math.round(cellSize * numY),
                    Math.round(cellSize * numX), Math.round(cellSize * numY));
        }
        g.setColor(color);
        // Die Dicke der Linie in Pixel.
        int thickness = (int) Math.round(pixelPerMeter * lineThickness);
        if (thickness < 1)
        {
            thickness = 1;
        }
        // Länge der vertikalen Linien.
        int lengthVertical = Math.round(numY * cellSize);
        for (int gridX = 0; gridX <= numX; gridX++)
        {
            // Zeichnen der vertikalen Linien von links nach rechts.
            g.fillRect(Math.round(gridX * cellSize), -lengthVertical, thickness,
                    lengthVertical);
        }
        // Länge der horizontalen Linien.
        int lengthHorizontal = Math.round(numX * cellSize);
        for (int gridY = 0; gridY <= numY; gridY++)
        {
            // Zeichnen der horizontalen Linien von unten nach oben.
            g.fillRect(0, -Math.round(gridY * cellSize), lengthHorizontal,
                    thickness);
        }
    }
}
