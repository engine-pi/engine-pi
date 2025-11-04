package de.pirckheimer_gymnasium.pacman.scenes;

import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.pacman.ColorManagement;

public class BaseScene extends Scene
{
    public BaseScene()
    {
        ColorManagement.setColors();
        getCamera().setPostion(14, 18);
    }
}
