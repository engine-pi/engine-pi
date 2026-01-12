/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025, 2026 Josef Friedrich and contributors.
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
package demos.classes.dsa.graph.dfs;

import pi.Controller;
import pi.Scene;
import pi.Image;
import pi.dsa.graph.GraphDrawer;

public class GraphDrawerDemo extends Scene
{

    public GraphDrawerDemo()
    {
        Image graphImage = new Image(
                "/home/jf/Nextcloud/graph-schoolbooks/Oldenbourgh/k11_a2_1.png",
                // "/home/jf/Nextcloud/graph-schoolbooks/Cornelsen/11-spb/Seite-58.png",
                10);
        add(graphImage);

        new GraphDrawer(this);

        camera().focus(graphImage);
    }

    public static void main(String[] args)
    {
        Controller.start(new GraphDrawerDemo(), 1200, 800);
    }
}
