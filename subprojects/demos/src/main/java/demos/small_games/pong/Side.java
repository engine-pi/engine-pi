package demos.small_games.pong;

import pi.Game;

/**
 * Die Seiten des Spielfelds
 */
public enum Side
{
    /**
     * Die <b>linke</b> Seite des Spielfelds,
     */
    LEFT,

    /**
     * Die <b>rechte</b> Seite des Spielfelds
     */
    RIGHT;

    public static void main(String[] args)
    {
        Game.start(new Pong());
    }
}
