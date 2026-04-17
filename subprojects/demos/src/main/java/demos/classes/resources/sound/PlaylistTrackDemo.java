package demos.classes.resources.sound;

import pi.Controller;
import pi.resources.sound.PlaylistTrack;

public class PlaylistTrackDemo
{
    String a = "tetris/sounds/Korobeiniki_A-Teil.mp3";

    String b = "tetris/sounds/Korobeiniki_B-Teil.mp3";

    public PlaylistTrackDemo()
    {

        Controller.jukebox.playMusic(
            new PlaylistTrack(Controller.sounds.getMultipleAsList(a, a, b)));
    }

    public static void main(String[] args)
    {
        new PlaylistTrackDemo();
    }
}
