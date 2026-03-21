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

import static pi.Controller.images;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import pi.Controller;
import pi.actor.Image;
import pi.Scene;
import pi.event.KeyStrokeListener;

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/Image.java

/**
 * Demonstriert die Figur <b>Bild</b> ({@link Image}).
 *
 * @author Josef Friedrich
 */
public class ImageDemo extends Scene implements KeyStrokeListener
{
    Image box1;

    Image box2;

    BufferedImage image1 = images.get("dude/box/obj_box001.png");

    BufferedImage image2 = images.get("dude/box/obj_box002.png", 2);

    BufferedImage image3 = images.get("dude/box/obj_box003.png", 3);

    public ImageDemo()
    {
        info().title("Demonstriert die Figur Image")
            .help("Tastenkürzel: \n" + "1: pixelMultiplication = 1\n"
                    + "2: pixelMultiplication = 2\n"
                    + "3: pixelMultiplication = 3\n" + "v: vertikal spiegeln\n"
                    + "h: horizontal spiegeln\n");
        box1 = new Image("dude/box/obj_box004.png").pixelPerMeter(30);
        box1.x(-7).y(-3);
        add(box1);
        box2 = new Image("dude/box/obj_box005.png").size(2, 1);
        box2.anchor(5, 0);
        add(box2);
    }

    @Override
    public void onKeyDown(KeyEvent event)
    {
        switch (event.getKeyCode())
        {
        case KeyEvent.VK_1 -> box1.image(image1);
        case KeyEvent.VK_2 -> box1.image(image2);
        case KeyEvent.VK_3 -> box1.image(image3);
        case KeyEvent.VK_V -> box1.toggleFlipVertically();
        case KeyEvent.VK_H -> box1.toggleFlipHorizontally();
        }
        System.out.println(box1);
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new ImageDemo());
    }
}
