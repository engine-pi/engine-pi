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

import static pi.Resources.colors;

import java.awt.Graphics2D;

import pi.Controller;
import pi.Resources;
import pi.annotations.Internal;
import pi.graphics.boxes.Box;
import pi.graphics.boxes.ChildsBox;
import pi.graphics.boxes.CompassBox;
import pi.graphics.boxes.DimensionBox;
import pi.graphics.boxes.FramedBox;
import pi.graphics.boxes.FramedTextBox;
import pi.graphics.boxes.HorizontalBox;
import pi.graphics.boxes.TextLineBox;
import pi.graphics.boxes.VAlign;
import pi.graphics.boxes.VerticalBox;
import pi.loop.GameLoop;

/**
 * Zeichnet einige <b>Informationsboxen</b> mit Debug-Informationen in das linke
 * obere Eck.
 *
 * @author Josef Friedrich
 *
 * @since 0.17.0
 */
public final class DebugInfoBoxDrawer
{
    /**
     * Einzelbilder pro Sekunde
     */
    final FramedTextBox fps;

    /**
     * Anzahl an Einzelbildern seit Spielstart
     */
    final FramedTextBox frameCounter;

    /**
     * Anzahl an Figuren
     */
    final FramedTextBox actorsCount;

    /**
     * Schwerkraft
     */
    final TextLineBox gravity;

    final CompassBox compass;

    final VerticalBox<Box> verticalBox;

    public DebugInfoBoxDrawer()
    {
        fps = new FramedTextBox(null);
        fps.background.color(colors.getSafe("blue"));

        frameCounter = new FramedTextBox(null);
        frameCounter.background.color(colors.getSafe("purple"));

        actorsCount = new FramedTextBox(null);
        actorsCount.background.color(colors.getSafe("green"));

        var textBoxes = new ChildsBox<FramedTextBox>(fps, frameCounter,
                actorsCount);
        textBoxes.forEachChild(box -> {
            box.padding.allSides(5);
            box.textLine.fontSize(12).color(colors.getSafe("white"));
        });

        gravity = new TextLineBox("");
        gravity.fontSize(12).color(colors.getSafe("white"));
        compass = new CompassBox(25);
        FramedBox gravityFrame = new FramedBox(
                new HorizontalBox<>(gravity, new DimensionBox(5, 0), compass)
                        .vAlign(VAlign.MIDDLE));
        gravityFrame.background.color(Resources.colorScheme.bluePurple());
        gravityFrame.padding.allSides(5);

        verticalBox = new VerticalBox<>(fps, frameCounter, actorsCount,
                gravityFrame);
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
    public void draw(Graphics2D g, GameLoop loop)
    {
        var infos = new DebugInformationPreparer(loop);
        fps.content("FPS: " + infos.fpsFormatted());
        frameCounter.content("Frame counter: " + loop.frameCounter());
        actorsCount.content("Actors: " + infos.actorsCount());
        gravity.content(infos.gravityFormatted());
        compass.direction(infos.gravity().angle());
        verticalBox.remeasure().render(g);
    }

    public static void main(String[] args)
    {
        Controller.debug();
        Controller.start();
    }
}
