package demos.small_games.pong;

import pi.Game;

/**
 * Die beiden Seiten des Ping-Pong-Tisches.
 */
public enum Side
{
    /**
     * Die <b>linke</b> Seite des Ping-Pong-Tisches.
     */
    LEFT,

    /**
     * Die <b>rechte</b> Seite des Ping-Pong-Tisches.
     */
    RIGHT;

    public static void main(String[] args)
    {
        Game.start(new Pong());
    }
}
