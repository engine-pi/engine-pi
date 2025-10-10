package de.pirckheimer_gymnasium.engine_pi_demos;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Rectangle;

public class Chessboard
{

    private Rectangle[][] squares;

    public Chessboard(Scene scene, int numberOfSquares)
    {
        squares = new Rectangle[numberOfSquares][numberOfSquares];
        for (int row = 0; row < 8; row += 1)
        {
            for (int column = 0; column < 8; column += 1)
            {
                Rectangle rectangle = new Rectangle();
                rectangle.setPosition(column, row);
                rectangle.setColor(
                        ((column + row) % 2 == 0 ? "schwarz" : "weiß"));
                squares[row][column] = rectangle;
                scene.add(rectangle);
            }
        }
    }

    public Chessboard(Scene scene)
    {
        this(scene, 8);
    }

    public static void main(String[] args)
    {
        Scene scene = new Scene();
        scene.setMeter(100);
        new Chessboard(scene);
        Game.start(scene);
    }

}
