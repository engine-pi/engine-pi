package rocks.friedrich.engine_omega.examples.resources;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.sound.MusicPlayback;
import rocks.friedrich.engine_omega.sound.Sound;
import rocks.friedrich.engine_omega.sound.SoundEvent;
import rocks.friedrich.engine_omega.sound.SoundPlaybackListener;

import java.net.MalformedURLException;
import java.net.URL;

public class SoundResourcesExample
{
    public SoundResourcesExample() throws MalformedURLException
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
        new SoundResourcesExample();
    }
}
