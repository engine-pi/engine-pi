/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2024 Josef Friedrich and contributors.
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
package demos.classes.actor;

import java.awt.event.KeyEvent;

import pi.Controller;
import pi.actor.Image;
import pi.Scene;
import pi.event.KeyStrokeListener;

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/Image.java

/**
 * Demonstriert wie die <b>Abmessungen</b> der Figur <b>Bild</b> ({@link Image})
 * verändert werden können.
 *
 * @author Josef Friedrich
 */
public class ImageDimensionDemo extends Scene implements KeyStrokeListener
{
    Image traveler;

    public ImageDimensionDemo()
    {
        info().title(
            "Demonstriert wie die Abmessungen der Figur Image verändert werden können.")
            .help("Tastenkürzel:",
                "1: pixelPerMeter = 32",
                "2: pixelPerMeter = 64",
                "3: pixelPerMeter = 128",
                "w: width = 5m",
                "h: height = 5m",
                "s: size(1, 5) width = 1m height = 5m");

        traveler = new Image("openpixelproject/various/opp_promo_traveler.png");
        traveler.center(0, 0);
        add(traveler);
    }

    @Override
    public void onKeyDown(KeyEvent event)
    {
        switch (event.getKeyCode())
        {
        case KeyEvent.VK_1 -> traveler.pixelPerMeter(32);
        case KeyEvent.VK_2 -> traveler.pixelPerMeter(64);
        case KeyEvent.VK_3 -> traveler.pixelPerMeter(128);
        case KeyEvent.VK_W -> traveler.reset().width(5);
        case KeyEvent.VK_H -> traveler.reset().height(5);
        case KeyEvent.VK_S -> traveler.reset().size(1, 5);
        }
        System.out.println(traveler);
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new ImageDimensionDemo());
    }
}
