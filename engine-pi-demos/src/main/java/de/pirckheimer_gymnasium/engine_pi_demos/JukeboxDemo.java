package de.pirckheimer_gymnasium.engine_pi_demos;

import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Jukebox;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;
import de.pirckheimer_gymnasium.engine_pi.resources.ResourceLoader;
import de.pirckheimer_gymnasium.engine_pi.sound.LoopedTrack;
import de.pirckheimer_gymnasium.engine_pi.sound.MusicPlayback;
import de.pirckheimer_gymnasium.engine_pi.sound.Playback;
import de.pirckheimer_gymnasium.engine_pi.sound.SinglePlayTrack;
import de.pirckheimer_gymnasium.engine_pi.sound.Sound;
import de.pirckheimer_gymnasium.engine_pi.sound.Track;

public class JukeboxDemo extends Scene implements KeyStrokeListener
{
    Jukebox jukebox;

    Playback casinoBling;

    Track gameReached;

    Track gameBonus;

    Track levelMusic;

    public JukeboxDemo() throws IOException, UnsupportedAudioFileException
    {
        Game.start(200, 300, this);
        casinoBling = Jukebox
                .createPlayback("sounds/casino-bling-achievement.mp3", true);
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
            Jukebox.playSound("sounds/casino-bling-achievement.mp3");
            break;

        case KeyEvent.VK_2:
            Jukebox.playMusic(gameReached, false, false);
            break;

        case KeyEvent.VK_3:
            Jukebox.playMusic(gameBonus, false, false);
            break;

        case KeyEvent.VK_4:
            Jukebox.playMusic(levelMusic, false, false);
            break;

        case KeyEvent.VK_5:
            casinoBling.start();
            break;

        case KeyEvent.VK_6:
            casinoBling.cancel();
            break;

        case KeyEvent.VK_PLUS:
            increaseVolume();
            break;

        case KeyEvent.VK_MINUS:
            decreaseVolume();
            break;

        case KeyEvent.VK_S:
            Jukebox.stopMusic();
            break;

        case KeyEvent.VK_L:
            for (MusicPlayback playback : Jukebox.getAllMusic())
            {
                System.out.println(playback);
            }
            break;
        }
    }

    private void changeVolume(float diff)
    {
        MusicPlayback playback = Jukebox.getMusic();
        if (playback != null)
        {
            playback.setVolume(Jukebox.getMusic().getVolume() - 0.1f);
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
        new JukeboxDemo();
    }
}
