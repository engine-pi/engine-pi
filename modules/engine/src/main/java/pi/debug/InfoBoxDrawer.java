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
package pi.debug;

import java.awt.Graphics2D;

import pi.Game;
import pi.Resources;
import pi.Scene;
import pi.annotations.Internal;
import pi.graphics.boxes.Box;
import pi.graphics.boxes.CompassBox;
import pi.graphics.boxes.FramedTextBox;
import pi.graphics.boxes.HorizontalBox;
import pi.graphics.boxes.VerticalBox;

/**
 * Zeichnet einige <b>Informationsboxen</b> in das linke obere Eck.
 *
 * @author Josef Friedrich
 *
 * @since 0.17.0
 */
public final class InfoBoxDrawer
{
    FramedTextBox fps;

    FramedTextBox actorsCount;

    FramedTextBox gravity;

    CompassBox compass;

    VerticalBox<Box> verticalBox;

    public InfoBoxDrawer()
    {
        fps = new FramedTextBox(null);
        fps.background.color("blue");
        fps.padding.allSides(5);
        fps.textLine.fontSize(12);

        actorsCount = new FramedTextBox(null);
        actorsCount.background.color("green");
        actorsCount.padding.allSides(5);
        actorsCount.textLine.fontSize(12);

        gravity = new FramedTextBox(null);
        gravity.background.color(Resources.colorScheme.getBluePurple());
        gravity.padding.allSides(5);
        gravity.textLine.fontSize(12);

        compass = new CompassBox(25);
        verticalBox = new VerticalBox<>(fps, actorsCount,
                new HorizontalBox<>(gravity, compass));
        verticalBox.padding(5);
    }

    /**
     * Zeichnet einige <b>Informationsboxen</b> in das linke obere Eck.
     *
     * @param g Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     *
     * @hidden
     */
    @Internal
    public void draw(Graphics2D g, Scene scene, double frameDuration)
    {
        var infos = new DebugInformations(scene, frameDuration);

        // Einzelbilder pro Sekunden
        fps.content("FPS: " + infos.fpsFormatted());
        // Anzahl an Figuren
        actorsCount.content("Actors: " + infos.actorsCount());
        // Schwerkraft
        gravity.content(infos.gravityFormatted());

        compass.direction(infos.gravity().getAngle());

        verticalBox.remeasure().render(g);
    }

    public static void main(String[] args)
    {
        Game.debug();
        Game.start();
    }
}
