package de.pirckheimer_gymnasium.engine_pi_demos.algorithms.backtracking.eight_queens_puzzle;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

/**
 * Löst das Problem der N Damen.
 *
 * <p>
 * Der ursprünglich deutsche Name dieser Klasse war {@code Damen}.
 * </p>
 *
 * @author Albert Wiedemann
 * @author Josef Friedrich (englische Übersetzung, Array statt Arraylist)
 *
 * @version 1.0
 *
 * @since 0.33.0
 */
public class EightQueensPuzzle
{
    /**
     * <p>
     * Der ursprünglich deutsche Name dieses Attributes war {@code schachbrett}.
     * </p>
     */
    private boolean[][] queenPositions;

    private Chessboard chessboard;

    /**
     * Legt das Schachbrett an.
     *
     * @param numberOfQueens Die Anzahl der Damen (und damit auch die Größe des
     *     Schachbretts.
     */
    public EightQueensPuzzle(int numberOfQueens)
    {
        queenPositions = new boolean[numberOfQueens][numberOfQueens];
        Scene scene = new Scene();
        scene.setMeter(50);
        scene.getCamera().setCenter(4, 4);
        chessboard = new Chessboard(scene, numberOfQueens);
        Game.start(scene);
        makeStep(0);
    }

    /**
     * Testet, ob das angegebene Element des Schachbretts durch eine der bisher
     * aufgestellten Damen bedroht ist.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code IstFeldBedroht}.
     * </p>
     *
     * @param row Zeile das Feldelements. Laut englischer Schachfachsprache
     *     müsste der Parameter eigentlich {@code rank} heißen. Der ursprünglich
     *     deutsche Name dieses Parameters war {@code zeile}.
     * @param column Spalte das Feldelements. Laut englischer Schachfachsprache
     *     müsste der Parameter eigentlich {@code file} heißen. Der ursprünglich
     *     deutsche Name dieses Parameters war {@code spalte}.
     */
    boolean isSquareThreatened(int row, int column)
    {
        // aktuelle Zeile
        int r = row;
        // aktuelle Spalte
        int c = column - 1;
        while (c >= 0)
        {
            chessboard.highlightSquare(r, c);
            if (queenPositions[r][c])
            {
                return true;
            }
            c -= 1;
        }
        c = column - 1;
        r = row - 1;
        while (c >= 0 && r >= 0)
        {
            chessboard.highlightSquare(r, c);
            if (queenPositions[r][c])
            {
                return true;
            }
            c -= 1;
            r -= 1;
        }
        c = column - 1;
        r = row + 1;
        while (c >= 0 && r < queenPositions.length)
        {
            chessboard.highlightSquare(r, c);
            if (queenPositions[r][c])
            {
                return true;
            }
            c -= 1;
            r += 1;
        }
        return false;
    }

    /**
     * Gibt die gefundene Lösung aus.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code LösungAusgeben}.
     * </p>
     */
    void printSolution()
    {
        System.out.println("Lösung:");
        for (int column = 0; column < queenPositions.length; column += 1)
        {
            for (int row = 0; row < queenPositions.length; row += 1)
            {
                if (queenPositions[row][column])
                {
                    System.out.print(" " + row);
                }
            }
        }
        System.out.println();
    }

    /**
     * Macht einen Rekursionsschritt auf dem Weg zur Lösung.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code SchrittAusführen}.
     * </p>
     *
     * @param column Die Spalte das Feldelements. Laut englischer
     *     Schachfachsprache müsste der Parameter eigentlich {@code file}
     *     heißen. Der ursprünglich deutsche Name dieses Parameters war
     *     {@code spalte}.
     */
    void makeStep(int column)
    {
        for (int row = 0; row < queenPositions.length; row += 1)
        {
            if (!isSquareThreatened(row, column))
            {
                queenPositions[row][column] = true;
                if (column == queenPositions.length - 1)
                {
                    printSolution();
                    chessboard.setQueens(queenPositions, 2000);
                }
                else
                {
                    makeStep(column + 1);
                    chessboard.setQueens(queenPositions, 120);
                }
                queenPositions[row][column] = false;
            }
        }
    }

    public static void main(String[] args)
    {
        new EightQueensPuzzle(9);
    }
}
