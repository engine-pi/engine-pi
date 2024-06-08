/*
 * Engine Omega ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2024 Josef Friedrich and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package rocks.friedrich.engine_omega.actor;

import java.awt.Color;
import java.awt.Graphics2D;

import rocks.friedrich.engine_omega.annotations.API;
import rocks.friedrich.engine_omega.physics.FixtureBuilder;
import static rocks.friedrich.engine_omega.resources.Container.colors;

/**
 * Beschreibt ein Gitter aus quadratischen Zellen.
 *
 * @author Josef Friedrich
 */
public class Grid extends Actor
{
    /**
     * Die Anzahl der Spalten in x-Richtung.
     */
    int cols;

    /**
     * Die Anzahl der Reihen in y-Richtung.
     */
    int rows;

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
    Color color = colors.getGreen();

    /**
     * Die Hintergrundfarbe.
     */
    Color background;

    /**
     * @param cols Die Anzahl der Spalten in x-Richtung.
     * @param rows Die Anzahl der Reihen in y-Richtung.
     * @param size Die Größe einer Zelle bzw. eines Quadrats in Pixelmeter. Ist
     *             beispielsweise die Einheit Pixelmeter auf 60 Pixel und dieses
     *             Attribut auf 2 gesetzt, dann werden die vom Gitter
     *             eingeschlossenen Quadrate 120 auf 120 Pixel groß.
     */
    public Grid(int cols, int rows, double size)
    {
        super(() -> FixtureBuilder.rectangle(cols * size, rows * size));
        this.cols = cols;
        this.rows = rows;
        this.size = size;
    }

    /**
     * Erstellt eine Gitter mit der Zellegröße von einem Pixelmeter.
     *
     * @param cols Die Anzahl der Spalten in x-Richtung.
     * @param rows Die Anzahl der Reihen in y-Richtung.
     */
    public Grid(int cols, int rows)
    {
        this(cols, rows, 1);
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
            g.fillRect(0, -Math.round(cellSize * rows),
                    Math.round(cellSize * cols), Math.round(cellSize * rows));
        }
        g.setColor(color);
        // Die Dicke der Linie in Pixel.
        int thickness = (int) Math.round(pixelPerMeter * lineThickness);
        if (thickness < 1)
        {
            thickness = 1;
        }
        // Länge der vertikalen Linien.
        int lengthVertical = Math.round(rows * cellSize);
        for (int x = 0; x <= cols; x++)
        {
            // Zeichnen der vertikalen Linien von links nach rechts.
            g.fillRect(Math.round(x * cellSize), -lengthVertical, thickness,
                    lengthVertical);
        }
        // Länge der horizontalen Linien.
        int lengthHorizontal = Math.round(cols * cellSize);
        for (int y = 0; y <= rows; y++)
        {
            // Zeichnen der horizontalen Linien von unten nach oben.
            g.fillRect(0, -Math.round(y * cellSize), lengthHorizontal,
                    thickness);
        }
    }
}
