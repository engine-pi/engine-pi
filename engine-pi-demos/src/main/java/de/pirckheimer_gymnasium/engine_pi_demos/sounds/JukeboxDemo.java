package de.pirckheimer_gymnasium.engine_pi_demos.sounds;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.sound.Jukebox;

public class JukeboxDemo
{
    public JukeboxDemo()
    {
        Jukebox jukebox = Game.getJukebox();
        jukebox.playMusic("tetris/sounds/A-Type-Music_Korobeiniki.ogg");
    }

    public static void main(String[] args)
    {
        new JukeboxDemo();
        Game.start();
    }
}
