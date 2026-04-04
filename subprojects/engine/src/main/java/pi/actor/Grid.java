/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
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
package pi.actor;

import static pi.Controller.colorScheme;

import java.awt.Color;
import java.awt.Graphics2D;

import pi.annotations.API;
import pi.annotations.ChainableMethod;
import pi.annotations.Getter;
import pi.annotations.Internal;
import pi.annotations.Setter;
import pi.physics.FixtureBuilder;

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/main-classes/actor/grid.md

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/test/java/pi/actor/GridTest.java

/**
 * Beschreibt ein <b>Gitter</b> aus quadratischen Zellen.
 *
 * @author Josef Friedrich
 */
public class Grid extends Actor
{
    /**
     * Die Anzahl der <b>Spalten</b> in x-Richtung.
     */
    int cols;

    /**
     * Die Anzahl der <b>Reihen</b> in y-Richtung.
     */
    int rows;

    /**
     * Die Größe einer Zelle bzw. eines Quadrats in Meter.
     *
     * <p>
     * Ist beispielsweise die Einheit Meter auf 60 Pixel und dieses Attribut auf
     * 2 gesetzt, dann werden die vom Gitter eingeschlossenen Quadrate 120 auf
     * 120 Pixel groß.
     * </p>
     */
    double size = 1;

    /**
     * Die Dicke der Linien in Meter.
     */
    double lineThickness = 0.02;

    /**
     * Die Hintergrundfarbe.
     */
    Color background;

    /**
     * @param cols Die Anzahl der Spalten in x-Richtung.
     * @param rows Die Anzahl der Reihen in y-Richtung.
     * @param size Die Größe einer Zelle bzw. eines Quadrats in Meter. Ist
     *     beispielsweise die Einheit Meter auf 60 Pixel und dieses Attribut auf
     *     2 gesetzt, dann werden die vom Gitter eingeschlossenen Quadrate 120
     *     auf 120 Pixel groß.
     */
    public Grid(int cols, int rows, double size)
    {
        super(null);
        this.cols = cols;
        this.rows = rows;
        this.size = size;
        color = colorScheme.get().green();
        update();
    }

    /**
     * Erstellt ein Gitter mit der Zellengröße von einem Meter.
     *
     * @param cols Die Anzahl der Spalten in x-Richtung.
     * @param rows Die Anzahl der Reihen in y-Richtung.
     */
    public Grid(int cols, int rows)
    {
        this(cols, rows, 1);
    }

    /* cols */

    /**
     * Gibt die <b>Anzahl der Spalten</b> in x-Richtung zurück.
     *
     * @return Die {@code Anzahl der Spalten} in x-Richtung.
     */
    @API
    @Getter
    public int cols()
    {
        return cols;
    }

    /**
     * Setzt die <b>Anzahl der Spalten</b> in x-Richtung.
     *
     * @param cols Die {@code Anzahl der Spalten} in x-Richtung.
     */
    @API
    @Setter
    @ChainableMethod
    public Grid cols(int cols)
    {
        this.cols = cols;
        update();

        return this;
    }

    /* rows */

    /**
     * Gibt die <b>Anzahl der Reihen</b> in y-Richtung zurück.
     *
     * @return Die {@code Anzahl der Reihen} in y-Richtung.
     */
    @API
    @Getter
    public int rows()
    {
        return rows;
    }

    /**
     * Setzt die <b>Anzahl der Reihen</b> in y-Richtung.
     *
     * @param rows Die {@code Anzahl der Reihen} in y-Richtung.
     */
    @API
    @Setter
    @ChainableMethod
    public Grid rows(int rows)
    {
        this.rows = rows;
        update();

        return this;
    }

    /* size */

    /**
     * Gibt die <b>Größe einer Zelle bzw. eines Quadrats</b> in Meter zurück.
     *
     * @return Die {@code Größe einer Zelle bzw. eines Quadrats} in Meter.
     */
    @API
    @Getter
    public double size()
    {
        return size;
    }

    /**
     * Setzt die <b>Größe einer Zelle bzw. eines Quadrats</b> in Meter.
     *
     * @param size Die {@code Größe einer Zelle bzw. eines Quadrats} in Meter.
     */
    @API
    @Setter
    @ChainableMethod
    public Grid size(double size)
    {
        this.size = size;
        update();
        return this;
    }

    /* lineThickness */

    /**
     * Gibt die <b>Dicke der Linien</b> in Meter zurück.
     *
     * @return Die {@code Dicke der Linien} in Meter.
     */
    @API
    @Getter
    public double lineThickness()
    {
        return lineThickness;
    }

    /**
     * Setzt die <b>Dicke der Linien</b> in Meter.
     *
     * @param lineThickness Die {@code Dicke der Linien} in Meter.
     */
    @API
    @Setter
    @ChainableMethod
    public Grid lineThickness(double lineThickness)
    {
        this.lineThickness = lineThickness;
        return this;
    }

    /* background */

    /**
     * Gibt die <b>Hintergrundfarbe</b> zurück.
     *
     * @return Die <b>Hintergrundfarbe</b>.
     */
    @API
    @Getter
    public Color background()
    {
        return background;
    }

    /**
     * Setzt die <b>Hintergrundfarbe</b>.
     *
     * @param background Die <b>Hintergrundfarbe</b>.
     */
    @API
    @Setter
    @ChainableMethod
    public void background(Color background)
    {
        this.background = background;
    }

    /**
     * @hidden
     */
    @Internal
    public void update()
    {
        fixture(() -> FixtureBuilder.rectangle(cols * size, rows * size));
    }

    /**
     * @hidden
     */
    @Internal
    @Override
    public void render(Graphics2D g, double pixelPerMeter)
    {
        // Die Größe in Pixel einer Zelle.
        int cellSize = (int) Math.round(pixelPerMeter * size);
        // Zeichnen eines Rechtecks, dass die Hintergrundfarbe darstellt.
        if (background != null)
        {
            g.setColor(background);
            g.fillRect(0,
                -Math.round(cellSize * rows),
                Math.round(cellSize * cols),
                Math.round(cellSize * rows));
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
            g.fillRect(Math.round(x * cellSize),
                -lengthVertical,
                thickness,
                lengthVertical);
        }
        // Länge der horizontalen Linien.
        int lengthHorizontal = Math.round(cols * cellSize);
        for (int y = 0; y <= rows; y++)
        {
            // Zeichnen der horizontalen Linien von unten nach oben.
            g.fillRect(0,
                -Math.round(y * cellSize),
                lengthHorizontal,
                thickness);
        }
    }
}
