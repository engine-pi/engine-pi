package demos.classes.graphics;

import pi.Game;
import pi.Scene;

public class SceneInfoOverlayDemo extends Scene
{

    public SceneInfoOverlayDemo()
    {
        info().title("Der Titel der Szene").subtitle("Der Untertitel der Szene")
                .description("Ein längerer Beschreibungstext")
                .help("Ein Hilfetext");
    }

    public static void main(String[] args)
    {
        Game.start(new SceneInfoOverlayDemo());
    }
}
