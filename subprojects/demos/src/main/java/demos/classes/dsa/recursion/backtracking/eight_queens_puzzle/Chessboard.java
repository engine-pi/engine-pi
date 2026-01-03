package demos.classes.dsa.recursion.backtracking.eight_queens_puzzle;

import static pi.Resources.colorScheme;
import static pi.resources.color.ColorUtil.changeSaturation;
import static pi.util.TimeUtil.sleep;

import java.awt.Color;

import pi.Game;
import pi.Scene;
import pi.Circle;
import pi.actor.Image;
import pi.actor.Square;

/**
 * Ein quadratisches Schachbrett.
 *
 * @author Josef Friedrich
 *
 * @since 0.33.0
 */
public class Chessboard
{
    /**
     * Die in zwei Farben gefärbten Quadrate des Schachbrett. Der erste Index
     * des zweidimensionalen Arrays stellt die Reihen, der zweite Index die
     * Linien bzw. Spalten dar.
     */
    private Square[][] squares;

    /**
     * Auf allen Schachbrettfeldern werden Damen als Bilder platziert. Der erste
     * Index des zweidimensionalen Arrays stellt die Reihen, der zweite Index
     * die Linien bzw. Spalten dar.
     */
    private Image[][] queens;

    /**
     * Kleine blaue Kreise, die auf jedem Feld des Schachbretts platziert sind,
     * um gegebenenfalls angezeigt werden zu können. Der erste Index des
     * zweidimensionalen Arrays stellt die Reihen, der zweite Index die Linien
     * bzw. Spalten dar.
     */
    private Circle[][] highlightingPoints;

    /**
     * Wenn war, werden die einzelnen Feld durch kleinen blaue Punkte
     * hervorgehoben.
     */
    private boolean doHighlighting;

    /**
     * Zeichnet ein quadratisches Schachbrett mit einer bestimmten Anzahl an
     * Reihen und Linien.
     *
     * @param scene Die Szene, in der das Schachbrett gezeichnet werden soll.
     * @param numberOfSquares Die Anzahl der Felder einer Reihe bzw. Linie.
     *     Beispielsweise {@code 8} erzeugt ein Schachbrett mit {@code 8x8},
     *     also {@code 64} Feldern.
     */
    public Chessboard(Scene scene, int numberOfSquares)
    {
        doHighlighting = true;
        Color brown = colorScheme.getBrown();
        Color brown2 = changeSaturation(brown, -0.2);
        squares = new Square[numberOfSquares][numberOfSquares];
        queens = new Image[numberOfSquares][numberOfSquares];
        highlightingPoints = new Circle[numberOfSquares][numberOfSquares];

        for (int row = 0; row < numberOfSquares; row++)
        {
            for (int column = 0; column < numberOfSquares; column++)
            {
                Square square = new Square();
                square.setPosition(column, row);
                square.setColor(((column + row) % 2 == 0 ? brown : brown2));
                squares[row][column] = square;
                scene.add(square);

                Image queen = new Image("eight-queens-puzzle/queen-white.png",
                        120);
                double shift = 0.2;
                queen.setPosition(column + shift, row + shift);
                queen.hide();
                queens[row][column] = queen;
                scene.add(queen);

                Circle circle = new Circle(0.2);
                shift = 0.4;
                circle.setPosition(column + shift, row + shift);
                circle.hide();
                highlightingPoints[row][column] = circle;
                scene.add(circle);
            }
        }
    }

    /**
     * Zeichnet ein quadratisches Schachbrett mit 8 Reihen und 8 Linien.
     *
     * @param scene Die Szene, in der das Schachbrett gezeichnet werden soll.
     */
    public Chessboard(Scene scene)
    {
        this(scene, 8);
    }

    public void setQueens(boolean[][] hasQueens, int sleepMilliSeconds)
    {
        for (int row = 0; row < hasQueens.length; row++)
        {
            for (int column = 0; column < hasQueens[row].length; column++)
            {
                queens[row][column].setVisible(hasQueens[row][column]);
            }
        }
        sleep(sleepMilliSeconds);
    }

    /**
     * Deaktiviert die Methode {@link #highlightSquare(int, int)}
     */
    public void disableHighlighting()
    {
        doHighlighting = false;
    }

    /**
     * @param row Die Reihen- bzw. Zeilennummer eines Felds. {@code 0} ist die
     *     untereste Reihe. Laut englischer Schachfachsprache müsste der
     *     Parameter eigentlich {@code rank} heißen. Der ursprünglich deutsche
     *     Name dieses Parameters war {@code zeile}.
     * @param column Die Linien- bzw. Spaltennummer eines Felds. {@code 0} ist
     *     die Linie ganz links. Laut englischer Schachfachsprache müsste der
     *     Parameter eigentlich {@code file} heißen. Der ursprünglich deutsche
     *     Name dieses Parameters war {@code spalte}.
     */
    public void highlightSquare(int row, int column)
    {
        if (!doHighlighting)
        {
            return;
        }
        highlightingPoints[row][column].show();
        sleep(200);
        highlightingPoints[row][column].hide();
    }

    public static void main(String[] args)
    {
        Scene scene = new Scene();
        scene.meter(50);
        scene.camera().focus(4, 4);
        new Chessboard(scene);
        Game.start(scene);
    }
}
