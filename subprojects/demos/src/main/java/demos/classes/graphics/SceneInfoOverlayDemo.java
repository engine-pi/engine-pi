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
import pi.debug.MainAnimation;
import pi.event.KeyStrokeListener;
import pi.graphics.boxes.HAlign;
import pi.graphics.boxes.VAlign;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/engine/src/main/java/pi/graphics/SceneInfoOverlay.java

public class SceneInfoOverlayDemo extends MainAnimation
        implements KeyStrokeListener
{
    String lorem = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";

    String descriptionText = "Ein längerer Beschreibungstext";

    boolean showLongDescription = false;

    String helpText = "Ein Hilfetext";

    boolean showLongHelp = false;

    public SceneInfoOverlayDemo()
    {
        info().title("Der Titel der Szene")
                //
                .subtitle("Der Untertitel der Szene")
                //
                .description(descriptionText)
                //
                .help(helpText).permanent();
    }

    public void toggleDescriptionText()
    {
        showLongDescription = !showLongDescription;
        String description;
        if (showLongDescription)
        {
            description = lorem;
        }
        else
        {
            description = descriptionText;
        }
        info().description(description);
    }

    public void toggleHelpText()
    {
        showLongHelp = !showLongHelp;
        String help;
        if (showLongHelp)
        {
            help = lorem;
        }
        else
        {
            help = helpText;
        }
        info().help(help);
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

        case KeyEvent.VK_NUMPAD1:
            info().hAlign(HAlign.LEFT).vAlign(VAlign.BOTTOM);
            break;

        case KeyEvent.VK_NUMPAD2:
            info().hAlign(HAlign.CENTER).vAlign(VAlign.BOTTOM);
            break;

        case KeyEvent.VK_NUMPAD3:
            info().hAlign(HAlign.RIGHT).vAlign(VAlign.BOTTOM);
            break;

        case KeyEvent.VK_NUMPAD4:
            info().hAlign(HAlign.LEFT).vAlign(VAlign.MIDDLE);
            break;

        case KeyEvent.VK_NUMPAD5:
            info().hAlign(HAlign.CENTER).vAlign(VAlign.MIDDLE);
            break;

        case KeyEvent.VK_NUMPAD6:
            info().hAlign(HAlign.RIGHT).vAlign(VAlign.MIDDLE);
            break;

        case KeyEvent.VK_NUMPAD7:
            info().hAlign(HAlign.LEFT).vAlign(VAlign.TOP);
            break;

        case KeyEvent.VK_NUMPAD8:
            info().hAlign(HAlign.CENTER).vAlign(VAlign.TOP);
            break;

        case KeyEvent.VK_NUMPAD9:
            info().hAlign(HAlign.RIGHT).vAlign(VAlign.TOP);
            break;

        case KeyEvent.VK_T:
            info().toggle();
            break;

        case KeyEvent.VK_D:
            toggleDescriptionText();
            break;

        case KeyEvent.VK_H:
            toggleHelpText();
            break;
        }
    }

    public static void main(String[] args)
    {
        Game.start(new SceneInfoOverlayDemo());
    }

}
