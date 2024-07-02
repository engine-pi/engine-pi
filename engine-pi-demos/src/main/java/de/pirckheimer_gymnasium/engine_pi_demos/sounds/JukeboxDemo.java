package de.pirckheimer_gymnasium.engine_pi_demos.sounds;

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;
import de.pirckheimer_gymnasium.engine_pi.sound.Jukebox;

class Player
{
    static Jukebox jukebox = Game.getJukebox();

    private static String getPath(String baseName)
    {
        return "tetris/sounds/" + baseName + ".ogg";
    }

    public static void korobeiniki()
    {
        jukebox.playMusic(getPath("A-Type-Music_Korobeiniki"));
    }

    public static void title()
    {
        jukebox.playIntroTrack(getPath("Title_Intro"), getPath("Title_Loop"));
    }

    public static void blockMove()
    {
        jukebox.playSound(getPath("Block_move"));
    }

    public static void blockRotate()
    {
        jukebox.playSound(getPath("Block_rotate"));
    }
}

public class JukeboxDemo extends Scene implements KeyStrokeListener
{
    @Override
    public void onKeyDown(KeyEvent event)
    {
        switch (event.getKeyCode())
        {
        case KeyEvent.VK_1 -> Player.title();
        case KeyEvent.VK_2 -> Player.korobeiniki();
        case KeyEvent.VK_3 -> Player.blockMove();
        case KeyEvent.VK_4 -> Player.blockRotate();
        }
    }

    public static void main(String[] args)
    {
        Game.start(new JukeboxDemo());
    }
}
