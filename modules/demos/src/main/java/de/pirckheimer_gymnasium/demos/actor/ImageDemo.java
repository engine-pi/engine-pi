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
package de.pirckheimer_gymnasium.demos.actor;

import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Game;

import de.pirckheimer_gymnasium.engine_pi.actor.Image;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * Demonstriert die Figur <b>Bild</b> ({@link Image}).
 *
 * @author Josef Friedrich
 */
public class ImageDemo extends Scene implements KeyStrokeListener
{
    Image box1;

    Image box2;

    BufferedImage image1 = Resources.images.get("dude/box/obj_box003.png");

    BufferedImage image2 = Resources.images.get("dude/box/obj_box004.png", 2);

    BufferedImage image3 = Resources.images.get("dude/box/obj_box005.png", 3);

    public ImageDemo()
    {
        box1 = new Image("dude/box/obj_box001.png", 30);
        add(box1);
        box2 = new Image("dude/box/obj_box002.png", 2, 1);
        box2.setPosition(5, 0);
        add(box2);
    }

    @Override
    public void onKeyDown(KeyEvent event)
    {
        switch (event.getKeyCode())
        {
        case KeyEvent.VK_1 -> box1.setImage(image1);
        case KeyEvent.VK_2 -> box1.setImage(image2);
        case KeyEvent.VK_3 -> box1.setImage(image3);
        case KeyEvent.VK_V -> box1.flipVertically();
        case KeyEvent.VK_H -> box1.flipHorizontally();
        }
        System.out.println(box1);
    }

    public static void main(String[] args)
    {
        Game.start(new ImageDemo());
    }
}
