/*
 * Source: https://github.com/engine-alpha/tutorials/blob/master/src/eatutorials/util/Util.java
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
package de.pirckheimer_gymnasium.engine_pi.tutorials.util;

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;

public class Util
{
    public static void makeScreenshot(String code)
    {
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        Game.takeScreenshot("Tutorial_" + code + ".png");
    }

    public static String getCallerClassName()
    {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        for (int i = 1; i < stElements.length; i++)
        {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(Util.class.getName())
                    && ste.getClassName().indexOf("java.lang.Thread") != 0)
            {
                return ste.getClassName()
                        .substring(ste.getClassName().lastIndexOf(".") + 1);
            }
        }
        return null;
    }

    public static void addScreenshotKey(String code)
    {
        Game.getActiveScene().addKeyStrokeListener(keyEvent -> {
            if (keyEvent.getKeyCode() == KeyEvent.VK_1)
            {
                makeScreenshot(code);
            }
        });
    }
}
