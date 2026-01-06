package demos.small_games.pong;

import pi.Controller;

public class Pong
{

    public static void main(String[] args)
    {
        Controller.start(new PongTable());
    }
}
