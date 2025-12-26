package pacman.debug;

import pacman.Main;
import pacman.actors.Text;
import pacman.scenes.BaseScene;

public class TextDebugScene extends BaseScene
{
    public TextDebugScene()
    {
        Text text = new Text("Blinky");
        add(text);
    }

    public static void main(String[] args)
    {
        Main.start(new TextDebugScene(), 1);
    }
}
