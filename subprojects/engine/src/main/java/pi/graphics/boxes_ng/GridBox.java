/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
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
package pi.graphics.boxes_ng;

import java.util.ArrayList;

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/graphics/boxes/GridBoxDemo.java

import java.util.List;
import java.util.function.Consumer;

import pi.annotations.ChainableMethod;
import pi.annotations.Getter;
import pi.annotations.Setter;
import pi.debug.ToStringFormatter;

/**
 * Eine <b>Gitter</b>-Box, die mehrere untergeordnete Kinder-Boxen in Zeilen und
 * Spalten anordnet.
 *
 * <p>
 * Jede Zelle des Gitters wird in eine {@link CellBox} eingebettet, damit die
 * Positionierung und Ausrichtung der einzelnen Kinder separat angepasst werden
 * kann.
 * </p>
 *
 * @author Josef Friedrich
 */
public class GridBox<T extends Box> extends PaddingBox<T>
{
    int columns = 2;

    List<List<CellBox>> grid;

    /**
     * Erstellt eine neue Gitter-Box und baut das Gitter aus den angegebenen
     * Kinder-Boxen auf.
     *
     * @param childs Die Kinder-Boxen, die im Gitter angeordnet werden sollen.
     */
    public GridBox(Box... childs)
    {
        super(childs);
        buildGrid();
    }

    /**
     * Setzt die <b>Anzahl</b> der <b>Spalten</b> im Gitter.
     *
     * @param columns Die <b>Anzahl</b> der <b>Spalten</b> , die im Gitter
     *     verwendet werden sollen.
     *
     * @return Eine Referenz auf diese Gitter-Box, damit Setter-Ketten möglich
     *     sind.
     */
    @Setter
    @ChainableMethod
    public GridBox<T> columns(int columns)
    {
        this.columns = columns;
        buildGrid();
        return this;
    }

    /**
     * Gibt die <b>Spaltenanzahl</b> des Gitters zurück.
     *
     * @return Die <b>Spaltenanzahl</b>.
     */
    @Getter
    public int columnCount()
    {
        return columns;
    }

    /**
     * Gibt die <b>Reihenanzahl</b> des Gitters zurück.
     *
     * @return Die <b>Reihenanzahl</b>.
     */
    @Getter
    public int rowCount()
    {
        return (int) Math.ceil((double) numberOfChilds() / columns);
    }

    /**
     * Baut das interne Gitter anhand der aktuellen Kinder-Boxen und der
     * konfigurierten Spaltenanzahl auf.
     */
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

    private CellBox getChild(int row, int column)
    {
        int index = row * columnCount() + column;
        if (index < numberOfChilds())
        {
            return (CellBox) childs.get(index);
        }
        return null;
    }

    /**
     * Liefert die {@link CellBox}-Zeile mit dem angegebenen Index zurück.
     *
     * @param row Der Index der gewünschten Zeile.
     *
     * @return Die Zellen der angegebenen Zeile.
     */
    public List<CellBox> getRow(int row)
    {
        return grid.get(row);
    }

    /**
     * Wendet für jede Zelle einer Zeile einen Consumer an.
     *
     * @param row Der Index der Zeile.
     * @param consumer Die Funktion, die für jede gefundene Zelle aufgerufen
     *     wird.
     *
     * @return Eine Referenz auf diese Gitter-Box.
     */
    public GridBox<T> forEachRowBox(int row,
            Consumer<PopulatedCell<T>> consumer)
    {
        for (CellBox box : getRow(row))
        {
            if (box != null)
            {
                consumer.accept(new PopulatedCell<>(box));
            }
        }
        return this;
    }

    /**
     * Bestimmt die maximale Höhe der angegebenen Zeile.
     *
     * @param row Der Index der Zeile.
     *
     * @return Die größte Höhe aller Zellen in der Zeile.
     */
    public double getMaxHeightOfRow(int row)
    {
        double maxHeight = 0;
        for (Box box : getRow(row))
        {
            if (box != null && box.height > maxHeight)
            {
                maxHeight = box.height;
            }
        }
        return maxHeight;
    }

    /**
     * Liefert die {@link CellBox}-Spalte mit dem angegebenen Index zurück.
     *
     * @param column Der Index der gewünschten Spalte.
     *
     * @return Die Zellen der angegebenen Spalte.
     */
    public List<CellBox> getColumn(int column)
    {
        List<CellBox> childs = new ArrayList<>(rowCount());
        for (int row = 0; row < rowCount(); row++)
        {
            childs.add(grid.get(row).get(column));
        }
        return childs;
    }

    /**
     * Wendet für jede Zelle einer Spalte einen Consumer an.
     *
     * @param column Der Index der Spalte.
     * @param consumer Die Funktion, die für jede gefundene Zelle aufgerufen
     *     wird.
     *
     * @return Eine Referenz auf diese Gitter-Box.
     */
    public GridBox<T> forEachColumnBox(int column,
            Consumer<PopulatedCell<T>> consumer)
    {
        for (CellBox box : getColumn(column))
        {
            if (box != null)
            {
                consumer.accept(new PopulatedCell<>(box));
            }
        }
        return this;
    }

    /**
     * Wendet einen Consumer auf eine bestimmte Zelle im Gitter an.
     *
     * @param row Die Zeile der Zelle.
     * @param column Die Spalte der Zelle.
     * @param consumer Die Funktion, die für die Zelle aufgerufen wird.
     *
     * @return Eine Referenz auf diese Gitter-Box.
     */
    public GridBox<T> forBox(int row, int column,
            Consumer<PopulatedCell<T>> consumer)
    {
        CellBox box = grid.get(row).get(column);
        if (box != null)
        {
            consumer.accept(new PopulatedCell<>(box));
        }
        return this;
    }

    /**
     * Bestimmt die maximale Breite der angegebenen Spalte in Pixel.
     *
     * @param column Der Index der Spalte.
     *
     * @return Die größte Breite in Pixel aller Zellen in der Spalte.
     */
    public double getMaxWidthOfColumn(int column)
    {
        double maxWidth = 0;
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
            double maxWidth = getMaxWidthOfColumn(column);
            width += maxWidth;
            forEachColumnBox(column, b -> b.cell.width(maxWidth));
        }

        height = 0;
        for (int row = 0; row < rowCount(); row++)
        {
            double maxHeight = getMaxHeightOfRow(row);
            height += maxHeight;
            forEachRowBox(row, b -> b.cell.height(maxHeight));
        }
        width += (columnCount() + 1) * padding;
        height += (rowCount() + 1) * padding;
    }

    @Override
    protected void calculateAnchors()
    {
        double yCursor = y - height;
        for (int row = 0; row < rowCount(); row++)
        {
            double xCursor = x + padding;
            yCursor += getMaxHeightOfRow(row) + padding;
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
        }
    }

    @Override
    public ToStringFormatter toStringFormatter()
    {
        var formatter = super.toStringFormatter();
        formatter.prepend("columnCount", columnCount());
        formatter.prepend("rowCount", rowCount());
        return formatter;
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        return toStringFormatter().format();
    }
}
