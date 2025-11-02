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
package de.pirckheimer_gymnasium.tetris.tetrominos;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

/**
 * Ein Tetromino ist ein Spielstein in Form von vier Blöcken.
 *
 * @author Josef Friedrich
 */
public abstract class Tetromino
{
    /**
     * Die Reihenfolge der Tetrominos wurde so übernommen, wie sie für den
     * damaligen <a href=
     * "https://harddrop.com/wiki/File:GBrandomizer.png">Zufallsgenerator</a>
     * des ursprünglichen Gameboys aufgereiht wurden.
     */
    public static String[] names = { "L", "J", "I", "O", "Z", "S", "T" };

    /**
     * Eine Referenz auf die {@link Scene Szene}, in der der Tetromino erstellt
     * wurde. Diese Referenz wird an die Block-Klasse weitergereicht.
     */
    protected Scene scene;

    protected Grid grid;

    protected String name;

    /**
     * Die x-Koordinate des Tetrominos entspricht der Lage des ersten Blocks,
     * also dem Block mit der Indexnummer 0 im Blockfeld.
     */
    protected int x;

    /**
     * Die y-Koordinate des Tetrominos entspricht der Lage des ersten Blocks,
     * also dem Block mit der Indexnummer 0 im Blockfeld.
     */
    protected int y;

    protected Block[] blocks;

    /**
     * Die Rotation des Tetrominos.
     */
    public int rotation;

    /**
     * Erzeugt ein Tetromino durch Angabe des Names.
     *
     * @param scene Die Szene, in der das Tetromino eingefügt werden soll.
     * @param grid  Das Blockgitter, in das das Tetromino eingefügt werden soll.
     * @param name  Der Name des Tetrominos, zum Beispiel J, L, etc. Die
     *              Tetrominos sind nach Großbuchstaben benannt.
     * @param x     Die x-Koordinate (entspricht der Koordinate des 0-ten
     *              Blocks), an der das Tetromino eingefügt werden soll.
     * @param y     Die y-Koordinate (entspricht der Koordinate des 0-ten
     *              Blocks), an der das Tetromino eingefügt werden soll.
     */
    public Tetromino(Scene scene, Grid grid, String name, int x, int y)
    {
        this.scene = scene;
        this.name = name;
        this.grid = grid;
        this.x = x;
        this.y = y;
        blocks = new Block[4];
    }

    /**
     * Gibt die x-Koordinate des Tetrominos zurück.
     *
     * @return Die x-Koordinate des Tetrominos entspricht der Lage des ersten
     *         Blocks, also dem Block mit der Indexnummer 0 im Blockfeld.
     */
    public int getX()
    {
        return x;
    }

    /**
     * Gibt die y-Koordinate des Tetrominos zurück.
     *
     * @return Die y-Koordinate des Tetrominos entspricht der Lage des ersten
     *         Blocks, also dem Block mit der Indexnummer 0 im Blockfeld.
     */
    public int getY()
    {
        return y;
    }

    /**
     * Fügt einen Block ein.
     *
     * @param index Die Indexnummer im Blockfeld.
     * @param x     Die x-Koordinate, an der der Block eingefügt werden soll.
     * @param y     Die y-Koordinate, an der der Block eingefügt werden soll.
     */
    protected void addBlock(int index, int x, int y)
    {
        Block block;
        if (Game.isDebug())
        {
            block = new Block(this.scene, "Debug-" + index, x, y);
        }
        else
        {
            block = new Block(this.scene, name, x, y);
        }
        blocks[index] = block;
        if (grid != null)
        {
            grid.addBlock(block);
        }
    }

    /**
     * Bewegt einen Block an eine neue Position durch Angabe eines relativen
     * Vectors.
     *
     * @param index Die Indexnummer, die angibt, welcher Block verschoben werden
     *              soll.
     * @param dX    Delta-Wert der Bewegung in x-Richtung.
     * @param dY    Delta-Wert der Bewegung in y-Richtung.
     */
    protected void moveBlock(int index, int dX, int dY)
    {
        blocks[index].moveBy(dX, dY);
    }

    /**
     * Überprüft, ob die gegebene Koordinate mit einem der vier eigenen Blöcke
     * des Tetrominos übereinstimmt.
     */
    public boolean isOwnBlockPosition(int x, int y)
    {
        for (Block block : blocks)
        {
            if (block.getX() == x && block.getY() == y)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Überprüft, ob die gegebene Koordinate im Blockgitter besetzt ist. Dabei
     * wird ein eigener Block ignoriert.
     */
    protected boolean isGridTaken(int x, int y)
    {
        if (grid == null)
        {
            return false;
        }
        return !isOwnBlockPosition(x, y) && grid.isTaken(x, y);
    }

    /**
     * Diese Methode wird benötigt, um Tetrominos außerhalb des Grids zu
     * rotieren und dann in die gewünschte Endposition zu bringen. So können wir
     * ein vorbelegtes Gitter erzeugen, um den Algorithmus zu testen, wie
     * ausgefüllte Reihen aus dem Gitter gelöscht werden. Die Tetrominos werden
     * zunächst ohne Gitter erzeugt ({@code grid = null}), dann rotiert und an
     * die gewünschte Position geschoben und schließlich wird diese Methode
     * aufgerufen und die Blöcke werden dem Gitter hinzugefügt.
     *
     * @param grid Ein Blockgitter.
     */
    public void addGrid(Grid grid)
    {
        this.grid = grid;
        addBlocksToGrid();
    }

    /**
     * Fügt alle Blöcke des Tetrominos in das Blockgitter ein.
     *
     * <p>
     * Die Blöcke können nicht einzeln im Gitter verschoben werden, da sie sich
     * sonst gegenseitig überschreiben würden.
     * </p>
     *
     * @see #removeBlocksFromGrid()
     */
    protected void addBlocksToGrid()
    {
        if (grid == null)
        {
            return;
        }
        for (Block block : blocks)
        {
            grid.addBlock(block);
        }
    }

    /**
     * Entfernt alle Blöcke des Tetrominos aus dem Blockgitter.
     *
     * <p>
     * Die Blöcke können nicht einzeln im Gitter verschoben werden, da sie sich
     * sonst gegenseitig überschreiben würden.
     * </p>
     *
     * @see #addBlocksToGrid()
     */
    protected void removeBlocksFromGrid()
    {
        if (grid == null)
        {
            return;
        }
        for (Block block : blocks)
        {
            grid.removeBlock(block);
        }
    }

    protected boolean checkLeft()
    {
        for (Block block : blocks)
        {
            if (isGridTaken(block.getX() - 1, block.getY()))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Bewegt das Tetromino um eine Spalte nach links.
     *
     * @return Wahr, wenn sich das Tetromino bewegen konnte, sonst falsch.
     */
    public boolean moveLeft()
    {
        if (!checkLeft())
        {
            return false;
        }
        removeBlocksFromGrid();
        for (Block block : blocks)
        {
            block.moveLeft();
        }
        addBlocksToGrid();
        x--;
        assert x == blocks[0].getX();
        return true;
    }

    protected boolean checkRight()
    {
        for (Block block : blocks)
        {
            if (isGridTaken(block.getX() + 1, block.getY()))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Bewegt das Tetromino um eine Spalte nach rechts.
     *
     * @return Wahr, wenn sich das Tetromino bewegen konnte, sonst falsch.
     */
    public boolean moveRight()
    {
        if (!checkRight())
        {
            return false;
        }
        removeBlocksFromGrid();
        for (Block block : blocks)
        {
            block.moveRight();
        }
        addBlocksToGrid();
        x++;
        assert x == blocks[0].getX();
        return true;
    }

    protected boolean checkDown()
    {
        for (Block block : blocks)
        {
            if (isGridTaken(block.getX(), block.getY() - 1))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Bewegt das Tetromino um eine Reihe nach unten.
     *
     * @return Wahr, wenn sich das Tetromino bewegen konnte, sonst falsch.
     */
    public boolean moveDown()
    {
        if (!checkDown())
        {
            return false;
        }
        removeBlocksFromGrid();
        for (Block block : blocks)
        {
            block.moveDown();
        }
        addBlocksToGrid();
        y--;
        assert y == blocks[0].getY();
        return true;
    }

    /**
     * Überprüft, ob sich das Tetromino drehen kann.
     *
     * <p>
     * Achtung das ist eine naive Implementation! Wir überprüfen einen 3x3-Block
     * um den Mittelpunkt des Tetromino.
     *
     * <p>
     * Probleme dieser Implementation:
     *
     * <ul>
     * <li>Das I-Tetromino schaut einen Block über den 3x3-Block hinaus.</li>
     *
     * <li>In den 3x3-Feld dürfen sind an gewissen Position Blöcke befinden und
     * das Tetromino kann sich trotzdem bewegen.</li>
     * </ul>
     *
     * @return Wahr, falls sich das Tetromino drehen kann.
     */
    private boolean checkRotation()
    {
        for (int x = getX() - 1; x <= getX() + 1; x++)
        {
            for (int y = getY() - 1; y <= getY() + 1; y++)
            {
                if (isGridTaken(x, y))
                {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Führt die eigentliche Rotation des Tetrominos durch, d. h. sie bewegt
     * einzelnen Blöcke an neue Positionen.
     *
     * <p>
     * Es handelt sich um eine abstrakte Einschubmethode, die nach dem
     * Schablonen-Methode-Entwurfsmuster (englisch template method) von den
     * Unterklassen - den einzelnen Tetrominos - implementiert werden müssen.
     * </p>
     *
     * @see #rotate
     */
    protected abstract void doRotation();

    /**
     * Führt eine Rechtsdrehung durch.
     *
     * <p>
     * Es handelt sich um eine Schablonen-Methode (englisch template method)
     * nach dem gleichnamigen Entwurfsmuster. Diese Methode ruft die abstrakte
     * Methoden {@link #doRotation()} auf, die erst in den Unterklassen der
     * einzelnen Tetrominos definiert werden.
     * </p>
     *
     * @see #doRotation
     *
     * @return Gibt wahr zurück, wenn sind das Tetromino drehen konnte, sonst
     *         falsch.
     */
    public boolean rotate()
    {
        if (!checkRotation())
        {
            return false;
        }
        if (rotation > 2)
        {
            rotation = 0;
        }
        else
        {
            rotation++;
        }
        removeBlocksFromGrid();
        doRotation();
        addBlocksToGrid();
        return true;
    }

    /**
     * Entfernt das Tetromino aus der Szene, d. h. alle Block werden sowohl aus
     * der Szene als auch aus dem Blockgitter entfernt.
     */
    public void remove()
    {
        for (Block block : blocks)
        {
            block.remove();
        }
        removeBlocksFromGrid();
    }

    /**
     * Erzeugt ein Tetromino durch Angabe des Names.
     *
     * @param scene Die Szene, in der das Tetromino eingefügt werden soll.
     * @param grid  Das Blockgitter, in das das Tetromino eingefügt werden soll.
     * @param name  Der Name des Tetrominos, zum Beispiel J, L, etc. Die
     *              Tetrominos sind nach Großbuchstaben benannt.
     * @param x     Die x-Koordinate (entspricht der Koordinate des 0-ten
     *              Blocks), an der das Tetromino eingefügt werden soll.
     * @param y     Die y-Koordinate (entspricht der Koordinate des 0-ten
     *              Blocks), an der das Tetromino eingefügt werden soll.
     *
     * @return Das erzeugte Tetromino.
     */
    public static Tetromino create(Scene scene, Grid grid, String name, int x,
            int y)
    {
        return switch (name)
        {
        case "J" -> new J(scene, grid, x, y);
        case "I" -> new I(scene, grid, x, y);
        case "O" -> new O(scene, grid, x, y);
        case "Z" -> new Z(scene, grid, x, y);
        case "S" -> new S(scene, grid, x, y);
        case "T" -> new T(scene, grid, x, y);
        default -> new L(scene, grid, x, y);
        };
    }

    /**
     * Erzeugt ein Tetromino durch Angabe einer Nummer.
     *
     * @param scene  Die Szene, in der das Tetromino eingefügt werden soll.
     * @param grid   Das Blockgitter, in das das Tetromino eingefügt werden
     *               soll.
     * @param number Die Nummer des Tetrominos, 0 ist zum Beispiel das
     *               L-Tetromino, 6 das T-Tetromino.
     * @param x      Die x-Koordinate (entspricht der Koordinate des 0-ten
     *               Blocks), an der das Tetromino eingefügt werden soll.
     * @param y      Die y-Koordinate (entspricht der Koordinate des 0-ten
     *               Blocks), an der das Tetromino eingefügt werden soll.
     *
     * @return Das erzeugte Tetromino.
     */
    public static Tetromino create(Scene scene, Grid grid, int number, int x,
            int y)
    {
        return create(scene, grid, names[number], x, y);
    }
}
