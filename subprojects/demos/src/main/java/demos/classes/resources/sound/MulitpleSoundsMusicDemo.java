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

import pi.Controller;
import pi.resources.sound.MulitpleSoundsMusic;

/**
 * Demonstriert die Klasse {@link MulitpleSoundsMusic}.
 *
 * @author Josef Friedrich
 *
 * @since 0.47.0
 */
public class MulitpleSoundsMusicDemo extends AudioDebugScene
{
    String a = "tetris/sounds/Korobeiniki_A-Teil.mp3";

    String b = "tetris/sounds/Korobeiniki_B-Teil.mp3";

    public MulitpleSoundsMusicDemo()
    {
        info("Demonstriert die Klasse MulitpleSoundsTrack")
            .description("Teil A wird zweimal abgespielt. Teil B nur einmal.");
        var track = new MulitpleSoundsMusic(a, a, b);
        jukebox.playMusic(new MulitpleSoundsMusic(a, a, b));
        System.out.println(track);
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new MulitpleSoundsMusicDemo());
    }
}
