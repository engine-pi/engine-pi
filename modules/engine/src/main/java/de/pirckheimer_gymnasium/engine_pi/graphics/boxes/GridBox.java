package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

import java.util.ArrayList;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/graphics/boxes/GridBoxDemo.java

import java.util.List;
import java.util.function.Consumer;

public class GridBox<T extends Box> extends PaddingBox
{
    int columns = 2;

    List<List<T>> grid;

    public GridBox(Box... childs)
    {
        super(childs);
        buildGrid();
    }

    public GridBox<T> columns(int columns)
    {
        this.columns = columns;
        buildGrid();
        return this;
    }

    /**
     * Gibt die Anzahl an Spalten zurück.
     *
     * @return Die Anzahl an Spalten.
     */
    public int columnCount()
    {
        return columns;
    }

    /**
     * Gibt die Anzahl an Reihen zurück.
     *
     * @return Die Anzahl an Reihen.
     */
    public int rowCount()
    {
        return (int) Math.ceil((double) numberOfChilds() / columns);
    }

    protected void buildGrid()
    {
        grid = new ArrayList<>(rowCount());

        for (int row = 0; row < rowCount(); row++)
        {
            grid.add(new ArrayList<>(columnCount()));
            for (int column = 0; column < columnCount(); column++)
            {
                grid.get(row).add(getChild(row, column));
            }
        }
    }

    @SuppressWarnings("unchecked")
    private T getChild(int row, int column)
    {
        int index = row * columnCount() + column;
        if (index < numberOfChilds())
        {
            return (T) childs.get(index);
        }
        return null;
    }

    public List<T> getRow(int row)
    {
        return grid.get(row);
    }

    public GridBox<T> row(int row, Consumer<T> consumer)
    {
        for (T box : getRow(row))
        {
            if (box != null)
            {
                consumer.accept(box);
            }
        }
        return this;
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

    public List<T> getColumn(int column)
    {
        List<T> childs = new ArrayList<>(rowCount());
        for (int row = 0; row < rowCount(); row++)
        {
            childs.add(grid.get(row).get(column));
        }
        return childs;
    }

    public GridBox<T> column(int column, Consumer<T> consumer)
    {
        for (T box : getColumn(column))
        {
            if (box != null)
            {
                consumer.accept(box);
            }
        }
        return this;
    }

    public GridBox<T> cell(int row, int column, Consumer<T> consumer)
    {
        T box = grid.get(row).get(column);
        if (box != null)
        {
            consumer.accept(box);
        }
        return this;
    }

    public GridBox<T> allCells(Consumer<T> consumer)
    {
        for (int row = 0; row < rowCount(); row++)
        {
            for (int column = 0; column < columnCount(); column++)
            {
                T box = grid.get(row).get(column);
                if (box != null)
                {
                    consumer.accept(box);
                }
            }
        }
        return this;
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
        width = 0;
        for (int column = 0; column < columnCount(); column++)
        {
            int maxWidth = getMaxWidthOfColumn(column);
            width += maxWidth;
            column(column, b -> b.width(maxWidth));
        }

        height = 0;
        for (int row = 0; row < rowCount(); row++)
        {
            int maxHeight = getMaxHeightOfRow(row);
            height += maxHeight;
            row(row, b -> b.height(maxHeight));
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
    }
}
