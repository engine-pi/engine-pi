package pacman.scenes;

import pacman.ColorManagement;
import pi.Scene;

public class BaseScene extends Scene
{
    public BaseScene()
    {
        ColorManagement.setColors();
        getCamera().position(14, 18);
    }
}
