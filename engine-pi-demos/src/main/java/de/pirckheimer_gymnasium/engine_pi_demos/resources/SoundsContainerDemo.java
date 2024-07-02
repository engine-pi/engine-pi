package de.pirckheimer_gymnasium.engine_pi_demos.resources;

import java.net.MalformedURLException;
import java.net.URL;

import de.pirckheimer_gymnasium.engine_pi.Jukebox;
import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.sound.MusicPlayback;
import de.pirckheimer_gymnasium.engine_pi.sound.Sound;
import de.pirckheimer_gymnasium.engine_pi.sound.SoundEvent;
import de.pirckheimer_gymnasium.engine_pi.sound.SoundPlaybackListener;

public class SoundsContainerDemo
{
    public SoundsContainerDemo() throws MalformedURLException
    {
        String soundName = "sounds/game-level-music.mp3";
        Sound mySound = Resources.SOUNDS.get("sounds/game-level-music.mp3");
        if (Resources.SOUNDS.contains(mySound))
        {
            System.out.println("Contains mySound");
        }
        if (Resources.SOUNDS.contains(soundName))
        {
            System.out.println("Contains soundName");
        }
        if (Resources.SOUNDS.contains(new URL("file://" + soundName)))
        {
            System.out.println("Contains url");
        }
        Resources.SOUNDS.contains(soundName);
        MusicPlayback playback = Jukebox.playMusic(mySound);
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
