package demos.small_games.pong;

import pi.Bounds;
import pi.actor.Text;

/**
 * Eine Hälfte des Spieltischs.
 */
public class TableSide
{
    /**
     * Gibt an, ob es sich um die linke oder rechte <b>Seite</b> des
     * Ping-Pong-Tisches handelt.
     */
    Side side;

    /**
     * Die Punktestand Anzeige.
     */
    Text scoreDisplay;

    Paddle paddle;

    /**
     *
     * @param side
     * @param table Die sichtbare Fläche der des Ping-Pong-Tisches in Meter.
     */
    public TableSide(Side side, Bounds table)
    {
        paddle = new Paddle(side, table);
    }
}
