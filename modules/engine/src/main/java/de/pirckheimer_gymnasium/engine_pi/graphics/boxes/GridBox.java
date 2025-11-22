package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/graphics/boxes/GridBoxDemo.java

public class GridBox extends PaddingBox
{
    int columns = 2;

    Box[][] grid;

    public GridBox(Box... childs)
    {
        super(childs);
        buildGrid();
    }

    public GridBox columns(int columns)
    {
        this.columns = columns;
        buildGrid();
        return this;
    }

    /**
     * Gibt die Anzahl der Spalten zurück.
     *
     * @return Die Anzahl der Spalten.
     */
    public int columnCount()
    {
        return columns;
    }

    /**
     * Gibt die Anzahl der Reihen zurück.
     *
     * @return Die Anzahl der Reihen.
     */
    public int rowCount()
    {
        return (int) Math.ceil((double) numberOfChilds() / columns);
    }

    private void buildGrid()
    {
        grid = new Box[rowCount()][columnCount()];
        for (int column = 0; column < columnCount(); column++)
        {
            for (int row = 0; row < rowCount(); row++)
            {
                grid[row][column] = getChild(row, column);
            }
        }
    }

    private Box getChild(int row, int column)
    {
        int index = row * columnCount() + column;
        if (index < numberOfChilds())
        {
            return childs.get(index);
        }
        return null;
    }

    public Box[] getRow(int row)
    {
        return grid[row];
    }

    public int getMaxHeightOfRow(int row)
    {
        int maxHeight = 0;
        for (Box box : getRow(row))
        {
            if (box != null && box.height > maxHeight)
            {
                maxHeight = box.height;
            }
        }
        return maxHeight;
    }

    public Box[] getColumn(int column)
    {
        Box[] childs = new Box[rowCount()];
        for (int i = 0; i < childs.length; i++)
        {
            childs[i] = grid[i][column];
        }
        return childs;
    }

    public int getMaxWidthOfColumn(int column)
    {
        int maxWidth = 0;
        for (Box box : getColumn(column))
        {
            if (box != null && box.width > maxWidth)
            {
                maxWidth = box.width;
            }
        }
        return maxWidth;
    }

    @Override
    protected void calculateDimension()
    {
        for (Box child : childs)
        {
            child.calculateDimension();
        }

        width = 0;
        for (int column = 0; column < columnCount(); column++)
        {
            width += getMaxWidthOfColumn(column);
        }

        height = 0;
        for (int row = 0; row < rowCount(); row++)
        {
            height += getMaxHeightOfRow(row);
        }

        width += (columnCount() + 1) * padding;
        height += (rowCount() + 1) * padding;
    }

    @Override
    protected void calculateAnchors()
    {
        int yCursor = y + padding;
        for (int row = 0; row < rowCount(); row++)
        {
            int xCursor = x + padding;
            for (int column = 0; column < columnCount(); column++)
            {
                Box child = getChild(row, column);
                if (child != null)
                {
                    child.x = xCursor;
                    child.y = yCursor;
                }
                xCursor += getMaxWidthOfColumn(column) + padding;
            }
            yCursor += getMaxHeightOfRow(row) + padding;
        }

        for (Box child : childs)
        {
            child.calculateAnchors();
        }
    }
}
