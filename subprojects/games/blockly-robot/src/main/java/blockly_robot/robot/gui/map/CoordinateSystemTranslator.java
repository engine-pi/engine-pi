package blockly_robot.robot.gui.map;

import blockly_robot.robot.logic.navigation.Coords;
import pi.graphics.geom.Vector;

public class CoordinateSystemTranslator
{
    /**
     * Anzahl an Reihen (y-Richtung bzw. Höhe)
     */
    public int rows;

    /**
     * Anzahl an Spalten (x-Richtung bzw. Breite)
     */
    public int cols;

    /**
     * Die x-Koordinate des linken unteren Ecks, an dem das Gitter im
     * Engine-Alpha-Koordinatensystem verankert ist.
     */
    public double x;

    /**
     * Die y-Koordinate des linken unteren Ecks, an dem das Gitter im
     * Engine-Alpha-Koordinatensystem verankert ist.
     */
    public double y;

    public CoordinateSystemTranslator(int rows, int cols, double x, double y)
    {
        this.rows = rows;
        this.cols = cols;
        this.x = x;
        this.y = y;
    }

    public CoordinateSystemTranslator(int rows, int cols)
    {
        this.rows = rows;
        this.cols = cols;
    }

    public void setPosition(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * y-Koordinate des Ursprungs des Gitters (links oben)
     */
    public double row0y()
    {
        return y + rows - 1;
    }

    public int toRow(double y)
    {
        return (int) Math.round(row0y() - y);
    }

    public int toCol(double x)
    {
        return (int) Math.round(x - this.x);
    }

    public double toX(int col)
    {
        return col + x;
    }

    public double toY(int row)
    {
        return row0y() - row;
    }

    /**
     * @param vector Ein Punkt im Engine-Alpha-Koordinatensystem.
     */
    public Coords toPoint(Vector vector)
    {
        return new Coords(toRow(vector.y()), toCol(vector.x()));
    }

    public Coords toPoint(double x, double y)
    {
        return toPoint(new Vector(x, y));
    }

    public Vector toVector(Coords point)
    {
        return new Vector(toX(point.getCol()), toY(point.getRow()));
    }

    public Vector toVector(int row, int col)
    {
        return toVector(new Coords(row, col));
    }
}
