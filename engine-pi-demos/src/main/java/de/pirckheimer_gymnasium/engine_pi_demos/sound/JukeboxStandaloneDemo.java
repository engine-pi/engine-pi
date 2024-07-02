package de.pirckheimer_gymnasium.engine_pi_demos.sound;

import de.pirckheimer_gymnasium.engine_pi.Jukebox;

public class JukeboxStandaloneDemo
{
    public JukeboxStandaloneDemo()
    {
        Jukebox.playMusic("tetris/sounds/A-Type-Music_Korobeiniki.ogg");
    }

    public static void main(String[] args)
    {
        new JukeboxStandaloneDemo();
    }
}
