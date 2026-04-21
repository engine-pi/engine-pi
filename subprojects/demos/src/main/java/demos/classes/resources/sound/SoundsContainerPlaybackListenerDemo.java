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
package demos.classes.resources.sound;

import static pi.Controller.jukebox;

import java.net.MalformedURLException;

import pi.Controller;
import pi.resources.sound.MusicPlayback;
import pi.resources.sound.PlaybackListener;
import pi.resources.sound.SoundEvent;

public class SoundsContainerPlaybackListenerDemo extends AudioDebugScene
{
    public SoundsContainerPlaybackListenerDemo() throws MalformedURLException
    {
        MusicPlayback playback = jukebox
            .playMusic("sounds/game-level-music.mp3");
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

    public static void main(String[] args) throws MalformedURLException
    {
        Controller.instantMode(false);
        Controller.start(new SoundsContainerPlaybackListenerDemo());
    }
}
