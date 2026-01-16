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

import pi.annotations.Getter;
import pi.annotations.Setter;
import pi.config.ConfigGroup;
import pi.config.ConfigGroupInfo;

@ConfigGroupInfo(prefix = "custom_")
public class MyConfigGroup extends ConfigGroup
{
    private int myInt = 23;

    @Setter
    public int myInt()
    {
        return myInt;
    }

    @Getter
    public void myInt(int myInt)
    {
        this.myInt = myInt;
    }
}
