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
package demos.docs.main_classes;

import pi.Bounds;
import pi.Camera;
import pi.Controller;
import pi.Scene;
import pi.Vector;
import pi.Image;
import pi.actor.Line;
import pi.event.FrameUpdateListener;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/engine/src/main/java/pi/Camera.java
// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/docs/manual/main-classes/camera.md

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
                "Bewege den Mauszeiger im Fenster. Es ist ein Pfeil von der Fenstermitte zum Mauszeiger zu sehen. In diese Richtung bewegt sich die Kamera.")
                .disable();
        camera = camera();
        Image background = new Image("main-classes/camera/Fez.png", 32);
        background.center(0, 0);
        add(background);

        line = new Line(new Vector(0, 0), new Vector(1, 1));
        line.color("red");
        add(line);

        Bounds visible = visibleArea();

        Bounds cameraBounds = new Bounds(background.x() + visible.width() / 2,
                background.y() + visible.height() / 2,
                background.width() - visible.width(),
                background.height() - visible.height());

        camera.bounds(cameraBounds);
    }

    @Override
    public void onFrameUpdate(double pastTime)
    {
        var position = Controller.mousePositionInFrame();
        var size = Controller.windowSize();
        double x = position.getX() - (size.x() / 2);
        double y = -1 * (position.getY() - (size.y() / 2));
        camera.moveFocus(new Vector(x, y).multiply(0.001));
        line.point1(camera.focus());
        line.point2(Controller.mousePosition());
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new CameraDemo());
    }
}
