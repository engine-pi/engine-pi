package demos.small_games.pong;

import pi.Game;
import pi.actor.Counter;

/**
 * Eine Hälfte des Spieltischs.
 */
public class TableSide
{
    static
    {
        Game.instantMode(false);
    }

    /**
     * Die Punktestandsanzeige.
     */
    private Counter score;

    /**
     * Ein Schläger des Ping-Pong-Spiels.
     */
    private Paddle paddle;

    /**
     * @param sideSign Gibt an, um welche Seite des Spieltisches es sich
     *     handelt.
     *
     *     <p>
     *     {@code +1} steht für rechts und {@code -1} für links. Da das Zentrum
     *     des Koordinatensystems in der Mitte des Spielfensters liegt, kann
     *     einfache Multiplikation mit diesem Attribut dazu verwendet werden, um
     *     das Objekt richtig zu platzieren.
     *     </p>
     * @param table Die sichtbare Fläche der des Ping-Pong-Tisches in Meter.
     */
    public TableSide(int sideSign, PongTable table)
    {
        score = new Counter();
        score.center(sideSign * (table.bounds.xRight() - 5),
                table.bounds.yTop() - 2);
        paddle = new Paddle(sideSign, table);
        table.add(score, paddle);
    }

    void increaseScore()
    {
        score.increase();
    }

    void movePaddleUp()
    {
        paddle.moveUp();
    }

    void movePaddleDown()
    {
        paddle.moveDown();
    }

    public static void main(String[] args)
    {
        Game.start(new PongTable());
    }
}
