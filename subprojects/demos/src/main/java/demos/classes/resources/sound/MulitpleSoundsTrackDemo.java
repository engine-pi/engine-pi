package demos.classes.resources.sound;

import pi.Controller;
import pi.resources.sound.MulitpleSoundsTrack;

public class MulitpleSoundsTrackDemo
{
    String a = "tetris/sounds/Korobeiniki_A-Teil.mp3";

    String b = "tetris/sounds/Korobeiniki_B-Teil.mp3";

    public MulitpleSoundsTrackDemo()
    {
        Controller.jukebox.playMusic(new MulitpleSoundsTrack(a, a, b));
    }

    public static void main(String[] args)
    {
        new MulitpleSoundsTrackDemo();
    }
}
