package de.pirckheimer_gymnasium.pacman.debug;

import de.pirckheimer_gymnasium.pacman.Main;
import de.pirckheimer_gymnasium.pacman.actors.Text;
import de.pirckheimer_gymnasium.pacman.scenes.BaseScene;

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
