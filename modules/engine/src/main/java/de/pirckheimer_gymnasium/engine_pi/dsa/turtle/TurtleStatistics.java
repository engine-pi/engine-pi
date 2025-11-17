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
package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colors;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.engine_pi.graphics.boxes.FramedTextBox;
import de.pirckheimer_gymnasium.engine_pi.util.TextUtil;

/**
 * Sammelt statistische Information, wie zum Beispiel die zurückgelegte
 * Wegstrecker der Schilkröte und erstellt Textboxen, die dann in die Szene
 * eingezeichnet werden können.
 *
 * @author Josef Friedrich
 *
 * @since 0.40.0
 */
public class TurtleStatistics
{
    /**
     * @since 0.40.0
     */
    private double traveledDistance;

    /**
     * @since 0.40.0
     */
    private FramedTextBox textBox;

    /**
     * @since 0.40.0
     */
    public TurtleStatistics()
    {
        traveledDistance = 0;
        textBox = new FramedTextBox(TextUtil.roundNumber(traveledDistance))
                .borderThickness(0).backgroundColor(colors.get("grey", 50))
                .textColor(colors.get("black"));
        textBox.anchor(20, 20);
    }

    /**
     * @since 0.40.0
     */
    public void addTraveledDistance(double distance)
    {
        traveledDistance += distance;
    }

    /**
     * @since 0.40.0
     */
    public void render(Graphics2D g)
    {
        textBox.content(TextUtil.roundNumber(traveledDistance));
        textBox.measure();
        textBox.render(g);
    }
}
