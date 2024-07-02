package de.pirckheimer_gymnasium.engine_pi_demos.sound;

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Jukebox;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;

class Player
{
    private static String getPath(String baseName)
    {
        return "tetris/sounds/" + baseName;
    }

    public static void korobeiniki()
    {
        Jukebox.playMusic(getPath("A-Type-Music_Korobeiniki.ogg"));
    }

    public static void title()
    {
        Jukebox.playIntroTrack(getPath("Title_Intro.ogg"),
                getPath("Title_Loop.ogg"));
    }

    public static void blockMove()
    {
        Jukebox.playSound(getPath("Block_move.wav"));
    }

    public static void blockRotate()
    {
        Jukebox.playSound(getPath("Block_rotate.wav"));
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
