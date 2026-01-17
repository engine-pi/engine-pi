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
package demos.docs.resources.color;

import pi.Controller;
import pi.Rectangle;
import pi.Scene;
import pi.Image;

/**
 * Demonstriert die Methoden {@link pi.actor.Actor#color()}
 * {@link pi.actor.Actor#complementaryColor()}.
 *
 * @author Josef Friedrich
 */
public class ImageAverageColorDemo extends Scene
{
    public ImageAverageColorDemo()
    {
        info().title("Figurfarben-Demo")
            .description(
                "Demonstriert die Methoden Actor#color() und Actor#complementaryColor().")
            .help("In der ersten Reihe sind die originalen Bilder zusehen, "
                    + "in der zweiten Reihe die Durchschnittsfarbe und "
                    + "in der dritten Reihe die Komplementärfarbe der Durchschnittsfarbe.");
        camera().meter(90);
        double x = -4;
        for (String filepath : new String[] { "car/background-color-grass.png",
                "car/wheel-back.png", "car/truck-240px.png",
                "dude/background/snow.png", "dude/box/obj_box001.png",
                "dude/moon.png" })
        {
            createImageWithAverageColor(filepath, x);
            x = x + 1.2;
        }
    }

    private void createImageWithAverageColor(String filepath, double x)
    {
        var image = new Image(filepath, 1, 1).anchor(x, 0);
        add(image);
        add(new Rectangle(1.0, 1.0).anchor(x, -1.2).color(image.color()));
        add(new Rectangle(1.0, 0.5).anchor(x, -1.9)
            .color(image.complementaryColor()));
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new ImageAverageColorDemo());
    }
}
