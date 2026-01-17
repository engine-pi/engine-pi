/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025, 2026 Josef Friedrich and contributors.
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
package demos.classes.class_scene;

import static pi.Controller.colors;

import java.awt.Color;
import java.awt.Graphics2D;

import pi.Circle;
import pi.Controller;
import pi.Scene;

/**
 * Demonstriert die Methode {@link Scene#renderOverlay(Graphics2D, int, int)}.
 *
 * <p>
 * Das Demo zeigt zwei grüne Rechtecke, die die Kreisfigur überlagern. Das linke
 * Rechteck ist undurchsichtig und das rechte Rechteck ist transparent. Die
 * Transparenz wird über eine Farbe mit reduziertem Alphakanal erzeugt.
 * </p>
 */
public class OverlayDemo extends Scene
{

    private int margin = 50;

    public OverlayDemo()
    {
        Circle circle = new Circle(15);
        circle.center(0, 0);
        add(circle);
    }

    @Override
    public void renderOverlay(Graphics2D g, int width, int height)
    {
        Color old = g.getColor();
        int rectangleWidth = (width - 3 * margin) / 2;
        int rectangleHeight = height - 2 * margin;

        // Undurchsichtiges Rechteck links
        g.setColor(colors.get("green"));
        g.fillRect(margin, margin, rectangleWidth, rectangleHeight);

        // Durchsichtiges Rechteck rechts
        g.setColor(colors.get("green", 100));
        g.fillRect(2 * margin + rectangleWidth,
            margin,
            rectangleWidth,
            rectangleHeight);
        g.setColor(old);
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new OverlayDemo());
    }

}
