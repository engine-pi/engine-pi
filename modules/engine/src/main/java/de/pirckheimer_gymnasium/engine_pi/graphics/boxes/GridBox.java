package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

import java.util.ArrayList;

public class GridBox extends ChildsBox
{
    int columns = 2;

    public GridBox(Box... childs)
    {
        super(childs);
    }

    public GridBox columns(int columns)
    {
        this.columns = columns;
        return this;
    }

    /**
     * Gibt die Anzahl der Spalten zurück.
     *
     * @return Die Anzahl der Spalten.
     */
    public int columns()
    {
        return columns;
    }

    /**
     * Gibt die Anzahl der Reihen zurück.
     *
     * @return Die Anzahl der Reihen.
     */
    public int rows()
    {
        return (int) Math.ceil((double) numberOfChilds() / columns);
    }

    public ArrayList<Box> getRow(int row)
    {
        // Anfangsindex
        final int start = row * columns;
        ArrayList<Box> rowChilds = new ArrayList<Box>();
        for (int i = start; i < Math.min(numberOfChilds(),
                start + columns); i++)
        {
            rowChilds.add(childs.get(i));
        }
        return rowChilds;
    }

    public ArrayList<Box> getColumn(int column)
    {
        ArrayList<Box> columnChilds = new ArrayList<Box>();
        for (int i = column; i < numberOfChilds(); i += columns())
        {
            columnChilds.add(childs.get(i));
        }
        return columnChilds;
    }
}
