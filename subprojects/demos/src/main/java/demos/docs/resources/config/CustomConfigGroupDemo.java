/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2026 Josef Friedrich and contributors.
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
package demos.docs.resources.config;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/docs/manual/resources/config.md

import static pi.Controller.config;

import pi.Controller;
import pi.Scene;
import pi.Text;

public class CustomConfigGroupDemo extends Scene
{
    static
    {
        MyConfigGroup custom = new MyConfigGroup();
        config.add(custom);
        custom.myInt(42);
    }

    public CustomConfigGroupDemo()
    {
        MyConfigGroup custom = config.getGroup(MyConfigGroup.class);
        // Oder:
        // MyConfigGroup custom = (MyConfigGroup) config.getGroup("custom_");
        add(new Text(custom.myInt()).center(0, 0));
    }

    public static void main(String[] args)
    {
        config.game.instantMode(false);
        config.graphics.pixelPerMeter(512);
        Controller.start(new CustomConfigGroupDemo());
    }
}
