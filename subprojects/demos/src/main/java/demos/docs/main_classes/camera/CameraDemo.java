/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
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
package demos.docs.main_classes.camera;

import pi.Bounds;
import pi.Camera;
import pi.Game;
import pi.Scene;
import pi.Vector;
import pi.actor.Image;
import pi.actor.Line;
import pi.event.FrameUpdateListener;

/**
 * Demonstriert die Klasse {@link Camera}.
 */
public class CameraDemo extends Scene implements FrameUpdateListener
{
    Camera camera;

    Line line;

    public CameraDemo()
    {
        info("Kamera-Demo").description(
                "Bewege den Mauszeiger im Fenster. Es ist ein Pfeil von der Fenstermitte zum Mauszeiger zu sehen. In diese Richtung bewegt sich die Kamera.");
        camera = getCamera();
        Image background = new Image("main-classes/camera/Fez.png", 32);
        background.setCenter(0, 0);
        add(background);

        line = new Line(new Vector(0, 0), new Vector(1, 1));
        line.setColor("red");
        add(line);

        Bounds visible = getVisibleArea();

        camera.bounds(new Bounds(background.getX() + visible.width() / 2,
                background.getY() + visible.height() / 2,
                background.width() - visible.width(),
                background.height() - visible.height()));
    }

    @Override
    public void onFrameUpdate(double pastTime)
    {
        var position = Game.getMousePositionInFrame();
        var size = Game.getWindowSize();
        double x = position.getX() - (size.getX() / 2);
        double y = -1 * (position.getY() - (size.getY() / 2));
        camera.moveFocus(new Vector(x, y).multiply(0.001));
        line.point1(camera.focus());
        line.point2(Game.getMousePosition());
    }

    public static void main(String[] args)
    {
        Game.instantMode(false);
        Game.start(new CameraDemo());
    }
}
