/*
 * Source: https://github.com/engine-alpha/tutorials/blob/master/src/eatutorials/scenes/MainScene.java
 *
 * Engine Alpha ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2024 Michael Andonie and contributors.
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
package demos.docs.main_classes.scene.tutorial;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/docs/manual/main-classes/scene.md

import pi.Controller;
import pi.Rectangle;
import pi.Scene;
import pi.Text;
import pi.event.FrameUpdateListener;
import pi.event.MouseButton;
import pi.event.MouseClickListener;
import pi.graphics.geom.Vector;

public class MenuItem extends Rectangle
        implements MouseClickListener, FrameUpdateListener
{
    final Text label;

    private final Scene mainScene;

    public MenuItem(Scene mainScene, Vector center, String labelText)
    {
        super(10, 1.5);
        this.mainScene = mainScene;
        label = new Text(labelText, 1);
        label.layerPosition(1);
        label.color("black");
        label.center(center);
        layerPosition(0);
        color("blueGreen");
        center(center);
    }

    @Override
    public void onMouseDown(Vector clickLoc, MouseButton mouseButton)
    {
        if (contains(clickLoc))
        {
            Controller.transitionToScene(mainScene);
        }
    }

    @Override
    public void onFrameUpdate(double pastTime)
    {
        if (contains(Controller.mousePosition()))
        {
            color("blue");
        }
        else
        {
            color("blueGreen");
        }
    }
}
