/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
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
package demos.classes.graphics;

import java.awt.event.KeyEvent;

import pi.Game;
import pi.Scene;
import pi.event.KeyStrokeListener;
import pi.graphics.boxes.HAlign;
import pi.graphics.boxes.VAlign;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/engine/src/main/java/pi/graphics/SceneInfoOverlay.java

public class SceneInfoOverlayDemo extends Scene implements KeyStrokeListener
{
    public SceneInfoOverlayDemo()
    {
        info().title("Der Titel der Szene")
                //
                .subtitle("Der Untertitel der Szene")
                //
                .description("Ein längerer Beschreibungstext")
                //
                .help("Ein Hilfetext").permanent();
    }

    @Override
    public void onKeyDown(KeyEvent event)
    {
        switch (event.getKeyCode())
        {
        case KeyEvent.VK_1:
            info().vAlign(VAlign.TOP);
            break;

        case KeyEvent.VK_2:
            info().vAlign(VAlign.MIDDLE);
            break;

        case KeyEvent.VK_3:
            info().vAlign(VAlign.BOTTOM);
            break;

        case KeyEvent.VK_Q:
            info().hAlign(HAlign.LEFT);
            break;

        case KeyEvent.VK_W:
            info().hAlign(HAlign.CENTER);
            break;

        case KeyEvent.VK_E:
            info().hAlign(HAlign.RIGHT);
            break;

        case KeyEvent.VK_T:
            info().toggle();
            break;
        }
    }

    public static void main(String[] args)
    {
        Game.start(new SceneInfoOverlayDemo());
    }

}
