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

import pi.Scene;
import pi.Text;
import pi.graphics.geom.Vector;

public class PauseMenu extends Scene
{
    public PauseMenu(Scene mainScene)
    {
        MenuItem back = new MenuItem(mainScene, new Vector(0, -5), "Zurück");
        add(back, back.label);
        Text headline = new Text("Mach mal Pause.");
        headline.height(2);
        headline.center(0, 3);
        add(headline);
    }
}
