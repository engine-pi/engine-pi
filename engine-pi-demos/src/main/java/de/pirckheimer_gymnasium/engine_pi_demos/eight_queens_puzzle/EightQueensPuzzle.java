package de.pirckheimer_gymnasium.engine_pi_demos.eight_queens_puzzle;

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
class EightQueensPuzzle
{
    /**
     * <p>
     * Der ursprünglich deutsche Name dieses Attributes war {@code schachbrett}.
     * </p>
     */
    private boolean[][] chessboard;

    /**
     * Legt das Schachbrett an.
     *
     * @param numberOfQueens Die Anzahl der Damen (und damit auch die Größe des
     *     Schachbretts.
     */
    EightQueensPuzzle(int numberOfQueens)
    {
        chessboard = new boolean[numberOfQueens][numberOfQueens];
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
        int currentColumn = column - 1;
        while (currentColumn >= 0)
        {
            if (chessboard[row][currentColumn])
            {
                return true;
            }
            currentColumn -= 1;
        }
        currentColumn = column - 1;
        int currentRow = row - 1;
        while (currentColumn >= 0 && currentRow >= 0)
        {
            if (chessboard[currentRow][currentColumn])
            {
                return true;
            }
            currentColumn -= 1;
            currentRow -= 1;
        }
        currentColumn = column - 1;
        currentRow = row + 1;
        while (currentColumn >= 0 && currentRow < chessboard.length)
        {
            if (chessboard[currentRow][currentColumn])
            {
                return true;
            }
            currentColumn -= 1;
            currentRow += 1;
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
        for (int column = 0; column < chessboard.length; column += 1)
        {
            for (int row = 0; row < chessboard.length; row += 1)
            {
                if (chessboard[row][column])
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
        for (int row = 0; row < chessboard.length; row += 1)
        {
            if (!isSquareThreatened(row, column))
            {
                chessboard[row][column] = true;
                if (column == chessboard.length - 1)
                {
                    printSolution();
                }
                else
                {
                    makeStep(column + 1);
                }
                chessboard[row][column] = false;
            }
        }
    }

    public static void main(String[] args)
    {
        new EightQueensPuzzle(8);
    }
}
