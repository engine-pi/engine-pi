package de.pirckheimer_gymnasium.engine_pi_demos.eight_queens_puzzle;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colorScheme;
import static de.pirckheimer_gymnasium.engine_pi.util.ColorUtil.changeSaturation;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Image;
import de.pirckheimer_gymnasium.engine_pi.actor.Square;

/**
 * @since 0.33.0
 */
public class Chessboard
{

    private Square[][] squares;

    private Image[][] queens;

    public Chessboard(Scene scene, int numberOfSquares)
    {
        Color brown = colorScheme.getBrown();
        Color brown2 = changeSaturation(brown, -0.2);
        squares = new Square[numberOfSquares][numberOfSquares];
        queens = new Image[numberOfSquares][numberOfSquares];

        for (int row = 0; row < 8; row += 1)
        {
            for (int column = 0; column < 8; column += 1)
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
                queen.setVisible(false);
                queens[row][column] = queen;
                scene.add(queen);
            }
        }
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

        try
        {
            TimeUnit.MILLISECONDS.sleep(sleepMilliSeconds);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public Chessboard(Scene scene)
    {
        this(scene, 8);
    }

    public static void main(String[] args)
    {
        Scene scene = new Scene();
        scene.setMeter(50);
        scene.getCamera().setCenter(4, 4);
        new Chessboard(scene);
        Game.start(scene);
    }

}
