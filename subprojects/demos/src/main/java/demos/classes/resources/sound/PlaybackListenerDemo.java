/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2026 Josef Friedrich and contributors.
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
package demos.classes.resources.sound;

import static pi.Controller.jukebox;
import static pi.Controller.sounds;

import java.awt.event.KeyEvent;

import pi.Controller;
import pi.event.KeyStrokeListener;
import pi.resources.sound.MusicPlayback;
import pi.resources.sound.PlaybackListener;
import pi.resources.sound.SinglePlayTrack;
import pi.resources.sound.Sound;
import pi.resources.sound.SoundEvent;

/**
 *
 */
public class PlaybackListenerDemo extends AudioDebugScene
        implements KeyStrokeListener
{

    MusicPlayback playback;

    public PlaybackListenerDemo()
    {
        Sound mySound = sounds.get("sounds/game-level-music.mp3");

        playback = jukebox.playMusic(new SinglePlayTrack(mySound));
        playback.addPlaybackListener(new PlaybackListener()
        {
            @Override
            public void finished(SoundEvent event)
            {
                System.out.println("finished");
            }

            @Override
            public void cancelled(SoundEvent event)
            {
                System.out.println("cancelled");
            }
        });
    }

    @Override
    public void onKeyDown(KeyEvent event)
    {
        switch (event.getKeyCode())
        {
        case KeyEvent.VK_1 -> playback.pausePlayback();
        case KeyEvent.VK_2 -> playback.cancel();
        case KeyEvent.VK_3 -> playback.start();
        case KeyEvent.VK_4 -> playback.resumePlayback();
        }
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new PlaybackListenerDemo());
    }
}
