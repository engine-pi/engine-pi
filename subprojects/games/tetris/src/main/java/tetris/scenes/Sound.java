/*
 * Copyright (c) 2024, 2026 Josef Friedrich and contributors.
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
package tetris.scenes;

import static pi.Controller.jukebox;

/**
 * @author Josef Friedrich
 */
public class Sound
{
    /**
     * Dieser private Konstruktor dient dazu, den öffentlichen Konstruktor zu
     * verbergen. Dadurch ist es nicht möglich, Instanzen dieser Klasse zu
     * erstellen.
     *
     * @throws UnsupportedOperationException Falls eine Instanz der Klasse
     *     erzeugt wird.
     */
    private Sound()
    {
        throw new UnsupportedOperationException();
    }

    private static void playMusic(String filename)
    {
        jukebox.playMusic("sounds/" + filename);
    }

    private static void playSound(String filename)
    {
        jukebox.playSound("sounds/" + filename);
    }

    public static void playTitle()
    {
        jukebox.playIntroMusic("sounds/Title_intro.mp3",
            "sounds/Title_loop.mp3");
    }

    public static void playKorobeiniki()
    {
        playMusic("A-Type-Music_korobeiniki.mp3");
    }

    public static void playBlockMove()
    {
        playSound("Block_move.wav");
    }

    public static void playBlockRotate()
    {
        playSound("Block_rotate.wav");
    }

    public static void playBlockDrop()
    {
        playSound("Block_drop.wav");
    }

    public static void playRowClear1to3()
    {
        playSound("Row_clear1-3.wav");
    }

    public static void playRowClear4()
    {
        playSound("Row_clear4.wav");
    }
}
