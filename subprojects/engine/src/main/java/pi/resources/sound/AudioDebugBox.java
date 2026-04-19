package pi.resources.sound;

import java.awt.Graphics2D;
import java.util.Collection;

import pi.graphics.boxes.Box;
import pi.graphics.boxes.TextBlockBox;
import pi.graphics.boxes.TextLineBox;
import pi.graphics.boxes.VerticalBox;

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/resources/sound/AudioDebugScene.java

/**
 * @author Josef Friedrich
 *
 * @since 0.47.0
 */
public class AudioDebugBox extends VerticalBox<Box>
{
    TextBlockBox soundsBox;

    TextBlockBox musicBox;

    SoundEngine engine;

    Collection<SoundPlayback> allSounds;

    Collection<MusicPlayback> allMusic;

    public AudioDebugBox()
    {

        addChild(new TextLineBox("Sounds:"));

        soundsBox = new TextBlockBox("no sounds are playing");
        soundsBox.width(700);

        addChild(soundsBox);

        addChild(new TextLineBox("Audio:"));

        musicBox = new TextBlockBox("no music is playing");
        musicBox.width(700);
        addChild(musicBox);

        engine = SoundEngine.getInstance();
        allMusic = engine.allMusic();
        allSounds = engine.allSounds();
    }

    private String formatPlaybacks(Collection<? extends Playback> playbacks)
    {
        if (playbacks.isEmpty())
        {
            return "[]";
        }

        var string = new StringBuilder();

        var list = new java.util.ArrayList<>(playbacks);

        // for (int i = list.size() - 1; i > list.size() - 5 && i >= 0; i--)
        // {
        // string.append(i + ". " + list.get(i) + "\n");
        // }

        for (int i = 0; i < list.size(); i++)
        {
            string.append(i + ". " + list.get(i) + "\n");
        }

        return string.toString();

    }

    @Override
    public Box render(Graphics2D g)
    {
        soundsBox.content(formatPlaybacks(allSounds));
        musicBox.content(formatPlaybacks(allMusic));
        return super.render(g);
    }
}
