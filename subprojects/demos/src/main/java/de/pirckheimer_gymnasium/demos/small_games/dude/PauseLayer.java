/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha-examples/src/main/java/ea/example/showcase/dude/PauseLayer.java
 *
 * Engine Alpha ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2019 Michael Andonie and contributors.
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
package de.pirckheimer_gymnasium.demos.small_games.dude;

import java.awt.Color;

import pi.Layer;
import pi.actor.Rectangle;
import pi.actor.Text;

public class PauseLayer extends Layer
{
    private static final Color BACKGROUND_COLOR = new Color(100, 200, 255, 120);

    public PauseLayer()
    {
        setLayerPosition(1000);
        Rectangle back = new Rectangle(DudeDemo.WIDTH, DudeDemo.HEIGHT);
        back.setColor(BACKGROUND_COLOR);
        back.setCenter(0, 0);
        add(back);
        Text announce = new Text("Pause.", 10, "Monospaced");
        announce.setCenter(0, 0);
        add(announce);
        setParallaxPosition(0, 0);
        setParallaxZoom(0);
        setParallaxRotation(0);
    }
}
