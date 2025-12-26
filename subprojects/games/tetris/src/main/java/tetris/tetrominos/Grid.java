/*
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
package tetris.tetrominos;

/**
 * Ein Blockgitter, das die Positionen aller Blöcke speichert.
 *
 * <p>
 * Das „Herz“ dieser Klasse ist ein zweidimensionales Array/Feld aus Blöcken.
 * </p>
 *
 * @author Josef Friedrich
 */
public class Grid
{
    /**
     * Das zweidimensionale Array, das die Blöcke speichert.
     *
     * <p>
     * Die erste Dimension ist die x-Koordinate, die zweite die y-Koordinate.
     * </p>
     */
    private final Block[][] grid;

    public Grid(int width, int height)
    {
        grid = new Block[width][height];
    }

    /**
     * Gibt die Breite des Blockgitters zurück, also die Anzahl der Blöcke in
     * x-Richtung.
     *
     * @return Die Breite des Blockgitters.
     */
    public int getWidth()
    {
        return grid.length;
    }

    /**
     * Gibt die Höhe des Blockgitters zurück, also die Anzahl der Blöcke in
     * y-Richtung.
     *
     * @return Die Höhe des Blockgitters.
     */
    public int getHeight()
    {
        return grid[0].length;
    }

    /**
     * Fügt einen Block zum Blockgitter hinzu.
     *
     * @param block Der Block, der hinzugefügt werden soll.
     */
    public void addBlock(Block block)
    {
        assert grid[block.getX()][block.getY()] == null;
        grid[block.getX()][block.getY()] = block;
    }

    /**
     * Entfernt einen Block aus dem Blockgitter.
     *
     * @param block Der Block, der entfernt werden soll.
     */
    public void removeBlock(Block block)
    {
        grid[block.getX()][block.getY()] = null;
    }

    /**
     * Überprüft, ob die angegebene Koordinate im Blockgitter besetzt ist.
     *
     * @param x Die entsprechende x-Koordinate der zu überprüfenden Position.
     * @param y Die entsprechende y-Koordinate der zu überprüfenden Position.
     *
     * @return Wahr, wenn die Koordinate besetzt ist, sonst falsch.
     */
    public boolean isTaken(int x, int y)
    {
        return x < 0 || x >= getWidth() || y < 0 || y >= getHeight()
                || grid[x][y] != null;
    }

    /**
     * Überprüft, ob eine Zeile mit Blöcken ausgefüllt ist.
     *
     * @param y Die y-Koordinate ({@code 0} ist die unterste Zeile).
     *
     * @return Wahr, wenn die angegebene Zeile mit Blöcken ausgefüllt ist.
     */
    private boolean isRowFull(int y)
    {
        for (int x = 0; x < getWidth(); x++)
        {
            if (grid[x][y] == null)
            {
                return false;
            }
        }
        return true;
    }

    public FilledRowRange getFilledRowRange()
    {
        int from = -1;
        int to = -1;
        for (int y = 0; y < getHeight(); y++)
        {
            if (isRowFull(y))
            {
                if (from == -1)
                {
                    from = y;
                }
                to = y;
            }
        }
        if (from > -1 && to > -1)
        {
            return new FilledRowRange(from, to);
        }
        return null;
    }

    /**
     * Löscht alle Blöcke aus einer Zeile.
     *
     * @param y Die y-Koordinate der Zeile aus der Blöcke gelöscht werden
     *     sollen.
     */
    public void clearRow(int y)
    {
        for (int x = 0; x < getWidth(); x++)
        {
            if (isTaken(x, y))
            {
                grid[x][y].remove();
                grid[x][y] = null;
            }
        }
    }

    /**
     * Leert das ganze Gitter.
     *
     * @see tetris.debug.GridDebugScene
     */
    public void clear()
    {
        for (int y = 0; y < getHeight(); y++)
        {
            clearRow(y);
        }
    }

    /**
     * Entfernt die Blöcke der vollen Zeilen aus dem Spiel.
     *
     * @param range Der getilgte Bereich mit vollen Zeilen.
     */
    public void removeFilledRowRange(FilledRowRange range)
    {
        if (range == null)
        {
            return;
        }
        for (int y = range.getFrom(); y <= range.getTo(); y++)
        {
            clearRow(y);
        }
    }

    /**
     * Löst einen Erdrutsch (landslide) aus, das heißt alle Blöcke oberhalb des
     * getilgten Bereichs werden nach unten bewegt.
     *
     * @param range Der getilgte Bereich mit vollen Zeilen.
     */
    public void triggerLandslide(FilledRowRange range)
    {
        if (range == null)
        {
            return;
        }
        for (int y = range.getTo() + 1; y < getHeight(); y++)
        {
            for (int x = 0; x < getWidth(); x++)
            {
                if (isTaken(x, y))
                {
                    Block block = grid[x][y];
                    block.moveBy(0, -range.getRowCount());
                    grid[x][y] = null;
                    grid[x][y - range.getRowCount()] = block;
                }
            }
        }
    }

    /**
     * Gibt eine Textrepräsentation des Blockgitters und der momentan
     * enthaltenen Blöcke aus.
     *
     * <p>
     * Diese Methode ist nur für Testzwecke gedacht.
     * </p>
     */
    public void print()
    {
        String horizontalLine = "-".repeat(getWidth() + 2);
        System.out.println(horizontalLine);
        for (int y = getHeight() - 1; y > -1; y--)
        {
            System.out.print('|');
            for (int x = 0; x < getWidth(); x++)
            {
                if (grid[x][y] == null)
                {
                    System.out.print(" ");
                }
                else
                {
                    System.out.print(grid[x][y].getChar());
                }
            }
            System.out.println('|');
        }
        System.out.println(horizontalLine);
    }
}
