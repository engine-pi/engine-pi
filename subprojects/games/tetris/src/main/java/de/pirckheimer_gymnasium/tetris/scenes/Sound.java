package de.pirckheimer_gymnasium.tetris.scenes;

import pi.Jukebox;

/**
 * @author Josef Friedrich
 */
public class Sound
{
    private static void playMusic(String filename)
    {
        Jukebox.playMusic("sounds/" + filename);
    }

    private static void playSound(String filename)
    {
        Jukebox.playSound("sounds/" + filename);
    }

    public static void playTitle()
    {
        Jukebox.playIntroTrack("sounds/Title_intro.mp3",
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
