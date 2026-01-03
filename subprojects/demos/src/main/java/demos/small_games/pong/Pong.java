package demos.small_games.pong;

import pi.Game;

public class Pong
{

    public static void main(String[] args)
    {
        Game.start(new PongTable());
    }
}
