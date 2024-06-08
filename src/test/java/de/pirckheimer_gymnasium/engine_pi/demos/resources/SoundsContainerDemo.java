package de.pirckheimer_gymnasium.engine_pi.demos.resources;

import java.net.MalformedURLException;
import java.net.URL;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.sound.MusicPlayback;
import de.pirckheimer_gymnasium.engine_pi.sound.Sound;
import de.pirckheimer_gymnasium.engine_pi.sound.SoundEvent;
import de.pirckheimer_gymnasium.engine_pi.sound.SoundPlaybackListener;

public class SoundsContainerDemo
{
    public SoundsContainerDemo() throws MalformedURLException
    {
        String soundName = "sounds/game-level-music.mp3";
        Sound mySound = Game.getSounds().get("sounds/game-level-music.mp3");
        if (Game.getSounds().contains(mySound))
        {
            System.out.println("Contains mySound");
        }
        if (Game.getSounds().contains(soundName))
        {
            System.out.println("Contains soundName");
        }
        if (Game.getSounds().contains(new URL("file://" + soundName)))
        {
            System.out.println("Contains url");
        }
        Game.getSounds().contains(soundName);
        MusicPlayback playback = Game.getJukebox().playMusic(mySound);
        playback.addSoundPlaybackListener(new SoundPlaybackListener()
        {
            @Override
            public void finished(SoundEvent event)
            {
            }

            @Override
            public void cancelled(SoundEvent event)
            {
            }
        });
    }

    public static void main(String[] args) throws MalformedURLException
    {
        new SoundsContainerDemo();
    }
}
