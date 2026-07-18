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

import static pi.Controller.config;

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/resources/config.md

import pi.Controller;
import pi.Scene;
import pi.Text;
import pi.config.ConfigLoader;

public class ConfigurationChangedListenerDemo extends Scene
{
    public ConfigurationChangedListenerDemo()
    {
        ConfigLoader customConfig = new ConfigLoader(
                "custom-config-loader.properties");
        MyConfigGroup custom = new MyConfigGroup();
        customConfig.addGroup(custom);
        custom.myInt(23);

        custom.onChanged(event -> {
            System.out.println(event.getOldValue());
            System.out.println(event.getNewValue());
            System.out.println(event.getSource().getClass().getCanonicalName());
        });
        custom.myInt(42);
        customConfig.save();
        add(new Text(custom.myInt()).center(0, 0));
    }

    public static void main(String[] args)
    {
        config.game.instantMode(false);
        config.graphics.pixelPerMeter(512);
        Controller.start(new ConfigurationChangedListenerDemo());
    }
}
