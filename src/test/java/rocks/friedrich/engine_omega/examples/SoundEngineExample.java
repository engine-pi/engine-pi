package rocks.friedrich.engine_omega.examples;

import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.event.KeyListener;
import rocks.friedrich.engine_omega.io.ResourceLoader;
import rocks.friedrich.engine_omega.sound.Sound;
import rocks.friedrich.engine_omega.sound.SoundEngine;

public class SoundEngineExample extends Scene implements KeyListener
{
    SoundEngine sound;

    Sound casinoBling;

    Sound gameReached;

    Sound gameBonus;

    Sound levelMusic;

    public SoundEngineExample()
            throws IOException, UnsupportedAudioFileException
    {
        Game.start(200, 300, this);
        sound = Game.getSoundEngine();
        casinoBling = loadSound("casino-bling-achievement.mp3");
        gameReached = loadSound("game-bonus-reached.mp3");
        gameBonus = loadSound("arcade-video-game-bonus.mp3");
        levelMusic = loadSound("game-level-music.mp3");
    }

    public Sound loadSound(String fileName)
            throws IOException, UnsupportedAudioFileException
    {
        return new Sound(ResourceLoader.loadAsStream("sounds/" + fileName),
                fileName);
    }

    @Override
    public void onKeyDown(KeyEvent keyEvent)
    {
        switch (keyEvent.getKeyCode())
        {
        case KeyEvent.VK_1:
            sound.playMusic(casinoBling);
            break;

        case KeyEvent.VK_2:
            sound.playMusic(gameReached);
            break;

        case KeyEvent.VK_3:
            sound.playMusic(gameBonus);
            break;

        case KeyEvent.VK_4:
            sound.playMusic(levelMusic);
            break;
        }
    }

    public static void main(String[] args)
            throws IOException, UnsupportedAudioFileException
    {
        new SoundEngineExample();
    }
}
