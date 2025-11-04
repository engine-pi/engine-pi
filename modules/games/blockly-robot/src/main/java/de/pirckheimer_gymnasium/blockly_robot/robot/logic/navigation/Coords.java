package de.pirckheimer_gymnasium.blockly_robot.robot.logic.navigation;

/**
 * Ein Punkt auf dem Gitter. Der Ursprung ist links oben (Reihe 0 und Spalte 0).
 */
public class Coords
{
    protected int col;

    protected int row;

    public Coords(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }

    public Coords east()
    {
        col++;
        return this;
    }

    public Coords south()
    {
        row++;
        return this;
    }

    public Coords west()
    {
        col--;
        return this;
    }

    public Coords north()
    {
        row--;
        return this;
    }

    @Override
    public String toString()
    {
        return "Coords [row=" + row + ", col=" + col + "]";
    }
}
