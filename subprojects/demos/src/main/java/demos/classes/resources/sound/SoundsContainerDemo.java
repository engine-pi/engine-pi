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

import java.net.MalformedURLException;
import java.net.URL;

import pi.Jukebox;
import pi.Resources;
import pi.resources.sound.MusicPlayback;
import pi.resources.sound.Sound;
import pi.resources.sound.SoundEvent;
import pi.resources.sound.SoundPlaybackListener;

public class SoundsContainerDemo
{
    public SoundsContainerDemo() throws MalformedURLException
    {
        String soundName = "sounds/game-level-music.mp3";
        Sound mySound = Resources.sounds.get("sounds/game-level-music.mp3");
        if (Resources.sounds.contains(mySound))
        {
            System.out.println("Contains mySound");
        }
        if (Resources.sounds.contains(soundName))
        {
            System.out.println("Contains soundName");
        }
        if (Resources.sounds.contains(new URL("file://" + soundName)))
        {
            System.out.println("Contains url");
        }
        Resources.sounds.contains(soundName);
        MusicPlayback playback = Jukebox.playMusic(mySound);
        playback.addSoundPlaybackListener(new SoundPlaybackListener()
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
        new SoundsContainerDemo();
    }
}
