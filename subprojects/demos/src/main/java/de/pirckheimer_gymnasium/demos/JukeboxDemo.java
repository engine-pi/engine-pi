/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2024 Josef Friedrich and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package de.pirckheimer_gymnasium.demos;

import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import pi.Game;
import pi.Jukebox;
import pi.Scene;
import pi.event.KeyStrokeListener;
import pi.resources.ResourceLoader;
import pi.sound.LoopedTrack;
import pi.sound.MusicPlayback;
import pi.sound.Playback;
import pi.sound.SinglePlayTrack;
import pi.sound.Sound;
import pi.sound.Track;

/**
 * Demonstriert die Klasse <b>Jukebox</b>.
 */
public class JukeboxDemo extends Scene implements KeyStrokeListener
{
    Playback casinoBling;

    Track gameReached;

    Track gameBonus;

    Track levelMusic;

    public JukeboxDemo() throws IOException, UnsupportedAudioFileException
    {
        Game.start(this, 200, 300);
        casinoBling = Jukebox.createSoundPlayback(
                "sounds/casino-bling-achievement.mp3", true);
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
            playback.setVolume(Jukebox.getMusic().getVolume() - diff);
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
