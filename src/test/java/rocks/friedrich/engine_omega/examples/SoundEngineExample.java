package rocks.friedrich.engine_omega.examples;

import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.event.KeyListener;
import rocks.friedrich.engine_omega.io.ResourceLoader;
import rocks.friedrich.engine_omega.sound.LoopedTrack;
import rocks.friedrich.engine_omega.sound.MusicPlayback;
import rocks.friedrich.engine_omega.sound.SinglePlayTrack;
import rocks.friedrich.engine_omega.sound.Sound;
import rocks.friedrich.engine_omega.sound.SoundEngine;
import rocks.friedrich.engine_omega.sound.Track;

public class SoundEngineExample extends Scene implements KeyListener
{
    SoundEngine soundEngine;

    Track casinoBling;

    Track gameReached;

    Track gameBonus;

    Track levelMusic;

    public SoundEngineExample()
            throws IOException, UnsupportedAudioFileException
    {
        Game.start(200, 300, this);
        soundEngine = Game.getSoundEngine();
        casinoBling = loadSinglePlayTrack("casino-bling-achievement.mp3");
        gameReached = loadSinglePlayTrack("game-bonus-reached.mp3");
        gameBonus = loadSinglePlayTrack("arcade-video-game-bonus.mp3");
        levelMusic = loadLoopedTrack("game-level-music.mp3");
    }

    public Sound loadSound(String fileName)
            throws IOException, UnsupportedAudioFileException
    {
        return new Sound(ResourceLoader.loadAsStream("sounds/" + fileName),
                fileName);
    }

    public Track loadSinglePlayTrack(String fileName)
            throws IOException, UnsupportedAudioFileException
    {
        return new SinglePlayTrack(loadSound(fileName));
    }

    public Track loadLoopedTrack(String fileName)
            throws IOException, UnsupportedAudioFileException
    {
        return new LoopedTrack(loadSound(fileName));
    }

    @Override
    public void onKeyDown(KeyEvent keyEvent)
    {
        switch (keyEvent.getKeyCode())
        {
        case KeyEvent.VK_1:
            soundEngine.playMusic(casinoBling, false, false);
            break;

        case KeyEvent.VK_2:
            soundEngine.playMusic(gameReached, false, false);
            break;

        case KeyEvent.VK_3:
            soundEngine.playMusic(gameBonus, false, false);
            break;

        case KeyEvent.VK_4:
            soundEngine.playMusic(levelMusic, false, false);
            break;

        case KeyEvent.VK_PLUS:
            increaseVolume();
            break;

        case KeyEvent.VK_MINUS:
            decreaseVolume();
            break;
        }
    }

    private void changeVolume(float diff)
    {
        MusicPlayback playback = soundEngine.getMusic();
        if (playback != null)
        {
            playback.setVolume(soundEngine.getMusic().getVolume() - 0.1f);
        }
    }

    private void increaseVolume()
    {
        changeVolume(+0.1f);
    }

    private void decreaseVolume()
    {
        changeVolume(-0.1f);
    }

    public static void main(String[] args)
            throws IOException, UnsupportedAudioFileException
    {
        new SoundEngineExample();
    }
}
